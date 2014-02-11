package concept.components.admin;


import com.webobjects.appserver.WOApplication;
import com.webobjects.appserver.WOComponent;
import com.webobjects.appserver.WOContext;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSDictionary;

import concept.SWAdminComponent;
import concept.SWApplication;
import concept.data.SWAssetFolder;
import concept.data.SWDocument;
import concept.data.SWDocumentFolder;
import concept.data.SWNewsCategory;
import concept.data.SWNewsItem;
import concept.data.SWPicture;

/**
 * The "System menu" component, displayed at the top of the SoloWeb system.
 */

public class SWMainMenu extends SWAdminComponent {

	/**
	 * The name of the current system being displayed
	 */
	public String currentKey;

	public SWMainMenu( WOContext context ) {
		super( context );
	}

	public NSDictionary systemDictionary() {
		return SWApplication.swapplication().activeSystems();
	}

	/**
	 * An array with all system names - the keys of the tempSystems dictionary
	 */
	public NSArray theSystems() {
		return systemDictionary().allKeys();
	}

	/**
	 * Name of component to display for the current system name (when clicked)
	 */
	public String currentSystem() {
		return (String)systemDictionary().objectForKey( currentKey );
	}

	@Override
	public void sleep() {
		session().savePageInPermanentCache( this );
	}

	/**
	 * Logs out the current user and displays the "SWLoggedOut" component.
	 */
	public WOComponent logout() {
		session().terminate();
		return pageWithName( SWLoggedOut.class );
	}

	public WOComponent pictureDB() {
		SWAssetManagement nextPage = pageWithName( SWAssetManagement.class );
		nextPage.setEntityName( SWPicture.ENTITY_NAME );
		nextPage.setFolderEntityName( SWAssetFolder.ENTITY_NAME );
		nextPage.setEditingComponentName( SWEditDataAsset.class.getName() );
		return nextPage;
	}

	public WOComponent documentDB() {
		SWAssetManagement nextPage = pageWithName( SWAssetManagement.class );
		nextPage.setEntityName( SWDocument.ENTITY_NAME );
		nextPage.setFolderEntityName( SWDocumentFolder.ENTITY_NAME );
		nextPage.setEditingComponentName( SWEditDataAsset.class.getName() );
		return nextPage;
	}

	public WOComponent newsDB() {
		SWAssetManagement nextPage = pageWithName( SWAssetManagement.class );
		nextPage.setEntityName( SWNewsItem.ENTITY_NAME );
		nextPage.setFolderEntityName( SWNewsCategory.ENTITY_NAME );
		nextPage.setEditingComponentName( SWEditNewsItem.class.getName() );
		return nextPage;
	}

	public String href() {
		return WOApplication.application().resourceManager().urlForResourceNamed( "sw32/css/soloweb.css", SWApplication.swapplication().frameworkBundleName(), null, context().request() );
	}
}