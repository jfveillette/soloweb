package concept.components.admin;


import com.webobjects.appserver.WOComponent;
import com.webobjects.appserver.WOContext;
import com.webobjects.foundation.NSDictionary;
import com.webobjects.foundation.NSMutableDictionary;

import concept.data.SWSite;
import concept.deprecated.SWLoc;

public class SWEditSitePrivileges extends WOComponent {

	public SWSite selectedSite;

	public SWEditSitePrivileges( WOContext context ) {
		super( context );
	}

	public NSDictionary privilegePairs() {
		NSMutableDictionary d = new NSMutableDictionary();
		d.setObjectForKey( "allowToSee", SWLoc.string( "spHasAccess", session() ) );
		return d.immutableClone();
	}
}