package concept.components.jplayer;

import is.rebbi.wo.util.USUtilities;

import com.webobjects.appserver.WOApplication;
import com.webobjects.appserver.WOContext;
import com.webobjects.appserver.WORequest;
import com.webobjects.appserver.WOResponse;
import com.webobjects.foundation.NSArray;

import concept.Concept;
import er.ajax.AjaxUtils;
import er.extensions.components.ERXStatelessComponent;

public class JPlayerAudio extends ERXStatelessComponent {

	private static final String FAKE_RESOURCE = "empty.css";

	public JPlayerAudio( WOContext context ) {
		super( context );
	}

	@Override
	public void appendToResponse( WOResponse r, WOContext c ) {
		super.appendToResponse( r, c );
		AjaxUtils.addStylesheetResourceInHead( context(), r, Concept.sw().frameworkBundleName(), "jplayer/jplayer.blue.monday.css" );
		AjaxUtils.addScriptResourceInHead( context(), r, Concept.sw().frameworkBundleName(), "jplayer/jquery.jplayer.min.js" );
	}

	public String swfPath() {
		return urlForFolderNamed( "jplayer", context().request() );
	}

	private static String urlForFolderNamed( String folderName, WORequest request ) {
		String url = WOApplication.application().resourceManager().urlForResourceNamed( folderName + "/" + FAKE_RESOURCE, Concept.sw().frameworkBundleName(), NSArray.<String> emptyArray(), request );
		url = url.substring( 0, url.length() - FAKE_RESOURCE.length() - 1 );
		return url;
	}

	public boolean autoplay() {
		return USUtilities.booleanFromObject( context().request().stringFormValueForKey( "autoplay" ) );
	}
}