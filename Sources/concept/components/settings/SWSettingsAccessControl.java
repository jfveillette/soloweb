package concept.components.settings;

import is.rebbi.wo.util.SWSettings;

import com.webobjects.appserver.WOContext;
import com.webobjects.foundation.NSArray;

import concept.data.SWGroup;
import er.extensions.eof.ERXEOControlUtilities;

/**
 * Settings panel for the access controls.
 */

public class SWSettingsAccessControl extends SWSettingsPanel {

	public SWGroup currentGroup;

	public SWSettingsAccessControl( WOContext context ) {
		super( context );
	}

	public NSArray<SWGroup> allGroups() {
		return SWGroup.allGroups( ec() );
	}

	public SWGroup selectedGroup() {
		if( SWSettings.allUsersGroupID() != null ) {
			return (SWGroup) ERXEOControlUtilities.objectWithPrimaryKeyValue( ec(), SWGroup.ENTITY_NAME, SWSettings.allUsersGroupID(), NSArray.emptyArray() );
		}

		return null;
	}

	public void setSelectedGroup( SWGroup g ) {
		SWSettings.setAllUsersGroupID( Integer.valueOf( g.primaryKey() ) );
	}
}