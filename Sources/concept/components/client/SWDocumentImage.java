package concept.components.client;

import com.webobjects.appserver.WOContext;

import concept.data.SWDocument;
import er.extensions.components.ERXStatelessComponent;

public class SWDocumentImage extends ERXStatelessComponent {

	public SWDocumentImage( WOContext context ) {
		super( context );
	}

	public SWDocument picture() {
		return (SWDocument)valueForBinding( "picture" );
	}

	public String size() {
		return (String)valueForBinding( "size" );
	}

	public String url() {
		String url = null;

		if( size() != null ) {
			url = picture().thumbnailURL( Integer.parseInt( size() ) );
		}
		else {
			url = picture().url();
		}

		return url;
	}
}