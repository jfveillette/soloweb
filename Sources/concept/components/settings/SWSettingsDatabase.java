package concept.components.settings;

import is.rebbi.wo.util.SWSQLUtilities;
import is.rebbi.wo.util.SWSettings;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOContext;
import com.webobjects.eoaccess.EOAdaptor;
import com.webobjects.eoaccess.EOAdaptorChannel;
import com.webobjects.eoaccess.EODatabaseChannel;
import com.webobjects.eoaccess.EODatabaseContext;
import com.webobjects.eoaccess.EOModelGroup;
import com.webobjects.eoaccess.EOSQLExpressionFactory;
import com.webobjects.eoaccess.EOUtilities;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.foundation.NSArray;

import concept.util.SWLoc;

/**
 * Settings for the DB connection.
 */

public class SWSettingsDatabase extends SWSettingsPanel {

	public String connectionsTabName = SWLoc.string( "dbsTabConnections", context() );
	public String switchTabName = SWLoc.string( "dbsTabSwitchAndConstruct", context() );
	public NSArray<String> dbTabs = new NSArray<String>( connectionsTabName, switchTabName );
	public String dbSelectedTab = connectionsTabName;

	/**
	 * A list of all registered plugins.
	 */
//	public NSMutableArray<SWModuleItem> plugins;

	/**
	* The module currently being iterated over in the list of plugins in the SQL-schema generation menu.
	 */
//	public SWModuleItem currentPlugin;

	public SWSettingsDatabase( WOContext context ) {
		super( context );
	}

	/**
	* Initializes the plugins array to a list of all plugins registered with the system and their associated EOModels.
	 */
//	public NSArray<SWModuleItem> plugins() {
//		if( plugins == null ) {
//
//			NSMutableArray<SWModuleItem> a = new NSMutableArray<SWModuleItem>();
//
//			SWModuleItem conceptPlugin = new SWModuleItem();
//			conceptPlugin.setName( Concept.sw().productName() );
//			conceptPlugin.setVersion( Concept.sw().productVersion() );
//			conceptPlugin.setModels( new NSArray<String>( "Concept" ) );
//			a.addObject( conceptPlugin );
///*
//			Enumeration<SWPlugin> e = SWPluginHandler.registeredPlugins().objectEnumerator();
//
//			while( e.hasMoreElements() ) {
//				SWPlugin aPlugin = e.nextElement();
//
//				if( aPlugin.models() != null ) {
//					SWPluginItem pi = new SWPluginItem();
//					pi.setName( aPlugin.name() );
//					pi.setModels( aPlugin.models() );
//					pi.setVersion( aPlugin.version() );
//					anArray.addObject( pi );
//				}
//			}
//*/
//			plugins = a;
//		}
//
//		return plugins;
//	}

	/**
	 * Invokes reconnect() and returns SWLoggedOut
	 */
	public WOActionResults switchConnection() {
		SWSQLUtilities.reconnectToDatabase();
		session().terminate();
		return null;
//		return pageWithName( SWLoggedOut.class );
	}

	/**
	 *  Creates the SQL string to execute, based on selections made in the SQL generation window
	 *
	public WOActionResults executeSQL() {

		SWSQLUtilities.reconnectToDatabase();

		Enumeration<SWModuleItem> b = plugins.objectEnumerator();
		SWModuleItem pi;

		while( b.hasMoreElements() ) {
			pi = b.nextElement();

			Enumeration<String> e = pi.models().objectEnumerator();

			String aModelName;
			EOModel aModel;
			String sqlString = null;

			while( e.hasMoreElements() ) {
				aModelName = e.nextElement();
				aModel = EOModelGroup.defaultGroup().modelNamed( aModelName );

				if( pi.dropSchema() && pi.constructSchema() ) {
					sqlString = SWSQLUtilities.sqlForModel( aModel, "YES" );
				}
				else if( !pi.dropSchema() && pi.constructSchema() ) {
					sqlString = SWSQLUtilities.sqlForModel( aModel, "NO" );
				}
				else if( pi.dropSchema() && !pi.constructSchema() ) {
					sqlString = SWSQLUtilities.sqlToDropTablesForModel( aModel );
				}
				else if( !pi.dropSchema() && !pi.constructSchema() ) {
					sqlString = null;
				}

				if( sqlString != null ) {
					generateSQL( ERXEC.newEditingContext(), aModelName, sqlString );
				}
			}
		}

		return switchConnection();
	}
	*/

	/**
	 * Executes the given SQL string, using the expression class from the given model and the
	 * DatabaseContext from the Model in the EC
	 *
	 * @param ec The Editing context to execute the SQL in
	 * @param modelName The model to base the database connection on
	 * @param sqlString The SQL to execute
	 */
	private void generateSQL( EOEditingContext ec, String modelName, String sqlString ) {

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
	* Indicates if a connection dictionary has been created.
	 */
	public boolean connectionDictionary() {
		return SWSettings.connectionDictionary() != null;
	}
}