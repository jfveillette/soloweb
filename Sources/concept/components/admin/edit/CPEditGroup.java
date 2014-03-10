package concept.components.admin.edit;


import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOContext;
import com.webobjects.foundation.NSArray;

import concept.ViewPage;
import concept.data.SWAccessPrivilege;
import concept.data.SWGroup;
import concept.data.SWUser;

/**
 * Manages Groups and memberships
 */

public class CPEditGroup extends ViewPage<SWGroup> {

	/**
	 * The user currently being displayed in the pop-up menu
	 */
	public SWUser currentUser;

	/**
	 * The user currently being displayed in the list of group members
	 */
	public SWUser currentGroupUser;

	/**
	 * The user currently selected in the user pop-up menu
	 */
	public SWUser selectedUser;

	/**
	 * Current index of the table displaying group members. Used for alternating bgcolor
	 */
	public int tableIndex;

	/**
	 * Access privilege currently being iterated through.
	 */
	public SWAccessPrivilege currentPrivilege;

	public CPEditGroup( WOContext context ) {
		super( context );
	}

	/**
	 * All users registered in the system
	 */
	public NSArray<SWUser> allUsers() {
		return SWUser.allUsers( ec() );
	}

	/**
	 * Adds the selected user to the selected group
	 */
	public WOActionResults addUserToGroup() {

		if( selectedUser == null ) {
			return context().page();
		}

		selectedUser.addObjectToBothSidesOfRelationshipWithKey( selectedObject(), "groups" );

		return saveChanges();
	}

	/**
	 * Removes the current groupUser from the selected group
	 */
	public WOActionResults removeUserFromGroup() {
		currentGroupUser.removeObjectFromBothSidesOfRelationshipWithKey( selectedObject(), "groups" );
		return saveChanges();
	}
}