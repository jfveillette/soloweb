package concept;

import is.rebbi.wo.components.BaseComponent;
import is.rebbi.wo.util.SWSettings;

import java.util.Locale;

import com.webobjects.appserver.WOContext;

import concept.components.SWStandardLook;
import concept.data.SWPage;
import concept.data.SWSite;
import concept.data.SWUser;
import concept.util.SWLoc;

/**
 * Common functionality for client and admin side components.
 */

public abstract class SWBaseComponent extends BaseComponent {

	private SWSite _site;

	public SWBaseComponent( WOContext context ) {
		super( context );
	}

	/**
	 * @return Quick access to the system.
	 */
	public Concept sw() {
		return Concept.sw();
	}

	/**
	 * @return The logged in user.
	 */
	public SWUser conceptUser() {
		if( hasSession() ) {
			return SWSessionHelper.userInSession( session() );
		}

		return null;
	}

	/**
	 * @return The currently active Locale.
	 */
	public Locale locale() {
		return SWLoc.localeInRequest( context().request() );
	}

	/**
	 * Overridden to provide localization.
	 */
	@Override
	public Object valueForKeyPath( String keypath ) {

		if( keypath.startsWith( SWLoc.LS_KEYPATH ) ) {
			return SWLoc.string( keypath.substring( 4, keypath.length() ), context() );
		}

		if( keypath.startsWith( SWLoc.CS_KEYPATH ) ) {
			return SWLoc.confirmString( keypath.substring( 4, keypath.length() ), context() );
		}

		return super.valueForKeyPath( keypath );
	}

	/**
	 * @return Currently active site.
	 */
	public SWSite site() {
		if( _site == null ) {
			_site = SWPageUtilities.siteFromRequest( ec(), context().request() );
		}

		return _site;
	}

	/**
	 * @return Name of WOComponent to wrap around site content.
	 */
	@Override
	public String lookName() {

		String lookName = context().request().stringFormValueForKey( "look" );

		if( lookName != null ) {
			return lookName;
		}

		if( selectedObject() instanceof SWPage ) {
			lookName = ((SWPage)selectedObject()).look();

			if( lookName != null ) {
				return lookName;
			}

			SWSite s = ((SWPage)selectedObject()).siteForThisPage();

			if( s != null && s.look() != null ) {
				return s.look();
			}
		}

		SWSite s = SWPageUtilities.siteFromRequest( ec(), context().request() );

		if( s != null ) {
			lookName = s.look();
		}

		if( lookName == null ) {
			lookName = SWSettings.defaultLookName();
		}

		if( lookName == null ) {
			lookName = SWStandardLook.class.getSimpleName();
		}

		return lookName;
	}
}