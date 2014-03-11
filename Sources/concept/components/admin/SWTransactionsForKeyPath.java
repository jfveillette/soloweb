package concept.components.admin;

import is.rebbi.wo.interfaces.HasFakeRelationship;
import is.rebbi.wo.util.Inspection;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOContext;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableArray;

import concept.CPAdminComponent;
import concept.data.SWComponent;
import concept.data.SWTransaction;
import er.extensions.appserver.ERXApplication;
import er.extensions.eof.ERXEC;
import er.extensions.eof.ERXGenericRecord;

public class SWTransactionsForKeyPath extends CPAdminComponent {

	private ERXGenericRecord object;
	private String keyPath;

	public boolean escapeHTML;

	public SWTransaction currentTransaction;
	public SWTransaction selectedTransaction;

	public SWTransactionsForKeyPath( WOContext context ) {
		super( context );
	}

	public static WOActionResults look( WOContext context, ERXGenericRecord object, String keyPath ) {
		SWTransactionsForKeyPath nextPage = ERXApplication.erxApplication().pageWithName( SWTransactionsForKeyPath.class );
		nextPage.object = object;
		nextPage.keyPath = keyPath;
		return nextPage;
	}

	public NSArray<SWTransaction> transactions() {
		NSArray<SWTransaction> all = HasFakeRelationship.Util.relatedObjects( SWTransaction.class, object, SWTransaction.DATE.descs() );
		NSMutableArray<SWTransaction> results = new NSMutableArray<SWTransaction>();

		for( SWTransaction t : all ) {
			if( t.afterDictionary().containsKey( keyPath ) ) {
				results.addObject( t );
			}
		}

		return results;
	}

	public WOActionResults selectTransaction() {
		selectedTransaction = currentTransaction;
		return null;
	}

	public Object selectedValue() {
		if( selectedTransaction != null ) {
			return selectedTransaction.afterDictionary().objectForKey( keyPath );
		}

		return null;
	}

	/**
	 * @return Use content from the selected transaction.
	 */
	public WOActionResults use() {
		Object value = selectedTransaction.afterDictionary().objectForKey( keyPath );
		object.takeValueForKeyPath( value, keyPath );
		object.editingContext().saveChanges();
		return null;
	}

	public WOActionResults preview() {
		EOEditingContext peerEC = ERXEC.newEditingContext();
		ERXGenericRecord localObject = (ERXGenericRecord)object.localInstanceIn( peerEC );
		Object value = selectedTransaction.afterDictionary().objectForKey( keyPath );
		localObject.takeValueForKeyPath( value, keyPath );

		if( object instanceof SWComponent ) {
			return Inspection.inspectObjectInContext( ((SWComponent)localObject).page(), context() );
		}
		else {
			return Inspection.inspectObjectInContext( localObject, context() );
		}
	}
}