package concept.managers;

import com.webobjects.appserver.WOSession;
import com.webobjects.foundation.NSNotification;
import com.webobjects.foundation.NSNotificationCenter;
import com.webobjects.foundation.NSSelector;
import com.webobjects.foundation.NSTimestamp;

import er.extensions.appserver.ERXSession;

/**
 * Mark last activity of the session.
 */

public class SessionManager {

	private static SessionManager _singleton;

	/**
	 * It's a singleton.
	 */
	private SessionManager() {}

	/**
	 * Creates our default transaction manager.
	 */
	public static SessionManager singleton() {
		if( _singleton == null ) {
			_singleton = new SessionManager();
		}

		return _singleton;
	}

	/**
	 * Registers the transaction manager so it starts listening and watching transactions.
	 */
	public static void register() {
		NSSelector<SessionManager> selector1 = new NSSelector<>( "sessionWasRestored", new Class[] { NSNotification.class } );
		NSNotificationCenter.defaultCenter().addObserver( singleton(), selector1, WOSession.SessionDidRestoreNotification, null );
	}

	public void sessionWasRestored( NSNotification notification ) {
		ERXSession session = (ERXSession)notification.object();

		if( session != null ) {
			session.objectStore().takeValueForKey( new NSTimestamp(), "lastTouchedDate" );
		}
	}
}