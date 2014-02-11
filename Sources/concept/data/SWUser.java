package concept.data;

import is.rebbi.core.util.StringUtilities;
import is.rebbi.wo.interfaces.HumanReadable;
import is.rebbi.wo.interfaces.SWUserInterface;
import is.rebbi.wo.interfaces.TimeStamped;
import is.rebbi.wo.util.SoftUser;
import is.rebbi.wo.util.USHTTPUtilities;
import is.rebbi.wo.util.USUtilities;

import com.webobjects.appserver.WOContext;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOEnterpriseObject;
import com.webobjects.eocontrol.EOSortOrdering;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSTimestamp;

import concept.SWSessionHelper;
import concept.data.auto._SWUser;
import concept.util.CPAccessPrivilegeUtilities;
import er.extensions.crypting.ERXCrypto;
import er.extensions.eof.ERXEOControlUtilities;

/**
 * A user.
 */

public class SWUser extends _SWUser implements HumanReadable, TimeStamped, SWUserInterface {

	/**
	 * @return All users.
	 */
	public static NSArray<SWUser> allUsers( EOEditingContext ec ) {
		return SWUser.fetchAllSWUsers( ec, SWUser.NAME.ascInsensitives() );
	}

	/**
	 * Create a new user.
	 */
	public static SWUser create( EOEditingContext ec ) {
		SWUser user = new SWUser();
		ec.insertObject( user );

		SWGroup g = SWGroup.allUsersGroup( ec );

		if( g != null ) {
			user.addObjectToBothSidesOfRelationshipWithKey( g, GROUPS_KEY );
		}

		return user;
	}

	/**
	 * Indicates if the given username is already in use.
	 */
	public static boolean emailAddressExists( EOEditingContext ec, String emailAddress ) {
		Number i = ERXEOControlUtilities.objectCountWithQualifier( ec, SWUser.ENTITY_NAME, SWUser.EMAIL_ADDRESS.eq( emailAddress ) );
		return i.intValue() > 0;
	}

	/**
	 * @return Privileges this user gains from groups.
	 */
	public NSArray<SWAccessPrivilege> groupPrivileges() {
		NSMutableArray<SWAccessPrivilege> gp = new NSMutableArray<>();

		for( SWGroup g : groups() ) {
			gp.addObjectsFromArray( g.accessPrivileges() );
		}

		return gp;
	}

	/**
	 * @return If the current user is granted the specified privilege.
	 */
	public boolean hasPrivilegeFor( EOEnterpriseObject record, String privilegeName ) {
		return CPAccessPrivilegeUtilities.hasPrivilegeFor( this, record, privilegeName );
	}

	/**
	 * @return sites this user has access to.
	 */
	public NSArray<SWSite> sites() {
		NSArray<SWSite> sites = CPAccessPrivilegeUtilities.filteredArrayWithUserAndPrivilege( SWSite.allSites( editingContext() ), this, CPAccessPrivilegeUtilities.PRIVILEGE_ALLOW_TO_SEE );
		return EOSortOrdering.sortedArrayUsingKeyOrderArray( sites, SWSite.NAME.ascInsensitives() );
	}

	/**
	 * Returns a list of all groups, in the system, except those the user is already a member of.
	 */
	public NSArray<SWGroup> groupsNotIn() {
		NSMutableArray<SWGroup> groups = SWGroup.allGroups( editingContext() ).mutableClone();
		groups.removeObjectsInArray( groups() );
		return groups;
	}

	/**
	 * @return The associated SoftUser
	 */
	public SoftUser softUser() {
		return new SoftUser( uuid() );
	}

	/**
	 * @return An error message if there are problems, null on success.
	 */
	public static String loginInContext( EOEditingContext ec, String emailAddress, String password, WOContext context ) {

		if( !StringUtilities.hasValue( emailAddress ) ) {
			return "Vinsamlegast sláðu inn netfangið þitt";
		}

		if( !StringUtilities.hasValue( password ) ) {
			return "Vinsamlegast sláðu inn aðgangsorð";
		}

		SWUser user = SWUser.fetchSWUser( ec, SWUser.USERNAME.eq( emailAddress ) );

		if( user == null ) {
			user = SWUser.fetchSWUser( ec, SWUser.EMAIL_ADDRESS.eq( emailAddress ) );

			if( user == null ) {
				return "Enginn notandi fannst með þetta netfang eða notandanafn";
			}
		}

		String passwordEncrypted = ERXCrypto.shaEncode( password );

		// Migrating to crypted password. Keep this for a couple of months.
		if( password.equals( user.password() ) ) {
			password = passwordEncrypted;
			user.setPassword( passwordEncrypted );
			user.editingContext().saveChanges();
		}

		if( !passwordEncrypted.equals( user.password() ) ) {
			return "Rangt aðgangsorð";
		}

		user.loginInContext( context );

		return null;
	}

	public boolean validatePassword( String password ) {
		if( password == null ) {
			return false;
		}

		if( password.equals( password() ) ) {
			return true;
		}

		if( ERXCrypto.shaEncode( password ).equals( password() ) ) {
			return true;
		}

		return false;
	}

	public void setPasswordHash( String value) {
		if( StringUtilities.hasValue( value ) ) {
			setPassword( ERXCrypto.shaEncode( value ) );
		}
		else {
			setPassword( value );
		}
	}

	public void loginInContext( WOContext context ) {
		SWSystemEvent.logEvent( "login", emailAddress() );
		SWSessionHelper.setUserInSession( context.session(), this );

		String userUUID = uuid();
		SoftUser softUser = null;

		if( userUUID != null ) {
			softUser = new SoftUser( userUUID );
		}
		else {
			softUser = SoftUser.fromRequest( context.request() );
			setUuid( softUser.uuid() );
		}

		softUser.assign( context.request(), context.response() );

		setLastLoginDate( new NSTimestamp() );
		setLastLoginAgent( USHTTPUtilities.userAgent( context.request() ) );
		setLastLoginIP( USHTTPUtilities.ipAddressFromRequest( context.request() ) );
		editingContext().saveChanges();
	}

	/**
	 * @return Boolean version of the "administrator" attribute.
	 */
	public boolean isAdministrator() {
		return USUtilities.booleanFromObject( administrator() );
	}

	@Override
	public String toStringHuman() {
		return name();
	}
}