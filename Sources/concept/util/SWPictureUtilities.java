package concept.util;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import concept.data.SWPicture;

public class SWPictureUtilities {

	public static final Logger logger = LoggerFactory.getLogger( SWPictureUtilities.class );

	/**
	 * @param picture The picture to scale
	 * @param scalePercent The percentage to scale by (from 1-infinity)
	 * @param qualityPercent The JPEG quality to write out (1-100)
	 */
	public static void scale( SWPicture picture, int scalePercent, int qualityPercent ) {
		if( picture != null ) {
			int newWidth = (int)(picture.width() * ((float)scalePercent / (float)100));
			int newHeight = (int)(picture.height() * ((float)scalePercent / (float)100));
			scale( picture, newWidth, newHeight, qualityPercent );
		}
	}

	/**
	 * @param picture The picture to scale
	 * @param width The new width
	 * @param height The new height
	 * @param qualityPercent The JPEG quality to write out (1-100)
	 */
	public static void scale( SWPicture picture, int width, int height, int qualityPercent ) {

		String cmd = "/usr/bin/convert " + picture.path() + " -resize " + width + "x" + height + " -quality " + qualityPercent + " " + picture.path();

		try {
			Process process = Runtime.getRuntime().exec( cmd );
			process.waitFor();
		}
		catch( Exception ex ) {
			logger.error( "Failed to resize picture with id: " + picture.primaryKey() + " because of exception", ex );
		}
	}

	public static void createThumbnail( String imagePath, String thumbnailPath, int maxWidth, int maxHeight, int qualityPercent ) throws IOException {

		try {
			File thumbFile = new File( thumbnailPath );

			if( thumbFile.exists() ) {
				thumbFile.delete();
			}
		}
		catch( Exception ex ) {
			logger.error( "Failed to delete thumbnail for picture: " + imagePath + " because of exception", ex );
		}

		String cmd = "/usr/bin/convert " + imagePath + " -resize " + maxWidth + "x" + maxHeight + " " + thumbnailPath + "";
		Process process = Runtime.getRuntime().exec( cmd );

		try {
			process.waitFor();
		}
		catch( Exception ex ) {
			logger.error( "Failed to create thumbnail for picture: " + imagePath + " because of exception", ex );
		}
	}
}