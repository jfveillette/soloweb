package concept.util;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

import com.webobjects.appserver.WORequest;
import com.webobjects.appserver.WORequestHandler;
import com.webobjects.appserver.WOResponse;

public class SWURLRewrite extends WORequestHandler {

	@Override
	public WOResponse handleRequest( WORequest request ) {
		if( request.requestHandlerPath().equals( "smu" ) ) {
			try {
				URL url = new URL( "http://hugi.karlmenn.is/d/name.csv.zip" );
				HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
				long contentLength = urlConnection.getContentLengthLong();
				Map<String, List<String>> headers = urlConnection.getHeaderFields();

				WOResponse r = new WOResponse();

				for( String key : headers.keySet() ) {
					List<String> values = headers.get( key );
					r.setHeaders( values, key );
				}

				r.setContentStream( urlConnection.getInputStream(), 32000, contentLength );
				r.setStatus( urlConnection.getResponseCode() );
				return r;
			}
			catch( IOException e ) {
				throw new RuntimeException( "Sorry man...", e );
			}
		}

		return null;
	}
}