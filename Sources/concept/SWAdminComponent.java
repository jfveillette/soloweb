package concept;

import com.webobjects.appserver.WOContext;
import com.webobjects.appserver.WOResponse;

import concept.data.SWUser;
import er.extensions.components.ERXComponent;

public abstract class SWAdminComponent extends ERXComponent {

	public SWAdminComponent( WOContext context ) {
		super( context );
	}

	@Override
	public void appendToResponse( WOResponse response, WOContext context ) {
		if( ((SWSession)session()).isLoggedIn() ) {
			super.appendToResponse( response, context );
		}
		else {
			super.appendToResponse( notLoggedInResponse(), context );
		}
	}

	private WOResponse notLoggedInResponse() {
		WOResponse r = new WOResponse();
		r.setContent( "You must log in to view this component." );
		return r;
	}

	public SWUser user() {
		return ((SWSession)session()).activeUser();
	}
}