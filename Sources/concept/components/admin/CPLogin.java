package concept.components.admin;

import is.rebbi.wo.util.SWSettings;
import is.rebbi.wo.util.USHTTPUtilities;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOContext;
import com.webobjects.appserver.WOCookie;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.foundation.NSArray;

import concept.CPAdminComponent;
import concept.components.settings.SWSettingsPanel;
import concept.data.SWUser;
import concept.util.CPLoc;

/**
 * The login window
 */

public class CPLogin extends CPAdminComponent {

	private static final int COOKIE_LIFETIME_IN_SECONDS = 2592000;
	private static final String COOKIE_USERNAME = "SYSTEM_USER_NAME";
	private static final String COOKIE_USER_ID = "SYSTEM_USER_ID";

	private String _referer;

	/**
	 * The username entered
	 */
	private String _username;

	/**
	 * The password entered
	 */
	public String _password;

	/**
	 * The error string displayed if authentication fails
	 */
	private String _message;

	/**
	 * The language currently displayed in the language pop-up menu
	 */
	public String currentLanguage;

	/**
	 * The Editing context to use.
	 */
	private EOEditingContext ec = session().defaultEditingContext();

	public CPLogin( WOContext context ) {
		super( context );
	}

	/**
	 * @return Languages the system is available in.
	 */
	public NSArray<String> languages() {
		return CPLoc.AVAILABLE_LANGUAGES;
	}

	public String referer() {
		return _referer;
	}

	public void setReferer( String value ) {
		_referer = value;
	}

	/**
	 * Executes a login attempt
	 */
	public WOActionResults login() {

		if( SWSettings.adminUsername().equals( username() ) && SWSettings.adminUsername().equals( password() ) ) {
			return pageWithName( SWSettingsPanel.class );
		}

		String message = SWUser.loginInContext( ec, username(), password(), context() );

		if( message != null ) {
			return error( message );
		}

		CPStartPage nextPage = pageWithName( CPStartPage.class );
		nextPage.context().response().addCookie( usernameCookie() );
		return nextPage;
	}

	/**
	 * Check for a cookie with the username
	 */
	public String username() {

		if( _username == null ) {
			String cookieString = context().request().cookieValueForKey( COOKIE_USER_ID );

			if( cookieString != null ) {
				_username = cookieString;
			}
		}

		return _username;
	}

	public void setUsername( String s ) {
		_username = s;
	}

	public String password() {
		return _password;
	}

	public void setPassword( String newValue ) {
		_password = newValue;
	}

	public void setMessage( String value ) {
		_message = value;
	}

	public String message() {
		return _message;
	}

	/**
	 * Creates and returns the username cookie
	 */
	private WOCookie usernameCookie() {
		return new WOCookie( COOKIE_USERNAME, username(), "/", "." + USHTTPUtilities.domain( context().request() ), COOKIE_LIFETIME_IN_SECONDS, false );
	}

	/**
	 * A convenience method to fetch and return return the session's selected language
	 */
	public String selectedLanguage() {
		return session().languages().objectAtIndex( 0 );
	}

	/**
	 * Sets the session's selected language
	 */
	public void setSelectedLanguage( String value ) {
		session().setLanguages( new NSArray<>( value, CPLoc.DEFAULT_LANGUAGE ) );
	}

	/**
	 * For returning an error message to the user.
	 */
	public WOActionResults error( String message ) {
		setMessage( message );
		return context().page();
	}

	@Override
	public boolean requiresLogin() {
		return false;
	}
}