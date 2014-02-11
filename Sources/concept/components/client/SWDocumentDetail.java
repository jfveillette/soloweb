package concept.components.client;

import is.rebbi.wo.util.USUtilities;

import com.webobjects.appserver.WOContext;

import concept.CPGenericComponent;
import concept.data.SWDocument;

/**
 * The display component for a single SWDocument.
 */

public class SWDocumentDetail extends CPGenericComponent {

	private static final String DOCUMENT_ID = "documentID";

	public SWDocumentDetail( WOContext c ) {
		super( c );
	}

	@Override
	public boolean synchronizesVariablesWithBindings() {
		return false;
	}

	@Override
	public boolean isStateless() {
		return true;
	}

	/**
	 * @return the selected document.
	 */
	public SWDocument selectedDocument() {
		Object o = currentComponent().customInfo().valueForKey( DOCUMENT_ID );
		Integer i = USUtilities.integerFromObject( o );
		SWDocument d = SWDocument.documentWithID( ec(), i );
		return d;
	}
}