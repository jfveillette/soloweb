package concept.util;

import is.rebbi.core.util.StringUtilities;
import is.rebbi.wo.interfaces.SWDataAsset;
import is.rebbi.wo.interfaces.SWFolderInterface;
import is.rebbi.wo.util.FileTypes;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.webobjects.eoaccess.EOUtilities;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableArray;

/**
 * This class combines functionality for extracting data from Zip-archives
 */

public class CPZipUtilities {

	private static final Logger logger = LoggerFactory.getLogger( CPZipUtilities.class );

	public static void expandZipFileAndInsertIntoFolder( EOEditingContext ec, File file, SWFolderInterface folder, String entityName ) {
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
					insertDocument( ec, z.getName(), zipFile.getInputStream( z ), folder, entityName );
				}
			}
		}
		catch( IOException e ) {
			logger.error( "Error while expanding zip-file", e );
		}

		ec.saveChanges();
		logger.info( "Finished expansion of zipFile: " + file );
	}

	private static void generateFolderStructure( EOEditingContext ec, String path, SWFolderInterface parent ) {
		SWFolderInterface workingDirectory = parent;
		NSMutableArray<String> pathArray = NSArray.componentsSeparatedByString( path, "/" ).mutableClone();
		pathArray.removeObjectAtIndex( pathArray.count() - 1 );

		for( String folderName : pathArray ) {
			SWFolderInterface currentFolder = workingDirectory.subFolderNamed( folderName );

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

	private static void insertDocument( EOEditingContext ec, String path, InputStream stream, SWFolderInterface parent, String entityName) {

		String filename = StringUtilities.fileNameFromPath( path );

		if( ignoreEntry( filename ) ) {
			logger.info( "Ignoring file: " + path );
		}
		else {
			logger.info( "Extracting file: " + path );
			SWFolderInterface folder = subFolderFromPath( parent, path );
			SWDataAsset document = create( ec, entityName, filename, stream );
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

	private static SWFolderInterface subFolderFromPath( SWFolderInterface folder, String path ) {

		if( path == null ) {
			return null;
		}

		SWFolderInterface workingDirectory = folder;
		NSMutableArray<String> pathArray = NSArray.componentsSeparatedByString( path, "/" ).mutableClone();
		pathArray.removeObjectAtIndex( pathArray.count() - 1 );

		Enumeration<String> e = pathArray.objectEnumerator();

		while( e.hasMoreElements() ) {
			workingDirectory = workingDirectory.subFolderNamed( (e.nextElement()) );
		}

		return workingDirectory;
	}

	private static SWFolderInterface createFolder( EOEditingContext ec, String name, SWFolderInterface parent ) {
		SWFolderInterface folder = (SWFolderInterface)EOUtilities.createAndInsertInstance( ec, parent.entityName() );
		folder.setName( name );
		folder.setInheritsPrivileges( 1 );
		folder.setParent( parent );
		ec.saveChanges();
		return folder;
	}

	public static SWDataAsset create( EOEditingContext ec, String entityName, String filename, InputStream stream ) {
		String name = FileTypes.extractFilename( filename );
		String extension = FileTypes.extensionFromFileName( filename );
		return create( ec, entityName, name, extension, stream );
	}

	public static SWDataAsset create( EOEditingContext ec, String entityName, String name, String extension, InputStream stream ) {
		SWDataAsset document = (SWDataAsset)EOUtilities.createAndInsertInstance( ec, entityName );
		document.setName( name );
		document.setExtension( extension );

		try {
			IOUtils.copy( stream, document.outputStream() );
		}
		catch( IOException e ) {
			throw new RuntimeException( "Failed to copy stream!", e );
		}

		document.updateThumbnails();
		return document;
	}
}