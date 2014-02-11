package concept.components.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.webobjects.appserver.WOComponent;
import com.webobjects.appserver.WOContext;

import concept.data.SWPictureLink;

public class SWPictureInsert extends WOComponent {

	private static final Logger logger = LoggerFactory.getLogger( SWPictureInsert.class );

	public SWPictureLink link;
	public String tag = "span"; // use 'div' or 'span' -- currently under testing

	public SWPictureInsert( WOContext context ) {
		super( context );
	}

	public boolean linkToNone() {
		return link.linkToLarge() == null || link.linkToLarge().intValue() == 0;
	}

	public String imageHolderClass() {
		StringBuffer klass = new StringBuffer( "sw-image-holder " );

		if( link.align() != null ) {
			switch (link.align().intValue()) {
				case 0:
					klass.append( "sw-image-left" );
					break;

				case 1:
					klass.append( "sw-image-right" );
					break;

				case 2:
					klass.append( "sw-image-center" );
					break;

				case 3:
					klass.append( "sw-image-inline" );
					break;

				default:
					break;
			}
		}
		return klass.toString();
	}

	public String imgSrc() {
		return link.picture().previewURL( link.size() );
	}

	public String imgWidth() {
		try {
			return String.valueOf( link.picture().widthForPictureSize( link.size() ) );
		}
		catch( Exception e ) {
			logger.error( "Failed to get picture width", e );
			return "";
		}
	}

	public String imgHeight() {
		try {
			return String.valueOf( link.picture().heightForPictureSize( link.size() ) );
		}
		catch( Exception e ) {
			logger.error( "Failed to get picture height", e );
			return "";
		}
	}

	public String captionStyle() {
		return "width:" + imgWidth() + "px;";
	}

	public String imgSrcLarge() {
		return link.picture().previewURL( link.sizeLarge() );
	}
}