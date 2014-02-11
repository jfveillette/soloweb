package concept.components.admin;

import com.webobjects.appserver.WOContext;

import concept.CPAdminComponent;

/**
 * Most back end components are wrapped in this component.
 */

public class CPAdminComponentWrapper extends CPAdminComponent {

	public CPAdminComponentWrapper( WOContext context ) {
		super( context );
	}

	@Override
	public boolean synchronizesVariablesWithBindings() {
		return false;
	}

	@Override
	public boolean requiresLogin() {
		return false;
	}
}