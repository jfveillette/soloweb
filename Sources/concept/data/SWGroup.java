package concept.data;

import is.rebbi.wo.util.HumanReadable;
import is.rebbi.wo.util.SWSettings;

import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableArray;

import concept.data.auto._SWGroup;
import er.extensions.eof.ERXEOControlUtilities;

/**
 * A group of users with access to the system
 */

public class SWGroup extends _SWGroup implements HumanReadable {

	/**
	 * @return All groups.
	 */
	public static NSArray<SWGroup> allGroups( EOEditingContext ec ) {
		return SWGroup.fetchSWGroups( ec, null, NAME.ascInsensitives() );
	}

	/**
	 * @return The group that contains all users.
	 */
	public static SWGroup allUsersGroup( EOEditingContext ec ) {
		Integer allUsersGroupID = SWSettings.allUsersGroupID();

		if( allUsersGroupID == null ) {
			return null;
		}

		return (SWGroup)ERXEOControlUtilities.objectWithPrimaryKeyValue( ec, SWGroup.ENTITY_NAME, allUsersGroupID, NSArray.emptyArray() );
	}

	/**
	 * @return Users not in this group.
	 */
	public NSArray<SWUser> usersNotInGroup() {
		NSMutableArray<SWUser> users = SWUser.allUsers( editingContext() ).mutableClone();
		users.removeObjectsInArray( users() );
		return users;
	}

	@Override
	public String toStringHuman() {
		return name();
	}
}