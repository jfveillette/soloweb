package concept.components;

import com.webobjects.appserver.WOContext;

import concept.ViewPage;

public class ViewWrapper extends ViewPage {

	/**
	 * Name of the component currently being displayed.
	 */
	private String _displayComponentName;

	public ViewWrapper( WOContext context ) {
		super( context );
	}

	public String displayComponentName() {
		return _displayComponentName;
	}

	public void setDisplayComponentName( String displayComponentName ) {
		_displayComponentName = displayComponentName;
	}
}