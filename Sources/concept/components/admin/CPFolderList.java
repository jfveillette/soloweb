package concept.components.admin;

import is.rebbi.wo.util.USArrayUtilities;
import is.rebbi.wo.util.USHierarchyUtilities;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOApplication;
import com.webobjects.appserver.WOContext;
import com.webobjects.foundation.NSArray;

import concept.CPAdminComponent;
import concept.Concept;
import concept.SWAccessPrivilegeUtilities;
import concept.SWSessionHelper;
import concept.data.SWFolder;

/**
 * Hierarchical list of folders.
 */

public class CPFolderList extends CPAdminComponent {

	private static final String PLUS_GIF = "plus.gif";
	private static final String MINUS_GIF = "minus.gif";
	private static final String SPACER_GIF = "spacer.gif";

	public SWFolder selectedFolder;
	public SWFolder currentFolder;
	public int currentIndex;

	public CPFolderList( WOContext context ) {
		super( context );
	}

	@Override
	public boolean synchronizesVariablesWithBindings() {
		return false;
	}

	public NSArray<SWFolder> rootFolders() {
		NSArray<SWFolder> a = SWFolder.rootFolders( ec() );
		a = SWAccessPrivilegeUtilities.filteredArrayWithUserAndPrivilege( a, conceptUser(), SWAccessPrivilegeUtilities.PRIVILEGE_ALLOW_TO_SEE );
		return a;
	}

	public boolean isDisabled() {

		if( disabledFolder() != null ) {
			if( currentFolder.equals( disabledFolder() ) ) {
				return true;
			}
		}

		if( disabledBranch() != null ) {
			if( USHierarchyUtilities.isChildOfNode( currentFolder, disabledBranch(), false ) ) {
				return true;
			}
		}

		return false;
	}

	public WOActionResults selectFolder() {
		setSelectedFolder( currentFolder );

		if( actionName() == null ) {
			return null;
		}

		return performParentAction( actionName() );
	}

	public String actionName() {
		return (String)valueForBinding( "actionName" );
	}

	public SWFolder disabledFolder() {
		return (SWFolder)valueForBinding( "disabledFolder" );
	}

	public SWFolder disabledBranch() {
		return (SWFolder)valueForBinding( "disabledBranch" );
	}

	public SWFolder selectedFolder() {
		return (SWFolder)valueForBinding( "selectedFolder" );
	}

	public void setSelectedFolder( SWFolder newSelectedFolder ) {
		selectedFolder = newSelectedFolder;
		setValueForBinding( selectedFolder, "selectedFolder" );
	}

	public NSArray<SWFolder> subFolders() {
		if( isExpanded( currentFolder ) ) {
			return SWAccessPrivilegeUtilities.filteredArrayWithUserAndPrivilege( currentFolder.sortedChildren(), conceptUser(), SWAccessPrivilegeUtilities.PRIVILEGE_ALLOW_TO_SEE );
		}

		return NSArray.emptyArray();
	}

	/**
	 * Determines if the current branch should be expanded (if subpages should be displayed)
	 */
	public boolean isExpanded( SWFolder folder ) {
		return SWSessionHelper.arrayWithKeyContainsObject( session(), SWFolder.ENTITY_NAME, folder );
	}

	/**
	 * Expands the current branch.
	 */
	public void expandBranch( SWFolder anObject ) {
		SWSessionHelper.addObjectToArrayWithKey( session(), anObject, SWFolder.ENTITY_NAME );
	}

	/**
	 * Collapses the current branch.
	 */
	public void collapseBranch( SWFolder anObject ) {
		SWSessionHelper.removeObjectFromArrayWithKey( session(), anObject, SWFolder.ENTITY_NAME );
	}

	/**
	 * Determines if the plus sign or the minus sign should be displayed in
	 * front of the current page, based on if it's branch is expanded or not.
	 * Returns the name of the picture to display.
	 */
	public String toggleString() {
		String imageName = null;

		if( hasNoSubFolders() ) {
			imageName = SPACER_GIF;
		}
		else if( isExpanded( currentFolder ) ) {
			imageName = MINUS_GIF;
		}
		else {
			imageName = PLUS_GIF;
		}

		return WOApplication.application().resourceManager().urlForResourceNamed( imageName, Concept.sw().frameworkBundleName(), null, context().request() );
	}

	/**
	 * Toggles visibility of subfolders.
	 */
	public WOActionResults toggleDisplay() {
		if( isExpanded( currentFolder ) ) {
			collapseBranch( currentFolder );
		}
		else {
			expandBranch( currentFolder );
		}

		return null;
	}

	/**
	 * True if the current folder has no subfolders.
	 */
	public boolean hasNoSubFolders() {
		return !USArrayUtilities.hasObjects( currentFolder.sortedChildren() );
	}
}