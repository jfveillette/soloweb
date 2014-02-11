package concept.components;


import com.webobjects.appserver.WOContext;

import concept.SWGenericComponent;

public class SWSingularPlural extends SWGenericComponent {

	/* bindings */
	public int number;
	public String singular = "", plural = "";
	public boolean escapeHTML = true;

	public SWSingularPlural( WOContext context ) {
		super( context );
	}

	public String getTheString() {
		String str = plural;
		String num = new Integer( number ).toString();
		if( num.endsWith( "1" ) ) {
			if( !num.endsWith( "11" ) ) {
				str = singular;
			}
		}
		return str;
	}
}