package concept.components.relationships;

import is.rebbi.wo.util.USEOUtilities;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOContext;
import com.webobjects.eoaccess.EODatabaseDataSource;
import com.webobjects.eoaccess.EORelationship;
import com.webobjects.foundation.NSArray;

import concept.definitions.AttributeViewDefinition;
import concept.definitions.EntityViewDefinition;
import er.extensions.appserver.ERXDisplayGroup;
import er.extensions.batching.ERXBatchingDisplayGroup;
import er.extensions.components.ERXComponent;
import er.extensions.eof.ERXEOControlUtilities;
import er.extensions.eof.ERXGenericRecord;

public class IMRelationshipTargetSelection extends ERXComponent {

	public ERXDisplayGroup dg;
	public String searchString;

	public ERXComponent callingComponent;
	public ERXGenericRecord currentObject;
	public ERXGenericRecord object;
	public String key;
	public AttributeViewDefinition currentAttribute;

	public IMRelationshipTargetSelection( WOContext context ) {
		super( context );
	}

	public EORelationship relationship() {
		return object.entity().relationshipNamed( key );
	}

	public NSArray<ERXGenericRecord> objects() {
		return ERXEOControlUtilities.objectsWithQualifier( object.editingContext(), relationship().destinationEntity().name(), null, null, false );
	}

	public WOActionResults cancel() {
		callingComponent.ensureAwakeInContext( context() );
		return callingComponent;
	}

	public WOActionResults selectObject() {
		Object previousObject = object.valueForKey( key );

		if( !relationship().isToMany() && previousObject != null ) {
			object.removeObjectFromBothSidesOfRelationshipWithKey( (ERXGenericRecord)previousObject, key );
		}

		object.addObjectToBothSidesOfRelationshipWithKey( currentObject, key );
		return callingComponent;
	}

	public void resetDG() {
		dg = new ERXBatchingDisplayGroup();
		dg.setDataSource( new EODatabaseDataSource( object.editingContext(), viewDefinition().entity().name() ) );
		dg.setNumberOfObjectsPerBatch( 100 );
		dg.setSortOrderings( viewDefinition().defaultSortOrderings() );

		if( searchString != null ) {
			dg.setQualifier( USEOUtilities.allQualifier( searchString, viewDefinition().entity() ) );
		}

		dg.qualifyDataSource();
	}

	public Object currentValue() {
		return currentObject.valueForKeyPath( currentAttribute.name() );
	}

	public EntityViewDefinition viewDefinition() {
		return EntityViewDefinition.get( relationship().destinationEntity() );
	}
}