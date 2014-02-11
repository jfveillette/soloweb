package concept.deprecated;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.webobjects.foundation.NSData;

/*
 * A class that wraps some of the ImageMagick command line tools.  This stuff will only
 * work on OS X at the moment.
 *
 * The purpose of this class is to allow Java code to work with an image that comes either from
 * a file on disk or from memory (it is then written to a temporary file during work).  After
 * work has been completed on the image, it can be read into memory or copied to some other location
 * on disk.
 */

@Deprecated
public class SWImageMagick {

	private static final Logger logger = LoggerFactory.getLogger( SWImageMagick.class );

	private static final String mIMToolsPath = "/usr/local/bin/";

	private String mFilePath;
	private boolean mIsValid;
	private boolean mIsTemporaryFile = false;

	/**
	 * Construct a SWImageMagick object given a complete path to the file (full path + file name)
	 * @param filePath The full path of the file
	 */
	public SWImageMagick( String filePath ) {
		File aFile = new File( filePath );
		if( aFile.exists() && aFile.canRead() ) {
			mFilePath = filePath;
			mIsValid = true;
		}
		else {
			mIsValid = false;
		}
	}

	/**
	 * Construct a SWImageMagick object given the actual data of the image.
	 * @param imageData The image data
	 */
	public SWImageMagick( NSData imageData ) {
		try {
			File temp = File.createTempFile( "SWImageMagick", null );
			temp.deleteOnExit();
			FileOutputStream out = new FileOutputStream( temp );
			out.write( imageData.bytes() );
			out.close();
			mFilePath = temp.getAbsolutePath();
			mIsValid = true;
			mIsTemporaryFile = true;
		}
		catch( IOException ioex ) {
			logger.error( "Failed to create SWImageMagick instance", ioex );
			mIsValid = false;
		}
	}

	/**
	 * Construct a SWImageMagick object given a File object.
	 * @param aFile A File object pointing to the image file
	 */
	public SWImageMagick( File aFile ) {
		if( aFile.exists() && aFile.canRead() ) {
			mFilePath = aFile.getAbsolutePath();
			mIsValid = true;
		}
		else {
			mIsValid = false;
		}
	}

	/**
	 * Delete any temporary files created.
	 */
	public void cleanUp() {
		if( mIsValid && mIsTemporaryFile ) {
			File aFile = new File( mFilePath );
			try {
				aFile.delete();
			}
			catch( Exception ex ) {}
		}
	}

	/**
	 * Returns the bytes of the image or null if there is not a valid file.
	 * @return The bytes
	 */
	public NSData getDataAsNSData() {
		NSData theData = null;

		if( mIsValid ) {
			try {
				File aFile = new File( mFilePath );
				FileInputStream in = new FileInputStream( aFile );
				byte[] bytes = new byte[(int)aFile.length()];
				in.read( bytes );
				in.close();
				theData = new NSData( bytes );
			}
			catch( Exception ex ) {
				logger.error( "Exception", ex );
			}
		}

		return theData;
	}

	/**
	 * Scales the image proportionally so that it is max maxWidth pixels wide and max maxHeight pixels high.
	 * @param maxWidth Maximum allowed width
	 * @param maxHeight Maximum allowed height
	 * @return True if the image was scaled successfully, false otherwise
	 */
	public boolean scaleImage( int maxWidth, int maxHeight ) {
		boolean successful = false;

		if( mIsValid ) {
			try {
				// Compose and run the command
				File newFile = File.createTempFile( "SWImageMagick", null );
				String cmd = mIMToolsPath + "convert " + mFilePath + " -resize " + maxWidth + "x" + maxHeight + " jpg:" + newFile.getAbsolutePath();
				Process proc = Runtime.getRuntime().exec( cmd );
				int result = proc.waitFor();
				if( result == 0 ) {
					File oldFile = new File( mFilePath );
					oldFile.delete();
					mFilePath = newFile.getAbsolutePath();
					successful = true;
				}
				else {
					mFilePath = null;
					successful = false;
				}
			}
			catch( Exception ex ) {
				logger.error( ex.getMessage(), ex );
			}
		}

		return successful;
	}

	/**
	 * Main method for testing purposes only.
	 * @param args
	 */
	public static void main( String[] args ) {}
}