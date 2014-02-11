package concept.documents;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.webobjects.foundation.NSData;

import er.extensions.eof.ERXGenericRecord;

/**
 * Classes that will provide data for SWDocument should inherit from this class;
 */

public abstract class Storage {

	private static final Logger logger = LoggerFactory.getLogger( Storage.class );

	public abstract InputStream in( ERXGenericRecord document );
	public abstract OutputStream out( ERXGenericRecord document );
	public abstract void deleteData( ERXGenericRecord document );
	public abstract boolean hasData( ERXGenericRecord document );
	public abstract long sizeOfData( ERXGenericRecord document );

	public NSData fetchData( ERXGenericRecord document ) {

		try {
			InputStream is = in( document );
			long length = sizeOfData( document );

			if( length > Integer.MAX_VALUE ) {
				throw new RuntimeException( "File is too large" );
			}

			byte[] bytes = new byte[(int)length];

			int offset = 0;
			int numRead = 0;
			while( offset < bytes.length && (numRead = is.read( bytes, offset, bytes.length - offset )) >= 0 ) {
				offset += numRead;
			}

			if( offset < bytes.length ) {
				throw new IOException( "Could not completely read file" );
			}

			is.close();
			return new NSData( bytes );
		}
		catch( IOException e ) {
			logger.debug( "Failed to read data" );
			return null;
		}
	}

	public void writeData( ERXGenericRecord document, NSData data ) {
		byte[] bytes;

		if( data == null ) {
			bytes = new byte[0];
		}
		else {
			bytes = data.bytes();
		}

		try {
			OutputStream out = out( document );
			out.write( bytes );
			out.close();
		}
		catch( IOException e ) {
			throw new RuntimeException( e );
		}
	}
}