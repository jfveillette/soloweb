package concept.components.admin.edit;

import is.rebbi.wo.util.USUtilities;

import java.util.Enumeration;

import com.webobjects.appserver.WOContext;
import com.webobjects.appserver.WOResponse;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOFetchSpecification;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableDictionary;

import concept.CPGenericComponent;
import concept.components.client.SWNewsList;
import concept.data.SWFolder;
import er.extensions.eof.ERXEOControlUtilities;

/**
 * Component admin for news components.
 */

public class SWCANews extends CPGenericComponent {

	private EOEditingContext ec = session().defaultEditingContext();

	/**
	 * A variable the iteration in the categories pop-up-menu
	 */
	public SWFolder currentFolder;

	/**
	 * Default constructor
	 */
	public SWCANews( WOContext context ) {
		super( context );
	}

	/**
	 * We set the Template Name for this component in appendToResponse,
	 * so the correct template is automatically selected when the SoloNews tab is selected.
	 */
	@Override
	public void appendToResponse( WOResponse r, WOContext c ) {
		currentComponent().setTemplateName( SWNewsList.class.getSimpleName() );
		super.appendToResponse( r, c );
	}

	/**
	 * Returns all SWNewsFolder Objects
	 */
	public NSArray<SWFolder> allCategories() {
		EOFetchSpecification fs = new EOFetchSpecification( SWFolder.ENTITY_NAME, null, null );
		return ec.objectsWithFetchSpecification( fs );
	}

	/**
	 * This method retrieves the "category" selection from the customInfo Dictionary in
	 * the component, and selects the folder object with the corresponding primary key.
	 */
	public SWFolder selectedFolder() {
		Integer folderID = USUtilities.integerFromObject( currentComponent().customInfo().valueForKey( "category" ) );

		if( folderID != null ) {
			return (SWFolder)ERXEOControlUtilities.objectWithPrimaryKeyValue( ec, SWFolder.ENTITY_NAME, folderID, NSArray.emptyArray() );
		}

		return null;
	}

	public void setSelectedFolder( SWFolder folder ) {
		if( folder == null ) {
			currentComponent().customInfo().takeValueForKey( null, "category" );
		}
		else {
			currentComponent().customInfo().takeValueForKey( folder.primaryKey(), "category" );
		}
	}

	/**
	 * Possible image alignments.
	 */
	public NSArray<String> imageAlignment() {
		return new NSArray<String>( "left", "right" );
	}

	/**
	 * Possible sort fields.
	 */
	public NSArray<String> sortKeys() {
		return new NSArray<String>( "By date", "Alphabetically", "Random" );
	}

	/**
	 * Possible sort directions.
	 */
	public NSArray<String> sortOrderings() {
		return new NSArray<String>( "Descending", "Ascending" );
	}

	/**
	 * TODO: Review!
	 */
	public NSArray<String> componentKeys() {
		return componentsAndKeys().allKeys();
	}

	public NSMutableDictionary<String, String> componentsAndKeys() {
		NSMutableDictionary<String, String> d = new NSMutableDictionary<String, String>();
		d.setObjectForKey( SWNewsList.class.getSimpleName(), "A list of news" );
		return d;
	}

	public String selectedComponentKey() {
		Enumeration<String> e = componentsAndKeys().objectEnumerator();

		while( e.hasMoreElements() ) {
			String s = e.nextElement();
			if( s.equals( currentComponent().templateName() ) ) {
				return componentsAndKeys().allKeysForObject( s ).lastObject();
			}
		}

		return null;
	}

	public void setSelectedComponentKey( String newKey ) {
		currentComponent().setTemplateName( (String)componentsAndKeys().valueForKey( newKey ) );
	}
}