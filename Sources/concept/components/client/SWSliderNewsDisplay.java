package concept.components.client;

import com.webobjects.appserver.WOContext;

import concept.SWGenericComponent;
import concept.data.SWNewsItem;
import concept.data.SWPicture;

public class SWSliderNewsDisplay extends SWGenericComponent {

	public SWNewsItem selectedNewsItem;

	public SWSliderNewsDisplay( WOContext context ) {
		super( context );
	}

	public String newsLink() {
		String pageSymbol = customInfoString( "swslider_newsdisplaypage", "" );
		String link = "/page/" + pageSymbol + "&detail=" + selectedNewsItem.primaryKey();
		return link;
	}

	public String newsImageSource() {
		String source = "";

		if( selectedNewsItem != null ) {
			SWPicture picture = selectedNewsItem.picture2OrPicture();
			if( picture != null ) {
				source = picture.pictureURL();
			}
		}

		return source;
	}
}