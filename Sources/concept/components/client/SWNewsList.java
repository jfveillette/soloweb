package concept.components.client;

import is.rebbi.wo.util.USArrayUtilities;
import is.rebbi.wo.util.USUtilities;

import java.text.DateFormatSymbols;
import java.text.Format;

import com.webobjects.appserver.WOContext;
import com.webobjects.eocontrol.EOSortOrdering;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSSelector;
import com.webobjects.foundation.NSTimestampFormatter;

import concept.CPGenericComponent;
import concept.data.SWNewsItem;

/**
 * The default news list
 */

public class SWNewsList extends CPGenericComponent {

	private static final String VALUE_SORT_KEY_RANDOM = "Random";
	private static final String VALUE_SORT_KEY_ALPHABETICALLY = "Alphabetically";
	private static final String VALUE_SORT_ORDER_ASCENDING = "Ascending";

	private static final String PARAM_CATEGORY = "category";
	private static final String PARAM_DAYS_TO_EXCLUDE = "daysToExclude";
	private static final String PARAM_DAYS_TO_INCLUDE = "daysToInclude";
	private static final String DETAIL_DETAIL_PAGE_ID = "detailPageID";
	private static final String PARAM_VIEW_MODE = "viewMode";
	private static final String PARAM_DETAIL_PAGE_NAME = "detailPageName";
	private static final String PARAM_ITEMS_TO_SHOW = "itemsToShow";
	private static final String PARAM_ITEMS_TO_SKIP = "itemsToSkip";
	private static final String PARAM_SORT_ORDER = "sortOrder";
	private static final String PARAM_SORT_KEY = "sortKey";

	private static final int VALUE_VIEW_MODE_HEADING = 1;
	private static final int VALUE_VIEW_MODE_EXCERPT = 2;
	private static final int VALUE_VIEW_MODE_TEXT = 3;

	private SWNewsItem _selectedNewsItem;
	public SWNewsItem currentNewsItem;

	public SWNewsList( WOContext context ) {
		super( context );
	}

	public NSArray<SWNewsItem> newsItems() {
		return (NSArray<SWNewsItem>)valueForBinding( "newsItems" );
	}

	/**
	 * @return the list of news to show on the page.
	 */
	public NSArray<SWNewsItem> newslist() {
		if( USArrayUtilities.hasObjects( newsItems() ) ) {
			return newsItems();
		}

		// FIXME: Implement with SWNewsUtilities.
		return NSArray.emptyArray();
	}

	private Integer integerValueForKeyWithDefaultValue( String key, Integer defaultValue ) {
		Object o = valueForKeyWithDefaultValue( key, defaultValue );
		return USUtilities.integerFromObject( o );
	}

	private String stringValueForKeyWithDefaultValue( String key, String defaultValue ) {
		Object o = valueForKeyWithDefaultValue( key, defaultValue );
		return USUtilities.stringFromObject( o );
	}

	private Object valueForKeyWithDefaultValue( String key, Object defaultValue ) {

		if( currentComponent() == null ) {
			return defaultValue;
		}

		Object o = currentComponent().customInfo().valueForKey( key );

		if( o == null ) {
			return defaultValue;
		}

		return o;
	}

	private NSArray<EOSortOrdering> sortOrdering() {
		return new NSArray<EOSortOrdering>( new EOSortOrdering( sortKey(), sortSelector() ) );
	}

	private String sortKey() {
		if( VALUE_SORT_KEY_ALPHABETICALLY.equals( stringValueForKeyWithDefaultValue( PARAM_SORT_KEY, null ) ) ) {
			return SWNewsItem.HEADING_KEY;
		}

		return SWNewsItem.DATE_KEY;
	}

	private NSSelector sortSelector() {
		if( VALUE_SORT_ORDER_ASCENDING.equals( stringValueForKeyWithDefaultValue( PARAM_SORT_ORDER, null ) ) ) {
			return EOSortOrdering.CompareCaseInsensitiveAscending;
		}

		return EOSortOrdering.CompareCaseInsensitiveDescending;
	}

	public Integer itemsToSkip() {
		return integerValueForKeyWithDefaultValue( PARAM_ITEMS_TO_SKIP, null );
	}

	public Integer itemsToShow() {
		return integerValueForKeyWithDefaultValue( PARAM_ITEMS_TO_SHOW, 10000 );
	}

	public boolean displayLinkOnly() {
		return (integerValueForKeyWithDefaultValue( PARAM_VIEW_MODE, -1 ) == VALUE_VIEW_MODE_HEADING);
	}

	public boolean displayExcerpt() {
		return (integerValueForKeyWithDefaultValue( PARAM_VIEW_MODE, -1 ) == VALUE_VIEW_MODE_EXCERPT);
	}

	public boolean displayText() {
		return (integerValueForKeyWithDefaultValue( PARAM_VIEW_MODE, -1 ) == VALUE_VIEW_MODE_TEXT);
	}

	public String detailPageName() {
		return stringValueForKeyWithDefaultValue( PARAM_DETAIL_PAGE_NAME, null );
	}

	public String detailPageID() {
		return stringValueForKeyWithDefaultValue( DETAIL_DETAIL_PAGE_ID, null );
	}

	public Integer folderID() {
		return integerValueForKeyWithDefaultValue( PARAM_CATEGORY, null );
	}

	public Integer daysToInclude() {
		return integerValueForKeyWithDefaultValue( PARAM_DAYS_TO_INCLUDE, null );
	}

	public Integer daysToExclude() {
		return integerValueForKeyWithDefaultValue( PARAM_DAYS_TO_EXCLUDE, null );
	}

	public boolean randomSort() {
		return VALUE_SORT_KEY_RANDOM.equals( stringValueForKeyWithDefaultValue( PARAM_SORT_KEY, null ) );
	}

	public Format dateFormatter() {
		return new NSTimestampFormatter( "%A %e. %B %Y", new DateFormatSymbols( locale() ) );
	}

	public Format timeFormatter() {
		return new NSTimestampFormatter( "%H:%M" );
	}
}