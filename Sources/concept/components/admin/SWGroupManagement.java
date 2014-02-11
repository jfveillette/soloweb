package concept.components.admin;


import com.webobjects.appserver.WOComponent;
import com.webobjects.appserver.WOContext;
import com.webobjects.eocontrol.EOFetchSpecification;
import com.webobjects.eocontrol.EOSortOrdering;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableArray;

import concept.SWAdminComponent;
import concept.data.SWGroup;
import concept.data.SWUser;

/**
 * Manages Groups and memberships
 */

public class SWGroupManagement extends SWAdminComponent {

	/**
	* The user currently being dispalyed in the pop-up menu
	*/
	public SWUser currentUser;

	/**
	* The user currently being dispalyed in the list of group members
	*/
	public SWUser currentGroupUser;

	/**
	 * The group currently displayed in the groups pop-up menu
	 */
	public SWGroup currentGroup;

	/**
	 * The user currently selected in the user pop-up menu
	 */
	public SWUser selectedUser;

	/**
	 * The currently selected group
	 */
	public SWGroup selectedGroup;

	/**
	 * Current index of the table disaplying group members. Used for alternating bgcolor
	 */
	public int tableIndex;

	public SWGroupManagement( WOContext context ) {
		super( context );
	}

	/**
	 * All users registered in the SoloWeb system
	 */
	public NSArray allUsers() {
		EOSortOrdering s = new EOSortOrdering( "name", EOSortOrdering.CompareAscending );
		EOFetchSpecification fs = new EOFetchSpecification( "SWUser", null, new NSArray( s ) );
		return session().defaultEditingContext().objectsWithFetchSpecification( fs );
	}

	/**
	 * Deletes the selected group and saves changes
	 */
	public WOComponent deleteGroup() {
		session().defaultEditingContext().deleteObject( selectedGroup );
		session().defaultEditingContext().saveChanges();
		selectedGroup = null;
		return null;
	}

	/**
	 * Saves changes made in the user's session
	 */
	public WOComponent saveChanges() {
		session().defaultEditingContext().saveChanges();
		return null;
	}

	/**
	 * Adds the selected user to the selected group
	 */
	public WOComponent addUserToGroup() {
		if( selectedUser != null ) {
			selectedUser.addObjectToBothSidesOfRelationshipWithKey( selectedGroup, "groups" );
			session().defaultEditingContext().saveChanges();
		}
		return null;
	}

	/**
	 * Removes the current groupUser from the selected group
	 */
	public WOComponent removeUserFromGroup() {
		currentGroupUser.removeObjectFromBothSidesOfRelationshipWithKey( selectedGroup, "groups" );
		session().defaultEditingContext().saveChanges();
		return null;
	}

	/**
	 * Returns an array of all users, excluding users registered in this group
	 */
	public NSArray usersExcludingSelectedUsers() {
		if( selectedGroup != null ) {
			NSMutableArray tempUsers = (NSMutableArray)allUsers();
			tempUsers.removeObjectsInArray( selectedGroup.users() );
			return tempUsers;
		}
		else {
			return new NSArray();
		}
	}

	/**
	 * Returns a color for the user rows
	 */
	public String rowColor() {
		return (tableIndex % 2 == 0) ? "#dddddd" : "#bbbbbb";
	}

	/**
	 * Selects the current group user
	 */
	public WOComponent selectUser() {
		SWUsersAndGroups nextPage = (SWUsersAndGroups)pageWithName( "SWUsersAndGroups" );
		nextPage.selectedObject = currentGroupUser;
		return nextPage;
	}
}