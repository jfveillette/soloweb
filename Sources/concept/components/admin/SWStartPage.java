package concept.components.admin;

import is.rebbi.core.util.StringUtilities;
import is.rebbi.wo.util.SWSettings;

import com.webobjects.appserver.WOComponent;
import com.webobjects.appserver.WOContext;
import com.webobjects.appserver.WOResponse;

import concept.SWPageUtilities;

public class SWStartPage extends WOComponent {

	public SWStartPage( WOContext context ) {
		super( context );
	}

	@Override
	public void appendToResponse( WOResponse r, WOContext c ) {
		String s = SWSettings.stringForKey( "systemFrontPageLinkingName" );

		if( StringUtilities.hasValue( s ) ) {
			WOComponent component = SWPageUtilities.pageMatchingName( session().defaultEditingContext(), context(), s );

			if( component != null ) {
				r.setContent( component.generateResponse().content() );
			}
		}
		else {
			super.appendToResponse( r, c );
		}
	}
}