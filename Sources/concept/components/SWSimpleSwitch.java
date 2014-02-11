package concept.components;


import com.webobjects.appserver.WOContext;

import concept.SWGenericComponent;

public class SWSimpleSwitch extends SWGenericComponent {

	public int count;
	public int index;

	public SWSimpleSwitch( WOContext context ) {
		super( context );
	}

	public String itemClass() {
		String klass = "item";
		klass += " nr" + currentComponent().primaryKey();
		klass += " " + currentComponent().templateName().toLowerCase();
		if( index == 0 ) {
			klass += " first";
		}
		else if( index == count - 1 ) {
			klass += " last";
		}
		return klass;
	}
}