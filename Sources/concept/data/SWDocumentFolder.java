package concept.data;

import is.rebbi.wo.interfaces.SWFolderInterface;
import is.rebbi.wo.interfaces.SWInheritsPrivileges;
import is.rebbi.wo.util.USEOUtilities;
import is.rebbi.wo.util.USHierarchyUtilities;

import java.util.Enumeration;

import com.webobjects.eoaccess.EOUtilities;
import com.webobjects.eocontrol.EOAndQualifier;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOEnterpriseObject;
import com.webobjects.eocontrol.EOFetchSpecification;
import com.webobjects.eocontrol.EOKeyValueQualifier;
import com.webobjects.eocontrol.EOQualifier;
import com.webobjects.eocontrol.EOSortOrdering;
import com.webobjects.foundation.NSArray;

import concept.data.auto._SWDocumentFolder;

/**
 * An SWDocumentFolder represents a folder containing SWDocument objects
 */

public class SWDocumentFolder extends _SWDocumentFolder implements SWFolderInterface<SWDocumentFolder, SWDocument> {

	private static final NSArray DEFAULT_SORT_ORDERINGS = new NSArray( new EOSortOrdering( "name", EOSortOrdering.CompareAscending ) );
	private static final EOQualifier ROOT_FOLDER_QUALIFIER = new EOKeyValueQualifier( "parentFolderID", EOQualifier.QualifierOperatorEqual, null );
	private static final EOFetchSpecification ROOT_FOLDER_FS = new EOFetchSpecification( "SWDocumentFolder", ROOT_FOLDER_QUALIFIER, DEFAULT_SORT_ORDERINGS );

	/**
	 * Returns all folder objects in the database
	 *
	 * @param ec The EOEditingContext to fetch into
	 * @return all folder objects
	 */
	public static NSArray<SWDocumentFolder> allFolders( EOEditingContext ec ) {
		return EOUtilities.objectsForEntityNamed( ec, "SWDocumentFolder" );
	}

	/**
	 * Returns all folder objects without a parent folder (essentially root folders), sorted alphabetically
	 *
	 * @param ec The calling EOEditingContext
	 * @return folder objects without a parent folder
	 */
	@Override
	public NSArray<SWDocumentFolder> sortedRootFolders( EOEditingContext ec ) {
		return ec.objectsWithFetchSpecification( ROOT_FOLDER_FS );
	}

	/**
	 * Returns all subfolders sorted alphabetically
	 */
	@Override
	public NSArray<SWDocumentFolder> sortedSubFolders() {
		return EOSortOrdering.sortedArrayUsingKeyOrderArray( subFolders(), DEFAULT_SORT_ORDERINGS );
	}

	/**
	 * Returns all pictures in this folder, sorted alphabetically
	 *
	 * @return An NSArray of SWDocument objects
	 */
	@Override
	public NSArray<SWDocument> sortedDocuments() {
		return USEOUtilities.objectsForKeyWithValueSortedByKey( editingContext(), SWDocument.ENTITY_NAME, SWDocument.DOCUMENT_FOLDER_ID_KEY, folderID(), SWDocument.NAME_KEY );
	}

	/**
	 * @return A document in this folder containing the linkKey
	 */
	public SWDocument documentWithLinkKey( String key ) {
		EOQualifier q1 = new EOKeyValueQualifier( SWDocument.DOCUMENT_FOLDER_ID_KEY, EOQualifier.QualifierOperatorEqual, this.folderID() );
		EOQualifier q2 = new EOKeyValueQualifier( SWDocument.LINK_KEY_KEY, EOQualifier.QualifierOperatorEqual, key );
		EOQualifier q = new EOAndQualifier( new NSArray<>( new EOQualifier[] { q1, q2 } ) );
		EOFetchSpecification fs = new EOFetchSpecification( SWDocument.ENTITY_NAME, q, null );
		NSArray<SWDocument> list = editingContext().objectsWithFetchSpecification( fs );
		SWDocument doc = null;

		if( list.count() > 0 ) {
			doc = list.objectAtIndex( 0 );
		}

		return doc;
	}

	/**
	 * A convenience method to create a new folder and insert it into the specified EOEditingContext
	 *
	 * @param ec The current EOEditingContext
	 * @param aName A name for the new folder
	 * @param aFolder the parent folder. If this is null, the resulting object is a root folder
	 * @return An NSArray of SWPicture objects
	 */
	public static SWDocumentFolder newFolderWithNameAndParentFolder( EOEditingContext ec, String aName, SWDocumentFolder aFolder ) {
		SWDocumentFolder a = new SWDocumentFolder();
		ec.insertObject( a );

		a.setName( aName );
		a.setInheritsPrivileges( 1 );

		if( aFolder != null ) {
			a.addObjectToBothSidesOfRelationshipWithKey( aFolder, "parentFolder" );
		}

		return a;
	}

	/**
	 * Calculates the total size of documents in this folder. Does not include subfolders.
	 */
	public long size() {
		Enumeration<SWDocument> e = documents().objectEnumerator();

		int totalSize = 0;

		while( e.hasMoreElements() ) {
			totalSize += e.nextElement().size();
		}

		return totalSize;
	}

	/**
	 * Returns all parent folders of this folder
	 *
	 * @param includingSelf Indicates if the calling object should be included in the resulting array
	 */
	public NSArray<SWDocumentFolder> everyParentFolder( boolean includingSelf ) {
		return USHierarchyUtilities.everyParentNode( this, includingSelf );
	}

	/**
	 * Indicates if this folder is a subfolder of the named folder
	 *
	 * @param parentFolder The folder to check against
	 * @param includingTopLevel Indicates if parentFolder should be included in the check
	 */
	public boolean isSubFolderOfFolder( SWDocumentFolder parentFolder, boolean includingTopLevel ) {
		return USHierarchyUtilities.isChildOfNode( this, parentFolder, includingTopLevel );
	}

	/**
	 * Deletes this folder and all documents contained in it
	 */
	public void deleteFolder() {
		Enumeration<SWDocument> e = documents().objectEnumerator();

		while( e.hasMoreElements() ) {
			e.nextElement().deleteAsset();
		}

		if( parentFolder() != null ) {
			this.removeObjectFromBothSidesOfRelationshipWithKey( parentFolder(), "parentFolder" );
		}

		editingContext().deleteObject( this );
	}

	/**
	 * Implementation of SWHierarchy - returns this object's parentFolder
	 */
	@Override
	public SWDocumentFolder parent() {
		return parentFolder();
	}

	/**
	 * Implementation of SWHierarchy - returns this object's children
	 */
	@Override
	public NSArray<SWDocumentFolder> children() {
		return subFolders();
	}

	/**
	 * Implementation of SWTransferable - transfers ownership to a new folder
	 *
	 * @param newOwner The destination parent object
	 */
	@Override
	public void transferOwnership( EOEnterpriseObject newOwner ) {
		this.removeObjectFromBothSidesOfRelationshipWithKey( parentFolder(), "parentFolder" );
		this.addObjectToBothSidesOfRelationshipWithKey( newOwner, "parentFolder" );
	}

	/**
	 * Returns the named subfolder. If a folder with that name does not esist, or null is passed as the folder name, null is returned
	 *
	 * @param folderName The name of the folder to retrieve
	 */
	@Override
	public SWDocumentFolder subFolderNamed( String folderName ) {

		if( folderName == null ) {
			return null;
		}

		Enumeration e = subFolders().objectEnumerator();

		while( e.hasMoreElements() ) {
			SWDocumentFolder currentFolder = ((SWDocumentFolder)e.nextElement());

			if( folderName.equals( currentFolder.name() ) ) {
				return currentFolder;
			}
		}

		return null;
	}

	/**
	 * Returns the named item from the folder
	 */
	public SWDocument itemNamed( String aName ) {

		if( aName == null ) {
			return null;
		}

		Enumeration<SWDocument> e = documents().objectEnumerator();

		while( e.hasMoreElements() ) {
			SWDocument nextDocument = e.nextElement();

			if( aName.equals( nextDocument.name() ) ) {
				return nextDocument;
			}
		}

		return null;
	}

	/**
	 * Implementation of SWInheritsPrivileges - returns the object this object should inherit privileges from (the parent folder)
	 */
	@Override
	public SWInheritsPrivileges inheritsPrivilegesFrom() {
		return parentFolder();
	}

	@Override
	public Integer folderID() {
		return Integer.valueOf( primaryKey() );
	}

	@Override
	public boolean batchFetchContent() {
		return false;
	}

	@Override
	public boolean showThumbnails() {
		return false;
	}

	@Override
	public String title() {
		return "Skj&ouml;l";
	}

	@Override
	public String key() {
		return "skjol";
	}
}