package concept;

import is.rebbi.wo.interfaces.SWInheritsPrivileges;
import is.rebbi.wo.util.USArrayUtilities;
import is.rebbi.wo.util.USUtilities;

import java.util.Enumeration;

import com.webobjects.eocontrol.EOAndQualifier;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOEnterpriseObject;
import com.webobjects.eocontrol.EOFetchSpecification;
import com.webobjects.eocontrol.EOKeyValueQualifier;
import com.webobjects.eocontrol.EOQualifier;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableArray;

import concept.data.SWAccessPrivilege;
import concept.data.SWUser;
import er.extensions.eof.ERXGenericRecord;

/**
 * A utility class for working with access privileges
 */

public class SWAccessPrivilegeUtilities extends Object {

	/**
	 * @return An array containing privileges specified for the specified object (does not include inherited privileges)
	 */
	public static NSArray<SWAccessPrivilege> privilegesForObject( EOEnterpriseObject record ) {
		EOEditingContext ec = record.editingContext();

		EOQualifier q1 = new EOKeyValueQualifier( SWAccessPrivilege.TARGET_ENTITY_NAME_KEY, EOQualifier.QualifierOperatorEqual, record.entityName() );
		EOQualifier q2 = new EOKeyValueQualifier( SWAccessPrivilege.TARGET_ID_KEY, EOQualifier.QualifierOperatorEqual, ((ERXGenericRecord)record).primaryKey() );
		NSArray<EOQualifier> qualArray = new NSArray<>( new EOQualifier[] { q1, q2 } );
		EOQualifier andQual = new EOAndQualifier( qualArray );
		EOFetchSpecification fs = new EOFetchSpecification( SWAccessPrivilege.ENTITY_NAME, andQual, null );

		return ec.objectsWithFetchSpecification( fs );
	}

	/**
	 * @return An array containing all inherited privileges for the specified object
	 */
	public static NSArray<SWAccessPrivilege> inheritedPrivilegesForObject( SWInheritsPrivileges record ) {

		EOEnterpriseObject parent = record.inheritsPrivilegesFrom();

		if( parent == null ) {
			return NSArray.emptyArray();
		}

		NSMutableArray<SWAccessPrivilege> allParentPrivileges = privilegesIncludingInheritedPrivilegesForObject( parent ).mutableClone();
		Enumeration<SWAccessPrivilege> e = allParentPrivileges.objectEnumerator();

		while( e.hasMoreElements() ) {
			SWAccessPrivilege nextPrivilege = e.nextElement();

			if( nextPrivilege != null ) {
				if( USUtilities.numberIsTrue( nextPrivilege.notInherited() ) ) {
					allParentPrivileges.removeObject( nextPrivilege );
				}
			}
		}

		return allParentPrivileges;
	}

	/**
	 * @return An array containing all privileges and inherited privileges for the specified object
	 */
	public static NSArray<SWAccessPrivilege> privilegesIncludingInheritedPrivilegesForObject( EOEnterpriseObject record ) {

		NSArray<SWAccessPrivilege> localPrivileges = SWAccessPrivilegeUtilities.privilegesForObject( record );

		if( record instanceof SWInheritsPrivileges ) {
			if( USUtilities.numberIsTrue( ((SWInheritsPrivileges)record).inheritsPrivileges() ) ) {
				return localPrivileges.arrayByAddingObjectsFromArray( inheritedPrivilegesForObject( ((SWInheritsPrivileges)record) ) );
			}
		}

		return localPrivileges;
	}

	public static <E extends EOEnterpriseObject> NSArray<E> filteredArrayWithUserAndPrivilege( NSArray<E> array, SWUser user, String privilegeName ) {

		// FIXME: This should not be like this. (superuser is always null)

		if( USArrayUtilities.hasObjects( array ) ) {
			Enumeration<E> e = array.objectEnumerator();
			NSMutableArray<E> b = new NSMutableArray<>();

			while( e.hasMoreElements() ) {
				E nextCategory = e.nextElement();

				if( user == null || user.hasPrivilegeFor( nextCategory, privilegeName ) ) {
					b.addObject( nextCategory );
				}
			}

			return b;
		}

		return NSArray.emptyArray();
	}
}