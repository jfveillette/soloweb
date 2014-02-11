package concept.components;

import is.rebbi.wo.interfaces.HumanReadable;

import com.webobjects.appserver.WOContext;
import com.webobjects.foundation.NSKeyValueCodingAdditions;

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