package concept.components.admin.sanity;

import is.rebbi.wo.urls.SWURLProvider;
import is.rebbi.wo.util.USArrayUtilities;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WORequest;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.foundation.NSArray;

import concept.data.SWDocument;
import concept.util.Documents;
import er.extensions.appserver.ERXDirectAction;
import er.extensions.eof.ERXGenericRecord;

public class FindDocumentsWithoutData extends ERXDirectAction {

	public FindDocumentsWithoutData( WORequest r ) {
		super( r );
	}

	@Override
	public WOActionResults defaultAction() {
		EOEditingContext ec = session().defaultEditingContext();
		NSArray<SWDocument> a = SWDocument.fetchAllSWDocuments( ec );

		int i = a.count();

		for( SWDocument d : a ) {
			long size = d.size();

			if( size <= 0 ) {
				String documentURL = "http://www.ismus.is/i/document/id-" + d.primaryKey();
				NSArray<ERXGenericRecord> related = Documents.relatedObjects( d );
				String objectUrl = null;

				if( USArrayUtilities.hasObjects( related ) ) {
					objectUrl = SWURLProvider.urlForObjectInContext( related.objectAtIndex( 0 ), null );
				}

				System.out.println( documentURL + " ;;; " + objectUrl + " ;;; " + d.name() );
			}

			if( i-- % 100 == 0 ) {
				System.out.println( "====> Remaining: " + i );
			}
		}

		return null;
	}
}