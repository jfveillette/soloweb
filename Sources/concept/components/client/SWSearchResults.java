package concept.components.client;

import is.rebbi.wo.util.USArrayUtilities;

import java.util.Enumeration;
import java.util.HashMap;


import com.webobjects.appserver.WOContext;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableArray;

import concept.SWGenericTemplate;
import concept.data.SWPage;
import concept.search.SWSearchItem;

/**
 * This is the page returned by SWDirectAction.search().
 */

public class SWSearchResults extends SWGenericTemplate {

	/**
	 * All results returned by the search
	 */
	public NSArray<? extends SWSearchItem> results;
	public Integer resultCount = null;
	public HashMap<String, Object> resultItemSettings;
	public String language;

	/**
	 * Current item being displayed in the found items list
	 */
	public SWSearchItem currentItem;

	public SWSearchResults( WOContext context ) {
		super( context );
	}

	public SWPage currentPage() {
		return (SWPage)currentItem;
	}

	/**
	 * The search string
	 */
	public String searchString() {
		return context().request().stringFormValueForKey( "searchString" );
	}

	/**
	 * Indicates if there was only one result from the search (so we can customize the result string for singular if required).
	 */
	public boolean oneResult() {
		return USArrayUtilities.hasObjects( results ) && results.count() == 1;
	}

	public boolean hasResults() {

		if( results == null ) {
			return false;
		}

		if( resultCount == null ) {
			NSMutableArray<SWSearchItem> newRes = new NSMutableArray<>();
			Enumeration<? extends SWSearchItem> iter = results.objectEnumerator();

			while( iter.hasMoreElements() ) {
				currentItem = iter.nextElement();

				if( currentItem.isValidResult() ) {
					newRes.addObject( currentItem );
				}
			}

			resultCount = new Integer( newRes.count() );
			results = newRes;
		}

		return (resultCount.intValue() > 0);
	}

	public String engarSidurFundustVidLeitAdStr() {
		if( "en".equals( language ) ) {
			return "No pages were found that contained";
		}
		else {
			return "Engar síður fundust við leit að";
		}
	}

	public String sidaFannstSemInniheldurStr() {
		if( "en".equals( language ) ) {
			return "page was found that contains";
		}
		else {
			return "síða fannst sem inniheldur";
		}
	}

	public String sidurFundustSemInnihaldaStr() {
		if( "en".equals( language ) ) {
			return "pages were found that contain";
		}
		else {
			return "síður fundust sem innihalda";
		}
	}

	public String ordidStr() {
		if( "en".equals( language ) ) {
			return "the word";
		}
		else {
			return "orðið";
		}
	}
}