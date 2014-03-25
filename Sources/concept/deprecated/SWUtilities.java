package concept.deprecated;

import is.rebbi.wo.util.USEOUtilities;

import com.webobjects.appserver.WOContext;
import com.webobjects.appserver.WORequest;
import com.webobjects.eocontrol.EOEditingContext;

import concept.data.SWUser;

@Deprecated
public class SWUtilities extends Object {

	public static final String USER_COOKIE_NAME = "SW_CONTENTUSER_ID";

	private SWUtilities() {}

	public static String scrambleCookieUserID( int userId ) {
		String userIdString = String.valueOf( userId );
		String userIdStringReversed = "";

		for( int i = userIdString.length() - 1; i >= 0; i-- ) {
			userIdStringReversed += userIdString.charAt( i );
		}

		return userIdStringReversed;
	}

	public static int unscrambleCookieUserID( String cookieUserId ) {
		String userIdStringReversed = String.valueOf( cookieUserId );
		String userIdString = "";

		for( int i = userIdStringReversed.length() - 1; i >= 0; i-- ) {
			userIdString += userIdStringReversed.charAt( i );
		}

		return Integer.parseInt( userIdString );
	}

	public static SWUser userFromCookieUserID( EOEditingContext ec, String cookieUserId ) {
		int userId = unscrambleCookieUserID( cookieUserId );
		return (SWUser)(USEOUtilities.objectWithPK( ec, SWUser.ENTITY_NAME, new Integer( userId ) ));
	}

	public static SWUser getLoggedInUserFromCookie( WOContext theContext ) {
		SWUser user = null;
		WORequest theRequest = theContext.request();
		String cookieValue = theRequest.cookieValueForKey( USER_COOKIE_NAME );

		if( cookieValue != null && cookieValue.length() > 0 ) {
			// Fix the cookie and get the userId
			cookieValue = cookieValue.replaceAll( "\\(\"", "" );
			cookieValue = cookieValue.replaceAll( "\"\\)", "" );

			// Get the user
			user = userFromCookieUserID( theContext.session().defaultEditingContext(), cookieValue );
		}

		return user;
	}
}