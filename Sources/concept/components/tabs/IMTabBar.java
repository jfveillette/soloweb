package concept.components.tabs;

import is.rebbi.wo.util.IMTab;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOContext;
import com.webobjects.foundation.NSArray;

import er.extensions.components.ERXComponent;

public class IMTabBar extends ERXComponent {

	public NSArray<IMTab> tabs;
	public IMTab currentTab;
	private IMTab _selectedTab;

	public IMTabBar( WOContext context ) {
		super( context );
	}

	public WOActionResults selectTab() {
		setSelectedTab( currentTab );
		return null;
	}

	public String currentTabClass() {
		String s = "";

		if( currentTab.equals( selectedTab() ) ) {
			s = s + "active";
		}

		if( currentTab.disabled() ) {
			s = s + " disabled";
		}

		return s;
	}

	public IMTab selectedTab() {
		if( _selectedTab == null && tabs.count() > 0 ) {
			_selectedTab = tabs.get( 0 );
		}

		return _selectedTab;
	}

	public void setSelectedTab( IMTab value ) {
		_selectedTab = value;
	}

	public String linkClass() {
		return currentTab.disabled() ? "disabled" : null;
	}
}