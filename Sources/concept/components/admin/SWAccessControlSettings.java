package concept.components.admin;

import is.rebbi.wo.util.USEOUtilities;

import com.webobjects.appserver.WOContext;
import com.webobjects.eocontrol.EOFetchSpecification;
import com.webobjects.foundation.NSArray;

import concept.data.SWGroup;

public class SWAccessControlSettings extends SWManageSettings {

	public SWGroup currentGroup;

	public SWAccessControlSettings( WOContext context ) {
		super( context );
	}

	public NSArray<SWGroup> allGroups() {
		EOFetchSpecification fs = new EOFetchSpecification( SWGroup.ENTITY_NAME, null, SWGroup.NAME.ascInsensitives() );
		return session().defaultEditingContext().objectsWithFetchSpecification( fs );
	}

	public SWGroup selectedGroup() {
		try {
			return (SWGroup)USEOUtilities.objectWithPK( session().defaultEditingContext(), SWGroup.ENTITY_NAME, selectedDictionary().valueForKey( "allUserGroupID" ) );
		}
		catch( Exception e ) {
			return null;
		}
	}

	public void setSelectedGroup( SWGroup newSelectedGroup ) {
		if( newSelectedGroup == null ) {
			selectedDictionary().takeValueForKey( null, "allUserGroupID" );
		}
		else {
			selectedDictionary().takeValueForKey( Integer.valueOf( newSelectedGroup.primaryKey() ), "allUserGroupID" );
		}
	}
}