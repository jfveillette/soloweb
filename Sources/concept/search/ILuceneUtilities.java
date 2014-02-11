package concept.search;

import org.apache.lucene.index.IndexWriter;

public interface ILuceneUtilities {

	public String entityName();
	public void indexObject( IndexWriter writer, SWSearchItem object );
}