package concept.data;

import is.rebbi.wo.interfaces.HasFakeRelationship;

import com.webobjects.eocontrol.EOAndQualifier;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOQualifier;
import com.webobjects.foundation.NSMutableArray;

import concept.data.auto._SWMeta;
import er.extensions.eof.ERXGenericRecord;

/**
 * Class to allow storage of meta data for any objects.
 */

public class SWMeta extends _SWMeta implements HasFakeRelationship {

	private static SWMeta meta( ERXGenericRecord object, String name ) {

		if( object == null || name == null ) {
			throw new IllegalArgumentException( "you must specify both a name and an EO when fetching meta data." );
		}

		EOEditingContext ec = object.editingContext();
		NSMutableArray<EOQualifier> a = new NSMutableArray<>();
		a.addObject( NAME.eq( name ) );
		a.addObject( TARGET_ENTITY_NAME.eq( object.entityName() ) );
		a.addObject( TARGET_ID.eq( object.primaryKey() ) );
		EOQualifier q = new EOAndQualifier( a );
		SWMeta m = fetchSWMeta( ec, q );
		return m;
	}

	public static String value( ERXGenericRecord object, String name ) {

		SWMeta m = meta( object, name );

		if( m != null ) {
			return m.text();
		}

		return null;
	}

	public static void setValue( ERXGenericRecord object, String name, String value ) {
		SWMeta m = meta( object, name );

		if( m != null ) {
			if( value != null ) {
				m.setText( value );
			}
			else {
				m.delete();
			}
		}
		else {
			if( value != null ) {
				m = HasFakeRelationship.Util.create( SWMeta.class, object );
				m.setName( name );
				m.setText( value );
			}
		}
	}
}