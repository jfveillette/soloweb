package concept.search;

import is.rebbi.core.util.StringUtilities;
import is.rebbi.wo.util.SWSettings;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.htmlparser.Parser;
import org.htmlparser.visitors.TextExtractingVisitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.webobjects.eoaccess.EOUtilities;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableArray;

public class SWLuceneUtilities {

	private static final Logger logger = LoggerFactory.getLogger( SWLuceneUtilities.class );

	private static NSMutableArray<ILuceneUtilities> extensions = new NSMutableArray<>();

	public static void addExtension( ILuceneUtilities ex ) {
		extensions.addObject( ex );
	}

	public static NSArray<ILuceneUtilities> extensions() {
		return extensions;
	}

	public static void rebuildSearchIndex( EOEditingContext ec ) {

		if( StringUtilities.hasValue( SWSettings.stringForKey( "indexLocationOndisk" ) ) ) {
			try (IndexWriter writer = getIndexWriter( true )) {
				logger.debug( "Full Lucene index rebuild started" );
				Enumeration<ILuceneUtilities> e = extensions.objectEnumerator();

				while( e.hasMoreElements() ) {
					rebuildSingleIndex( e.nextElement(), ec, writer );
				}

				logger.debug( "Lucene index rebuild done" );
			}
			catch( IOException e ) {
				logger.error( "an error occurred while rebuilding index", e );
			}
		}
	}

	private static void rebuildSingleIndex( ILuceneUtilities ut, EOEditingContext ec, IndexWriter writer ) {

		String entityName = NSArray.componentsSeparatedByString( ut.entityName(), "." ).lastObject();

		try {
			logger.debug( entityName + " Lucene index rebuild started" );

			NSArray<SWSearchItem> items = EOUtilities.objectsForEntityNamed( ec, entityName );

			for( SWSearchItem item : items ) {
				ut.indexObject( writer, item );
			}

			logger.debug( "Lucene index rebuild done" );
		}
		catch( Exception e ) {
			logger.error( "Exception while rebuilding Lucene indexes: ", e );
		}
	}

	/**
	 * Creates a index writer
	 */
	public static IndexWriter getIndexWriter( boolean fullRebuild ) throws IOException {
		File indexDir = new File( SWSettings.stringForKey( "indexLocationOndisk" ) );
		Directory directory = FSDirectory.open( indexDir );
		Analyzer analyzer = new StandardAnalyzer( Version.LUCENE_36 );
		IndexWriterConfig config = new IndexWriterConfig( Version.LUCENE_36, analyzer );
		return new IndexWriter( directory, config );
	}

	public static void indexObject( IndexWriter writer, SWSearchItem object ) {

		if( object != null ) {
			for( ILuceneUtilities ut : extensions ) {
				if( ut.entityName() == object.entityName() ) {
					ut.indexObject( writer, object );
				}
			}
		}
	}

	public static String parseHtmlToText( StringBuilder b ) {
		String theText = "";

		if( b != null && b.length() > 0 ) {
			try {
				Parser parser = new Parser();
				parser.setInputHTML( b.toString() );
				TextExtractingVisitor visitor = new TextExtractingVisitor();
				parser.visitAllNodesWith( visitor );
				theText = visitor.getExtractedText();
			}
			catch( Exception ex ) {
				logger.error( "Error in parseHtmlToText: ", ex );
			}
		}

		return theText;
	}

	/**
	 * Deletes a specified item from the lucene index.
	 */
	public static void deleteIDFromIndex( String theId ) {

		try (IndexWriter mod = getIndexWriter( false )) {
			mod.deleteDocuments( new Term( "ID", theId ) );
		}
		catch( Exception ex ) {
			logger.error( "Error deleting doc from lucene index with ID = " + theId + ": ", ex );
		}
	}

	public static String hiliteSearchString( String resultString, String searchString ) {
		if( StringUtilities.hasValue( resultString ) && StringUtilities.hasValue( searchString ) ) {

			String[] searchWords = searchString.split( " " );

			for( String word : searchWords ) {
				resultString = resultString.replaceAll( "(?iu)(" + word + ")", "<span class=\"searchresultword\">$1</span>" );
			}
		}

		return resultString;
	}
}