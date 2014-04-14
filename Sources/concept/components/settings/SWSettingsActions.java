package concept.components.settings;

import is.rebbi.wo.interfaces.SWInheritsPrivileges;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.webobjects.appserver.WOApplication;
import com.webobjects.appserver.WOComponent;
import com.webobjects.appserver.WOContext;
import com.webobjects.eoaccess.EOUtilities;
import com.webobjects.eocontrol.EOAndQualifier;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOFetchSpecification;
import com.webobjects.eocontrol.EOKeyValueQualifier;
import com.webobjects.eocontrol.EOQualifier;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableArray;

import concept.components.admin.SWUsersAndGroups;
import concept.data.SWAssetFolder;
import concept.data.SWDocumentFolder;
import concept.data.SWGroup;
import concept.data.SWNewsCategory;
import concept.data.SWPicture;
import concept.data.SWUser;
import concept.util.SWPictureUtilities;

public class SWSettingsActions extends SWManageSettings {

	private static final Logger logger = LoggerFactory.getLogger( SWSettingsActions.class );

	public SWSettingsActions( WOContext context ) {
		super( context );
	}

	public WOComponent flushComponentCache() {
		WOApplication.application()._removeComponentDefinitionCacheContents();
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
		SWSettingsActions.updateAllPictureThumbnailsFromId( session().defaultEditingContext(), 0, Integer.MAX_VALUE );
		return null;
	}

	public SWUsersAndGroups goToUsersAndGroupd() {
		return pageWithName( SWUsersAndGroups.class );
	}

	public static void updateAllPictureThumbnailsFromId( EOEditingContext ec, Integer startPictureId, Integer endPictureId ) {
		NSMutableArray<EOQualifier> a = new NSMutableArray<>();

		if( startPictureId != null ) {
			a.addObject( new EOKeyValueQualifier( SWPicture.ID_KEY, EOQualifier.QualifierOperatorGreaterThanOrEqualTo, startPictureId ) );
		}

		if( endPictureId != null ) {
			a.addObject( new EOKeyValueQualifier( SWPicture.ID_KEY, EOQualifier.QualifierOperatorLessThanOrEqualTo, endPictureId ) );
		}

		EOAndQualifier q = new EOAndQualifier( a );
		EOFetchSpecification fs = new EOFetchSpecification( SWPicture.ENTITY_NAME, q, null );
		NSArray<SWPicture> pictures = ec.objectsWithFetchSpecification( fs );

		logger.debug( "Full thumbnail rebuild started" );

		int i = 0;

		for( SWPicture picture : pictures ) {
			SWPictureUtilities.logger.debug( "Updating picture_id: " + picture.primaryKey() + " (" + i + " of " + pictures.size() + ")" );
			picture.updateThumbnails();

			if( i++ % 500 == 0 ) {
				ec.saveChanges();
			}
		}
		ec.saveChanges();

		logger.debug( "Full thumbnail rebuild done" );
	}
}