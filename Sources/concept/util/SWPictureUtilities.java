package concept.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.MemoryCacheImageOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.webobjects.eocontrol.EOAndQualifier;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOFetchSpecification;
import com.webobjects.eocontrol.EOKeyValueQualifier;
import com.webobjects.eocontrol.EOQualifier;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSData;

import concept.data.SWPicture;

/**
 * SWPictureUtilities allows the programmer to work with SWPicture objects
 */

public class SWPictureUtilities {

	private static final Logger logger = LoggerFactory.getLogger( SWPictureUtilities.class );

	/**
	 * Takes the given SWPicture, scales it by the given percentage and resaves it as JPEG.
	 *
	 * @param aPicture The picture to scale
	 * @param scalePercent The percentage to scale by (from 1-infinity)
	 * @param qualityPercent The JPEG quality to write out (1-100)
	 */
	public static void scale( SWPicture aPicture, int scalePercent, int qualityPercent ) {

		if( aPicture != null ) {
			int newWidth = (int)(aPicture.width() * ((float)scalePercent / (float)100));
			int newHeight = (int)(aPicture.height() * ((float)scalePercent / (float)100));
			scale( aPicture, newWidth, newHeight, qualityPercent );
		}
	}

	/**
	 * Takes the given SWPicture and scales it to the given size
	 *
	 * @param aPicture The picture to scale
	 * @param width The new width
	 * @param height The new height
	 * @param qualityPercent The JPEG quality to write out (1-100)
	 */

	public static void scale( SWPicture aPicture, int width, int height, int qualityPercent ) {
		// Compose the ImageMagick command
		String cmd = "/usr/bin/convert " + aPicture.path() + " -resize " + width + "x" + height + " -quality " + qualityPercent + " " + aPicture.path();
		try {
			Process process = Runtime.getRuntime().exec( cmd );
			process.waitFor();
		}
		catch( Exception ex ) {
			// Failed for some reason, just log it
			logger.debug( "Failed to resize picture with id: " + aPicture.id() + " because of exception", ex );
		}
	}

	public static void createThumbnail( String imagePath, String thumbnailPath, int maxWidth, int maxHeight, int qualityPercent ) throws IOException {
		String cmd = "";

		// Delete the thumbnail if it exists
		try {
			File thumbFile = new File( thumbnailPath );
			if( thumbFile.exists() ) {
				thumbFile.delete();
			}
		}
		catch( Exception ex ) {}

		// Compose the command
		//TODO: Allow for spaces in paths (tried adding quotes but it didn't work...)
		cmd = "/usr/bin/convert " + imagePath + " -resize " + maxWidth + "x" + maxHeight + " " + thumbnailPath + "";
		Process process = Runtime.getRuntime().exec( cmd );
		try {
			process.waitFor();
		}
		catch( Exception ex ) {}
	}

	public static BufferedImage bufferedImageFromData( NSData imageData ) {
		try {
			BufferedImage img = ImageIO.read( imageData.stream() );
			return img;
		}
		catch( Throwable e ) {
			logger.error( "Error reading buffered image from data", e );
			return null;
		}
	}

	public static NSData jpegEncodeBufferedImage( BufferedImage image, int qualityPercent ) {
		try {
			ByteArrayOutputStream os = new ByteArrayOutputStream();

			try {
				Iterator<ImageWriter> iter = ImageIO.getImageWritersByFormatName( "jpeg" );

				float quality = qualityPercent / 100f;
				ImageWriter writer = iter.next();
				ImageWriteParam iwp = writer.getDefaultWriteParam();
				iwp.setCompressionMode( ImageWriteParam.MODE_EXPLICIT );
				iwp.setCompressionQuality( quality );

				MemoryCacheImageOutputStream output = new MemoryCacheImageOutputStream( os );
				writer.setOutput( output );
				IIOImage iioimage = new IIOImage( image, null, null );
				writer.write( null, iioimage, iwp );
				writer.dispose();
			}
			catch( Exception e ) {
				logger.error( "Error while jpeg encoding buffered image", e );
			}
			finally {
				os.close();
			}
			byte[] arr = os.toByteArray();
			os.flush();
			os = null;

			if( arr != null ) {
				return new NSData( arr );
			}
			else {
				return NSData.EmptyData;
			}
		}
		catch( IOException e ) {
			logger.error( "Error while jpeg encoding buffered image", e );
			return null;
		}
	}

	public static void updateAllPictureThumbnailsFromId( EOEditingContext ec, Integer startPictureId, Integer endPictureId ) {
		EOQualifier q1 = new EOKeyValueQualifier( SWPicture.ID_KEY, EOQualifier.QualifierOperatorGreaterThanOrEqualTo, startPictureId );
		EOQualifier q2 = new EOKeyValueQualifier( SWPicture.ID_KEY, EOQualifier.QualifierOperatorLessThanOrEqualTo, endPictureId );
		EOAndQualifier q3 = new EOAndQualifier( new NSArray<>( new EOQualifier[] { q1, q2 } ) );
		EOFetchSpecification fs = new EOFetchSpecification( SWPicture.ENTITY_NAME, q3, null );
		NSArray<SWPicture> a = ec.objectsWithFetchSpecification( fs );

		Enumeration<SWPicture> e = a.objectEnumerator();

		logger.debug( "Full thumbnail rebuild started" );

		SWPicture pict;
		int cnt = 0;
		while( e.hasMoreElements() ) {
			pict = e.nextElement();
			cnt++;
			logger.debug( "Updating picture_id: " + pict.id() + " (" + cnt + " of " + a.size() + ")" );
			pict.updateThumbnails();
			if( cnt % 500 == 0 ) {
				ec.saveChanges();
			}
		}
		ec.saveChanges();

		logger.debug( "Full thumbnail rebuild done" );
	}
}