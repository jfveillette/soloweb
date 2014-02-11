package concept.components.admin;

import is.rebbi.wo.util.SWSettings;
import is.rebbi.wo.util.USArrayUtilities;
import is.rebbi.wo.util.USEOUtilities;

import com.webobjects.appserver.WOComponent;
import com.webobjects.appserver.WOContext;
import com.webobjects.appserver.WOCookie;
import com.webobjects.foundation.NSArray;

import concept.SWSession;
import concept.data.SWSite;
import concept.data.SWUser;
import concept.util.CPLoc;
import er.extensions.components.ERXComponent;

/**
 * The system's login window
 */

public class SWLogin extends ERXComponent {

	/**
	 * The username entered
	 */
	public String userString;

	/**
	 * The password entered
	 */
	public String passString;

	/**
	 * The error string displayed if authentication fails
	 */
	public String wrongString;

	/**
	 * The language currently displayed in the language pop-up menu
	 */
	public String currentLanguage;

	/**
	 * The list of languages displayed in the language-select pop-up menu
	 */
	public NSArray<String> availableLanguages = new NSArray<>( new String[] { "Icelandic", "English" } );

	public static final String DEFAULT_LANGUAGE = "Icelandic";

	public SWLogin( WOContext context ) {
		super( context );
	}

	/**
	 * Check for a cookie with the username
	 */
	@Override
	public void awake() {

		String cookieString = context().request().cookieValueForKey( "SW_USER_ID" );

		if( cookieString != null ) {
			userString = cookieString;
		}

		String languageString = context().request().cookieValueForKey( "SW_LANGUAGE" );

		if( languageString != null ) {
			setSelectedLanguage( languageString );
		}
		else {
			languageString = DEFAULT_LANGUAGE;
		}
	}

	/**
	 * Executes a login attempt
	 */
	public WOComponent doLogin() {

		String defaultUsername = SWSettings.stringForKey( "defaultUsername", "admin" );
		String defaultPassword = SWSettings.stringForKey( "defaultPassword", "admin" );

		SWMainFrameset nextPage = pageWithName( SWMainFrameset.class );
		nextPage.context().response().addCookie( theCookie() );

		SWSession sess = (SWSession)session();

		if( defaultUsername.equals( userString ) && defaultPassword.equals( passString ) ) {
			sess.setIsLoggedIn( true );
			sess.setTimeOut( 28800 ); // 8 hour timeout
			return pageWithName( "SWManageSettings" );
		}

		SWUser tempUser = (SWUser)USEOUtilities.objectMatchingKeyAndValue( session().defaultEditingContext(), SWUser.ENTITY_NAME, SWUser.USERNAME_KEY, userString );

		if( tempUser == null ) {
			tempUser = (SWUser)USEOUtilities.objectMatchingKeyAndValue( session().defaultEditingContext(), SWUser.ENTITY_NAME, SWUser.EMAIL_ADDRESS_KEY, userString );
		}

		if( passString != null && tempUser != null && tempUser.validatePassword( passString ) ) {
			sess.setActiveUser( tempUser );
			sess.setIsLoggedIn( true );
			sess.setTimeOut( 28800 ); // 8 hour timeout

			if( tempUser.defaultSite() != null ) {
				if( tempUser.hasPrivilegeFor( tempUser.defaultSite(), "allowToSee" ) ) {
					;
				}
				session().takeValueForKey( tempUser.defaultSite(), "selectedSite" );
			}
			else {
				NSArray<SWSite> a = tempUser.sites();

				if( USArrayUtilities.hasObjects( a ) ) {
					session().takeValueForKey( a.objectAtIndex( 0 ), "selectedSite" );
				}
			}

			return nextPage;
		}

		wrongString = CPLoc.string( "wrongLoginCredentials", session() );

		return null;
	}

	/**
	 * Creates and returns the username cookie
	 */
	public WOCookie theCookie() {
		String host = context().request().headerForKey( "Host" );

		if( host.indexOf( "localhost" ) > -1 ) {
			host = "";
		}

		return new WOCookie( "SW_USER_ID", userString, "/", host, 2592000, false );
	}

	public WOCookie langaugeCookie() {
		return new WOCookie( "SW_LANGUAGE", selectedLanguage(), "/", context().request().headerForKey( "Host" ), 2592000, false );
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
}