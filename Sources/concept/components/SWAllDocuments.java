package concept.components;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOContext;

import concept.data.SWDocument;
import er.extensions.batching.ERXBatchingDisplayGroup;
import er.extensions.components.ERXComponent;

public class SWAllDocuments extends ERXComponent {

	public SWDocument currentDocument;
	public ERXBatchingDisplayGroup dg;

	public SWAllDocuments( WOContext context ) {
		super( context );
	}

	public WOActionResults search() {
		dg.qualifyDataSource();
		return null;
	}
}