package concept.components.admin;

import com.webobjects.appserver.WOComponent;
import com.webobjects.appserver.WOContext;
import com.webobjects.appserver.WOResponse;

import concept.SWApplication;
import er.ajax.AjaxUtils;

public class SWAdminComponentWrapper extends WOComponent {

	public SWAdminComponentWrapper( WOContext context ) {
		super( context );
	}

	public boolean hasRefresh() {
		return pageNameToRefresh() != null;
	}

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