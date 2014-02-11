package concept.components.client;

import is.rebbi.core.util.StringUtilities;
import is.rebbi.wo.util.SWSettings;
import is.rebbi.wo.util.USArrayUtilities;
import is.rebbi.wo.util.USEOUtilities;
import is.rebbi.wo.util.USTimestampUtilities;
import is.rebbi.wo.util.USUtilities;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.GregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.webobjects.appserver.WOContext;
import com.webobjects.eoaccess.EOUtilities;
import com.webobjects.eocontrol.EOAndQualifier;
import com.webobjects.eocontrol.EOFetchSpecification;
import com.webobjects.eocontrol.EOKeyValueQualifier;
import com.webobjects.eocontrol.EOQualifier;
import com.webobjects.eocontrol.EOSortOrdering;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSDictionary;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSRange;
import com.webobjects.foundation.NSSelector;
import com.webobjects.foundation.NSTimestamp;
import com.webobjects.foundation.NSTimestampFormatter;

import concept.SWGenericComponent;
import concept.data.SWNewsItem;
import er.extensions.eof.qualifiers.ERXInQualifier;

/**
 * The default news list
 */

public class SoloNewsNewsList extends SWGenericComponent {

	private static final Logger logger = LoggerFactory.getLogger( SoloNewsNewsList.class );

	private static final int SW_DISPLAY_EXCERPT = 2;
	private static final int SW_DISPLAY_TEXT = 3;
	private static final int SW_DISPLAY_TEXT_ONLY = 4;
	private static final String DEFAULT_SHOW_MORE_TEXT = "More...";

	private static final int DISPLAY_ALL = 0;
	private static final int DISPLAY_ONE_DATE = 1;
	private static final int DISPLAY_ONE_MONTH = 2;
	private static final int DISPLAY_ONE_YEAR = 3;

	private static final String[] ISL_DAGANOFN = { "Sunnudagur", "Mánudagur", "Þriðjudagur", "Miðvikudagur", "Fimmtudagur", "Föstudagur", "Laugardagur" };
	private static final String[] ISL_MANADARNOFN = { "janúar", "febrúar", "mars", "apríl", "maí", "júní", "júlí", "ágúst", "september", "október", "nóvember", "desember" };
	private static final String[] ISL_MANADARNOFN_STORSTAFUR = { "Janúar", "Febrúar", "Mars", "Apríl", "Maí", "Júní", "Júlí", "Ágúst", "September", "Október", "Nóvember", "Desember" };

	private static final String[] EN_DAGANOFN = { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" };
	private static final String[] EN_MANADARNOFN = { "january", "february", "march", "april", "may", "june", "july", "august", "september", "october", "november", "december" };
	private static final String[] EN_MANADARNOFN_STORSTAFUR = { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" };

	private static final NSArray<EOSortOrdering> DEFAULT_SORT_ORDERINGS = new NSArray<>( new EOSortOrdering( "date", EOSortOrdering.CompareAscending ) );

	private static NSTimestampFormatter tsFormatter = new NSTimestampFormatter();

	public SWNewsItem currentNewsItem;
	public Integer currentNewsItemNumber;
	private SWNewsItem _selectedNewsItem;

	public String selectedYear;
	public String selectedMonth;
	public String year;
	public String month;
	public int monthNo;
	public boolean forceYearDisplay = false;
	public int newsItemIndex;

	private String millidagsetning = null;

	public SoloNewsNewsList( WOContext context ) {
		super( context );
	}

	public NSArray<String> monthsToDisplay() {
		if( "en".equals( currentComponent().page().pageLanguageCode() ) ) {
			return new NSMutableArray<>( EN_MANADARNOFN_STORSTAFUR );
		}
		else {
			return new NSMutableArray<>( ISL_MANADARNOFN_STORSTAFUR );
		}
	}

	public NSArray yearsToDisplay() {
		String whereString = newsListQuerySql( null, null );
		NSArray years = EOUtilities.rawRowsForSQL( session().defaultEditingContext(), "SoloWeb", "SELECT DISTINCT EXTRACT(YEAR FROM SW_News_Item.date) FROM SW_News_Item WHERE " + whereString + " AND sw_news_item.date is not null ORDER BY 1 DESC", new NSArray( new Object[] { "year" } ) );
		NSMutableArray yearStrings = new NSMutableArray();

		NSDictionary dict;

		for( int i = 0; i < years.count(); i++ ) {
			dict = (NSDictionary)(years.objectAtIndex( i ));
			yearStrings.addObject( String.valueOf( ((Integer)dict.valueForKey( "year" )).longValue() ) );
		}

		if( yearStrings != null && yearStrings.count() > 0 ) {
			selectedYear = (String)yearStrings.objectAtIndex( 0 );
			forceYearDisplay = true;
		}

		return yearStrings;
	}

	public NSArray newslist() {
		int displayType = this.getDisplayType();

		switch (displayType) {
			case DISPLAY_ONE_DATE:
				return this.newslistForOneDate();

			case DISPLAY_ONE_MONTH:
			case DISPLAY_ONE_YEAR:
				return this.newslistForOneYear();

			case DISPLAY_ALL:
			default:
				return newslistForManyDates();
		}
	}

	public NSArray newslistForManyDates() {
		return newslistForManyDates( null, null );
	}

	public NSArray newslistForManyDates( NSTimestamp t1, NSTimestamp t2 ) {
		try {
			int i;
			String whereString = newsListQuerySql( t1, t2 );
			int itemsToFetch = itemsToShow() + itemsToSkip();
			String queryString = "";

			if( whereString == null ) {
				return null;
			}

			queryString = "SELECT id, date, heading FROM Sw_News_Item WHERE " + whereString + " ORDER BY date desc";

			String oldestOrNewestItems = stringValueForKeyWithDefaultValue( "showOldestOrNewestItems", "yngstu" );

			if( t1 == null && t2 == null && "yngstu".equals( oldestOrNewestItems ) ) {
				queryString += " LIMIT " + itemsToFetch;
			}

			// Fetch news item ids
			NSArray idDicts = EOUtilities.rawRowsForSQL( session().defaultEditingContext(), "SoloWeb", queryString, new NSArray( new Object[] { "id" } ) );

			// Get news item ids from dictionary array

			NSMutableArray ids = new NSMutableArray();
			NSDictionary dict;

			for( i = 0; i < idDicts.count(); i++ ) {
				dict = (NSDictionary)(idDicts.objectAtIndex( i ));
				// ids.addObject(String.valueOf(((Long)dict.valueForKey("id")).longValue()));
				ids.addObject( dict.valueForKey( "id" ) );
			}

			// Limit number of items if we should show oldest items, not newest
			if( ids.count() > itemsToFetch && "elstu".equals( oldestOrNewestItems ) ) {
				ids.removeObjectsInRange( new NSRange( 0, ids.count() - itemsToFetch ) );
			}

			// Fetch news items with fetched ids

			ERXInQualifier inQualifier = new ERXInQualifier( SWNewsItem.ID_KEY, ids );
			EOFetchSpecification fs = new EOFetchSpecification( SWNewsItem.ENTITY_NAME, inQualifier, new NSArray( sortOrdering() ) );

			NSMutableArray<SWNewsItem> aResult = (NSMutableArray)session().defaultEditingContext().objectsWithFetchSpecification( fs );

			if( itemsToSkip() > 0 && aResult.count() > 0 ) {
				aResult.removeObjectsInRange( new NSRange( 0, itemsToSkip() ) );
			}

			if( aResult.count() > 0 ) {
				boolean onlyOneRandom = booleanValueForKeyWithDefaultValue( "showOneRandomItem", false );
				if( onlyOneRandom ) {
					aResult = new NSMutableArray( USArrayUtilities.randomObject( aResult ) );
				}

				if( "Random".equals( stringValueForKeyWithDefaultValue( "sortKey", "" ) ) ) {
					return USArrayUtilities.randomized( aResult );
				}

				return aResult;
			}
			else {
				String itemToShowIfNoneFound = stringValueForKeyWithDefaultValue( "newsItemIfNoItemsFound", "" ).trim();
				if( StringUtilities.hasValue( itemToShowIfNoneFound ) ) {
					Integer itemNoToShowIfNoneFound = USUtilities.integerFromObject( itemToShowIfNoneFound );

					if( itemNoToShowIfNoneFound != null && itemNoToShowIfNoneFound.intValue() > 0 ) {
						SWNewsItem theItem = (SWNewsItem)USEOUtilities.objectWithPK( session().defaultEditingContext(), SWNewsItem.ENTITY_NAME, itemNoToShowIfNoneFound );

						if( theItem != null ) {
							aResult.addObject( theItem );
						}
					}
				}

				return aResult;
			}
		}
		catch( Exception e ) {
			logger.error( "Error showing news", e );
			return NSArray.emptyArray();
		}
	}

	private String newsListQuerySql( NSTimestamp t1, NSTimestamp t2 ) {
		String categoryCondition = "news_Category_ID IN(";

		String cats = currentComponent().customInfo().stringValueForKey( "categoryIDs" );
		NSArray categoryIds = NSArray.componentsSeparatedByString( cats, " " );
		if( categoryIds == null || categoryIds.count() == 0 ) {
			return null;
		}

		Enumeration iter = categoryIds.objectEnumerator();
		int i = 0;

		while( iter.hasMoreElements() ) {
			if( i > 0 ) {
				categoryCondition += ", ";
			}

			categoryCondition += (String)iter.nextElement();

			i++;
		}

		categoryCondition += ")";

		NSTimestamp currentTimestamp = USTimestampUtilities.normalizeTimestampToMidnight( new NSTimestamp() );
		SimpleDateFormat timeformat = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
		String currentTimeString = timeformat.format( currentTimestamp );

		String publishedCondition = "published=1";
		String timeInCondition = "(time_In <= '" + currentTimeString + "' OR time_In IS NULL)";
		String timeOutCondition = "(time_Out >= '" + currentTimeString + "' OR time_Out IS NULL)";

		String daysToIncludeCondition = null;

		if( stringValueForKeyWithDefaultValue( "daysToInclude", null ) != null ) {
			int daysToInclude = intValueForKeyWithDefaultValue( "daysToInclude", 0 );
			NSTimestamp targetDate = currentTimestamp.timestampByAddingGregorianUnits( 0, 0, -daysToInclude, 0, 0, 0 );
			String targetDateString = timeformat.format( targetDate );
			daysToIncludeCondition = "\"date\" > '" + targetDateString + "'";
		}

		String daysToExcludeCondition = null;

		if( stringValueForKeyWithDefaultValue( "daysToExclude", null ) != null ) {
			int daysToExclude = intValueForKeyWithDefaultValue( "daysToExclude", 0 );
			NSTimestamp targetDate = currentTimestamp.timestampByAddingGregorianUnits( 0, 0, -daysToExclude, 0, 0, 0 );
			String targetDateString = timeformat.format( targetDate );
			daysToExcludeCondition = "date < '" + targetDateString + "'";
		}

		String onlyPastFutureCondition = null;

		if( booleanValueForKeyWithDefaultValue( "showOnlyPastItems", false ) == true ) {
			onlyPastFutureCondition = "date < '" + currentTimeString + "'";
		}
		else if( booleanValueForKeyWithDefaultValue( "showOnlyFutureItems", false ) == true ) {
			onlyPastFutureCondition = "date >= '" + currentTimeString + "'";
		}

		String dateRangeCondition = null;

		if( t1 != null || t2 != null ) {
			dateRangeCondition = "";

			if( t1 != null ) {
				dateRangeCondition = "date >= '" + timeformat.format( t1 ) + "'";
			}

			if( t2 != null ) {
				if( dateRangeCondition.length() > 0 ) {
					dateRangeCondition += " AND ";
				}

				dateRangeCondition += "date <= '" + timeformat.format( t2 ) + "'";
			}
		}

		String whereString = categoryCondition + " AND " + publishedCondition + " AND " + timeInCondition + " AND " + timeOutCondition + ((daysToIncludeCondition != null) ? " AND " + daysToIncludeCondition : "") + ((daysToExcludeCondition != null) ? " AND " + daysToExcludeCondition : "") + ((onlyPastFutureCondition != null) ? " AND " + onlyPastFutureCondition : "") + ((dateRangeCondition != null) ? " AND " + dateRangeCondition : "");

		return whereString;
	}

	public NSArray newslistForOneDate() {
		try {
			int year = Integer.valueOf( context().request().stringFormValueForKey( "year" ) ).intValue();
			int month = Integer.valueOf( context().request().stringFormValueForKey( "month" ) ).intValue();
			int day = Integer.valueOf( context().request().stringFormValueForKey( "day" ) ).intValue();
			// return eventsForDayStartingAt( new NSTimestamp( year, month+1,
			// day, 0, 0, 0, null ) );
			return this.newslistForManyDates( new NSTimestamp( year, month + 1, day, 0, 0, 0, null ), null );
		}
		catch( Exception e ) {
			return NSArray.EmptyArray;
		}
	}

	public NSArray newslistForOneYear() {
		int y = 0, m = 0;
		GregorianCalendar today = new GregorianCalendar();

		// Reset the force status
		forceYearDisplay = false;

		try {
			String s = context().request().stringFormValueForKey( "year" );
			if( s == null || s.length() == 0 ) {
				if( selectedYear != null ) {
					s = selectedYear;
				}
				else {
					s = String.valueOf( today.get( GregorianCalendar.YEAR ) );
					m = today.get( GregorianCalendar.MONTH ) + 1;
				}
			}
			y = Integer.valueOf( s ).intValue();
			selectedYear = s;
		}
		catch( Exception ex ) {
			y = 0;
		}

		if( y > 0 ) {
			String month = context().request().stringFormValueForKey( "month" );
			if( !StringUtilities.hasValue( month ) && m == 0 ) {
				return this.newslistForManyDates( new NSTimestamp( y, 1, 1, 0, 0, 0, null ), new NSTimestamp( y, 12, 31, 23, 59, 59, null ) );
			}
			else {
				if( StringUtilities.hasValue( month ) ) {
					m = Integer.valueOf( month ).intValue();
				}
				GregorianCalendar d = new GregorianCalendar( y, m - 1, 1 );
				int daysInMonth = d.getActualMaximum( GregorianCalendar.DAY_OF_MONTH );
				return this.newslistForManyDates( new NSTimestamp( y, m, 1, 0, 0, 0, null ), new NSTimestamp( y, m, daysInMonth, 23, 59, 59, null ) );
			}
		}
		else {
			return this.newslistForManyDates( null, null );
		}
	}

	/**
	 * Returns all events happening on the given day.
	 */
	public NSArray eventsForDayStartingAt( NSTimestamp t ) {
		return eventsBetweenDates( t, t.timestampByAddingGregorianUnits( 0, 0, 1, 0, 0, 0 ) );
	}

	/**
	 * Returns all events happening between the two given dates.
	 */
	private NSArray eventsBetweenDates( NSTimestamp t1, NSTimestamp t2 ) {
		EOQualifier q1 = new EOKeyValueQualifier( "date", EOQualifier.QualifierOperatorGreaterThan, t1 );
		EOQualifier q2 = new EOKeyValueQualifier( "date", EOQualifier.QualifierOperatorLessThan, t2 );
		EOQualifier q = new EOAndQualifier( new NSArray( new Object[] { q1, q2 } ) );
		NSArray a = EOQualifier.filteredArrayWithQualifier( items(), q );
		return EOSortOrdering.sortedArrayUsingKeyOrderArray( a, DEFAULT_SORT_ORDERINGS );
	}

	private NSArray items() {
		NSArray _items = null;
		String newsCategories = context().request().stringFormValueForKey( "newscat" );
		if( _items == null ) {
			_items = new NSArray();
			// remove double spaces
			while( newsCategories.indexOf( "  " ) > 0 ) {
				newsCategories = newsCategories.replaceAll( "  ", " " );
			}
			NSArray newsCategoryList = NSArray.componentsSeparatedByString( newsCategories.trim(), " " );
			Enumeration e = newsCategoryList.objectEnumerator();
			while( e.hasMoreElements() ) {
				String id = (String)e.nextElement();
				EOQualifier q1 = new EOKeyValueQualifier( SWNewsItem.NEWS_CATEGORY_ID_KEY, EOQualifier.QualifierOperatorEqual, Integer.valueOf( id.trim() ) );
				EOQualifier q2 = new EOKeyValueQualifier( SWNewsItem.PUBLISHED_KEY, EOQualifier.QualifierOperatorEqual, 1 );
				EOQualifier q = new EOAndQualifier( new NSArray<>( new EOQualifier[] { q1, q2 } ) );
				EOFetchSpecification fs = new EOFetchSpecification( SWNewsItem.ENTITY_NAME, q, null );
				NSArray a = session().defaultEditingContext().objectsWithFetchSpecification( fs );
				_items = _items.arrayByAddingObjectsFromArray( a );
			}
		}

		return _items;
	}

	public boolean displayDetail() {
		boolean result = selectedNewsItem() != null;
		return result;
	}

	public int getDisplayType() {
		String year = context().request().stringFormValueForKey( "year" );
		String month = context().request().stringFormValueForKey( "month" );
		String day = context().request().stringFormValueForKey( "day" );
		boolean showMonths = customInfoBoolean( "newsReg_ShowMonths" );
		boolean showYears = customInfoBoolean( "newsReg_ShowYears" );

		int displayType;

		if( (year != null && year.length() > 0) || showYears || showMonths ) {
			if( day != null && day.length() > 0 ) {
				displayType = DISPLAY_ONE_DATE;
			}
			else if( (month != null && month.length() > 0) || showMonths ) {
				displayType = DISPLAY_ONE_MONTH;
			}
			else {
				displayType = DISPLAY_ONE_YEAR;
			}
		}
		else if( forceYearDisplay == true ) {
			displayType = DISPLAY_ONE_YEAR;
		}
		else {
			displayType = DISPLAY_ALL;
		}

		return displayType;
	}

	public SWNewsItem selectedNewsItem() {
		String detail = context().request().stringFormValueForKey( "detail" );

		if( detail != null && _selectedNewsItem == null ) {
			if( detail.indexOf( ',' ) > -1 ) {
				// Remove display page name from the id
				detail = detail.substring( 0, detail.indexOf( ',' ) );
			}
			_selectedNewsItem = (SWNewsItem)USEOUtilities.objectWithPK( session().defaultEditingContext(), SWNewsItem.ENTITY_NAME, new Integer( detail ) );
		}

		return _selectedNewsItem;
	}

	public String olderURL() {
		String url = "/page/" + currentComponent().customInfo().valueForKey( "showOlderPage" );
		return url;
	}

	public String moreURL() {

		String url = "";

		if( currentNewsItem.hasCustomMoreURL() ) {
			url = currentNewsItem.customMoreURL();
		}
		else {
			if( StringUtilities.hasValue( detailPageName() ) ) {
				url = "/page/" + detailPageName();
			}
			else if( StringUtilities.hasValue( detailPageID() ) ) {
				url = "/id/" + detailPageID();
			}
			else {
				url = currentComponent().page().pageLink();
			}

			String look = context().request().stringFormValueForKey( "look" );
			if( StringUtilities.hasValue( look ) ) {
				url += "&look=" + look;
			}

			url += "&detail=" + currentNewsItem.primaryKey();
		}

		return url;
	}

	public String showMoreText() {
		String s = currentComponent().customInfo().stringValueForKey( "showMoreText" );

		if( StringUtilities.hasValue( s ) ) {
			return s;
		}

		return SWSettings.stringForKey( "defaultNewsShowMoreString", DEFAULT_SHOW_MORE_TEXT );
	}

	private int intValueForKeyWithDefaultValue( String key, int defaultValue ) {

		Integer i = USUtilities.integerFromObject( currentComponent().customInfo().valueForKey( key ) );

		if( i != null ) {
			return i.intValue();
		}

		return defaultValue;
	}

	private String stringValueForKeyWithDefaultValue( String key, String defaultValue ) {
		String s = (String)currentComponent().customInfo().valueForKey( key );

		if( s == null ) {
			return defaultValue;
		}

		return s;
	}

	private boolean booleanValueForKeyWithDefaultValue( String key, boolean defaultValue ) {
		Object b = currentComponent().customInfo().valueForKey( key );
		boolean result = false;

		if( b != null ) {
			if( b instanceof String ) {
				String s = (String)b;
				if( s.equals( "" ) ) {
					result = defaultValue;
				}
				else if( s.equals( "false" ) ) {
					result = false;
				}
				else {
					result = true;
				}
			}
			else if( b instanceof Boolean ) {
				result = ((Boolean)b).booleanValue();
			}
		}

		return result;
	}

	private EOSortOrdering sortOrdering() {
		return new EOSortOrdering( sortKey(), sortSelector() );
	}

	private String sortKey() {
		if( "Alphabetically".equals( stringValueForKeyWithDefaultValue( "sortKey", null ) ) ) {
			return "heading";
		}

		return "date";
	}

	private String sortOrder() {
		if( "Ascending".equals( stringValueForKeyWithDefaultValue( "sortOrder", null ) ) ) {
			return "asc";
		}

		return "desc";
	}

	private NSSelector sortSelector() {
		if( "asc".equals( sortOrder() ) ) {
			return EOSortOrdering.CompareAscending;
		}

		return EOSortOrdering.CompareDescending;
	}

	private int itemsToSkip() {
		return intValueForKeyWithDefaultValue( "itemsToSkip", 0 );
	}

	private int itemsToShow() {
		return intValueForKeyWithDefaultValue( "itemsToShow", 10000 );
	}

	public boolean displayExcerpt() {
		return (intValueForKeyWithDefaultValue( "viewMode", -1 ) == SW_DISPLAY_EXCERPT);
	}

	public boolean displayText() {
		int dispMode = intValueForKeyWithDefaultValue( "viewMode", -1 );
		return (dispMode == SW_DISPLAY_TEXT || dispMode == SW_DISPLAY_TEXT_ONLY);
	}

	public boolean displayTextOnly() {
		int dispMode = intValueForKeyWithDefaultValue( "viewMode", -1 );
		return (dispMode == SW_DISPLAY_TEXT_ONLY);
	}

	public String detailPageName() {
		return stringValueForKeyWithDefaultValue( "detailPageName", null );
	}

	public String detailPageID() {
		return stringValueForKeyWithDefaultValue( "detailPageID", null );
	}

	public boolean isFirstItem() {
		return currentNewsItemNumber.intValue() == 0;
	}

	public String dateDisplayKey() {
		String key = (String)currentComponent().customInfo().valueForKey( "dateDisplay" );
		if( key == null || "".equals( key ) ) {
			key = "above";
		}
		return key;
	}

	public boolean showWeekday() {
		String f = currentComponent().customInfo().stringValueForKey( "dateFormat" );
		if( "Föstudagur 1. desember 12:34".equals( f ) ) {
			return true;
		}
		else {
			return false;
		}
	}

	@SuppressWarnings( "deprecation" )
	public String weekdayString() {
		NSTimestamp date = currentNewsItem.date();
		if( date != null ) {
			return dagsnafn( date.dayOfWeek() );
		}
		else {
			return "";
		}
	}

	public boolean showDate() {
		String f = currentComponent().customInfo().stringValueForKey( "dateFormat" );
		if( "Dagsetning".equals( f ) || "1.12.2000".equals( f ) || "Dagsetning og tími".equals( f ) || "1.12.2000 12:34".equals( f ) || "Föstudagur 1. desember 12:34".equals( f ) ) {
			return true;
		}
		else {
			return false;
		}
	}

	@SuppressWarnings( "deprecation" )
	public String dateString() {
		String requestedFormat = currentComponent().customInfo().stringValueForKey( "dateFormat" );
		NSTimestamp date = currentNewsItem.date();
		String result = null;

		if( "Dagsetning".equals( requestedFormat ) || "1.12.2000".equals( requestedFormat ) || "Dagsetning og tími".equals( requestedFormat ) || "1.12.2000 12:34".equals( requestedFormat ) ) {
			tsFormatter.setPattern( "%d.%m.%Y" );
		}
		else if( "Föstudagur 1. desember 12:34".equals( requestedFormat ) ) {
			result = date.dayOfMonth() + ". " + manadarnafn( date.monthOfYear() - 1 );
		}
		else {
			tsFormatter.setPattern( "%d.%m.%Y" );
		}

		if( result == null ) {
			result = tsFormatter.format( date );
		}

		return result;
	}

	public boolean showTime() {
		String f = currentComponent().customInfo().stringValueForKey( "dateFormat" );
		if( "Tími".equals( f ) || "12:34".equals( f ) || "Dagsetning og tími".equals( f ) || "1.12.2000 12:34".equals( f ) || "Föstudagur 1. desember 12:34".equals( f ) ) {
			return true;
		}
		else {
			return false;
		}
	}

	public String timeString() {
		tsFormatter.setPattern( "%H:%M" );
		String result = tsFormatter.format( currentNewsItem.date() );
		return result;
	}

	public boolean showAuthor() {
		String author = currentNewsItem.author();
		boolean shouldShow = USUtilities.booleanFromObject( currentComponent().customInfo().valueForKey( "showNewsAuthor" ) );
		return (shouldShow && StringUtilities.hasValue( author ));
	}

	public String monthYearSearchUrl() {
		boolean showMonths = customInfoBoolean( "newsReg_ShowMonths" );
		boolean showYears = customInfoBoolean( "newsReg_ShowYears" );
		String url = selectedPage().pageLink();

		if( showMonths ) {
			url += "&month=" + month;
		}
		if( showYears ) {
			url += "&year=" + year;
		}

		return url;
	}

	/**
	 * When in list mode, return null for no additional title but when in detail
	 * mode, return the news item title.
	 */
	@Override
	public String additionalPageTitle() {
		String additionalTitle = null;

		if( displayDetail() ) {
			additionalTitle = " - " + selectedNewsItem().heading();
		}

		return additionalTitle;
	}

	public String newsItemsID() {
		String id = "newsItems" + currentComponent().primaryKey();
		return id;
	}

	public String itemClass() {
		String klass = "item";
		if( newsItemIndex == 0 ) {
			klass += " first";
		}
		return klass;
	}

	public boolean showPerDayHeader() {
		boolean doShow = currentComponent().customInfo().booleanValueForKey( "showPerDayHeader" );

		if( doShow ) {
			NSTimestamp d = currentNewsItem.date();
			String dags = d.dayOfMonth() + "." + d.monthOfYear() + "." + d.yearOfCommonEra();
			if( d.equals( millidagsetning ) ) {
				doShow = false;
			}
		}

		return doShow;
	}

	public String perDayHeaderStr() {
		NSTimestamp d = currentNewsItem.date();
		String dayStr = dagsnafn( d.dayOfWeek() ) + " " + d.dayOfMonth() + ". " + manadarnafn( d.monthOfYear() - 1 );
		millidagsetning = dayStr;
		return dayStr;
	}

	public boolean showYearSearchAbove() {
		boolean show = customInfoBoolean( "newsReg_ShowYears" );
		boolean above = "ofan".equals( customInfoString( "searchByYearLocation", "ofan" ) );
		return show && above;
	}

	public boolean showYearSearchBelow() {
		boolean show = customInfoBoolean( "newsReg_ShowYears" );
		boolean above = "neðan".equals( customInfoString( "searchByYearLocation", "ofan" ) );
		return show && above;
	}

	public boolean showYearSearchAsPopup() {
		boolean popup = "fellilisti".equals( customInfoString( "searchByYearType", "fellilisti" ) );
		return popup;
	}

	public boolean showMonthSearchAbove() {
		boolean show = customInfoBoolean( "newsReg_ShowMonths" );
		boolean above = "ofan".equals( customInfoString( "searchByMonthLocation", "ofan" ) );
		return show && above;
	}

	public boolean showMonthSearchBelow() {
		boolean show = customInfoBoolean( "newsReg_ShowMonths" );
		boolean above = "neðan".equals( customInfoString( "searchByMonthLocation", "ofan" ) );
		return show && above;
	}

	public boolean showMonthSearchAsPopup() {
		boolean popup = "fellilisti".equals( customInfoString( "searchByMonthType", "fellilisti" ) );
		return popup;
	}

	public String yearSearchLink() {
		String url = selectedPage().pageLink() + "&year=" + year;
		return url;
	}

	public String yearSearchLinkClass() {
		String cls = "year";
		String selYear = (String)context().request().formValueForKey( "year" );
		if( selYear == null || "".equals( selYear ) ) {
			selYear = String.valueOf( (new GregorianCalendar()).get( Calendar.YEAR ) );
		}
		if( selYear.equals( year ) ) {
			cls += " selected";
		}
		return cls;
	}

	public String monthSearchLink() {
		String url = selectedPage().pageLink();
		NSArray months = monthsToDisplay();
		int selMonth = months.indexOf( month ) + 1;
		String selYear = (String)context().request().formValueForKey( "year" );

		if( !StringUtilities.hasValue( selYear ) ) {
			selYear = String.valueOf( (new GregorianCalendar()).get( GregorianCalendar.YEAR ) );
		}
		url += "&year=" + selYear + "&month=" + selMonth;

		return url;
	}

	public String monthSearchLinkClass() {
		String cls = "month";
		String selMonth = (String)context().request().formValueForKey( "month" );
		if( selMonth == null || "".equals( selMonth ) ) {
			selMonth = String.valueOf( (new GregorianCalendar()).get( Calendar.MONTH ) + 1 );
		}
		if( Integer.valueOf( selMonth ).intValue() - 1 == monthNo ) {
			cls += " selected";
		}
		return cls;
	}

	public boolean showPictureAboveTitle() {
		String picLocation = customInfoString( "imageAlignment", null );
		return "above".equals( picLocation );
	}

	public String dagsnafn( int numer ) {
		if( "en".equals( currentComponent().page().pageLanguageCode() ) ) {
			return EN_DAGANOFN[numer];
		}
		else {
			return ISL_DAGANOFN[numer];
		}
	}

	public String manadarnafn( int numer ) {
		if( "en".equals( currentComponent().page().pageLanguageCode() ) ) {
			return EN_MANADARNOFN[numer];
		}
		else {
			return ISL_MANADARNOFN[numer];
		}
	}

	public String manadarnafnStorStafur( int numer ) {
		if( "en".equals( currentComponent().page().pageLanguageCode() ) ) {
			return EN_MANADARNOFN_STORSTAFUR[numer];
		}
		else {
			return ISL_MANADARNOFN_STORSTAFUR[numer];
		}
	}
}