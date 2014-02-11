package concept.logging;

import is.rebbi.wo.util.USJson;

import java.nio.charset.Charset;
import java.util.Date;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import er.extensions.appserver.ERXApplication;

public class LoggingQueue {

	private static HttpClient client = new DefaultHttpClient();

	public static synchronized void log( String type, String text, Map<?,?> info ) {
		LogMessage message = new LogMessage();
		message.setType( type );
		message.setText( text );
		message.setInfo( info );
		log( message );
	}

	public static synchronized void log( String type, String text, String userAgent, String softUserID, Map<?,?> info ) {
		LogMessage message = new LogMessage();
		message.setType( type );
		message.setText( text );
		message.setUserAgent( userAgent );
		message.setSoftUserID( softUserID );
		message.setInfo( info );
		log( message );
	}

	private static synchronized void log( LogMessage message ) {
		message.setDate( new Date() );

		if( ERXApplication.application() != null ) {
			message.setApplicationName( ERXApplication.application().name() );
		}

		String json = USJson.toJson( message );

		try {
			HttpPost request = new HttpPost( "http://www.godurkodi.is/Apps/WebObjects/Godurkodi.woa/wa/LogAction" );
			StringEntity entity = new StringEntity( json, Charset.forName( "UTF-8" ) );
			request.setEntity( entity );
			HttpResponse response = client.execute( request );
//			String s = StringUtilities.stringFromDataUsingEncoding( new NSData( response.getEntity().getContent(), 1024 ).bytes(), "UTF-8" );
		}
		catch( Exception e ) {
			e.printStackTrace();
		}
	}
}