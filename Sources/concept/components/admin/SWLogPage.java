package concept.components.admin;

import com.webobjects.appserver.WOContext;
import com.webobjects.foundation.NSArray;

import concept.logging.LogAction;
import concept.logging.LogMessage;
import er.extensions.components.ERXComponent;

public class SWLogPage extends ERXComponent {

	public LogMessage current;

	public SWLogPage( WOContext context ) {
		super( context );
	}

	public NSArray<LogMessage> objects() {
		return LogAction.messages;
	}
}