package concept.components.client;

import is.rebbi.core.util.StringUtilities;
import is.rebbi.wo.util.SWSettings;
import is.rebbi.wo.util.USArrayUtilities;

import java.util.Enumeration;
import java.util.HashMap;


import com.webobjects.appserver.WOContext;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableArray;

import concept.SWContentSearch;
import concept.SWGenericComponent;
import concept.data.SWNewsItem;
import concept.data.SWPage;
import concept.deprecated.SWUtilities;
import concept.search.SWLuceneSearch;
import concept.search.SWSearchItem;

public class SWSearchResults2 extends SWGenericComponent {

	/**
	 * All results returned by the search
	 */
	public NSArray results;
	public Integer resultCount = null;
	public HashMap<String, Object> resultItemSettings = new HashMap<String, Object>();

	/**
	 * Current item being displayed in the found items list
	 */
	public SWSearchItem currentItem;

	public SWSearchResults2( WOContext context ) {
		super( context );
	}

	public String strSearchResults() {
		if( "en".equals( selectedPage().pageLanguageCode() ) ) {
			return "Search results";
		}
		else {
			return "Leitarniðurstöður";
		}
	}

	public String strSearchWord() {
		if( "en".equals( selectedPage().pageLanguageCode() ) ) {
			return "Search word";
		}
		else {
			return "Leitarorð";
		}
	}

	public SWPage currentPage() {
		return (SWPage)currentItem;
	}

	/**
	 * The search string
	 */
	public String searchString() {
		String sw = context().request().stringFormValueForKey( "searchString" );
		return (sw != null ? sw : "");
	}

	/**
	 * Indicates if there was only one result from the search (so we can
	 * customize the result string for singular if required).
	 */
	public boolean oneResult() {
		return USArrayUtilities.hasObjects( results ) && results.count() == 1;
	}

	public String engarSidurFundustVidLeitAdStr( String lang ) {
		if( "en".equals( lang ) ) {
			return "No content was found that contained";
		}
		else {
			return "Ekkert efni fannst við leit að";
		}
	}

	public String sidaFannstStr( String lang, int cnt ) {
		if( cnt == 1 ) {
			if( "en".equals( lang ) ) {
				return " page";
			}
			else {
				return " síða";
			}
		}
		else {
			if( "en".equals( lang ) ) {
				return " pages";
			}
			else {
				return " síður";
			}
		}
	}

	public String frettFannstStr( String lang, int cnt ) {
		if( cnt == 1 ) {
			if( "en".equals( lang ) ) {
				return " news item";
			}
			else {
				return " frétt";
			}
		}
		else {
			if( "en".equals( lang ) ) {
				return " news items";
			}
			else {
				return " fréttir";
			}
		}
	}

	public String semInniheldurStr( String lang, int cnt ) {
		if( cnt == 1 ) {
			if( "en".equals( lang ) ) {
				return " was found that contains";
			}
			else {
				return " fannst sem inniheldur";
			}
		}
		else {
			if( "en".equals( lang ) ) {
				return " were found that contain";
			}
			else {
				return " fundust sem innihalda";
			}
		}
	}

	public String ordidStr( String lang ) {
		if( searchString() != null ) {
			String ordid;
			int cnt = searchString().split( " " ).length;
			if( cnt == 1 ) {
				if( "en".equals( lang ) ) {
					ordid = " the word";
				}
				else {
					ordid = " orðið";
				}
			}
			else {
				if( "en".equals( lang ) ) {
					ordid = " the words";
				}
				else {
					ordid = " orðin";
				}
			}
			return ordid + " \"" + searchString() + "\"";
		}
		else {
			if( "en".equals( lang ) ) {
				return "No search word specified";
			}
			else {
				return "Ekkert leitarorð gefið upp";
			}
		}
	}

	public String ordinuStr( String lang ) {
		String ordid;
		int cnt = searchString().split( " " ).length;
		if( cnt == 1 ) {
			if( "en".equals( lang ) ) {
				ordid = " the word";
			}
			else {
				ordid = " orðinu";
			}
		}
		else {
			if( "en".equals( lang ) ) {
				ordid = " the words";
			}
			else {
				ordid = " orðunum";
			}
		}
		return ordid + " \"" + searchString() + "\"";
	}

	public String resultCountStr() {
		String lang = currentComponent().page().pageLanguageCode();
		String cntStr = "";
		SWSearchItem item;
		int newsCnt = 0, pageCnt = 0;

		doSearch();

		if( results != null ) {
			for( int i = 0; i < results.count(); i++ ) {
				item = (SWSearchItem)results.objectAtIndex( i );
				if( item instanceof SWPage ) {
					pageCnt++;
				}
				else if( item instanceof SWNewsItem ) {
					newsCnt++;
				}
			}
		}

		if( newsCnt + pageCnt > 0 ) {
			if( pageCnt > 0 ) {
				cntStr = pageCnt + sidaFannstStr( lang, pageCnt );
			}
			if( newsCnt > 0 ) {
				if( pageCnt > 0 ) {
					cntStr += ("en".equals( lang ) ? " and" : " og");
				}
				if( cntStr.length() > 0 ) {
					cntStr += " ";
				}
				cntStr += newsCnt + frettFannstStr( lang, newsCnt );
			}
			cntStr += semInniheldurStr( lang, newsCnt + pageCnt );
			cntStr += ordidStr( lang );
		}
		else {
			cntStr = engarSidurFundustVidLeitAdStr( lang );
			cntStr += ordinuStr( lang );
		}

		return cntStr;
	}

	private void doSearch() {
		String indexLocationOndisk = SWSettings.stringForKey( "indexLocationOndisk" );
		String branchIDString = customInfoString( "searchBranchID", "" );
		String newsFolderIDsString = customInfoString( "searchNewsFolderIDs", "" );
		String newsDisplayPageSymbol = customInfoString( "searchNewsDisplayPageSymbol", "" );

		resultItemSettings.clear();
		resultItemSettings.put( "newsDisplayPageSymbol", newsDisplayPageSymbol );
		resultItemSettings.put( "searchString", searchString() );

		Integer branchID = null;
		if( branchIDString != null ) {
			branchID = new Integer( branchIDString );
		}

		if( StringUtilities.hasValue( indexLocationOndisk ) ) {
			results = new SWLuceneSearch( session().defaultEditingContext(), searchString(), branchID, newsFolderIDsString ).search();
		}
		else {
			results = new SWContentSearch( session().defaultEditingContext(), searchString(), branchID ).search();
		}

		if( resultCount == null ) {
			NSMutableArray newRes = new NSMutableArray();
			Enumeration iter = results.objectEnumerator();
			while( iter.hasMoreElements() ) {
				currentItem = (SWSearchItem)iter.nextElement();
				if( currentItem.isValidResult() ) {
					newRes.addObject( currentItem );
				}
			}
			resultCount = new Integer( newRes.count() );
			results = newRes;
		}
	}
}