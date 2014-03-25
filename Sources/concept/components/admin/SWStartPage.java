package concept.components.admin;

import is.rebbi.core.util.StringUtilities;
import is.rebbi.wo.util.HumanReadable;
import is.rebbi.wo.util.SWSettings;

import com.webobjects.appserver.WOComponent;
import com.webobjects.appserver.WOContext;
import com.webobjects.appserver.WOResponse;
import com.webobjects.eocontrol.EOFetchSpecification;
import com.webobjects.foundation.NSArray;

import concept.Concept;
import concept.SWAdminComponent;
import concept.SWPageUtilities;
import concept.data.SWTransaction;
import er.ajax.AjaxUtils;

public class SWStartPage extends SWAdminComponent {

	public SWTransaction currentTransaction;

	public SWStartPage( WOContext context ) {
		super( context );
	}

	public NSArray<SWTransaction> transactions() {
		EOFetchSpecification fs = new EOFetchSpecification( SWTransaction.ENTITY_NAME, SWTransaction.TARGET_ENTITY_NAME.isNotNull(), SWTransaction.DATE.descs() );
		fs.setFetchLimit( 50 );
		return ec().objectsWithFetchSpecification( fs );
	}

	@Override
	public void appendToResponse( WOResponse r, WOContext c ) {
		String s = SWSettings.stringForKey( "systemFrontPageLinkingName" );

		if( StringUtilities.hasValue( s ) ) {
			WOComponent component = SWPageUtilities.pageMatchingName( session().defaultEditingContext(), context(), s );

			if( component != null ) {
				r.setContent( component.generateResponse().content() );
			}
		}
		else {
			super.appendToResponse( r, c );
			AjaxUtils.addStylesheetResourceInHead( context(), r, Concept.sw().frameworkBundleName(), "bootstrap-3/css/bootstrap.min.css" );
			AjaxUtils.addStylesheetResourceInHead( context(), r, Concept.sw().frameworkBundleName(), "bootstrap-3/css/bootstrap-theme.min.css" );
			AjaxUtils.addScriptResourceInHead( context(), r, Concept.sw().frameworkBundleName(), "bootstrap-3/js/bootstrap.min.js" );
		}
	}

	public String currentDisplayString() {
		return HumanReadable.DefaultImplementation.toStringHuman( currentTransaction.targetObject() );
	}
}