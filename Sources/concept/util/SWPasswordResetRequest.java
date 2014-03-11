package concept.util;

import is.rebbi.wo.urls.SWURLProvider;
import is.rebbi.wo.util.InspectAction;
import is.rebbi.wo.util.SWSettings;
import is.rebbi.wo.util.USEOUtilities;
import is.rebbi.wo.util.USMailSender;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.webobjects.appserver.WOContext;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSTimestamp;

import concept.data.SWUser;

public class SWPasswordResetRequest {

	private static final Logger logger = LoggerFactory.getLogger( SWPasswordResetRequest.class );

	private static String TYPE = "passwordResetRequest";

	/**
	 * Create a password reset request for the given user.
	 */
	public static void submit( SWUser user, WOContext context ) {
		Data data = new Data();
		data.date = new Date();
		data.userID = user.primaryKey();
		String key = SWDatastore.store( TYPE, data );

		StringBuilder b = new StringBuilder();
		b.append( "Smelltu á hlekkinn hér að neðan til að endurstilla aðgangsorðið þitt" );
		b.append( "\n\n" );
		b.append( SWURLProvider.absoluteURL( InspectAction.PASSWORD_RESET_REQUEST + key, context ) );
		USMailSender.composeEmail( SWSettings.webmasterName(), SWSettings.webmasterEmail(), new NSArray<>( user.emailAddress() ), "Aðgangsorð", b.toString(), null );
		logger.info( "Created password reset request for user ID: " + user.primaryKey() );
	}

	/**
	 * @return A valid password reset request with the given ID, if one exists. Otherwise, null.
	 */
	public static SWUser fetch( EOEditingContext ec, String key ) {
		Data data = SWDatastore.fetch( Data.class, TYPE, key );

		if( data != null ) {
			if( data.date != null && data.userID != null ) {
				NSTimestamp timeToSurvive = new NSTimestamp().timestampByAddingGregorianUnits( 0, 0, -1, 0, 0, 0 );

				if( timeToSurvive.before( data.date ) ) {
					return USEOUtilities.objectWithPK( ec, SWUser.ENTITY_NAME, Integer.valueOf( data.userID ) );
				}
			}
		}

		return null;
	}

	public static void finish( String key ) {
		SWDatastore.delete( TYPE, key );
	}

	public static class Data {
		public String userID;
		public Date date;
	}
}