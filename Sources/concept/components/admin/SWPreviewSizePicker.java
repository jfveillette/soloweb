package concept.components.admin;

import com.webobjects.appserver.WOContext;
import com.webobjects.foundation.NSArray;

import er.extensions.components.ERXComponent;
import er.extensions.foundation.ERXArrayUtilities;

public class SWPreviewSizePicker extends ERXComponent {

	public NSArray<String> available;
	public NSArray<String> selected;
	public String current;

	public SWPreviewSizePicker( WOContext context ) {
		super( context );
	}

	public boolean checked() {
		return selected.containsObject( current );
	}

	public void setChecked( boolean checked ) {
//		System.out.println( current + ": " + checked  );

		if( checked && !selected.containsObject( current ) ) {
			selected = selected.arrayByAddingObject( current );
		}
		else if( !checked && selected.containsObject( current ) ) {
			selected = ERXArrayUtilities.arrayMinusObject( selected, current );
		}

//		System.out.println( selected );
	}
}