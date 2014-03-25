package concept.components.admin;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOComponent;
import com.webobjects.appserver.WOContext;
import com.webobjects.foundation.NSMutableArray;

import concept.ViewPage;
import concept.data.SWPage;

/**
 * A component for reordering the subpages of the selected page.
 */

public class SWReorderSubpages extends ViewPage<SWPage> {

	public WOComponent componentToReturnTo;
	public SWPage currentPage;
	public NSMutableArray<SWPage> pageArray;

	public NSMutableArray<SWPage> pageArray() {
		if( pageArray == null ) {
			pageArray = selectedObject().sortedSubPages().mutableClone();
		}

		return pageArray;
	}

	public SWReorderSubpages( WOContext context ) {
		super( context );
	}

	public WOActionResults reorder() {
		int i = 0;

		for( SWPage nextPage : pageArray ) {
			nextPage.setSortNumber( i++ );
		}

		return null;
	}
}