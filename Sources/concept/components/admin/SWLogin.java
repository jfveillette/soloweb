package concept.components.admin;

import is.rebbi.wo.util.SWSettings;
import is.rebbi.wo.util.USArrayUtilities;
import is.rebbi.wo.util.USEOUtilities;

import com.webobjects.appserver.WOComponent;
import com.webobjects.appserver.WOContext;
import com.webobjects.appserver.WOCookie;
import com.webobjects.appserver.WOResponse;
import com.webobjects.foundation.NSArray;

import concept.Concept;
import concept.SWSession;
import concept.data.SWSite;
import concept.data.SWUser;
import concept.util.CPLoc;
import er.ajax.AjaxUtils;
import er.extensions.components.ERXComponent;

/**
 * The system's login window
 */

public class SWLogin extends ERXComponent {

	public String userString;
	public String passString;
	public String wrongString;
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

		SWSession session = (SWSession)session();

		if( SWSettings.adminUsername().equals( userString ) && SWSettings.adminUsername().equals( passString ) ) {
			session.setIsLoggedIn( true );
			return pageWithName( SWManageSettings.class );
		}

		SWUser user = (SWUser)USEOUtilities.objectMatchingKeyAndValue( session().defaultEditingContext(), SWUser.ENTITY_NAME, SWUser.USERNAME_KEY, userString );

		if( user == null ) {
			user = (SWUser)USEOUtilities.objectMatchingKeyAndValue( session().defaultEditingContext(), SWUser.ENTITY_NAME, SWUser.EMAIL_ADDRESS_KEY, userString );
		}

		if( passString != null && user != null && user.validatePassword( passString ) ) {
			session.setActiveUser( user );
			session.setIsLoggedIn( true );
			session.setTimeOut( 28800 ); // 8 hour timeout

			if( user.defaultSite() != null ) {
				if( user.hasPrivilegeFor( user.defaultSite(), "allowToSee" ) ) {
					;
				}
				session().takeValueForKey( user.defaultSite(), "selectedSite" );
			}
			else {
				NSArray<SWSite> a = user.sites();

				if( USArrayUtilities.hasObjects( a ) ) {
					session().takeValueForKey( a.objectAtIndex( 0 ), "selectedSite" );
				}
			}

			WOComponent nextPage;

			if( selectedSystem.equals( "SoloWeb 3" ) ) {
				nextPage = pageWithName( SWMainFrameset.class );
			}
			else {
				nextPage = pageWithName( CPStartPage.class );
			}

			nextPage.context().response().addCookie( theCookie() );
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