package concept.components.admin;

import is.rebbi.wo.interfaces.HasFakeRelationship;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOContext;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.foundation.NSArray;

import concept.Inspection;
import concept.CPAdminComponent;
import concept.data.SWComponent;
import concept.data.SWTransaction;
import er.extensions.appserver.ERXApplication;
import er.extensions.eof.ERXEC;
import er.extensions.eof.ERXGenericRecord;

public class SWTransactionsForObject extends CPAdminComponent {

	private ERXGenericRecord object;

	public SWTransaction currentTransaction;

	public SWTransactionsForObject( WOContext context ) {
		super( context );
	}

	public static WOActionResults open( WOContext context, ERXGenericRecord object ) {
		SWTransactionsForObject nextPage = ERXApplication.erxApplication().pageWithName( SWTransactionsForObject.class );
		nextPage.object = object;
		return nextPage;
	}

	public NSArray<SWTransaction> transactions() {
		return HasFakeRelationship.Util.relatedObjects( SWTransaction.class, object, SWTransaction.DATE.descs() );
	}

	/**
	 * @return Use content from the selected transaction.
	 */
	public WOActionResults use() {

		for( String keyPath : currentTransaction.afterDictionary().allKeys() ) {
			Object value = currentTransaction.afterDictionary().objectForKey( keyPath );
			object.takeValueForKeyPath( value, keyPath );
		}

		object.editingContext().saveChanges();

		return null;
	}

	public WOActionResults preview() {
		EOEditingContext peerEC = ERXEC.newEditingContext();
		ERXGenericRecord localObject = (ERXGenericRecord)object.localInstanceIn( peerEC );

		for( String keyPath : currentTransaction.afterDictionary().allKeys() ) {
			try {
				Object value = currentTransaction.afterDictionary().objectForKey( keyPath );
				localObject.takeValueForKeyPath( value, keyPath );
			}
			catch( Exception e ) {
				e.printStackTrace();
			}
		}

		if( object instanceof SWComponent ) {
			return Inspection.inspectObjectInContext( ((SWComponent)localObject).page(), context() );
		}
		else {
			return Inspection.inspectObjectInContext( localObject, context() );
		}
	}
}