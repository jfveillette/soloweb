package concept.components.settings;


import com.webobjects.appserver.WOApplication;
import com.webobjects.appserver.WOContext;
import com.webobjects.foundation.NSNumberFormatter;
import com.webobjects.foundation.NSTimestamp;

import concept.SWSession;

public class SWStatisticsSettings extends SWManageSettings {

	public SWSession currentSession;

	public SWStatisticsSettings( WOContext context ) {
		super( context );
	}

	public int totalMemory() {
		Number n = (Number)WOApplication.application().statisticsStore().memoryUsage().valueForKey( "Total Memory" );
		int i = n.intValue();
		return i / 1024 / 1024;
	}

	public int freeMemory() {
		Number n = (Number)WOApplication.application().statisticsStore().memoryUsage().valueForKey( "Free Memory" );
		int i = n.intValue();
		return i / 1024 / 1024;
	}

	public NSNumberFormatter europeanNumberFormatter() {
		NSNumberFormatter numberFormatter = new NSNumberFormatter( "0.00" );
		numberFormatter.setHasThousandSeparators( true );
		numberFormatter.setThousandSeparator( "." );
		numberFormatter.setDecimalSeparator( "," );
		return numberFormatter;
	}

	public NSTimestamp currentTime() {
		return new NSTimestamp();
	}
}