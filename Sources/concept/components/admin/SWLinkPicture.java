package concept.components.admin;

import com.webobjects.appserver.WOComponent;
import com.webobjects.appserver.WOContext;

import concept.data.SWAssetFolder;
import concept.data.SWPicture;
import concept.data.SWPictureLink;
import er.extensions.components.ERXComponent;

public class SWLinkPicture extends ERXComponent {

	public SWPictureLink link = new SWPictureLink();

	public SWLinkPicture( WOContext context ) {
		super( context );
	}

	/** return unique name for alignment radio button group **/
	public String radioName() {
		String id = "";
		if( link != null ) {
			id = link.componentID() + "_" + link.pictureID();
		}

		return "align" + id;
	}

	public String radioLink() {
		String id = "";
		if( link != null ) {
			id = link.componentID() + "_" + link.pictureID();
		}

		return "link" + id;
	}

	public WOComponent browsePictures() {
		/*SWPictureLink link = new SWPictureLink();
		link.setName( _placeHolder );
		link.setAlign(0);
		//link.setComponent(currentComponent);
		ec.insertObject(link);
		link.addObjectToBothSidesOfRelationshipWithKey( currentComponent, "component" );
		//ec.saveChanges();
		*/

		SWAssetManagement nextPage = pageWithName( SWAssetManagement.class );
		nextPage.setEntityName( SWPicture.ENTITY_NAME );
		nextPage.setFolderEntityName( SWAssetFolder.ENTITY_NAME );
		nextPage.setEditingComponentName( SWEditDataAsset.class.getName() );
		nextPage.componentToReturn = context().page();
		nextPage.record = link;
		nextPage.fieldName = "picture";
		nextPage.useID = false;

		return nextPage;
	}
}