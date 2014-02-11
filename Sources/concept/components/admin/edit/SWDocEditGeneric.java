package concept.components.admin.edit;

import com.webobjects.appserver.WOContext;

import concept.CPAdminComponent;
import concept.data.SWDocument;

/**
 * Document editing for generic binary documents.
 */

public abstract class SWDocEditGeneric extends CPAdminComponent {

	private SWDocument _document;

	public SWDocEditGeneric( WOContext context ) {
		super( context );
	}

	public void setDocument( SWDocument newDocument ) {
		_document = newDocument;
	}

	public SWDocument document() {
		return _document;
	}
}