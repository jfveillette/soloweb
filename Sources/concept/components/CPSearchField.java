package concept.components;

import com.webobjects.appserver.WOContext;
import com.webobjects.foundation.NSArray;

import concept.SWSearchAction;
import concept.search.Indexer;
import er.extensions.appserver.ERXApplication;
import er.extensions.components.ERXComponent;

public class CPSearchField extends ERXComponent {

	/**
	 * The search string entered by the user.
	 */
	public String searchString;

	public CPSearchField( WOContext context ) {
		super( context );
	}

	public String searchString() {
		String s = context().request().stringFormValueForKey( "searchString_field" );

		if( searchString == null && s != null ) {
			searchString = s;
		}

		return searchString;
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
		if( searchString != null && searchString.length() > 0 ) {
			return Indexer.autocomplete( searchString );
		}

		return NSArray.emptyArray();
	}
}