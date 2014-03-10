package concept.components.tagging;


import is.rebbi.wo.definitions.EntityViewDefinition;

import com.webobjects.appserver.WOContext;
import com.webobjects.foundation.NSArray;

import concept.ViewPage;
import concept.data.SWTag;
import concept.util.Tagging;
import er.extensions.eof.ERXGenericRecord;

public class SWTagDetailPage extends ViewPage<SWTag> {

	public ERXGenericRecord currentObject;

	public SWTagDetailPage( WOContext context ) {
		super( context );
	}

	public NSArray<ERXGenericRecord> objects() {
		return Tagging.objects( selectedObject() );
	}

	public EntityViewDefinition currentViewDefinition() {
		return EntityViewDefinition.get( currentObject.entityName() );
	}
}