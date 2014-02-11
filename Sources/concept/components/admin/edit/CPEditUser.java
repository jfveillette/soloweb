package concept.components.admin.edit;

import is.rebbi.core.util.StringUtilities;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOContext;
import com.webobjects.foundation.NSArray;

import concept.ViewPage;
import concept.data.SWAccessPrivilege;
import concept.data.SWGroup;
import concept.data.SWSite;
import concept.data.SWUser;

/**
 * Management of users.
 */

public class CPEditUser extends ViewPage<SWUser> {

	/**
	 * The site being iterated over in the pop-up menu
	 */
	public SWSite currentSite;

	/**
	 * The current group being iterated over in the add-group pop-up menu
	 */
	public SWGroup currentGroup;

	/**
	 * The selected group in the add-group pop-up menu
	 */
	public SWGroup selectedGroup;

	/**
	 * The current group being iterated over in the list of groups the selected user is a member of.
	 */
	public SWGroup currentUserGroup;

	/**
	 * Access privilege currently being iterated through.
	 */
	public SWAccessPrivilege currentPrivilege;

	public CPEditUser( WOContext context ) {
		super( context );
	}

	/**
	 * A list of all groups in the system, sorted by name.
	 */
	public NSArray<SWGroup> allGroups() {
		return SWGroup.allGroups( ec() );
	}

	/**
	 * A list of all sites in the system, sorted by name.
	 */
	public NSArray<SWSite> allSites() {
		return SWSite.allSites( ec() );
	}

	/**
	 * Makes the selected user a member of the group selected in the group pop-up menu.
	 */
	public WOActionResults addGroupToUser() {

		if( selectedGroup == null ) {
			return context().page();
		}

		selectedGroup.addObjectToBothSidesOfRelationshipWithKey( selectedObject(), "users" );

		return saveChanges();
	}

	/**
	 * Removes the current group from the list of this user's groups.
	 */
	public WOActionResults removeGroupFromUser() {
		currentUserGroup.removeObjectFromBothSidesOfRelationshipWithKey( selectedObject(), "users" );
		ec().saveChanges();
		return context().page();
	}

	public String password() {
		return null;
	}

	public void setPassword( String value ) {
		if( StringUtilities.hasValue( value ) ) {
			selectedObject().setPasswordHash( value );
		}
	}
}