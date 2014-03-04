package concept.components;


import com.webobjects.appserver.WOContext;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSKeyValueCodingAdditions;

import concept.util.HumanReadable;

import er.extensions.components.ERXStatelessComponent;
import er.extensions.eof.ERXGenericRecord;

/**
 * Display any object as a human readable string in a component.
 */

public class IMValue extends ERXStatelessComponent {

	public IMValue( WOContext context ) {
		super( context );
	}

	public Object object() {
		return valueForBinding( "object" );
	}

	public String displayKey() {
		return (String)valueForBinding( "displayKey" );
	}

	public boolean isArray() {
		return object() instanceof NSArray;
	}

	public boolean isEO() {
		return object() instanceof ERXGenericRecord;
	}
	public boolean isValue() {
		return !isArray() && !isEO();
	}

	public String string() {

    	String value;

    	if( displayKey() != null ) {
    		value = (String)((NSKeyValueCodingAdditions)object()).valueForKeyPath( displayKey() );
    	}
    	else {
    		value = HumanReadable.DefaultImplementation.toStringHuman( object() );
    	}

		return value;
	}
}