package concept.search;

import is.rebbi.core.util.StringUtilities;
import is.rebbi.wo.util.SWSettings;

import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import concept.data.SWNewsItem;

public class SWNewsItemLuceneUtilities implements ILuceneUtilities {

	private static final Logger logger = LoggerFactory.getLogger( SWNewsItemLuceneUtilities.class );

	@Override
	public String entityName() {
		return SWNewsItem.ENTITY_NAME;
	}

	@Override
	public void indexObject( IndexWriter writer, SWSearchItem object ) {
		if( StringUtilities.hasValue( SWSettings.stringForKey( "indexLocationOndisk" ) ) ) {

			SWNewsItem newsItem = (SWNewsItem)object;

			if( newsItem != null && newsItem.isPublished() && newsItem.isOriginalItem() && StringUtilities.hasValue( newsItem.name() ) ) {
				Document doc = new Document();
				doc.add( new Field( "type", SWNewsItem.ENTITY_NAME, Field.Store.YES, Field.Index.NO ) );
				doc.add( new Field( "ID", "swnewsitem_" + newsItem.primaryKey().toString(), Field.Store.YES, Field.Index.NO ) );
				doc.add( new Field( "name", newsItem.name(), Field.Store.YES, Field.Index.ANALYZED ) );
				doc.add( new Field( "contents", newsItemTextToIndex( newsItem ), Field.Store.YES, Field.Index.ANALYZED ) );

				IndexWriter localWriter = writer;

				try {
					if( localWriter == null ) {
						SWLuceneUtilities.deleteIDFromIndex( "swnewsitem_" + newsItem.primaryKey().toString() );
						localWriter = SWLuceneUtilities.getIndexWriter( false );
					}
					localWriter.addDocument( doc );
				}
				catch( IOException ioex ) {
					logger.error( "IOException while indexing a SWNewsItem object with id " + newsItem.primaryKey() + ": ", ioex );
				}
				finally {
					try {
						if( writer == null && localWriter != null ) {
							localWriter.close();
						}
					}
					catch( Exception ex ) {}
				}
			}
		}
	}

	private static String newsItemTextToIndex( SWNewsItem newsItem ) {
		String theText = "";

		if( StringUtilities.hasValue( newsItem.text() ) ) {
			StringBuilder b = new StringBuilder();
			b.append( newsItem.text() );
			theText = SWLuceneUtilities.parseHtmlToText( b );
		}

		return theText;
	}
}