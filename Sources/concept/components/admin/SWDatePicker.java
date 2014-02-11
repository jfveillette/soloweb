package concept.components.admin;

import is.rebbi.core.util.StringUtilities;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import com.webobjects.appserver.WOComponent;
import com.webobjects.appserver.WOContext;
import com.webobjects.eocontrol.EOEnterpriseObject;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSKeyValueCoding;
import com.webobjects.foundation.NSKeyValueCodingAdditions;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSTimestamp;

public class SWDatePicker extends WOComponent {

	private GregorianCalendar calendar;
	public NSKeyValueCoding record;
	public WOComponent componentToReturn;
	public String key;

	public SWDatePicker( WOContext context ) {
		super( context );
	}

	public NSTimestamp timestamp() {
		return (calendar != null) ? new NSTimestamp( calendar.getTime() ) : null;
	}

	public void setTimestamp( NSTimestamp newStamp ) {
		if( newStamp != null ) {
			initializeCalendar();
			calendar.setTime( newStamp );
		}
	}

	public GregorianCalendar calendar() {
		return calendar;
	}

	private void initializeCalendar() {
		setCalendar( (GregorianCalendar)GregorianCalendar.getInstance( Locale.getDefault() ) );
		setHour( "0" );
		setMinute( "0" );
		setSecond( "0" );
	}

	public void setCalendar( GregorianCalendar g ) {
		calendar = g;
	}

	public String second() {
		return calendarValueForKey( Calendar.SECOND );
	}

	public String minute() {
		return calendarValueForKey( Calendar.MINUTE );
	}

	public String hour() {
		return calendarValueForKey( Calendar.HOUR_OF_DAY );
	}

	public String day() {
		return calendarValueForKey( Calendar.DAY_OF_MONTH );
	}

	public String month() {
		String initialValue = calendarValueForKey( Calendar.MONTH );

		if( initialValue != null ) {
			return String.valueOf( Integer.parseInt( initialValue ) + 1 );
		}
		else {
			return null;
		}
	}

	public void setMonth( String value ) {
		if( value != null ) {
			String finalValue = String.valueOf( Integer.parseInt( value ) - 1 );
			calendarSetValueForKey( finalValue, Calendar.MONTH );
		}
	}

	public String year() {
		return calendarValueForKey( Calendar.YEAR );
	}

	public void setSecond( String value ) {
		calendarSetValueForKey( value, Calendar.SECOND );
	}

	public void setMinute( String value ) {
		calendarSetValueForKey( value, Calendar.MINUTE );
	}

	public void setHour( String value ) {
		calendarSetValueForKey( value, Calendar.HOUR_OF_DAY );
	}

	public void setDay( String value ) {
		calendarSetValueForKey( value, Calendar.DAY_OF_MONTH );
	}

	public void setYear( String value ) {
		calendarSetValueForKey( value, Calendar.YEAR );
	}

	public String calendarValueForKey( int key ) {

		if( calendar() != null ) {
			return String.valueOf( calendar().get( key ) );
		}

		return null;
	}

	public void calendarSetValueForKey( String value, int key ) {

		if( StringUtilities.hasValue( value ) ) {

			if( calendar() == null ) {
				initializeCalendar();
			}

			calendar().set( key, new Integer( value ).intValue() );
		}
	}

	public NSArray days() {
		return arrayWithNumberStringsInRange( 1, 31 );
	}

	public NSArray months() {
		return arrayWithNumberStringsInRange( 1, 12 );
	}

	public NSArray years() {
		return arrayWithNumberStringsInRange( 2000, 2020 );
	}

	public NSArray hours() {
		return arrayWithNumberStringsInRange( 0, 23 );
	}

	public NSArray minutes() {
		return arrayWithNumberStringsInRange( 0, 59 );
	}

	public NSArray arrayWithNumberStringsInRange( int start, int end ) {
		int i = start;
		NSMutableArray resultArray = new NSMutableArray();

		while( i < (end + 1) ) {
			resultArray.addObject( String.valueOf( i++ ) );
		}

		return resultArray.immutableClone();
	}

	public WOComponent nullifyCalendar() {
		setCalendar( null );
		record.takeValueForKey( null, key );

		componentToReturn.ensureAwakeInContext( context() );
		return componentToReturn;
	}

	public WOComponent submit() {
		if( record instanceof EOEnterpriseObject ) {
			((EOEnterpriseObject)record).takeValueForKeyPath( timestamp(), key );
			((EOEnterpriseObject)record).editingContext().saveChanges();
		}
		else if( record instanceof NSKeyValueCodingAdditions ) {
			((NSKeyValueCodingAdditions)record).takeValueForKeyPath( timestamp(), key );
		}
		else {
			record.takeValueForKey( timestamp(), key );
		}

		componentToReturn.ensureAwakeInContext( context() );

		return componentToReturn;

	}
}