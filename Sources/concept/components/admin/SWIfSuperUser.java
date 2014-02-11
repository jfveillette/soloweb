package concept.components.admin;

import com.webobjects.appserver.WOContext;

import concept.SWSessionHelper;
import concept.data.SWUser;
import er.extensions.components.ERXStatelessComponent;

public class SWIfSuperUser extends ERXStatelessComponent {

	public SWIfSuperUser( WOContext context ) {
		super( context );
	}

	/**
	 * @return The logged in user.
	 */
	public SWUser conceptUser() {
		if( hasSession() ) {
			return SWSessionHelper.userInSession( session() );
		}

		return null;
	}
}