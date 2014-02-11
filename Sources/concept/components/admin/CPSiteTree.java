package concept.components.admin;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOContext;

import concept.Inspection;
import concept.CPAdminComponent;
import concept.data.SWPage;
import concept.data.SWSite;

/**
 * The site tree displayed to the left when you log into the system.
 */

public class CPSiteTree extends CPAdminComponent {

	/**
	 * The current site being iterated over
	 */
	public SWSite currentSite;

	/**
	 * The current page being iterated over
	 */
	public SWPage currentPage;

	public CPSiteTree( WOContext context ) {
		super( context );
	}

	/**
	 * Selects the current page and displays it for editing in an SWEditPage component.
	 */
	public WOActionResults selectPage() {
		return Inspection.editObjectInContext( currentPage, context() );
	}
}