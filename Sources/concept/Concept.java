package concept;

import is.rebbi.wo.definitions.EntityViewDefinition;
import is.rebbi.wo.util.SWSettings;
import is.rebbi.wo.util.SoftUser;
import is.rebbi.wo.util.StatsManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.webobjects.appserver.WOApplication;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSMutableDictionary;
import com.webobjects.foundation.NSNotification;
import com.webobjects.foundation.NSNotificationCenter;
import com.webobjects.foundation.NSSelector;
import com.webobjects.foundation.NSTimestamp;

import concept.components.admin.SWAdminCustomComponent;
import concept.definitions.DatabaseDefinitions;
import concept.definitions.SystemDefinitions;
import concept.managers.CacheManager;
import concept.managers.DBConnectionManager;
import concept.managers.DBDefinitionManager;
import concept.managers.RequestManager;
import concept.managers.TransactionLogger;
import concept.managers.TransactionStamper;
import concept.plugin.SWPluginHandler;
import concept.search.IndexManager;
import concept.search.SWLuceneUtilities;
import concept.search.SWNewsItemLuceneUtilities;
import concept.search.SWPageLuceneUtilities;
import er.extensions.appserver.ERXApplication;
import er.extensions.appserver.ERXSession;
import er.extensions.eof.ERXConstant;

/**
 * God Object.
 */

public class Concept {

	private static final Logger logger = LoggerFactory.getLogger( Concept.class );

	private static Concept _sw;

	private NSMutableArray<ERXSession> _activeUserSessions = new NSMutableArray<>();
	private NSMutableDictionary<String, String> _activeSettingsTabs;
	private NSMutableDictionary<String, String> _activePageEditingComponents;
	private NSMutableDictionary<String, String> _activeSiteEditingComponents;
	private NSMutableDictionary<String, String> _activeSystems;
	private NSMutableDictionary<String, String> _activeComponents;
	private NSMutableDictionary<String, String> _activeSystemsAndComponents;

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

		if( SWSettings.home() == null ) {
			logger.error( "The property concept.home must be set" );
			System.exit(1);
		}

		if( WOApplication.application() instanceof SWApplication ) {
			_activeSystems = new NSMutableDictionary<>( SWApplication.swapplication().additionalSystems() );
			_activeComponents = new NSMutableDictionary<>( SWApplication.swapplication().additionalComponents() );
			_activePageEditingComponents = new NSMutableDictionary<>( SWApplication.swapplication().additionalPageEditingComponents() );
			_activeSiteEditingComponents = new NSMutableDictionary<>( SWApplication.swapplication().additionalSiteEditingComponents() );
			_activeSystemsAndComponents = new NSMutableDictionary<>( SWApplication.swapplication().additionalSystemsAndComponents() );
			_activeSettingsTabs = new NSMutableDictionary<>( SWApplication.swapplication().additionalSettingsTabs() );
		}
		else {
			_activeSystems = new NSMutableDictionary<>();
			_activeComponents = new NSMutableDictionary<>();
			_activePageEditingComponents = new NSMutableDictionary<>();
			_activeSiteEditingComponents = new NSMutableDictionary<>();
			_activeSystemsAndComponents = new NSMutableDictionary<>();
			_activeSettingsTabs = new NSMutableDictionary<>();
		}

		_activeComponents.setObjectForKey( SWAdminCustomComponent.class.getSimpleName(), "Custom" );

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

		EntityViewDefinition.entityViewDefinitionProviders.addObject( new SystemDefinitions() );
		EntityViewDefinition.entityViewDefinitionProviders.addObject( new DatabaseDefinitions() );

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

	public NSMutableArray<ERXSession> activeUserSessions() {
		return _activeUserSessions;
	}

	public NSMutableDictionary<String, String> activeSettingsTabs() {
		return _activeSettingsTabs;
	}

	public NSMutableDictionary<String, String> activePageEditingComponents() {
		return _activePageEditingComponents;
	}

	public NSMutableDictionary<String, String> activeSiteEditingComponents() {
		return _activeSiteEditingComponents;
	}

	public NSMutableDictionary<String, String> activeSystems() {
		return _activeSystems;
	}

	public NSMutableDictionary<String, String> activeComponents() {
		return _activeComponents;
	}

	public NSMutableDictionary<String, String> activeSystemsAndComponents() {
		return _activeSystemsAndComponents;
	}
}