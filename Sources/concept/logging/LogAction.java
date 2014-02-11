package concept.logging;

import is.rebbi.wo.util.USHTTPUtilities;
import is.rebbi.wo.util.USJson;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WORequest;
import com.webobjects.foundation.NSMutableArray;

import concept.components.admin.SWLogPage;
import er.extensions.appserver.ERXDirectAction;

public class LogAction extends ERXDirectAction {

	public static NSMutableArray<LogMessage> messages = new NSMutableArray<>();

	public LogAction( WORequest r ) {
		super( r );
	}

	@Override
	public WOActionResults defaultAction() {
		String json = request().contentString();
		LogMessage m = USJson.fromJson( json, LogMessage.class );
		addLogMessage( m );
		return USHTTPUtilities.responseWithDataAndMimeType( "request.txt", m.toString(), "text/plain" );
	}

	private void addLogMessage( LogMessage message ) {
		messages.addObject( message );

		if( messages.count() > 1000 ) {
			messages.removeObjectAtIndex( 0 );
		}
	}

	public WOActionResults showAction() {
		return pageWithName( SWLogPage.class );
	}
}