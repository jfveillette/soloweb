package concept;

import is.rebbi.wo.util.SWDictionary;

import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableArray;

import concept.data.SWUser;
import er.extensions.appserver.ERXSession;

public class SWSession extends ERXSession {

	private boolean _isLoggedIn = false;
	private String _language = null;

	public SWSession() {
		setStoresIDsInCookies( true );
		setStoresIDsInURLs( false );
		setTimeOut( 60 * 60 );
		setLanguages( new NSArray<>( "Icelandic" ) );
	}

	public void setActiveUser( SWUser value ) {
		SWSessionHelper.setUserInSession( this, value );
		SWApplication.swapplication().activeUserSessions().addObject( this );
	}

	public SWUser activeUser() {
		return SWSessionHelper.userInSession( this );
	}

	@Override
	public Object handleQueryWithUnboundKey( String myKey ) {
		return objectStore().valueForKey( myKey );
	}

	@Override
	public void handleTakeValueForUnboundKey( Object myValue, String myKey ) {
		objectStore().takeValueForKey( myValue, myKey );
	}

	public void logout() {
		terminate();
	}

	@Override
	public void terminate() {
		SWApplication.swapplication().activeUserSessions().removeObject( this );
		super.terminate();
	}

	public void addObjectToArrayWithKey( Object anObject, String key ) {
		arrayWithKey( key ).addObject( anObject );
	}

	public void removeObjectFromArrayWithKey( Object anObject, String key ) {
		arrayWithKey( key ).removeObject( anObject );
	}

	public boolean arrayWithKeyContainsObject( String key, Object anObject ) {
		return arrayWithKey( key ).containsObject( anObject );
	}

	public NSMutableArray arrayWithKey( String key ) {
		Object theArray = valueForKey( key );

		if( theArray == null ) {
			takeValueForKey( new NSMutableArray(), key );
			theArray = valueForKey( key );
		}

		return (NSMutableArray)theArray;
	}

	@Override
	public Object valueForKeyPath( String keypath ) {

		if( keypath.startsWith( "@ls" ) ) {
			return localizedStringForKey( keypath.substring( 4, keypath.length() ) );
		}

		if( keypath.startsWith( "@cs" ) ) {
			return confirmStringForKey( keypath.substring( 4, keypath.length() ) );
		}

		return super.valueForKeyPath( keypath );
	}

	@Override
	public Object valueForKey( String keypath ) {

		if( keypath.startsWith( "@ls" ) ) {
			return localizedStringForKey( keypath.substring( 4, keypath.length() ) );
		}

		if( keypath.startsWith( "@cs" ) ) {
			return confirmStringForKey( keypath.substring( 4, keypath.length() ) );
		}

		return super.valueForKey( keypath );
	}

	public String localizedStringForKey( String key ) {

		if( _language == null ) {
			_language = languages().objectAtIndex( 0 );
		}

		SWDictionary dict = SWApplication.swapplication().getLocalizedStringsForLanguage( _language );
		String str = (String)dict.valueForKey( key );
		return str;
	}

	public String confirmStringForKey( String key ) {
		StringBuffer b = new StringBuffer();

		b.append( "return confirm('" );
		b.append( localizedStringForKey( key ) );
		b.append( "');" );

		return b.toString();
	}

	public void setIsLoggedIn( boolean b ) {
		_isLoggedIn = b;
	}

	public boolean isLoggedIn() {
		return _isLoggedIn;
	}

	@Override
	public String domainForIDCookies() {
		return "/";
	}
}