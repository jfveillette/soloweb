package concept.components.admin;
import is.rebbi.wo.util.SWSettings;

import com.webobjects.appserver.WOComponent;
import com.webobjects.appserver.WOContext;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSDictionary;
import com.webobjects.foundation.NSMutableDictionary;

import concept.SWAdminComponent;
import concept.SWApplication;
import concept.SWSession;
import concept.data.SWSite;
import concept.deprecated.SWLoc;

/**
 * SWEditSite is used to edit information for an SWSite
 */

public class SWEditSite extends SWAdminComponent {

    public String selectedTab = SWLoc.string( "eSiteTabGeneral", session() );
    public NSArray tabs = tabDictionary().allKeys();

    public SWEditSite(WOContext context) {
        super(context);
    }

    public NSDictionary tabDictionary() {
        NSMutableDictionary activeSiteEditingComponents = new NSMutableDictionary( SWApplication.swapplication().activeSiteEditingComponents() );
        activeSiteEditingComponents.setObjectForKey( "SWEditSiteGeneralInfo", 	SWLoc.string( "eSiteTabGeneral", session() ) );

        if( SWSettings.booleanForKey( "enablePrivileges" ) ) {
			activeSiteEditingComponents.setObjectForKey( "SWEditSitePrivileges", SWLoc.string( "eSiteTabAccessPrivileges", session() ) );
		}

        Integer intAdmin = ((SWSession)session()).activeUser().administrator();
        boolean isAdmin = (intAdmin != null && intAdmin.intValue() == 1 ? true : false);
        if (isAdmin /* && "SWDefaultLook6".equals(selectedSite().look()) */) {
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
        return (SWSite)session().valueForKey( "selectedSite" );
    }

    /**
     * Sets the selected site.
     */
    public void setSelectedSite( SWSite newSelectedSite ) {
        session().takeValueForKey( newSelectedSite, "selectedSite" );
    }


    /**
	 * Saves changes made in this session to the DB
     */
    public WOComponent saveChanges() {
    	session().defaultEditingContext().saveChanges();
        return null;
    }
}