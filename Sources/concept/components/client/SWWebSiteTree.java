package concept.components.client;

import com.webobjects.appserver.WOContext;

import concept.SWGenericComponent;
import concept.SWPageUtilities;
import concept.data.SWPage;

public class SWWebSiteTree extends SWGenericComponent {

	public SWWebSiteTree( WOContext context ) {
		super( context );
	}

	public SWPage parentPage() {
		return SWPageUtilities.pageWithID( session().defaultEditingContext(), pageID() );
	}

	public Integer pageID() {
		SWPage parent = this.selectedPage();
		parent = parent.topLevelPage();
		return new Integer( parent.primaryKey() );
	}
}