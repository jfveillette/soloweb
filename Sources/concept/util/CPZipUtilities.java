package concept.util;

import is.rebbi.core.util.StringUtilities;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableArray;

import concept.data.SWDocument;
import concept.data.SWFolder;

/**
 * This class combines functionality for extracting data from Zip-archives
 */

public class CPZipUtilities {

	private static final Logger logger = LoggerFactory.getLogger( CPZipUtilities.class );

	public static void expandZipFileAndInsertIntoFolder( EOEditingContext ec, File file, SWFolder folder ) {
		logger.info( "Starting expansion of zipFile: " + file );

		try( ZipFile zipFile = new ZipFile( file ); ) {
			Enumeration<ZipEntry> e = (Enumeration<ZipEntry>)zipFile.entries();
			Enumeration<ZipEntry> e2 = (Enumeration<ZipEntry>)zipFile.entries();

			while( e.hasMoreElements() ) {
				ZipEntry z = e.nextElement();

				if( z.isDirectory() ) {
					generateFolderStructure( ec, z.getName(), folder );
				}
			}

			while( e2.hasMoreElements() ) {
				ZipEntry z = e2.nextElement();

				if( !z.isDirectory() ) {
					insertDocument( ec, z.getName(), zipFile.getInputStream( z ), folder );
				}
			}
		}
		catch( IOException e ) {
			logger.error( "Error while expanding zip-file", e );
		}

		ec.saveChanges();
		logger.info( "Finished expansion of zipFile: " + file );
	}

	private static void generateFolderStructure( EOEditingContext ec, String path, SWFolder parent ) {
		SWFolder workingDirectory = parent;
		NSMutableArray<String> pathArray = NSArray.componentsSeparatedByString( path, "/" ).mutableClone();
		pathArray.removeObjectAtIndex( pathArray.count() - 1 );

		for( String folderName : pathArray ) {
			SWFolder currentFolder = workingDirectory.subFolderNamed( folderName );

			if( currentFolder == null ) {
				if( ignoreEntry( folderName ) ) {
					logger.info( "Ignoring folder: " + folderName );
				}
				else {
					logger.info( "Creating folder:" + path );
					workingDirectory = createFolder( ec, folderName, workingDirectory );
				}
			}
			else {
				workingDirectory = currentFolder;
			}
		}
	}

	private static void insertDocument( EOEditingContext ec, String path, InputStream stream, SWFolder parent ) {

		String filename = StringUtilities.fileNameFromPath( path );

		if( ignoreEntry( filename ) ) {
			logger.info( "Ignoring file: " + path );
		}
		else {
			logger.info( "Extracting file: " + path );
			SWFolder folder = subFolderFromPath( parent, path );
			SWDocument document = Documents.create( ec, filename, stream );
			ec.saveChanges();
			folder.addItem( document );
		}
	}

	private static boolean ignoreEntry( String name ) {

		// Name must have a value
		if( name == null || name.equals( "" ) ) {
			return true;
		}

		// Hidden file
		if( name.startsWith( "." ) ) {
			return true;
		}

		// Mac OS X icon file
		if( name.equals( "Icon\r" ) ) {
			return true;
		}

		// Mac OS X resource fork
		if( name.contains( "__MACOSX" ) ) {
			return true;
		}

		return false;
	}

	private static SWFolder subFolderFromPath( SWFolder folder, String path ) {

		if( path == null ) {
			return null;
		}

		SWFolder workingDirectory = folder;
		NSMutableArray<String> pathArray = NSArray.componentsSeparatedByString( path, "/" ).mutableClone();
		pathArray.removeObjectAtIndex( pathArray.count() - 1 );

		Enumeration<String> e = pathArray.objectEnumerator();

		while( e.hasMoreElements() ) {
			workingDirectory = workingDirectory.subFolderNamed( (e.nextElement()) );
		}

		return workingDirectory;
	}

	private static SWFolder createFolder( EOEditingContext ec, String name, SWFolder parent ) {
		SWFolder folder = SWFolder.createSWFolder( ec );
		folder.setName( name );
		folder.setInheritsPrivileges( 1 );
		folder.setParent( parent );
		ec.saveChanges();
		return folder;
	}
}