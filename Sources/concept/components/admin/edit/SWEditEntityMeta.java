package concept.components.admin.edit;

import is.rebbi.wo.util.IMTab;
import is.rebbi.wo.util.USEOUtilities;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOContext;
import com.webobjects.eoaccess.EOModelGroup;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableArray;

import concept.ViewPage;
import concept.data.SWAttributeMeta;
import concept.data.SWEntityMeta;

public class SWEditEntityMeta extends ViewPage<SWEntityMeta> {

	public SWAttributeMeta currentColumn;
	public String currentAttributeName;

	public SWEditEntityMeta( WOContext context ) {
		super( context );
	}

	public WOActionResults createAttributeMeta() {
		SWAttributeMeta a = selectedObject().createColumnsRelationship();
		a.setName( currentAttributeName );
		return null;
	}

	public WOActionResults deleteAttributeMeta() {
		ec().deleteObject( currentColumn );
		saveChangesToObjectStore();
		return null;
	}

	public NSArray<String> unmappedAttributeNames() {

		if( selectedObject().name() == null ) {
			return NSArray.emptyArray();
		}

		NSMutableArray<String> attributeNames = new NSMutableArray<>();
		attributeNames.addObjectsFromArray( (NSArray<? extends String>)USEOUtilities.relationships( EOModelGroup.defaultGroup().entityNamed( selectedObject().name() ) ).valueForKeyPath( "name" ) );
		attributeNames.addObjectsFromArray( (NSArray<? extends String>)USEOUtilities.attributes( EOModelGroup.defaultGroup().entityNamed( selectedObject().name() ) ).valueForKeyPath( "name" ) );
		attributeNames.removeObjectsInArray( mappedAttributeNames() );
		return attributeNames;
	}

	public NSArray<String> mappedAttributeNames() {
		return (NSArray<String>)selectedObject().columns().valueForKeyPath( "name" );
	}

	@Override
	public NSArray<IMTab> additionalTabs() {
		NSMutableArray<IMTab> tabs = new NSMutableArray<IMTab>();

		tabs.addObject( new IMTab( "Almennt" ) );
		tabs.addObject( new IMTab( "Skilgreindir reitir" ) );
		tabs.addObject( new IMTab( "Óskilgreindir reitir" ) );

		return tabs;
	}
}