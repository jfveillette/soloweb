package concept.deprecated;

import is.rebbi.wo.util.USUtilities;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WORequest;

import concept.SWDirectAction;
import concept.components.SWSNRSSComponent;

/**
 * RSS support
 */

@Deprecated
public class SoloNews extends SWDirectAction {

	public SoloNews( WORequest aRequest ) {
		super( aRequest );
	}

	public WOActionResults rssAction() {
		SWSNRSSComponent nextPage = pageWithName( SWSNRSSComponent.class );

		Integer categoryID = new Integer( request().stringFormValueForKey( "category" ) );
		Integer count = USUtilities.integerFromObject( request().stringFormValueForKey( "count" ) );

		nextPage.setCategoryID( categoryID );

		if( count != null ) {
			nextPage.setCount( count.intValue() );
		}
		else {
			nextPage.setCount( 10 );
		}

		return nextPage;
	}
}