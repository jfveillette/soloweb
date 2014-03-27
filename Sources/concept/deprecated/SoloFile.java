package concept.deprecated;

import is.rebbi.wo.util.USHTTPUtilities;

import com.webobjects.appserver.WOApplication;
import com.webobjects.appserver.WORequest;
import com.webobjects.appserver.WOResponse;

import concept.SWDirectAction;
import concept.util.SWOldURLs;

/**
 * Used to be the default way of downloading documents, but has been replaced by SWDocumentRequestHandler.
 */

@Deprecated
public class SoloFile extends SWDirectAction {

	public SoloFile( WORequest aRequest ) {
		super( aRequest );
	}

	/**
	 * @deprecated Use SWDocumentRequestHandler instead.
	 */
	@Deprecated
	public WOResponse attachmentAction() {
		String id = request().stringFormValueForKey( "id" );
		String url = SWOldURLs.urlForDocumentWithIDInContext( session().defaultEditingContext(), id, WOApplication.application().createContextForRequest( request() ) );
		return USHTTPUtilities.redirectTemporary( url );
	}
}