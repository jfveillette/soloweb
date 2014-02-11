package concept.components.client;


import com.webobjects.appserver.WOContext;

import concept.SWGenericSiteLook;

/**
 * The Look returned if no other look is specified for the alternate (printer) look.
 */

public class SWStandardPrinterLook extends SWGenericSiteLook {

	public SWStandardPrinterLook( WOContext context ) {
		super( context );
	}
}