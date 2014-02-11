package concept.components;

import com.webobjects.appserver.WOContext;
import com.webobjects.appserver.WOResponse;

import concept.Concept;
import er.ajax.AjaxUtils;
import er.extensions.appserver.ERXApplication;
import er.extensions.components.ERXComponent;

public class SWSessionTimeoutPage extends ERXComponent {

	public SWSessionTimeoutPage( WOContext context ) {
		super( context );
	}

	/**
	 * Returns a response, based on if the user is logged in or not.
	 */
	@Override
	public void appendToResponse( WOResponse r, WOContext c ) {
		super.appendToResponse( r, c );
		AjaxUtils.addStylesheetResourceInHead( context(), r, Concept.sw().frameworkBundleName(), "bootstrap-3/css/bootstrap.min.css" );
		AjaxUtils.addScriptResourceInHead( context(), r, Concept.sw().frameworkBundleName(), "bootstrap-3/js/bootstrap.min.js" );
	}

	public static WOResponse handleSessionRestorationErrorInContext( WOContext context ) {
		return ERXApplication.erxApplication().pageWithName( SWSessionTimeoutPage.class, context ).generateResponse();
	}
}