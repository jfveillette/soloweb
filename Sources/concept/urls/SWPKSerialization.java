package concept.urls;

import com.webobjects.eoaccess.EOAttribute;
import com.webobjects.eoaccess.EOEntity;
import com.webobjects.eoaccess.EOModelGroup;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOEnterpriseObject;
import com.webobjects.eocontrol.EOGlobalID;
import com.webobjects.eocontrol.EOKeyGlobalID;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableArray;

import er.extensions.eof.ERXGenericRecord;
import er.extensions.eof.ERXKeyGlobalID;

public class SWPKSerialization {

	private static final String PK_ELEMENT_SEPARATOR = "|";

	public static String serialize( EOEnterpriseObject eo ) {
		EOGlobalID gid = eo.editingContext().globalIDForObject( eo );
		return serialize( gid );
	}

	public static String serialize( EOGlobalID gid ) {

		if( !(gid instanceof EOKeyGlobalID) ) {
			throw new IllegalArgumentException( "We currently only support serialization of EOKeyGlobalIDs, but you provided a " + gid.getClass() );
		}

		ERXKeyGlobalID keyGID = ERXKeyGlobalID.globalIDForGID( (EOKeyGlobalID)gid );
		return keyGID.keyValuesArray().componentsJoinedByString( PK_ELEMENT_SEPARATOR );
	}

	public static ERXGenericRecord eo( EOEditingContext ec, String entityName, String string ) {
		EOGlobalID gid = SWPKSerialization.deSerialize( entityName, string );
		ERXGenericRecord eo = (ERXGenericRecord)ec.faultForGlobalID( gid, ec );
		return eo;
	}

	public static EOGlobalID deSerialize( String entityName, String string ) {
		NSArray<?> array = NSArray.componentsSeparatedByString( string, PK_ELEMENT_SEPARATOR );
		array = coercePKValues( entityName, array );
		System.out.println( array );
		Object[] values = array.toArray();
		ERXKeyGlobalID gid = new ERXKeyGlobalID( entityName, values );
		return gid.globalID();
	}

	public static NSArray<?> coercePKValues( String entityName, NSArray<?> values ) {

		if( entityName == null || values == null ) {
			throw new IllegalArgumentException( "You must provide both an entityName and values" );
		}

		NSMutableArray results = new NSMutableArray();

		EOEntity entity = EOModelGroup.defaultGroup().entityNamed( entityName );

		for( int i = 0; i < entity.primaryKeyAttributes().count(); i++ ) {
			EOAttribute attribute = entity.primaryKeyAttributes().objectAtIndex( i );
			Object value = values.get( i );

			if( "java.lang.Number".equals( attribute.className() ) || "i".equals( attribute.valueType() ) ) {
				value = new Integer( (String)value );
			}

			results.addObject( value );
		}

		return results;
	}
}