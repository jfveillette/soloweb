package concept.components.settings;

import is.rebbi.wo.util.SWSettings;

import com.webobjects.appserver.WOContext;

import concept.data.SWGroup;

/**
 * Settings panel for the access controls.
 */

public class SWSettingsAccessControl extends SWSettingsPanel {

	public SWGroup currentGroup;

	public SWSettingsAccessControl( WOContext context ) {
		super( context );
	}



	public void setSelectedGroup( SWGroup g ) {
		SWSettings.setAllUsersGroupID( Integer.valueOf( g.primaryKey() ) );
	}
}