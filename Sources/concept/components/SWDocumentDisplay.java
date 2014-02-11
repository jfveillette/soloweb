package concept.components;

import com.webobjects.appserver.WOContext;
import com.webobjects.foundation.NSArray;

import concept.data.SWDocument;
import er.extensions.components.ERXStatelessComponent;

public class SWDocumentDisplay extends ERXStatelessComponent {

	public SWDocument currentDocument;

	public SWDocumentDisplay( WOContext context ) {
		super( context );
	}

	public SWDocument document() {
		return (SWDocument)valueForBinding( "document" );
	}

	public NSArray<SWDocument> documents() {
		NSArray<SWDocument> documents = (NSArray<SWDocument>)valueForBinding( "documents" );

		if( documents == null ) {
			if( document() != null ) {
				documents = new NSArray<SWDocument>( document() );
			}
		}

		return documents;
	}
}