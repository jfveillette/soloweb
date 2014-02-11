package concept;

import is.rebbi.wo.util.USHTTPUtilities;

import com.webobjects.appserver.WORequest;
import com.webobjects.appserver.WORequestHandler;
import com.webobjects.appserver.WOResponse;
import com.webobjects.eocontrol.EOEditingContext;

import concept.data.SWDocument;
import er.extensions.eof.ERXEC;

/**
 * Handles requests for documents
 */

public class SWThumbnailRequestHandler extends WORequestHandler {

	public static final String KEY = "swthumbnail";

	/**
	 * Handles the request and returns a corresponding document.
	 */
	@Override
	public WOResponse handleRequest( WORequest request ) {

		String s = request.requestHandlerPath();
		String[] components = s.split( "/" );
		s = s.substring( 0, s.indexOf( "/" ) );

		EOEditingContext ec = ERXEC.newEditingContext();
		SWDocument document = SWDocument.documentWithID( ec, Integer.parseInt( components[0] ) );

		if( document == null ) {
			return USHTTPUtilities.response404();
		}

		String pixels = components[1];

		int intPixels = Integer.parseInt( pixels );
		return USHTTPUtilities.responseWithStreamAndMimeType( document.nameForDownload(), document.thumbnailInputStream( intPixels ), document.thumbnailFileSize( intPixels ), document.mimeType(), false );
	}
}