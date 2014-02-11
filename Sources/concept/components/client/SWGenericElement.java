package concept.components.client;

import com.webobjects.appserver.WOContext;

import er.extensions.components.ERXStatelessComponent;

public class SWGenericElement extends ERXStatelessComponent {

	public SWGenericElement( WOContext context ) {
		super( context );
	}

	private String elementName() {
		return stringValueForBinding( "elementName" );
	}

	public String value() {
		return "<" + elementName() + ">";
	}
}