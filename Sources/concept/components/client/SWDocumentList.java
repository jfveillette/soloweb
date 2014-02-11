package concept.components.client;

import com.webobjects.appserver.WOContext;
import com.webobjects.foundation.NSArray;

import concept.data.SWDocument;

/**
 * The display component for a list of SWDocuments.
 */

public class SWDocumentList extends SWDocumentDetail {

	private static final String REVERSE_SORT_ORDER = "reverseSortOrder";

	public SWDocument currentDocument;

	public SWDocumentList( WOContext context ) {
		super( context );
	}

	@Override
	protected boolean useDefaultComponentCSS() {
		return true;
	}

	/**
	 * @return the list of documents to show.
	 */
	public NSArray<SWDocument> documents() {
		return null;
		/*

		if( selectedDocument() == null ) {
			return NSArray.emptyArray();
		}

		NSArray<SWDocument> documents = selectedDocument().folder().sortedDocuments();

		Object o = currentComponent().customInfo().valueForKey( REVERSE_SORT_ORDER );
		Boolean reverseSortOrder = USUtilities.booleanFromObject( o );

		if( reverseSortOrder ) {
			documents = ERXArrayUtilities.reverse( documents );
		}

		return documents;
		*/
	}
}