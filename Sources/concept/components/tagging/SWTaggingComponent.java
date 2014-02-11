package concept.components.tagging;

import com.webobjects.appserver.WOContext;
import com.webobjects.appserver.WOResponse;
import com.webobjects.foundation.NSArray;

import concept.Concept;
import concept.data.SWTag;
import concept.util.Tagging;
import er.ajax.AjaxUtils;
import er.extensions.components.ERXNonSynchronizingComponent;
import er.extensions.eof.ERXGenericRecord;

public class SWTaggingComponent extends ERXNonSynchronizingComponent {

	public SWTag currentTag;

	public SWTaggingComponent( WOContext context ) {
		super( context );
	}

	@Override
	public void appendToResponse( WOResponse r, WOContext c ) {
		super.appendToResponse( r, c );
		AjaxUtils.addStylesheetResourceInHead( context(), r, Concept.sw().frameworkBundleName(), "chosen/chosen.css" );
		AjaxUtils.addScriptResourceInHead( context(), r, Concept.sw().frameworkBundleName(), "chosen/chosen.proto.min.js" );
	}

	public ERXGenericRecord selectedObject() {
		return (ERXGenericRecord)valueForBinding( "selectedObject" );
	}

	public NSArray<SWTag> allTags() {
		return SWTag.fetchAllSWTags( selectedObject().editingContext() );
	}

	public NSArray<SWTag> selectedTags() {
		return Tagging.tags( selectedObject() );
	}

	public void setSelectedTags( NSArray<SWTag> tags ) {
		Tagging.setTags( tags, selectedObject() );
	}
}