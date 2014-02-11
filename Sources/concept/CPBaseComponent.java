package concept;

import is.rebbi.core.util.StringUtilities;
import is.rebbi.wo.util.SWSettings;

import java.util.Locale;

import com.webobjects.appserver.WOContext;
import com.webobjects.eocontrol.EOEditingContext;

import concept.components.SWStandardLook;
import concept.data.SWPage;
import concept.data.SWSite;
import concept.data.SWUser;
import concept.util.CPLoc;
import concept.util.CPPageUtilities;
import er.extensions.components.ERXComponent;
import er.extensions.eof.ERXGenericRecord;
import er.extensions.foundation.ERXStringUtilities;

/**
 * Common functionality for client and admin side components.
 */

public abstract class CPBaseComponent extends ERXComponent {

	/**
	 * Currently selected object.
	 */
	private ERXGenericRecord _selectedObject;

	private EOEditingContext _ec;
	private String _uniqueID;
	private SWSite _site;

	public CPBaseComponent( WOContext context ) {
		super( context );
	}

	protected EOEditingContext ec() {
		if( _ec == null ) {
			_ec = session().defaultEditingContext();
		}

		return _ec;
	}

	/**
	 * Quick access to the system.
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
		return CPLoc.localeInRequest( context().request() );
	}

	/**
	 * Overridden to provide localization.
	 */
	@Override
	public Object valueForKeyPath( String keypath ) {

		if( keypath.startsWith( CPLoc.LS_KEYPATH ) ) {
			return CPLoc.string( keypath.substring( 4, keypath.length() ), context() );
		}

		if( keypath.startsWith( CPLoc.CS_KEYPATH ) ) {
			return CPLoc.confirmString( keypath.substring( 4, keypath.length() ), context() );
		}

		return super.valueForKeyPath( keypath );
	}

	/**
	 * @return value of the optional "id" binding for components that generate a uniqueID.
	 */
	private String id() {
		return stringValueForBinding( "id" );
	}

	/**
	 * @return A unique ID that can be used for creating unique IDs for elements on a page.
	 */
	public String uniqueID() {

		if( _uniqueID == null ) {
			if( StringUtilities.hasValue( id() ) ) {
				_uniqueID = id();
			}
			else {
				_uniqueID = context().elementID();
			}

			_uniqueID = ERXStringUtilities.safeIdentifierName( _uniqueID, "u_" );
		}

		return _uniqueID;
	}

	public ERXGenericRecord selectedObject() {

		ERXGenericRecord newSelectedObject = (ERXGenericRecord)valueForBinding( "selectedObject" );

		if( newSelectedObject != null && !newSelectedObject.equals( _selectedObject ) ) {
			_selectedObject = newSelectedObject;
		}

		return _selectedObject;
	}

	public void setSelectedObject( ERXGenericRecord value ) {
		_selectedObject = value;
	}

	/**
	 * @return Currently active site.
	 */
	public SWSite site() {
		if( _site == null ) {
			_site = CPPageUtilities.siteFromRequest( ec(), context().request() );
		}

		return _site;
	}

	/**
	 * @return Name of WOComponent to wrap around site content.
	 */
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

		SWSite s = CPPageUtilities.siteFromRequest( ec(), context().request() );

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