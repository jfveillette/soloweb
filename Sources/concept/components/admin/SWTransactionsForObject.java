package concept.components.admin;

import is.rebbi.wo.interfaces.HasFakeRelationship;
import is.rebbi.wo.util.Inspection;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOContext;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.foundation.NSArray;

import concept.ViewPage;
import concept.data.SWComponent;
import concept.data.SWTransaction;
import er.extensions.eof.ERXEC;
import er.extensions.eof.ERXGenericRecord;

public class SWTransactionsForObject extends ViewPage {

	public SWTransaction currentTransaction;

	public SWTransactionsForObject( WOContext context ) {
		super( context );
	}

	public NSArray<SWTransaction> transactions() {
		return HasFakeRelationship.Util.relatedObjects( SWTransaction.class, selectedObject(), SWTransaction.DATE.descs() );
	}

	/**
	 * @return Use content from the selected transaction.
	 */
	public WOActionResults use() {

		for( String keyPath : currentTransaction.afterDictionary().allKeys() ) {
			Object value = currentTransaction.afterDictionary().objectForKey( keyPath );
			selectedObject().takeValueForKeyPath( value, keyPath );
		}

		selectedObject().editingContext().saveChanges();

		return null;
	}

	public WOActionResults preview() {
		EOEditingContext peerEC = ERXEC.newEditingContext();
		ERXGenericRecord localObject = (ERXGenericRecord)selectedObject().localInstanceIn( peerEC );

		for( String keyPath : currentTransaction.afterDictionary().allKeys() ) {
			try {
				Object value = currentTransaction.afterDictionary().objectForKey( keyPath );
				localObject.takeValueForKeyPath( value, keyPath );
			}
			catch( Exception e ) {
				e.printStackTrace();
			}
		}

		if( selectedObject() instanceof SWComponent ) {
			return Inspection.inspectObjectInContext( ((SWComponent)localObject).page(), context() );
		}
		else {
			return Inspection.inspectObjectInContext( localObject, context() );
		}
	}
}