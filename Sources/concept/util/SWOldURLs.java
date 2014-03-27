package concept.util;

import is.rebbi.wo.util.USUtilities;

import com.webobjects.appserver.WOContext;
import com.webobjects.eocontrol.EOEditingContext;

import concept.data.SWDocument;

public class SWOldURLs {

	public static String urlForDocumentWithIDInContext( EOEditingContext ec, Object documentID, WOContext context ) {
		SWDocument document = SWDocument.documentWithID( ec, USUtilities.integerFromObject( documentID ) );
		return SWOldURLs.urlForDocumentInContext( document, context );
	}

	public static String urlForObject( Object o, WOContext context ) {

		if( o instanceof SWDocument ) {
			return urlForDocumentInContext( (SWDocument)o, context );
		}

		throw new RuntimeException( "I don't know how to generate URLs for objects of class: " + o.getClass() );
	}

	public static String urlForDocumentInContext( SWDocument document, WOContext context ) {
		String url = null;

		if( document != null ) {
			url = "/doc/" + document.primaryKey();
		}

		return url;
	}
}
