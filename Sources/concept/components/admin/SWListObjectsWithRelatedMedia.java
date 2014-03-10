package concept.components.admin;

import is.rebbi.wo.definitions.EntityViewDefinition;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOContext;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOQualifier;
import com.webobjects.eocontrol.EOSortOrdering;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSDictionary;
import com.webobjects.foundation.NSMutableArray;

import concept.data.SWDocumentLink;
import er.extensions.components.ERXComponent;
import er.extensions.foundation.ERXArrayUtilities;

public class SWListObjectsWithRelatedMedia extends ERXComponent {

	public int currentIndex;
	public int maxItems = 1;
	public EntityViewDefinition item;
	public EntityViewDefinition selection;

	public NSArray<SWDocumentLink> links;
	public SWDocumentLink currentLink;

	public SWListObjectsWithRelatedMedia( WOContext context ) {
		super( context );
	}

	public NSArray<EntityViewDefinition> list() {
		return EntityViewDefinition.all();
	}

	public int currentIndexForDisplay() {
		return currentIndex + 1;
	}

	public WOActionResults search() {
		NSMutableArray<SWDocumentLink> results = new NSMutableArray<SWDocumentLink>();
		EOEditingContext ec = session().defaultEditingContext();
		EOQualifier q = SWDocumentLink.TARGET_ENTITY_NAME.eq( selection.name() );
		NSArray<SWDocumentLink> a = SWDocumentLink.fetchSWDocumentLinks( ec, q, SWDocumentLink.TARGET_ID.ascs() );
		NSDictionary<Object, NSArray<SWDocumentLink>> groups = ERXArrayUtilities.arrayGroupedByKeyPath( a, SWDocumentLink.TARGET_ID_KEY );

		for( Object key : groups.allKeys() ) {
			NSArray<SWDocumentLink> group = groups.objectForKey( key );

			if( group.count() > maxItems ) {
				results.addObjectsFromArray( group );
			}
		}

		EOSortOrdering.sortArrayUsingKeyOrderArray( results, SWDocumentLink.TARGET_ID.ascs() );

		links = results;
		return null;
	}
}