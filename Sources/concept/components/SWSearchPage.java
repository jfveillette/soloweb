package concept.components;

import is.rebbi.wo.definitions.EntityViewDefinition;
import is.rebbi.wo.util.USUtilities;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOContext;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSMutableSet;

import concept.SWBaseComponent;
import concept.SWSearchAction;
import concept.search.IndexRecord;
import concept.search.Indexer;
import er.extensions.appserver.ERXApplication;
import er.extensions.appserver.ERXDisplayGroup;

public class SWSearchPage extends SWBaseComponent {

	public ERXDisplayGroup<IndexRecord> dg;

	public NSMutableArray<EntityViewDefinition> definitionsToExclude = new NSMutableArray<>();
	public EntityViewDefinition currentViewDefinition;

	/**
	 * The search string entered by the user.
	 */
	private String _searchString;

	/**
	 * The actual query string submitted to Lucene.
	 */
	private String _queryString;

	/**
	 * Indicates if we want to use the inflexer.
	 */
	private Boolean _useInflection;

	public IndexRecord currentRecord;

	public SWSearchPage( WOContext context ) {
		super( context );
	}

	public NSArray<IndexRecord> results() {
		return dg.displayedObjects();
	}

	public NSArray<IndexRecord> resultsFiltered() {
		NSMutableArray<IndexRecord> a = new NSMutableArray<>();

		for( IndexRecord r : results() ) {
			if( !definitionsToExclude.containsObject( r.viewDefinition() ) ) {
				a.addObject( r );
			}
		}

		return a;
	}

	public void setResults( NSArray<IndexRecord> value ) {
		if( dg == null ) {
			dg = new ERXDisplayGroup<>();
			dg.setObjectArray( value );
		}
	}

	public boolean hasSearched() {
		return searchString() != null;
	}

	public boolean multipleResults() {
		return results().count() > 1;
	}

	public String searchActionName() {
		StringBuilder b = new StringBuilder();
		b.append( SWSearchAction.class.getSimpleName() );
		b.append( "/" );

		if( ERXApplication.isDevelopmentModeSafe() ) {
			b.append( "search" );
		}
		else {
			b.append( "searchRedirection" );
		}

		return b.toString();
	}

	public NSArray<String> autoCompletes() {
		if( searchString() != null && searchString().length() > 1 ) {
			return Indexer.autocomplete( searchString() );
		}

		return NSArray.emptyArray();
	}

	public String searchString() {
		return _searchString;
	}

	public void setSearchString( String value ) {
		_searchString = value;
	}

	public String queryString() {
		return _queryString;
	}

	public void setQueryString( String value ) {
		_queryString = value;
	}

	public Boolean useInflection() {
		String s = context().request().stringFormValueForKey( "allar-ordmyndir" );
		_useInflection = USUtilities.booleanFromObject( s );
		return _useInflection;
	}

	public void setUseInflection( Boolean value ) {
		_useInflection = value;
	}

	public NSArray<EntityViewDefinition> viewDefinitions() {
		NSMutableSet<EntityViewDefinition> a = new NSMutableSet<>();

		for( IndexRecord r : results() ) {
			EntityViewDefinition vd = r.viewDefinition();

			if( vd != null ) {
				a.addObject( vd );
			}
		}

		return a.allObjects();
	}

	public WOActionResults toggle() {
		if( definitionsToExclude.containsObject( currentViewDefinition ) ) {
			definitionsToExclude.removeObject( currentViewDefinition );
		}
		else {
			definitionsToExclude.addObject( currentViewDefinition );
		}

		return null;
	}

	public String viewDefinitionClass() {
		StringBuilder b = new StringBuilder();

		b.append( "label" );

		if( !definitionsToExclude.containsObject( currentViewDefinition) ) {
			b.append( " label-success" );
		}

		return b.toString();
	}
}