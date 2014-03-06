package concept.components.admin;

import is.rebbi.wo.util.SWSettings;
import is.rebbi.wo.util.USArrayUtilities;
import is.rebbi.wo.util.USHTTPUtilities;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOComponent;
import com.webobjects.appserver.WOContext;
import com.webobjects.appserver.WOCookie;
import com.webobjects.appserver.WOResponse;
import com.webobjects.appserver.WOSession;
import com.webobjects.foundation.NSArray;

import concept.Concept;
import concept.SWSessionHelper;
import concept.components.settings.SWManageSettings;
import concept.data.SWSite;
import concept.data.SWUser;
import er.ajax.AjaxUtils;
import er.extensions.appserver.ERXSession;
import er.extensions.components.ERXComponent;

/**
 * The system's login window
 */

public class SWLogin extends ERXComponent {

	private static final int COOKIE_LIFETIME_IN_SECONDS = 2592000;
	private static final String COOKIE_USERNAME = "SW_USER_ID";

	private String _username;
	private String _password;
	public String _message;
	public String currentLanguage;

	public String currentSystem;
	public String selectedSystem;
	public NSArray<String> availableLanguages = new NSArray<>( new String[] { "Icelandic", "English" } );
	public NSArray<String> availableSystems = new NSArray<>( new String[] { "SoloWeb 3", "SoloWeb 4 (beta)" } );

	public static final String DEFAULT_LANGUAGE = "Icelandic";

	public SWLogin( WOContext context ) {
		super( context );
	}

	@Override
	public void appendToResponse( WOResponse r, WOContext c ) {
		super.appendToResponse( r, c );
		AjaxUtils.addStylesheetResourceInHead( context(), r, Concept.sw().frameworkBundleName(), "sw32/css/soloweb.css" );
	}

	/**
	 * Executes a login attempt
	 */
	public WOActionResults doLogin() {

		WOSession session = session();

		if( SWSettings.adminUsername().equals( username() ) && SWSettings.adminPassword().equals( password() ) ) {
			SWSessionHelper.setIsLoggedIn( session, true );
			return pageWithName( SWManageSettings.class );
		}

		String message = SWUser.loginInContext( session().defaultEditingContext(), username(), password(), context() );

		if( message != null ) {
			return error( message );
		}

		SWUser user = SWSessionHelper.userInSession( session );
		SWSessionHelper.setIsLoggedIn( session, true );
		session.setTimeOut( 28800 );

		if( user.defaultSite() != null ) {
			if( user.hasPrivilegeFor( user.defaultSite(), "allowToSee" ) ) {
				((ERXSession)session()).objectStore().takeValueForKey( user.defaultSite(), "selectedSite" );
			}
		}
		else {
			NSArray<SWSite> a = user.sites();

			if( USArrayUtilities.hasObjects( a ) ) {
				((ERXSession)session()).objectStore().takeValueForKey( a.objectAtIndex( 0 ), "selectedSite" );
			}
		}

		WOComponent nextPage;

		if( selectedSystem.equals( "SoloWeb 3" ) ) {
			nextPage = pageWithName( SWMainFrameset.class );
		}
		else {
			nextPage = pageWithName( CPStartPage.class );
		}

		nextPage.context().response().addCookie( usernameCookie() );
		return nextPage;
	}

	/**
	 * For returning an error message to the user.
	 */
	public WOActionResults error( String message ) {
		setMessage( message );
		return context().page();
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
		return session().languages().lastObject();
	}

	/**
	 * Sets the session's selected language
	 */
	public void setSelectedLanguage( String newSelectedLanguage ) {
		session().setLanguages( new NSArray<>( new String[] { newSelectedLanguage, DEFAULT_LANGUAGE } ) );
	}

	/**
	 * Check for a cookie with the username
	 */
	public String username() {

		if( _username == null ) {
			String cookieString = context().request().cookieValueForKey( COOKIE_USERNAME );

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

	public String frameworkBundleName() {
		return Concept.sw().frameworkBundleName();
	}
}