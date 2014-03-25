package concept.components.admin;

import java.awt.image.BufferedImage;
import java.util.Random;

import com.webobjects.appserver.WOComponent;
import com.webobjects.appserver.WOContext;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSData;

import concept.data.SWPicture;
import concept.util.SWPictureUtilities;

public class SWPreviewPicture extends WOComponent {

	public SWPicture picture;
	public int scalePercentage = 100;
	public String compression = "70";

	public int width = 0;
	public int height = 0;

	public NSData tempData;

	public NSArray<String> qualityValues = new NSArray<>( new String[] { "10", "20", "30", "40", "50", "60", "70", "80", "90", "100" } );

	public SWPreviewPicture( WOContext context ) {
		super( context );
	}

	public void setPicture( SWPicture newPicture ) {
		picture = newPicture;
	}

	public SWPicture picture() {
		return picture;
	}

	public int width() {
		if( width == 0 ) {
			width = picture().width();
		}

		return width;
	}

	public int height() {
		if( height == 0 ) {
			height = picture().height();
		}

		return height;
	}

	public WOComponent resize() {

		if( widthHasChanged() || heightHasChanged() ) {

			if( !widthHasChanged() ) {
				width = picture.width();
			}

			if( !heightHasChanged() ) {
				height = picture.height();
			}

			SWPictureUtilities.scale( picture, width, height, Integer.parseInt( compression ) );
		}

		scalePercentage = 100;
		width = picture.width();
		height = picture.height();

		return null;
	}

	public boolean sizeHasChanged() {
		return (scalePercentage != 0) && (scalePercentage != 100);
	}

	public boolean widthHasChanged() {
		return ((width != 0) && (width != picture.width()));
	}

	public boolean heightHasChanged() {
		return ((height != 0) && (height != picture.height()));
	}

	public String anURL() {
		int randomNumber = new Random().nextInt( 32000 );
		return picture.pictureURL() + "?" + randomNumber;
	}

	/* Image cropping code */

	public int x1 = -1;
	public int y1 = -1;
	public int x2 = -1;
	public int y2 = -1;

	public void setX( int newX ) {
		if( x1 < 0 ) {
			x1 = newX;
		}
		else {
			x2 = newX;
		}
	}

	public void setY( int newY ) {
		if( y1 < 0 ) {
			y1 = newY;
		}
		else {
			y2 = newY;
		}
	}

	public int x() {
		return 0;
	}

	public int y() {
		return 0;
	}

	public boolean hasTwoPoints() {
		return x1 > -1 && x2 > -1 && y1 > -1 && y2 > -1;
	}

	public WOComponent selectAreaForCropping() {

		/*if( x1 < 0 || x2 < 0 || y1 < 0 || y2 < 0 )
		    return null;

		BufferedImage image = bufferedImage();

		Graphics graphics = image.getGraphics();
		graphics.setColor(Color.gray);
		graphics.fillRect( x1, y1, x2 - x1 + 1, y2 - y1 + 1 );

		tempData = SWPictureUtilities.jpegEncodeBufferedImage( image, 80 );*/

		return null;
	}

	public WOComponent performCrop() {
		BufferedImage image = bufferedImage();
		image = image.getSubimage( x1, y1, x2 - x1, y2 - y1 );
		picture.setData( SWPictureUtilities.jpegEncodeBufferedImage( image, 80 ) );
		tempData = null;

		x1 = -1;
		y1 = -1;
		x2 = -1;
		y2 = -1;

		return null;
	}

	public BufferedImage bufferedImage() {
		return SWPictureUtilities.bufferedImageFromData( picture.data() );
	}
}