package concept.components.admin.edit;

import is.rebbi.wo.util.USSortable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOContext;
import com.webobjects.foundation.NSBundle;
import com.webobjects.foundation.NSMutableArray;

import concept.Inspection;
import concept.ViewPage;
import concept.components.admin.SWErrorPage;
import concept.data.SWComponent;
import concept.data.SWPage;
import er.extensions.appserver.ERXSession;

/**
 * Edit the page's content
 */

public class CPEditPageContent extends ViewPage<SWPage> {

	private static final Logger logger = LoggerFactory.getLogger( CPEditPageContent.class );

	/**
	 * The current component being iterated over
	 */
	public SWComponent currentComponent;
	public int currentIndex;

	public CPEditPageContent( WOContext context ) {
		super( context );
	}

	/**
	 * Moves the current component up by one
	 */
	public WOActionResults moveComponentUp() {
		currentComponent.changeSortOrder( USSortable.UP );
		return saveChanges();
	}

	/**
	 * Moves the current component down by one
	 */
	public WOActionResults moveComponentDown() {
		currentComponent.changeSortOrder( USSortable.DOWN );
		return saveChanges();
	}

	/**
	 * Inserts a new component above all other components on the page.
	 */
	public WOActionResults insertComponentAbove() {
		if( currentComponent == null ) {
			return insertComponentAtIndex( 0 );
		}

		return insertComponentAtIndex( currentComponent.sortNumber().intValue() );
	}

	/**
	 * Inserts a new component above the current component
	 */
	public WOActionResults insertComponentBelow() {
		return insertComponentAtIndex( currentComponent.sortNumber().intValue() + 1 );
	}

	/**
	 * Creates a new component and inserts it at the specified index
	 *
	 * @param anInt The index to insert the component at.
	 */
	private WOActionResults insertComponentAtIndex( int anInt ) {
		SWComponent newComponent = new SWComponent();
		ec().insertObject( newComponent );
		selectedObject().insertComponentAtIndex( newComponent, anInt );
		ec().saveChanges();

		((ERXSession)session()).objectStore().takeValueForKey( new NSMutableArray<SWComponent>( newComponent ), SWComponent.class.getSimpleName() );

		return Inspection.editObjectInContext( newComponent, context() );
	}

	/**
	 * Deletes the current component
	 */
	public WOActionResults deleteComponent() {
		selectedObject().removeComponent( currentComponent );
		ec().deleteObject( currentComponent );
		return saveChanges();
	}

	/**
	 * Returns the name of the template to display for the current component. If
	 * a bogus templateName is specified, an SWAdminErrorMessage is appended to
	 * the response instead.
	 */
	public String templateName() {
		Class c = NSBundle.mainBundle()._classWithName( currentComponent.templateName() );

		if( c == null ) {
			logger.error( "No class available for template: " + currentComponent.templateName() );
			return SWErrorPage.class.getName();
		}

		return currentComponent.templateName();
	}

	public String componentMenuClass() {
		if( !currentComponent.isPublished() ) {
			return "sw-menu sw-unpbublishedcomponentmenu";
		}

		return "sw-menu";
	}
}