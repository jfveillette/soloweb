package concept.components.settings;

import is.rebbi.wo.util.USEOUtilities;

import java.util.Enumeration;

import com.webobjects.appserver.WOComponent;
import com.webobjects.appserver.WOContext;
import com.webobjects.foundation.NSMutableArray;

import concept.components.admin.SWEditDocumentContentAsText;
import concept.data.SWComponent;
import concept.data.SWPage;

public class SWSettingsCustomInfo extends SWManageSettings {

	public SWPage selectedPage;
	public SWComponent selectedComponent;

	public String pageId;
	public String componentId;

	public String pageId2;
	public String componentId2;

	public SWSettingsCustomInfo( WOContext context ) {
		super( context );
	}

	public WOComponent editAsText() {
		SWEditDocumentContentAsText nextPage = null;

		if( selectedPage != null ) {
			nextPage = pageWithName( SWEditDocumentContentAsText.class );
			nextPage.selectedPage = selectedPage;
		}
		else if( selectedComponent != null ) {
			nextPage = pageWithName( SWEditDocumentContentAsText.class );
			nextPage.selectedComponent = selectedComponent;
		}

		return nextPage;
	}

	public WOComponent editPageCustomInfo() {
		Integer iId = Integer.valueOf( pageId );

		selectedComponent = null;
		selectedPage = null;

		selectedPage = (SWPage)USEOUtilities.objectMatchingKeyAndValue( session().defaultEditingContext(), "SWPage", "pageID", iId );

		if( selectedPage != null ) {
			return editAsText();
		}
		else {
			return null;
		}
	}

	public WOComponent editComponentCustomInfo() {
		Integer iId = Integer.valueOf( componentId );

		selectedComponent = null;
		selectedPage = null;

		selectedComponent = USEOUtilities.objectWithPK( session().defaultEditingContext(), SWComponent.ENTITY_NAME, iId );

		if( selectedComponent != null ) {
			return editAsText();
		}
		else {
			return null;
		}
	}

	/**
	 * Invalidates a page and all its components.
	 */
	public WOComponent invalidatePage() {
		Integer iId = Integer.valueOf( pageId2 );
		NSMutableArray gids = new NSMutableArray();

		SWPage thePage = USEOUtilities.objectWithPK( session().defaultEditingContext(), SWPage.ENTITY_NAME, iId );

		if( thePage != null ) {
			if( thePage.components() != null ) {
				Enumeration comps = thePage.components().objectEnumerator();
				while( comps.hasMoreElements() ) {
					gids.addObject( ((SWComponent)comps.nextElement()).__globalID() );
				}
			}
			gids.addObject( thePage.__globalID() );
			session().defaultEditingContext().invalidateObjectsWithGlobalIDs( gids );
		}

		return null;
	}

	/**
	 * Invalidates a single component.
	 */
	public WOComponent invalidateComponent() {
		Integer iId = Integer.valueOf( componentId2 );
		NSMutableArray gids = new NSMutableArray();

		SWComponent theComp = USEOUtilities.objectWithPK( session().defaultEditingContext(), SWComponent.ENTITY_NAME, iId );

		if( theComp != null ) {
			gids.addObject( theComp.__globalID() );
			session().defaultEditingContext().invalidateObjectsWithGlobalIDs( gids );
		}

		return null;
	}
}