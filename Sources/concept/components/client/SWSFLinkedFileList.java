package concept.components.client;

import is.rebbi.wo.util.USEOUtilities;

import java.util.HashMap;
import java.util.Vector;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOContext;
import com.webobjects.foundation.NSArray;

import concept.IDynamicComponent;
import concept.SWGenericComponent;
import concept.data.SWDocument;
import concept.data.SWDocumentFolder;
import concept.util.SWOldURLs;

public class SWSFLinkedFileList extends SWGenericComponent implements IDynamicComponent {

	public SWDocumentFolder currentFolder;
	public Vector<SWDocumentFolder> folders = new Vector<>();
	public int columnIndex, rowIndex, selectedColumn;

	public SWSFLinkedFileList( WOContext context ) {
		super( context );
	}

	@Override
	public void parameters( HashMap<String, String> value ) {
		if( value.containsKey( "sort" ) ) {
			selectedColumn = new Integer( value.get( "sort" ) ).intValue();
		}
		else {
			selectedColumn = 0;
		}
	}

	@Override
	public void arguments( Vector<String> args ) {
		for( int i = 0; i < args.size(); i++ ) {
			Integer id = new Integer( args.get( i ) );
			if( id != null ) {
				SWDocumentFolder folder = (SWDocumentFolder)USEOUtilities.objectWithPK( session().defaultEditingContext(), SWDocumentFolder.ENTITY_NAME, id );
				if( folder != null ) {
					folders.add( folder );
				}
			}
		}
	}

	public NSArray itemList() {
		// get folder for selected column
		SWDocumentFolder folder = folders.get( selectedColumn );
		return folder.sortedDocuments();
	}

	public SWDocument currentDocument() {
		// get folder for selected column
		SWDocumentFolder folder = folders.get( selectedColumn );
		// get document for current row
		SWDocument doc1 = (SWDocument)folder.sortedDocuments().objectAtIndex( rowIndex );
		// get linked document in current column
		folder = folders.get( columnIndex );
		SWDocument doc = folder.documentWithLinkKey( doc1.linkKey() );

		return doc;
	}

	public String documentURL() {
		return SWOldURLs.urlForDocumentInContext( currentDocument(), context() );
	}

	public String thClass() {
		String klass = null;
		if( columnIndex == selectedColumn ) {
			klass = "selected";
		}
		return klass;
	}

	public WOActionResults selectTheFolder() {
		selectedColumn = columnIndex;
		return this;
	}
}