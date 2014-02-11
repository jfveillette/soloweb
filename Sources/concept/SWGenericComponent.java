package concept;

import is.rebbi.core.util.StringUtilities;

import com.webobjects.appserver.WOContext;

import concept.data.SWComponent;
import concept.data.SWPage;
import er.extensions.components.ERXComponent;

/**
 * SWGenericComponent is the common ancestor of all page components displayed on
 * an SWPage in SoloWeb. Subclass this to create your own custom components.
 */

public abstract class SWGenericComponent extends ERXComponent {

	/**
	 * The SWComponent object currently being displayed
	 */
	private SWComponent _currentComponent;
	public boolean hasCustomTemplate = false;
	public SWPage selectedContentPage;

	private String _buturID;

	public SWGenericComponent( WOContext context ) {
		super( context );
	}

	/**
	 * The SWComponent object currently being displayed
	 */
	public SWComponent currentComponent() {
		return _currentComponent;
	}

	/**
	 * Sets the SWComponent object currently being displayed
	 */
	public void setCurrentComponent( SWComponent c ) {
		_currentComponent = c;
	}

	public String customInfoString( String key, String defaultValue ) {
		String value = currentComponent().customInfo().stringValueForKey( key );

		if( !StringUtilities.hasValue( value ) && defaultValue != null ) {
			value = defaultValue;
		}

		return value;
	}

	public Integer customInfoInteger( String key, Integer defaultValue ) {
		Integer value = currentComponent().customInfo().integerValueForKey( key );

		if( value == null && defaultValue != null ) {
			value = defaultValue;
		}

		return value;
	}

	public boolean customInfoBoolean( String key ) {
		return currentComponent().customInfo().booleanValueForKey( key );
	}

	/**
	 * The SWPage currently being displayed.
	 */
	public SWPage selectedPage() {
		if( selectedContentPage != null ) {
			return selectedContentPage;
		}
		else if( currentComponent() != null ) {
			return currentComponent().page();
		}
		else {
			if( parent() instanceof SWGenericSiteLook ) {
				SWGenericSiteLook look = ((SWGenericSiteLook)parent());
				return look.selectedPage();
			}
			else {
				return null;
			}
		}
	}

	/**
	 * Checks if textOne (usually the component heading) has any content.
	 */
	public boolean headingIsEmpty() {
		if( currentComponent() != null ) {
			return !StringUtilities.hasValue( currentComponent().textOne() );
		}
		else {
			return true;
		}
	}

	/**
	 * Overridden in subclasses to allow components to return additional data for the page title.
	 */
	public String additionalPageTitle() {
		return null;
	}

	/**
	 * return Unique identifier for this component
	 */
	public String buturID() {
		_buturID = null;

		if( _buturID == null ) {
			if( currentComponent() != null ) {
				_buturID = "butur" + currentComponent().primaryKey();
			}
		}

		return _buturID;
	}

	public SWGenericSiteLook look() {
		return (SWGenericSiteLook)parent();
	}
}