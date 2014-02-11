package concept.managers;

import is.rebbi.wo.util.SWSettings;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.webobjects.eoaccess.EOModel;
import com.webobjects.eoaccess.EOModelGroup;
import com.webobjects.foundation.NSNotification;
import com.webobjects.foundation.NSNotificationCenter;
import com.webobjects.foundation.NSSelector;

/**
 * Handles the initialization of the database connection of the system.
 */

public class DBConnectionManager {

	private static final Logger logger = LoggerFactory.getLogger( DBConnectionManager.class );

	private static final String HANDLE_MODEL_ADDED_NOTIFICATION_NAME = "handleModelAddedNotification";

	private static DBConnectionManager _singleton;

	/**
	 * It's a singleton.
	 */
	private DBConnectionManager() {}

	/**
	 * Creates our default transaction manager.
	 */
	public static DBConnectionManager singleton() {
		if( _singleton == null ) {
			_singleton = new DBConnectionManager();
		}

		return _singleton;
	}

	public static void register() {
		NSSelector<Void> modelAddedSelector = new NSSelector<>( HANDLE_MODEL_ADDED_NOTIFICATION_NAME, new Class[] { NSNotification.class } );
		NSNotificationCenter.defaultCenter().addObserver( singleton(), modelAddedSelector, EOModelGroup.ModelAddedNotification, null );
	}

	/**
	 * This method has several main responsibilities.
	 */
	public void handleModelAddedNotification( NSNotification aNotication ) {
		EOModel model = (EOModel)aNotication.object();

		if( "Concept".equals( model.name() ) ) {
			logger.info( "Reconfigured connection for EOModel:  " + model.name() );
			model.setConnectionDictionary( SWSettings.connectionDictionary() );
		}
		else {
			logger.info( "Did NOT reconfigure the connection for EOModel:  " + model.name() );
		}
	}
}