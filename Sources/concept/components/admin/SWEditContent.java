package concept.components.admin;

import java.util.Enumeration;


import com.webobjects.appserver.WOComponent;
import com.webobjects.appserver.WOContext;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.foundation.NSMutableArray;

import concept.SWAdminComponent;
import concept.SWSession;
import concept.data.SWComponent;
import concept.data.SWPage;
import concept.data.SWPictureLink;

public class SWEditContent extends SWAdminComponent {

	/**
	 * The selected page
	 */
	public SWPage selectedPage;

	/**
	* The current component being iterated over
	*/
	public SWComponent currentComponent;

	public SWEditContent( WOContext context ) {
		super( context );
	}

	/**
	 * Moves the current component up by one
	 */
	public WOComponent buturUpp() {
		currentComponent.changeSortOrder( -1 );
		session().defaultEditingContext().saveChanges();
		return null;
	}

	/**
	 * Moves the current component down by one
	 */
	public WOComponent buturNidur() {
		currentComponent.changeSortOrder( 1 );
		session().defaultEditingContext().saveChanges();
		return null;
	}

	/**
	    * Inserts a new component above all other components on the page.
	    */
	public WOComponent insertComponentAbove() {
		if( currentComponent == null ) {
			return insertComponentAtIndex( 0 );
		}

		return insertComponentAtIndex( currentComponent.sortNumber().intValue() );
	}

	/**
	    * Inserts a new component above the current component
	    */
	public WOComponent insertComponentBelow() {
		return insertComponentAtIndex( currentComponent.sortNumber().intValue() + 1 );
	}

	/**
	    * Creates a new component and inserts it at the specified index
	    *
	    * @param anInt The index to insert the component at.
	    */
	public WOComponent insertComponentAtIndex( int anInt ) {
		SWComponent c = new SWComponent();
		session().defaultEditingContext().insertObject( c );

		selectedPage.insertComponentAtIndex( c, anInt );
		session().defaultEditingContext().saveChanges();

		((SWSession)session()).takeValueForKey( new NSMutableArray( c ), "SWComponent" );

		return null;
	}

	/**
	 * Deletes the current component
	 */
	public WOComponent deleteComponent() {
		EOEditingContext ec = session().defaultEditingContext();
		Enumeration<SWPictureLink> e = currentComponent.swPictureLinks().objectEnumerator();
		while( e.hasMoreElements() ) {
			ec.deleteObject( e.nextElement() );
		}
		selectedPage.removeComponent( currentComponent );
		ec.deleteObject( currentComponent );
		ec.saveChanges();

		return null;
	}

	/**
	 * Returns a color of red if the current component is not published.
	 */
	public String tdBGColor() {
		return currentComponent.isPublished() ? null : "#ff6666";
	}
}