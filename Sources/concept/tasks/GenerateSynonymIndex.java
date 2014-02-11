package concept.tasks;

import concept.search.IcelandicInflector;

public class GenerateSynonymIndex extends SWTask {

	@Override
	public void run() {
		IcelandicInflector.sharedInstance().createIndex();
	}
}