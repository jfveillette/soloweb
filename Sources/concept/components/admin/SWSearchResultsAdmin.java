package concept.components.admin;

import com.webobjects.appserver.WOContext;

import concept.SWGenericComponent;
import concept.components.client.SWSearchResults2;
import concept.data.SWComponent;

public class SWSearchResultsAdmin extends SWGenericComponent {

	public SWSearchResultsAdmin( WOContext context ) {
		super( context );
	}

	@Override
	public void setCurrentComponent( SWComponent compo ) {
		super.setCurrentComponent( compo );
		currentComponent().setTemplateName( SWSearchResults2.class.getSimpleName() );
	}
}