package concept.components;

import is.rebbi.wo.util.USEOUtilities;
import is.rebbi.wo.util.USHTTPUtilities;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOContext;
import com.webobjects.eoaccess.EOAttribute;
import com.webobjects.eoaccess.EORelationship;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSData;

import concept.ViewPage;
import concept.definitions.AttributeViewDefinition;
import concept.util.HumanReadable;
import er.extensions.eof.ERXGenericRecord;

/**
 * Generic object editing interface.
 */

public class ViewPageGeneric extends ViewPage {

	public EOAttribute currentAttribute;
	public EORelationship currentRelationship;
	public String filename;

	public ERXGenericRecord currentObject;

	public ViewPageGeneric( WOContext context ) {
		super( context );
	}

	public NSArray<EOAttribute> attributes() {
		return USEOUtilities.attributes( selectedObject().entity() );
	}

	public NSArray<EORelationship> relationships() {
		return USEOUtilities.relationships( selectedObject().entity() );
	}

	public Object currentAttributeValue() {
		return selectedObject().valueForKey( currentAttribute.name() );
	}

	public Object currentRelationshipValue() {
		return selectedObject().valueForKey( currentRelationship.name() );
	}

	public Object currentRelationshipValueHumanReadable() {
		ERXGenericRecord eo = (ERXGenericRecord)selectedObject().valueForKey( currentRelationship.name() );

		if( eo != null ) {
			return HumanReadable.DefaultImplementation.toStringHuman( eo );
		}

		return null;
	}

	public String currentObjectHumanReadable() {
		return HumanReadable.DefaultImplementation.toStringHuman( currentObject );
	}

	public boolean attributeIsNumeric() {
		return USEOUtilities.attributeIsNumeric( currentAttribute );
	}

	public boolean attributeIsString() {
		return USEOUtilities.attributeIsString( currentAttribute );
	}

	public boolean attributeIsTimestamp() {
		return USEOUtilities.attributeIsTimestamp( currentAttribute );
	}

	public boolean attributeIsData() {
		return USEOUtilities.attributeIsData( currentAttribute );
	}

	public WOActionResults download() {
		return USHTTPUtilities.responseWithDataAndMimeType( "file.bin", (NSData)currentAttributeValue(), "octet/stream", true );
	}

	public String currentAttributeName() {
		AttributeViewDefinition meta = viewDefinition().attributeNamed( currentAttribute.name() );

		if( meta != null ) {
			return meta.icelandicName();
		}

		return currentAttribute.name();
	}

	public String currentRelationshipName() {
		AttributeViewDefinition meta = viewDefinition().attributeNamed( currentRelationship.name() );

		if( meta != null ) {
			return meta.icelandicName();
		}

		return currentRelationship.name();
	}

	public boolean showOriginalName() {
		return !currentAttributeName().equals( currentAttribute.name() );
	}
}