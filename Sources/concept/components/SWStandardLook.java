package concept.components;

import com.webobjects.appserver.WOContext;
import com.webobjects.appserver.WOResponse;

import concept.Concept;
import concept.data.SWSite;
import er.ajax.AjaxUtils;
import er.extensions.appserver.ERXRequest;

public class SWStandardLook extends SWLook {

	public SWStandardLook( WOContext context ) {
		super( context );
	}

	@Override
	public void appendToResponse( WOResponse r, WOContext c ) {
		super.appendToResponse( r, c );
		AjaxUtils.addStylesheetResourceInHead( context(), r, Concept.sw().frameworkBundleName(), "bootstrap-3/css/bootstrap.min.css" );
		AjaxUtils.addStylesheetResourceInHead( context(), r, Concept.sw().frameworkBundleName(), "SWStandardLook.css" );
		AjaxUtils.addScriptResourceInHead( context(), r, Concept.sw().frameworkBundleName(), "bootstrap-3/js/bootstrap.min.js" );
	}

	public String siteTitle() {
		SWSite site = site();

		if( site != null ) {
			if( site.name() != null ) {
				return site.name();
			}
		}

		return ((ERXRequest)context().request())._serverName();
	}
}