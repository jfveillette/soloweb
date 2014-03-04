package concept.components.relationships;

import is.rebbi.wo.components.UniqueIDComponent;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOContext;
import com.webobjects.eoaccess.EOEntity;
import com.webobjects.eoaccess.EORelationship;
import com.webobjects.eocontrol.EOEditingContext;

import concept.Inspection;
import concept.util.HumanReadable;
import er.extensions.components.ERXComponent;
import er.extensions.eof.ERXEOControlUtilities;
import er.extensions.eof.ERXGenericRecord;

/**
 * Inspects a to-one relationship, allowing editing, addition and removal of objects.
 */

public class IMToOneRelationship extends UniqueIDComponent {

	public IMToOneRelationship( WOContext context ) {
		super( context );
	}

	@Override
	public boolean synchronizesVariablesWithBindings() {
		return false;
	}

	private ERXGenericRecord object() {
		return (ERXGenericRecord)valueForBinding( "object" );
	}

	private String key() {
		return stringValueForBinding( "key" );
	}

	private String displayKey() {
		return stringValueForBinding( "displayKey" );
	}

	private EORelationship relationship() {
		return object().entity().relationshipNamed( key() );
	}

	public ERXGenericRecord destinationObject() {
		return (ERXGenericRecord)object().valueForKey( key() );
	}

	public WOActionResults createObject() {
		EOEditingContext ec = object().editingContext();
		EOEntity destinationEntity = relationship().destinationEntity();
		String destinationEntityName = destinationEntity.name();
		ERXGenericRecord object = (ERXGenericRecord)ERXEOControlUtilities.createAndAddObjectToRelationship( ec, object(), relationship().name(), destinationEntityName, null );
		return Inspection.editObjectInContext( object, context() );
	}

	public WOActionResults removeObject() {
		object().removeObjectFromBothSidesOfRelationshipWithKey( destinationObject(), key() );
		return null;
	}

	public Object displayString() {
		Object displayString = null;

		if( displayKey() == null ) {
			displayString = HumanReadable.DefaultImplementation.toStringHuman( destinationObject() );
		}
		else {
			displayString = destinationObject().valueForKeyPath( displayKey() );
		}

		return displayString;
	}

	public WOActionResults selectObject() {
		IMRelationshipTargetSelection nextPage = pageWithName( IMRelationshipTargetSelection.class );
		nextPage.object = object();
		nextPage.key = key();
		nextPage.callingComponent = (ERXComponent)context().page();
		nextPage.resetDG();
		return nextPage;
	}
}