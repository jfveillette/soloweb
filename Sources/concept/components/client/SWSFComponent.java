package concept.components.client;

import is.rebbi.core.util.StringUtilities;
import is.rebbi.wo.util.SWSettings;

import com.webobjects.appserver.WOContext;

import concept.SWGenericComponent;
import concept.data.SWDocument;
import concept.util.SWOldURLs;

/**
 * The display component for a single SWDocument.
 */

public class SWSFComponent extends SWGenericComponent {

	public SWSFComponent( WOContext c ) {
		super( c );
	}

	public SWDocument selectedDocument() {
		Integer anID = currentComponent().customInfo().integerValueForKey( "documentID" );
		return SWDocument.documentWithID( session().defaultEditingContext(), anID );
	}

	public String downloadText() {

		String s = currentComponent().customInfo().stringValueForKey( "downloadText" );

		if( StringUtilities.hasValue( s ) ) {
			return s;
		}

		return SWSettings.stringForKey( "defaultDownloadFileString", "Download file" );
	}

	public String documentURL() {
		return SWOldURLs.urlForDocumentInContext( selectedDocument(), context() );
	}
}