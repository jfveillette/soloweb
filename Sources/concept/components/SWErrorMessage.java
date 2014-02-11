package concept.components;

import com.webobjects.appserver.WOApplication;
import com.webobjects.appserver.WOContext;
import com.webobjects.appserver.WOResponse;

import er.extensions.components.ERXNonSynchronizingComponent;

/**
 * If you want to display an error message to the user, SWErrorMessage is the way to go. Use the static method errorWithMessage to create your error message.
 */

public class SWErrorMessage extends ERXNonSynchronizingComponent {

	public String _errorMessage;

	public SWErrorMessage( WOContext context ) {
		super( context );
	}

	/**
	 * Use this method to display error messages. The error string along with header information from the context is presented to the user.
	 */
	public static WOResponse errorWithMessageAndStatusCode( String message, WOContext context, int status ) {
		SWErrorMessage nextPage = (SWErrorMessage)WOApplication.application().pageWithName( SWErrorMessage.class.getSimpleName(), context );
		nextPage.setErrorMessage( message );
		WOResponse r = nextPage.generateResponse();
		r.setStatus( status );
		return r;
	}

	public String errorMessage() {
		return _errorMessage;
	}

	/**
	 * This method is intentionally undocumented
	 */
	private void setErrorMessage( String value ) {
		_errorMessage = value;
	}

	/**
	 * This method is intentionally undocumented
	 */
	public static WOResponse handleSessionRestorationErrorInContext( WOContext context ) {
		String appPath = "/Apps" + WOApplication.application().baseURL();
		String s = "Session has expired - the maximum period of inactivity before session termination is " + (WOApplication.application().sessionTimeOut().intValue() / 60) + " minutes. Click <A href=\"" + appPath + "\" target=\"top\">here</A> to reconnect.";
		return errorWithMessageAndStatusCode( s, context, 403 );
	}
}