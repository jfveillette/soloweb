package concept.data;

import is.rebbi.wo.interfaces.HasFakeRelationship;
import is.rebbi.wo.interfaces.TimeStamped;
import er.extensions.eof.ERXGenericRecord;

public class SWTagging extends concept.data.auto._SWTagging implements HasFakeRelationship, TimeStamped {

	/**
	 * The record this object targets.
	 */
	private ERXGenericRecord _targetObject;

	/**
	 * @return The record this object targets.
	 */
	public Object targetObject() {
		if( _targetObject == null ) {
			_targetObject = HasFakeRelationship.Util.targetObject( this );
		}

		return _targetObject;
	}
}