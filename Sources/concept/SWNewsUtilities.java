package concept;

import is.rebbi.wo.util.SWTimedContentUtilities;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.webobjects.eoaccess.EOUtilities;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOFetchSpecification;
import com.webobjects.eocontrol.EOSortOrdering;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSDictionary;
import com.webobjects.foundation.NSMutableArray;

import concept.data.SWNewsItem;
import er.extensions.eof.qualifiers.ERXInQualifier;

/**
 * SWNewsUtilities contains some convenience methods to fetch news
 */

public class SWNewsUtilities extends Object {

	private static final Logger logger = LoggerFactory.getLogger( SWNewsUtilities.class );

	private static final String NEWS_DATE_ASC_SORT_ORDER = "date";
	private static final String NEWS_DATE_DESC_SORT_ORDER = "date desc";
	private static final NSArray<EOSortOrdering> NEWS_DATE_ASC_SORT_ORDERINGS = new NSArray<>( new EOSortOrdering( "date", EOSortOrdering.CompareAscending ) );
	private static final NSArray<EOSortOrdering> NEWS_DATE_DESC_SORT_ORDERINGS = new NSArray<>( new EOSortOrdering( "date", EOSortOrdering.CompareDescending ) );

	/**
	 * Finds the category with the given ID and returns a specified amount of news from it, sorted descendingly by date
	 *
	 * @param ec The EOEditingContext to fetch into
	 * @param numberOfItems The number of newsitem to fetch
	 * @param categoryID The primary key of the category to fetch from
	 */
	public static NSArray<SWNewsItem> recentNewsFromCategoryWithID( EOEditingContext ec, int numberOfItems, int categoryID ) {
		return recentNewsFromCategoryWithID( ec, numberOfItems, categoryID, false );
	}

	public static NSArray<SWNewsItem> recentNewsFromCategoryWithID( EOEditingContext ec, int numberOfItems, int categoryID, boolean ascendingOrder ) {
		String limitString = (numberOfItems != 0) ? " LIMIT " + numberOfItems : "";
		String categoryCondition = "news_Category_ID=" + categoryID;
		String publishedCondition = "published=1";
		String timeInOutCondition = "(time_in IS NULL OR time_in <= NOW()) AND (time_out IS NULL OR time_out > NOW())";
		String whereString = categoryCondition + " AND " + publishedCondition + " AND " + timeInOutCondition;
		String queryString = "SELECT id, date FROM Sw_News_Item WHERE " + whereString + " ORDER BY " + (ascendingOrder == true ? NEWS_DATE_ASC_SORT_ORDER : NEWS_DATE_DESC_SORT_ORDER) + limitString;

		NSArray<NSDictionary> idDicts = EOUtilities.rawRowsForSQL( ec, "SoloWeb", queryString, new NSArray( new Object[] { "id" } ) );

		NSMutableArray ids = new NSMutableArray();

		for( int i = 0; i < idDicts.count(); i++ ) {
			NSDictionary dict = idDicts.objectAtIndex( i );
			ids.addObject( dict.valueForKey( "id" ) );
		}

		ERXInQualifier q = new ERXInQualifier( SWNewsItem.ID_KEY, ids );
		EOFetchSpecification fs = new EOFetchSpecification( SWNewsItem.ENTITY_NAME, q, (ascendingOrder ? NEWS_DATE_ASC_SORT_ORDERINGS : NEWS_DATE_DESC_SORT_ORDERINGS) );
		NSArray<SWNewsItem> fetchedNews = ec.objectsWithFetchSpecification( fs );
		return SWTimedContentUtilities.validateDisplayTimeForArray( fetchedNews );
	}

	/**
	 * Finds the category with the given ID and returns a specified amount of news from it that are dated on or after today, in descending order by date.
	 * Note that news items that have no date are also returned and are at the top of the list.
	 *
	 * @param ec The EOEditingContext to fetch into
	 * @param numberOfItems The number of newsitems to fetch
	 * @param categoryID The primary key of the category to fetch from
	 */
	public static NSArray<SWNewsItem> nextEventsFromCategoryWithID( EOEditingContext ec, int numberOfItems, int categoryID ) {
		String limitString = (numberOfItems != 0) ? " LIMIT " + numberOfItems : "";
		String categoryCondition = "news_Category_ID=" + categoryID;
		String publishedCondition = "published=1";
		GregorianCalendar cal = new GregorianCalendar();
		String todayStr = "" + cal.get( Calendar.YEAR ) + "-" + (cal.get( Calendar.MONTH ) < 9 ? "0" : "") + (cal.get( Calendar.MONTH ) + 1) + "-" + (cal.get( Calendar.DAY_OF_MONTH ) < 10 ? "0" : "") + cal.get( Calendar.DAY_OF_MONTH ) + " 00:00:00.0";
		String dateCondition = "(date is null or date>=TIMESTAMP '" + todayStr + "')";
		String whereString = categoryCondition + " AND " + publishedCondition + " AND " + dateCondition;
		String queryString = "SELECT id, date FROM Sw_News_Item WHERE " + whereString + " ORDER BY " + " date ASC" + limitString;

		NSArray<NSDictionary> idDicts = EOUtilities.rawRowsForSQL( ec, "SoloWeb", queryString, new NSArray<>( new String[] { "id" } ) );

		NSMutableArray ids = new NSMutableArray();

		for( int i = 0; i < idDicts.count(); i++ ) {
			NSDictionary dict = (idDicts.objectAtIndex( i ));
			ids.addObject( dict.valueForKey( "id" ) );
		}

		ERXInQualifier q = new ERXInQualifier( SWNewsItem.ID_KEY, ids );
		EOFetchSpecification fs = new EOFetchSpecification( SWNewsItem.ENTITY_NAME, q, NEWS_DATE_ASC_SORT_ORDERINGS );
		NSArray<SWNewsItem> fetchedNews = ec.objectsWithFetchSpecification( fs );
		return SWTimedContentUtilities.validateDisplayTimeForArray( fetchedNews );
	}
}