package concept.components.admin;

import com.webobjects.appserver.WOContext;

import concept.CPAdminComponent;

public class CPAdminComponentWrapper extends CPAdminComponent {

	public CPAdminComponentWrapper( WOContext context ) {
		super( context );
	}

	@Override
	public boolean synchronizesVariablesWithBindings() {
		return false;
	}
}