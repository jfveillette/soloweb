package concept.components.client;

import is.rebbi.core.util.StringUtilities;

import com.webobjects.appserver.WOApplication;
import com.webobjects.appserver.WOContext;

import concept.SWApplication;
import concept.SWGenericComponent;
import concept.data.SWPicture;

public class SWSearchField2 extends SWGenericComponent {

	public SWSearchField2( WOContext context ) {
		super( context );
	}

	public String fieldStyle() {
		String style = ""; // Was following but that should be in the css
							// file: "font-size: 10px; color: gray;";
		String size = (String)currentComponent().customInfo().valueForKey( "searchFieldSize" );
		if( StringUtilities.hasValue( size ) ) {
			style += "width: " + size + ";";
		}
		return style;
	}

	public String imageSRC() {
		String src = WOApplication.application().resourceManager().urlForResourceNamed( "img/magnifying_glass.gif", SWApplication.swapplication().frameworkBundleName(), null, context().request() );

		Object id = currentComponent().customInfo().valueForKey( "searchImageID" );

		if( id != null ) {
			SWPicture pic = SWPicture.pictureWithID( session().defaultEditingContext(), new Integer( id.toString() ) );

			if( pic != null ) {
				src = pic.pictureURL();
			}
		}

		return src;
	}

	// adding compo id to javascript function makes them unique so more than
	// one search field can be placed on the page

	public String searchFormName() {
		return "searchForm" + currentComponent().primaryKey();
	}

	public String inputOnClick() {
		return "clearField" + currentComponent().primaryKey() + "();";
	}

	public String searchButtonLabel() {
		return customInfoString( "searchButtonLabel", "Leita" );
	}

	public String searchHref() {
		String p = customInfoString( "searchResultsPageSymbol", "" );
		return "/page/" + p;
	}
}