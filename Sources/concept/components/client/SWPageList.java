package concept.components.client;

import com.webobjects.appserver.WOComponent;
import com.webobjects.appserver.WOContext;
import com.webobjects.foundation.NSArray;

import concept.SWPageUtilities;
import concept.data.SWPage;

public class SWPageList extends WOComponent {

	// Class binding properties
	public SWPage selectedPage;
	public Boolean isHidden = new Boolean( false );
	public String parentPage;
	public String title;
	public Boolean titleHidden = new Boolean( false );

	// Other class properties
	private NSArray pages = null;
	public SWPage currentPage;

	public SWPageList( WOContext context ) {
		super( context );
	}

	public NSArray getPages() {
		if( pages == null ) {
			if( parentPage != null && parentPage.length() > 0 ) {
				int parentPageId = Integer.valueOf( parentPage ).intValue();
				SWPage parent = SWPageUtilities.pageWithID( session().defaultEditingContext(), new Integer( parentPageId ) );
				pages = parent.sortedAndApprovedSubPages();
			}
		}

		return pages;
	}
}