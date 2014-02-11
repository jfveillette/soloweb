package concept.components;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOContext;

import er.extensions.components.ERXNonSynchronizingComponent;

public class ViewModeSwitch extends ERXNonSynchronizingComponent {

	public String updateContainerID() {
		return stringValueForBinding( "updateContainerID" );
	}

	public ViewModeSwitch( WOContext context ) {
		super( context );
	}

	public boolean table() {
		return booleanValueForBinding( "table" );
	}

	public void setTable( boolean value ) {
		setValueForBinding( value, "table" );
	}

	public WOActionResults toggle() {
		setTable( !table() );
		return null;
	}

	public String tableButtonClass() {
		return table() ? "btn btn-sm btn-default active" : "btn btn-sm btn-default ";
	}

	public String buttonsButtonClass() {
		return table() ? "btn btn-sm btn-default " : "btn btn-sm btn-default active";
	}
}