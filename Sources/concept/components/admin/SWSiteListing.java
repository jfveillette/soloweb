package concept.components.admin;

import is.rebbi.core.util.StringUtilities;
import is.rebbi.wo.util.USArrayUtilities;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOApplication;
import com.webobjects.appserver.WOContext;
import com.webobjects.appserver.WOSession;
import com.webobjects.foundation.NSArray;

import concept.Concept;
import concept.Inspection;
import concept.CPAdminComponent;
import concept.SWSessionHelper;
import concept.data.SWPage;
import concept.data.SWSite;
import concept.data.SWUser;
import er.extensions.appserver.ERXSession;

/**
 * Displays all sites in the current system and their subpages in a hierarchical list.
 */

public class SWSiteListing extends CPAdminComponent {

	private static final String PLUS_GIF = "plus.gif";
	private static final String MINUS_GIF = "minus.gif";
	private static final String SPACER_GIF = "spacer.gif";

	/**
	 * The link on subpages of this page will be disabled, including itself.
	 */
	public SWPage recordToDisableSubPagesOf;

	/**
	 * Index of the current page being iterated over in a repetition (reset for each branch in the hierarchy).
	 */
	public int currentIndex;

	/**
	 * Site being iterated over in the repetition.
	 */
	public SWSite currentSite;

	/**
	 * Page being iterated over in the repetition.
	 */
	public SWPage currentPage;

	/**
	 * Name of an action to perform in the parent component when a page is clicked.
	 */
	public String pageAction;

	/**
	 * Name of an access privilege required to have the page active
	 */
	public String requiredPrivilege;

	/**
	 * The selected page.
	 */
	private SWPage _selectedObject;

	public SWSiteListing( WOContext context ) {
		super( context );
	}

	@Override
	public SWPage selectedObject() {
		return _selectedObject;
	}

	/**
	 * The site`s frontpage in an Array (for use with nested lists and such)
	 */
	public NSArray<SWPage> frontPageInArray() {
		if( selectedSite() != null ) {
			return new NSArray<SWPage>( selectedSite().frontpage() );
		}

		return NSArray.emptyArray();
	}

	public void setSelectedObject( SWPage value ) {
		_selectedObject = value;
	}

	public SWSite selectedSite() {
		return selectedSite( session() );
	}

	public void setSelectedSite( SWSite site ) {
		setSelectedSite( session(), site );
	}

	/**
	 * Selects the current site for editing in an SWEditSite component.
	 */
	public WOActionResults selectSite() {
		return Inspection.editObjectInContext( selectedSite( session() ), context() );
	}

	/**
	 * Set the currently active site.
	 */
	public static void setSelectedSite( WOSession s, SWSite site ) {
		ERXSession session = (ERXSession)s;
		session.objectStore().takeValueForKey( site, "selectedSite" );
	}

	public static SWSite selectedSite( WOSession s ) {
		ERXSession session = (ERXSession)s;

		SWSite site = (SWSite)session.objectStore().valueForKey( "selectedSite" );

		if( site == null ) {
			SWUser user = SWSessionHelper.userInSession( s );

			if( user.defaultSite() != null ) {
				site = user.defaultSite();
			}
			else {
				NSArray<SWSite> sites = user.sites();

				if( USArrayUtilities.hasObjects( sites ) ) {
					site = sites.objectAtIndex( 0 );
				}
			}
		}

		return site;
	}
	/**
	 * Selects the current page and performs the action specified in "action"
	 */
	public WOActionResults selectPage() {
		setSelectedObject( currentPage );
		return performParentAction( pageAction );
	}

	/**
	 * The css class to set for a page link.
	 */
	public String currentClass() {

		if( !currentPage.isAccessible() ) {
			return "sw-inaccessible";
		}

		if( !currentPage.isPublished() ) {
			return "sw-unpublished";
		}

		return "sw-published";
	}

	/**
	 * Checks if the current branch is expanded or not, and returns either the
	 * page's subpages, or an empty array, based on that info.
	 */
	public NSArray<SWPage> theSubPages() {
		return isExpanded( currentPage ) ? currentPage.sortedSubPages() : NSArray.<SWPage>emptyArray();
	}

	/**
	 * Toggles the subpage display status for the current page, if it's subpages should be displayed or not.
	 */
	public WOActionResults toggleBranch() {

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
	public String img() {
		if( currentPage.hasNoChildren() ) {
			return WOApplication.application().resourceManager().urlForResourceNamed( SPACER_GIF, Concept.sw().frameworkBundleName(), null, context().request() );
		}

		if( isExpanded( currentPage ) ) {
			return WOApplication.application().resourceManager().urlForResourceNamed( MINUS_GIF, Concept.sw().frameworkBundleName(), null, context().request() );
		}

		return WOApplication.application().resourceManager().urlForResourceNamed( PLUS_GIF, Concept.sw().frameworkBundleName(), null, context().request() );
	}

	/**
	 * Determines if the link for the current page should be disabled.
	 */
	public boolean pageIsDisabled() {

		if( recordToDisableSubPagesOf != null ) {
			if( currentPage.isSubPageOfPage( recordToDisableSubPagesOf, true ) ) {
				return true;
			}
		}

		if( StringUtilities.hasValue( requiredPrivilege ) ) {
			if( !conceptUser().hasPrivilegeFor( currentPage, requiredPrivilege ) ) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Determines if the current branch should be expanded (if subpages should be displayed)
	 */
	private boolean isExpanded( SWPage page ) {
		return SWSessionHelper.arrayWithKeyContainsObject( session(), SWPage.ENTITY_NAME, page );
	}

	/**
	 * Expands the current branch.
	 */
	private void expandBranch( SWPage page ) {
		SWSessionHelper.addObjectToArrayWithKey( session(), page, SWPage.ENTITY_NAME );
	}

	/**
	 * Collapses the current branch.
	 */
	private void collapseBranch( SWPage page ) {
		SWSessionHelper.removeObjectFromArrayWithKey( session(), page, SWPage.ENTITY_NAME );
	}

	/**
	 * Indicates if the site pop up menu should be shown.
	 */
	public boolean showSitePopUp() {
		return conceptUser().sites().count() > 1;
	}
}