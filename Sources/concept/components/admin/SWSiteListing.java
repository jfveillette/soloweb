package concept.components.admin;

import is.rebbi.core.util.StringUtilities;
import is.rebbi.wo.util.USEOUtilities;

import java.util.Enumeration;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOApplication;
import com.webobjects.appserver.WOComponent;
import com.webobjects.appserver.WOContext;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSMutableSet;

import concept.SWAdminComponent;
import concept.SWApplication;
import concept.SWSessionHelper;
import concept.data.SWPage;
import concept.data.SWSite;
import er.extensions.appserver.ERXSession;
import er.extensions.foundation.ERXStringUtilities;

/**
 * Displays all sites in the current SoloWeb system and their subpages in a hierarchical list.
 */

public class SWSiteListing extends SWAdminComponent {

	public static String CSS_UNPUBLISHED = "unpublished";
	public static String CSS_INACCESSIBLE = "inaccessible";
	public static String CSS_PUBLISHED = "published";
	public static String CSS_HILITE = "hilite"; // used for search result hilite

	/**
	 * The hyperlink around all subpages of this page is disabled, including itself.
	 * null if no pages should be disabled.
	 */
	public SWPage recordToDisableSubPagesOf;

	/**
	 * Index of the current page being iterated over ina  repetition (reset for each branch in the hierarchy).
	 */
	public int currentIndex;

	public int currentDisplayIndex() {
		return currentIndex + 1;
	}

	/**
	 * The current site being iterated over in the repetition.
	 */
	public SWSite currentSite;

	/**
	 * The current site being iterated over in the repetition.
	 */
	public SWPage currentPage;

	/**
	 * Name of an action to perform in the parent component when a page is clicked.
	 */
	public String action;

	/**
	 * Name of an access privilege required to have the page active
	 */
	public String requiredPrivilege;

	/**
	 * The selected page.
	 */
	public SWPage selectedPage;

	/**
	 * Name of an action to perform in the parent component when a site is clicked.
	 */
	public String siteAction;

	/**
	 * Determines if arrows to move pages up and down in the site tree should be shown.
	 */
	public boolean showArrows;

	/**
	 * Determines if we should show indicators for inbetween page transfers.
	 */
	public boolean inBetween = false;

	/**
	 * page id or hlekkjunarheiti to search for
	 */
	public String searchString;

	public SWSite currentSearchSite;
	public SWPage currentSearchPage;

	public NSArray<SWPage> searchResultPages = NSArray.EmptyArray;

	public SWSiteListing( WOContext context ) {
		super( context );
	}

	/**
	 * Selects the current page and performs the action specified in "action"
	 */
	public WOComponent selectObject() {
		selectedPage = currentPage;
		return (WOComponent)performParentAction( action );
	}

	public WOComponent selectedSearchObject() {
		setSelectedSite( currentSearchSite );
		selectedPage = currentSearchPage;
		return (WOComponent)performParentAction( action );
	}

	public WOComponent transferFirst() {
		selectedPage = currentPage;
		return (WOComponent)performParentAction( "transferFirst" );
	}

	public WOComponent transferOther() {
		selectedPage = currentPage;
		return (WOComponent)performParentAction( "transferOther" );
	}

	/**
	 * Selects the current site and performs the action specified in "siteAction"
	 */
	public WOComponent selectSite() {
		setSelectedSite( selectedSite() );
		return (WOComponent)performParentAction( siteAction );
	}

	public void setSelectedSite( SWSite aSite ) {
		if( aSite != null ) {
			((ERXSession)session()).objectStore().takeValueForKey( aSite, "selectedSite" );
		}
	}

	public SWSite selectedSite() {
		Object o = ((ERXSession)session()).objectStore().valueForKey( "selectedSite" );

		if( o != null ) {
			return (SWSite)o;
		}

		return null;
	}

	/**
	 * The css class to set for a page link, determined by it's accessibility/publishing status.<BR>
	 * "inaccessible" if a page is inacessible<BR>
	 * "unpublished" if a page is not publishsed<BR>
	 * "published" if the page is both acessible and published.
	 */
	public String currentClass() {
		String klass = CSS_PUBLISHED;

		if( !currentPage.isAccessible() ) {
			klass = CSS_INACCESSIBLE;
		}

		if( !currentPage.isPublished() ) {
			klass = CSS_UNPUBLISHED;
		}

		return klass;
	}

	/**
	 * Checks if the current branch is expanded or not, and returns either the
	 * page's subpages, or an empty array, based on that info.
	 */
	public NSArray<SWPage> theSubPages() {
		return isExpanded( currentPage ) ? currentPage.sortedSubPages() : NSArray.<SWPage>emptyArray();
	}

	/**
	 * Toggles the subpage display status for the current page, if it's subpages should be dispalyed or not.
	 */
	public WOComponent toggleDisplay() {
		if( isExpanded( currentPage ) ) {
			collapseBranch( currentPage );
		}
		else {
			expandBranch( currentPage );
		}

		return null;
	}

	/**
	 * Determines if the plus sign or the minus sign should be displayed in
	 * front of the current page, based on if it's branch is expanded or not.
	 * Returns the name of the picture to display.
	 */
	public String toggleString() {
		try {
			if( isExpanded( currentPage ) ) {
				return WOApplication.application().resourceManager().urlForResourceNamed( "sw32/img/minus.gif", SWApplication.swapplication().frameworkBundleName(), null, context().request() );
			}
			else {
				return WOApplication.application().resourceManager().urlForResourceNamed( "sw32/img/plus.gif", SWApplication.swapplication().frameworkBundleName(), null, context().request() );
			}
		}
		catch( Exception e ) {
			return null;
		}
	}

	/**
	 * Determines if the hyperlink around the current page name should be disabled, based on "recordToDisableSubPagesOf".
	 * Also disables the "recordToDisableSubPagesOf" itself.
	 */
	public boolean isDisabled() {
		boolean disabled = false;

		if( recordToDisableSubPagesOf != null ) {
			if( currentPage.isSubPageOfPage( recordToDisableSubPagesOf, true ) ) {
				disabled = true;
			}
		}

		if( StringUtilities.hasValue( requiredPrivilege ) ) {
			if( !user().hasPrivilegeFor( currentPage, requiredPrivilege ) ) {
				disabled = true;
			}
		}

		if( inBetween && currentPage.hasSubPages() ) {
			disabled = true;
		}

		return disabled;
	}

	/**
	 * Determines if the currentPage is the one that is being disabled.
	 * @return True if so.
	 */
	public boolean isRecordBeingTransferred() {
		if( recordToDisableSubPagesOf != null && currentPage != null && currentPage.primaryKey().equals( recordToDisableSubPagesOf.primaryKey() ) ) {
			return true;
		}
		return false;
	}

	/**
	 * Determines if this site's hyperlink should be disabled.
	 * It's active only if siteAction is not null, and the current user is an administrator.
	 */
	public boolean siteIsDisabled() {

		if( siteAction == null ) {
			return true;
		}

		if( user().isAdministrator() ) {
			return false;
		}

		return false;
	}

	/**
	 * Determines if the current branch should be expanded (if subpages should be displayed)
	 */
	public boolean isExpanded( SWPage aPage ) {
		return SWSessionHelper.arrayWithKeyContainsObject( session(), "SWPage", aPage );
	}

	/**
	 * Expands the current branch.
	 */
	public void expandBranch( SWPage aPage ) {
		SWSessionHelper.addObjectToArrayWithKey( session(), aPage, "SWPage" );
	}

	/**
	 * Collapses the current branch.
	 */
	public void collapseBranch( SWPage aPage ) {
		SWSessionHelper.removeObjectFromArrayWithKey( session(), aPage, "SWPage" );
	}

	public boolean showSitePopUp() {
		return user().sites().count() > 1;
	}

	public WOComponent expandAllBranches() {

		NSArray<SWPage> a = selectedSite().frontpage().everySubPage();
		Enumeration<SWPage> e = a.objectEnumerator();

		while( e.hasMoreElements() ) {
			expandBranch( e.nextElement() );
		}

		return null;
	}

	public String setTheSelectedSite() {
		NSArray<SWSite> sites = user().sites();

		if( sites.size() > 0 ) {
			setSelectedSite( sites.get( 0 ) );
		}

		return null;
	}

	public WOActionResults search() {
		action = "selectObject";

		if( StringUtilities.hasValue( searchString ) ) {
			if( ERXStringUtilities.isDigitsOnly( searchString ) ) {
				searchResultPages = USEOUtilities.objectsMatchingKeyAndValue( session().defaultEditingContext(), SWPage.ENTITY_NAME, "pageID", new Integer( searchString ) );
			}
			else {
				searchResultPages = USEOUtilities.objectsMatchingKeyAndValue( session().defaultEditingContext(), SWPage.ENTITY_NAME, "symbol", searchString );
			}
		}

		return context().page();
	}

	public NSArray<SWSite> searchResultSites() {

		NSMutableSet<SWSite> sites = new NSMutableSet<>();
		Enumeration<SWPage> e = searchResultPages.objectEnumerator();

		while( e.hasMoreElements() ) {
			SWPage page = e.nextElement();
			sites.addObject( page.siteForThisPage() );
		}

		return sites.allObjects();
	}

	public NSArray<SWPage> searchResultPagesForCurrentSite() {

		NSMutableArray<SWPage> pages = new NSMutableArray<>();
		Enumeration<SWPage> e = searchResultPages.objectEnumerator();

		while( e.hasMoreElements() ) {
			SWPage page = e.nextElement();

			if( page.siteForThisPage().equals( currentSearchSite ) ) {
				pages.addObject( page );
			}
		}

		return pages;
	}
}