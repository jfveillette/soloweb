package concept.components.admin.edit;

import is.rebbi.wo.util.SWSettings;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOContext;
import com.webobjects.foundation.NSDictionary;
import com.webobjects.foundation.NSMutableDictionary;

import concept.Inspection;
import concept.SWSessionHelper;
import concept.ViewPage;
import concept.components.admin.SWReorderSubpages;
import concept.data.SWPage;
import concept.util.CPAccessPrivilegeUtilities;
import concept.util.CPLoc;
import er.extensions.appserver.ERXSession;

/**
 * SWEditPage is displayed when editing pages, and contains among other things
 * the tabs to select a new component type.
 */

public class CPEditPage extends ViewPage<SWPage> {

	// Localized strings
	private static final String EPT_GENERAL = "eptGeneral";
	private static final String EPT_CONTENT = "eptContent";
	private static final String EPT_ACCESS_PRIVILEGES = "eptAccessPrivileges";
	private static final String SELECTED_PAGE_TAB = "selectedTab";

	public CPEditPage( WOContext context ) {
		super( context );
	}

	public NSDictionary<String, String> stringTabs() {
		NSMutableDictionary<String, String> d = new NSMutableDictionary<>();

		if( conceptUser().hasPrivilegeFor( selectedObject(), CPAccessPrivilegeUtilities.PRIVILEGE_CAN_MANAGE_PAGE ) ) {
			d.setObjectForKey( CPEditPageGeneralInfo.class.getSimpleName(), CPLoc.string( EPT_GENERAL, context() ) );
		}

		if( conceptUser().hasPrivilegeFor( selectedObject(), CPAccessPrivilegeUtilities.PRIVILEGE_CAN_MANAGE_CONTENT ) ) {
			d.setObjectForKey( CPEditPageContent.class.getSimpleName(), CPLoc.string( EPT_CONTENT, context() ) );
		}

		if( SWSettings.privilegesEnabled() ) {
			if( conceptUser().hasPrivilegeFor( selectedObject(), CPAccessPrivilegeUtilities.PRIVILEGE_CAN_MANAGE_USERS ) ) {
				d.setObjectForKey( CPEditPagePrivileges.class.getSimpleName(), CPLoc.string( EPT_ACCESS_PRIVILEGES, context() ) );
			}
		}

		return d;
	}

	/**
	 * @return Name of edit component corresponding to currently selected tab.
	 */
	public String selectedComponentName() {

		if( !stringTabs().containsKey( selectedStringTab() ) ) {
			return stringTabs().objectForKey( stringTabs().allKeys().objectAtIndex( 0 ) );
		}

		return stringTabs().objectForKey( selectedStringTab() );
	}

	/**
	 * @return Name of the currently selected tab
	 */
	public String selectedStringTab() {
		String selected = (String)((ERXSession)session()).objectStore().valueForKey( SELECTED_PAGE_TAB );

		if( selected == null ) {
			selected = CPLoc.string( EPT_CONTENT, context() );
		}

		return selected;
	}

	/**
	 * Sets the selected tab
	 */
	public void setSelectedStringTab( String value ) {
		((ERXSession)session()).objectStore().takeValueForKey( value, SELECTED_PAGE_TAB );
	}

	/**
	 * Creates a new SWPage below all other subpages in the sortorder and
	 * expands the selected page's branch in the site tree.
	 */
	public WOActionResults createSubPage() {
		SWPage p = selectedObject().createSubPage();

		if( !SWSessionHelper.arrayWithKeyContainsObject( session(), SWPage.class.getSimpleName(), selectedObject() ) ) {
			SWSessionHelper.addObjectToArrayWithKey( session(), selectedObject(), SWPage.class.getSimpleName() );
		}

		saveChanges();

		return Inspection.editObjectInContext( p, context() );
	}

	/**
	 * Deletes the selected page and all it's content
	 */
	public WOActionResults deletePage() {
		selectedObject().deletePage();

		setSelectedStringTab( CPLoc.string( EPT_GENERAL, context() ) );

		return saveChanges();
	}

	/**
	 * For re-ordering the subpages of the selected page.
	 */
	public WOActionResults reorderSubpages() {
		SWReorderSubpages nextPage = pageWithName( SWReorderSubpages.class );
		nextPage.setSelectedObject( selectedObject() );
		nextPage.componentToReturnTo = this;
		return nextPage;
	}
}