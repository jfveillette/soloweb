package concept.components.settings;

import is.rebbi.wo.util.SWDictionary;
import is.rebbi.wo.util.SWSettings;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOContext;
import com.webobjects.foundation.NSDictionary;
import com.webobjects.foundation.NSMutableDictionary;

import concept.CPAdminComponent;
import concept.components.admin.SWApplicationInfo;
import concept.util.CPLoc;

/**
 * The settings management component.
 */

public class SWSettingsPanel extends CPAdminComponent {

	/**
	 * Tab currently being iterated over.
	 */
	public String currentTab;

	/**
	 * The currently selected tab.
	 */
	public String selectedTab = CPLoc.string( "settingsTabGeneral", context() );

	public SWSettingsPanel( WOContext context ) {
		super( context );
	}

	/**
	 * @return A dictionary of available settings tabs.
	 */
	public NSDictionary<String, String> tabs() {
		NSMutableDictionary<String, String> d = new NSMutableDictionary<>();
		d.setObjectForKey( SWSettingsGeneral.class.getSimpleName(), CPLoc.string( "settingsTabGeneral", context() ) );
		d.setObjectForKey( SWSettingsDatabase.class.getSimpleName(), CPLoc.string( "settingsTabDatabase", context() ) );
		d.setObjectForKey( SWSettingsAction.class.getSimpleName(), CPLoc.string( "settingsTabAdministration", context() ) );
		d.setObjectForKey( SWSettingsAccessControl.class.getSimpleName(), CPLoc.string( "settingsTabAccessControls", context() ) );
		d.setObjectForKey( SWSettingsStatistics.class.getSimpleName(), CPLoc.string( "settingsTabStatistics", context() ) );
		d.setObjectForKey( SWApplicationInfo.class.getSimpleName(), "Upplýsingar" );
		d.setObjectForKey( SWSettingsSites.class.getSimpleName(), "Vefir" );
		return d;
	}

	/**
	 * @return All settings for the system local settings file.
	 */
	public SWDictionary<String, Object> settings() {
		return SWSettings.localDictionary();
	}

	/**
	 * Saves changes made to settings
	 */
	public WOActionResults save() {
		settings().write();
		return context().page();
	}

	/**
	 * @return The name of the currently selected component for view in settings.
	 */
	public String selectedComponent() {
		return tabs().objectForKey( selectedTab );
	}

	/**
	 * Selects the current setting.
	 */
	public WOActionResults selectSetting() {
		selectedTab = currentTab;
		return null;
	}
}