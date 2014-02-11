package concept.components.client;


import com.webobjects.appserver.WOContext;

import concept.SWGenericSiteLook;
import concept.data.SWPage;

public class SWDefaultLook2 extends SWGenericSiteLook {

	public SWPage currentPage;
	public SWPage currentBreadcrumbPage;

	public SWDefaultLook2( WOContext context ) {
		super( context );
	}

	public boolean isSelected() {
		return currentPage.isParentPageOfPage( selectedPage(), true );
	}
}