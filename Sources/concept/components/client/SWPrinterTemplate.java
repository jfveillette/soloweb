package concept.components.client;


import com.webobjects.appserver.WOContext;

import concept.SWGenericTemplate;

/**
 * The default template to use for printing pages. Same as SWStandardTemplate, except it dispalays the alternate (printer) sitelook instead of the normal one.
 */

public class SWPrinterTemplate extends SWGenericTemplate {

	public SWPrinterTemplate( WOContext context ) {
		super( context );
	}

	@Override
	public String lookName() {

		String lookName = selectedPage().siteForThisPage().printTemplate();

		if( lookName != null ) {
			return lookName;
		}

		return SWStandardPrinterLook.class.getSimpleName();
	}
}