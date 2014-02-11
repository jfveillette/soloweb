package concept.util;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import concept.search.IcelandicInflector;

@RunWith( Parameterized.class )
public class TestIcelandicInflector {

	@Before
	public void init() {
		System.setProperty( "IcelandicInflector.sourceFilePath", "/Users/hugi/Documents/Verkefni/ISMUS/SHsnid/SHsnid.csv" );
		System.setProperty( "IcelandicInflector.indexPath", "/Users/hugi/Documents/Verkefni/ISMUS/index_bin" );
	}

	public String input;
	public String modifier;
	public String expected;

	public TestIcelandicInflector( String input, String modifier, String expected ) {
		this.input = input;
		this.modifier = modifier;
		this.expected = expected;
	}

	@Parameterized.Parameters
	public static Collection data() {
		return Arrays.asList( new Object[][] {
		{
		"hugi", "nfft", "hugar"
		}, {
		"nonni", "nfft", "nonnar"
		},
		} );
	}

	@Test
	public void testInflection() {
		IcelandicInflector shared = IcelandicInflector.sharedInstance();
		System.out.println( input + " : " + modifier + " : " + shared.word( input, "nfft" ) );
	}
}
