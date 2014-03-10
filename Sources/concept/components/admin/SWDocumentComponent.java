package concept.components.admin;


import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOContext;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSData;

import concept.ViewPage;
import concept.data.SWDocument;
import concept.util.Documents;

public class SWDocumentComponent extends ViewPage {

	public SWDocument currentObject;

	public NSData data;
	public String filePath;

	public SWDocumentComponent( WOContext context ) {
		super( context );
	}

	public NSArray<SWDocument> objects() {
		return Documents.relatedDocuments( selectedObject() );
	}

	public WOActionResults create() {
		Documents.createAndLink( selectedObject(), filePath, data );
		ec().saveChanges();
		return null;
	}

	public WOActionResults delete() {
		currentObject.delete();
		ec().saveChanges();
		return null;
	}

	public boolean primary() {
		return currentObject.equals( Documents.primary( selectedObject() ) );
	}

	public void setPrimary( boolean value ) {
		if( value ) {
			Documents.setPrimary( selectedObject(), currentObject );
		}
	}
}