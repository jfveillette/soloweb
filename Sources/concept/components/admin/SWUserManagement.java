package concept.components.admin;


import com.webobjects.appserver.WOComponent;
import com.webobjects.appserver.WOContext;
import com.webobjects.eocontrol.EOFetchSpecification;
import com.webobjects.eocontrol.EOSortOrdering;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableArray;

import concept.SWAdminComponent;
import concept.data.SWGroup;
import concept.data.SWSite;
import concept.data.SWUser;

/**
 * For management of users in the SoloWeb system
 */

public class SWUserManagement extends SWAdminComponent {

	/**
	 * The current user being iterated over in the pop-up menu
	 */
	public SWUser currentUser;

	/**
	* The site being iterated over in the pop-up menu
	 */
	public SWSite currentSite;

	/**
	 * The current group being iterated over in the add-group pop-up menu
	 */
	public SWGroup currentGroup;

	/**
	 * The selected user
	 */
	public SWUser selectedUser;

	/**
	 * The selected group in the add-group pop-up menu
	 */
	public SWGroup selectedGroup;

	/**
	 * The current group being iterated over in the list of groups the selected user is a member of.
	 */
	public SWGroup currentUserGroup;

	/**
	 * Index of the current row being drawn in the member groups list.
	 */
	public int tableIndex;

	public SWUserManagement( WOContext context ) {
		super( context );
	}

	/**
	 * A list of all groups in the soloweb system, sorted by name
	 */
	public NSArray<SWGroup> allGroups() {
		EOSortOrdering s = new EOSortOrdering( "name", EOSortOrdering.CompareAscending );
		EOFetchSpecification fs = new EOFetchSpecification( "SWGroup", null, new NSArray( s ) );
		return session().defaultEditingContext().objectsWithFetchSpecification( fs );
	}

	/**
	 * Deletes the selected user.
	 */
	public WOComponent deleteUser() {
		session().defaultEditingContext().deleteObject( selectedUser );
		session().defaultEditingContext().saveChanges();
		selectedUser = null;
		return null;
	}

	/**
	 * Saves changes in the current session
	 */
	@Override
	public WOComponent saveChanges() {
		session().defaultEditingContext().saveChanges();
		return null;
	}

	/**
	 * Makes the selected user a member of the group selected in the group pop-up menu.
	 */
	public WOComponent addGroupToUser() {
		if( selectedGroup != null ) {
			selectedGroup.addObjectToBothSidesOfRelationshipWithKey( selectedUser, "users" );
			session().defaultEditingContext().saveChanges();
		}

		return null;
	}

	/**
	 * Removes the current group from the list of this user's groups.
	 */
	public WOComponent removeGroupFromUser() {
		currentUserGroup.removeObjectFromBothSidesOfRelationshipWithKey( selectedUser, "users" );
		session().defaultEditingContext().saveChanges();
		return null;
	}

	/**
	 * Returns a list of all groups, in the system, except those the user is already a member of.
	 */
	public NSArray groupsExcludingSelectedGroups() {
		if( selectedUser != null ) {
			NSMutableArray tempGroups = (NSMutableArray)allGroups();
			tempGroups.removeObjectsInArray( selectedUser.groups() );

			return tempGroups;
		}
		else {
			return new NSArray();
		}
	}

	/**
	 * Determines the color of the current row in the member-group table, based on the tableIndex.
	 */
	public String rowColor() {
		return (tableIndex % 2 == 0) ? "#dddddd" : "#bbbbbb";
	}

	/**
	 * Selects the current group and displays it in a "SWGroupManagement page"
	 */
	public WOComponent selectGroup() {
		SWUsersAndGroups nextPage = pageWithName( SWUsersAndGroups.class );
		nextPage.setSelectedGroup( currentUserGroup );
		return nextPage;
	}

	public NSArray<SWSite> allSites() {
		return SWSite.allSites( session().defaultEditingContext() );
	}
}