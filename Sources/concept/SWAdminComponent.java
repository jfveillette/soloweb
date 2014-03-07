package concept;

import is.rebbi.wo.util.USHTTPUtilities;

import com.webobjects.appserver.WOContext;
import com.webobjects.appserver.WOResponse;

import concept.data.SWUser;

public abstract class SWAdminComponent extends SWBaseComponent {

	public SWAdminComponent( WOContext context ) {
		super( context );
	}

	@Override
	public void appendToResponse( WOResponse r, WOContext c ) {
		if( hasSession() && SWSessionHelper.isLoggedIn( session() ) ) {
			super.appendToResponse( r, c );
		}
		else {
			super.appendToResponse( notLoggedInResponse(), c );
		}
	}

	private WOResponse notLoggedInResponse() {
		return USHTTPUtilities.statusResponse( 403, "You must log in to view this component." );
	}

	public SWUser user() {
		return conceptUser();
	}
}