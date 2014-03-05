package concept.data;

import is.rebbi.wo.interfaces.SWFolderInterface;
import is.rebbi.wo.interfaces.SWInheritsPrivileges;
import is.rebbi.wo.util.SWSettings;
import is.rebbi.wo.util.USEOUtilities;
import is.rebbi.wo.util.USHierarchyUtilities;
import is.rebbi.wo.util.USUtilities;

import java.util.Enumeration;

import com.webobjects.eoaccess.EOUtilities;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOEnterpriseObject;
import com.webobjects.eocontrol.EOFetchSpecification;
import com.webobjects.eocontrol.EOKeyValueQualifier;
import com.webobjects.eocontrol.EOQualifier;
import com.webobjects.eocontrol.EOSortOrdering;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableArray;

import concept.data.auto._SWAssetFolder;

/**
 * An SWAssetFolder represents a folder that can keep SWPicture objects in a hierarchical structure
 */

public class SWAssetFolder extends _SWAssetFolder implements SWFolderInterface<SWAssetFolder, SWPicture> {

	private static final NSArray<EOSortOrdering> DEFAULT_SORT_ORDERINGS = new NSArray<>( new EOSortOrdering( "name", EOSortOrdering.CompareAscending ) );
	private static final EOQualifier ROOT_FOLDER_QUALIFIER = new EOKeyValueQualifier( "parentFolderID", EOQualifier.QualifierOperatorEqual, null );
	private static final EOFetchSpecification ROOT_FOLDER_FS = new EOFetchSpecification( "SWAssetFolder", ROOT_FOLDER_QUALIFIER, DEFAULT_SORT_ORDERINGS );

	/**
	 * Returns all folder objects in the database
	 *
	 * @param ec The EditingContext to fetch the folders into
	 * @return An NSArray with all folder objects in the database
	 */
	public static NSArray<SWAssetFolder> allFolders( EOEditingContext ec ) {
		return EOUtilities.objectsForEntityNamed( ec, SWAssetFolder.ENTITY_NAME );
	}

	/**
	 * Returns all folder objects without a parent folder (essentially root folders), sorted alphabetically
	 *
	 * @param ec The EditingContext to fetch the folders into
	 * @return folder objects without a parent folder
	 */
	@Override
	public NSArray<SWAssetFolder> sortedRootFolders( EOEditingContext ec ) {
		return ec.objectsWithFetchSpecification( ROOT_FOLDER_FS );
	}

	/**
	 * @return An NSArray with subfolders, sorted alphabetically
	 */
	@Override
	public NSArray<SWAssetFolder> sortedSubFolders() {
		return EOSortOrdering.sortedArrayUsingKeyOrderArray( subFolders(), DEFAULT_SORT_ORDERINGS );
	}

	/**
	 * Returns all pictures in this folder, sorted alphabetically
	 */
	public NSArray<SWPicture> sortedPictures() {
		NSArray<SWPicture> pics = USEOUtilities.objectsForKeyWithValueSortedByKey( editingContext(), SWPicture.ENTITY_NAME, SWPicture.ASSET_FOLDER_ID_KEY, Integer.valueOf( primaryKey() ), SWPicture.NAME_KEY );

		// Special case for RKI, filters out all folders with a filename that looks like an ECWeb
		// extra version filename (number_filename.ext).  This is to reduce clutter in the image admin system.
		if( SWSettings.booleanForKey( "RKI_FilterOutExtraECWebPictures" ) == true ) {
			NSMutableArray<SWPicture> mpics = new NSMutableArray<>( pics );
			String s;
			SWPicture pic;
			boolean didChange = false;
			int i = 0;
			while( i < mpics.count() ) {
				pic = mpics.objectAtIndex( i );
				s = pic.name();
				if( s != null && s.indexOf( '_' ) > 3 ) {
					s = s.substring( 0, s.indexOf( '_' ) );
					if( USUtilities.integerFromObject( s ) != null ) {
						mpics.removeObjectAtIndex( i );
						didChange = true;
					}
					else {
						i++;
					}
				}
				else {
					i++;
				}
			}

			if( didChange ) {
				pics = mpics;
			}
		}
		return pics;
	}

	/**
	 * A convenience method to create a new folder and insert it into the specified EOEditingContext
	 *
	 * @param ec The current EOEditingContext
	 * @param aName A name for the new folder
	 * @param aFolder the parent folder. If this is null, the resulting object is a root folder
	 * @return An NSArray of SWPicture objects
	 */
	public static SWAssetFolder newFolderWithNameAndParentFolder( EOEditingContext ec, String aName, SWAssetFolder aFolder ) {
		SWAssetFolder a = new SWAssetFolder();
		ec.insertObject( a );

		a.setName( aName );
		a.setInheritsPrivileges( 1 );

		if( aFolder != null ) {
			a.addObjectToBothSidesOfRelationshipWithKey( aFolder, "parentFolder" );
		}

		return a;
	}

	/**
	 * Calculates the total size of images in this folder in Kilobytes. Does not include subfolders.
	 *
	 * @return An int representing the size in Kilobytes.
	 */
	public long size() {
		Enumeration<SWPicture> e = pictures().objectEnumerator();

		int totalSize = 0;

		while( e.hasMoreElements() ) {
			totalSize += e.nextElement().size();
		}

		return totalSize;
	}

	/**
	 * Implementation of the SWTransferable interface, allows this folder to be transferred to a new parent folder.
	 *
	 * @param newOwner The destination parent folder
	 */
	@Override
	public void transferOwnership( EOEnterpriseObject newOwner ) {
		this.removeObjectFromBothSidesOfRelationshipWithKey( parentFolder(), "parentFolder" );
		this.addObjectToBothSidesOfRelationshipWithKey( newOwner, "parentFolder" );
	}

	/**
	 * Returns all folders that this folder owes inheritance to
	 *
	 * @param includingSelf Tells if the calling folder should be included in the resulting array
	 * @returns An array containing all parent folders
	 */
	public NSArray<SWAssetFolder> everyParentFolder( boolean includingSelf ) {
		return USHierarchyUtilities.everyParentNode( this, includingSelf );
	}

	/**
	 * Checks if the calling folder is a subfolder of the given folder
	 *
	 * @param parentFolder The parent folder to check against
	 * @param includingTopLevel Tells if 'parentFolder' should be checked against as well, or only it's subfolders
	 *
	 * @returns A boolean, indicating if parentFolder is a parent of the calling folder
	 */
	public boolean isSubFolderOfFolder( SWAssetFolder parentFolder, boolean includingTopLevel ) {
		return USHierarchyUtilities.isChildOfNode( this, parentFolder, includingTopLevel );
	}

	/**
	 * Checks if the calling folder is a parent of the given folder
	 *
	 * @param subFolder The subfolder to check against
	 * @param includingSelf Tells if the calling folder should be included in the check
	 * @returns An array containing all parent folders
	 */
	public boolean isParentFolderOfFolder( SWAssetFolder subFolder, boolean includingSelf ) {
		return USHierarchyUtilities.isParentNodeOfNode( this, subFolder, includingSelf );
	}

	/**
	 * Returns the named subfolder. If a folder with that name does not esist, or null is passed as the folder name, null is returned
	 *
	 * @param folderName The name of the folder to retrieve
	 */
	@Override
	public SWAssetFolder subFolderNamed( String folderName ) {

		if( folderName == null ) {
			return null;
		}

		Enumeration e = subFolders().objectEnumerator();

		while( e.hasMoreElements() ) {
			SWAssetFolder currentFolder = ((SWAssetFolder)e.nextElement());

			if( folderName.equals( currentFolder.name() ) ) {
				return currentFolder;
			}
		}

		return null;
	}

	/**
	 * Implementation of SWHierarchy, returns the parentFolder
	 */
	@Override
	public SWAssetFolder parent() {
		return parentFolder();
	}

	/**
	 * Implementation of SWHierarchy, returns the child folders
	 */
	@Override
	public NSArray<SWAssetFolder> children() {
		return subFolders();
	}

	/**
	 * Implementation of SWInheritsPrivileges, indicates what object this object inherits privileges from
	 */
	@Override
	public SWInheritsPrivileges inheritsPrivilegesFrom() {
		return parentFolder();
	}

	/**
	 * Returns the named item from the folder
	 */
	public SWPicture itemNamed( String aName ) {

		if( aName == null ) {
			return null;
		}

		Enumeration<SWPicture> e = pictures().objectEnumerator();

		while( e.hasMoreElements() ) {
			SWPicture nextPicture = e.nextElement();

			if( aName.equals( nextPicture.name() ) ) {
				return nextPicture;
			}
		}

		return null;
	}

	@Override
	public NSArray<SWPicture> sortedDocuments() {
		return sortedPictures();
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
		return true;
	}

	@Override
	public String title() {
		return "Myndir";
	}

	@Override
	public String key() {
		return "myndir";
	}
}