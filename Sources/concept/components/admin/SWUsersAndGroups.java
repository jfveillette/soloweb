package concept.components.admin;


import com.webobjects.appserver.WOComponent;
import com.webobjects.appserver.WOContext;
import com.webobjects.eocontrol.EOFetchSpecification;
import com.webobjects.eocontrol.EOGenericRecord;
import com.webobjects.eocontrol.EOSortOrdering;
import com.webobjects.foundation.NSArray;

import concept.SWAdminComponent;
import concept.data.SWGroup;
import concept.data.SWUser;

/**
 * For management of users and groups
 */

public class SWUsersAndGroups extends SWAdminComponent {

	/**
	 * The currently selecte user or group
	 */
	public EOGenericRecord selectedObject;

	/**
	 * The current user being iterated over in the user pop-up menu.
	 */
	public SWUser currentUser;

	/**
	 * The current group being iterated over in the user pop-up menu.
	 */
	public SWGroup currentGroup;

	/**
	 * Determines if privileges from groups should be displayed
	 */
	public boolean displayPrivilegesFromGroups;

	public SWUsersAndGroups( WOContext context ) {
		super( context );
	}

	/**
	 * Fetches a list of all grous in the SoloWeb system.
	 */
	public NSArray allGroups() {
		EOSortOrdering s = new EOSortOrdering( "name", EOSortOrdering.CompareAscending );
		EOFetchSpecification fs = new EOFetchSpecification( "SWGroup", null, new NSArray( s ) );
		return session().defaultEditingContext().objectsWithFetchSpecification( fs );
	}

	/**
	 * Fetches a list of all users in the SoloWeb system.
	 */
	public NSArray allUsers() {
		EOSortOrdering s = new EOSortOrdering( "name", EOSortOrdering.CompareAscending );
		EOFetchSpecification fs = new EOFetchSpecification( "SWUser", null, new NSArray( s ) );
		return session().defaultEditingContext().objectsWithFetchSpecification( fs );
	}

	/**
	 * Creates a new user object and saves it to the database.
	 */
	public WOComponent createUser() {
		SWUser u = new SWUser();
		session().defaultEditingContext().insertObject( u );

		SWGroup g = SWGroup.allUsersGroup( session().defaultEditingContext() );

		if( g != null ) {
			u.addObjectToBothSidesOfRelationshipWithKey( g, "groups" );
		}

		session().defaultEditingContext().saveChanges();
		selectedObject = u;

		return null;
	}

	/**
	 * Creates a new group object and saves it to the database.
	 */
	public WOComponent createGroup() {
		SWGroup g = new SWGroup();
		session().defaultEditingContext().insertObject( g );

		session().defaultEditingContext().saveChanges();
		selectedObject = g;

		return null;
	}

	/**
	 * Determines if the selected record is a user - if not, it is a group
	 */
	public boolean selectedObjectIsUser() {
		if( selectedObject == null ) {
			return false;
		}

		if( selectedObject.getClass().getName().equals( SWGroup.class.getName() ) ) {
			return false;
		}

		return true;
	}
}