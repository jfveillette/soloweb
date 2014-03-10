package concept.components;

import is.rebbi.wo.urls.SWURLProvider;

import com.webobjects.appserver.WOContext;

import er.extensions.components.ERXStatelessComponent;
import er.extensions.eof.ERXGenericRecord;

/**
 * A hyperlink that generates beautiful URLs for use on the external site.
 */

public class ViewLink extends ERXStatelessComponent {

	public ViewLink( WOContext context ) {
		super( context );
	}

	/**
	 * We don't display a link if there's a null object.
	 */
	public boolean disabled() {
		return object() == null || booleanValueForBinding( "disabled" );
	}

	/**
	 * @return The URL for the link.
	 */
	public String href() {
		return SWURLProvider.urlForObjectInContext( object(), context() );
	}

	/**
	 * @return The value of the "object"-binding.
	 */
	public ERXGenericRecord object() {
		return (ERXGenericRecord)valueForBinding( "object" );
	}
}