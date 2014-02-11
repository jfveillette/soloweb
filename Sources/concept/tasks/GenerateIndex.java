package concept.tasks;

import concept.search.Indexer;
import er.extensions.eof.ERXEC;

public class GenerateIndex extends SWTask {

	@Override
	public void run() {
		Indexer.createIndex( ERXEC.newEditingContext() );
	}
}