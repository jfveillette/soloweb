package concept.managers;

import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOEnterpriseObject;
import com.webobjects.foundation.NSNotification;
import com.webobjects.foundation.NSNotificationCenter;
import com.webobjects.foundation.NSSelector;

import concept.data.SWAttributeMeta;
import concept.data.SWEntityMeta;
import concept.definitions.EntityViewDefinition;
import er.extensions.eof.ERXEC;

public class DBDefinitionManager {

	private static DBDefinitionManager _instance;

	/**
	 * Creates our default transaction manager.
	 */
	public static DBDefinitionManager instance() {
		if( _instance == null ) {
			_instance = new DBDefinitionManager();
		}

		return _instance;
	}

	private boolean isDocumentation( EOEnterpriseObject eo ) {
		return eo.entityName().equals( SWEntityMeta.ENTITY_NAME ) || eo.entityName().equals( SWAttributeMeta.ENTITY_NAME );
	}

	public void beforeSaveChangesInEditingContext( NSNotification notification ) {
		boolean hasUpdated = false;

		EOEditingContext ec = (EOEditingContext)notification.object();

		for( EOEnterpriseObject eo : ec.insertedObjects() ) {
			if( isDocumentation( eo ) && !hasUpdated ) {
				EntityViewDefinition.invalidateCache();
				hasUpdated = true;
			}
		}

		for( EOEnterpriseObject eo : ec.updatedObjects() ) {
			if( isDocumentation( eo ) && !hasUpdated ) {
				EntityViewDefinition.invalidateCache();
				hasUpdated = true;
			}
		}

		for( EOEnterpriseObject eo : ec.deletedObjects() ) {
			if( isDocumentation( eo ) && !hasUpdated ) {
				EntityViewDefinition.invalidateCache();
				hasUpdated = true;
			}
		}
	}

	/**
	 * Registers the transaction manager so it starts listening and watching transactions.
	 */
	public static void register() {
		NSSelector<Void> beforeSaveSelector = new NSSelector<>( "beforeSaveChangesInEditingContext", new Class[] { NSNotification.class } );
		NSNotificationCenter.defaultCenter().addObserver( instance(), beforeSaveSelector, ERXEC.EditingContextWillSaveChangesNotification, null );
	}
}