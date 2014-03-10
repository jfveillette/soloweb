package concept;

import is.rebbi.wo.definitions.EntityViewDefinition;
import is.rebbi.wo.util.USHTTPUtilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WORequest;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.foundation.NSDictionary;

import concept.components.ListPageGeneric;
import concept.components.admin.SWLogin;
import concept.components.user.SWCreateUser;
import concept.components.user.SWForgottenPassword;
import concept.data.SWPage;
import concept.data.SWSite;
import concept.urls.SWEOURLProvider;
import concept.urls.SWStaticURLs;
import er.extensions.appserver.ERXDirectAction;
import er.extensions.eof.ERXGenericRecord;

/**
 * Main entry point into the system.
 */

public class InspectAction extends ERXDirectAction {

	private static final Logger logger = LoggerFactory.getLogger( InspectAction.class );

	private static final String INSPECTION_PREFIX = "/i/";
	private static final String LIST_PREFIX = "/l/";
	private static final String SEARCH_PREFIX = "/search/";
	public static final String PASSWORD_RESET_REQUEST = "/passwordResetRequest/";

	public InspectAction( WORequest r ) {
		super( r );
	}

	/**
	 * Checks the request for what site was requested, and returns its frontpage.
	 */
	@Override
	public WOActionResults defaultAction() {
		SWSite site = SWPageUtilities.siteFromRequest( ec(), request() );

		if( site == null ) {
			String message = "No site was found matching the host: " + USHTTPUtilities.host( request() );
			return USHTTPUtilities.statusResponse( 404, message );
		}

		SWPage page = site.frontpage();

		if( page == null ) {
			String message = "A site was found, but it has no frontpage. The site's id is: " + site.primaryKey();
			return USHTTPUtilities.statusResponse( 404, message );
		}

		return Inspection.inspectObjectInContext( page, context() );
	}

	/**
	 * @return The default editingContext.
	 */
	private EOEditingContext ec() {
		return session().defaultEditingContext();
	}

	/**
	 * @return The login page.
	 */
	public WOActionResults loginAction() {
		return pageWithName( SWLogin.class );
	}

	/**
	 * @return A page for resetting a user's password.
	 */
	public WOActionResults forgottenPasswordAction() {
		return pageWithName( SWForgottenPassword.class );
	}

	/**
	 * @return A page for creating a user.
	 */
	public WOActionResults createUserAction() {
		return pageWithName( SWCreateUser.class );
	}

	/**
	 * @return A page for inspecting the specified object.
	 */
	public WOActionResults handlerAction() {
		String url = url();

		logger.info( "Handling URL: {}", url );

		String redirectURL = SWStaticURLs.url( url, context() );

		if( redirectURL != null ) {
			return USHTTPUtilities.redirectTemporary( redirectURL );
		}

		if( url.startsWith( INSPECTION_PREFIX ) ) {
			ERXGenericRecord object = SWEOURLProvider.objectFromURL( ec(), url );

			if( object == null ) {
				return response404();
			}

			return Inspection.inspectObjectInContext( object, context() );
		}

		if( url.startsWith( PASSWORD_RESET_REQUEST ) ) {
			String afterPrefix = url.substring( PASSWORD_RESET_REQUEST.length() );
			NSDictionary<String, Object> params = new NSDictionary<String, Object>( afterPrefix, "key" );
			String searchURL = context().directActionURLForActionNamed( SWPasswordResetAction.class.getSimpleName(), params );
			return USHTTPUtilities.redirectTemporary( searchURL );
		}

		if( url.startsWith( LIST_PREFIX ) ) {
			String afterPrefix = url.substring( LIST_PREFIX.length() );
			EntityViewDefinition t = EntityViewDefinition.definitionForURLPrefix( afterPrefix );
			ListPageGeneric nextPage = pageWithName( ListPageGeneric.class );
			nextPage.setViewDefinition( t );
			return nextPage;
		}

		if( url.startsWith( SEARCH_PREFIX ) ) {
			String afterPrefix = url.substring( SEARCH_PREFIX.length() );
			logger.info( "searchString: " + afterPrefix );
			String directActionName = SWSearchAction.class.getSimpleName() + "/search";
			NSDictionary<String, Object> params = new NSDictionary<String, Object>( afterPrefix, "searchString_field" );
			String searchURL = context().directActionURLForActionNamed( directActionName, params );
			return USHTTPUtilities.redirectTemporary( searchURL );
		}

		return response404();
	}

	/**
	 * @return The requested URL, either from a URL parameter or Apache's 404 handler
	 */
	private String url() {
		String url = request().stringFormValueForKey( "url" );

		if( url == null ) {
			url = USHTTPUtilities.redirectURL( request() );
		}

		return url;
	}

	/**
	 * @return The 404 page.
	 */
	private WOActionResults response404() {
		return USHTTPUtilities.statusResponse( 404, "Nothing found at: " + url() );
	}
}