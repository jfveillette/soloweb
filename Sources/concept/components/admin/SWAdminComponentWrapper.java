package concept.components.admin;


import com.webobjects.appserver.WOComponent;
import com.webobjects.appserver.WOContext;
import com.webobjects.appserver.WOResponse;

import concept.SWApplication;

import er.ajax.AjaxUtils;

/**
 * Most components in the admin system are wrapped in SWAdminComoponentWrapper. It contains standard header information
 * such as links to the system's standard stylesheets and javascripts
 */

public class SWAdminComponentWrapper extends WOComponent {

	public SWAdminComponentWrapper( WOContext context ) {
		super( context );
	}

	/**
	 * Determines if this page should execute a refresh when the refresh() JS-method is invoked.
	 */
	public boolean hasRefresh() {
		return pageNameToRefresh() != null;
	}

	/**
	* If this variable is not null, soloweb will attempt to reload the page's content, when the javascript function
	* refresh() is executed.
	*/
	public String pageNameToRefresh() {
		return (String)valueForBinding( "pageNameToRefresh" );
	}

	@Override
	public boolean synchronizesVariablesWithBindings() {
		return false;
	}

	@Override
	public void appendToResponse( WOResponse r, WOContext c ) {
		super.appendToResponse( r, c );
		AjaxUtils.addStylesheetResourceInHead( context(), r, SWApplication.swapplication().frameworkBundleName(), "sw32/css/soloweb.css" );
		AjaxUtils.addScriptResourceInHead( context(), r, SWApplication.swapplication().frameworkBundleName(), "sw32/scripts/changescript.js" );
		AjaxUtils.addScriptResourceInHead( context(), r, SWApplication.swapplication().frameworkBundleName(), "sw32/scripts/globalscript.js" );
		AjaxUtils.addScriptResourceInHead( context(), r, SWApplication.swapplication().frameworkBundleName(), "sw32/ckeditor/ckeditor.js" );

	}
}