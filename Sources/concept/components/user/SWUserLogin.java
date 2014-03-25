package concept.components.user;

import is.rebbi.wo.util.SWSettings;
import is.rebbi.wo.util.USHTTPUtilities;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOContext;
import com.webobjects.appserver.WOResponse;
import com.webobjects.eocontrol.EOEditingContext;

import concept.SWBaseComponent;
import concept.data.SWUser;

public class SWUserLogin extends SWBaseComponent {

	public String emailAddress;
	public String password;

	public SWUserLogin( WOContext context ) {
		super( context );
	}

	public WOActionResults login() {
		EOEditingContext ec = session().defaultEditingContext();
		String message = SWUser.loginInContext( ec, emailAddress, password, context() );

		if( message != null ) {
			//			AjaxModalDialog.open( context(), "modal", "Villa" );
			return error( message );
		}

		emailAddress = null;
		password = null;

		String referer = USHTTPUtilities.referer( context().request() );

		if( referer != null ) {
			WOResponse response = USHTTPUtilities.redirectTemporary( referer );
			conceptUser().softUser().assign( context().request(), response );
			return response;
		}
		else {
			conceptUser().softUser().assign( context().request(), context().response() );
			return null;
		}
	}

	public boolean allowUserRegistration() {
		return SWSettings.allowUserRegistration();
	}
}