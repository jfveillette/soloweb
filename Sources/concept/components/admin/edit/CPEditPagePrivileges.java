package concept.components.admin.edit;

import com.webobjects.appserver.WOContext;

import concept.ViewPage;
import concept.data.SWPage;

/**
 * Edit the privileges for a page.
 */

public class CPEditPagePrivileges extends ViewPage<SWPage> {

	public CPEditPagePrivileges( WOContext context ) {
		super( context );
	}
}