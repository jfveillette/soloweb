package concept.components.admin;

import is.rebbi.wo.definitions.EntityViewDefinition;
import is.rebbi.wo.util.USGenericComparator;
import is.rebbi.wo.util.USHTTPUtilities;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOContext;
import com.webobjects.eocontrol.EOKeyValueQualifier;
import com.webobjects.eocontrol.EOQualifier;
import com.webobjects.eocontrol.EOSortOrdering;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSComparator.ComparisonException;
import com.webobjects.foundation.NSMutableSet;

import concept.CPAdminComponent;
import concept.components.IMAdminDB;

/**
 * The Menu at the top of the system
 */

public class CPMainMenu extends CPAdminComponent {

	public String currentCategoryName;
	public EntityViewDefinition currentViewDefinition;
	private NSArray<EntityViewDefinition> _allViewDefinitions;

	public CPMainMenu( WOContext context ) {
		super( context );
	}

	/**
	 * Logs out the current user.
	 */
	public WOActionResults logout() {
		session().terminate();
		return USHTTPUtilities.responseWithDataAndMimeType( "Logged out.html", "Logged out", "text/html" );
	}

	public NSArray<String> categoryNames() throws ComparisonException {
		NSMutableSet<String> results = new NSMutableSet<>();

		for( EntityViewDefinition d : allViewDefinitions() ) {
			String categoryName = d.categoryName();

			if( categoryName == null ) {
				categoryName = "Óflokkað";
			}

			results.addObject( categoryName );
		}

		return results.allObjects().sortedArrayUsingComparator( USGenericComparator.IcelandicAscendingComparator );
	}

	public NSArray<EntityViewDefinition> viewDefinitions() {
		EOQualifier q = null;

		if( currentCategoryName.equals( "Óflokkað" ) ) {
			q = new EOKeyValueQualifier( "categoryName", EOQualifier.QualifierOperatorEqual, null );
		}
		else {
			q = new EOKeyValueQualifier( "categoryName", EOQualifier.QualifierOperatorEqual, currentCategoryName );
		}

		NSArray<EntityViewDefinition> a = EOQualifier.filteredArrayWithQualifier( allViewDefinitions(), q );
		EOSortOrdering s = new EOSortOrdering( "icelandicName", EOSortOrdering.CompareCaseInsensitiveAscending );
		a = EOSortOrdering.sortedArrayUsingKeyOrderArray( a, new NSArray<>( s ) );
		return a;
	}

	private NSArray<EntityViewDefinition> allViewDefinitions() {
		if( _allViewDefinitions == null ) {
			_allViewDefinitions = EntityViewDefinition.all();
		}

		return _allViewDefinitions;
	}

	public WOActionResults selectViewDefinition() {
		IMAdminDB nextPage = pageWithName( IMAdminDB.class );
		nextPage.setSelectedObject( currentViewDefinition );
		return nextPage;
	}
}