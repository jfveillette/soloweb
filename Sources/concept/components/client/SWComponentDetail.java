package concept.components.client;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOContext;
import com.webobjects.foundation.NSArray;

import concept.CPGenericComponent;
import concept.data.SWDocument;
import concept.util.Documents;

/**
 * Left aligned text with a left aligned picture.
 */

public class SWComponentDetail extends CPGenericComponent {

	public SWComponentDetail( WOContext context ) {
		super( context );
	}

	public WOActionResults saveChanges() {
		ec().saveChanges();
		return null;
	}

	@Override
	public boolean synchronizesVariablesWithBindings() {
		return false;
	}

	@Override
	public boolean isStateless() {
		return true;
	}

	public NSArray<SWDocument> documents() {
		return Documents.relatedDocuments( currentComponent() );
	}
}