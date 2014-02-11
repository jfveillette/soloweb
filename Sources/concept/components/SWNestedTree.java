package concept.components;


import com.webobjects.appserver.WOContext;
import com.webobjects.foundation.NSArray;

import concept.SWGenericComponent;
import concept.data.SWPage;

/**
 * Displays a nested tree of SWPages in a standard, unnumbered, hierarchical HTML-list (UL)
 */

public class SWNestedTree extends SWGenericComponent {

	/**
	 * The list of pages to display at the top level.
	 */
	public NSArray topLevelList;

	/**
	 * The page that the list is currently iterating through
	 */
	public SWPage currentPage;

	/**
	 * Index of the current iteration.
	 */
	public int currentIndex;

	public SWNestedTree( WOContext context ) {
		super( context );
	}
}