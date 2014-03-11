package concept.components;

import is.rebbi.wo.definitions.AttributeViewDefinition;
import is.rebbi.wo.definitions.EntityViewDefinition;

import com.webobjects.appserver.WOContext;
import com.webobjects.eoaccess.ERXEntity;

import er.extensions.components.ERXNonSynchronizingComponent;
import er.extensions.eof.ERXGenericRecord;

/**
 * Shows a label accompanying fields of information.
 */

public class IMLabel extends ERXNonSynchronizingComponent {

	private AttributeViewDefinition _viewDefinition;

	public IMLabel( WOContext context ) {
		super( context );
	}

	public ERXGenericRecord object() {
		return (ERXGenericRecord)valueForBinding( "object" );
	}

	public ERXEntity entity() {
		ERXEntity entity = (ERXEntity)valueForBinding( "entity" );

		if( entity == null ) {
			entity = (ERXEntity)object().entity();
		}

		return entity;
	}

	public String key() {
		String key = stringValueForBinding( "key" );
		return key;
	}

	public EntityViewDefinition viewDefinition() {
		return EntityViewDefinition.get( entity() );
	}

	private AttributeViewDefinition meta() {
		if( _viewDefinition == null ) {
			_viewDefinition = viewDefinition().attributeNamed( key() );
		}

		return _viewDefinition;
	}

	public String displayName() {
		String result = null;

		if( meta() != null ) {
			return meta().icelandicName();
		}

		if( result == null ) {
			result = key();
		}

		return result;
	}

	public String text() {
		if( meta() != null ) {
			return meta().text();
		}

		return null;
	}
}