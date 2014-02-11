package concept.components.admin;

import is.rebbi.wo.util.USEOUtilities;
import is.rebbi.wo.util.USUtilities;

import com.webobjects.appserver.WOComponent;
import com.webobjects.appserver.WOContext;
import com.webobjects.eocontrol.EOEnterpriseObject;
import com.webobjects.eocontrol.EOFetchSpecification;
import com.webobjects.eocontrol.EOSortOrdering;
import com.webobjects.foundation.NSArray;

import concept.SWAdminComponent;
import concept.SWApplication;
import concept.data.SWGroup;
import concept.data.SWPage;

public class SWEditPageGeneralInfo extends SWAdminComponent {

	public SWPage selectedPage;

	/**
	 * The current user or group being iterated over in the user/group pop-up-menu
	 */
	public EOEnterpriseObject currentObject;

	public String lookPopupItem;

	public SWEditPageGeneralInfo( WOContext context ) {
		super( context );
	}

	public void setPageName( String value ) {
		selectedPage.setName( value );
	}

	public String pageName() {
		return selectedPage.name();
	}

	public void setPageSymbol( String value ) {
		selectedPage.setSymbol( value );
	}

	public String pageSymbol() {
		return selectedPage.symbol();
	}

	public void setSelectedGroup( SWGroup newSelectedGroup ) {
		Integer groupID;

		if( newSelectedGroup != null ) {
			groupID = Integer.valueOf( newSelectedGroup.primaryKey() );
		}
		else {
			groupID = new Integer( -1 );
		}

		selectedPage.customInfo().takeValueForKey( groupID, "assignedGroup" );
	}

	public SWGroup getSelectedGroup() {
		Object groupIDObject = selectedPage.customInfo().valueForKey( "assignedGroup" );
		Integer groupID = USUtilities.integerFromObject( groupIDObject );

		if( groupID != null && groupID.intValue() != -1 ) {
			try {
				return (SWGroup)(USEOUtilities.objectWithPK( session().defaultEditingContext(), SWGroup.ENTITY_NAME, groupID ));
			}
			catch( Exception ex ) {}
		}

		return null;
	}

	public WOComponent editCustomInfoAsText() {
		SWEditDocumentContentAsText nextPage = (SWEditDocumentContentAsText)pageWithName( "SWEditDocumentContentAsText" );
		nextPage.selectedPage = selectedPage;
		return nextPage;
	}

	/**
	 * Retrieves all groups in the SoloWeb system sorted by name
	 */
	public NSArray allGroups() {
		EOSortOrdering s = new EOSortOrdering( "name", EOSortOrdering.CompareAscending );
		EOFetchSpecification fs = new EOFetchSpecification( "SWGroup", null, new NSArray( s ) );
		return session().defaultEditingContext().objectsWithFetchSpecification( fs );
	}

	public NSArray lookPopupItems() {
		return SWApplication.looks();
	}
}