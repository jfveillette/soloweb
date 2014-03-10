package concept.components.client;


import com.webobjects.appserver.WOContext;

import concept.ViewPage;
import concept.data.SWUser;

public class SWUserDetailPage extends ViewPage<SWUser> {

	public SWUserDetailPage( WOContext context ) {
		super( context );
	}
}