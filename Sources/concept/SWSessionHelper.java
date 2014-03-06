package concept;

import is.rebbi.wo.util.SoftUser;

import com.webobjects.appserver.WORequest;
import com.webobjects.appserver.WOSession;
import com.webobjects.foundation.NSMutableArray;

import concept.data.SWUser;
import concept.managers.TransactionLogger;
import er.extensions.appserver.ERXSession;

/**
 * Mostly just user management
 */

public abstract class SWSessionHelper {

	/**
	 * Key used to store user in the session's object store.
	 */
	private static final String IDENTIFIER = "conceptUser";

	/**
	 * @return The user in the given session.
	 */
	public static SWUser userInSession( WOSession s ) {

		if( s == null ) {
			return null;
		}

		SWUser user = (SWUser)((ERXSession)s).objectStore().valueForKey( IDENTIFIER );

		if( user == null ) {
			WORequest request = s.context().request();
			SoftUser softUser = SoftUser.fromRequest( request );

			user = SWUser.fetchSWUser( s.defaultEditingContext(), SWUser.UUID.eq( softUser.uuid() ) );

			if( user != null ) {
				setUserInSession( s, user );
			}
		}

		return user;
	}

	/**
	 * Set the current active user.
	 */
	public static void setUserInSession( WOSession s, SWUser user ) {
		((ERXSession)s).objectStore().takeValueForKey( user, IDENTIFIER );

		if( user != null ) {
			TransactionLogger.setUserInEditingContext( user, s.defaultEditingContext() );
		}
	}

	public static boolean isLoggedIn( WOSession s ) {
		Object o = ((ERXSession)s).objectStore().valueForKey( "isLoggedIn" );

		if( o != null ) {
			return (Boolean)o;
		}

		return false;
	}

	public static void setIsLoggedIn( WOSession s, Boolean value ) {
		((ERXSession)s).objectStore().takeValueForKey( value, "isLoggedIn" );
	}

	public static void addObjectToArrayWithKey( WOSession s, Object anObject, String key ) {
		ERXSession session = (ERXSession)s;
		arrayWithKey( session, key ).addObject( anObject );
	}

	public static void removeObjectFromArrayWithKey( WOSession s, Object anObject, String key ) {
		ERXSession session = (ERXSession)s;
		arrayWithKey( session, key ).removeObject( anObject );
	}

	public static boolean arrayWithKeyContainsObject( WOSession s, String key, Object anObject ) {
		ERXSession session = (ERXSession)s;
		return arrayWithKey( session, key ).containsObject( anObject );
	}

	public static NSMutableArray arrayWithKey( WOSession s, String key ) {
		ERXSession session = (ERXSession)s;
		Object theArray = session.objectStore().valueForKey( key );

		if( theArray == null ) {
			session.objectStore().takeValueForKey( new NSMutableArray(), key );
			theArray = session.objectStore().valueForKey( key );
		}

		return (NSMutableArray<?>)theArray;
	}
}