package concept.components.client;


import com.webobjects.appserver.WOContext;

import concept.SWGenericSiteLook;

/**
 * The default SiteLook for a SoloWeb site. Returned if no other look is specified for the current site.
 */

public class SWStandardSiteLook extends SWGenericSiteLook {

	public SWStandardSiteLook( WOContext context ) {
		super( context );
	}
}