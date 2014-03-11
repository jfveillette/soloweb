package concept.components;

import is.rebbi.wo.components.BaseComponent;

import com.webobjects.appserver.WOContext;

public class ViewWrapper extends BaseComponent {

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