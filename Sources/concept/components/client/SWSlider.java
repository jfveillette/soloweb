package concept.components.client;

import is.rebbi.core.util.StringUtilities;
import is.rebbi.wo.util.USEOUtilities;

import com.webobjects.appserver.WOContext;
import com.webobjects.foundation.NSArray;

import concept.SWGenericComponent;
import concept.SWNewsUtilities;
import concept.data.SWComponent;
import concept.data.SWNewsItem;
import concept.data.SWPage;
import concept.data.SWPicture;

public class SWSlider extends SWGenericComponent {

	public SWPicture currentPicture;
	public SWNewsItem currentNewsItem;
	public SWComponent curComponent;
	public int sliderPageCount;

	public SWSlider( WOContext context ) {
		super( context );
	}

	public String sliderId() {
		return "swslider_" + currentComponent().primaryKey();
	}

	public String sliderStyle() {
		String style = "";
		String sliderType = customInfoString( "swslider_type", null );
		String sliderHeight = customInfoString( "swslider_height", "" );
		String sliderWidth = customInfoString( "swslider_width", "" );
		String pictureSize = customInfoString( "swslider_picturesize", "" );

		if( !"".equals( sliderHeight ) ) {
			style += "height: " + sliderHeight + "px;";
		}
		if( "imagesfromfolder".equals( sliderType ) ) {
			if( !"original".equals( sliderWidth ) && !"".equals( sliderWidth ) ) {
				style += "width: " + pictureSize + "px;";
			}
		}
		else {
			style += "width: " + sliderWidth + "px;";
		}

		return style;
	}

	public Boolean sliderAutoTransition() {
		return !sliderControls();
	}

	public Boolean sliderControls() {
		boolean result = true;
		Integer pause = sliderPause();
		if( pause != null && pause.intValue() > 0 ) {
			result = false;
		}
		return result;
	}

	public Integer sliderPause() {
		return customInfoInteger( "swslider_delay", 0 ) * 1000;
	}

	public Integer sliderSpeed() {
		return customInfoInteger( "swslider_speed", 0 ) * 1000;
	}

	public NSArray pictures() {
		NSArray pictures = null;

		// Get the selected picture
		SWPicture pict = null;
		Integer pictId = currentComponent().customInfo().integerValueForKey( "swslider_pictureid" );
		if( pictId != null && pictId.intValue() > 0 ) {
			pict = SWPicture.pictureWithID( session().defaultEditingContext(), pictId );
			if( pict != null ) {
				// Get all the pictures from the same folder as pict
				pictures = USEOUtilities.objectsForKeyWithValueSortedByKey( session().defaultEditingContext(), SWPicture.ENTITY_NAME, SWPicture.ASSET_FOLDER_ID_KEY, Integer.valueOf( pict.folder().primaryKey() ), "name" );
			}
		}

		if( pictures != null ) {
			sliderPageCount = pictures.count();
		}
		else {
			sliderPageCount = 0;
		}

		return pictures;
	}

	public String currentPictureUrl() {
		return currentPicture.previewURL( (String)currentComponent().customInfo().valueForKey( "swslider_picturesize" ) );
	}

	public Integer currentPictureWidth() {
		String s = customInfoString( "swslider_picturesize", "0" );

		if( s.equals( "original" ) ) {
			return currentPicture.width();
		}

		int w = Integer.parseInt( s );

		if( w <= 0 ) {
			w = currentPicture.width();
		}
		return w;
	}

	public Integer currentPictureHeight() {
		int h = currentPicture.heightForPictureSize( customInfoString( "swslider_picturesize", "original" ) );
		if( h <= 0 ) {
			h = currentPicture.height();
		}
		return h;
	}

	public NSArray news() {
		NSArray news = null;
		int newsFolderId = customInfoInteger( "swslider_newsfolderid", 0 );

		if( newsFolderId > 0 ) {
			int maxNewsCount = customInfoInteger( "swslider_newsmaxcount", 0 );
			news = SWNewsUtilities.recentNewsFromCategoryWithID( session().defaultEditingContext(), maxNewsCount, newsFolderId );
		}

		if( news != null ) {
			sliderPageCount = news.count();
		}
		else {
			sliderPageCount = 0;
		}

		return news;
	}

	public String newsTemplateName() {
		String name = customInfoString( "swslider_newsdisplaycomponent", null );

		if( name == null ) {
			name = "SWSliderNewsDisplay";
		}

		return name;
	}

	public NSArray components() {
		NSArray comps = null;
		String pageName = currentComponent().customInfo().stringValueForKey( "swslider_pagelinkingname" );
		if( StringUtilities.hasValue( pageName ) ) {
			SWPage page = (SWPage)USEOUtilities.objectMatchingKeyAndValue( session().defaultEditingContext(), "SWPage", "symbol", pageName );
			if( page != null ) {
				comps = page.sortedAndApprovedComponents();
			}
		}

		if( comps != null ) {
			sliderPageCount = comps.count();
		}
		else {
			sliderPageCount = 0;
		}

		return comps;
	}
}