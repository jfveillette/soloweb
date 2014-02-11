package concept.components.user;

import is.rebbi.core.util.StringUtilities;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOContext;

import concept.CPBaseComponent;
import concept.data.SWUser;
import concept.util.SWPasswordResetRequest;

public class SWPasswordResetPage extends CPBaseComponent {

	private SWUser _userToResetPasswordFor;
	public String _key;
	public boolean hasReset;
	public String message;

	public String password;

	public SWPasswordResetPage( WOContext context ) {
		super( context );
	}

	public SWUser userToResetPasswordFor() {
		return _userToResetPasswordFor;
	}

	public void setUserToResetPasswordFor( SWUser value ) {
		_userToResetPasswordFor = value;
	}

	public WOActionResults resetPassword() {

		if( !StringUtilities.hasValue( password ) ) {
			return error( "Þú verður að slá inn aðgangsorð" );
		}

		userToResetPasswordFor().setPasswordHash( password );
		ec().saveChanges();
		SWPasswordResetRequest.finish( _key );
		hasReset = true;
		userToResetPasswordFor().loginInContext( context() );
		return null;
	}

	public void setKey( String value ) {
		_key = value;
		SWUser user = SWPasswordResetRequest.fetch( session().defaultEditingContext(), _key );
		setUserToResetPasswordFor( user );
	}

	public WOActionResults error( String errorString ) {
		message = errorString;
		return null;
	}
}