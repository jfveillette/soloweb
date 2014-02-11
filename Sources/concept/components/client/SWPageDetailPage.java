package concept.components.client;

import com.webobjects.appserver.WOContext;

import concept.ViewPage;
import concept.data.SWComponent;
import concept.data.SWPage;

public class SWPageDetailPage extends ViewPage<SWPage> {

	public SWComponent currentComponent;

	public SWPageDetailPage( WOContext context ) {
		super( context );
	}
}