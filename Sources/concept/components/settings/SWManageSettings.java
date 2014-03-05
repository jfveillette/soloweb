package concept.components.settings;

import is.rebbi.wo.util.SWDictionary;
import is.rebbi.wo.util.SWSettings;

import com.webobjects.appserver.WOComponent;
import com.webobjects.appserver.WOContext;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSDictionary;
import com.webobjects.foundation.NSMutableDictionary;

import concept.SWAdminComponent;
import concept.SWApplication;
import concept.util.CPLoc;

/**
 * The Settings management component.
 */

public class SWManageSettings extends SWAdminComponent {

	/**
	 * The currently selected tab
	 */
	public String tabPanelSelection = CPLoc.string( "settingsTabGeneral", session() );

	/**
	 * The tabs to display in the settings component
	 */
	public NSArray tabs = tabDictionary().allKeys();

	public SWManageSettings( WOContext context ) {
		super( context );
	}

	public NSDictionary tabDictionary() {
		NSMutableDictionary activeSettingsTabs = new NSMutableDictionary( SWApplication.swapplication().activeSettingsTabs() );
		activeSettingsTabs.setObjectForKey( "SWGeneralSettings", CPLoc.string( "settingsTabGeneral", session() ) );
		activeSettingsTabs.setObjectForKey( "SWDatabaseSettings", CPLoc.string( "settingsTabDatabase", session() ) );
		activeSettingsTabs.setObjectForKey( "SWNewsSettings", CPLoc.string( "settingsTabNews", session() ) );
		activeSettingsTabs.setObjectForKey( "SWActionSettings", CPLoc.string( "settingsTabAdministration", session() ) );
		activeSettingsTabs.setObjectForKey( "SWAccessControlSettings", CPLoc.string( "settingsTabAccessControls", session() ) );
		activeSettingsTabs.setObjectForKey( "SWLocalizationSettings", CPLoc.string( "settingsTabLocalization", session() ) );
		activeSettingsTabs.setObjectForKey( "SWStatisticsSettings", CPLoc.string( "settingsTabStatistics", session() ) );
		activeSettingsTabs.setObjectForKey( "SWEditCustomInfo", "Custom info ofl" );

		return activeSettingsTabs;
	}

	public boolean hasSettingsFile() {
		return true;
	}

	/**
	 * Returns all settings in the SWSettings file, as in SWSettings.allSettings()
	 */
	public SWDictionary selectedDictionary() {
		return SWSettings.localDictionary();
	}

	/**
	 * Saves changes made to settings
	 */
	public WOComponent save() {
		selectedDictionary().write();
		return null;
	}

	/**
	 * This method just returns null, is invoked when clicking the save button in the
	 * initial "Set storage location" screen.
	 */
	public WOComponent changeSettingsStorage() {
		return null;
	}

	public String selectedSettingsComponent() {
		return (String)tabDictionary().valueForKey( tabPanelSelection );
	}
}