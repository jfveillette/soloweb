package concept.search;

import is.rebbi.wo.util.IndexRecord;
import is.rebbi.wo.util.Indexable;
import is.rebbi.wo.util.SWSettings;
import is.rebbi.wo.util.USEOUtilities;
import is.rebbi.wo.util.USMassiveOperation;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.webobjects.eoaccess.EOEntity;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSComparator;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSMutableSet;

import er.extensions.foundation.ERXArrayUtilities;

/**
 * Creates and maintains the index.
 */

public class Indexer {

	private static final Logger logger = LoggerFactory.getLogger( Indexer.class );

	private static Directory _indexDirectory;
	private static IndexSearcher _indexSearcher;

	private static final Analyzer getAnalyzer() {
		return new LowerCaseAnalyzer();
	}

	/*
	 * Fields in the Lucene index.
	 */
	private static final String F_UNIQUE_ID = "uniqueID";
	private static final String F_TARGET_ENTITY_NAME = "targetEntityName";
	private static final String F_TARGET_ID = "targetID";
	private static final String F_NAME = "name";
	private static final String F_TEXT = "text";
	private static final String F_HIDDEN_TEXT = "hiddenText";

	/**
	 * Perform a reset of the indexSearcher. Must be done after updating or deleting of records.
	 */
	private static void resetIndexSearcher() {
		_indexSearcher = null;
	}

	/**
	 * @return The index used to store the files.
	 * @throws IOException If the index location is invalid.
	 */
	private static synchronized Directory indexDirectory() throws IOException {
		if( _indexDirectory == null ) {
			_indexDirectory = FSDirectory.open( new File( SWSettings.indexPath() ) );
		}

		return _indexDirectory;
	}

	/**
	 * @return The index used to store the files.
	 * @throws IOException If the index location is invalid.
	 */
	private static synchronized IndexSearcher indexSearcher() throws IOException {
		if( _indexSearcher == null ) {
			IndexReader reader = DirectoryReader.open( indexDirectory() );
			_indexSearcher = new IndexSearcher( reader );
		}

		return _indexSearcher;
	}

	/**
	 * Generates the index. If an index already exists, it will be deleted and a new one created in it's stead.
	 */
	public static void createIndex( EOEditingContext ec ) {

		IndexWriterConfig config = new IndexWriterConfig( Version.LUCENE_45, getAnalyzer() );

		try (IndexWriter writer = new IndexWriter( indexDirectory(), config ); ) {
			config.setOpenMode( OpenMode.CREATE );

			for( String entityName : entitiesToIndex() ) {
				USMassiveOperation o = new USMassiveOperation();
				USMassiveOperation.Operation handler = new USMassiveOperation.Operation() {

					@Override
					public void handleObject( Object object ) {
						try {
							addRecord( writer, ((Indexable)object).indexRecord() );
						}
						catch( CorruptIndexException e ) {
							e.printStackTrace();
						}
						catch( IOException e ) {
							e.printStackTrace();
						}
					}
				};

				o.start( ec, USEOUtilities.classForEntityNamed( ec, entityName ), null, 500, handler );
/*
				EOFetchSpecification fs = new EOFetchSpecification( entityName, null, null );
				ERXFetchSpecificationBatchIterator bfs = new ERXFetchSpecificationBatchIterator( fs, ec );

				while( bfs.hasMoreElements() ) {
					NSArray<Indexable> records = bfs.nextBatch();
					Integer totalCount = bfs.batchCount() * bfs.batchSize();
					Integer currentLocation = bfs.currentBatchIndex() *  bfs.batchSize();

					logger.info( "Indexing {} of {} for {}", new Object[] { currentLocation, totalCount, entityName } );

					for( Indexable r : records ) {
						addRecord( writer, r.indexRecord() );
					}
				}
*/
			}

		}
		catch( Exception e ) {
			logger.error( "Failed to perform indexing", e );
		}

		resetIndexSearcher();
	}

	/**
	 * @return A list of all entities to index.
	 */
	public static NSArray<String> entitiesToIndex() {
		NSMutableArray<String> entityNames = new NSMutableArray<>();

		for( EOEntity entity : USEOUtilities.allEntities() ) {
			Class c = USEOUtilities.classForEntity( entity );

			if( c != null ) {
				boolean isIndexable = Indexable.class.isAssignableFrom( c );

				if( isIndexable ) {
					entityNames.addObject( entity.name() );
				}
			}
		}

		return entityNames;
	}

	public static void updateRecord( IndexRecord indexRecord ) {
		logger.info( "Updating record: " + indexRecord );
		deleteRecord( indexRecord );
		addRecord( indexRecord );
	}

	public static void deleteRecord( IndexRecord record ) {

		IndexWriterConfig config = new IndexWriterConfig( Version.LUCENE_45, getAnalyzer() );

		try( IndexWriter writer = new IndexWriter( indexDirectory(), config ); ){
			config.setOpenMode( OpenMode.CREATE_OR_APPEND );
			Term term = new Term( F_UNIQUE_ID, record.uniqueID() );
			logger.info( "Deleting record using term: " + term );
			writer.deleteDocuments( term );
		}
		catch( Exception e ) {
			logger.error( "An error occurred while deleting an index record", e );
		}

		resetIndexSearcher();
	}

	private static void addRecord( IndexRecord record ) {

		IndexWriterConfig config = new IndexWriterConfig( Version.LUCENE_45, getAnalyzer() );

		try( IndexWriter writer = new IndexWriter( indexDirectory(), config ); ) {
			config.setOpenMode( OpenMode.CREATE_OR_APPEND );
			addRecord( writer, record );
		}
		catch( Exception e ) {
			logger.error( "An error occurred while adding an index record", e );
		}

		resetIndexSearcher();
	}

	/**
	 * Adds a single record to the index.
	 */
	private static void addRecord( IndexWriter writer, IndexRecord record ) throws CorruptIndexException, IOException {
		logger.debug( "Adding new index record:" + record );

		Document doc = new Document();

		Field uniqueIDField = new Field( F_UNIQUE_ID, record.uniqueID(), Field.Store.YES, Field.Index.NOT_ANALYZED );
		Field entityNameField = new Field( F_TARGET_ENTITY_NAME, record.targetEntityName(), Field.Store.YES, Field.Index.NOT_ANALYZED );
		Field targetIDField = new Field( F_TARGET_ID, String.valueOf( record.targetID() ), Field.Store.YES, Field.Index.NOT_ANALYZED );

		Field nameField = new Field( F_NAME, record.name() == null ? "" : record.name(), Field.Store.YES, Field.Index.ANALYZED );
		nameField.setBoost( 2 );

		Field textField = new Field( F_TEXT, record.text() == null ? "" : record.text(), Field.Store.YES, Field.Index.ANALYZED );
		textField.setBoost( 1 );

		Field hiddenTextField = new Field( F_HIDDEN_TEXT, record.hiddenText() == null ? "" : record.hiddenText(), Field.Store.YES, Field.Index.ANALYZED );
		textField.setBoost( 1 );

		doc.add( uniqueIDField );
		doc.add( entityNameField );
		doc.add( targetIDField );
		doc.add( nameField );
		doc.add( textField );
		doc.add( hiddenTextField );

		writer.addDocument( doc );
	}

	/**
	 * Perform a search on the index.
	 */
	public static NSArray<IndexRecord> search( String queryString ) {

		try {
			QueryParser queryParser = new MultiFieldQueryParser( Version.LUCENE_45, new String[] { F_NAME, F_TEXT, F_HIDDEN_TEXT }, getAnalyzer() );
			queryParser.setDefaultOperator( QueryParser.Operator.AND );
			Query query = queryParser.parse( queryString );

			ScoreDoc[] hits = indexSearcher().search( query, null, 2000 ).scoreDocs;

			NSMutableArray<IndexRecord> results = new NSMutableArray<>();

			for( int i = 0; i < hits.length; ++i ) {
				Document doc = indexSearcher().doc( hits[i].doc );
				String name = doc.get( F_NAME );
				String text = doc.get( F_TEXT );
				String entityName = doc.get( F_TARGET_ENTITY_NAME );
				String targetID = doc.get( F_TARGET_ID );

				IndexRecord record = IndexRecord.create( entityName, targetID );
				record.setName( name );
				record.setText( text );
				results.addObject( record );
			}

			return results;
		}
		catch( Exception e ) {
			e.printStackTrace();
			return NSArray.emptyArray();
		}
	}

	/**
	 * Perform a search on the index.
	 */
	public static NSArray<String> autocomplete( String searchString ) {
		NSMutableSet<String> results = new NSMutableSet<>();
		searchString = searchString.toLowerCase();

		try {
			Query query = new PrefixQuery( new Term( F_NAME, searchString ) );
			ScoreDoc[] hits = indexSearcher().search( query, null, 2000 ).scoreDocs;

			for( int i = 0; i < hits.length; ++i ) {
				Document doc = indexSearcher().doc( hits[i].doc );
				String name = doc.get( F_NAME );

				if( name.toLowerCase().startsWith( searchString ) ) {
					results.addObject( name );
				}
			}
		}
		catch( Exception e ) {
			logger.error( "An error occurred during autocomplete: " + e );
		}

		NSArray<String> resultArray = null;

		try {
			resultArray = results.allObjects().sortedArrayUsingComparator( NSComparator.AscendingCaseInsensitiveStringComparator );
		}
		catch( Exception e ) {
			logger.error( "An error occurred while sorting", e );
		}

		return resultArray;
	}

	public static NSArray<IndexRecord> results( String queryString ) {
		NSMutableArray<IndexRecord> results = new NSMutableArray<>();
		ERXArrayUtilities.addObjectsFromArrayWithoutDuplicates( results, Indexer.search( queryString ) );
		return results;
	}
}