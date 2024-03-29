package concept.data;

import is.rebbi.wo.definitions.EntityViewDefinition;
import is.rebbi.wo.interfaces.HasFakeRelationship;
import is.rebbi.wo.util.HumanReadable;
import is.rebbi.wo.util.USEOUtilities;

import com.webobjects.foundation.NSDictionary;
import com.webobjects.foundation.NSMutableDictionary;
import com.webobjects.foundation.NSPropertyListSerialization;

import concept.data.auto._SWTransaction;
import er.extensions.eof.ERXGenericRecord;

/**
 * An EOF transaction.
 */

public class SWTransaction extends _SWTransaction implements HasFakeRelationship, HumanReadable {

	public static final String ACTION_INSERT = "I";
	public static final String ACTION_DELETE = "D";
	public static final String ACTION_UPDATE = "U";

	/**
	 * The record this object refers to.
	 */
	private ERXGenericRecord _targetObject;

	private static NSDictionary<String, String> _actionTypes;

	private static NSDictionary<String, String> actionTypes() {
		if( _actionTypes == null ) {
			_actionTypes = new NSMutableDictionary<>();
			_actionTypes.put( ACTION_DELETE, "Eyða" );
			_actionTypes.put( ACTION_INSERT, "Búa til" );
			_actionTypes.put( ACTION_UPDATE, "Uppfæra" );
		}

		return _actionTypes;
	}

	/**
	 * @return The object that this transaction refers to.
	 */
	public ERXGenericRecord targetObject() {
		if( _targetObject == null ) {
			_targetObject = HasFakeRelationship.Util.targetObject( this );
		}

		return _targetObject;
	}

	public void setTargetObject( ERXGenericRecord eo ) {
		_targetObject = eo;

		String primaryKey = eo.primaryKey();

		if( primaryKey == null ) {
			primaryKey = eo.permanentGlobalID().keyValues()[0].toString();
		}

		setTargetEntityName( eo.entityName() );
		setTargetID( primaryKey );
	}

	/**
	 * Localized description of the entity this record applies to.
	 */
	public String localizedDescriptionOfAction() {
		return actionTypes().get( action() );
	}

	public NSDictionary<String, Object> beforeDictionary() {
		return (NSDictionary)NSPropertyListSerialization.propertyListFromString( before() );
	}

	public NSDictionary<String, Object> afterDictionary() {
		return (NSDictionary)NSPropertyListSerialization.propertyListFromString( after() );
	}

	@Override
	public String toStringHuman() {
		return entityName() + ": " + date();
	}

	public EntityViewDefinition viewDefinition() {
		return EntityViewDefinition.get( targetEntityName() );
	}

	public String localizedDescriptionOfEntity() {
		String s = viewDefinition().icelandicName();

		if( s == null ) {
			s = targetEntityName();
		}

		return s;
	}

	@Override
	public SWUser user() {
		return USEOUtilities.objectWithPK( editingContext(), SWUser.ENTITY_NAME, userID() );
	}

	@Override
	public void setUser( SWUser value ) {
		if( value == null ) {
			setUserID( null );
		}
		else {
			setUserID( Integer.valueOf( value.primaryKey() ) );
		}
	}
}