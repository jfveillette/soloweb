package concept.components.client;

import is.rebbi.wo.util.SWSettings;

import com.webobjects.appserver.WOContext;

import concept.SWGenericTemplate;

/**
 * This component is displayed for a page that has a password.
 */

public class SWPasswordFieldComponent extends SWGenericTemplate {

	public SWPasswordFieldComponent( WOContext context ) {
		super( context );
	}

	public String userMessage() {
		return SWSettings.stringForKey( "defaultPasswordProtectedString", "This page is public with a password" );
	}

	public String buttonTitle() {
		return SWSettings.stringForKey( "defaultPasswordProtectedButtonTitle", "Submit" );
	}
}