package concept.components.admin;

import com.webobjects.appserver.WOComponent;
import com.webobjects.appserver.WOContext;
import com.webobjects.foundation.NSDictionary;
import com.webobjects.foundation.NSMutableDictionary;

import concept.data.SWPage;
import concept.util.SWLoc;

public class SWEditPagePrivileges extends WOComponent {

	public SWPage selectedPage;

	public SWEditPagePrivileges( WOContext context ) {
		super( context );
	}

	public NSDictionary privilegePairs() {
		NSMutableDictionary d = new NSMutableDictionary();
		d.setObjectForKey( "canDeletePage", SWLoc.string( "ppDeletePages", session() ) );
		d.setObjectForKey( "canManagePage", SWLoc.string( "ppModifyPage", session() ) );
		d.setObjectForKey( "canManageContent", SWLoc.string( "ppModifyContent", session() ) );
		d.setObjectForKey( "canManageUsers", SWLoc.string( "ppControlPrivileges", session() ) );
		return d.immutableClone();
	}
}