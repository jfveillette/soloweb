package concept.components;

import is.rebbi.wo.definitions.AttributeViewDefinition;
import is.rebbi.wo.definitions.EntityViewDefinition;
import is.rebbi.wo.util.USEOUtilities;

import com.webobjects.appserver.WOContext;
import com.webobjects.eoaccess.EODatabaseDataSource;
import com.webobjects.eocontrol.EOEditingContext;

import er.extensions.appserver.ERXDisplayGroup;
import er.extensions.batching.ERXBatchingDisplayGroup;
import er.extensions.components.ERXComponent;
import er.extensions.eof.ERXGenericRecord;

public class ListPageGeneric extends ERXComponent {

	public ERXDisplayGroup dg;
	public String searchString;

	private EntityViewDefinition _viewDefinition;
	public ERXGenericRecord currentObject;
	public AttributeViewDefinition currentAttribute;

	public int currentColumnIndex;

	public ListPageGeneric( WOContext context ) {
		super( context );
	}

	private EOEditingContext ec() {
		return session().defaultEditingContext();
	}

	public void resetDG() {
		dg = new ERXBatchingDisplayGroup();
		dg.setDataSource( new EODatabaseDataSource( ec(), viewDefinition().entity().name() ) );
		dg.setNumberOfObjectsPerBatch( 100 );
		dg.setSortOrderings( viewDefinition().defaultSortOrderings() );

		if( searchString != null ) {
			dg.setQualifier( USEOUtilities.allQualifier( searchString, viewDefinition().entity() ) );
		}

		dg.qualifyDataSource();
	}

	public Object currentValue() {
		return currentObject.valueForKeyPath( currentAttribute.name() );
	}

	public EntityViewDefinition viewDefinition() {
		return _viewDefinition;
	}

	public void setViewDefinition( EntityViewDefinition value ) {
		_viewDefinition = value;
		resetDG();
	}

	public boolean isNotFirst() {
		return currentColumnIndex > 0;
	}
}