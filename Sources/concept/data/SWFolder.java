package concept.data;

import is.rebbi.wo.interfaces.HasFakeRelationship;
import is.rebbi.wo.interfaces.SWInheritsPrivileges;
import is.rebbi.wo.util.USHierarchy;

import java.util.Enumeration;

import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOEnterpriseObject;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableArray;

import concept.data.auto._SWFolder;
import er.extensions.eof.ERXEnterpriseObject;
import er.extensions.eof.ERXGenericRecord;

/**
 * A folder.
 */

public class SWFolder extends _SWFolder implements USHierarchy<SWFolder>, SWInheritsPrivileges {

	/**
	 * @return Folders without a parent folder
	 */
	public static NSArray<SWFolder> rootFolders( EOEditingContext ec ) {
		return SWFolder.fetchSWFolders( ec, PARENT.isNull(), NAME.ascInsensitives() );
	}

	/**
	 * Transfers this folder to a new parent folder
	 */
	public void transferOwnership( EOEnterpriseObject newOwner ) {
		removeObjectFromBothSidesOfRelationshipWithKey( parent(), PARENT_KEY );
		addObjectToBothSidesOfRelationshipWithKey( newOwner, PARENT_KEY );
	}

	/**
	 * Implementation of SWInheritsPrivileges - returns the object this object
	 * should inherit privileges from (the parent folder)
	 */
	@Override
	public SWInheritsPrivileges inheritsPrivilegesFrom() {
		return parent();
	}

	/**
	 * Creates a new folder with the given parent folder.
	 */
	public static SWFolder newFolderWithNameAndParentFolder( EOEditingContext ec, String aName, SWFolder parentFolder, String folderEntityName ) {
		SWFolder newFolder = SWFolder.createSWFolder( ec );

		newFolder.setName( aName );
		newFolder.setInheritsPrivileges( 1 );

		if( parentFolder != null ) {
			newFolder.addObjectToBothSidesOfRelationshipWithKey( parentFolder, PARENT_KEY );
		}

		return newFolder;
	}

	public SWFolder subFolderFromPath( String pathString ) {

		if( pathString == null ) {
			return null;
		}

		SWFolder workingDirectory = this;
		NSMutableArray<String> pathArray = NSArray.componentsSeparatedByString( pathString, "/" ).mutableClone();
		pathArray.removeObjectAtIndex( pathArray.count() - 1 );

		Enumeration<String> e = pathArray.objectEnumerator();

		while( e.hasMoreElements() ) {
			workingDirectory = workingDirectory.subFolderNamed( (e.nextElement()) );
		}

		return workingDirectory;
	}

	/**
	 * Returns the first instance of the named subfolder. If a folder with that
	 * name does not exist, or null is passed as the folder name, null is returned
	 */
	public SWFolder subFolderNamed( String folderName ) {

		if( folderName == null ) {
			return null;
		}

		NSArray<SWFolder> a = this.sortedChildren();

		for( SWFolder f : a ) {

			if( folderName.equals( f.name() ) ) {
				return f;
			}
		}

		return null;
	}

	/**
	 * @return Subfolders sorted by name.
	 */
	public NSArray<SWFolder> sortedChildren() {
		return NAME.ascInsensitives().sorted( children() );
	}

	/**
	 * @return All items in this folder and it's subfolders, sorted by name.
	 */
	public NSArray<ERXGenericRecord> items() {
		NSMutableArray<ERXGenericRecord> items = new NSMutableArray<>();

		for( SWFolderLink link : links() ) {
			items.addObject( HasFakeRelationship.Util.targetObject( link ) );
		}

		return NAME.ascInsensitives().sorted( items );
	}

	/**
	 * @return All items in this folder and it's subfolders, sorted by name.
	 */
	public NSArray<ERXGenericRecord> itemsIncludingItemsFromChildren() {
		NSMutableArray<ERXGenericRecord> items = items().mutableClone();

		for( SWFolder folder : children() ) {
			items.addObjectsFromArray( folder.itemsIncludingItemsFromChildren() );
		}

		return NAME.ascInsensitives().sorted( items );
	}

	public void addItem( ERXEnterpriseObject item ) {
		SWFolderLink link = createLinksRelationship();
		HasFakeRelationship.Util.setTargetObject( link, item );
	}

	public int count() {
		return items().count();
	}

	/**
	 * Returns all items in this folder, sorted by default sort order.
	 */
	public <E extends ERXGenericRecord> NSArray<E> itemsOfType( Class<E> clazz ) {
		NSMutableArray<E> results = new NSMutableArray<>();

		for( SWFolderLink link : links() ) {
			Object o = HasFakeRelationship.Util.targetObject( link );

			if( o != null && o.getClass().isAssignableFrom( clazz ) ) {
				results.addObject( (E)o );
			}
		}

		return results;
	}
}