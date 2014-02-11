package concept.components.admin;

import is.rebbi.core.util.StringUtilities;

import com.webobjects.appserver.WOComponent;
import com.webobjects.appserver.WOContext;

public class SWTextEditing extends WOComponent {

	public String content;

	public SWTextEditing( WOContext context ) {
		super( context );
	}

	public boolean isExplorerCompatible() {

		String userAgentString = context().request().headerForKey( "User-Agent" );

		if( !StringUtilities.hasValue( userAgentString ) ) {
			return false;
		}

		userAgentString = userAgentString.toLowerCase();

		if( userAgentString.indexOf( "msie" ) != -1 && userAgentString.indexOf( "windows" ) != -1 ) {
			return true;
		}

		if( userAgentString.indexOf( "safari" ) != -1 ) {
			if( userAgentString.indexOf( "version/2" ) != -1 || userAgentString.indexOf( "version/1" ) != -1 ) {
				return false;
			}
			else {
				return true;
			}
		}

		if( userAgentString.indexOf( "mozilla" ) != -1 && userAgentString.indexOf( "safari" ) == -1 ) {
			return true;
		}

		return false;
	}
}