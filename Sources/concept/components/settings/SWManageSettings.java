package concept.components.settings;

import is.rebbi.wo.util.SWDictionary;
import is.rebbi.wo.util.SWSettings;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOComponent;
import com.webobjects.appserver.WOContext;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSDictionary;
import com.webobjects.foundation.NSMutableDictionary;

import concept.CPAdminComponent;
import concept.Concept;
import concept.util.SWLoc;

public class SWManageSettings extends CPAdminComponent {

	public String selectedTab = SWLoc.string( "settingsTabGeneral", session() );
	public String currentTab;

	public SWManageSettings( WOContext context ) {
		super( context );
	}

	public NSDictionary<String, String> tabDictionary() {
		NSMutableDictionary<String, String> activeSettingsTabs = new NSMutableDictionary<>( Concept.sw().activeSettingsTabs() );
		activeSettingsTabs.setObjectForKey( SWSettingsAccessControl.class.getSimpleName(), SWLoc.string( "settingsTabAccessControls", session() ) );
		activeSettingsTabs.setObjectForKey( SWSettingsActions.class.getSimpleName(), SWLoc.string( "settingsTabAdministration", session() ) );
		activeSettingsTabs.setObjectForKey( SWSettingsCustomInfo.class.getSimpleName(), "Custom info ofl" );
		activeSettingsTabs.setObjectForKey( SWSettingsDatabase.class.getSimpleName(), SWLoc.string( "settingsTabDatabase", session() ) );
		activeSettingsTabs.setObjectForKey( SWSettingsGeneral.class.getSimpleName(), SWLoc.string( "settingsTabGeneral", session() ) );
		activeSettingsTabs.setObjectForKey( SWSettingsLocalization.class.getSimpleName(), SWLoc.string( "settingsTabLocalization", session() ) );
		activeSettingsTabs.setObjectForKey( SWSettingsNews.class.getSimpleName(), SWLoc.string( "settingsTabNews", session() ) );
		activeSettingsTabs.setObjectForKey( SWSettingsSites.class.getSimpleName(), "Vefir" );
		activeSettingsTabs.setObjectForKey( SWSettingsStatistics.class.getSimpleName(), SWLoc.string( "settingsTabStatistics", session() ) );
		return activeSettingsTabs;
	}

	public NSArray<String> tabs() {
		return tabDictionary().allKeys();
	}

	public SWDictionary selectedDictionary() {
		return SWSettings.localDictionary();
	}

	public String selectedComponentName() {
		return (String)tabDictionary().valueForKey( selectedTab );
	}

	public WOActionResults select() {
		selectedTab = currentTab;
		return null;
	}

	public WOComponent save() {
		selectedDictionary().write();
		return null;
	}
}