package concept.components.admin;


import com.webobjects.appserver.WOComponent;
import com.webobjects.appserver.WOContext;
import com.webobjects.foundation.NSArray;

import concept.SWAdminComponent;
import concept.data.SWSite;

public class SWMySettings extends SWAdminComponent {

	public SWSite currentSite;

	public SWMySettings( WOContext context ) {
		super( context );
	}

	public WOComponent saveChanges() {
		session().defaultEditingContext().saveChanges();
		return context().page();
	}

	public NSArray allSites() {
		return SWSite.allSites( session().defaultEditingContext() );
	}
}