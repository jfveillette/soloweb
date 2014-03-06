package concept.components.admin;

import com.webobjects.appserver.WOComponent;
import com.webobjects.appserver.WOContext;
import com.webobjects.foundation.NSDictionary;
import com.webobjects.foundation.NSMutableDictionary;

import concept.data.SWPage;
import concept.util.CPLoc;

public class SWEditPagePrivileges extends WOComponent {

	public SWPage selectedPage;

	public SWEditPagePrivileges( WOContext context ) {
		super( context );
	}

	public NSDictionary privilegePairs() {
		NSMutableDictionary d = new NSMutableDictionary();
		d.setObjectForKey( "canDeletePage", CPLoc.string( "ppDeletePages", session() ) );
		d.setObjectForKey( "canManagePage", CPLoc.string( "ppModifyPage", session() ) );
		d.setObjectForKey( "canManageContent", CPLoc.string( "ppModifyContent", session() ) );
		d.setObjectForKey( "canManageUsers", CPLoc.string( "ppControlPrivileges", session() ) );
		return d.immutableClone();
	}
}