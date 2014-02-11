package concept.components.admin;


import com.webobjects.appserver.WOComponent;
import com.webobjects.appserver.WOContext;
import com.webobjects.foundation.NSArray;

import concept.SWAdminComponent;
import concept.data.SWSite;

public class SWEditSiteGeneralInfo extends SWAdminComponent {

	public SWSite selectedSite;
	public String currentClass;

	public SWEditSiteGeneralInfo( WOContext context ) {
		super( context );
	}

	public NSArray navigationClasses() {
		return NSArray.componentsSeparatedByString( "default,custom", "," );
	}

	/**
	 * Deletes the selected site
	 */
	public WOComponent deleteSite() {
		session().defaultEditingContext().deleteObject( selectedSite );
		selectedSite = null;
		return saveChanges();
	}

	/**
	 * Saves changes made in this session to the DB
	 */
	public WOComponent saveChanges() {
		session().defaultEditingContext().saveChanges();
		return null;
	}
}