package concept.components;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOContext;

import concept.Inspection;
import concept.ViewPage;
import concept.components.admin.SWDocumentComponent;
import concept.components.admin.SWMetaEdit;
import concept.components.admin.SWTransactionsForObject;
import concept.components.tagging.SWTaggingPage;

public class ViewWrapper extends ViewPage {

	/**
	 * Name of the component currently being displayed.
	 */
	private String _displayComponentName;

	public ViewWrapper( WOContext context ) {
		super( context );
	}

	public String displayComponentName() {
		return _displayComponentName;
	}

	public void setDisplayComponentName( String displayComponentName ) {
		_displayComponentName = displayComponentName;
	}

	public WOActionResults viewGenericVersion() {
		return Inspection.inspectObjectInContextUsingComponent( selectedObject(), context(), ViewPageGeneric.class );
	}

	public WOActionResults viewTransactions() {
		return Inspection.editObjectInContextUsingComponent( selectedObject(), context(), SWTransactionsForObject.class );
	}

	public WOActionResults viewDocuments() {
		return Inspection.editObjectInContextUsingComponent( selectedObject(), context(), SWDocumentComponent.class );
	}

	public WOActionResults viewMeta() {
		return Inspection.editObjectInContextUsingComponent( selectedObject(), context(), SWMetaEdit.class );
	}

	public WOActionResults viewTags() {
		return Inspection.editObjectInContextUsingComponent( selectedObject(), context(), SWTaggingPage.class );
	}
}