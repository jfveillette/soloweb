package concept.components.client;

import is.rebbi.wo.util.USArrayUtilities;

import com.webobjects.appserver.WOContext;
import com.webobjects.foundation.NSArray;

import concept.CPBaseComponent;
import concept.data.SWPage;

/**
 * A heavily CSS classed navigation menu for use with the CMS.
 * Can also be used as a breadcrumbtrail.
 */

public class CPNavigation extends CPBaseComponent {

	private static final String LIST_BINDING = "list";
	private static final String LEVEL_NORMAL = "swLevelNormal";
	private static final String LEVEL_OPEN = "swLevelOpen";
	private static final String LEVEL_CLOSED = "swLevelClosed";
	private static final String LEVEL_SELECTED = "swLevelSelected";
	private static final String LEVEL_FIRST_IN_LIST = "swLevelFirstInList";
	private static final String LEVEL_LAST_IN_LIST = "swLevelLastInList";

	public SWPage currentObject;
	public int currentIndex;

	public CPNavigation( WOContext context ) {
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

	public NSArray<SWPage> list() {
		return (NSArray<SWPage>)valueForBinding( LIST_BINDING );
	}

	public String currentClass() {

		StringBuilder b = new StringBuilder();

		if( currentIndex == 0 ) {
			b.append( LEVEL_FIRST_IN_LIST );
			b.append( " " );
		}

		if( currentObject.parent() == null || currentIndex == currentObject.parent().sortedAndApprovedSubPages().count() - 1 ) {
			b.append( LEVEL_LAST_IN_LIST );
			b.append( " " );
		}

		if( currentObject.equals( selectedObject() ) ) {
			b.append( LEVEL_SELECTED );
			b.append( " " );
		}

		if( USArrayUtilities.hasObjects( currentObject.sortedAndApprovedSubPages() ) ) {
			if( subPageIsSelected() ) {
				b.append( LEVEL_OPEN );
			}
			else {
				b.append( LEVEL_CLOSED );
			}
		}
		else {
			b.append( LEVEL_NORMAL );
		}

		return b.toString();
	}

	/**
	 * @return true if a subpage of this page is currently selected.
	 */
	public boolean subPageIsSelected() {
		if( selectedObject() instanceof SWPage ) {
			boolean returnValue = ((SWPage)selectedObject()).isSubPageOfPage( currentObject, true );
			return returnValue;
		}

		return false;
	}
}