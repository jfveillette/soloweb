package concept.components;


import com.webobjects.appserver.WOContext;
import com.webobjects.foundation.NSKeyValueCodingAdditions;

import concept.util.HumanReadable;

import er.extensions.components.ERXStatelessComponent;
import er.extensions.eof.ERXGenericRecord;

public class IMHumanReadable extends ERXStatelessComponent {

	public IMHumanReadable( WOContext context ) {
		super( context );
	}

	public ERXGenericRecord object() {
		return (ERXGenericRecord)valueForBinding( "object" );
	}

	public String displayKey() {
		return (String)valueForBinding( "displayKey" );
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