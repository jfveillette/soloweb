package concept.components.admin;

import is.rebbi.wo.util.SWSettings;

import com.webobjects.appserver.WOContext;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableDictionary;

import concept.SWGenericComponent;
import concept.data.SWComponent;

public class SWImageGalleryAdmin extends SWGenericComponent {

	public String currentDisplayType, currentSize;
	private NSMutableDictionary _displayTypes = null;

	public SWImageGalleryAdmin( WOContext context ) {
		super( context );

		// Build the displayTypes dictionary
		_displayTypes = new NSMutableDictionary();
		_displayTypes.setObjectForKey( "Ein mynd", "SWImageGallery_one" );
		_displayTypes.setObjectForKey( "Mynd af handah&oacute;fi &uacute;r m&ouml;ppu myndar", "SWImageGallery_random" );
		_displayTypes.setObjectForKey( "Myndas&yacute;ning &uacute;r m&ouml;ppu myndar", "SWImageGallery_slideshow" );
		_displayTypes.setObjectForKey( "Myndaalb&uacute;m &uacute;r m&ouml;ppu myndar", "SWImageGallery_gallery" );
	}

	public NSArray displayTypes() {
		return _displayTypes.allKeys();
	}

	public NSArray pictureSizes() {
		String allSizes = "original," + SWSettings.stringForKey( "pictureSizes" );
		return NSArray.componentsSeparatedByString( allSizes, "," );
	}

	public String currentDisplayTypeDescription() {
		return (String)_displayTypes.valueForKey( currentDisplayType );
	}

	@Override
	public void setCurrentComponent( SWComponent newOne ) {
		super.setCurrentComponent( newOne );
		currentComponent().setTemplateName( "SWImageGallery" );
	}

	public boolean showSizes() {
		boolean ok = "SWImageGallery_one".equals( currentComponent().customInfo().stringValueForKey( "swimagegallerytype" ) );
		ok |= "SWImageGallery_random".equals( currentComponent().customInfo().stringValueForKey( "swimagegallerytype" ) );
		return ok;
	}
}