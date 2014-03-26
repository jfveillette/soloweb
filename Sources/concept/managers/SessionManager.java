package concept.managers;

import is.rebbi.wo.util.USHTTPUtilities;

import com.webobjects.appserver.WOSession;
import com.webobjects.foundation.NSNotification;
import com.webobjects.foundation.NSNotificationCenter;
import com.webobjects.foundation.NSSelector;
import com.webobjects.foundation.NSTimestamp;

import concept.Concept;
import er.extensions.appserver.ERXRequest;
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
		NSSelector<SessionManager> sessionDidRestore = new NSSelector<>( "sessionDidRestore", new Class[] { NSNotification.class } );
		NSNotificationCenter.defaultCenter().addObserver( singleton(), sessionDidRestore, WOSession.SessionDidRestoreNotification, null );

		NSSelector<SessionManager> sessionDidCreate = new NSSelector<>( "sessionDidCreate", new Class[] { NSNotification.class } );
		NSNotificationCenter.defaultCenter().addObserver( singleton(), sessionDidCreate, WOSession.SessionDidCreateNotification, null );

		NSSelector<SessionManager> sessionDidTimeOut = new NSSelector<>( "sessionDidTimeOut", new Class[] { NSNotification.class } );
		NSNotificationCenter.defaultCenter().addObserver( singleton(), sessionDidTimeOut, WOSession.SessionDidTimeOutNotification, null );
	}

	public void sessionDidRestore( NSNotification notification ) {
		ERXSession session = (ERXSession)notification.object();

		if( session != null ) {
			session.objectStore().takeValueForKey( new NSTimestamp(), "lastTouchedDate" );
		}
	}

	public void sessionDidCreate( NSNotification notification ) {
		ERXSession session = (ERXSession)notification.object();

		if( session != null ) {
			session.objectStore().takeValueForKey( new NSTimestamp(), "lastTouchedDate" );
			session.objectStore().takeValueForKey( ((ERXRequest)session.context().request()).remoteHostAddress(), "remoteHostAddress" );
			session.objectStore().takeValueForKey( USHTTPUtilities.userAgent( (session.context().request()) ), "user-agent" );
			Concept.sw().activeUserSessions().addObject( session );
		}
	}

	public void sessionDidTimeOut( NSNotification notification ) {
		ERXSession session = (ERXSession)notification.object();

		if( session != null ) {
			Concept.sw().activeUserSessions().removeObject( this );
		}
	}
}