package concept.data;

import is.rebbi.wo.interfaces.HumanReadable;
import is.rebbi.wo.interfaces.TimeStamped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.webobjects.eocontrol.EOEditingContext;

import er.extensions.eof.ERXEC;

/**
 * Event log for the system.
 */

public class SWSystemEvent extends concept.data.auto._SWSystemEvent implements HumanReadable, TimeStamped {

	private static final Logger logger = LoggerFactory.getLogger( SWSystemEvent.class );

	public static void logEvent( String type, String text ) {
		EOEditingContext ec = ERXEC.newEditingContext();
//		TransactionLogger.disableInEditingContext( ec );

		try {
			SWSystemEvent event = SWSystemEvent.createSWSystemEvent( ec );
			event.setType( type );
			event.setText( text );
//			event.setUser( SWSessionHelper.currentUser() );
			ec.saveChanges();
		}
		catch( Exception e ) {
			logger.error( "An error occurred while attempting to log an event: {} - {}", e );
		}
		finally {
			ec.unlock();
		}
	}

	@Override
	public String toStringHuman() {
		return type() + ": " + text();
	}
}