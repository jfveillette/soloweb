package concept.components.client;

import is.rebbi.wo.util.USArrayUtilities;

import com.webobjects.appserver.WOContext;
import com.webobjects.foundation.NSArray;

import concept.CPBaseComponent;
import concept.data.SWPage;

public class CPSearchResults extends CPBaseComponent {

	/**
	* All results returned by the search
	*/
	public NSArray<SWPage> results;

	/**
	 * Current page being displayed in the found page list
	 */
	public SWPage currentPage;

	public CPSearchResults( WOContext context ) {
		super( context );
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
}