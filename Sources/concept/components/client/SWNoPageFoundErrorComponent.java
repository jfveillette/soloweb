package concept.components.client;

import com.webobjects.appserver.WOContext;
import com.webobjects.appserver.WOResponse;
import com.webobjects.foundation.NSArray;

import concept.Concept;
import concept.SWBaseComponent;
import concept.data.SWSite;
import er.ajax.AjaxUtils;

public class SWNoPageFoundErrorComponent extends SWBaseComponent {

	public SWSite currentSite;

	public SWNoPageFoundErrorComponent( WOContext context ) {
		super( context );
	}

	@Override
	public void appendToResponse( WOResponse r, WOContext c ) {
		super.appendToResponse( r, c );
		AjaxUtils.addStylesheetResourceInHead( context(), r, Concept.sw().frameworkBundleName(), "bootstrap-3/css/bootstrap.min.css" );
		AjaxUtils.addStylesheetResourceInHead( context(), r, Concept.sw().frameworkBundleName(), "bootstrap-3/css/bootstrap-theme.min.css" );
		AjaxUtils.addScriptResourceInHead( context(), r, Concept.sw().frameworkBundleName(), "bootstrap-3/js/bootstrap.min.js" );
	}

	public NSArray<SWSite> sites() {
		return SWSite.allSites( ec() );
	}
}