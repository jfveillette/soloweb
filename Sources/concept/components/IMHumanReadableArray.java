package concept.components;

import com.webobjects.appserver.WOContext;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSKeyValueCoding;
import com.webobjects.foundation.NSKeyValueCodingAdditions;

import concept.util.HumanReadable;
import er.extensions.components.ERXStatelessComponent;
import er.extensions.eof.ERXGenericRecord;

public class IMHumanReadableArray extends ERXStatelessComponent {

	public int currentIndex;
	public Object currentObject;

	public IMHumanReadableArray( WOContext context ) {
		super( context );
	}

	public String valueWhenEmpty() {
		return stringValueForBinding( "valueWhenEmpty" );
	}

	private String displayKey() {
		return stringValueForBinding( "displayKey" );
	}

	private boolean forceLowercase() {
		return booleanValueForBinding( "forceLowercase" );
	}

	public NSArray<?> objects() {
		return (NSArray<?>)valueForBinding( "objects" );
	}

	public Object currentString() {

		Object value = null;

		if( displayKey() != null ) {
			if( currentObject != null && !(currentObject instanceof NSKeyValueCoding.Null) ) {
				value = ((NSKeyValueCodingAdditions)currentObject).valueForKeyPath( displayKey() );
			}
		}
		else {
			value = HumanReadable.DefaultImplementation.toStringHuman( currentObject );
		}

		if( forceLowercase() && (value instanceof String) ) {
			if( value != null && currentIndex > 0 ) {
				value = ((String)value).toLowerCase();
			}
		}

		return value;
	}

	/**
	 * The separator shown between records
	 */
	public String separator() {

		if( currentIndex == objects().count() - 1 ) {
			return "";
		}

		if( currentIndex == objects().count() - 2 ) {
			return " og ";
		}

		return ", ";
	}

	public boolean isInspectable() {
		return currentObject instanceof ERXGenericRecord;
	}
}