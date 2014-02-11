package concept.components.admin;


import com.webobjects.appserver.WOContext;

import concept.SWGenericComponent;
import concept.components.client.SWSearchField;
import concept.data.SWComponent;

public class SWSearchAdmin extends SWGenericComponent {

	public SWSearchAdmin( WOContext context ) {
		super( context );
	}

	@Override
	public void setCurrentComponent( SWComponent compo ) {
		super.setCurrentComponent( compo );
		currentComponent().setTemplateName( SWSearchField.class.getSimpleName() );
	}
}