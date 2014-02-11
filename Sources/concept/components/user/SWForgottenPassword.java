package concept.components.user;

import is.rebbi.core.util.StringUtilities;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOContext;

import concept.CPBaseComponent;
import concept.data.SWUser;
import concept.util.SWPasswordResetRequest;

public class SWForgottenPassword extends CPBaseComponent {

	public boolean hasSubmitted;
	public String emailAddress;
	public String message;

	public SWForgottenPassword( WOContext context ) {
		super( context );
	}

	public WOActionResults submit() {

		if( !StringUtilities.hasValue( emailAddress ) ) {
			return error( "Vinsamlegast sláðu inn netfangið þitt" );
		}

		SWUser user = SWUser.fetchSWUser( ec(), SWUser.EMAIL_ADDRESS.eq( emailAddress ) );

		if( user == null ) {
			return error( "Enginn notandi fannst með þetta netfang" );
		}

		SWPasswordResetRequest.submit( user, context() );

		message = null;
		hasSubmitted = true;

		return null;
	}

	public WOActionResults error( String errorString ) {
		message = errorString;
		return null;
	}
}