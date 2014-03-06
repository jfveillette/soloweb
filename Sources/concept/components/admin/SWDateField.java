package concept.components.admin;


import com.webobjects.appserver.WOComponent;
import com.webobjects.appserver.WOContext;
import com.webobjects.foundation.NSKeyValueCoding;
import com.webobjects.foundation.NSTimestamp;
import com.webobjects.foundation.NSTimestampFormatter;

import concept.util.SWLoc;

public class SWDateField extends WOComponent {

	public static NSTimestampFormatter formatter = new NSTimestampFormatter( "%d.%m.%Y, %H:%M" );
	public NSKeyValueCoding record;
	public String key;

	public SWDateField( WOContext context ) {
		super( context );
	}

	public WOComponent selectDate() {
		SWDatePicker nextPage = (SWDatePicker)pageWithName( "SWDatePicker" );

		nextPage.record = record;
		nextPage.key = key;
		nextPage.componentToReturn = context().page();
		nextPage.setTimestamp( timestamp() );

		return nextPage;
	}

	public NSTimestamp timestamp() {
		return (NSTimestamp)record.valueForKey( key );
	}

	public String dateAsString() {

		if( timestamp() == null ) {
			return SWLoc.string( "datePickerNoTimeSetString", session() );
		}

		return formatter.format( timestamp() );
	}
}