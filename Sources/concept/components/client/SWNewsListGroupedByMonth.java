package concept.components.client;

import is.rebbi.wo.util.USTimestampUtilities;

import java.text.SimpleDateFormat;

import com.webobjects.appserver.WOContext;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSTimestamp;

import concept.SWNewsUtilities;
import concept.data.SWNewsItem;
import er.extensions.foundation.ERXStringUtilities;

/**
 * Displays newsitems sorted by date.
 */

public class SWNewsListGroupedByMonth extends SWNewsList {

	public NSTimestamp currentMonth;
	public SWNewsItem currentNewsItem;
	private NSArray<SWNewsItem> _allItems;

	public SWNewsListGroupedByMonth( WOContext context ) {
		super( context );
	}

	private NSArray<SWNewsItem> allItems() {
		if( _allItems == null ) {
			EOEditingContext ec = session().defaultEditingContext();
			_allItems = SWNewsUtilities.recentNewsFromCategoryWithID( ec, null, folderID() );
			_allItems = SWNewsItem.DATE.descs().sorted( _allItems );
		}

		return _allItems;
	}

	public SimpleDateFormat monthFormat() {
		return new java.text.SimpleDateFormat( "MMMM yyyy", locale() );
	}

	public SimpleDateFormat dayFormat() {
		return new java.text.SimpleDateFormat( "d. MMM", locale() );
	}

	/**
	 * All months containing stuff.
	 */
	public NSArray<NSTimestamp> months() {
		NSMutableArray<NSTimestamp> months = new NSMutableArray<NSTimestamp>();

		for( SWNewsItem n : allItems() ) {

			NSTimestamp beginning = USTimestampUtilities.normalizeTimestampForMonth( n.date() );

			if( !months.containsObject( beginning ) ) {
				months.addObject( beginning );
			}
		}

		return months;
	}

	public NSArray<SWNewsItem> newsitems() {
		NSTimestamp nextMonth = currentMonth.timestampByAddingGregorianUnits( 0, 1, 0, 0, 0, 0 );
		return SWNewsItem.DATE.gte( currentMonth ).and( SWNewsItem.DATE.lt( nextMonth ) ).filtered( allItems() );
	}

	public String currentMonthString() {
		String s = monthFormat().format( currentMonth );
		return ERXStringUtilities.capitalize( s );
	}
}