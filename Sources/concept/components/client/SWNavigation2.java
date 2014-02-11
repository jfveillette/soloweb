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

public class SWNavigation2 extends SWGenericComponent {

	public final static String LEVEL_NORMAL = "normal";
	public final static String LEVEL_OPEN = "open";
	public final static String LEVEL_CLOSED = "closed";
	public final static String LEVEL_SELECTED = "selected";
	public final static String LEVEL_FIRST_IN_LIST = "first";
	public final static String LEVEL_LAST_IN_LIST = "last";
	public final static String LEVEL_NUMBER = "level";

	public SWPage _currentPage;
	public int currentIndex;

	public SWNavigation2( WOContext context ) {
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

	@Override
	public SWPage selectedPage() {
		return (SWPage)valueForBinding( "selectedPage" );
	}

	public NSArray list() {
		return (NSArray)valueForBinding( "list" );
	}

	public Integer level() {
		return (Integer)valueForBinding( "level" );
	}

	public boolean isNotSelected() {
		return _currentPage.equals( selectedPage() );
	}

	public String currentClass() {

		StringBuffer b = new StringBuffer();
		b.append( "nav" );
		b.append( " " );

		if( currentIndex == 0 ) {
			b.append( LEVEL_FIRST_IN_LIST );
			b.append( " " );
		}

		if( _currentPage.parent() == null || currentIndex == _currentPage.parent().sortedAndApprovedSubPages().count() - 1 ) {
			b.append( LEVEL_LAST_IN_LIST );
			b.append( " " );
		}

		if( _currentPage.equals( selectedPage() ) ) {
			b.append( LEVEL_SELECTED );
			b.append( " " );
		}

		if( USArrayUtilities.hasObjects( _currentPage.sortedAndApprovedSubPages() ) ) {
			if( selectedPage().isSubPageOfPage( _currentPage, true ) || alwaysOpen() ) {
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

		b.append( LEVEL_NUMBER );
		b.append( _currentPage.everyParentPage().count() - 1 );

		return b.toString();
	}

	public String currentURL() {
		String url = null;

		if( _currentPage != null ) {
			String look = context().request().stringFormValueForKey( "look" );
			String symbol = _currentPage.symbol(); //hlekkjunarheiti

			if( StringUtilities.hasValue( look ) ) {
				NSMutableDictionary d = new NSMutableDictionary();
				d.setObjectForKey( look, "look" );
				d.setObjectForKey( Integer.valueOf( _currentPage.primaryKey() ), "id" );
				url = context().directActionURLForActionNamed( "dp", d );

			}
			else if( StringUtilities.hasValue( _currentPage.externalURL() ) ) {
				url = _currentPage.externalURL();
			}
			else if( StringUtilities.hasValue( symbol ) ) {
				url = "/page/" + symbol;

			}
			else {
				url = "/id/" + _currentPage.primaryKey();
			}
		}
		return url;
	}

	public String currentTarget() {
		String target = null;
		if( USUtilities.booleanFromObject( _currentPage.customInfo().valueForKey( "externalUrlInNewWindow" ) ) ) {
			target = "_blank";
		}
		return target;
	}

	public boolean subPageIsSelected() {
		return selectedPage().isSubPageOfPage( _currentPage, true );
	}

	public boolean subPageIsOpen() {
		boolean open = selectedPage().isSubPageOfPage( _currentPage, true ); //subPageIsSelected();
		open = open || alwaysOpen();
		return open;
	}

	public String ulClass() {
		return "navigation";
	}

	public String ulId() {
		String id = "navigation" + level();
		return id;
	}

	public Integer nextLevel() {
		return new Integer( level().intValue() + 1 );
	}

	private boolean alwaysOpen() {
		return USUtilities.booleanFromObject( _currentPage.customInfo().valueForKey( "alwaysOpen" ) );
	}

	public String currentPageName() {
		String pageName = _currentPage.name();
		if( pageName != null ) {
			int semiIndex = pageName.indexOf( ';' );
			if( pageName.indexOf( ';' ) > -1 ) {
				pageName = pageName.substring( 0, semiIndex );
			}
		}
		else {
			pageName = "";
		}
		return pageName;
	}
}