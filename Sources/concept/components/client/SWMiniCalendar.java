package concept.components.client;

import is.rebbi.wo.util.USArrayUtilities;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.Locale;

import com.webobjects.appserver.WOContext;
import com.webobjects.eocontrol.EOAndQualifier;
import com.webobjects.eocontrol.EOFetchSpecification;
import com.webobjects.eocontrol.EOKeyValueQualifier;
import com.webobjects.eocontrol.EOQualifier;
import com.webobjects.eocontrol.EOSortOrdering;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSTimestamp;

import concept.SWGenericComponent;
import concept.data.SWNewsItem;
import concept.data.SWPage;

public class SWMiniCalendar extends SWGenericComponent {

	private NSTimestamp now = new NSTimestamp();
	private NSArray _items;
	private static final NSArray ICELANDIC_MONTH_NAMES = initMonthNames();
	private static final NSArray ICELANDIC_DAY_HEADERS = initDayHeaders();
	public SWPage selectedPage;
	public SWNewsItem currentNewsItem;
	public String newsCategories;
	public String showCalendarPage;
	public String showEventPage;
	public Boolean isHidden;
	public Boolean showItemsBelowCalendar;

	public String currentDayHeader;
	public NSArray currentWeek;
	public String currentDay;

	public SWMiniCalendar( WOContext context ) {
		super( context );
	}

	public int selectedDay() {
		String s = context().request().stringFormValueForKey( "day" );
		return (s != null) ? new Integer( s ).intValue() : 0;
	}

	public int selectedMonth() {
		String s = context().request().stringFormValueForKey( "month" );
		return (s != null) ? new Integer( s ).intValue() : monthOfYear( now );
	}

	public int selectedYear() {
		String s = context().request().stringFormValueForKey( "year" );
		return (s != null) ? new Integer( s ).intValue() : year( now );
	}

	public int nextMonth() {
		return (selectedMonth() == 11) ? 0 : selectedMonth() + 1;
	}

	public int previousMonth() {
		return (selectedMonth() == 0) ? 11 : selectedMonth() - 1;
	}

	public int nextYear() {
		return (selectedMonth() == 11) ? selectedYear() + 1 : selectedYear();
	}

	public int previousYear() {
		return (selectedMonth() == 0) ? selectedYear() - 1 : selectedYear();
	}

	public String selectedMonthName() {
		return (String)ICELANDIC_MONTH_NAMES.objectAtIndex( selectedMonth() );
	}

	public NSArray selectedEvents() {
		try {
			return eventsForDayStartingAt( new NSTimestamp( selectedYear(), selectedMonth() + 1, selectedDay(), 0, 0, 0, null ) );
		}
		catch( Exception e ) {
			return NSArray.EmptyArray;
		}
	}

	public String detail() {
		return context().request().stringFormValueForKey( "detail" );
	}

	public int numberOfDaysInMonth() {
		Calendar cal = new GregorianCalendar( selectedYear(), selectedMonth(), 1 );
		return cal.getActualMaximum( Calendar.DAY_OF_MONTH );
	}

	public NSArray dayHeaders() {
		return ICELANDIC_DAY_HEADERS;
	}

	public NSArray days() {
		NSMutableArray a = new NSMutableArray();

		int i = firstDayOfMonth( selectedYear(), selectedMonth() );

		while( i > 1 ) {
			a.addObject( "&nbsp;" );
			i--;
		}

		i = 0;
		int j = numberOfDaysInMonth();

		while( i < j ) {
			a.addObject( String.valueOf( ++i ) );
		}

		i = 7 - (a.count() % 7);

		if( i < 7 ) {
			while( i > 0 ) {
				a.addObject( "&nbsp;" );
				i--;
			}
		}

		return a;
	}

	public NSArray weeks() {
		NSMutableArray result = new NSMutableArray();

		Enumeration e = days().objectEnumerator();
		int i = 0;
		NSMutableArray nextWeek = new NSMutableArray();

		while( e.hasMoreElements() ) {
			String s = (String)e.nextElement();
			nextWeek.addObject( s );
			i++;

			if( i == 7 ) {
				i = 0;
				result.addObject( nextWeek );
				nextWeek = new NSMutableArray();
			}
		}

		return result;
	}

	public boolean notToday() {
		try {
			return !(selectedYear() == year( now ) && selectedMonth() == monthOfYear( now ) && new Integer( currentDay ).intValue() == dayOfMonth( now ));
		}
		catch( Exception e ) {
			return true;
		}
	}

	public boolean currentDayHasNoEvents() {
		try {
			return !USArrayUtilities.hasObjects( eventsForDayStartingAt( new NSTimestamp( selectedYear(), selectedMonth() + 1, new Integer( currentDay ).intValue(), 0, 0, 0, null ) ) );
		}
		catch( Exception e ) {
			return true;
		}
	}

	private static final NSArray DEFAULT_SORT_ORDERINGS = new NSArray( new EOSortOrdering( "date", EOSortOrdering.CompareAscending ) );

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
		if( _items == null ) {
			_items = new NSArray();
			//remove double spaces
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

	private int firstDayOfMonth( int year, int month ) {
		return new GregorianCalendar( year, month, 1 ).get( GregorianCalendar.DAY_OF_WEEK );
	}

	private static int dayOfMonth( NSTimestamp ts ) {
		GregorianCalendar calendar = (GregorianCalendar)GregorianCalendar.getInstance();
		calendar.setTime( ts );
		return calendar.get( GregorianCalendar.DAY_OF_MONTH );
	}

	private static int monthOfYear( NSTimestamp ts ) {
		GregorianCalendar calendar = (GregorianCalendar)GregorianCalendar.getInstance();
		calendar.setTime( ts );
		return calendar.get( GregorianCalendar.MONTH );
	}

	private static int year( NSTimestamp ts ) {
		GregorianCalendar calendar = (GregorianCalendar)GregorianCalendar.getInstance();
		calendar.setTime( ts );
		return calendar.get( GregorianCalendar.YEAR );
	}

	private static String capitalize( String s ) {
		if( s != null && s.length() > 0 ) {
			return s.substring( 0, 1 ).toUpperCase() + s.substring( 1, s.length() );
		}

		return null;
	}

	private static NSArray initDayHeaders() {
		NSArray a = new NSArray( new DateFormatSymbols( new Locale( "is", "IS" ) ).getWeekdays() );
		NSMutableArray result = new NSMutableArray();
		Enumeration e = a.objectEnumerator();

		while( e.hasMoreElements() ) {
			String s = (String)e.nextElement();

			if( s != null && s.length() > 0 ) {
				result.addObject( s.substring( 0, 1 ).toUpperCase() );
			}
		}

		return result;
	}

	private static NSArray initMonthNames() {
		NSArray a = new NSArray( new DateFormatSymbols( new Locale( "is", "IS" ) ).getMonths() );

		NSMutableArray result = new NSMutableArray();
		Enumeration e = a.objectEnumerator();

		while( e.hasMoreElements() ) {
			String s = (String)e.nextElement();

			if( s != null && s.length() > 0 ) {
				result.addObject( capitalize( s ) );
			}
		}

		return result;
	}

	public boolean showSelectedDayBelowCalendar() {
		if( selectedDay() != 0 && showItemsBelowCalendar.booleanValue() ) {
			return true;
		}
		else {
			return false;
		}
	}
}