package concept;

import is.rebbi.core.util.StringUtilities;
import is.rebbi.wo.util.USArrayUtilities;
import is.rebbi.wo.util.USHTTPUtilities;

import com.webobjects.appserver.WOApplication;
import com.webobjects.appserver.WORequest;
import com.webobjects.appserver.WORequestHandler;
import com.webobjects.appserver.WOResponse;
import com.webobjects.foundation.NSArray;

import concept.components.SWErrorMessage;
import concept.data.SWPicture;
import er.extensions.eof.ERXEC;
import er.extensions.foundation.ERXStringUtilities;

public class SWPictureRequestHandler extends WORequestHandler {

	public static final String KEY = "swpicture";

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
			return error( request, "Illegal id. Document numbers may only contain numeric characters", 404 );
		}

		SWPicture object = SWPicture.pictureWithID( ERXEC.newEditingContext(), Integer.valueOf( id ) );

		if( object == null ) {
			return error( request, "The requested document does not exist", 404 );
		}

		if( !object.hasData() ) {
			return error( request, "The requested document contains no data", 500 );
		}

		boolean forceDownload = "true".equals( request.stringFormValueForKey( "download" ) );

		return USHTTPUtilities.responseWithStreamAndMimeType( object.nameForDownload(), object.inputStream(), object.size(), object.mimeType(), forceDownload );
	}

	private static final WOResponse error( WORequest request, String message, int status ) {
		WOApplication app = WOApplication.application();
		return SWErrorMessage.errorWithMessageAndStatusCode( message, app.createContextForRequest( request ), status );
	}
}