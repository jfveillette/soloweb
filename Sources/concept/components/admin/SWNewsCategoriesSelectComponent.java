package concept.components.admin;

import com.webobjects.appserver.WOApplication;
import com.webobjects.appserver.WOComponent;
import com.webobjects.appserver.WOContext;

import concept.Concept;
import concept.data.SWNewsCategory;

/**
 * Selection of extra news categories.
 */

public class SWNewsCategoriesSelectComponent extends WOComponent {

	public SWNewsCategory selectedFolder;
	public String initialSelectedIds;
	public String folderEntityName = SWNewsCategory.ENTITY_NAME;

	/**
	 * Default constructor
	 */
	public SWNewsCategoriesSelectComponent( WOContext context ) {
		super( context );
	}

	public String initialSelectedIds() {
		return initialSelectedIds;
	}

	public void setInitialSelectedIds( String newInitialSelectedIds ) {
		initialSelectedIds = newInitialSelectedIds;
	}

	public String href() {
		return WOApplication.application().resourceManager().urlForResourceNamed( "sw32/css/soloweb.css", Concept.sw().frameworkBundleName(), null, context().request() );
	}
}