package concept.components.admin;

import is.rebbi.wo.interfaces.SWInheritsPrivileges;
import is.rebbi.wo.util.SWSettings;

import java.util.Enumeration;

import com.webobjects.appserver.WOComponent;
import com.webobjects.appserver.WOContext;
import com.webobjects.eocontrol.EOEnterpriseObject;
import com.webobjects.eocontrol.EOFetchSpecification;
import com.webobjects.eocontrol.EOKeyGlobalID;
import com.webobjects.eocontrol.EOSortOrdering;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSDictionary;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSMutableDictionary;

import concept.SWAccessPrivilegeUtilities;
import concept.SWAdminComponent;
import concept.data.SWAccessPrivilege;

/**
 * SWAssignPrivileges2 takes a single EOEnterpriseObject as a binding, and allows the user to set access privileges for it
 */

public class SWAssignPrivileges2 extends SWAdminComponent {

	/**
	 * The current index of the repetition in the table. Used to calculate the bgcolor for the table row.
	 */
	public int tableIndex;

	/**
	 * The SWAccessPrivilege currently being iterated over.
	 */
	public SWAccessPrivilege currentPrivilege;

	/**
	 * The current user or group being iterated over in the user/group pop-up-menu
	 */
	public EOEnterpriseObject currentObject;

	/**
	 * The EOEnterpriseObject currently being worked on
	 */
	public EOEnterpriseObject record;

	/**
	 * The selected user or group in the user/group pop-up-menu
	 */
	public EOEnterpriseObject selectedObject;

	/**
	 * Indicates if the new privilege should be inherited to the select page's subpages
	 */
	public Integer notInherited;

	/**
	 * Name of the current Privilege value being displayed
	 */
	public String currentFieldHeader;

	/**
	 * Identifier of the current Privilege value being displayed
	 */
	public String currentFieldName;

	/**
	 * The pairs of privilege names and identifiers passed to the component
	 */
	public NSDictionary privilegePairs;

	/**
	 * Indicates if the privilegePairs array has been populated or not
	 */
	public boolean hasInitializedPairs = false;

	/**
	 * The array of privilege value headers
	 */
	public NSMutableArray fieldHeaders = new NSMutableArray();

	/**
	 * The array of privilege value headers
	 */
	public boolean inheritance = false;

	/**
	 * The array of privilege value identifiers
	 */
	public NSMutableArray fieldNames = new NSMutableArray();

	/**
	 * The dictionary that takes on values for a new privilege
	 */
	public NSMutableDictionary newPrivilegeValues = new NSMutableDictionary();

	public SWAssignPrivileges2( WOContext context ) {
		super( context );
	}

	public NSDictionary privilegePairs() {
		return privilegePairs;
	}

	public void setPrivilegePairs( NSDictionary d ) {
		privilegePairs = d;

		if( !hasInitializedPairs ) {
			Enumeration e = privilegePairs.allKeys().objectEnumerator();

			while( e.hasMoreElements() ) {
				String nextKey = (String)e.nextElement();
				fieldHeaders.addObject( nextKey );
				fieldNames.addObject( privilegePairs.objectForKey( nextKey ) );
				hasInitializedPairs = true;
			}
		}
	}

	public Integer currentAddPrivilege() {
		return (Integer)newPrivilegeValues.objectForKey( currentFieldName );
	}

	public void setCurrentAddPrivilege( Integer value ) {
		newPrivilegeValues.setObjectForKey( value, currentFieldName );
	}

	public Integer currentPrivilegeValue() {
		return currentPrivilege.valueForIdentifier( currentFieldName );
	}

	public void setCurrentPrivilegeValue( Integer value ) {
		currentPrivilege.setValueForIdentifier( value, currentFieldName );
	}

	/**
	 * Revokes the current privilege
	 */
	public WOComponent removePrivilege() {
		session().defaultEditingContext().deleteObject( currentPrivilege );
		session().defaultEditingContext().saveChanges();
		return null;
	}

	/**
	 * Retrieves all groups in the SoloWeb system sorted by name
	 */
	public NSArray allGroups() {
		EOSortOrdering s = new EOSortOrdering( "name", EOSortOrdering.CompareAscending );
		EOFetchSpecification fs = new EOFetchSpecification( "SWGroup", null, new NSArray( s ) );
		return session().defaultEditingContext().objectsWithFetchSpecification( fs );
	}

	/**
	 * Retrieves all users in the SoloWeb system sorted by name
	 */
	public NSArray allUsers() {
		EOSortOrdering s = new EOSortOrdering( "name", EOSortOrdering.CompareAscending );
		EOFetchSpecification fs = new EOFetchSpecification( "SWUser", null, new NSArray( s ) );
		return session().defaultEditingContext().objectsWithFetchSpecification( fs );
	}

	/**
	* All Users and Groups in the SoloWeb system in a single array.
	 */
	public NSArray allUsersAndGroups() {
		if( SWSettings.booleanForKey( "onlyShowGroupPrivileges" ) ) {
			return allGroups();
		}
		else {
			return allGroups().arrayByAddingObjectsFromArray( allUsers() );
		}
	}

	/**
	 * Adds the current user/group to the page with the selected set of privileges.
	 */
	public WOComponent addGroupOrUserToPage() {
		if( selectedObject.entityName().equals( "SWGroup" ) ) {
			addPrivilege( selectedObject, "group" );
		}
		else {
			addPrivilege( selectedObject, "user" );
		}

		session().defaultEditingContext().saveChanges();
		return null;
	}

	/**
	 * Adds an SWAccessPrivilege with the selectedObject (user or group) to the page
	 *
	 * @param aRecord The User or Group to add
	 * @param relationshipName The name of the relationship to add the object to ("user" or "group")
	 */
	public void addPrivilege( EOEnterpriseObject aRecord, String relationshipName ) {
		SWAccessPrivilege newPrivilege = new SWAccessPrivilege();
		session().defaultEditingContext().insertObject( newPrivilege );

		newPrivilege.addObjectToBothSidesOfRelationshipWithKey( aRecord, relationshipName );
		newPrivilege.setTargetEntityName( record.entityName() );
		newPrivilege.setTargetID( String.valueOf( primaryKey() ) );
		newPrivilege.setNotInherited( notInherited );

		Enumeration e = newPrivilegeValues.allKeys().objectEnumerator();

		while( e.hasMoreElements() ) {
			String nextKey = (String)e.nextElement();
			newPrivilege.setValueForIdentifier( (Integer)newPrivilegeValues.objectForKey( nextKey ), nextKey );
		}

		newPrivilegeValues = new NSMutableDictionary();
	}

	/**
	 * Determines the color of the current row being drawn, based on the tableIndex variable
	 */
	public String rowColor() {
		return (tableIndex % 2 == 0) ? "#dddddd" : "#bbbbbb";
	}

	/**
	 * The privileges that belong to this object only
	 */
	public NSArray privileges() {
		return SWAccessPrivilegeUtilities.privilegesForObject( record );
	}

	/**
	 * This object's inherited privileges (if any)
	 */
	public NSArray inheritedPrivileges() {
		if( recordSupportsInheritance() ) {
			return SWAccessPrivilegeUtilities.inheritedPrivilegesForObject( (SWInheritsPrivileges)record );
		}

		return null;
	}

	/**
	 * Finds and returns the primary key of the selected record
	 */
	public Integer primaryKey() {
		return (Integer)((EOKeyGlobalID)session().defaultEditingContext().globalIDForObject( record )).keyValuesArray().objectAtIndex( 0 );
	}

	/**
	 * Indicates if the selected record supports privilege inheritance
	 */
	public boolean recordSupportsInheritance() {
		return (record instanceof SWInheritsPrivileges);
	}
}