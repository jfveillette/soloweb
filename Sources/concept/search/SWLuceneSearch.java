package concept.search;

import is.rebbi.core.util.StringUtilities;
import is.rebbi.wo.util.SWSettings;
import is.rebbi.wo.util.USArrayUtilities;
import is.rebbi.wo.util.USEOUtilities;

import java.io.File;
import java.util.Enumeration;
import java.util.HashMap;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableArray;

import concept.data.SWNewsItem;
import concept.data.SWPage;

/**
 * Searching using the Lucene index.
 */

public class SWLuceneSearch {

	private static final Logger logger = LoggerFactory.getLogger( SWLuceneSearch.class );

	private EOEditingContext _editingContext;
	private String _searchString;
	private SWPage branch;
	private NSMutableArray<Integer> newsFolderIDsIncluded;
	private NSMutableArray<Integer> newsFolderIDsExcluded;

	private static File indexDir = new File( SWSettings.stringForKey( "indexLocationOndisk" ) );

	public SWLuceneSearch( EOEditingContext editingContext, String searchString, Integer branchID, String newsfolderIDs ) {
		setEditingContext( editingContext );
		setSearchString( searchString );
		branch = (SWPage)USEOUtilities.objectWithPK( editingContext(), SWPage.ENTITY_NAME, branchID );
		newsFolderIDsIncluded = new NSMutableArray<>();
		newsFolderIDsExcluded = new NSMutableArray<>();

		NSArray<String> ids = NSArray.componentsSeparatedByString( newsfolderIDs, "," );

		for( String folderIDString : ids ) {
			Integer folderId = Integer.valueOf( folderIDString );

			if( folderId.intValue() > 0 ) {
				newsFolderIDsIncluded.addObject( folderId );
			}
			else if( folderId.intValue() < 0 ) {
				newsFolderIDsExcluded.addObject( Integer.valueOf( -1 * folderId.intValue() ) );
			}
		}
	}

	public NSArray<SWSearchItem> search() {

		try {
			if( StringUtilities.hasValue( searchString() ) ) {
				Directory fsDir = FSDirectory.open( indexDir );
				IndexReader indexReader = IndexReader.open( fsDir );
				IndexSearcher indexSearcher = new IndexSearcher( indexReader );
				QueryParser parser = new QueryParser( Version.LUCENE_36, "contents", new StandardAnalyzer( Version.LUCENE_36 ) );
				Query query = parser.parse( searchString() );
				ScoreDoc[] hits = indexSearcher.search( query, null, 1000 ).scoreDocs;
				NSMutableArray<SWSearchItem> a = new NSMutableArray<>();
				HashMap<String, String> idMap = new HashMap<>();

				for( int i = 0; i < hits.length; i++ ) {
					Document doc = indexSearcher.doc( hits[i].doc );
					String docId = doc.get( "ID" );
					SWSearchItem item = parseHit( doc.get( "type" ), docId, doc.get( "contents" ) );

					if( item != null && !idMap.containsKey( docId ) ) {
						a.addObject( item );
						idMap.put( docId, "" );
					}
				}

				indexReader.close();
				fsDir.close();

				return filterArray( a );
			}
			else {
				logger.debug( "No search string specified" );
				return NSArray.emptyArray();
			}
		}
		catch( Exception e ) {
			logger.debug( "error while searching for string: " + searchString(), e );
			return NSArray.emptyArray();
		}
	}

	private SWSearchItem parseHit( String type, String idString, String contents ) {

		if( idString == null ) {
			return null;
		}

		String s = idString.substring( idString.indexOf( '_' ) + 1 );
		Integer id = new Integer( Integer.parseInt( s ) );
		Enumeration<ILuceneUtilities> e = SWLuceneUtilities.extensions().objectEnumerator();

		while( e.hasMoreElements() ) {
			ILuceneUtilities ut = e.nextElement();

			if( ut.entityName().equals( type ) ) {
				SWSearchItem searchItem = USEOUtilities.objectWithPK( editingContext(), ut.entityName(), id );

				if( searchItem != null ) {
					searchItem.setSearchItemContents( contents );
				}

				return searchItem;
			}
		}

		return null;
	}

	private NSArray filterArray( NSArray a ) {

		if( !USArrayUtilities.hasObjects( a ) ) {
			return a;
		}

		Enumeration e = a.objectEnumerator();
		NSMutableArray returnArray = new NSMutableArray();
		SWNewsItem anItem;
		SWPage nextPage;
		NSArray parents;
		int catNo;
		Integer catId;
		boolean noInclusions = (newsFolderIDsIncluded.size() == 0);
		boolean noExclusions = (newsFolderIDsExcluded.size() == 0);
		boolean dontInclude;

		while( e.hasMoreElements() ) {
			Object nextObj = e.nextElement();
			if( nextObj instanceof SWPage ) {
				nextPage = (SWPage)nextObj;
				if( branch != null ) {
					if( branch.isParentPageOfPage( nextPage, true ) ) {
						returnArray.addObject( nextPage );
					}
				}
				else {
					returnArray.addObject( nextPage );
				}
			}
			else if( nextObj instanceof SWNewsItem ) {
				if( noInclusions && noExclusions ) {
					returnArray.addObject( nextObj ); // No folders specified ==> add all items
				}
				else {
					try {
						anItem = (SWNewsItem)nextObj;
						parents = anItem.allParentCategoryIDs();
						dontInclude = false;
						for( catNo = 0; catNo < parents.size(); catNo++ ) {
							catId = (Integer)parents.objectAtIndex( catNo );
							if( !noExclusions && newsFolderIDsExcluded.containsObject( catId ) ) {
								dontInclude = true;
								break; // Item is in excluded folder, don't add it
							}
							else if( newsFolderIDsIncluded.containsObject( catId ) ) {
								returnArray.addObject( nextObj ); // Item is in included folder, add it
								dontInclude = true;
								break;
							}
						}

						// Item was neither included or excluded, if no inclusions specified then we should include it
						if( !dontInclude && noInclusions ) {
							returnArray.addObject( nextObj ); // Item is in included folder, add it
						}
					}
					catch( Exception ex ) {}
				}
			}
			else {
				returnArray.addObject( nextObj );
			}
		}

		return returnArray;
	}

	private void setEditingContext( EOEditingContext value ) {
		_editingContext = value;
	}

	private EOEditingContext editingContext() {
		return _editingContext;
	}

	private void setSearchString( String value ) {
		_searchString = value;
	}

	private String searchString() {
		return _searchString;
	}
}