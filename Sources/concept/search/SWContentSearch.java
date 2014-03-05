package concept.search;

import is.rebbi.core.util.StringUtilities;
import is.rebbi.wo.util.USEOUtilities;

import java.util.Enumeration;

import com.webobjects.eocontrol.EOAndQualifier;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOFetchSpecification;
import com.webobjects.eocontrol.EOKeyValueQualifier;
import com.webobjects.eocontrol.EOOrQualifier;
import com.webobjects.eocontrol.EOQualifier;
import com.webobjects.eocontrol.EOSortOrdering;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSMutableSet;

import concept.data.SWComponent;
import concept.data.SWPage;

/**
 * SWContentSearch allows you to search the contents in a SoloWeb using a single object.
 * In it's current incarnation, the search does a brute force search of the database, returning
 * all pages, even ones not published. If different functionality is required, the user
 * will have to construct a new EOQualifier and filter out undesirable objects.
 */

public class SWContentSearch {

	private Integer branchID;
	private String searchString;
	private EOEditingContext editingContext;
	private NSArray<EOSortOrdering> sortOrderings = new NSArray<>( new EOSortOrdering( "name", EOSortOrdering.CompareAscending ) );

	/**
	 * The default constructor if you want to search the entire contents of the SoloWeb site, all sites, all content.
	 *
	 * @param editingContext The editing context to fetch the objects into
	 * @param string The string to search for
	 */

	public SWContentSearch( EOEditingContext newEditingContext, String newSearchString ) {
		setEditingContext( newEditingContext );
		setSearchString( newSearchString );
	}

	/**
	 * The default constructor if you want to search a branch in the SoloWeb site.
	 *
	 * @param editingContext The editing context to fetch the objects into
	 * @param string The string to search for
	 * @param branchID The ID of the branch (SWPage object) you want to search below in the  hierarchy. The object specified by branchID is included in the search hierarchy.
	 */

	public SWContentSearch( EOEditingContext newEditingContext, String newSearchString, Integer newBranchID ) {
		setEditingContext( newEditingContext );
		setSearchString( newSearchString );
		setBranchID( newBranchID );
	}

	/**
	 * Sets the EditingContext to fetch the objects into
	 *
	 * @param newEditingContext The editingConrtext to fetch into
	 */
	public void setEditingContext( EOEditingContext newEditingContext ) {
		editingContext = newEditingContext;
	}

	public EOEditingContext editingContext() {
		return editingContext;
	}

	/**
	 * Sets the string to search for
	 *
	 * @param newSearchString The string to search for
	 */
	public void setSearchString( String newSearchString ) {
		searchString = newSearchString;
	}

	public String searchString() {
		return searchString;
	}

	/**
	 * Sets the branch to search
	 *
	 * @param newSearchString The ID of the branch to search
	 */
	public void setBranchID( Integer newBranchID ) {
		branchID = newBranchID;
	}

	public Integer branchID() {
		return branchID;
	}

	/**
	 * Creates the qualifier that's used to search the content.
	 *
	 * @param aString The string to search for
	 * @param attributes The attributes to search
	 */
	private static EOQualifier qualifierFromString( String aString, NSArray<String> attributes ) {
		NSMutableArray<EOQualifier> tempArray = new NSMutableArray<>();
		Enumeration<String> e = attributes.objectEnumerator();

		while( e.hasMoreElements() ) {
			String anAttribute = e.nextElement();
			tempArray.addObject( new EOKeyValueQualifier( anAttribute, EOQualifier.QualifierOperatorCaseInsensitiveLike, "*" + aString + "*" ) );
		}

		return new EOOrQualifier( tempArray );
	}

	/**
	 * @return the components found by searching
	 */
	private NSArray<SWComponent> components() {
		NSArray<String> searchKeyPaths = new NSArray<>( new String[] { "page.name", "page.text", "page.keywords" } );
		EOQualifier textQualifier = qualifierFromString( searchString, searchKeyPaths );
		EOQualifier publishedQualifier = new EOKeyValueQualifier( "published", EOQualifier.QualifierOperatorEqual, 1 );
		EOAndQualifier andQual = new EOAndQualifier( new NSArray<>( new EOQualifier[] { textQualifier, publishedQualifier } ) );
		EOFetchSpecification fs = new EOFetchSpecification( SWComponent.ENTITY_NAME, andQual, null );
		return editingContext.objectsWithFetchSpecification( fs );
	}

	/**
	 * @return pages found by a brute force search of the entire SoloWeb system.
	 *
	 * @deprecated Use the method search() instead
	 */
	@Deprecated
	public NSArray<SWPage> results() {

		NSMutableSet<SWPage> theSet = new NSMutableSet<>();
		Enumeration<SWComponent> e = components().objectEnumerator();

		while( e.hasMoreElements() ) {
			SWComponent aComponent = e.nextElement();
			theSet.addObject( aComponent.page() );
		}

		Enumeration<SWPage> f = theSet.allObjects().objectEnumerator();
		NSMutableArray<SWPage> resultArray = new NSMutableArray<>();

		while( f.hasMoreElements() ) {
			SWPage aPage = f.nextElement();

			if( aPage.isAccessible() ) {
				resultArray.addObject( aPage );
			}
		}

		EOSortOrdering.sortArrayUsingKeyOrderArray( resultArray, sortOrderings() );

		return resultArray.immutableClone();
	}

	/**
	 * Searches only the branch specified by branchID.
	 *
	 * @param branchID The ID of the branch (SWPage object) you want to search below in the  hierarchy. The object specified by branchID is included in the search hierarchy.
	 * @deprecated Use search() instead
	 */
	@Deprecated
	public NSArray<SWPage> searchBranch( Integer branchNumber ) {
		SWPage aPage = (SWPage)USEOUtilities.objectWithPK( editingContext, SWPage.ENTITY_NAME, branchNumber );
		NSMutableArray<SWPage> anArray = new NSMutableArray<>();

		Enumeration<SWPage> e = results().objectEnumerator();

		while( e.hasMoreElements() ) {
			SWPage iPage = e.nextElement();

			if( aPage.isParentPageOfPage( iPage, true ) ) {
				anArray.addObject( iPage );
			}
		}

		return anArray;
	}

	public NSArray<EOSortOrdering> sortOrderings() {
		return sortOrderings;
	}

	/**
	 * Sets an array of EOSortOrderings used to sort the pages fetched.
	 * The default is to sort ascendingly by name.
	 *
	 * @param newSortOrderings The array of sortorderings
	 */
	public void setSortOrderings( NSArray<EOSortOrdering> newSortOrderings ) {
		sortOrderings = newSortOrderings;
	}

	/**
	 * Performs a search of pages, searching only the specified branch if any
	 */
	public NSArray<SWPage> search() {

		if( !StringUtilities.hasValue( searchString ) ) {
			return null;
		}

		if( branchID() != null ) {
			return searchBranch( branchID() );
		}

		return results();
	}
}