package concept.components.admin;

import com.webobjects.appserver.WOContext;

import concept.SWGenericComponent;
import concept.data.SWComponent;

public class SWYouTubeListAdmin extends SWGenericComponent {

	public SWYouTubeListAdmin( WOContext context ) {
		super( context );
	}

	@Override
	public void setCurrentComponent( SWComponent newOne ) {
		super.setCurrentComponent( newOne );
		currentComponent().setTemplateName( "SWYouTubeList" );
	}
}