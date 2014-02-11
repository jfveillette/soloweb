package concept.components;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOContext;

import concept.Inspection;

public class EditWrapper extends ViewWrapper {

	public EditWrapper( WOContext context ) {
		super( context );
	}

	public WOActionResults editGeneric() {
		return Inspection.editObjectInContextUsingComponent( selectedObject(), context(), EditPageGeneric.class );
	}
}