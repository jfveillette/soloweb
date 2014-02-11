package concept;

import is.rebbi.wo.util.USHTTPUtilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.webobjects.appserver.WORequest;
import com.webobjects.appserver.WORequestHandler;
import com.webobjects.appserver.WOResponse;
import com.webobjects.eocontrol.EOEditingContext;

import concept.data.SWDocument;
import er.extensions.eof.ERXEC;

/**
 * Handles requests for documents
 */

public class CPDocumentRequestHandler extends WORequestHandler {

	private static final Logger logger = LoggerFactory.getLogger( CPDocumentRequestHandler.class );

	public static final String KEY = "cpdocument";

	/**
	 * Handles the request and returns a corresponding document.
	 */
	@Override
	public WOResponse handleRequest( WORequest request ) {

		String s = request.requestHandlerPath();
		s = s.substring( 0, s.indexOf( "/" ) );

		EOEditingContext ec = ERXEC.newEditingContext();
		SWDocument document = SWDocument.documentWithID( ec, new Integer( s ) );

		WOResponse response = USHTTPUtilities.responseWithStreamAndMimeType( document.nameForDownload(), document.inputStream(), document.size(), document.mimeType(), false );

/*
		String rangeHeader = request.headerForKey( "range" );

		if( rangeHeader != null ) {
			logger.info( "range: " + rangeHeader );
			if( rangeHeader.equals( "bytes=0-1" ) ) {
				data = data.subdataWithRange( new NSRange(0,1) );
				logger.info( "I madeit you a subdata");
			}
		}

//		WOResponse response = new WOResponse();
//		response.setContent( data );

		WOResponse response = USHTTPUtilities.responseWithDataAndMimeType( document.nameForDownload(), data, document.mimeType() );
//		response.setHeader( "8b30001-ab4caa-49fa182643940", "ETag" );
//		response.setHTTPVersion( "HTTP/1.1" );

		if( rangeHeader != null ) {
			response.setHeader( "bytes", "accept-ranges" );
			response.setHeader( contentRangeHeader( data.length() ), "content-range" );
			response.setStatus( 206 );
		}
 */

		return response;
	}

	private String contentRangeHeader( int length ) {
		StringBuilder b = new StringBuilder();
		b.append( "bytes " );
		b.append( 0 );
		b.append( "-" );
		b.append( length - 1 );
		b.append( "/" );
		b.append( length );
		return b.toString();
	}
}