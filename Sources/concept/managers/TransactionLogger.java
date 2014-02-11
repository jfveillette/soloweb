package concept.managers;

import is.rebbi.wo.util.USEOUtilities;
import is.rebbi.wo.util.USUtilities;

import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOEnterpriseObject;
import com.webobjects.foundation.NSNotification;
import com.webobjects.foundation.NSNotificationCenter;
import com.webobjects.foundation.NSPropertyListSerialization;
import com.webobjects.foundation.NSSelector;
import com.webobjects.foundation.NSTimestamp;

import concept.SWSessionHelper;
import concept.data.SWTransaction;
import er.extensions.appserver.ERXSession;
import er.extensions.eof.ERXEC;
import er.extensions.eof.ERXEnterpriseObject;
import er.extensions.eof.ERXGenericRecord;
import er.extensions.foundation.ERXProperties;

/**
 * Transaction logging..
 */

public class TransactionLogger {

	/**
	 * userInfo key for ECs indicating current user.
	 */
	private static final String USER_USERINFO_KEY = "USER_FOR_TRANSACTION_MANAGEMENT";

	/**
	 * userInfo key for EditingContexts indicating that this manager should be disabled in them.
	 */
	private static final String DISABLED_MARKER = "DISABLED" + TransactionLogger.class.getSimpleName();

	/**
	 * Actions that can be performed (and logged) on EOs.
	 */
	private static final String ACTION_INSERT = "I";
	private static final String ACTION_UPDATE = "U";
	private static final String ACTION_DELETE = "D";

	/**
	 * The transaction watcher is a singleton - this is the instance.
	 */
	private static TransactionLogger _instance;

	/**
	 * Logging happens in this EC.
	 */
	private ERXEC _loggingEC = (ERXEC)ERXEC.newEditingContext();

	/**
	 * It's a singleton.
	 */
	private TransactionLogger() {
		disableInEditingContext( _loggingEC );
	}

	/**
	 * Start watching transactions.
	 */
	public static void register() {
		NSSelector<TransactionLogger> beforeSaveSelector = new NSSelector<TransactionLogger>( "beforeSaveChangesInEditingContext", new Class[] { NSNotification.class } );
		NSNotificationCenter.defaultCenter().addObserver( instance(), beforeSaveSelector, ERXEC.EditingContextWillSaveChangesNotification, null );
	}

	/**
	 * Creates our default transaction manager.
	 */
	public static TransactionLogger instance() {
		if( _instance == null ) {
			_instance = new TransactionLogger();
		}

		return _instance;
	}

	/**
	 * Marks the given Editing context to disable any logging.
	 */
	public static void disableInEditingContext( EOEditingContext ec ) {
		ec.setUserInfoForKey( true, DISABLED_MARKER );
	}

	private static boolean isDisabledGlobally() {
		return ERXProperties.booleanForKey( "concept.TransactionLogger.disable" );
	}

	/**
	 * Marks the given Editing context to disable any logging.
	 */
	private static boolean isDisabledInEditingContext( EOEditingContext ec ) {
		return ec.userInfoForKey( DISABLED_MARKER ) != null;
	}

	/**
	 * Marks the given Editing context to disable any logging.
	 */
	public static void setUserInEditingContext( ERXEnterpriseObject user, EOEditingContext ec ) {
		ec.setUserInfoForKey( user, USER_USERINFO_KEY );
	}

	/**
	 * Marks the given Editing context to disable any logging.
	 */
	public static ERXEnterpriseObject userInEditingContext( EOEditingContext ec ) {
		return (ERXEnterpriseObject)ec.userInfoForKey( USER_USERINFO_KEY );
	}

	/**
	 * This method is invoked each time changes are saved in *any* EC in the application.
	 */
	public void beforeSaveChangesInEditingContext( NSNotification notification ) {
		EOEditingContext ec = (EOEditingContext)notification.object();

		if( !isDisabledGlobally() && !isDisabledInEditingContext( ec ) ) {
			if( !USEOUtilities.isNested( ec ) ) {
				for( EOEnterpriseObject eo : ec.insertedObjects() ) {
					makeTransaction( ACTION_INSERT, (ERXGenericRecord)eo );
				}

				for( EOEnterpriseObject eo : ec.updatedObjects() ) {
					makeTransaction( ACTION_UPDATE, (ERXGenericRecord)eo );
				}

				for( EOEnterpriseObject eo : ec.deletedObjects() ) {
					makeTransaction( ACTION_DELETE, (ERXGenericRecord)eo );
				}

				_loggingEC.saveChanges();
			}
		}
	}

	private void makeTransaction( String action, ERXGenericRecord eo ) {

		// FIXME!
		if( eo.primaryKeyAttributeNames().count() == 1 ) {
			SWTransaction t = SWTransaction.createSWTransaction( _loggingEC );
			t.setDate( new NSTimestamp() );

			ERXEnterpriseObject user = userInEditingContext( eo.editingContext() );

			if( user == null ) {
				user = SWSessionHelper.userInSession( ERXSession.session() );
			}

			if( user != null ) {
				Object userPrimaryKey = user.primaryKey();

				if( userPrimaryKey == null ) {
					userPrimaryKey = Integer.valueOf( eo.permanentGlobalID().keyValues()[0].toString() );
				}

				t.setUserID( USUtilities.integerFromObject( userPrimaryKey ) );
			}

			t.setBefore( NSPropertyListSerialization.stringFromPropertyList( eo.committedSnapshot() ) );
			t.setAfter( NSPropertyListSerialization.stringFromPropertyList( eo.changesFromCommittedSnapshot() ) );
			t.setAction( action );
			t.setTargetObject( eo );
		}
	}
}