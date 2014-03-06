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
import concept.components.admin.SWEditCustomInfo;
import concept.util.CPLoc;

public class SWManageSettings extends SWAdminComponent {

	public String tabPanelSelection = CPLoc.string( "settingsTabGeneral", session() );

	public NSArray<String> tabs = tabDictionary().allKeys();

	public SWManageSettings( WOContext context ) {
		super( context );
	}

	public NSDictionary<String,String> tabDictionary() {
		NSMutableDictionary<String,String> activeSettingsTabs = new NSMutableDictionary<>( Concept.sw().activeSettingsTabs() );
		activeSettingsTabs.setObjectForKey( SWGeneralSettings.class.getSimpleName(), CPLoc.string( "settingsTabGeneral", session() ) );
		activeSettingsTabs.setObjectForKey( SWDatabaseSettings.class.getSimpleName(), CPLoc.string( "settingsTabDatabase", session() ) );
		activeSettingsTabs.setObjectForKey( SWNewsSettings.class.getSimpleName(), CPLoc.string( "settingsTabNews", session() ) );
		activeSettingsTabs.setObjectForKey( SWActionSettings.class.getSimpleName(), CPLoc.string( "settingsTabAdministration", session() ) );
		activeSettingsTabs.setObjectForKey( SWAccessControlSettings.class.getSimpleName(), CPLoc.string( "settingsTabAccessControls", session() ) );
		activeSettingsTabs.setObjectForKey( SWLocalizationSettings.class.getSimpleName(), CPLoc.string( "settingsTabLocalization", session() ) );
		activeSettingsTabs.setObjectForKey( SWStatisticsSettings.class.getSimpleName(), CPLoc.string( "settingsTabStatistics", session() ) );
		activeSettingsTabs.setObjectForKey( SWEditCustomInfo.class.getSimpleName(), "Custom info ofl" );
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