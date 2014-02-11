package concept;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WORequest;

import concept.components.user.SWPasswordResetPage;
import er.extensions.appserver.ERXDirectAction;

public class SWPasswordResetAction extends ERXDirectAction {

	public SWPasswordResetAction( WORequest r ) {
		super( r );
	}

	@Override
	public WOActionResults defaultAction() {
		String key = request().stringFormValueForKey( "key" );
		SWPasswordResetPage nextPage = pageWithName( SWPasswordResetPage.class );
		nextPage.setKey( key );
		return nextPage;
	}
}