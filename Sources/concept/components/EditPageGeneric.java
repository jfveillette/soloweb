package concept.components;

import is.rebbi.wo.util.USEOUtilities;
import is.rebbi.wo.util.USHTTPUtilities;

import java.text.Format;
import java.util.Locale;

import com.ibm.icu.text.DecimalFormat;
import com.ibm.icu.text.DecimalFormatSymbols;
import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOContext;
import com.webobjects.eoaccess.EOAttribute;
import com.webobjects.eoaccess.EORelationship;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSData;

import concept.ViewPage;

/**
 * Generic object editing page.
 */

public class EditPageGeneric extends ViewPage {

	public EOAttribute currentAttribute;
	public EORelationship currentRelationship;
	public String filename;

	public EditPageGeneric( WOContext context ) {
		super( context );
	}

	// FIXME: We're always using an Icelandic numerical format here.
	public Format decimalFormat() {
		DecimalFormatSymbols symbols = new DecimalFormatSymbols( new Locale("is") );
		symbols.setMonetaryGroupingSeparator( '.' );
		symbols.setMonetaryDecimalSeparator( ',' );
		return new DecimalFormat( "##.####", symbols );
	}
	/**
	 * @return All attributes.
	 */
	public NSArray<EOAttribute> attributes() {
		return USEOUtilities.attributes( selectedObject().entity() );
	}

	/**
	 * @return All relationships.
	 */
	public NSArray<EORelationship> relationships() {
		return USEOUtilities.relationships( selectedObject().entity() );
	}

	public void setCurrentAttributeValue( Object value ) {
		if( value != null ) {
			selectedObject().takeValueForKey( value, currentAttribute.name() );
		}

		if( value == null ) {
			if( !attributeIsData() ) {
				selectedObject().takeValueForKey( value, currentAttribute.name() );
			}
		}
	}

	public Object currentAttributeValue() {
		return selectedObject().valueForKey( currentAttribute.name() );
	}

	public String currentEditComponentName() {
		return null;
	}

	public boolean attributeIsInteger() {
		return USEOUtilities.attributeIsInteger( currentAttribute );
	}

	public boolean attributeIsDecimal() {
		return USEOUtilities.attributeIsDecimal( currentAttribute );
	}

	private boolean attributeIsString() {
		return USEOUtilities.attributeIsString( currentAttribute );
	}

	public boolean attributeIsShortString() {
		return attributeIsString() && !attributeIsLongString();
	}

	// FIXME: We're assuming long strings for certain field names here
	public boolean attributeIsLongString() {
		return attributeIsString() && ("text".equals( currentAttribute.name() ) || "history".equals( currentAttribute.name() ) );
	}

	public boolean attributeIsTimestamp() {
		return USEOUtilities.attributeIsTimestamp( currentAttribute );
	}

	public boolean attributeIsData() {
		return USEOUtilities.attributeIsData( currentAttribute );
	}

	public boolean attributeIsBoolean() {
		return USEOUtilities.attributeIsBoolean( currentAttribute );
	}

	public WOActionResults download() {
		return USHTTPUtilities.responseWithDataAndMimeType( "file.bin", (NSData)currentAttributeValue(), "octet/stream", true );
	}
}