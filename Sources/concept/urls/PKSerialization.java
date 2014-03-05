package concept.urls;

import org.apache.commons.lang.StringUtils;

import com.webobjects.eocontrol.EOKeyGlobalID;

public class PKSerialization {

	public static String decode( String keyString ) {
		String s = keyString.toString();
		s = StringUtils.replace( s, "\'", "\"" );
		return s;
	}

	public static String encode( EOKeyGlobalID primaryKey ) {

		if( primaryKey != null ) {
			String s = primaryKey.toString();
			s = StringUtils.remove( s, "\n" );
			s = StringUtils.remove( s, "\t" );
			s = StringUtils.replace( s, "\"", "\'" );
			return s;
		}

		return null;
	}
/*
	public static String encode( ERXGenericRecord eo ) {
		ERXKeyGlobalID globalIDForGID = ERXKeyGlobalID.globalIDForGID( eo.permanentGlobalID() );
		globalIDForGID.v
		if( primaryKey != null ) {
			String s = primaryKey.toString();
			s = StringUtils.remove( s, "\n" );
			s = StringUtils.remove( s, "\t" );
			s = StringUtils.replace( s, "\"", "\'" );
			return s;
		}

		return null;
	}
*/
}