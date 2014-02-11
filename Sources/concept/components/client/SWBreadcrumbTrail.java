package concept.components.client;


import com.webobjects.appserver.WOContext;
import com.webobjects.foundation.NSArray;

import concept.SWGenericComponent;
import concept.data.SWPage;

/**
 * SWBreadcrumbTrail allows you to include a breadcrumbTrail in your applications.
 * Just include it in your page and bind the SWPage object that the trail should lead to.
 */

public class SWBreadcrumbTrail extends SWGenericComponent {

	public SWPage selectedPage, currentBreadcrumb;
	public int index;
	public boolean includeSelf = false;
	private String itemSeparatorHtml = null;

	public SWBreadcrumbTrail( WOContext context ) {
		super( context );
	}

	/**
	 * Returns the array of pages that is displayed.
	 */
	public NSArray breadcrumb() {
		NSArray crumbs = NSArray.EmptyArray;
		if( selectedPage != null ) {
			crumbs = selectedPage.breadcrumb( includeSelf );
		}
		return crumbs;
	}

	/* *
	 * The page to show the trail from.
	 * /
	public SWPage selectedPage() {
		return (SWPage)valueForBinding( "selectedPage" );
	}
	*/

	public String className() {
		String className = "breadcrumb";
		className += " nr" + index;
		if( index == 0 ) {
			className += " first";
		}
		else if( index == breadcrumb().count() - 1 ) {
			className += " last";
		}
		return className;
	}

	public String getItemSeparatorHtml() {
		if( itemSeparatorHtml != null && index < breadcrumb().count() - 1 ) {
			return itemSeparatorHtml;
		}
		else {
			return "";
		}
	}

	public void setItemSeparatorHtml( String s ) {
		itemSeparatorHtml = s;
	}
}