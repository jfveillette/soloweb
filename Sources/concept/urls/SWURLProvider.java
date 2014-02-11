package concept.urls;

import is.rebbi.wo.util.SWSettings;
import is.rebbi.wo.util.USHTTPUtilities;

import com.webobjects.appserver.WOContext;
import com.webobjects.eocontrol.EOGenericRecord;
import com.webobjects.foundation.NSDictionary;
import com.webobjects.foundation.NSMutableDictionary;

import er.extensions.foundation.ERXStringUtilities;

/**
 * Generates URLs.
 */

public abstract class SWURLProvider {

	private static NSDictionary<Class, SWURLProvider> _urlProviders;

	public abstract String urlForObject( Object object, WOContext context );

	/**
	 * @return A URL for the given object.
	 */
	public static String urlForObjectInContext( Object object, WOContext context ) {
		return urlProviderForClass( object.getClass() ).urlForObject( object, context );
	}

	private static NSDictionary<Class, SWURLProvider> urlProviders() {
		if( _urlProviders == null ) {
			_urlProviders = new NSMutableDictionary<>();
		}

		return _urlProviders;
	}

	public static SWURLProvider urlProviderForClass( Class<?> clazz ) {

		if( EOGenericRecord.class.isAssignableFrom( clazz ) ) {
			return new SWEOURLProvider();
		}

		return urlProviders().objectForKey( clazz );
	}

	/**
	 * @return A direct connect version of the URL.
	 */
	public static String makeURLDeveloperFriendly( String url, WOContext context ) {
		NSMutableDictionary<String, Object> d = new NSMutableDictionary<>();
		d.setObjectForKey( url, "url" );
		url = context.directActionURLForActionNamed( "InspectAction/handler", d );
		url = ERXStringUtilities.replaceStringByStringInString( "&", "&amp;", url );
		return url;
	}

	public static String absoluteURL( String url, WOContext context ) {
		String host = USHTTPUtilities.host( context.request() );

		if( host == null ) {
			host = SWSettings.defaultDomainName();
		}

		if( !SWSettings.generateFriendlyURLs( context.request() ) ) {
			url = makeURLDeveloperFriendly( url, context );
		}

		return "http://" + host + url;
	}
}