package concept.components.admin.edit;

import is.rebbi.wo.util.USUtilities;

import java.util.Enumeration;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOContext;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableDictionary;

import concept.CPGenericComponent;
import concept.components.client.SWDocumentDetail;
import concept.components.client.SWDocumentList;
import concept.data.SWDocument;
import concept.util.CPLoc;

/**
 * Component admin for document components.
 */

public class SWCADocuments extends CPGenericComponent {

	private static final String DOCUMENT_ID_STRING = "documentID";

	public SWCADocuments( WOContext c ) {
		super( c );
	}

	public NSArray<String> componentKeys() {
		return componentsAndKeys().allKeys();
	}

	public NSMutableDictionary<String, String> componentsAndKeys() {
		NSMutableDictionary<String, String> d = new NSMutableDictionary<String, String>();
		d.setObjectForKey( SWDocumentDetail.class.getSimpleName(), CPLoc.string( "docComponentSingleDocument", context() ) );
		d.setObjectForKey( SWDocumentList.class.getSimpleName(), CPLoc.string( "docComponentListOfDocuments", context() ) );
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

	public SWDocument selectedDocument() {
		return SWDocument.documentWithID( session().defaultEditingContext(), documentIDFromDictionary() );
	}

	public void setSelectedDocument( SWDocument document ) {

		if( document == null ) {
			currentComponent().customInfo().takeValueForKey( null, DOCUMENT_ID_STRING );
		}
		else {
			currentComponent().customInfo().takeValueForKey( document.primaryKey(), DOCUMENT_ID_STRING );
		}
	}

	public Integer documentIDFromDictionary() {
		Object o = currentComponent().customInfo().valueForKey( DOCUMENT_ID_STRING );
		return USUtilities.integerFromObject( o );
	}

	public WOActionResults self() {
		return this;
	}
}