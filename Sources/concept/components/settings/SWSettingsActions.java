package concept.components.settings;

import is.rebbi.wo.interfaces.SWInheritsPrivileges;

import com.webobjects.appserver.WOApplication;
import com.webobjects.appserver.WOComponent;
import com.webobjects.appserver.WOContext;
import com.webobjects.eoaccess.EOUtilities;
import com.webobjects.foundation.NSArray;

import concept.components.admin.SWUsersAndGroups;
import concept.data.SWAssetFolder;
import concept.data.SWDocumentFolder;
import concept.data.SWGroup;
import concept.data.SWNewsCategory;
import concept.data.SWUser;
import concept.search.SWLuceneUtilities;
import concept.util.SWPictureUtilities;

public class SWSettingsActions extends SWManageSettings {

	public SWSettingsActions( WOContext context ) {
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

		NSArray<SWInheritsPrivileges> a = null;

		a = EOUtilities.objectsForEntityNamed( session().defaultEditingContext(), SWNewsCategory.ENTITY_NAME );
		makeArrayPropagatePrivileges( a );

		a = EOUtilities.objectsForEntityNamed( session().defaultEditingContext(), SWAssetFolder.ENTITY_NAME );
		makeArrayPropagatePrivileges( a );

		a = EOUtilities.objectsForEntityNamed( session().defaultEditingContext(), SWDocumentFolder.ENTITY_NAME );
		makeArrayPropagatePrivileges( a );

		session().defaultEditingContext().saveChanges();

		return null;
	}

	private void makeArrayPropagatePrivileges( NSArray<SWInheritsPrivileges> a ) {
		for( SWInheritsPrivileges next : a ) {
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