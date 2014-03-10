package concept.components.admin.edit;


import com.webobjects.appserver.WOContext;

import concept.ViewPage;
import concept.data.SWDocument;

/**
 * For editing of documents.
 */

public class SWEditDocument extends ViewPage<SWDocument> {

	/**
	 * The name of the file currently being uploaded from disk.
	 */
	public String filename;

	public SWEditDocument( WOContext context ) {
		super( context );
	}
}