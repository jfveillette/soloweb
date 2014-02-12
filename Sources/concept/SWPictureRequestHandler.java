package concept;

import is.rebbi.core.util.StringUtilities;
import is.rebbi.wo.util.FileType;
import is.rebbi.wo.util.USArrayUtilities;
import is.rebbi.wo.util.USHTTPUtilities;
import is.rebbi.wo.util.USUtilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.webobjects.appserver.WOApplication;
import com.webobjects.appserver.WOContext;
import com.webobjects.appserver.WORequest;
import com.webobjects.appserver.WORequestHandler;
import com.webobjects.appserver.WOResponse;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSData;

import concept.components.SWErrorMessage;
import concept.data.SWDocument;
import concept.data.SWPicture;
import er.extensions.eof.ERXEC;
import er.extensions.foundation.ERXStringUtilities;

public class SWPictureRequestHandler extends WORequestHandler {

	private static final Logger logger = LoggerFactory.getLogger( SWPictureRequestHandler.class );

	public static final String KEY = "swpicture";

	@Override
	public WOResponse handleRequest( WORequest request ) {

		EOEditingContext ec = ERXEC.newEditingContext();

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

		SWPicture document = SWPicture.pictureWithID( ec, Integer.valueOf( id ) );

		if( document == null ) {
			return error( request, "The requested document does not exist", 404 );
		}

		if( document.data() == null || document.data().length() == 0 ) {
			return error( request, "The requested document contains no data", 500 );
		}

		boolean forceDownload = "true".equals( request.stringFormValueForKey( "download" ) );
		return createResponse( document, forceDownload );
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

	/**
	 * Returns the data for this document, wrapped in a WOResponse object
	 */
	public static WOResponse createResponse( SWPicture document, boolean forceDownload ) {

		if( document.hasData() ) {
			String fileName = USHTTPUtilities.makeFilenameURLFriendly( document.name() );
			NSData data = document.data();
			FileType documentType = document.documentType();
			String contentType = "octet/stream";

			if( documentType != null && documentType.mimeType() != null ) {
				contentType = documentType.mimeType();
			}

			WOResponse aResponse = new WOResponse();

			aResponse.setContent( data );

			aResponse.appendHeader( contentType, "Content-Type" );
			aResponse.appendHeader( data.length() + "", "Content-Length" );

			if( forceDownload ) {
				aResponse.appendHeader( "attachment; filename=\"" + fileName + "\"", "Content-Disposition" );
			}
			else {
				aResponse.appendHeader( "inline;filename=\"" + fileName + "\"", "Content-Disposition" );
			}

			aResponse.disableClientCaching();

			aResponse.removeHeadersForKey( "Cache-Control" );
			aResponse.removeHeadersForKey( "pragma" );

			return aResponse;
		}

		return null;
	}
}