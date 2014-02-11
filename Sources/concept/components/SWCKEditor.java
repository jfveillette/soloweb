package concept.components;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOContext;
import com.webobjects.appserver.WOResponse;

import concept.Concept;
import er.ajax.AjaxUtils;
import er.extensions.components.ERXStatelessComponent;
import er.extensions.eof.ERXGenericRecord;

/**
 * CKEditor
 */

public class SWCKEditor extends ERXStatelessComponent {

	public ERXGenericRecord object() {
		return (ERXGenericRecord)valueForBinding( "object" );
	}

	public String keyPath() {
		return (String)valueForBinding( "keyPath" );
	}

	public SWCKEditor( WOContext context ) {
		super( context );
	}

	public String stringValue() {
		return (String)object().valueForKeyPath( keyPath() );
	}

	public void setStringValue( String value ) {
		;
	}

	@Override
	public void appendToResponse( WOResponse r, WOContext c ) {
		super.appendToResponse( r, c );
		AjaxUtils.addScriptResourceInHead( context(), r, Concept.sw().frameworkBundleName(), "ckeditor/ckeditor.js" );
	}

	public WOActionResults save() {
		String value = context().request().stringFormValueForKey( "WOIsmapCoords" );
		object().takeValueForKeyPath( value, keyPath() );
		object().editingContext().saveChanges();
		return null;
	}
}