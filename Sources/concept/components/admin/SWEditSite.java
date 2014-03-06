package concept.components.admin;

import is.rebbi.wo.util.SWSettings;

import com.webobjects.appserver.WOComponent;
import com.webobjects.appserver.WOContext;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSDictionary;
import com.webobjects.foundation.NSMutableDictionary;

import concept.Concept;
import concept.SWAdminComponent;
import concept.SWSessionHelper;
import concept.data.SWSite;
import concept.util.CPLoc;
import er.extensions.appserver.ERXSession;

/**
 * SWEditSite is used to edit information for an SWSite
 */

public class SWEditSite extends SWAdminComponent {

	public String selectedTab = CPLoc.string( "eSiteTabGeneral", session() );
	public NSArray tabs = tabDictionary().allKeys();

	public SWEditSite( WOContext context ) {
		super( context );
	}

	public NSDictionary tabDictionary() {
		NSMutableDictionary activeSiteEditingComponents = new NSMutableDictionary( Concept.sw().activeSiteEditingComponents() );
		activeSiteEditingComponents.setObjectForKey( "SWEditSiteGeneralInfo", CPLoc.string( "eSiteTabGeneral", session() ) );

		if( SWSettings.booleanForKey( "enablePrivileges" ) ) {
			activeSiteEditingComponents.setObjectForKey( "SWEditSitePrivileges", CPLoc.string( "eSiteTabAccessPrivileges", session() ) );
		}

		boolean isAdmin = SWSessionHelper.userInSession( session() ).isAdministrator();

		if( isAdmin /* && "SWDefaultLook6".equals(selectedSite().look()) */) {
			activeSiteEditingComponents.setObjectForKey( SWDefaultLook6Admin.class.getSimpleName(), "SWDefaultLook6" );
		}

		return activeSiteEditingComponents;
	}

	public String selectedEditingComponentName() {
		return (String)tabDictionary().valueForKey( selectedTab );
	}

	/**
	 * The selected site.
	 */
	public SWSite selectedSite() {
		return (SWSite)((ERXSession)session()).objectStore().valueForKey( "selectedSite" );
	}

	/**
	 * Sets the selected site.
	 */
	public void setSelectedSite( SWSite value ) {
		((ERXSession)session()).objectStore().takeValueForKey( value, "selectedSite" );
	}

	/**
	 * Saves changes made in this session to the DB
	 */
	public WOComponent saveChanges() {
		session().defaultEditingContext().saveChanges();
		return null;
	}
}