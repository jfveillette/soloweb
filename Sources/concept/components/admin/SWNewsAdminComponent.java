package concept.components.admin;

import is.rebbi.wo.util.USEOUtilities;

import java.util.Enumeration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.webobjects.appserver.WOContext;
import com.webobjects.appserver.WOResponse;
import com.webobjects.eocontrol.EOFetchSpecification;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSMutableDictionary;

import concept.SWAdminComponent;
import concept.data.SWComponent;
import concept.data.SWNewsCategory;
import er.extensions.eof.qualifiers.ERXInQualifier;

public class SWNewsAdminComponent extends SWAdminComponent {

	private static final Logger logger = LoggerFactory.getLogger( SWNewsAdminComponent.class );

	/**
	 * currentComponent is the component currently being worked on.
	 */
	public SWComponent currentComponent;
	public SWNewsCategory categoryItem;

	/**
	 * A variable the iteration in the categories pop-up-menu
	 */
	public SWNewsCategory currentCategory;

	/**
	 * Default contructor
	 */
	public SWNewsAdminComponent( WOContext context ) {
		super( context );
	}

	/**
	 * We set the Template Name for this component in appendToResponse,
	 * so the correct template is automatically selected when the SoloNews tab is selected.
	 */
	@Override
	public void appendToResponse( WOResponse r, WOContext c ) {
		super.appendToResponse( r, c );
	}

	/**
	 * Returns all SWNewsCategory Objects
	 */
	public NSArray<SWNewsCategory> allCategories() {
		EOFetchSpecification fs = new EOFetchSpecification( SWNewsCategory.ENTITY_NAME, null, null );
		return session().defaultEditingContext().objectsWithFetchSpecification( fs );
	}

	/**
	 * This method retrieves the "category" selection from the customInfo Dictionary in
	 * the component, and selects the category object with
	 * the corresponding primary key
	 */
	public SWNewsCategory selectedCategory() {
		try {
			Integer aCategoryID = new Integer( (String)currentComponent.customInfo().valueForKey( "category" ) );
			return (SWNewsCategory)USEOUtilities.objectWithPK( session().defaultEditingContext(), SWNewsCategory.ENTITY_NAME, aCategoryID );
		}
		catch( Exception e ) {
			logger.debug( e.toString() );
			return null;
		}
	}

	public void setSelectedCategory( SWNewsCategory newSelectedCategory ) {
		if( newSelectedCategory == null ) {
			currentComponent.customInfo().takeValueForKey( null, "category" );
		}
		else {
			currentComponent.customInfo().takeValueForKey( newSelectedCategory.primaryKey(), "category" );
		}
	}

	public NSArray<String> imageAlignment() {
		return new NSArray<>( new String[] { "left", "right", "above" } );
	}

	public NSArray<String> sortKeys() {
		return new NSArray<>( new String[] { "By date", "Alphabetically", "Random" } );
	}

	public NSArray<String> sortOrderings() {
		return new NSArray<>( new String[] { "Descending", "Ascending" } );
	}

	public NSArray<String> componentKeys() {
		return componentsAndKeys().allKeys();
	}

	public NSMutableDictionary<String, String> componentsAndKeys() {
		NSMutableDictionary<String, String> d = new NSMutableDictionary<>();

		d.setObjectForKey( "SWNewsSubmit", "Skr&aacute;ning fr&eacute;ttar" );
		d.setObjectForKey( "SoloNewsNewsList", "Fr&eacute;ttalisti" );

		return d;
	}

	public String selectedComponentKey() {
		Enumeration<String> e = componentsAndKeys().objectEnumerator();

		while( e.hasMoreElements() ) {
			String s1 = e.nextElement();
			String s2 = currentComponent.templateName();

			if( s1.equals( s2 ) ) {
				String s3 = componentsAndKeys().allKeysForObject( s1 ).lastObject();
				return s3;
			}
		}

		return null;
	}

	public void setSelectedComponentKey( String newKey ) {
		currentComponent.setTemplateName( (String)componentsAndKeys().valueForKey( newKey ) );
	}

	public NSArray categoryItems() {
		String cats = currentComponent.customInfo().stringValueForKey( "categoryIDs" );
		NSArray ids = NSArray.componentsSeparatedByString( cats, " " );

		if( ids == null || ids.count() == 0 ) {
			return null;
		}

		// Convert ids to numbers
		NSMutableArray ids2 = new NSMutableArray();
		Enumeration iter = ids.objectEnumerator();

		while( iter.hasMoreElements() ) {
			try {
				ids2.addObject( Integer.valueOf( (String)iter.nextElement() ) );
			}
			catch( Exception ex ) {
				logger.error( "Failed to read news category ID" );
			}
		}

		if( ids2.count() == 0 ) {
			return null;
		}

		ERXInQualifier inq = new ERXInQualifier( SWNewsCategory.ID_KEY, ids2 );
		EOFetchSpecification fsp = new EOFetchSpecification( SWNewsCategory.ENTITY_NAME, inq, null );
		return session().defaultEditingContext().objectsWithFetchSpecification( fsp );
	}

	public NSArray<String> dateDisplayOptions() {
		return new NSArray<>( new String[] { "above", "front", "behind", "hide" } );
	}

	public NSArray<String> dateFormatOptions() {
		return new NSArray<>( new String[] { "1.12.2000", "12:34", "1.12.2000 12:34", "Föstudagur 1. desember 12:34" } );
	}

	public String catSelWinUrl() {
		String url = context().directActionURLForActionNamed( "openNewsCategoriesSelect", null );
		url += "?extraCategories=";
		return url;
	}

	public NSArray<String> oldestNewestList() {
		return new NSArray<>( new String[] { "yngstu", "elstu" } );
	}

	public NSArray<String> searchLocationList() {
		return new NSArray<>( new String[] { "ofan", "neðan" } );
	}

	public NSArray<String> searchTypeList() {
		return new NSArray<>( new String[] { "fellilisti", "listi" } );
	}
}