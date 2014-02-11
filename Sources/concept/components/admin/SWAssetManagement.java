package concept.components.admin;

import is.rebbi.wo.interfaces.SWAsset;
import is.rebbi.wo.interfaces.SWDataAsset;
import is.rebbi.wo.interfaces.SWFolderInterface;
import is.rebbi.wo.util.SWSettings;
import is.rebbi.wo.util.USArrayUtilities;
import is.rebbi.wo.util.USEOUtilities;
import is.rebbi.wo.util.USHierarchyUtilities;
import is.rebbi.wo.util.USUtilities;

import java.util.Enumeration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOComponent;
import com.webobjects.appserver.WOContext;
import com.webobjects.appserver.WOResponse;
import com.webobjects.eoaccess.EOModelGroup;
import com.webobjects.eoaccess.EOUtilities;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOEnterpriseObject;
import com.webobjects.eocontrol.EOFetchSpecification;
import com.webobjects.eocontrol.EORelationshipManipulation;
import com.webobjects.eocontrol.EOSortOrdering;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSDictionary;
import com.webobjects.foundation.NSKeyValueCoding;
import com.webobjects.foundation.NSKeyValueCodingAdditions;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSMutableDictionary;

import concept.SWAdminComponent;
import concept.SWDocumentRequestHandler;
import concept.SWSession;
import concept.data.SWDocument;
import concept.data.SWNewsCategory;
import concept.data.SWNewsItem;
import concept.data.SWPicture;
import concept.deprecated.SWLoc;
import er.extensions.eof.qualifiers.ERXInQualifier;

public class SWAssetManagement extends SWAdminComponent {

	private static final Logger logger = LoggerFactory.getLogger( SWAssetManagement.class );

	public static final String EVEN_ROW_COLOR = "#d0d0d0";
	public static final String ODD_ROW_COLOR = "#d8d8d8";
	public static final String UNPUBLISHED_ROW_COLOR = "#cc9999";
	public static final String SELECTED_ROW_COLOR = "#ffffff";

	public SWFolderInterface selectedFolder;

	public SWAsset currentAsset;
	public SWAsset selectedAsset;

	public int currentBatchNo = 1;
	public int maxBatchNo = 1;

	public boolean useID = false;
	public WOComponent componentToReturn;
	public NSKeyValueCoding record;
	public String fieldName, currentString;

	public int tableIndex;

	public String documentsTabName = SWLoc.string( "sfAdminTabDocuments", session() );
	public String privilegesTabName = SWLoc.string( "sfAdminTabAccessPrivileges", session() );

	private NSArray _tabs;
	public String selectedTab = documentsTabName;

	private boolean hasSelectedAsset = false;

	private String _entityName;
	private String _folderEntityName;
	private String _editingComponentName;
	private NSDictionary _privilegePairs;
	private Class _entityClass;

	public SWAssetManagement( WOContext c ) {
		super( c );
	}

	public NSArray tabs() {

		if( _tabs == null ) {
			// TODO: Fix error here, if enablePrivileges is false then the user variable is never checked but if enablePrivileges is true then
			// we get a nullpointerexception as the user variable in the session isn't set on login when using the admin login
			// from the .conf file.

			NSMutableArray t = new NSMutableArray();
			t.addObject( documentsTabName );

			if( isNewsItem() ) {
				t.addObject( "RSS" );
			}

			if( SWSettings.booleanForKey( "enablePrivileges" ) && (user() == null || user().hasPrivilegeFor( selectedFolder, "canManageUsers" )) ) {
				t.addObject( privilegesTabName );
			}

			_tabs = t.immutableClone();
		}

		return _tabs;
	}

	@Override
	public void appendToResponse( WOResponse r, WOContext c ) {
		if( record != null ) {
			if( !hasSelectedAsset ) {
				if( record.valueForKey( fieldName ) != null ) {
					if( !useID ) {
						setSelectedAsset( (SWAsset)record.valueForKey( fieldName ) );
					}
					else {
						setSelectedAssetUsingID( record.valueForKey( fieldName ) );
					}
				}
			}
		}
		super.appendToResponse( r, c );
	}

	private String primaryKeyAttributeName() {
		return EOModelGroup.defaultGroup().entityNamed( entityName() ).primaryKeyAttributeNames().objectAtIndex( 0 );
	}

	private void setSelectedAssetUsingID( Object o ) {
		try {
			SWAsset asset = (SWAsset)USEOUtilities.objectMatchingKeyAndValue( session().defaultEditingContext(), entityName(), primaryKeyAttributeName(), o );
			setSelectedAsset( asset );
		}
		catch( Exception ex ) {

		}
	}

	private void setSelectedAsset( SWAsset asset ) {

		if( asset != null ) {
			hasSelectedAsset = true;
			setSelectedFolder( asset.containingFolder() );
		}

		selectedAsset = asset;
	}

	private void setSelectedFolder( SWFolderInterface newFolder ) {
		selectedFolder = newFolder;
		expandAllParentFolders( selectedFolder );
		setSelectedAsset( null );
	}

	public String rowColor() {
		if( currentAsset instanceof SWNewsItem && !USUtilities.numberIsTrue( ((SWNewsItem)currentAsset).published() ) ) {
			return UNPUBLISHED_ROW_COLOR;
		}

		if( isSelected() ) {
			return SELECTED_ROW_COLOR;
		}

		return (tableIndex % 2 == 0) ? EVEN_ROW_COLOR : ODD_ROW_COLOR;
	}

	public boolean isSelected() {
		return currentAsset.equals( selectedAsset );
	}

	public String itemClass() {
		String klass = null;
		if( isSelected() ) {
			klass = "selected";
		}
		return klass;
	}

	public boolean isDataAsset() {
		return SWDataAsset.class.isAssignableFrom( entityClass() );
	}

	public boolean isNewsItem() {
		return SWNewsItem.class.isAssignableFrom( entityClass() );
	}

	public WOComponent saveAsset() {

		if( !useID ) {
			if( record instanceof EOEnterpriseObject ) {
				((EOEnterpriseObject)record).addObjectToBothSidesOfRelationshipWithKey( currentAsset, fieldName );
				session().defaultEditingContext().saveChanges();
			}
			else if( record instanceof NSKeyValueCodingAdditions ) {
				((NSKeyValueCodingAdditions)record).takeValueForKeyPath( currentAsset, fieldName );
			}
			else {
				record.takeValueForKey( currentAsset, fieldName );
			}
		}
		else {
			if( record instanceof EOEnterpriseObject ) {
				((EOEnterpriseObject)record).takeValueForKeyPath( currentAsset.primaryKey(), fieldName );
				session().defaultEditingContext().saveChanges();
			}
			else if( record instanceof NSKeyValueCodingAdditions ) {
				((NSKeyValueCodingAdditions)record).takeValueForKeyPath( currentAsset.primaryKey(), fieldName );
			}
			else {
				record.takeValueForKey( currentAsset.primaryKey(), fieldName );
			}
		}

		return returnBack();
	}

	/*
	public WOComponent saveAssetPreview() {
		String size = currentString;

		if( record instanceof EOEnterpriseObject ) {
			((EOEnterpriseObject)record).takeValueForKeyPath( size, "customInfo.size" );
		}
		else if( record instanceof NSKeyValueCodingAdditions ) {
			((NSKeyValueCodingAdditions)record).takeValueForKeyPath( size, "customInfo.size" );
		}
		else {
			record.takeValueForKey( size, "customInfo.size" );
		}

		return saveAsset();
	}
	*/

	public WOComponent selectAsset() {
		setSelectedAsset( currentAsset );
		return null;
	}

	public WOComponent selectFolder() {
		setSelectedAsset( null );
		maxBatchNo = (folderDocumentCount() / 50) + 1;
		currentBatchNo = 1;
		return null;
	}

	public WOComponent returnBack() {
		componentToReturn.ensureAwakeInContext( context() );
		return componentToReturn;
	}

	public WOComponent deleteAsset() {
		currentAsset.deleteAsset();
		setSelectedAsset( null );
		return saveChanges();
	}

	public WOComponent deleteSelectedFolder() {
		deleteFolder( selectedFolder );
		setSelectedFolder( null );
		return saveChanges();
	}

	public WOComponent createAsset() {
		SWAsset asset = (SWAsset)EOUtilities.createAndInsertInstance( session().defaultEditingContext(), entityName() );
		asset.setContainingFolder( selectedFolder );
		setSelectedAsset( asset );
		return saveChanges();
	}

	public WOComponent createRootFolder() {
		setSelectedFolder( newFolderWithNameAndParentFolder( session().defaultEditingContext(), null, null, folderEntityName() ) );
		return saveChanges();
	}

	public WOComponent createSubFolder() {
		setSelectedFolder( newFolderWithNameAndParentFolder( session().defaultEditingContext(), null, selectedFolder, folderEntityName() ) );
		return saveChanges();
	}

	public WOComponent saveChanges() {
		session().defaultEditingContext().saveChanges();
		return null;
	}

	public String currentAssetURL() {
		return SWDocumentRequestHandler.urlForDocumentInContext( (SWDocument)currentAsset, context() );
	}

	public WOActionResults editPicture() {
		SWPreviewPicture nextPage = (SWPreviewPicture)pageWithName( "SWPreviewPicture" );
		nextPage.setPicture( (SWPicture)currentAsset );
		return nextPage;
	}

	public SWPicture currentAssetAsPicture() {
		return (SWPicture)currentAsset;
	}

	public String currentAssetThumbURL() {
		if( currentAsset instanceof SWPicture ) {
			return ((SWPicture)currentAsset).thumbnailURL();
		}
		else {
			return "";
		}
	}

	public boolean isPicture() {
		return currentAsset instanceof SWPicture;
	}

	public String entityName() {
		return _entityName;
	}

	public void setEntityName( String s ) {
		_entityName = s;
	}

	public String folderEntityName() {
		return _folderEntityName;
	}

	public void setFolderEntityName( String s ) {
		_folderEntityName = s;
	}

	public String editingComponentName() {
		return _editingComponentName;
	}

	public void setEditingComponentName( String s ) {
		_editingComponentName = s;
	}

	public NSDictionary privilegePairs() {
		if( _privilegePairs == null ) {
			_privilegePairs = defaultPrivilegePairs();
		}

		return _privilegePairs;
	}

	public void setPrivilegePairs( NSDictionary d ) {
		_privilegePairs = d;
	}

	private NSDictionary defaultPrivilegePairs() {
		NSMutableDictionary d = new NSMutableDictionary();
		d.setObjectForKey( "allowToSee", SWLoc.string( "docpSeeFolders", session() ) );
		d.setObjectForKey( "canManageUsers", SWLoc.string( "docpManagePrivileges", session() ) );
		return d;
	}

	private Class entityClass() {
		if( _entityClass == null ) {
			_entityClass = USEOUtilities.classForEntityNamed( session().defaultEditingContext(), entityName() );
		}

		return _entityClass;
	}

	private static SWFolderInterface newFolderWithNameAndParentFolder( EOEditingContext ec, String aName, SWFolderInterface parentFolder, String folderEntityName ) {
		SWFolderInterface newFolder = (SWFolderInterface)EOUtilities.createAndInsertInstance( ec, folderEntityName );

		newFolder.setName( aName );
		newFolder.setInheritsPrivileges( 1 );

		if( parentFolder != null ) {
			newFolder.addObjectToBothSidesOfRelationshipWithKey( parentFolder, "parentFolder" );
		}

		return newFolder;
	}

	private void expandAllParentFolders( SWFolderInterface aFolder ) {
		Enumeration e = USHierarchyUtilities.everyParentNode( aFolder, true ).objectEnumerator();

		while( e.hasMoreElements() ) {
			SWFolderInterface nextFolder = (SWFolderInterface)e.nextElement();
			((SWSession)session()).addObjectToArrayWithKey( nextFolder, folderEntityName() );
		}
	}

	private void deleteFolder( SWFolderInterface folder ) {
		try {
			NSArray a = folder.sortedDocuments();

			if( USArrayUtilities.hasObjects( a ) ) {
				Enumeration e1 = a.objectEnumerator();

				while( e1.hasMoreElements() ) {
					SWAsset asset = (SWAsset)e1.nextElement();
					asset.deleteAsset();
				}
			}
		}
		catch( Exception e ) {
			logger.debug( "Error while deleting folder", e );
		}

		if( folder.parent() != null ) {
			folder.removeObjectFromBothSidesOfRelationshipWithKey( (EORelationshipManipulation)folder.parent(), "parentFolder" );
		}

		session().defaultEditingContext().deleteObject( folder );
	}

	/**
	 * TODO: Hack to temporarily add access perms for creating and deleting forms and form folders to SoloForm only.
	 * Does not affect news, pictures, docs, SoloMail or other asset managers.
	 * @return
	 */
	public boolean canCreateDeleteAssetsInCurrentFolder() {
		boolean result = true;
/*
		if( selectedFolder != null && user() != null && selectedFolder.getClass().getName().equals( SWFFormFolder.class.getName() ) ) {
			result = user().hasPrivilegeFor( selectedFolder, "allowToCreateDelete" );
		}
*/
		return result;
	}

	public int folderDocumentCount() {
		int theCount = 0;

		if( selectedFolder instanceof SWNewsCategory ) {
			NSArray count = EOUtilities.rawRowsForSQL( session().defaultEditingContext(), "SoloWeb", "select count(*) as cnt from sw_news_item where news_category_id=" + selectedFolder.folderID(), null );
			if( count != null && count.count() > 0 ) {
				NSDictionary dict = (NSDictionary)count.objectAtIndex( 0 );
				Number cnt = (Number)dict.valueForKey( "CNT" );
				theCount = cnt.intValue();
			}
		}
		else {
			return selectedFolder.sortedDocuments().count();
		}

		return theCount;
	}

	public NSArray sortedDocuments() {
		NSArray docs = null;

		if( selectedFolder.batchFetchContent() == false ) {
			// Return all objects
			docs = selectedFolder.sortedDocuments();
		}
		else {
			// Return a batch of objects (currently fixed at 50 objects)
			EOFetchSpecification fs = new EOFetchSpecification( SWNewsItem.ENTITY_NAME, SWNewsItem.CATEGORY.eq( (SWNewsCategory)selectedFolder ), SWNewsItem.DATE.descs() );
			fs.setFetchesRawRows( true );
			fs.setRawRowKeyPaths( new NSArray<>( new String[] { SWNewsItem.ID_KEY, SWNewsItem.DATE_KEY } ) );
			NSArray rawIds = session().defaultEditingContext().objectsWithFetchSpecification( fs );

			if( rawIds != null && rawIds.count() > 0 ) {
				NSMutableArray ids = new NSMutableArray();
				int startNo = (currentBatchNo - 1) * 50;
				int endNo = startNo + 50;
				for( int i = 0; i < 50 && (i + startNo) < rawIds.count(); i++ ) {
					NSDictionary row = (NSDictionary)rawIds.objectAtIndex( startNo + i );
					Object itemID = row.valueForKey( SWNewsItem.ID_KEY );
					ids.addObject( itemID );
				}

				// Get the actual objects
				ERXInQualifier inQualifier = new ERXInQualifier( SWNewsItem.ID_KEY, ids );
				EOSortOrdering sort = new EOSortOrdering( SWNewsItem.DATE_KEY, EOSortOrdering.CompareDescending );
				EOFetchSpecification fs2 = new EOFetchSpecification( SWNewsItem.ENTITY_NAME, inQualifier, new NSArray<>( sort ) );
				docs = session().defaultEditingContext().objectsWithFetchSpecification( fs2 );
			}
		}

		return docs;
	}

	public WOComponent prevBatch() {
		if( currentBatchNo > 1 ) {
			currentBatchNo--;
		}

		return null;
	}

	public WOComponent nextBatch() {
		if( currentBatchNo < maxBatchNo ) {
			currentBatchNo++;
		}
		return null;
	}

	public NSArray<String> previewSizesList() {
		if( currentAsset == null ) {
			return null;
		}
		SWPicture pic = (SWPicture)currentAsset;
		String sizes = (String)pic.customInfo().valueForKey( "sizes" );
		NSArray<String> list = new NSArray<>( sizes.split( "," ) );
		return list;
	}

	public String thumbClass() {
		String klass = "thumb";
		if( currentAsset.equals( selectedAsset ) ) {
			klass += " selected";
		}
		return klass;
	}
}