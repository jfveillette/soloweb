package concept.components.admin;

import is.rebbi.wo.util.USUtilities;

import com.webobjects.appserver.WOComponent;
import com.webobjects.appserver.WOContext;
import com.webobjects.eocontrol.EOEnterpriseObject;
import com.webobjects.foundation.NSKeyValueCoding;
import com.webobjects.foundation.NSKeyValueCodingAdditions;

import concept.data.SWAssetFolder;
import concept.data.SWPicture;

import er.extensions.components.ERXComponent;

/**
 * Use this component to select an SWPicture to use.
 * To use this component, include it in your page, and specify a record to connect to a picture,
 * the name of the relationship pointing to the SWPicture, and an instance of a WOComponent to
 * return to, once picture selection has been completed.
 */

public class SWPictureHyperlink extends ERXComponent {

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
	 * A WOComponent instance to return after picture selection has been completed
	 */
	public WOComponent componentToReturn;

	/**
	 * the selected preview size - make sure to bind this to some variable or customInfo
	 */
	public String selectedSize;

	/**
	 * allow selection of sizes
	 */
	public boolean showSizes = true;

	public SWPictureHyperlink( WOContext context ) {
		super( context );
	}

	/**
	    * The method invoked when the user clicks the "select picture" button.
	    * Takes the user to "SWSelectAsset" where a picture can be selected.
	    */
	public WOComponent selectPicture() {

		SWAssetManagement nextPage = pageWithName( SWAssetManagement.class );

		if( componentToReturn == null ) {
			componentToReturn = context().page();
		}

		nextPage.setEntityName( SWPicture.ENTITY_NAME );
		nextPage.setFolderEntityName( SWAssetFolder.ENTITY_NAME );

		nextPage.setEditingComponentName( SWEditDataAsset.class.getName() );

		nextPage.componentToReturn = componentToReturn;
		nextPage.record = record;
		nextPage.fieldName = fieldName;
		nextPage.useID = useID;

		return nextPage;
	}

	/**
	    * returns the currently selected picture (if any)
	    */
	public SWPicture selectedPicture() {
		Object o = record.valueForKey( fieldName );

		if( !useID ) {
			return (SWPicture)o;
		}
		else {
			Integer i = USUtilities.integerFromObject( o );
			return SWPicture.pictureWithID( session().defaultEditingContext(), i );
		}
	}

	/**
	    * Removes the currently attached picture
	    */
	public WOComponent removeImage() {

		if( record instanceof EOEnterpriseObject ) {
			if( !useID ) {
				((EOEnterpriseObject)record).removeObjectFromBothSidesOfRelationshipWithKey( (SWPicture)record.valueForKey( fieldName ), fieldName );
			}
			else {
				((EOEnterpriseObject)record).takeValueForKeyPath( null, fieldName );
			}

			//((EOEnterpriseObject)record).editingContext().saveChanges();
			session().defaultEditingContext().saveChanges();
		}
		else if( record instanceof NSKeyValueCodingAdditions ) {
			((NSKeyValueCodingAdditions)record).takeValueForKeyPath( null, fieldName );
		}
		else {
			record.takeValueForKey( null, fieldName );
		}

		return null;
	}
}