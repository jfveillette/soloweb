package concept.util;

import is.rebbi.wo.util.USEOUtilities;

import com.webobjects.eoaccess.EOAttribute;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSKeyValueCoding;

import er.extensions.eof.ERXGenericRecord;

/**
 * Objects can implement this class to attempt to present themselves in a human readable way.
 */

public interface HumanReadable {

	/**
	 * @return A human readable representation of this object.
	 */
	public String toStringHuman();

	public static class DefaultImplementation {

		public static String toStringHuman( Object object ) {

			if( object == null ) {
				return null;
			}

			if( object instanceof HumanReadable ) {
				return ((HumanReadable)object).toStringHuman();
			}
			/*
			else if( object instanceof Indexable ) {
				return ((Indexable)object).indexRecord().name();
			}
			*/
			else if( object instanceof ERXGenericRecord ) {
				return eoToStringHuman( (ERXGenericRecord)object );
			}
			else {
				return object.toString();
			}
		}

		/**
		 * @return An attempt to generate a human readable string from an EO.
		 */
		private static String eoToStringHuman( ERXGenericRecord eo ) {
			StringBuilder b = new StringBuilder();

			b.append( "[EO:" );
			b.append( eo.entityName() );
			b.append( "] " );

			NSArray<EOAttribute> attributes = USEOUtilities.attributes( eo.entity() );
			int i = attributes.count();

			for( EOAttribute attribute : attributes ) {
				String key = attribute.name();
				Object value = eo.valueForKey( attribute.name() );
				value = toStringHuman( value );

				if( value instanceof NSKeyValueCoding.Null ) {
					value = null;
				}

				b.append( key + ": " + value );

				if( i > 1 ) {
					b.append( ", " );
				}

				i--;
			}

			return b.toString();
		}
	}
}