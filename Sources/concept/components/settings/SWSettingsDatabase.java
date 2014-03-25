package concept.components.settings;

import is.rebbi.wo.util.SWSQLUtilities;
import is.rebbi.wo.util.SWSettings;

import java.util.Enumeration;

import com.webobjects.appserver.WOComponent;
import com.webobjects.appserver.WOContext;
import com.webobjects.eoaccess.EOAdaptor;
import com.webobjects.eoaccess.EOAdaptorChannel;
import com.webobjects.eoaccess.EOAdaptorContext;
import com.webobjects.eoaccess.EODatabaseChannel;
import com.webobjects.eoaccess.EODatabaseContext;
import com.webobjects.eoaccess.EOModel;
import com.webobjects.eoaccess.EOModelGroup;
import com.webobjects.eoaccess.EOSQLExpressionFactory;
import com.webobjects.eoaccess.EOUtilities;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOObjectStoreCoordinator;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSMutableDictionary;

import concept.SWApplication;
import concept.plugin.SWPlugin;
import concept.plugin.SWPluginHandler;
import concept.util.SWLoc;
import er.extensions.eof.ERXEC;

public class SWSettingsDatabase extends SWManageSettings {

	public String connectionsTabName = SWLoc.string( "dbsTabConnections", session() );
	public String switchTabName = SWLoc.string( "dbsTabSwitchAndConstruct", session() );
	public NSArray<String> tabs = new NSArray<>( connectionsTabName, switchTabName );
	public String selectedTab = connectionsTabName;

	/**
	* A list of all plugins registered with the SoloWeb system
	 */
	public NSMutableArray<SWPlugin> plugins;

	/**
	* The plugin currently being iterated over in the list of plugins in the SQL-schema generation menu.
	 */
	public SWPluginItem currentPlugin;

	/**
	* The database name currently being iterated over in the list of databases ("Frontbase" or "Oracle")
	 */
	public String currentDatabase;

	/**
	* The selected plugins to construct DB schema for
	 */
	public NSMutableArray pluginsToConstruct = new NSMutableArray();

	/**
	    * The selected plugins to drop DB schema for
	 */
	public NSMutableArray pluginsToDrop = new NSMutableArray();

	public SWSettingsDatabase( WOContext context ) {
		super( context );
	}

	/**
	* Initializes the plugins array to a list of all plugins registered with the SoloWeb system and their EOModels.
	 */
	public NSArray plugins() {
		if( plugins == null ) {

			NSMutableArray anArray = new NSMutableArray();

			SWPluginItem pi2 = new SWPluginItem();
			pi2.name = "SoloWeb";
			SWApplication.swapplication();
			pi2.version = "Version";
			pi2.models = new NSArray<>( "SoloWeb" );
			anArray.addObject( pi2 );

			SWPluginHandler.defaultInstance();
			Enumeration<SWPlugin> e = SWPluginHandler.registeredPlugins().objectEnumerator();

			while( e.hasMoreElements() ) {
				SWPlugin aPlugin = e.nextElement();

				if( aPlugin.models() != null ) {
					SWPluginItem pi = new SWPluginItem();
					pi.name = aPlugin.name();
					pi.models = aPlugin.models();
					pi.version = aPlugin.version();
					anArray.addObject( pi );
				}
			}
			plugins = anArray;
		}

		return plugins;
	}

	/**
	* A list of supported databases. Legacy from WO 4.5
	 */
	public NSArray databases() {
		return new NSArray( new String[] { "MySql", "FrontBase", "JDBC", "MSSQL2000", "Oracle" } );
	}

	/**
	* Constructs a connection dictionary for the selected database type and inserts it into the settings
	 */
	public WOComponent switchAdaptors() {
		NSMutableDictionary dict = new NSMutableDictionary();

		if( selectedDictionary().valueForKey( "adaptorName" ).equals( "FrontBase" ) ) {
			dict.setObjectForKey( "", "URL" );
		}
		else {
			dict.setObjectForKey( "", "userName" );
			dict.setObjectForKey( "", "password" );
			dict.setObjectForKey( "", "serverId" );
		}

		SWSettings.setObjectForKey( dict, "connDict" );

		return null;
	}

	/**
	* Invokes reconnect() and returns SWLoggedOut
	 */
	public WOComponent switchConnection() {
		reconnect();
		session().terminate();

		return pageWithName( "SWLoggedOut" );
	}

	/**
	* Terminates all outstanding database connections, and connects all models in the
	 * default EOModelGroup to the specified database.
	 */
	public void reconnect() {

		EOObjectStoreCoordinator osc = new EOObjectStoreCoordinator();
		EOObjectStoreCoordinator.setDefaultCoordinator( osc );
		EOEditingContext anEC = ERXEC.newEditingContext( osc );

		Enumeration e = EOModelGroup.defaultGroup().models().objectEnumerator();

		while( e.hasMoreElements() ) {

			EOModel aModel = (EOModel)e.nextElement();

			EODatabaseContext dc = EODatabaseContext.registeredDatabaseContextForModel( aModel, anEC );
			EOAdaptor ad = dc.adaptorContext().adaptor();

			Enumeration a = ad.contexts().objectEnumerator();

			while( a.hasMoreElements() ) {
				Enumeration b = ((EOAdaptorContext)a.nextElement()).channels().objectEnumerator();

				while( b.hasMoreElements() ) {
					((EOAdaptorChannel)b.nextElement()).closeChannel();
				}
			}

			NSMutableDictionary connDict = new NSMutableDictionary( ad.connectionDictionary() );
			connDict.addEntriesFromDictionary( SWSettings.connectionDictionary() );

			ad.setConnectionDictionary( connDict );
			ad.assertConnectionDictionaryIsValid();
		}
	}

	/**
	* Creates the SQL string to execute, based on selections made in the SQL generation window
	 */
	public WOComponent executeSQL() {

		reconnect();

		Enumeration b = plugins.objectEnumerator();
		SWPluginItem pi;

		while( b.hasMoreElements() ) {
			pi = (SWPluginItem)b.nextElement();

			Enumeration e = pi.models.objectEnumerator();

			String aModelName;
			EOModel aModel;
			NSArray anArray;
			String sqlString = null;

			while( e.hasMoreElements() ) {
				aModelName = (String)e.nextElement();
				aModel = EOModelGroup.defaultGroup().modelNamed( aModelName );

				if( pi.dropSchema && pi.constructSchema ) {
					sqlString = SWSQLUtilities.sqlForModel( aModel, "YES" );
				}
				else if( !pi.dropSchema && pi.constructSchema ) {
					sqlString = SWSQLUtilities.sqlForModel( aModel, "NO" );
				}
				else if( pi.dropSchema && !pi.constructSchema ) {
					sqlString = SWSQLUtilities.sqlToDropTablesForModel( aModel );
				}
				else if( !pi.dropSchema && !pi.constructSchema ) {
					sqlString = null;
				}

				if( sqlString != null ) {
					executeSQL2( session().defaultEditingContext(), aModelName, sqlString );
				}
			}
		}

		return switchConnection();
	}

	/**
	 * Executes the given SQL string, using the expression class from the given model and the
	 * DatabaseContext from the Model in the EC
	 *
	 * @param ec The Editing context to execute the SQL in
	 * @param modelName The model to base the database connection on
	 * @param sqlString The SQL to execute
	 */
	public void executeSQL2( EOEditingContext ec, String modelName, String sqlString ) {

		EODatabaseContext databaseContext;
		EODatabaseChannel databaseChannel;
		EOAdaptorChannel adaptorChannel;

		databaseContext = EOUtilities.databaseContextForModelNamed( ec, modelName );
		databaseChannel = databaseContext.availableChannel();
		adaptorChannel = databaseChannel.adaptorChannel();

		if( !adaptorChannel.isOpen() ) {
			adaptorChannel.openChannel();
		}
		EOSQLExpressionFactory e = new EOSQLExpressionFactory( EOAdaptor.adaptorWithModel( EOModelGroup.defaultGroup().modelNamed( modelName ) ) );
		adaptorChannel.evaluateExpression( e.expressionForString( sqlString ) );
		adaptorChannel.closeChannel();
	}

	/**
	* The database connection dictionary specified in the settings file
	 */
	public boolean connectionDictionary() {
		if( SWSettings.connectionDictionary() != null ) {
			return true;
		}

		return false;
	}
}