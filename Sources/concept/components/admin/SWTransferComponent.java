package concept.components.admin;


import com.webobjects.appserver.WOComponent;
import com.webobjects.appserver.WOContext;

import concept.SWGenericTransferComponent;
import concept.data.SWComponent;
import concept.data.SWPage;

/**
 * A component for transferring of an SWComponent to a new page
 */

public class SWTransferComponent extends SWGenericTransferComponent {

	public boolean shouldCopy = false;

	public SWTransferComponent( WOContext context ) {
		super( context );
	}

	@Override
	public WOComponent selectObject() {
		if( !shouldCopy ) {
			return super.selectObject();
		}
		else {
			((SWPage)currentParent).insertComponentAtIndex( ((SWComponent)record).createCopy(), 0 );
			session().defaultEditingContext().saveChanges();
			return componentToReturn;
		}
	}
}