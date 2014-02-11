package concept.components.admin;


import com.webobjects.appserver.WOComponent;
import com.webobjects.appserver.WOContext;

import concept.SWGenericTransferComponent;
import concept.data.SWPage;

/**
 * A component for transferring a page to a new parent page
 */

public class SWTransferPage extends SWGenericTransferComponent {

	public int currentIndex;

	public SWTransferPage( WOContext context ) {
		super( context );
	}

	@Override
	public WOComponent selectObject() {
		if( !shouldCopy ) {
			return super.selectObject();
		}
		else {
			((SWPage)currentParent).insertSubPageAtIndex( ((SWPage)record).createCopy(), 0 );
			return returnBack();
		}
	}

	public WOComponent transferFirst() {
		if( !shouldCopy ) {
			((SWPage)record).transferOwnershipWithIndex( ((SWPage)currentParent).parent(), 0 );
		}
		else {
			((SWPage)currentParent).parent().insertSubPageAtIndex( ((SWPage)record).createCopy(), 0 );
		}

		return returnBack();
	}

	public WOComponent transferOther() {
		if( !shouldCopy ) {
			((SWPage)record).transferOwnershipWithIndex( ((SWPage)currentParent).parent(), currentIndex + 1 );
		}
		else {
			((SWPage)currentParent).parent().insertSubPageAtIndex( ((SWPage)record).createCopy(), currentIndex + 1 );
		}

		return returnBack();
	}

	public WOComponent returnBack() {
		session().defaultEditingContext().saveChanges();
		componentToReturn.ensureAwakeInContext( context() );
		return componentToReturn;
	}
}