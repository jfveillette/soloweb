package concept.components.admin;

import is.rebbi.wo.util.SWSettings;

import com.webobjects.appserver.WOContext;

import er.extensions.components.ERXNonSynchronizingComponent;

public class SWPrivilegesEnabled extends ERXNonSynchronizingComponent {

	public SWPrivilegesEnabled( WOContext context ) {
		super( context );
	}

	public boolean privilegesEnabled() {
		return SWSettings.privilegesEnabled();
	}
}