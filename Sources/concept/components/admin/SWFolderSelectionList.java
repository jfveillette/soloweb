package concept.components.admin;

import is.rebbi.wo.interfaces.SWFolderInterface;
import is.rebbi.wo.util.USArrayUtilities;
import is.rebbi.wo.util.USEOUtilities;
import is.rebbi.wo.util.USHierarchyUtilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOApplication;
import com.webobjects.appserver.WOComponent;
import com.webobjects.appserver.WOContext;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableArray;

import concept.SWAccessPrivilegeUtilities;
import concept.SWAdminComponent;
import concept.SWApplication;
import concept.SWSessionHelper;
import concept.data.SWNewsCategory;

public class SWFolderSelectionList extends SWAdminComponent {

	private static final Logger logger = LoggerFactory.getLogger( SWFolderSelectionList.class );

	private static final String PLUS_GIF = "plus.gif";
	private static final String MINUS_GIF = "minus.gif";
	private static final String SPACER_GIF = "spacer.gif";

	public SWFolderInterface selectedFolder;
	public SWFolderInterface currentFolder;
	public SWFolderInterface disabledFolder;
	public SWFolderInterface disabledBranch;
	public int currentIndex;

	public String entityName;
	public String actionName;

	public String selectedIds;

	private boolean init;

	public NSMutableArray parentIds;

	public SWFolderSelectionList( WOContext context ) {
		super( context );

		init = true;
		parentIds = new NSMutableArray();
	}

	public NSArray rootFolders() {
		NSArray a = null;

		try {
			a = ((SWFolderInterface)USEOUtilities.classForEntityNamed( session().defaultEditingContext(), entityName() ).newInstance()).sortedRootFolders( session().defaultEditingContext() );
			a = SWAccessPrivilegeUtilities.filteredArrayWithUserAndPrivilege( a, user(), "allowToSee" );
		}
		catch( Exception e ) {
			logger.debug( "Error fetching root folders", e );
		}

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

		if( actionName() == null || actionName().equals( "" ) ) {
			return null;
		}

		return performParentAction( actionName() );
	}

	@Override
	public boolean synchronizesVariablesWithBindings() {
		return true;
	}

	public String entityName() {
		return (String)valueForBinding( "entityName" );
	}

	public String actionName() {
		return (String)valueForBinding( "actionName" );
	}

	public SWFolderInterface disabledFolder() {
		return (SWFolderInterface)valueForBinding( "disabledFolder" );
	}

	public SWFolderInterface disabledBranch() {
		return (SWFolderInterface)valueForBinding( "disabledBranch" );
	}

	public SWFolderInterface selectedFolder() {
		return (SWFolderInterface)valueForBinding( "selectedFolder" );
	}

	public void setSelectedFolder( SWFolderInterface newSelectedFolder ) {
		selectedFolder = newSelectedFolder;
		setValueForBinding( selectedFolder, "selectedFolder" );
	}

	public String selectedIds() {
		if( init == true ) {
			init = false;
			return (String)(this.parent().valueForKey( "initialSelectedIds" ));
		}

		return selectedIds;
	}

	public void setSelectedIds( String newSelectedIds ) {
		selectedIds = newSelectedIds;
	}

	public NSArray subFolders() {
		if( isExpanded( currentFolder ) ) {
			return SWAccessPrivilegeUtilities.filteredArrayWithUserAndPrivilege( currentFolder.sortedSubFolders(), user(), "allowToSee" );
		}
		else {
			return NSArray.EmptyArray;
		}
	}

	/**
	 * Determines if the current branch should be expanded (if subpages should be displayed)
	 */
	public boolean isExpanded( SWFolderInterface anObject ) {
		boolean status = SWSessionHelper.arrayWithKeyContainsObject( session(), entityName(), anObject );

		if( status == false ) {
			return parentIds.containsObject( anObject.folderID() );
		}

		return true;
	}

	/**
	 * Expands the current branch.
	 */
	public void expandBranch( SWFolderInterface anObject ) {
		SWSessionHelper.addObjectToArrayWithKey( session(), anObject, entityName() );
	}

	/**
	 * Collapses the current branch.
	 */
	public void collapseBranch( SWFolderInterface anObject ) {
		SWSessionHelper.removeObjectFromArrayWithKey( session(), anObject, entityName() );
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

		return WOApplication.application().resourceManager().urlForResourceNamed( "sw32/img/" + imageName, SWApplication.swapplication().frameworkBundleName(), null, context().request() );
	}

	public WOComponent toggleDisplay() {
		selectedIds = this.context().request().stringFormValueForKey( "selectedIds" );

		NSArray selectedIdsArray = NSArray.componentsSeparatedByString( selectedIds, " " );
		Integer categoryId;
		SWNewsCategory category;

		parentIds = new NSMutableArray();

		for( int i = 0; i < selectedIdsArray.count(); i++ ) {
			try {
				categoryId = Integer.valueOf( (String)(selectedIdsArray.objectAtIndex( i )) );

				category = (SWNewsCategory)(USEOUtilities.objectWithPK( session().defaultEditingContext(), SWNewsCategory.ENTITY_NAME, categoryId ));

				while( category != null ) {
					if( category.parentFolder() != null ) {
						parentIds.addObject( Integer.valueOf( category.parentFolder().primaryKey() ) );
					}

					category = category.parentFolder();
				}
			}
			catch( Exception ex ) {}
		}

		if( isExpanded( currentFolder ) ) {
			collapseBranch( currentFolder );
		}
		else {
			expandBranch( currentFolder );
		}

		return null;
	}

	public boolean hasNoSubFolders() {
		return !USArrayUtilities.hasObjects( currentFolder.sortedSubFolders() );
	}
}