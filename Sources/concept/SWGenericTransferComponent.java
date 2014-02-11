package concept;

import is.rebbi.wo.interfaces.SWAsset;
import is.rebbi.wo.interfaces.SWCopyable;
import is.rebbi.wo.interfaces.SWTransferable;

import com.webobjects.appserver.WOComponent;
import com.webobjects.appserver.WOContext;
import com.webobjects.eocontrol.EOEnterpriseObject;

/**
 * This component defines the basis for transfer of objects that implement the SWTransferable interface.
 * To use this component, create your own subclass, displaying a list of the possible destination objects.
 * Make the parent objects iterate over the "currentParent" variable.
 * When a destination object is clicked, invoke the selectObject method. The object "record" will be transferred
 * from it's previous parent to "currentParent", changes are saved and the WOComponent instance
 * stored in "componentToReturn" is returned.
 */

public abstract class SWGenericTransferComponent extends SWAdminComponent {

	/**
	 * Indicates if we should copy or move.
	 */
	public boolean shouldCopy = false;

	/**
	 * The parent object currently being iterated over
	 */
	public EOEnterpriseObject currentParent;

	/**
	 * The record to transfer between parents. Must implement "SWTransferable"
	 */
	public SWTransferable record;

	/**
	 * The component to return to when the transfer operation has been completed or canceled
	 */
	public WOComponent componentToReturn;

	public SWGenericTransferComponent( WOContext context ) {
		super( context );
	}

	/**
	 * Takes "record", moves it's ownership to "currentParent", saves changes and returns "componentToReturn"
	 */
	public WOComponent selectObject() {

		if( shouldCopy ) {
			record = (SWTransferable)((SWCopyable)record).createCopy();
		}

		record.transferOwnership( currentParent );
		session().defaultEditingContext().saveChanges();
		componentToReturn.ensureAwakeInContext( context() );
		return componentToReturn;
	}

	/**
	 * Just returns "componentToReturn" without executing a transfer.
	 */
	public WOComponent cancel() {
		componentToReturn.ensureAwakeInContext( context() );
		return componentToReturn;
	}

	/**
	 * @return The full class name of the containing folder entity class.
	 */
	public String folderEntityName() {
		return ((SWAsset)record).containingFolder().entityName();
	}

	public String entityName() {
		String entityName = record.entityName();
		return entityName;
	}

	public boolean canCreateCopy() {
		return record instanceof SWCopyable;
	}
}