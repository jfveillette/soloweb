package concept.data;

import is.rebbi.wo.interfaces.HasFakeRelationship;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.webobjects.eocontrol.EOEditingContext;

import concept.util.HumanReadable;

import er.extensions.eof.ERXGenericRecord;
import er.extensions.qualifiers.ERXAndQualifier;

/**
 * A favorite object in a user's collection
 */

public class SWFavorite extends concept.data.auto._SWFavorite implements HasFakeRelationship {

	private static Logger logger = LoggerFactory.getLogger( SWFavorite.class );

	/**
	 * The object this favorite refers to.
	 */
	private ERXGenericRecord _targetObject;

	/**
	 * @return The object this favorite refers to.
	 */
	public ERXGenericRecord targetObject() {
		if( _targetObject == null ) {
			_targetObject = HasFakeRelationship.Util.targetObject( this );
		}

		return _targetObject;
	}

	/**
	 * Tag the given object.
	 */
	public static SWFavorite createFavorite( ERXGenericRecord object, SWUser user ) {
		SWFavorite favorite = HasFakeRelationship.Util.create( SWFavorite.class, object );
		favorite.setUser( user );
		return favorite;
	}

	public static void deleteFavorite( EOEditingContext ec, ERXGenericRecord object, SWUser user ) {
		SWFavorite favorite = fetchFavorite( ec, object, user );
		favorite.delete();
	}

	public static SWFavorite fetchFavorite( EOEditingContext ec, ERXGenericRecord object, SWUser user ) {
		try {
			ERXAndQualifier q = USER.eq( user ).and( TARGET_ENTITY_NAME.eq( object.entityName() ).and( TARGET_ID.eq( object.primaryKey() ) ) );
			return fetchSWFavorite( ec, q );
		}
		catch( Exception e ) {
			logger.error( "An error occurred while fetching a favorite", e );
			return null;
		}
	}

	public static boolean isFavorite( SWUser user,  ERXGenericRecord object ) {

		if( object == null ) {
			return false;
		}

		return fetchFavorite( user.editingContext(), object, user ) != null;
	}

	public String name() {
		ERXGenericRecord o = targetObject();

		if( o != null ) {
			return HumanReadable.DefaultImplementation.toStringHuman( o );
		}

		return "Þessi hlutur er ekki lengur til í gagnagrunninum: " + targetEntityName() + ":" + targetID();
	}

//	public EntityViewDefinition type() {
//		return EntityViewDefinition.get( targetEntityName() );
//	}
}