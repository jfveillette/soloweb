package concept.util;

import is.rebbi.core.util.StringUtilities;
import is.rebbi.wo.util.SWSettings;
import is.rebbi.wo.util.USJson;

import java.io.File;
import java.util.UUID;

/**
 * Allows storage of arbitrary JSON data as files in the data directory.
 */

public class SWDatastore {

	private static File folder( String type ) {
		String path = SWSettings.dataPath();
		path = path + "/" + type;
		return new File( path );
	}

	private static File file( String type, String key ) {

		if( type == null || key == null ) {
			throw new RuntimeException( "You must specify a type and a key when using the datastore" );
		}

		File folder = folder( type );

		if( !folder.exists() ) {
			folder.mkdirs();
		}

		if( !folder.exists() ) {
			throw new RuntimeException( "Data directory does not exist" );
		}

		return new File( folder, key + ".json" );
	}

	public static boolean exists( String type, String key ) {
		return file( type, key ).exists();
	}

	public static void store( String type, String key, Object value ) {
		String json = USJson.toJson( value );
		StringUtilities.writeStringToFileUsingEncoding( json, file( type, key ), "UTF-8" );
	}

	public static String store( String type, Object value ) {
		String key = UUID.randomUUID().toString();
		store( type, key, value );
		return key;
	}

	public static <E> E fetch( Class<E> clazz, String type, String key ) {
		String json = StringUtilities.readStringFromFileUsingEncoding( file( type, key ), "UTF-8" );
		return USJson.fromJson( json, clazz );
	}

	public static boolean delete( String type, String key ) {
		return file( type, key ).delete();
	}
}