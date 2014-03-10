package concept.components;

import is.rebbi.core.util.StringUtilities;
import is.rebbi.wo.definitions.EntityViewDefinition;
import is.rebbi.wo.util.USHTTPUtilities;

import com.webobjects.appserver.WOContext;
import com.webobjects.foundation.NSArray;

import concept.SWBaseComponent;
import concept.search.Indexable;
import concept.search.Indexer;
import concept.util.HumanReadable;
import er.extensions.appserver.ERXApplication;
import er.extensions.eof.ERXGenericRecord;

/**
 * Wrapper component for the look.
 */

public abstract class SWLook extends SWBaseComponent {

	public String searchString;

	public SWLook( WOContext context ) {
		super( context );
	}

	@Override
	public boolean synchronizesVariablesWithBindings() {
		return false;
	}

	public String searchActionName() {
		return ERXApplication.isDevelopmentModeSafe() ? "search" : "searchRedirection";
	}

	public NSArray<String> autoCompletes() {
		if( searchString != null && searchString.length() > 0 ) {
			return Indexer.autocomplete( searchString );
		}

		return NSArray.emptyArray();
	}

	public String pageTitle() {
		String result = "";

		ERXGenericRecord o = selectedObject();

		if( o != null ) {
			result = HumanReadable.DefaultImplementation.toStringHuman( o );
			result = result + " | ";
			result = result + EntityViewDefinition.icelandicName( o.entityName() );
			result = result + " | ";
		}

		result = result + USHTTPUtilities.host( context().request() );

		return result;
	}

	public String pageDescription() {
		Object o = selectedObject();

		String result = null;

		if( o instanceof Indexable ) {
			result = ((Indexable)o).indexRecord().text();
		}

		result = StringUtilities.replace( result, "\n", " " );

		return result;
	}

	public String pageKeywords() {
		return null;
	}
}