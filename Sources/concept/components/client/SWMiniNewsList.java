package concept.components.client;


import com.webobjects.appserver.WOContext;
import com.webobjects.foundation.NSArray;

import concept.SWGenericComponent;
import concept.SWNewsUtilities;
import concept.data.SWNewsItem;
import concept.data.SWPage;

public class SWMiniNewsList extends SWGenericComponent {

	// Class binding properties
	public SWPage selectedPage;
	public Boolean isHidden;
	public String displayCount;
	public String newsCategory;
	public String newsDisplayPage;
	public Boolean showTextInList;
	public Integer maxTextLengthInList;
	public String title;
	public Boolean titleHidden;
	public Integer displayType;
	public String oldNewsDisplayPage;
	public Boolean showOldNewsLink;
	public String oldNewsLinkName;

	// Other class properties
	private NSArray newsItems = null;
	public SWNewsItem currentNewsItem;

	public SWMiniNewsList( WOContext context ) {
		super( context );
	}

	public NSArray getNewsItems() {
		if( newsItems == null ) {
			int dispCount = Integer.valueOf( displayCount == null ? "0" : displayCount ).intValue();
			int newsCat = Integer.valueOf( newsCategory == null ? "0" : newsCategory ).intValue();
			if( newsCat != 0 ) {
				newsItems = SWNewsUtilities.recentNewsFromCategoryWithID( session().defaultEditingContext(), dispCount, newsCat );
			}
		}

		return newsItems;
	}

	public String textExcerpt() {
		String theText;
		try {
			theText = currentNewsItem.text().substring( 0, maxTextLengthInList.intValue() );
		}
		catch( Exception ex ) {
			theText = "";
		}
		return theText;
	}

	/*
	News title (news date)
	Excerpt from news text
	*/
	public boolean displayType1() {
		return (displayType == null || displayType.intValue() <= 1);
	}

	/*
	News date
	News text excerpt
	News title
	*/
	public boolean displayType2() {
		return (displayType != null && displayType.intValue() == 2);
	}
}