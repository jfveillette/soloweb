package concept.deprecated;

import concept.SWSession;

/**
 * A shorthand for simplifying localization of strings (see documentation for the "string"-method).
 */

@Deprecated
public class SWLoc {

	/**
	 * We don't want any instances of this class.
	 */
	private SWLoc() {}

	/**
	 * Returns a localized string based on a key (the string's key in the localizedStrings-file) and
	 * the language selected in the given session.
	 */
	public static String string( String key, com.webobjects.appserver.WOSession session ) {
		return ((SWSession)session).localizedStringForKey( key );
	}
}