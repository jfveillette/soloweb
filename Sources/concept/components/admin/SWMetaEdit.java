package concept.components.admin;

import is.rebbi.wo.interfaces.HasFakeRelationship;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOContext;
import com.webobjects.foundation.NSArray;

import concept.ViewPage;
import concept.data.SWMeta;

public class SWMetaEdit extends ViewPage {

	public SWMeta currentObject;

	public String newName;
	public String newText;

	public SWMetaEdit( WOContext context ) {
		super( context );
	}

	public NSArray<SWMeta> meta() {
		return HasFakeRelationship.Util.relatedObjects( SWMeta.class, selectedObject(), SWMeta.NAME.ascInsensitives() );
	}

	public WOActionResults create() {
		SWMeta meta = HasFakeRelationship.Util.create( SWMeta.class, selectedObject() );
		meta.setName( newName );
		meta.setText( newText );
		ec().saveChanges();

		newName = null;
		newText = null;

		return null;
	}

	public WOActionResults delete() {
		currentObject.delete();
		ec().saveChanges();
		return null;
	}
}