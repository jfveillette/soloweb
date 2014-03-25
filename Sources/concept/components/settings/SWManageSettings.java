package concept.components.settings;

import is.rebbi.wo.util.SWDictionary;
import is.rebbi.wo.util.SWSettings;

import com.webobjects.appserver.WOComponent;
import com.webobjects.appserver.WOContext;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSDictionary;
import com.webobjects.foundation.NSMutableDictionary;

import concept.Concept;
import concept.SWAdminComponent;
import concept.util.SWLoc;

public class SWManageSettings extends SWAdminComponent {

	public String tabPanelSelection = SWLoc.string( "settingsTabGeneral", session() );

	public NSArray<String> tabs = tabDictionary().allKeys();

	public SWManageSettings( WOContext context ) {
		super( context );
	}

	public NSDictionary<String,String> tabDictionary() {
		NSMutableDictionary<String,String> activeSettingsTabs = new NSMutableDictionary<>( Concept.sw().activeSettingsTabs() );
		activeSettingsTabs.setObjectForKey( SWSettingsGeneral.class.getSimpleName(), SWLoc.string( "settingsTabGeneral", session() ) );
		activeSettingsTabs.setObjectForKey( SWSettingsDatabase.class.getSimpleName(), SWLoc.string( "settingsTabDatabase", session() ) );
		activeSettingsTabs.setObjectForKey( SWSettingsNews.class.getSimpleName(), SWLoc.string( "settingsTabNews", session() ) );
		activeSettingsTabs.setObjectForKey( SWSettingsActions.class.getSimpleName(), SWLoc.string( "settingsTabAdministration", session() ) );
		activeSettingsTabs.setObjectForKey( SWSettingsAccessControl.class.getSimpleName(), SWLoc.string( "settingsTabAccessControls", session() ) );
		activeSettingsTabs.setObjectForKey( SWSettingsLocalization.class.getSimpleName(), SWLoc.string( "settingsTabLocalization", session() ) );
		activeSettingsTabs.setObjectForKey( SWSettingsStatistics.class.getSimpleName(), SWLoc.string( "settingsTabStatistics", session() ) );
		activeSettingsTabs.setObjectForKey( SWSettingsCustomInfo.class.getSimpleName(), "Custom info ofl" );
		return activeSettingsTabs;
	}

	public boolean hasSettingsFile() {
		return true;
	}

	public SWDictionary selectedDictionary() {
		return SWSettings.localDictionary();
	}

	public WOComponent save() {
		selectedDictionary().write();
		return null;
	}

	public String selectedSettingsComponent() {
		return (String)tabDictionary().valueForKey( tabPanelSelection );
	}
}