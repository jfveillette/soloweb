package concept;

import com.webobjects.appserver.WOContext;
import com.webobjects.eocontrol.EOEditingContext;

import er.extensions.eof.ERXGenericRecord;

/**
 * All detail pages inherit from this class.
 */

public abstract class ViewPage<E extends ERXGenericRecord> extends SWBaseComponent {

	public ViewPage( WOContext context ) {
		super( context );
	}

	@Override
	public E selectedObject() {
		return (E)super.selectedObject();
	}

	@Override
	public EOEditingContext ec() {
		return selectedObject().editingContext();
	}
}