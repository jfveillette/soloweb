package concept.components;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOContext;

import concept.Inspection;
import er.extensions.components.ERXStatelessComponent;
import er.extensions.eof.ERXGenericRecord;

/**
 * A link for editing objects.
 */

public class EditLink extends ERXStatelessComponent {

	public EditLink( WOContext context ) {
		super( context );
	}

	public WOActionResults selectObject() {
		return Inspection.editObjectInContext( object(), context() );
	}

	/**
	 * @return The value of the "object"-binding.
	 */
	public ERXGenericRecord object() {
		ERXGenericRecord object = (ERXGenericRecord)valueForBinding( "object" );
		return object;
	}
}