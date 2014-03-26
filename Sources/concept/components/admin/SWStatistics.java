package concept.components.admin;

import is.rebbi.wo.util.StatsManager;

import com.webobjects.appserver.WOContext;
import com.webobjects.foundation.NSTimestamp;

import concept.SWAdminComponent;
import concept.SWSessionHelper;
import concept.data.SWUser;
import er.extensions.appserver.ERXSession;

public class SWStatistics extends SWAdminComponent {

	public ERXSession currentSession;

	public SWStatistics( WOContext context ) {
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