package concept.components.user;

import is.rebbi.wo.util.SoftUser;
import is.rebbi.wo.util.USHTTPUtilities;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOContext;
import com.webobjects.appserver.WOResponse;

import concept.SWBaseComponent;
import concept.SWSessionHelper;
import concept.components.admin.CPStartPage;
import concept.components.admin.SWMySettings;

public class SWUserMenu extends SWBaseComponent {

	public SWUserMenu( WOContext context ) {
		super( context );
	}

	public WOActionResults logout() {
		SWSessionHelper.setUserInSession( session(), null );
		WOResponse r = USHTTPUtilities.redirectTemporary( "/" );
		SoftUser.deleteUserCookie( context().request(), r );
		return r;
	}

	public WOActionResults admin() {
		return pageWithName( CPStartPage.class );
	}

	public WOActionResults mySettings() {
		return pageWithName( SWMySettings.class );
	}
}