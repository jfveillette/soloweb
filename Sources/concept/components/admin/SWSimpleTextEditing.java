package concept.components.admin;

import com.webobjects.appserver.WOComponent;
import com.webobjects.appserver.WOContext;

public class SWSimpleTextEditing extends WOComponent {

	public String content;

	public SWSimpleTextEditing( WOContext context ) {
		super( context );
	}
}