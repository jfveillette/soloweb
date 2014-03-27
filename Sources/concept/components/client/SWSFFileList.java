package concept.components.client;

import is.rebbi.core.util.StringUtilities;
import is.rebbi.wo.util.SWSettings;
import is.rebbi.wo.util.USArrayUtilities;

import com.webobjects.appserver.WOContext;
import com.webobjects.foundation.NSArray;

import concept.SWGenericComponent;
import concept.data.SWComponent;
import concept.data.SWDocument;
import concept.util.SWOldURLs;

/**
 * The display component for a list of SWDocuments.
 */

public class SWSFFileList extends SWGenericComponent {

	public SWDocument currentDocument;

	public SWSFFileList( WOContext context ) {
		super( context );
	}

	@Override
	public SWComponent currentComponent() {
		if( super.currentComponent() == null && parent() instanceof SWGenericComponent ) {
			setCurrentComponent( ((SWGenericComponent)parent()).currentComponent() );
		}

		return super.currentComponent();
	}

	public SWDocument selectedDocument() {
		Integer anID = currentComponent().customInfo().integerValueForKey( "documentID" );
		return SWDocument.documentWithID( session().defaultEditingContext(), anID );
	}

	public NSArray<SWDocument> documents() {

		if( selectedDocument() == null ) {
			return null;
		}

		if( currentComponent().customInfo().booleanValueForKey( "reverseSortOrder" ) ) {
			return USArrayUtilities.reversed( selectedDocument().folder().sortedDocuments() );
		}
		else {
			return selectedDocument().folder().sortedDocuments();
		}
	}

	public String downloadText() {

		String s = currentComponent().customInfo().stringValueForKey( "downloadText" );

		if( StringUtilities.hasValue( s ) ) {
			return s;
		}

		return SWSettings.stringForKey( "defaultDownloadFileString", "Download file" );
	}

	public String documentURL() {
		return SWOldURLs.urlForDocumentInContext( currentDocument, context() );
	}
}