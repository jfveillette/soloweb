package concept;

import is.rebbi.core.util.StringUtilities;
import is.rebbi.wo.util.USArrayUtilities;
import is.rebbi.wo.util.USHTTPUtilities;
import is.rebbi.wo.util.USUtilities;

import com.webobjects.appserver.WOApplication;
import com.webobjects.appserver.WOContext;
import com.webobjects.appserver.WORequest;
import com.webobjects.appserver.WORequestHandler;
import com.webobjects.appserver.WOResponse;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.foundation.NSArray;

import concept.components.SWErrorMessage;
import concept.data.SWDocument;
import er.extensions.eof.ERXEC;
import er.extensions.foundation.ERXStringUtilities;

public class SWDocumentRequestHandler extends WORequestHandler {

	public static final String KEY = "swdocument";

	@Override
	public WOResponse handleRequest( WORequest request ) {

		String path = request.requestHandlerPath();

		if( !StringUtilities.hasValue( path ) ) {
			return error( request, "No document specified", 404 );
		}

		NSArray<String> parts = NSArray.componentsSeparatedByString( path, "/" );

		if( !USArrayUtilities.hasObjects( parts ) ) {
			return error( request, "No document specified", 404 );
		}

		String id = parts.objectAtIndex( 0 );

		if( !ERXStringUtilities.isDigitsOnly( id ) ) {
			return error( request, "Illegal documentID. Document numbers may only contain numeric characters", 404 );
		}

		SWDocument document = SWDocument.documentWithID( ERXEC.newEditingContext(), Integer.valueOf( id ) );

		if( document == null ) {
			return error( request, "The requested document does not exist", 404 );
		}

		if( !document.hasData() ) {
			return error( request, "The requested document contains no data", 500 );
		}

		boolean forceDownload = "true".equals( request.stringFormValueForKey( "download" ) );

		return USHTTPUtilities.responseWithStreamAndMimeType( document.nameForDownload(), document.inputStream(), document.size(), document.mimeType(), forceDownload );
	}

	private static final WOResponse error( WORequest request, String message, int status ) {
		WOApplication app = WOApplication.application();
		return SWErrorMessage.errorWithMessageAndStatusCode( message, app.createContextForRequest( request ), status );
	}

	public static String urlForDocumentWithIDInContext( EOEditingContext ec, Object documentID, WOContext context ) {
		SWDocument document = SWDocument.documentWithID( ec, USUtilities.integerFromObject( documentID ) );
		return urlForDocumentInContext( document, context );
	}

	public static String urlForDocumentInContext( SWDocument document, WOContext context ) {
		String url = null;

		if( document != null ) {
			url = "/doc/" + document.primaryKey();
		}

		return url;
	}
}