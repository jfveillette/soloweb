package concept.components.settings;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOApplication;
import com.webobjects.appserver.WOContext;

/**
 * Various actions that can be performed in the administrative interface.
 */

public class SWSettingsAction extends SWSettingsPanel {

	public SWSettingsAction( WOContext context ) {
		super( context );
	}

	/**
	 * Resets all component definitions.
	 */
	public WOActionResults flushComponentCache() {
		WOApplication.application()._removeComponentDefinitionCacheContents();
		return null;
	}
}