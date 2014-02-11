package concept.components.user;

import is.rebbi.wo.util.SoftUser;
import is.rebbi.wo.util.USHTTPUtilities;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOContext;
import com.webobjects.appserver.WOResponse;

import concept.CPBaseComponent;
import concept.SWSessionHelper;

public class SWUserMenu extends CPBaseComponent {

	public SWUserMenu( WOContext context ) {
		super( context );
	}

	public WOActionResults logout() {
		SWSessionHelper.setUserInSession( session(), null );
		WOResponse r = USHTTPUtilities.redirectTemporary( "/" );
		SoftUser.deleteUserCookie( context().request(), r );
		return r;
	}
}