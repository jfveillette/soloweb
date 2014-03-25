package concept.components.admin;

import com.webobjects.appserver.WOContext;

import concept.SWGenericComponent;
import concept.components.client.SWSearchField2;
import concept.data.SWComponent;

public class SWSearchAdmin2 extends SWGenericComponent {

	public SWSearchAdmin2( WOContext context ) {
		super( context );
	}

	@Override
	public void setCurrentComponent( SWComponent compo ) {
		super.setCurrentComponent( compo );
		currentComponent().setTemplateName( SWSearchField2.class.getSimpleName() );
	}
}