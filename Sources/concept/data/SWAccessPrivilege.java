package concept.data;

import is.rebbi.wo.interfaces.HasFakeRelationship;
import is.rebbi.wo.util.USUtilities;
import concept.data.auto._SWAccessPrivilege;
import er.extensions.eof.ERXGenericRecord;

/**
 * An SWAccessPrivilege stores an array of access privileges for a user/group
 */

public class SWAccessPrivilege extends _SWAccessPrivilege implements HasFakeRelationship {

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
	 * @return true if this privilege is a user privilege, rather than a group privilege.
	 */
	public boolean isUserPrivilege() {
		return user() != null;
	}

	/**
	 * @return true if this privilege is a group privilege, rather than a user privilege.
	 */
	public boolean isGroupPrivilege() {
		return group() != null;
	}

	/**
	 * @return Name of group if group privilege, name of user, if user privilege.
	 */
	public String name() {

		if( isUserPrivilege() ) {
			return user().name();
		}

		if( isGroupPrivilege() ) {
			return group().name();
		}

		return null;
	}

	/**
	 * @return The value of the named privilege
	 */
	public Integer valueForIdentifier( String identifier ) {
		SWAccessPrivilegeValue apv = privilegeValueForIdentifier( identifier );

		if( apv != null ) {
			return apv.value();
		}

		return null;
	}

	/**
	 * Set the value for the named privilege
	 */
	public void setValueForIdentifier( Integer value, String identifier ) {

		SWAccessPrivilegeValue apv = privilegeValueForIdentifier( identifier );

		if( apv == null && USUtilities.numberIsTrue( value ) ) {
			apv = new SWAccessPrivilegeValue();
			editingContext().insertObject( apv );
			apv.addObjectToBothSidesOfRelationshipWithKey( this, SWAccessPrivilegeValue.ACCESS_PRIVILEGE_KEY );
			apv.setIdentifier( identifier );
			apv.setValue( value );
		}

		if( apv != null && !USUtilities.numberIsTrue( value ) ) {
			editingContext().deleteObject( apv );
		}
	}

	/**
	 * @return The SWAccessPrivilegeValue object for the named privilege
	 */
	private SWAccessPrivilegeValue privilegeValueForIdentifier( String identifier ) {

		if( identifier.equals( null ) ) {
			return null;
		}

		for( SWAccessPrivilegeValue apv : values()  ) {
			if( identifier.equals( apv.identifier() ) ) {
				return apv;
			}
		}

		return null;
	}
}