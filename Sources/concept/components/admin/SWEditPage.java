package concept.components.admin;

import is.rebbi.core.util.StringUtilities;
import is.rebbi.wo.util.SWSettings;

import com.webobjects.appserver.WOComponent;
import com.webobjects.appserver.WOContext;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSDictionary;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSMutableDictionary;

import concept.SWAdminComponent;
import concept.SWApplication;
import concept.SWSession;
import concept.data.SWPage;
import concept.data.SWSite;
import concept.search.SWLuceneUtilities;
import concept.util.CPLoc;

/**
 * SWEditPage is displayed when editing pages, and contains among other things
 * the tabs to select a new component type.
 */

public class SWEditPage extends SWAdminComponent {

	/**
	 * The page being edited
	 */
	public SWPage selectedPage;

	public SWEditPage( WOContext context ) {
		super( context );
	}

	public NSDictionary tabDictionary() {
		NSMutableDictionary apec = new NSMutableDictionary( SWApplication.swapplication().activePageEditingComponents() );

		if( user().hasPrivilegeFor( selectedPage, "canManagePage" ) ) {
			apec.setObjectForKey( "SWEditPageGeneralInfo", CPLoc.string( "eptGeneral", session() ) );

			if( "SWDefaultLook6".equals( selectedPage.siteForThisPage().look() ) ) {
				apec.setObjectForKey( "SWDefaultLook6Admin", "SWDefaultLook6" );
			}
		}

		apec.setObjectForKey( "SWEditContent", CPLoc.string( "eptContent", session() ) );

		if( SWSettings.booleanForKey( "enablePrivileges" ) && user().hasPrivilegeFor( selectedPage, "canManageUsers" ) ) {
			apec.setObjectForKey( "SWEditPagePrivileges", CPLoc.string( "eptAccessPrivileges", session() ) );
		}

		return apec;
	}

	public String selectedComponentName() {
		return (String)tabDictionary().valueForKey( selectedTab() );
	}

	/**
	 * The currently selected tab
	 */
	public String selectedTab() {
		String selected = (String)session().valueForKey( "solowebSelectedPageTab" );

		if( selected == null ) {
			selected = CPLoc.string( "eptContent", session() );
		}

		return selected;
	}

	/**
	 * The editing tabs to display at the top of the editing page.
	 */
	public NSArray tabs() {
		NSMutableArray theTabs = new NSMutableArray();
		NSArray allTabs = tabDictionary().allKeys();

		for( int i = 0; i < allTabs.count(); i++ ) {
			if( ((String)allTabs.objectAtIndex( i )).charAt( 0 ) != '(' ) {
				theTabs.addObject( allTabs.objectAtIndex( i ) );
			}
		}

		return theTabs;
	}

	/**
	 * Sets the selected tab
	 *
	 * @param newSelectedTab
	 *           the tab to select
	 */
	public void setSelectedTab( String newSelectedTab ) {
		session().takeValueForKey( newSelectedTab, "solowebSelectedPageTab" );
	}

	/**
	 * Sets the selected page
	 *
	 * @param newSelectedPage
	 *           the page to select
	 */
	public WOComponent setSelectedPage( SWPage newSelectedPage ) {
		selectedPage = newSelectedPage;
		return null;
	}

	/**
	 * Deletes the selected page and all it's content
	 */
	public WOComponent deletePage() {
		selectedPage.parent().removeSubPage( selectedPage );
		session().defaultEditingContext().deleteObject( selectedPage );
		session().defaultEditingContext().saveChanges();
		setSelectedPage( null );
		return null;
	}

	/**
	 * creates a new SWPage below all other subpages in the sortorder, expands
	 * the selected page's branch in the site tree and sets default dor the new
	 * page.
	 */
	public WOComponent createSubPage() {
		String newPageName = CPLoc.string( "newPageName", session() );

		if( !((SWSession)session()).arrayWithKeyContainsObject( "SWPage", selectedPage ) ) {
			((SWSession)session()).addObjectToArrayWithKey( selectedPage, "SWPage" );
		}

		SWPage newPage = new SWPage();
		session().defaultEditingContext().insertObject( newPage );

		newPage.setName( newPageName );
		newPage.setInheritsPrivileges( 1 );
		newPage.setAccessible( 1 );
		newPage.setPublished( 0 );

		selectedPage.insertSubPageAtIndex( newPage, selectedPage.children().count() );

		session().defaultEditingContext().saveChanges();

		setSelectedPage( newPage );
		setSelectedTab( CPLoc.string( "eptGeneral", session() ) );

		return null;
	}

	/**
	 * Saves changes made in the current session.
	 */
	public WOComponent saveChanges() {
		session().defaultEditingContext().saveChanges();
		SWLuceneUtilities.indexObject( null, selectedPage );
		return null;
	}

	public String previewURL() {
		String host = this.context().request().headerForKey( "host" );
		boolean isDirectConnect = StringUtilities.hasValue( host ) && (host.indexOf( ':' ) > -1);
		String url = "";

		if( isDirectConnect ) {
			url = "http://localhost:" + host.substring( host.indexOf( ':' ) + 1 );
		}
		else {
			SWSite site = selectedPage.siteForThisPage();
			url = site.primaryDomain();

			if( !StringUtilities.hasValue( url ) ) {
				url = host;
			}

			url = "http://" + url;
		}

		url += selectedPage.pageLink();

		return url;
	}
}