package concept.components.client;

import com.webobjects.appserver.WOContext;

import concept.SWBaseComponent;

/**
 * 404!
 */

public class SW404 extends SWBaseComponent {

	public SW404( WOContext context ) {
		super( context );
	}

	@Override
	public boolean synchronizesVariablesWithBindings() {
		return false;
	}

	@Override
	public boolean isStateless() {
		return true;
	}
}