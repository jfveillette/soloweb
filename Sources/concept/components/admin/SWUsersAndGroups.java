package concept.components.admin;

import com.webobjects.appserver.WOComponent;
import com.webobjects.appserver.WOContext;
import com.webobjects.foundation.NSArray;

import concept.SWAdminComponent;
import concept.data.SWGroup;
import concept.data.SWUser;

/**
 * For management of users and groups
 */

public class SWUsersAndGroups extends SWAdminComponent {

	public SWUser selectedUser;
	public SWGroup selectedGroup;

	/**
	 * The current user being iterated over in the user pop-up menu.
	 */
	public SWUser currentUser;

	/**
	 * The current group being iterated over in the user pop-up menu.
	 */
	public SWGroup currentGroup;

	/**
	 * Determines if privileges from groups should be displayed
	 */
	public boolean displayPrivilegesFromGroups;

	public SWUsersAndGroups( WOContext context ) {
		super( context );
	}

	public NSArray<SWGroup> allGroups() {
		return SWGroup.fetchAllSWGroups( ec(), SWGroup.NAME.ascInsensitives() );
	}

	public NSArray<SWUser> allUsers() {
		return SWUser.fetchAllSWUsers( ec(), SWUser.NAME.ascInsensitives() );
	}

	public WOComponent createUser() {
		SWUser user = new SWUser();
		ec().insertObject( user );

		SWGroup g = SWGroup.allUsersGroup( session().defaultEditingContext() );

		if( g != null ) {
			user.addObjectToBothSidesOfRelationshipWithKey( g, "groups" );
		}

		session().defaultEditingContext().saveChanges();
		setSelectedUser( user );

		return null;
	}

	public WOComponent createGroup() {
		SWGroup group = new SWGroup();
		ec().insertObject( group );
		ec().saveChanges();
		setSelectedGroup( group );
		return null;
	}

	public void setSelectedUser( SWUser value ) {
		selectedUser = value;
		selectedGroup = null;
	}

	public void setSelectedGroup( SWGroup value ) {
		selectedGroup = value;
		selectedUser = null;
	}
}