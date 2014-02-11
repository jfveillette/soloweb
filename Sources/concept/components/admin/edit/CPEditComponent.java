package concept.components.admin.edit;

import com.webobjects.appserver.WOContext;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSDictionary;
import com.webobjects.foundation.NSMutableDictionary;

import concept.ViewPage;
import concept.components.client.SWComponentDetail;
import concept.components.client.SWDocumentDetail;
import concept.components.client.SWDocumentList;
import concept.components.client.SWNewsList;
import concept.data.SWComponent;
import concept.util.CPLoc;

public class CPEditComponent extends ViewPage<SWComponent> {

	private final String _defaultTabName = CPLoc.string( "cpteContent", context() );

	/**
	 * Name of the currently selected tab in the component type tab panel
	 */
	private String _tabPanelSelection;

	/**
	 * The tab currently being iterated over in the panel
	 */
	public String currentType;

	public CPEditComponent( WOContext context ) {
		super( context );
	}

	public String tabPanelSelection() {
		if( _tabPanelSelection == null ) {
			_tabPanelSelection = activeSystemsAndComponents().objectForKey( selectedObject().templateName() );

			if( _tabPanelSelection == null ) {
				_tabPanelSelection = _defaultTabName;
			}
		}

		return _tabPanelSelection;
	}

	public void setTabPanelSelection( String s ) {
		_tabPanelSelection = s;
	}

	/**
	 * All possible types of components and their corresponding editing components
	 */
	public NSDictionary<String, String> projectTypes() {
		NSMutableDictionary<String, String> d = new NSMutableDictionary<String, String>();
		d.setObjectForKey( SWCAText.class.getName(), CPLoc.string( "cpteContent", context() ) );
		d.setObjectForKey( SWCANews.class.getName(), CPLoc.string( "cpteNews", context() ) );
		d.setObjectForKey( SWCADocuments.class.getName(), CPLoc.string( "cpteDocuments", context() ) );
		d.setObjectForKey( SWCAGeneric.class.getName(), "Generic" );
		return d;
	}

	public NSDictionary<String, String> activeSystemsAndComponents() {
		NSMutableDictionary<String, String> d = new NSMutableDictionary<String, String>();
		d.setObjectForKey( CPLoc.string( "cpteContent", context() ), SWComponentDetail.class.getSimpleName() );
		d.setObjectForKey( CPLoc.string( "cpteNews", context() ), SWNewsList.class.getSimpleName() );
		d.setObjectForKey( CPLoc.string( "cpteDocuments", context() ), SWDocumentDetail.class.getSimpleName() );
		d.setObjectForKey( CPLoc.string( "cpteDocuments", context() ), SWDocumentList.class.getSimpleName() );
		return d;
	}

	/**
	 * All tabs to display for component type selection
	 */
	public NSArray<String> types() {
		return projectTypes().allKeys();
	}

	/**
	 * The name of the component editing component to display.
	 */
	@Override
	public String componentName() {
		return projectTypes().objectForKey( currentType );
	}
}