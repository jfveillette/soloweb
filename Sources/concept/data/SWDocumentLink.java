package concept.data;

import is.rebbi.wo.interfaces.HasFakeRelationship;
import is.rebbi.wo.util.USUtilities;
import er.extensions.eof.ERXGenericRecord;

/**
 * Links an SWDocument to other objects in the database.
 */

public class SWDocumentLink extends concept.data.auto._SWDocumentLink implements HasFakeRelationship {

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

	/**
	 * @return True if this link represents a primary document.
	 */
	public boolean isPrimary() {
		return USUtilities.booleanFromObject( primaryMarker() );
	}

	/**
	 * Set the primary marker for this link.
	 */
	public void setPrimary( boolean shouldBePrimary ) {
		if( shouldBePrimary && !isPrimary() ) {
			setPrimaryMarker( 1 );
		}

		if( !shouldBePrimary && isPrimary() ) {
			setPrimaryMarker( null );
		}
	}
}