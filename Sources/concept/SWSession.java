package concept;

import com.webobjects.foundation.NSArray;

import concept.data.SWUser;
import concept.util.SWLoc;
import er.extensions.appserver.ERXSession;

public class SWSession extends ERXSession {

	public SWSession() {
		setStoresIDsInCookies( true );
		setStoresIDsInURLs( false );
		setLanguages( new NSArray<>( "Icelandic" ) );

		if( browser().isRobot() ) {
			setTimeOut( 600 );
		}
	}

	@Override
	public String domainForIDCookies() {
		return "/";
	}

	public void setActiveUser( SWUser value ) {
		SWSessionHelper.setUserInSession( this, value );
	}

	public SWUser activeUser() {
		return SWSessionHelper.userInSession( this );
	}

	public void logout() {
		terminate();
	}

	public void addObjectToArrayWithKey( Object anObject, String key ) {
		SWSessionHelper.addObjectToArrayWithKey( this, anObject, key );
	}

	public void removeObjectFromArrayWithKey( Object anObject, String key ) {
		SWSessionHelper.removeObjectFromArrayWithKey( this, anObject, key );
	}

	public boolean arrayWithKeyContainsObject( String key, Object anObject ) {
		return SWSessionHelper.arrayWithKeyContainsObject( this, key, anObject );
	}

	@Override
	public Object valueForKeyPath( String keypath ) {

		if( keypath.startsWith( "@ls" ) ) {
			return SWLoc.string( keypath.substring( 4, keypath.length() ), this );
		}

		if( keypath.startsWith( "@cs" ) ) {
			return confirmStringForKey( keypath.substring( 4, keypath.length() ) );
		}

		return super.valueForKeyPath( keypath );
	}

	@Override
	public Object valueForKey( String keypath ) {

		if( keypath.startsWith( "@ls" ) ) {
			return SWLoc.string( keypath.substring( 4, keypath.length() ), this );
		}

		if( keypath.startsWith( "@cs" ) ) {
			return confirmStringForKey( keypath.substring( 4, keypath.length() ) );
		}

		return super.valueForKey( keypath );
	}

	private String confirmStringForKey( String key ) {
		StringBuilder b = new StringBuilder();
		b.append( "return confirm('" );
		b.append( SWLoc.string( key, this ) );
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