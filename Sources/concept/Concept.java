package concept;

import is.rebbi.wo.util.SWSettings;
import is.rebbi.wo.util.SoftUser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.webobjects.foundation.NSNotification;
import com.webobjects.foundation.NSNotificationCenter;
import com.webobjects.foundation.NSSelector;
import com.webobjects.foundation.NSTimestamp;

import concept.managers.CacheManager;
import concept.managers.DBConnectionManager;
import concept.managers.DBDefinitionManager;
import concept.managers.RequestManager;
import concept.managers.StatsManager;
import concept.managers.TransactionLogger;
import concept.managers.TransactionStamper;
import concept.plugin.SWPluginHandler;
import concept.search.IndexManager;
import concept.search.SWLuceneUtilities;
import concept.search.SWNewsItemLuceneUtilities;
import concept.search.SWPageLuceneUtilities;
import er.extensions.appserver.ERXApplication;
import er.extensions.eof.ERXConstant;

/**
 * God Object.
 */

public class Concept {

	private static final Logger logger = LoggerFactory.getLogger( Concept.class );

	private static Concept _sw;

	/**
	 * This is the framework's principal class.When it's loaded, it registers the framework for initialization after the application launches.
	 */
	static {
		NSSelector<Void> sel = new NSSelector<>( "init", ERXConstant.NotificationClassArray );
		NSNotificationCenter.defaultCenter().addObserver( Concept.sw(), sel, ERXApplication.ApplicationDidFinishInitializationNotification, null );
	}

	/**
	 * It's a singleton.
	 */
	private Concept() {}

	public void init( NSNotification n ) {
		init();
	}

	public static Concept sw() {
		if( _sw == null ) {
			_sw = new Concept();
		}

		return _sw;
	}

	/**
	 * Initialization of the system, invoked after application has finished loading.
	 */
	private synchronized void init() {
		logger.info( "*** Welcome to " + productNameAndVersion() );

		SWSettings.register();
		SoftUser.Manager.register();
		DBConnectionManager.register();
		TransactionStamper.register();
		CacheManager.register();
		TransactionLogger.register();
		RequestManager.register();
		DBDefinitionManager.register();
		IndexManager.register();
		StatsManager.register();

		ERXApplication app = ERXApplication.erxApplication();
		app.setDefaultRequestHandler( app.requestHandlerForKey( app.directActionRequestHandlerKey() ) );
		app.registerRequestHandler( new CPDocumentRequestHandler(), CPDocumentRequestHandler.KEY );
		app.registerRequestHandler( new SWDocumentRequestHandler(), SWDocumentRequestHandler.KEY );
		app.registerRequestHandler( new SWThumbnailRequestHandler(), SWThumbnailRequestHandler.KEY );
		app.setDefaultEncoding( "UTF-8");

		if( SWSettings.sessionTimeOut() != null ) {
			int sessionTimeOutInSeconds = SWSettings.sessionTimeOut() * 60;
			app.setSessionTimeOut( sessionTimeOutInSeconds );
		}

		SWLuceneUtilities.addExtension( new SWPageLuceneUtilities() );
		SWLuceneUtilities.addExtension( new SWNewsItemLuceneUtilities() );

		SWYouTubeUtils.init();

		SWPluginHandler.defaultInstance().loadRegisteredPlugins();
		String defaultMailServer = SWSettings.stringForKey( "defaultMailServer" );

		if( defaultMailServer != null ) {
			app.setSMTPHost( defaultMailServer );
		}

		logger.info( "*** " + productNameAndVersion() + " ready at " + new NSTimestamp() );
	}

	/**
	 * @return Name of the framework's bundle.
	 */
	public String frameworkBundleName() {
		return "SoloWeb";
	}

	/**
	 * @return Name of the product.
	 */
	public String productName() {
		return "Concept";
	}

	/**
	 * @return Version of the product.
	 */
	public String productVersion() {
		return "3.0.0";
	}

	/**
	 * @return Name of product and version, in one pretty package.
	 */
	public String productNameAndVersion() {
		return productName() + " " + productVersion();
	}
}