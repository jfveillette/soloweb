package concept.components.client;


import com.webobjects.appserver.WOContext;

import concept.SWGenericSiteLook;
import concept.data.SWPage;

public class SWDefaultLook4 extends SWGenericSiteLook {

	public SWPage currentPage;
	public SWPage currentBreadcrumbPage;

	public SWDefaultLook4( WOContext context ) {
		super( context );
	}
}