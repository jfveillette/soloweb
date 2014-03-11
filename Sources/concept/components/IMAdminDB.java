package concept.components;

import is.rebbi.wo.definitions.AttributeViewDefinition;
import is.rebbi.wo.definitions.EntityViewDefinition;
import is.rebbi.wo.util.HumanReadable;
import is.rebbi.wo.util.Inspection;
import is.rebbi.wo.util.USEOUtilities;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOContext;
import com.webobjects.eoaccess.EODatabaseDataSource;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOSortOrdering;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableArray;

import er.extensions.appserver.ERXDisplayGroup;
import er.extensions.components.ERXComponent;
import er.extensions.eof.ERXGenericRecord;

public class IMAdminDB extends ERXComponent {

	public boolean detailSearch = false;

	public ERXDisplayGroup dg;
	public String searchString;

	private EntityViewDefinition _selectedObject;
	public AttributeViewDefinition currentAttribute;

	public ERXGenericRecord currentObject;

	public IMAdminDB( WOContext context ) {
		super( context );
	}

	private EOEditingContext ec() {
		return session().defaultEditingContext();
	}

	@Override
	public void ensureAwakeInContext( WOContext aContext ) {
		super.ensureAwakeInContext( aContext );
//		resetDG();
	}

	public WOActionResults toggleDetailSearch() {
		detailSearch = !detailSearch;
		return null;
	}

	public void resetDG() {
//		dg = new ERXBatchingDisplayGroup();
		dg = new ERXDisplayGroup();
		dg.setDataSource( new EODatabaseDataSource( ec(), selectedObject().entity().name() ) );
		dg.setNumberOfObjectsPerBatch( 100 );

		NSMutableArray<EOSortOrdering> a = selectedObject().defaultSortOrderings().mutableClone();

		dg.setSortOrderings( a );

		if( searchString != null ) {
			dg.setQualifier( USEOUtilities.allQualifier( searchString, selectedObject().entity() ) );
		}

		dg.qualifyDataSource();
	}

	public NSArray objects() {
		NSMutableArray<EOSortOrdering> a = selectedObject().defaultSortOrderings().mutableClone();

		if( a.count() == 0 ) {
			if( HumanReadable.class.isAssignableFrom( selectedObject().objectClass() ) ) {
				a.addObject( new EOSortOrdering( "toStringHuman", EOSortOrdering.CompareCaseInsensitiveAscending ) );
			}
		}

		return EOSortOrdering.sortedArrayUsingKeyOrderArray( dg.displayedObjects(), a );
	}

	public Object currentValue() {
		return currentObject.valueForKeyPath( currentAttribute.name() );
	}

	public String currentHumanToStringValue() {
		return HumanReadable.DefaultImplementation.toStringHuman( currentObject );
	}

	public EntityViewDefinition selectedObject() {
		return _selectedObject;
	}

	public void setSelectedObject( EntityViewDefinition viewDefinition ) {
		_selectedObject = viewDefinition;
		resetDG();
	}

	public WOActionResults createObject() {
		try {
			Class clazz = Class.forName( selectedObject().entity().className() );
			WOActionResults nextPage = Inspection.createAndEditObject( ec(), clazz, context() );
			ec().saveChanges();
			return nextPage;
		}
		catch( ClassNotFoundException e ) {
			throw new RuntimeException( e );
		}
	}
}