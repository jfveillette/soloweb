package concept.urls;

import is.rebbi.wo.util.SWSettings;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.webobjects.appserver.WOContext;
import com.webobjects.eoaccess.EOModelGroup;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOGlobalID;

import concept.definitions.EntityViewDefinition;
import er.extensions.eof.ERXGenericRecord;

public class SWEOURLProvider extends SWURLProvider {

	private static final Logger logger = LoggerFactory.getLogger( SWEOURLProvider.class );

	/**
	 * If an object does not implement UrlFriendlyNaming, the URL will contain this prefix and the Object's id.
	 */
	private static final String PK_IDENTIFIER_PREFIX = "id-";

	/**
	 * If an object does not implement UrlFriendlyNaming, the URL will contain this prefix and the Object's id.
	 */
	private static final String ENTITY_IDENTIFIER_PREFIX = "entity-";

	@Override
	public String urlForObject( Object object, WOContext context ) {
		ERXGenericRecord eo = (ERXGenericRecord)object;
		String serializedID = SWPKSerialization.serialize( eo );
		return urlForObjectInContext( eo.entityName(), serializedID, context );
	}

	/**
	 * @return A URL for the given object.
	 */
	public static String urlForObjectInContext( String entityName, Object serializedID, WOContext context ) {
		String typeIdentifier = typeIdentifier( entityName );
		String objectIdentifier = objectIdentifier( serializedID );

		if( context == null ) {
			return urlWithDomain( typeIdentifier, objectIdentifier );
		}
		else {
			String url = urlWithoutDomain( typeIdentifier, objectIdentifier );

			if( !SWSettings.generateFriendlyURLs( context.request() ) ) {
				url = makeURLDeveloperFriendly( url, context );
			}

			return url;
		}
	}

	/**
	 * @return A friendly URL without the domain.
	 */
	private static String urlWithoutDomain( String typeIdentifier, String objectIdentifier ) {
		return friendlyURL( null, null, typeIdentifier, objectIdentifier );
	}

	/**
	 * @return A friendly URL including the domain.
	 */
	private static String urlWithDomain( String typeIdentifier, String objectIdentifier ) {
		return friendlyURL( "http", SWSettings.defaultDomainName(), typeIdentifier, objectIdentifier );
	}

	/**
	 * @return A friendly URL.
	 */
	private static String friendlyURL( String protocol, String host, String typeIdentifier, String objectIdentifier ) {
		StringBuilder b = new StringBuilder();

		if( protocol != null ) {
			b.append( protocol );
			b.append( "://" );
		}

		if( host != null ) {
			b.append( host );
		}

		b.append( "/i/" );
		b.append( typeIdentifier );
		b.append( "/" );
		b.append( objectIdentifier );

		return b.toString();
	}

	/**
	 * @return true if the given identifier is based on an object's primary key, rather than system generated.
	 */
	private static boolean objectIdentiferIsGeneric( String objectIdentifier ) {
		return objectIdentifier.startsWith( PK_IDENTIFIER_PREFIX );
	}

	/**
	 * @return true if the given identifier is based on an object's primary key, rather than system generated.
	 */
	private static boolean typeIdentifierIsGeneric( String typeIdentifier ) {
		return typeIdentifier.startsWith( ENTITY_IDENTIFIER_PREFIX );
	}

	/**
	 * @return The object the user wanted from the URL.
	 */
	public static ERXGenericRecord objectFromURL( EOEditingContext ec, String url ) {
		String[] smu = url.split( "/" );
		String urlPrefix = smu[2];
		String objectName = smu[3];
		return objectFromIdentifiers( ec, urlPrefix, objectName );
	}

	/**
	 * @return The object specified by the parameters.
	 */
	private static ERXGenericRecord objectFromIdentifiers( EOEditingContext ec, String typeIdentifier, String objectIdentifier ) {

		ERXGenericRecord object = null;
		String entityName = entityNameFromTypeIdentifier( typeIdentifier );

		if( objectIdentiferIsGeneric( objectIdentifier ) ) {
			String identifier = objectIdentifier.substring( PK_IDENTIFIER_PREFIX.length(), objectIdentifier.length() );

			EOGlobalID gid = SWPKSerialization.deSerialize( entityName, identifier );
			object = (ERXGenericRecord)ec.faultForGlobalID( gid, ec );

			/*
			if( ERXStringUtilities.isDigitsOnly( identifier ) ) {
				object = USEOUtilities.objectWithPK( ec, entityName, new Integer( identifier ) );
			}
			else {
				EOGlobalID gid = SWPKSerialization.deSerialize( entityName, identifier );
				object = (ERXGenericRecord)ec.faultForGlobalID( gid, ec );
			}
			*/
		}
		else {
			throw new RuntimeException( "Unsupported URL format" );
		}

		return object;
	}

	private static String entityNameFromTypeIdentifier( String typeIdentifier ) {
		Class clazz = classForTypeIdentifier( typeIdentifier );

		if( clazz == null ) {
			logger.error( "No class found to handle the URL-prefix: " + typeIdentifier );
		}

		return entityNameFromClass( clazz );
	}

	private static String entityNameFromClass( Class clazz ) {
		return clazz.getSimpleName();
	}

	/**
	 * @return URL identifier for objects based on  primary key.
	 */
	private static String objectIdentifier( Object primaryKey ) {
		return PK_IDENTIFIER_PREFIX + primaryKey;
	}

	/**
	 * @return The url prefix for the given object.
	 */
	private static String typeIdentifier( String entityName ) {
		EntityViewDefinition type = EntityViewDefinition.get( entityName );

		if( type != null ) {
			String urlPrefix = type.urlPrefix();

			if( urlPrefix != null ) {
				return urlPrefix;
			}
		}

		return ENTITY_IDENTIFIER_PREFIX + entityName;
	}

	/**
	 * @return The class for the given URL prefix.
	 */
	private static Class<? extends ERXGenericRecord> classForTypeIdentifier( String urlPrefix ) {
		Class clazz = EntityViewDefinition.classForURLPrefix( urlPrefix );

		if( clazz == null && typeIdentifierIsGeneric( urlPrefix ) ) {
			String entityName = urlPrefix.substring( ENTITY_IDENTIFIER_PREFIX.length() );

			try {
				String className = EOModelGroup.defaultGroup().entityNamed( entityName ).className();
				clazz = Class.forName( className );
			}
			catch( Exception e ) {
				throw new RuntimeException( "Can't find class for entity named:" + entityName, e );
			}
		}

		return clazz;
	}
}