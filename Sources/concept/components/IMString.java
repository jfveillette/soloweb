package concept.components;

import is.rebbi.core.util.StringUtilities;
import is.rebbi.wo.util.USUtilities;

import com.webobjects.appserver.WOContext;

import er.extensions.components.ERXStatelessComponent;

/**
 * String component with some added sugar.
 */

public class IMString extends ERXStatelessComponent {

	public IMString( WOContext context ) {
		super( context );
	}

	public Integer maxLength() {
		return USUtilities.integerFromObject( valueForBinding( "maxLength" ) );
	}

	public String valueWhenEmpty() {
		return stringValueForBinding( "valueWhenEmpty" );
	}

	public String value() {
		String value = stringValueForBinding( "value" );

		if( value == null ) {
			value = "";
		}

		if( maxLength() != null ) {
			value = StringUtilities.abbreviate( value, maxLength() );
		}

		return value.trim();
	}
}