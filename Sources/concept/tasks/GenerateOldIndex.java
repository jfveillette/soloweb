package concept.tasks;

import concept.search.SWLuceneUtilities;
import er.extensions.eof.ERXEC;

public class GenerateOldIndex extends SWTask {

	@Override
	public void run() {
		SWLuceneUtilities.rebuildSearchIndex( ERXEC.newEditingContext() );
	}
}