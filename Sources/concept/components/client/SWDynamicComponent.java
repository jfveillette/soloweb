package concept.components.client;

import java.util.Enumeration;


import com.webobjects.appserver.WOContext;
import com.webobjects.foundation.NSDictionary;

import concept.SWGenericComponent;

public class SWDynamicComponent extends SWGenericComponent {

	public String componentName;

	// public NSDictionary componentAttributes;

	public SWDynamicComponent( WOContext context ) {
		super( context );
	}

	// an attempt to map a dictionary to the binding values
	// of the passed component -- this doesn't work...
	public void setComponentAttributes( NSDictionary attributes ) {

		// componentAttributes = attributes.immutableClone(); //this doesn't work either...

		Enumeration e = attributes.keyEnumerator();
		while( e.hasMoreElements() ) {
			String key = (String)e.nextElement();
			Object value = attributes.valueForKey( key );
			// this is why... here the bindings are set to THIS
			// component but not the dynamic component of the
			// WOSwitchComponent (declared by componentName)
			// -- but how to access this dynamic compo from here?
			this.setValueForBinding( value, key );

		}
	}
}