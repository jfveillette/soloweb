package concept.search;

import is.rebbi.core.util.StringUtilities;
import is.rebbi.wo.util.SWSettings;
import is.rebbi.wo.util.USArrayUtilities;

import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.webobjects.foundation.NSArray;

import concept.data.SWComponent;
import concept.data.SWPage;

public class SWPageLuceneUtilities implements ILuceneUtilities {

	private static final Logger logger = LoggerFactory.getLogger( SWPageLuceneUtilities.class );

	@Override
	public String entityName() {
		return SWPage.ENTITY_NAME;
	}

	@Override
	public void indexObject( IndexWriter writer, SWSearchItem object ) {
		if( StringUtilities.hasValue( SWSettings.stringForKey( "indexLocationOndisk" ) ) ) {

			SWPage page = (SWPage)object;

			if( page != null && page.isPublished() && StringUtilities.hasValue( page.name() ) ) {
				Document doc = new Document();
				doc.add( new Field( "type", SWPage.ENTITY_NAME, Field.Store.YES, Field.Index.NO ) );
				doc.add( new Field( "ID", "swpage_" + page.primaryKey(), Field.Store.YES, Field.Index.NO ) );
				doc.add( new Field( "name", page.name(), Field.Store.YES, Field.Index.ANALYZED ) );
				doc.add( new Field( "contents", pageTextToIndex( page ), Field.Store.YES, Field.Index.ANALYZED ) );

				IndexWriter localWriter = writer;

				try {
					if( localWriter == null ) {
						SWLuceneUtilities.deleteIDFromIndex( "swpage_" + page.primaryKey() );
						localWriter = SWLuceneUtilities.getIndexWriter( false );
					}
					localWriter.addDocument( doc );
				}
				catch( IOException ioex ) {
					logger.error( "IOException while indexing a SWPage object with id " + page.primaryKey() + ": ", ioex );
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

	private static String pageTextToIndex( SWPage page ) {
		StringBuilder b = new StringBuilder();

		NSArray<SWComponent> a = page.sortedAndApprovedComponents();

		if( !USArrayUtilities.hasObjects( a ) ) {
			return "";
		}

		for( SWComponent comp : a ) {
			comp.addTextToIndex( b );
		}

		if( b.indexOf( "null" ) > -1 ) {
			logger.info( "" );
		}

		return SWLuceneUtilities.parseHtmlToText( b );
	}
}