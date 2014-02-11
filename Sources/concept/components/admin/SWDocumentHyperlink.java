package concept.components.admin;

import com.webobjects.appserver.WOComponent;
import com.webobjects.appserver.WOContext;
import com.webobjects.foundation.NSKeyValueCoding;

import concept.data.SWDocument;
import concept.data.SWDocumentFolder;
import er.extensions.components.ERXComponent;

/**
 * Use this component to select an SWPicture to use.
 * To use this component, include it in your page, and specify a record to connect to a picture,
 * the name of the relationship pointing to the SWPicture, and an instance of a WOComponent to
 * return to, once picture selection has been completed.
 */

public class SWDocumentHyperlink extends ERXComponent {

	/**
	 * The record to bind to an SWPicture
	 */
	public NSKeyValueCoding record;

	/**
	 * Indicates if we should set an ID, rather than an SWPicture object.
	 */
	public boolean useID = false;

	/**
	 * The name of the relationship that points to the SWPicture
	 */
	public String fieldName;

	/**
	 * WOComponent instance to return after picture selection has been completed
	 */
	public WOComponent componentToReturn;

	public SWDocumentHyperlink( WOContext context ) {
		super( context );
	}

	/**
	 * The method invoked when the user clicks the "select document" button.
	 * Takes the user to "SWSelectAsset" where a document can be selected.
	 */
	public WOComponent selectDocument() {

		SWAssetManagement nextPage = pageWithName( SWAssetManagement.class );

		if( componentToReturn == null ) {
			componentToReturn = context().page();
		}

		nextPage.setEntityName( SWDocument.ENTITY_NAME );
		nextPage.setFolderEntityName( SWDocumentFolder.ENTITY_NAME );

		nextPage.setEditingComponentName( SWEditDataAsset.class.getName() );

		nextPage.componentToReturn = componentToReturn;
		nextPage.record = record;
		nextPage.fieldName = fieldName;
		nextPage.useID = useID;

		return nextPage;
	}
}