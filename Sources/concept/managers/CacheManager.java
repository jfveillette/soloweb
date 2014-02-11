package concept.managers;

import is.rebbi.wo.interfaces.RefreshCacheOnSave;

import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOEnterpriseObject;
import com.webobjects.foundation.NSNotification;
import com.webobjects.foundation.NSNotificationCenter;
import com.webobjects.foundation.NSSelector;

import er.extensions.eof.ERXEC;

/**
 * Cache refreshing.
 */

public class CacheManager {

	/**
	 * The watcher is a singleton - this is the instance.
	 */
	private static CacheManager _instance;

	/**
	 * It's a singleton.
	 */
	private CacheManager() {}

	/**
	 * Start watching.
	 */
	public static void register() {
		NSSelector<CacheManager> beforeSaveSelector = new NSSelector<>( "beforeSaveChangesInEditingContext", new Class[] { NSNotification.class } );
		NSNotificationCenter.defaultCenter().addObserver( instance(), beforeSaveSelector, ERXEC.EditingContextWillSaveChangesNotification, null );
	}

	/**
	 * Creates our default manager.
	 */
	public static CacheManager instance() {
		if( _instance == null ) {
			_instance = new CacheManager();
		}

		return _instance;
	}

	/**
	 * Invoked each time changes are saved in *any* EC in the application.
	 */
	public void beforeSaveChangesInEditingContext( NSNotification notification ) {

		EOEditingContext ec = (EOEditingContext)notification.object();

		for( EOEnterpriseObject eo : ec.insertedObjects() ) {
			if( eo instanceof RefreshCacheOnSave ) {
				((RefreshCacheOnSave)eo).refreshCache();
			}
		}

		for( EOEnterpriseObject eo : ec.updatedObjects() ) {
			if( eo instanceof RefreshCacheOnSave ) {
				((RefreshCacheOnSave)eo).refreshCache();
			}
		}
	}
}