package concept;

import com.webobjects.appserver.WOContext;

import concept.data.SWComponent;

/**
 * SWGenericComponent is the common ancestor of all page components displayed on
 * an SWPage. Subclass this to create your own custom components.
 */

public abstract class CPGenericComponent extends CPBaseComponent {

	public CPGenericComponent( WOContext context ) {
		super( context );
	}

	public SWComponent currentComponent() {
		return (SWComponent)valueForBinding( "currentComponent" );
	}

	public void setCurrentComponent( SWComponent c ) {
		setValueForBinding( c, "currentComponent" );
	}
}