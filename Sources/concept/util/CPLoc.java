package concept.util;

import java.util.Locale;

import com.webobjects.appserver.WOApplication;
import com.webobjects.appserver.WOContext;
import com.webobjects.appserver.WORequest;
import com.webobjects.foundation.NSArray;

import concept.Concept;
import concept.SWSession;

/**
 * A shorthand for simplifying localization of strings (see documentation for the "string"-method).
 */

public class CPLoc {

	private static final String LANGUAGE_ICELANDIC = "Icelandic";
	private static final String LANGUAGE_ENGLISH = "English";
	public static final NSArray<String> AVAILABLE_LANGUAGES = new NSArray<>( LANGUAGE_ICELANDIC, LANGUAGE_ENGLISH );
	public static final String DEFAULT_LANGUAGE = LANGUAGE_ICELANDIC;

	public static final String LS_KEYPATH = "@ls";
	public static final String CS_KEYPATH = "@cs";

	/**
	 * We don't want any instances of this class.
	 */
	private CPLoc() {}

	/**
	 * @return The named localized string. If the string exists in the application package, it will be fetched from there, otherwise it will be fetched from the framework.
	 */
	private static String string( String key, NSArray<String> languages ) {
		String s = localizedStringInBundleNamed( key, WOApplication.application().name(), languages );

		if( notFound( key ).equals( s ) ) {
			s = localizedStringInBundleNamed( key, Concept.sw().frameworkBundleName(), languages );
		}

		return s;
	}

	/**
	 * @return The named localized string. If the string exists in the application package, it will be fetched from there, otherwise it will be fetched from the framework.
	 */
	public static String string( String key, WOContext context ) {
		return string( key, languagesInContext( context ) );
	}

	/**
	 * @return The language in the given context.
	 */
	private static NSArray<String> languagesInContext( WOContext context ) {
		NSArray<String> languages = null;

		Locale locale = localeInRequest( context.request() );
		String language = locale.getDisplayName( Locale.ENGLISH );

		if( language != null ) {
			languages = new NSArray<>( language );
		}
		else {
			if( context.hasSession() ) {
				languages = context.session().languages();
			}
		}

		if( languages == null ) {
			languages = new NSArray<>( DEFAULT_LANGUAGE );
		}

		return languages;
	}

	public static Locale localeInRequest( WORequest request ) {
		return new Locale( "is" );
	}

	public static String confirmString( String key, WOContext context ) {
		StringBuilder b = new StringBuilder();

		b.append( "return confirm('" );
		b.append( string( key, context ) );
		b.append( "');" );

		return b.toString();
	}

	private static String localizedStringInBundleNamed( String key, String bundleName, NSArray<String> languages ) {
		return WOApplication.application().resourceManager().stringForKey( key, "localizedStrings", notFound( key ), bundleName, languages );
	}

	private static String notFound( String key ) {
		return "Localized string not found [" + key + "]";
	}

	public static String string( String key, com.webobjects.appserver.WOSession session ) {
		return ((SWSession)session).localizedStringForKey( key );
	}
}