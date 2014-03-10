package concept.components;

import is.rebbi.wo.definitions.EntityViewDefinition;
import is.rebbi.wo.urls.SWURLProvider;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOContext;

import er.extensions.appserver.ERXApplication;
import er.extensions.components.ERXStatelessComponent;

public class ListLink extends ERXStatelessComponent {

	public ListLink( WOContext context ) {
		super( context );
	}

	public EntityViewDefinition viewDefinition() {
		return (EntityViewDefinition)valueForBinding( "viewDefinition" );
	}

	public WOActionResults selectType() {
		ListPageGeneric nextPage = pageWithName( ListPageGeneric.class );
		nextPage.setViewDefinition( viewDefinition() );
		return nextPage;
	}

	public String url() {
		String url = "/l/" + viewDefinition().urlPrefix();

		if( ERXApplication.erxApplication().isDevelopmentMode() ) {
			url = SWURLProvider.makeURLDeveloperFriendly( url, context() );
		}

		return url;
	}
}