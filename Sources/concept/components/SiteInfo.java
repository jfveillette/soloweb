package concept.components;

import is.rebbi.wo.util.SoftUser;
import is.rebbi.wo.util.USHTTPUtilities;

import com.webobjects.appserver.WOContext;
import com.webobjects.foundation.NSTimestamp;

import concept.managers.StatsManager;
import er.extensions.components.ERXComponent;
import er.extensions.foundation.ERXStringUtilities;

public class SiteInfo extends ERXComponent {

	public SiteInfo( WOContext context ) {
		super( context );
	}

	public NSTimestamp now() {
		return new NSTimestamp();
	}

	public String uri() {
		return USHTTPUtilities.redirectURL( context().request() );
	}

	public SoftUser softUser() {
		return SoftUser.fromRequest( context().request() );
	}

	public String branch() {
		return ERXStringUtilities.stringFromResource( "branch", "txt", null );
	}

	public String changeset() {
		return ERXStringUtilities.stringFromResource( "changeset", "txt", null );
	}

	public String buildDate() {
		return ERXStringUtilities.stringFromResource( "buildDate", "txt", null );
	}

	public NSTimestamp startupDate() {
		return StatsManager.instance().startupTime();
	}
}