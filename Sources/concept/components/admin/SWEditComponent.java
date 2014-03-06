package concept.components.admin;

import com.webobjects.appserver.WOComponent;
import com.webobjects.appserver.WOContext;
import com.webobjects.appserver.WOResponse;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSDictionary;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSMutableDictionary;

import concept.Concept;
import concept.SWAdminComponent;
import concept.SWSessionHelper;
import concept.components.client.ButurTemplate001;
import concept.components.client.ButurTemplate002;
import concept.components.client.ButurTemplate003;
import concept.components.client.ButurTemplate004;
import concept.components.client.SWImageGallery;
import concept.components.client.SWMedia;
import concept.components.client.SWNewsSubmit;
import concept.components.client.SWSFComponent;
import concept.components.client.SWSFFileList;
import concept.components.client.SWSearchField;
import concept.components.client.SWSearchField2;
import concept.components.client.SWSearchResults2;
import concept.components.client.SWSideBySide;
import concept.components.client.SWSlider;
import concept.components.client.SWYouTubeList;
import concept.components.client.SWYouTubePlayer;
import concept.components.client.SoloNewsNewsList;
import concept.data.SWComponent;
import concept.util.SWLoc;
import er.extensions.appserver.ERXSession;

/**
 * SWEditComponent is displayed when editing components, and contains among other things
 * the tabs to select a new component type.
 */

public class SWEditComponent extends SWAdminComponent {

	public String DEFAULT_TAB_NAME = SWLoc.string( "cpteContent", session() );
	public static final String COMPONENT_ARRAY_KEY = "SWComponent";

	/**
	 * The component currently being edited
	 */
	public SWComponent currentComponent;

	/**
	 * Name of the currently selected tab in the component type tab panel
	 */
	public String tabPanelSelection;

	/**
	 * The tab currently being iterated over in the panel
	 */
	public String currentType;

	public SWEditComponent( WOContext context ) {
		super( context );
	}

	@Override
	public void appendToResponse( WOResponse r, WOContext c ) {
		if( tabPanelSelection == null ) {
			tabPanelSelection = DEFAULT_TAB_NAME;
		}

		super.appendToResponse( r, c );
	}

	/**
	 * All possible types of components and their corresponding editing components
	 */
	public NSDictionary<String,String> projectTypes() {
		NSMutableDictionary<String,String> activeComponents = new NSMutableDictionary<>( Concept.sw().activeComponents() );
		activeComponents.setObjectForKey( SWEditStandardComponent.class.getSimpleName(), SWLoc.string( "cpteContent", session() ) );
		activeComponents.setObjectForKey( SWNewsAdminComponent.class.getSimpleName(), SWLoc.string( "cpteNews", session() ) );
		activeComponents.setObjectForKey( SWSFAdminComponent.class.getSimpleName(), SWLoc.string( "cpteDocuments", session() ) );
//		activeComponents.setObjectForKey( SWSAdminComponent.class.getSimpleName(), "SoloStaff" );
		activeComponents.setObjectForKey( SWImageGalleryAdmin.class.getSimpleName(), "Myndir" );
		activeComponents.setObjectForKey( SWSideBySideAdmin.class.getSimpleName(), "Multi" );
		activeComponents.setObjectForKey( SWMediaAdmin.class.getSimpleName(), "Media" );
		activeComponents.setObjectForKey( SWSearchAdmin.class.getSimpleName(), "Leit" );
		activeComponents.setObjectForKey( SWSearchAdmin2.class.getSimpleName(), "Leit2" );
		activeComponents.setObjectForKey( SWSearchResultsAdmin.class.getSimpleName(), "Leitarniðurstöður" );
//		activeComponents.setObjectForKey( SF4Admin.class.getSimpleName(), "SoloForms4" );
		activeComponents.setObjectForKey( SWSliderAdmin.class.getSimpleName(), "Slider" );
		activeComponents.setObjectForKey( SWYouTubePlayerAdmin.class.getSimpleName(), "YouTubePlay" );
		activeComponents.setObjectForKey( SWYouTubeListAdmin.class.getSimpleName(), "YouTubeList" );
		return activeComponents;
	}

	public NSDictionary<String,String> activeSystemsAndComponents() {
		NSMutableDictionary<String,String> activeSystemsAndComponents = new NSMutableDictionary<>( Concept.sw().activeSystemsAndComponents() );
		activeSystemsAndComponents.setObjectForKey( SWLoc.string( "cpteContent", session() ), ButurTemplate001.class.getSimpleName() );
		activeSystemsAndComponents.setObjectForKey( SWLoc.string( "cpteContent", session() ), ButurTemplate002.class.getSimpleName() );
		activeSystemsAndComponents.setObjectForKey( SWLoc.string( "cpteContent", session() ), ButurTemplate003.class.getSimpleName() );
		activeSystemsAndComponents.setObjectForKey( SWLoc.string( "cpteContent", session() ), ButurTemplate004.class.getSimpleName() );
		activeSystemsAndComponents.setObjectForKey( SWLoc.string( "cpteNews", session() ), SoloNewsNewsList.class.getSimpleName() );
		activeSystemsAndComponents.setObjectForKey( SWLoc.string( "cpteNews", session() ), SWNewsSubmit.class.getSimpleName() );
		activeSystemsAndComponents.setObjectForKey( SWLoc.string( "cpteDocuments", session() ), SWSFComponent.class.getSimpleName() );
		activeSystemsAndComponents.setObjectForKey( SWLoc.string( "cpteDocuments", session() ), SWSFFileList.class.getSimpleName() );
//		FIXME: Removed when combining Soloweb frmeworks & co activeSystemsAndComponents.setObjectForKey( "SoloStaff", SWSStaffListComponent.class.getSimpleName() );
		activeSystemsAndComponents.setObjectForKey( "Myndir", SWImageGallery.class.getSimpleName() );
		activeSystemsAndComponents.setObjectForKey( "Multi", SWSideBySide.class.getSimpleName() );
		activeSystemsAndComponents.setObjectForKey( "Media", SWMedia.class.getSimpleName() );
		activeSystemsAndComponents.setObjectForKey( "Leit", SWSearchField.class.getSimpleName() );
		activeSystemsAndComponents.setObjectForKey( "Leit2", SWSearchField2.class.getSimpleName() );
		activeSystemsAndComponents.setObjectForKey( "Leitarniðurstöður", SWSearchResults2.class.getSimpleName() );
//		activeSystemsAndComponents.setObjectForKey( "SoloForms4", SF4Butur.class.getSimpleName() );
		activeSystemsAndComponents.setObjectForKey( "Slider", SWSlider.class.getSimpleName() );
		activeSystemsAndComponents.setObjectForKey( "YouTubePlay", SWYouTubePlayer.class.getSimpleName() );
		activeSystemsAndComponents.setObjectForKey( "YouTubeList", SWYouTubeList.class.getSimpleName() );
		return activeSystemsAndComponents;
	}

	/**
	 * All tabs to display for component type selection
	 */
	public NSArray<String> types() {
		return projectTypes().allKeys();
	}

	/**
	 * Checks if the current component should be displayed in editing mode
	 */
	public boolean editingMode() {
		return SWSessionHelper.arrayWithKeyContainsObject( session(), COMPONENT_ARRAY_KEY, currentComponent );
	}

	/**
	 * The name of the component editing component to display.
	 */
	@Override
	public String componentName() {
		return projectTypes().objectForKey( currentType );
	}

	/**
	 * Toggles editing/preview mode for the current component.
	 */
	public WOComponent toggleMode() {

		if( SWSessionHelper.arrayWithKeyContainsObject( session(), COMPONENT_ARRAY_KEY, currentComponent ) ) {
			SWSessionHelper.removeObjectFromArrayWithKey( session(), currentComponent, COMPONENT_ARRAY_KEY );
		}
		else {
			selectTabForTemplateName( currentComponent.templateName() );
			((ERXSession)session()).objectStore().takeValueForKey( new NSMutableArray<>( currentComponent ), COMPONENT_ARRAY_KEY );
		}

		session().defaultEditingContext().saveChanges();

		return null;
	}

	/**
	 * Returns the name of the template to display for the current component.
	 * If a bogus templateName is specified, an SWErrorMessage is appended to the response instead.
	 */
	public String templateName() {
		try {
			pageWithName( currentComponent.templateName() );
			return currentComponent.templateName();
		}
		catch( Exception e ) {
			return "SWErrorMessage";
		}
	}

	public void selectTabForTemplateName( String aName ) {

		if( aName != null ) {
			tabPanelSelection = activeSystemsAndComponents().objectForKey( aName );

			if( tabPanelSelection == null ) {
				tabPanelSelection = DEFAULT_TAB_NAME;
			}
		}
		else {
			tabPanelSelection = DEFAULT_TAB_NAME;
		}
	}

	public WOComponent saveChanges() {
		session().defaultEditingContext().saveChanges();
		return null;
	}
}