package concept.components.admin;

import is.rebbi.wo.interfaces.SWInheritsPrivileges;

import java.util.Enumeration;

import com.webobjects.appserver.WOApplication;
import com.webobjects.appserver.WOComponent;
import com.webobjects.appserver.WOContext;
import com.webobjects.eoaccess.EOUtilities;
import com.webobjects.foundation.NSArray;

import concept.data.SWGroup;
import concept.data.SWUser;
import concept.search.SWLuceneUtilities;
import concept.util.SWPictureUtilities;

public class SWActionSettings extends SWManageSettings {

	public SWActionSettings( WOContext context ) {
		super( context );
	}

	public WOComponent flushComponentCache() {
		WOApplication.application()._removeComponentDefinitionCacheContents();
		return null;
	}

	public WOComponent invalidateAllObjectsInSoloEC() {
		session().defaultEditingContext().invalidateAllObjects();
		session().defaultEditingContext().invalidateAllObjects();
		return null;
	}

	public WOComponent createGroupWithAllUsers() {
		SWGroup g = new SWGroup();
		session().defaultEditingContext().insertObject( g );
		g.setName( "All users" );
		g.addObjectsToBothSidesOfRelationshipWithKey( SWUser.allUsers( session().defaultEditingContext() ), SWGroup.USERS_KEY );
		session().defaultEditingContext().saveChanges();
		return null;
	}

	public WOComponent makeAllFoldersPropagatePrivileges() {

		NSArray a = null;

		a = EOUtilities.objectsForEntityNamed( session().defaultEditingContext(), "SWNewsCategory" );
		makeArrayPropagatePrivileges( a );

		a = EOUtilities.objectsForEntityNamed( session().defaultEditingContext(), "SWAssetFolder" );
		makeArrayPropagatePrivileges( a );

		a = EOUtilities.objectsForEntityNamed( session().defaultEditingContext(), "SWDocumentFolder" );
		makeArrayPropagatePrivileges( a );

		session().defaultEditingContext().saveChanges();

		return null;
	}

	private void makeArrayPropagatePrivileges( NSArray a ) {
		Enumeration e = a.objectEnumerator();

		while( e.hasMoreElements() ) {
			SWInheritsPrivileges next = (SWInheritsPrivileges)e.nextElement();
			next.setInheritsPrivileges( 1 );
		}
	}

	public WOComponent updateAllPictureThumbnails() {
		SWPictureUtilities.updateAllPictureThumbnailsFromId( session().defaultEditingContext(), 0, Integer.MAX_VALUE );
		return null;
	}

	public SWUsersAndGroups goToUsersAndGroupd() {
		return (SWUsersAndGroups)pageWithName( "SWUsersAndGroups" );
	}

	public WOComponent updateSearchIndex() {
		SWLuceneUtilities.rebuildSearchIndex( session().defaultEditingContext() );
		return null;
	}
}