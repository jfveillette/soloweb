package concept.components.client;

import is.rebbi.core.util.StringUtilities;
import is.rebbi.wo.util.USArrayUtilities;
import is.rebbi.wo.util.USUtilities;

import com.webobjects.appserver.WOContext;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableDictionary;

import concept.SWGenericComponent;
import concept.data.SWPage;

/**
 * A heavily CSS-tagged navigation menu for use in SoloWeb.
 */

public class SWNavigation extends SWGenericComponent {

	public final static String LEVEL_NORMAL = "swLevelNormal";
	public final static String LEVEL_OPEN = "swLevelOpen";
	public final static String LEVEL_CLOSED = "swLevelClosed";
	public final static String LEVEL_SELECTED = "swLevelSelected";
	public final static String LEVEL_FIRST_IN_LIST = "swLevelFirstInList";
	public final static String LEVEL_LAST_IN_LIST = "swLevelLastInList";
	public final static String LEVEL_NUMBER = "swLevel";

	public SWPage currentPage;
	public int currentIndex;
	public boolean putClassOnList = false;

	public SWNavigation( WOContext context ) {
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

	@Override
	public void reset() {}

	public boolean getTruncatePageNames() {
		return USUtilities.booleanFromObject( valueForBinding( "truncatePageNames" ) );
	}

	@Override
	public SWPage selectedPage() {
		return (SWPage)valueForBinding( "selectedPage" );
	}

	public NSArray list() {
		return (NSArray)valueForBinding( "list" );
	}

	public boolean isNotSelected() {
		return currentPage.equals( selectedPage() );
	}

	public String currentClass() {

		StringBuffer b = new StringBuffer();

		if( currentIndex == 0 ) {
			b.append( LEVEL_FIRST_IN_LIST );
			b.append( " " );
		}

		if( currentPage.parent() == null || currentIndex == currentPage.parent().sortedAndApprovedSubPages().count() - 1 ) {
			b.append( LEVEL_LAST_IN_LIST );
			b.append( " " );
		}

		if( currentPage.equals( selectedPage() ) ) {
			b.append( LEVEL_SELECTED );
			b.append( " " );
		}

		if( USArrayUtilities.hasObjects( currentPage.sortedAndApprovedSubPages() ) ) {
			if( selectedPage().isSubPageOfPage( currentPage, true ) ) {
				b.append( LEVEL_OPEN );
			}
			else {
				b.append( LEVEL_CLOSED );
			}
			b.append( " " );
		}
		else {
			b.append( LEVEL_NORMAL );
			b.append( " " );
		}

		int level = currentPage.everyParentPage().count() - 1;
		b.append( LEVEL_NUMBER );
		b.append( level );

		return b.toString();
	}

	public String currentID() {
		return "sw_" + currentPage.primaryKey();
	}

	public String holderClass() {
		return "holder " + currentClass();
	}

	public String currentURL() {

		if( currentPage == null ) {
			return null;
		}

		String look = context().request().stringFormValueForKey( "look" );

		if( StringUtilities.hasValue( look ) ) {
			NSMutableDictionary d = new NSMutableDictionary();
			d.setObjectForKey( look, "look" );
			d.setObjectForKey( Integer.valueOf( currentPage.primaryKey() ), "id" );
			return context().directActionURLForActionNamed( "dp", d );
		}

		return "/id/" + currentPage.primaryKey();
	}

	public boolean subPageIsSelected() {
		return selectedPage().isSubPageOfPage( currentPage, true );
	}

	public String currentPageName() {
		if( getTruncatePageNames() ) {
			String[] a = currentPage.name().split( ";" );
			return a[0];
		}
		else {
			return currentPage.name();
		}
	}
}