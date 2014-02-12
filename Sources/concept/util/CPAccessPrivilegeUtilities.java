package concept.util;

import is.rebbi.wo.interfaces.SWInheritsPrivileges;
import is.rebbi.wo.util.SWSettings;
import is.rebbi.wo.util.USArrayUtilities;
import is.rebbi.wo.util.USUtilities;

import java.util.Enumeration;
import java.util.Objects;

import com.webobjects.appserver.WOContext;
import com.webobjects.eocontrol.EOAndQualifier;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOEnterpriseObject;
import com.webobjects.eocontrol.EOFetchSpecification;
import com.webobjects.eocontrol.EOKeyGlobalID;
import com.webobjects.eocontrol.EOKeyValueQualifier;
import com.webobjects.eocontrol.EOQualifier;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSMutableDictionary;
import com.webobjects.foundation.NSSet;

import concept.data.SWAccessPrivilege;
import concept.data.SWFolder;
import concept.data.SWPage;
import concept.data.SWSite;
import concept.data.SWUser;

/**
 * A utility class for working with access privileges
 */

public class CPAccessPrivilegeUtilities extends Object {

	/**
	 * Gets all privileges for the given object.
	 */
	public static NSArray<SWAccessPrivilege> privilegesForObjectAndUser( EOEnterpriseObject record, SWUser user ) {

		NSSet<SWAccessPrivilege> userSet = new NSSet<>( user.accessPrivileges() );
		NSSet<SWAccessPrivilege> groupSet = new NSSet<>( user.groupPrivileges() );
		NSSet<SWAccessPrivilege> recordSet = new NSSet<>( CPAccessPrivilegeUtilities.privilegesIncludingInheritedPrivilegesForObject( record ) );
		NSSet<SWAccessPrivilege> allSet = userSet.setByUnioningSet( groupSet ).setByIntersectingSet( recordSet );

		NSArray<SWAccessPrivilege> allArray = allSet.allObjects();

		return allArray;
	}

	/**
	 * Returns an array containing privileges specified for the specified object (does not include inherited privileges)
	 */
	public static NSArray<SWAccessPrivilege> privilegesForObject( EOEnterpriseObject record ) {
		EOEditingContext ec = record.editingContext();
		String primaryKey = Objects.toString( ((EOKeyGlobalID)ec.globalIDForObject( record )).keyValuesArray().objectAtIndex( 0 ) );

		EOQualifier q1 = new EOKeyValueQualifier( SWAccessPrivilege.TARGET_ENTITY_NAME_KEY, EOQualifier.QualifierOperatorEqual, record.entityName() );
		EOQualifier q2 = new EOKeyValueQualifier( SWAccessPrivilege.TARGET_ID_KEY, EOQualifier.QualifierOperatorEqual, primaryKey );
		NSArray<EOQualifier> qualArray = new NSArray<>( new EOQualifier[] { q1, q2 } );
		EOQualifier andQual = new EOAndQualifier( qualArray );
		EOFetchSpecification fs = new EOFetchSpecification( SWAccessPrivilege.ENTITY_NAME, andQual, null );

		return ec.objectsWithFetchSpecification( fs );
	}

	/**
	 * Returns an array containing all inherited privileges for the specified object
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
	 * Returns an array containing all privileges and inherited privileges for the specified object
	 */
	public static NSArray<SWAccessPrivilege> privilegesIncludingInheritedPrivilegesForObject( EOEnterpriseObject record ) {

		NSArray<SWAccessPrivilege> localPrivileges = CPAccessPrivilegeUtilities.privilegesForObject( record );

		if( record instanceof SWInheritsPrivileges ) {
			if( USUtilities.numberIsTrue( ((SWInheritsPrivileges)record).inheritsPrivileges() ) ) {
				return localPrivileges.arrayByAddingObjectsFromArray( inheritedPrivilegesForObject( ((SWInheritsPrivileges)record) ) );
			}
		}

		return localPrivileges;
	}

	/**
	 * TODO: This should not be like this. (superuser is always null)
	 */
	public static NSArray filteredArrayWithUserAndPrivilege( NSArray a, SWUser aUser, String privilegeName ) {

		if( USArrayUtilities.hasObjects( a ) ) {
			Enumeration e = a.objectEnumerator();
			NSMutableArray b = new NSMutableArray();

			while( e.hasMoreElements() ) {
				EOEnterpriseObject nextCategory = (EOEnterpriseObject)e.nextElement();

				if( aUser == null || aUser.hasPrivilegeFor( nextCategory, CPAccessPrivilegeUtilities.PRIVILEGE_ALLOW_TO_SEE ) ) {
					b.addObject( nextCategory );
				}
			}

			return b;
		}

		return NSArray.EmptyArray;
	}

	/**
	 * Returns applicable privileges for the given object.
	 */
	public static NSMutableDictionary<String, String> privilegePairsForObject( Object object, WOContext context ) {
		if( object instanceof SWFolder ) {
			NSMutableDictionary<String, String> d = new NSMutableDictionary<>();
			d.setObjectForKey( CPAccessPrivilegeUtilities.PRIVILEGE_ALLOW_TO_SEE, CPLoc.string( "docpSeeFolders", context ) );
			d.setObjectForKey( CPAccessPrivilegeUtilities.PRIVILEGE_CAN_MANAGE_USERS, CPLoc.string( "docpManagePrivileges", context ) );
			return d;
		}

		if( object instanceof SWPage ) {
			NSMutableDictionary<String, String> d = new NSMutableDictionary<>();
			d.setObjectForKey( CPAccessPrivilegeUtilities.PRIVILEGE_CAN_DELETE_PAGE, CPLoc.string( "ppDeletePages", context ) );
			d.setObjectForKey( CPAccessPrivilegeUtilities.PRIVILEGE_CAN_MANAGE_PAGE, CPLoc.string( "ppModifyPage", context ) );
			d.setObjectForKey( CPAccessPrivilegeUtilities.PRIVILEGE_CAN_MANAGE_CONTENT, CPLoc.string( "ppModifyContent", context ) );
			d.setObjectForKey( CPAccessPrivilegeUtilities.PRIVILEGE_CAN_MANAGE_USERS, CPLoc.string( "ppControlPrivileges", context ) );
			return d;
		}

		if( object instanceof SWSite ) {
			NSMutableDictionary<String, String> d = new NSMutableDictionary<>();
			d.setObjectForKey( CPAccessPrivilegeUtilities.PRIVILEGE_ALLOW_TO_SEE, CPLoc.string( "spHasAccess", context ) );
			return d;
		}

		return null;
	}

	/**
	 * Indicates if the current user is granted the specified privilege
	 *
	 * @param privilegeName The name of the privilege
	 */
	public static boolean hasPrivilegeFor( SWUser user, EOEnterpriseObject record, String privilegeName ) {

		if( !SWSettings.privilegesEnabled() ) {
			return true;
		}

		if( user.isAdministrator() ) {
			return true;
		}

		Enumeration<SWAccessPrivilege> e = privilegesForObjectAndUser( record, user ).objectEnumerator();

		while( e.hasMoreElements() ) {
			SWAccessPrivilege p = e.nextElement();

			if( USUtilities.numberIsTrue( p.valueForIdentifier( privilegeName ) ) ) {
				return true;
			}
		}

		return false;
	}

	public static final String PRIVILEGE_CAN_MANAGE_USERS = "canManageUsers";
	public static final String PRIVILEGE_CAN_MANAGE_CONTENT = "canManageContent";
	public static final String PRIVILEGE_CAN_MANAGE_PAGE = "canManagePage";
	public static final String PRIVILEGE_CAN_DELETE_PAGE = "canDeletePage";
	public static final String PRIVILEGE_ALLOW_TO_SEE = "allowToSee";
}