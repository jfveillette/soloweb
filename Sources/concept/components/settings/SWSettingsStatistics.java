package concept.components.settings;

import is.rebbi.wo.util.StatsManager;

import com.webobjects.appserver.WOContext;
import com.webobjects.appserver.WOSession;
import com.webobjects.foundation.NSTimestamp;

import concept.SWSessionHelper;
import concept.data.SWUser;

public class SWSettingsStatistics extends SWManageSettings {

	public WOSession currentSession;

	public SWSettingsStatistics( WOContext context ) {
		super( context );
	}

	public long totalMemory() {
		Long l = Runtime.getRuntime().totalMemory();
		return l / 1024 / 1024;
	}

	public long freeMemory() {
		Long l = Runtime.getRuntime().freeMemory();
		return l / 1024 / 1024;
	}

	public NSTimestamp now() {
		return new NSTimestamp();
	}

	public SWUser currentUser() {
		return SWSessionHelper.userInSession( currentSession );
	}

	public StatsManager statsManager() {
		return StatsManager.instance();
	}
}