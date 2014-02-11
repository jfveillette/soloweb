package concept;

import is.rebbi.wo.util.USHTTPUtilities;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOContext;
import com.webobjects.appserver.WOResponse;
import com.webobjects.eocontrol.EOEditingContext;

import er.ajax.AjaxUtils;

/**
 * SWAdminComponent is the common ancestor of most components presented in the SWAdmin framework.
 * It's function is to check if a user has logged in, - if he hasn't, nothing is appended to the response
 */

public abstract class CPAdminComponent extends CPBaseComponent {

	public CPAdminComponent( WOContext context ) {
		super( context );
	}

	/**
	 * A convenience variable to access the session's defaultEditingContext()
	 */
	@Override
	public EOEditingContext ec() {
		return session().defaultEditingContext();
	}

	/**
	 * Returns a response, based on if the user is logged in or not.
	 */
	@Override
	public void appendToResponse( WOResponse r, WOContext c ) {
//		if( requiresLogin() && SWSessionHelper.userInSession( session() ) == null ) {
//			super.appendToResponse( notLoggedInResponse(), c );
//		}
//		else {
			super.appendToResponse( r, c );
//		}

		AjaxUtils.addStylesheetResourceInHead( context(), r, Concept.sw().frameworkBundleName(), "bootstrap-3/css/bootstrap.min.css" );
		AjaxUtils.addStylesheetResourceInHead( context(), r, Concept.sw().frameworkBundleName(), "bootstrap-3/css/bootstrap-theme.min.css" );
		AjaxUtils.addScriptResourceInHead( context(), r, Concept.sw().frameworkBundleName(), "bootstrap-3/js/bootstrap.min.js" );
		AjaxUtils.addStylesheetResourceInHead( context(), r, Concept.sw().frameworkBundleName(), "admin.css" );
		AjaxUtils.addScriptResourceInHead( context(), r, Concept.sw().frameworkBundleName(), "admin.js" );
	}

	/**
	 * This method can be overridden in subclasses to indicate if the component requires login.
	 */
	public boolean requiresLogin() {
		return true;
	}

	/**
	 * This is returned if no user is logged in.
	 */
	private WOResponse notLoggedInResponse() {
		return USHTTPUtilities.statusResponse( 403, "You must log in to view this component." );
	}

	/**
	 * Saves changes made in the current session.
	 */
	public WOActionResults saveChanges() {
		ec().saveChanges();
		return null;
	}
}