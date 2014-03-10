package concept.components;

import is.rebbi.core.util.ListUtilities;
import is.rebbi.core.util.StringUtilities;
import is.rebbi.wo.definitions.EntityViewDefinition;
import is.rebbi.wo.util.USArrayUtilities;

import com.webobjects.appserver.WOContext;
import com.webobjects.foundation.NSArray;

import concept.ViewPage;
import concept.data.SWDocument;
import concept.util.Documents;
import concept.util.HumanReadable;
import er.extensions.eof.ERXEnterpriseObject;
import er.extensions.eof.ERXGenericRecord;

public class SWDocumentDetailPage extends ViewPage<SWDocument> {

	private NSArray<SWDocument> _allRelatedDocuments;
	private NSArray<ERXGenericRecord> _objects;
	public ERXEnterpriseObject currentObject;

	public SWDocumentDetailPage( WOContext context ) {
		super( context );
	}

	public EntityViewDefinition targetType() {
		return EntityViewDefinition.get( currentObject.entityName() );
	}

	public NSArray<SWDocument> allRelatedDocuments() {

		if( USArrayUtilities.hasObjects( objects() ) ) {
			if( _allRelatedDocuments == null ) {
				_allRelatedDocuments = Documents.relatedDocuments( objects().get( 0 ) );
			}
		}
		else {
			_allRelatedDocuments = NSArray.emptyArray();
		}

		return _allRelatedDocuments;
	}

	public NSArray<ERXGenericRecord> objects() {
		if( _objects == null ) {
			_objects = Documents.relatedObjects( selectedObject() );
		}

		return _objects;
	}

	public String targetObjectName() {
		return HumanReadable.DefaultImplementation.toStringHuman( currentObject );
	}

	public boolean moreThanOneRelatedObject() {
		return objects().count() > 1;
	}

	public String formattedSize() {
		return StringUtilities.formatBytes( selectedObject().size(), false );
	}

	public boolean showBatching() {
		return allRelatedDocuments().count() > 1;
	}

	public int indexOfCurrentDocument() {
		return allRelatedDocuments().indexOfObject( selectedObject() ) + 1;
	}

	public SWDocument previousDocument() {

		if( !USArrayUtilities.hasObjects( objects() ) ) {
			return null;
		}

		if( moreThanOneRelatedObject() ) {
			return null;
		}

		return ListUtilities.previousObject( allRelatedDocuments(), selectedObject() );
	}

	public SWDocument nextDocument() {
		if( !USArrayUtilities.hasObjects( objects() ) ) {
			return null;
		}

		if( moreThanOneRelatedObject() ) {
			return null;
		}

		return ListUtilities.nextObject( allRelatedDocuments(), selectedObject() );
	}
}