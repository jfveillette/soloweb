package concept.components.admin;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOComponent;
import com.webobjects.appserver.WOContext;
import com.webobjects.foundation.NSArray;

public class SWTabPanel extends WOComponent {

	public static final String ACTIVE_TAB_CLASSNAME = "activeTab";
	public static final String INACTIVE_TAB_CLASSNAME = "inactiveTab";

	public String currentTab;
	public Object selectedTab;
	public String submitActionName;

	public SWTabPanel( WOContext context ) {
		super( context );
	}

	public NSArray tabs() {
		return (NSArray)valueForBinding( "tabs" );
	}

	public String submitActionName() {
		return (String)valueForBinding( "submitActionName" );
	}

	public WOActionResults selectTab() {

		setSelectedTab( currentTab );

		if( submitActionName() != null ) {
			return performParentAction( submitActionName() );
		}

		return null;
	}

	public String currentStyleClass() {
		return currentTab.equals( selectedTab() ) ? ACTIVE_TAB_CLASSNAME : INACTIVE_TAB_CLASSNAME;
	}

	public void setSelectedTab( String newSelectedTab ) {
		selectedTab = newSelectedTab;
		setValueForBinding( selectedTab, "selectedTab" );
	}

	public Object selectedTab() {

		setSelectedTab( (String)valueForBinding( "selectedTab" ) );

		if( selectedTab == null ) {
			setSelectedTab( (String)tabs().objectAtIndex( 0 ) );
		}

		return selectedTab;
	}

	@Override
	public boolean synchronizesVariablesWithBindings() {
		return false;
	}

	public boolean hasMultipleTabs() {
		Boolean showOneTabAsMany = (Boolean)valueForBinding( "showOneTabAsMany" );
		return (tabs().count() > 1) || (showOneTabAsMany != null && showOneTabAsMany.booleanValue() == true);
	}
}