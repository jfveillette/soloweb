package concept.components.admin;

import is.rebbi.wo.util.SWSettings;

import com.webobjects.appserver.WOComponent;
import com.webobjects.appserver.WOContext;

import concept.SWAdminComponent;
import concept.data.SWPage;
import concept.data.SWSite;
import er.extensions.appserver.ERXSession;

/**
 * The site tree displayed to the left when you log into a SoloWeb system.
 */

public class SWSiteTree extends SWAdminComponent {

	/**
	 * The current site being iterated over
	 */
	public SWSite currentSite;

	/**
	 * The current page being iterated over
	 */
	public SWPage currentPage;

	public SWSiteTree( WOContext context ) {
		super( context );
	}

	/**
	 * Creates a new site and a frontpage for it.
	 */
	public WOComponent newSite() {
		SWSite site = SWSite.create( session().defaultEditingContext() );
		session().defaultEditingContext().saveChanges();
		((ERXSession)session()).objectStore().takeValueForKey( site, "selectedSite" );
		return null;
	}

	/**
	 * Indicates if the maximum number of sites for this SoloWeb system has been reached,
	 * as specified in the system settings.
	 */

	public boolean hasReachedMaxNumberOfSites() {
		return SWSite.allSites( session().defaultEditingContext() ).count() == new Integer( SWSettings.stringForKey( "numberOfLicensedSites", "1" ) ).intValue();
	}

	/**
	 * Selects the current page and displays it for editing in an SWEditPage component.
	 */
	public WOComponent selectObject() {
		SWEditPage nextPage = pageWithName( SWEditPage.class );
		nextPage.setSelectedPage( currentPage );
		return nextPage;
	}

	/**
	 * Selects the current site for editing in an SWEditSite component.
	 */
	public WOComponent selectSite() {
		SWEditSite nextPage = pageWithName( SWEditSite.class );
		nextPage.setSelectedSite( currentSite );
		return nextPage;
	}
}