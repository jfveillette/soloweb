package concept;

import is.rebbi.core.util.StringUtilities;
import is.rebbi.wo.util.USEOUtilities;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOComponent;
import com.webobjects.appserver.WOContext;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSDictionary;
import com.webobjects.foundation.NSMutableArray;

import concept.components.tabs.IMTab;
import concept.definitions.EntityViewDefinition;
import er.extensions.eof.ERXGenericRecord;

/**
 * All detail pages inherit from this class.
 */

public abstract class ViewPage<E extends ERXGenericRecord> extends SWBaseComponent {

	/**
	 * The component to return to in case of action cancellation.
	 */
	private WOComponent _componentToReturnTo;

	/**
	 * Currently selected tab in the UI.
	 */
	public IMTab selectedTab;

	/**
	 * An error displayed to the user.
	 */
	public String errorMessage;

	public ViewPage( WOContext context ) {
		super( context );
	}

	@Override
	public E selectedObject() {
		return (E)super.selectedObject();
	}

	@Override
	public EOEditingContext ec() {
		return selectedObject().editingContext();
	}

	public EntityViewDefinition viewDefinition() {
		return EntityViewDefinition.get( selectedObject() );
	}

    public WOComponent callingComponent() {
    	return _componentToReturnTo;
    }

    public void setCallingComponent( WOComponent value ) {
    	_componentToReturnTo = value;
    }

    public NSArray<IMTab> baseTabs() {
    	NSMutableArray<IMTab> a = new NSMutableArray<>();
//    	a.addObject( new IMTab( "Base" ) );
    	return a;
    }

	public NSArray<IMTab> additionalTabs() {
		return NSArray.emptyArray();
	}

    public NSArray<IMTab> tabs() {
    	return additionalTabs().arrayByAddingObjectsFromArray( baseTabs() );
    }

	public WOActionResults error( String message ) {
		errorMessage = message;
		return null;
	}

    public WOActionResults saveChangesAndReturn() {
    	saveChanges();
    	return returnToCallingComponent();
    }

    public WOActionResults saveChanges() {
    	String message = performValidation();

    	if( StringUtilities.hasValue( message ) ) {
    		return error( message );
    	}

    	saveChangesToObjectStore();

    	return null;
    }

    protected void saveChangesToObjectStore() {
    	EOEditingContext ec = selectedObject().editingContext();
    	ec.saveChanges();

    	if( USEOUtilities.isNested( ec ) ) {
    		EOEditingContext parent = ((EOEditingContext)ec.parentObjectStore());
    		parent.saveChanges();
    	}
    }

    public String performValidation() {
    	return null;
    }

	/**
	 * Deletes the currently selected object.
	 */
	public WOActionResults deleteObject() {
//		boolean willReturnToSameObject = selectedObject().equals( ((CPBaseComponent)callingComponent()).selectedObject() );
		ec().deleteObject( selectedObject() );
		saveChangesToObjectStore();

//		if( willReturnToSameObject ) {
//			return USHTTPUtilities.redirectTemporary( "http://www.ismus.is" );
//		}

		return returnToCallingComponent();
	}

	/**
	 * @return The component instance that invoked this component.
	 */
    public WOActionResults returnToCallingComponent() {
    	callingComponent().ensureAwakeInContext( context() );
    	return callingComponent();
    }

	/**
	 * @return Privileges for the selected Object.
	 */
	public NSDictionary<String, String> privilegePairs() {
		return SWAccessPrivilegeUtilities.privilegePairsForObject( selectedObject(), context() );
	}
}