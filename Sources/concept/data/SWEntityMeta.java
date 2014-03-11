package concept.data;

import is.rebbi.wo.definitions.AttributeViewDefinition;
import is.rebbi.wo.definitions.EntityViewDefinition;
import is.rebbi.wo.util.HumanReadable;
import is.rebbi.wo.util.USUtilities;

import com.webobjects.eocontrol.EOSortOrdering;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSBundle;

import concept.ViewPage;

/**
 * Further definition of an EOEntity.
 */

public class SWEntityMeta extends concept.data.auto._SWEntityMeta implements HumanReadable {

	private NSArray<EOSortOrdering> USER_DEFINED_ATTRIBUTE_SORTING = SWAttributeMeta.SORT_ORDER.ascs().then( SWAttributeMeta.NAME.asc() );

	public EntityViewDefinition toEntityViewDefinition() {
		EntityViewDefinition e = EntityViewDefinition.create( name(), icelandicName(), icelandicNamePlural(), categoryName(), text(), urlPrefix(), iconFileName(), USUtilities.booleanFromObject( showInLists() ), viewComponentClass(), editComponentClass() );

		for( SWAttributeMeta am : columns() ) {
			AttributeViewDefinition attributeViewDefinition = am.toAttributeViewDefinition();
			e.addAttributeViewDefinition( attributeViewDefinition );
		}

		return e;
	}

	public NSArray<SWAttributeMeta> columnsSorted() {
		return EOSortOrdering.sortedArrayUsingKeyOrderArray( columns(), USER_DEFINED_ATTRIBUTE_SORTING );
	}

	public Class<? extends ViewPage> editComponentClass() {
		if( editComponentName() != null ) {
			return NSBundle.mainBundle()._classWithName( editComponentName() );
		}

		return null;
	}

	public Class<? extends ViewPage> viewComponentClass() {
		if( viewComponentName() != null ) {
			return NSBundle.mainBundle()._classWithName( viewComponentName() );
		}

		return null;
	}

	@Override
	public String toStringHuman() {
		return name();
	}
}