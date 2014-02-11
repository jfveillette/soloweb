package concept.search;

import java.io.Reader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.analysis.core.WhitespaceTokenizer;
import org.apache.lucene.util.Version;

/**
 * Just lowercases a field's value
 */

public final class LowerCaseAnalyzer extends Analyzer {

	@Override
	protected TokenStreamComponents createComponents( final String fieldName, final Reader reader ) {
		final Tokenizer src = new WhitespaceTokenizer( Version.LUCENE_45, reader );
		TokenStream tok = new LowerCaseFilter( Version.LUCENE_45, src );
		return new TokenStreamComponents( src, tok );
	}
}