package concept;

import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableArray;

import concept.data.SWUser;
import concept.util.CPLoc;
import er.extensions.appserver.ERXSession;

public class SWSession extends ERXSession {

	public SWSession() {
		setStoresIDsInCookies( true );
		setStoresIDsInURLs( false );
		setLanguages( new NSArray<>( "Icelandic" ) );
	}

	@Override
	public String domainForIDCookies() {
		return "/";
	}

	public void setActiveUser( SWUser value ) {
		SWSessionHelper.setUserInSession( this, value );
		Concept.sw().activeUserSessions().addObject( this );
	}

	public SWUser activeUser() {
		return SWSessionHelper.userInSession( this );
	}

	public void logout() {
		terminate();
	}

	@Override
	public void terminate() {
		Concept.sw().activeUserSessions().removeObject( this );
		super.terminate();
	}

	public void addObjectToArrayWithKey( Object anObject, String key ) {
		SWSessionHelper.addObjectToArrayWithKey( this, anObject, key );
	}

	public void removeObjectFromArrayWithKey( Object anObject, String key ) {
		SWSessionHelper.addObjectToArrayWithKey( this, anObject, key );
	}

	public boolean arrayWithKeyContainsObject( String key, Object anObject ) {
		return SWSessionHelper.arrayWithKeyContainsObject( this, key, anObject );
	}

	public NSMutableArray arrayWithKey( String key ) {
		return SWSessionHelper.arrayWithKey( this, key );
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
		return CPLoc.string( key, this );
	}

	public String confirmStringForKey( String key ) {
		StringBuffer b = new StringBuffer();

		b.append( "return confirm('" );
		b.append( localizedStringForKey( key ) );
		b.append( "');" );

		return b.toString();
	}

	public void setIsLoggedIn( boolean b ) {
		SWSessionHelper.setIsLoggedIn( this, b );
	}

	public boolean isLoggedIn() {
		return SWSessionHelper.isLoggedIn( this );
	}
}