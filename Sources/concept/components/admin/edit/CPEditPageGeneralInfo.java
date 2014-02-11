package concept.components.admin.edit;

import com.webobjects.appserver.WOContext;

import concept.ViewPage;
import concept.data.SWPage;

/**
 * Edit the general info for a page.
 */

public class CPEditPageGeneralInfo extends ViewPage<SWPage> {

	public CPEditPageGeneralInfo( WOContext context ) {
		super( context );
	}
}