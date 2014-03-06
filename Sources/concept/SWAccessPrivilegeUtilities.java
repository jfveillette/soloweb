package concept;

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
import concept.util.CPLoc;

/**
 * A utility class for working with access privileges
 */

public class SWAccessPrivilegeUtilities extends Object {

	/**
	 * @return An array containing privileges specified for the specified object (does not include inherited privileges)
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

	/**
	 * Gets all privileges for the given object.
	 */
	public static NSArray<SWAccessPrivilege> privilegesForObjectAndUser( EOEnterpriseObject record, SWUser user ) {

		NSSet<SWAccessPrivilege> userSet = new NSSet<>( user.accessPrivileges() );
		NSSet<SWAccessPrivilege> groupSet = new NSSet<>( user.groupPrivileges() );
		NSSet<SWAccessPrivilege> recordSet = new NSSet<>( privilegesIncludingInheritedPrivilegesForObject( record ) );
		NSSet<SWAccessPrivilege> allSet = userSet.setByUnioningSet( groupSet ).setByIntersectingSet( recordSet );

		NSArray<SWAccessPrivilege> allArray = allSet.allObjects();

		return allArray;
	}

	public static final String PRIVILEGE_CAN_MANAGE_USERS = "canManageUsers";
	public static final String PRIVILEGE_CAN_MANAGE_CONTENT = "canManageContent";
	public static final String PRIVILEGE_CAN_MANAGE_PAGE = "canManagePage";
	public static final String PRIVILEGE_CAN_DELETE_PAGE = "canDeletePage";
	public static final String PRIVILEGE_ALLOW_TO_SEE = "allowToSee";
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

	/**
	 * Returns applicable privileges for the given object.
	 */
	public static NSMutableDictionary<String, String> privilegePairsForObject( Object object, WOContext context ) {
		if( object instanceof SWFolder ) {
			NSMutableDictionary<String, String> d = new NSMutableDictionary<>();
			d.setObjectForKey( PRIVILEGE_ALLOW_TO_SEE, CPLoc.string( "docpSeeFolders", context ) );
			d.setObjectForKey( PRIVILEGE_CAN_MANAGE_USERS, CPLoc.string( "docpManagePrivileges", context ) );
			return d;
		}

		if( object instanceof SWPage ) {
			NSMutableDictionary<String, String> d = new NSMutableDictionary<>();
			d.setObjectForKey( PRIVILEGE_CAN_DELETE_PAGE, CPLoc.string( "ppDeletePages", context ) );
			d.setObjectForKey( PRIVILEGE_CAN_MANAGE_PAGE, CPLoc.string( "ppModifyPage", context ) );
			d.setObjectForKey( PRIVILEGE_CAN_MANAGE_CONTENT, CPLoc.string( "ppModifyContent", context ) );
			d.setObjectForKey( PRIVILEGE_CAN_MANAGE_USERS, CPLoc.string( "ppControlPrivileges", context ) );
			return d;
		}

		if( object instanceof SWSite ) {
			NSMutableDictionary<String, String> d = new NSMutableDictionary<>();
			d.setObjectForKey( PRIVILEGE_ALLOW_TO_SEE, CPLoc.string( "spHasAccess", context ) );
			return d;
		}

		return null;
	}
}