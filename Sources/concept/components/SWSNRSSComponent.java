package concept.components;

import is.rebbi.wo.util.USEOUtilities;

import com.webobjects.appserver.WOComponent;
import com.webobjects.appserver.WOContext;
import com.webobjects.appserver.WOResponse;
import com.webobjects.foundation.NSArray;

import concept.SWNewsUtilities;
import concept.data.SWNewsCategory;
import concept.data.SWNewsItem;

public class SWSNRSSComponent extends WOComponent {

	public SWNewsItem currentNewsItem;
	private Integer categoryID;
	private int count;
	private SWNewsCategory _category;
	private String _host;

	public SWSNRSSComponent( WOContext context ) {
		super( context );
		_host = context.request().headerForKey( "host" );
	}

	@Override
	public void appendToResponse( WOResponse r, WOContext c ) {
		r.setHeader( "text/xml", "Content-Type" );
		r.setContentEncoding( "UTF8" );
		super.appendToResponse( r, c );
	}

	public NSArray<SWNewsItem> newsItems() {
		return SWNewsUtilities.recentNewsFromCategoryWithID( session().defaultEditingContext(), count(), categoryID().intValue() );
	}

	public String urlString() {
		return "http://" + _host + "/" + category().rssPageName() + "&detail=" + currentNewsItem.primaryKey();
	}

	public void setCategoryID( Integer newCategoryID ) {
		categoryID = newCategoryID;
	}

	public Integer categoryID() {
		return categoryID;
	}

	public void setCount( int newCount ) {
		count = newCount;
	}

	public int count() {
		return count;
	}

	public String title() {
		return context().request().stringFormValueForKey( "title" );
	}

	public SWNewsCategory category() {
		if( _category == null ) {
			_category = (SWNewsCategory)USEOUtilities.objectWithPK( session().defaultEditingContext(), SWNewsCategory.ENTITY_NAME, categoryID() );
		}

		return _category;
	}
}