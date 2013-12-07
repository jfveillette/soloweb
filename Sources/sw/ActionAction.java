package sw;

import is.rebbi.wo.util.USHTTPUtilities;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WORequest;

import er.extensions.appserver.ERXDirectAction;

public class ActionAction extends ERXDirectAction {

	public ActionAction( WORequest r ) {
		super( r );
	}

	@Override
	public WOActionResults defaultAction() {
		return USHTTPUtilities.responseWithDataAndMimeType( "smu.txt", "Haha!", "text/plain" );
	}
}
