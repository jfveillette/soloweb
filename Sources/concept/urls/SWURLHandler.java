package concept.urls;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOContext;
import com.webobjects.foundation.NSDictionary;
import com.webobjects.foundation.NSMutableDictionary;

public abstract class SWURLHandler {

	public NSMutableDictionary<String, SWURLHandler> _urlHandlers;

	public NSDictionary<String, SWURLHandler> urlHandlers() {
		if( _urlHandlers == null ) {
			_urlHandlers = new NSMutableDictionary<>();
		}

		return _urlHandlers;
	}

	public void registerURLHandler( String expression, SWURLHandler urlHandler ) {
		urlHandlers().put( expression, urlHandler );
	}

	public SWURLHandler handlerForURL( String url ) {

		for( String regex : urlHandlers().allKeys() ) {
			if( url.matches( regex ) ) {
				return urlHandlers().objectForKey( regex );
			}
		}

		return null;
	}

	public abstract WOActionResults handleURL( String url, WOContext context );
}