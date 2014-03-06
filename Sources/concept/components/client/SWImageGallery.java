package concept.components.client;

import is.rebbi.core.util.StringUtilities;
import is.rebbi.wo.util.ImageInfo;
import is.rebbi.wo.util.USArrayUtilities;
import is.rebbi.wo.util.USEOUtilities;
import is.rebbi.wo.util.USUtilities;

import java.io.File;
import java.io.FileInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.webobjects.appserver.WOContext;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSNumberFormatter;

import concept.SWDirectAction;
import concept.SWGenericComponent;
import concept.data.SWAssetFolder;
import concept.data.SWPicture;
import er.extensions.appserver.ERXSession;
import er.extensions.foundation.ERXStringUtilities;

public class SWImageGallery extends SWGenericComponent {

	private static final Logger logger = LoggerFactory.getLogger( SWImageGallery.class );

	public SWPicture currentPicture;

	// usageMode indicates if the component is being used as a content butur (usageMode = 0) or a
	// sidebar component (usageMode = 1).  When the component is embedded in smth like a front page
	// component, a binding must be added to set useageMode to 1 as the component will not have a
	// custom info dictionary to get values from.
	public int useageMode = 0;

	// The following variables are for use when usageMode is being set to 1.  All but the first one correspond to a customInfo key
	// with the same name.
	public int folderId = 0;
	public String swImageGalleryLink = "";
	public String swImageGalleryType = "";
	public String swImageGalleryWidth = "";
	public String swImageGalleryHeight = "";
	public String swImageGalleryDelay = "";
	public String swImageGallerySort = "";
	public String swImageGalleryPicSize = "";

	private ImageInfo ii;

	public SWImageGallery( WOContext context ) {
		super( context );
	}

	public SWPicture firstPicture() {
		try {
			if( currentPicture != null ) {
				NSArray<SWPicture> pics = currentComponent().picture().folder().sortedPictures();
				currentPicture = pics.objectAtIndex( 0 );
				return currentPicture;
			}
		}
		catch( Exception e ) {}

		return null;
	}

	public SWPicture nextPicture() {
		try {
			if( currentPicture != null ) {
				NSArray<SWPicture> pics = currentComponent().picture().folder().sortedPictures();
				int i = pics.indexOfObject( selectedPicture() );

				if( i == NSArray.NotFound ) {
					return null;
				}

				return pics.objectAtIndex( ++i );
			}
		}
		catch( Exception e ) {}

		return null;
	}

	public SWPicture previousPicture() {
		try {
			if( currentPicture != null ) {
				NSArray<SWPicture> pics = currentComponent().picture().folder().sortedPictures();
				int i = pics.indexOfObject( selectedPicture() );

				if( i == NSArray.NotFound ) {
					return null;
				}

				currentPicture = (pics.objectAtIndex( --i ));
				return currentPicture;
			}
		}
		catch( Exception e ) {}

		return null;
	}

	public SWPicture lastPicture() {
		try {
			if( currentPicture != null ) {
				NSArray<SWPicture> pics = currentComponent().picture().folder().sortedPictures();
				currentPicture = pics.objectAtIndex( pics.count() - 1 );
				return currentPicture;
			}
		}
		catch( Exception e ) {}

		return null;
	}

	public SWPicture selectedPicture() {
		try {
			Integer id = new Integer( context().request().stringFormValueForKey( "pictureID" ) );
			String size = currentComponent().customInfo().stringValueForKey( "swimagegallerypreviewsize" );
			currentPicture = (SWPicture)USEOUtilities.objectWithPK( session().defaultEditingContext(), SWPicture.ENTITY_NAME, id );
			return currentPicture;
		}
		catch( Exception e ) {}

		return null;
	}

	public String previewURL() {
		Integer id = new Integer( context().request().stringFormValueForKey( "pictureID" ) );
		String size = currentComponent().customInfo().stringValueForKey( "swimagegallerypreviewsize" );
		currentPicture = (SWPicture)USEOUtilities.objectWithPK( session().defaultEditingContext(), SWPicture.ENTITY_NAME, id );

		if( StringUtilities.hasValue( size ) ) {
			return currentPicture.previewURL( size );
		}
		else {
			return currentPicture.pictureURL();
		}
	}

	public String thumbnailURL() {
		String size = currentComponent().customInfo().stringValueForKey( "swimagegallerythumbsize" );

		if( StringUtilities.hasValue( size ) && currentPicture.hasSize( size ) ) {
			return currentPicture.previewURL( size );
		}
		else {
			return currentPicture.thumbnailURL();
		}
	}

	public boolean isValidImage() {
		return currentPicture.size() > 0;
	}

	public SWPicture oneImage() {
		currentPicture = currentComponent().picture();
		return currentPicture;
	}

	public SWPicture randomImage() {
		currentPicture = null;

		if( useageMode == 0 ) {
			SWPicture selectedPict = currentComponent().picture();

			if( selectedPict != null ) {
				try {
					NSArray pics = selectedPict.folder().sortedPictures();
					currentPicture = (SWPicture)USArrayUtilities.randomObject( pics );
				}
				catch( Exception e ) {
					logger.error( "Error showing random image", e );
				}
			}
			else {
				currentPicture = null;
			}
		}
		else {
			SWAssetFolder folder = (SWAssetFolder)USEOUtilities.objectWithPK( session().defaultEditingContext(), SWAssetFolder.ENTITY_NAME, new Integer( folderId ) );

			if( folder != null ) {
				NSArray pics = folder.sortedPictures();
				currentPicture = (SWPicture)USArrayUtilities.randomObject( pics );
			}
		}

		return currentPicture;
	}

	public String pictureHyperlink() {
		String result;

		if( useageMode == 0 ) {
			result = pageHyperlinkFromString( currentComponent().customInfo().stringValueForKey( "swimagegallerylink" ) );
		}
		else {
			result = swImageGalleryLink;
		}

		return result;
	}

	public boolean pictureHasNoLink() {
		return !StringUtilities.hasValue( pictureHyperlink() );
	}

	public String selectedImageGalleryType() {
		String result;

		if( useageMode == 0 ) {
			result = currentComponent().customInfo().stringValueForKey( "swimagegallerytype" );
		}
		else {
			result = swImageGalleryType;
		}

		return result;
	}

	public boolean currentPictureHasDescription() {
		if( currentPicture != null ) {
			String desc = currentPicture.description();
			return (desc != null && desc.length() > 0);
		}

		return false;
	}

	public String flashSlideShowUrl() {
		String host = SWDirectAction.hostForRequest( this.context().request() );

		// Get the picture folderId and transition delay time
		Integer theFolderId = null;
		String theDelayTime = null;
		if( useageMode == 0 ) {
			SWPicture selectedPict = currentComponent().picture();
			if( selectedPict != null && selectedPict.folder() != null ) {
				theFolderId = selectedPict.folder().folderID();
			}
			else {
				theFolderId = context().request().numericFormValueForKey( "folderid", new NSNumberFormatter( NSNumberFormatter._IntegerPattern ) ).intValue();
			}

			theDelayTime = currentComponent().customInfo().stringValueForKey( "swimagegallerydelay" );
		}
		else {
			theFolderId = new Integer( folderId );
			theDelayTime = swImageGalleryDelay;
		}

		String control = "auto";
		if( theDelayTime == null || "".equals( theDelayTime ) || "0".equals( theDelayTime ) ) {
			control = "manual";
		}
		String tansition = "";
		if( "auto".equals( control ) ) {
			tansition = "dissolve";
		}
		// Set default values if needed
		if( theFolderId == null ) {
			theFolderId = new Integer( 0 );
		}
		//if (theDelayTime == null) theDelayTime = "10";
		String sort = "false";
		if( USUtilities.booleanFromObject( currentComponent().customInfo().valueForKey( "swimagegallerysort" ) ) ) {
			sort = "true";
		}
		String description = "false";
		if( USUtilities.booleanFromObject( currentComponent().customInfo().valueForKey( "swimagegallerydescription" ) ) ) {
			description = "true";
		}

		String daurl = "http://" + host + context().directActionURLForActionNamed( "swPictureFolder", null );
		daurl += "&amp;folderID=" + theFolderId;
		daurl += "&amp;hold=" + theDelayTime;
		daurl += "&amp;control=" + control;
		daurl += "&amp;transition=" + tansition;
		daurl += "&amp;sort=" + sort;
		daurl += "&amp;description=" + description;
		String link = currentComponent().customInfo().stringValueForKey( "swimagegallerylink" );
		if( link == null ) {
			link = "";
		}
		//if (link.indexOf("http://") == -1)
		//	link = "http://" + context().request().headerForKey("host") + link;
		daurl += "&amp;link=" + link;
		daurl = "/sw32/flash/SWGallery4.swf?url=" + daurl;
		return daurl; // "http://www.lausn.is/Apps/WebObjects/Lausn.woa/wa/swPictureFolder&folderID=1000004&transition=dissolve&hold=10";
	}

	public String flashSlideShowWidth() {
		String result;

		if( useageMode == 0 ) {
			result = currentComponent().customInfo().stringValueForKey( "swimagegallerywidth" );
		}
		else {
			result = swImageGalleryWidth;
		}

		return result;
	}

	public String flashSlideShowHeight() {
		String result;

		if( useageMode == 0 ) {
			result = currentComponent().customInfo().stringValueForKey( "swimagegalleryheight" );
		}
		else {
			result = swImageGalleryHeight;
		}

		return result;
	}

	public String selectedPictureSize() {
		String result = "";

		if( useageMode == 0 ) {
			result = currentComponent().customInfo().stringValueForKey( "swimagegallerypicsize" );
		}
		else {
			result = swImageGalleryPicSize;
		}

		return result;
	}

	public String imgWidth() {
		String width = String.valueOf( imageInfo().getWidth() );

		if( "0".equals( width ) ) {
			width = null;
		}

		return width;
	}

	public String imgHeight() {
		String height = String.valueOf( imageInfo().getHeight() );

		if( "0".equals( height ) ) {
			height = null;
		}

		return height;
	}

	private ImageInfo imageInfo() {
		if( ii == null ) {
			ii = new ImageInfo();
			String size = selectedPictureSize();
			File file = currentPicture.file( size );

			try {
				ii.setInput( new FileInputStream( file ) );
			}
			catch( Exception e2 ) {
				try {
					ii.setInput( new FileInputStream( currentPicture.file() ) );
				}
				catch( Exception e3 ) {}
			}
			ii.check();
		}
		return ii;
	}

	public String thumbSize() {
		int size = 120;

		String string = currentComponent().customInfo().stringValueForKey( "swimagegallerythumbsize" );

		if( StringUtilities.hasValue( string ) && ERXStringUtilities.isDigitsOnly( string ) ) {
			Integer i = Integer.valueOf( string );
			size = i.intValue();
		}

		size += 30;

		return String.valueOf( size );
	}

	public String ecardResult() {
		return (String)((ERXSession)session()).objectStore().valueForKey( "ecard" );
	}

	/**
	 * Creates a hyperlink (href) from theLink.  theLink can contain one of the following:
	 * <ul>
	 * <li>A numeric page id (f.ex. 1000001) ==> /id/1000001
	 * <li>A page linking name (f.ex. frontpage) ==> /page/frontpage
	 * <li>Anything else which will be assumed to be a complete URL except http:// will be added if it is missing
	 * </ul>
	 * @param link The data on which the returned href is based
	 * @return The complete href
	 */
	public static String pageHyperlinkFromString( String link ) {
		String href = null;

		if( StringUtilities.hasValue( link ) ) {
			link = link.trim();

			try {
				if( ERXStringUtilities.isDigitsOnly( link ) ) {
					href = "/id/" + link;
				}
				else if( link.indexOf( "http" ) == -1 && link.indexOf( "/" ) == -1 && link.indexOf( "." ) == -1 ) {
					// Seems to be a page linking name
					href = "/page/" + link;
				}
				else {
					// Assume it is a complete URL, at the most missing http://
					if( link.indexOf( "http://" ) == -1 && link.indexOf( "https://" ) == -1 ) {
						href = "http://" + link;
					}
					else {
						href = link;
					}
				}
			}
			catch( Exception ex ) {
				// Handle by using data as i
				href = link;
				logger.debug( "Failed to create hyperlink from: " + link, ex );
			}
		}

		return href;
	}
}