package concept.components.admin;

import com.webobjects.appserver.WOContext;

import concept.SWAdminComponent;
import concept.data.SWComponent;

/**
 * For editing the standard, built in SoloWeb components (ButurTemplate00x)
 */

public class SWEditStandardComponent extends SWAdminComponent {

	public SWComponent currentComponent;

	public SWEditStandardComponent( WOContext context ) {
		super( context );
	}
}