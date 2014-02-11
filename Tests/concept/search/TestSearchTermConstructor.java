package concept.search;

import org.junit.Before;
import org.junit.Test;

import concept.search.SearchTermConstructor;

public class TestSearchTermConstructor {

	@Before
	public void initialize() {
		System.setProperty( "IcelandicInflector.sourceFilePath", "/Users/hugi/Documents/Verkefni/ISMUS/SHsnid/SHsnid.csv" );
		System.setProperty( "IcelandicInflector.indexPath", "/Users/hugi/Documents/Verkefni/ISMUS/index_bin" );
	}

	@Test
	public void orgel() {
		SearchTermConstructor c = new SearchTermConstructor( "orgel jón" );
		System.out.println( c.searchString() );
	}
}
