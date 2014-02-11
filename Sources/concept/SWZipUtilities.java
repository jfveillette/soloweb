package concept;

import is.rebbi.core.util.StringUtilities;
import is.rebbi.wo.interfaces.SWDataAsset;
import is.rebbi.wo.interfaces.SWFolderInterface;
import is.rebbi.wo.util.FileTypes;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.webobjects.eoaccess.EOUtilities;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSData;
import com.webobjects.foundation.NSMutableArray;

/**
 * This class combines functionality for extracting data from Zip-archives
 */

public class SWZipUtilities {

	private static final Logger logger = LoggerFactory.getLogger( SWZipUtilities.class );

	public static void expandZipFileAndInsertIntoFolder( EOEditingContext ec, File file, SWFolderInterface folder, String entityName, String folderEntityName ) {
		logger.info( "Starting expansion of zipFile: " + file );

		try( ZipFile zipFile = new ZipFile( file ); ) {
			Enumeration<ZipEntry> e = (Enumeration<ZipEntry>)zipFile.entries();
			Enumeration<ZipEntry> e2 = (Enumeration<ZipEntry>)zipFile.entries();

			while( e.hasMoreElements() ) {
				ZipEntry z = e.nextElement();

				if( z.isDirectory() ) {
					generateFolderStructure( ec, z.getName(), folder, folderEntityName );
				}
			}

			while( e2.hasMoreElements() ) {
				ZipEntry z = e2.nextElement();

				if( !z.isDirectory() ) {
					insertDocument( ec, z.getName(), new NSData( zipFile.getInputStream( z ), 4096 ), folder, entityName );
				}
			}
		}
		catch( IOException e ) {
			logger.error( "Error while expanding zip-file", e );
		}

		ec.saveChanges();
		logger.info( "Finished expansion of zipFile: " + file );
	}

	private static void generateFolderStructure( EOEditingContext ec, String path, SWFolderInterface parent, String folderEntityName ) {
		SWFolderInterface workingDirectory = parent;
		NSMutableArray<String> pathArray = NSArray.componentsSeparatedByString( path, "/" ).mutableClone();
		pathArray.removeObjectAtIndex( pathArray.count() - 1 );

		for( String folderName : pathArray ) {
			SWFolderInterface currentFolder = (SWFolderInterface)workingDirectory.subFolderNamed( folderName );

			if( currentFolder == null ) {
				if( ignoreEntry( folderName ) ) {
					logger.info( "Ignoring folder: " + folderName );
				}
				else {
					logger.info( "Creating folder:" + path );
					workingDirectory = newFolderWithNameAndParentFolder( ec, folderName, workingDirectory, folderEntityName );
				}
			}
			else {
				workingDirectory = currentFolder;
			}
		}
	}

	private static void insertDocument( EOEditingContext ec, String path, NSData data, SWFolderInterface parentFolder, String entityName ) {

		String newName = StringUtilities.fileNameFromPath( path );

		if( !newName.startsWith( "." ) ) {
			SWFolderInterface directory = subFolderFromPath( parentFolder, path );

			SWDataAsset newAsset = (SWDataAsset)EOUtilities.createAndInsertInstance( ec, entityName );

			newAsset.setName( newName );
			newAsset.setData( data );
			newAsset.setDocumentType( FileTypes.documentTypeFromPath( path ) );

			newAsset.setContainingFolder( directory );
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

	private static SWFolderInterface subFolderFromPath( SWFolderInterface aFolder, String pathString ) {

		if( pathString == null ) {
			return null;
		}

		SWFolderInterface workingDirectory = aFolder;
		NSMutableArray pathArray = NSArray.componentsSeparatedByString( pathString, "/" ).mutableClone();
		pathArray.removeObjectAtIndex( pathArray.count() - 1 );

		Enumeration e = pathArray.objectEnumerator();

		while( e.hasMoreElements() ) {
			workingDirectory = (SWFolderInterface)workingDirectory.subFolderNamed( ((String)e.nextElement()) );
		}

		return workingDirectory;
	}

	private static SWFolderInterface newFolderWithNameAndParentFolder( EOEditingContext ec, String name, SWFolderInterface parentFolder, String folderEntityName ) {
		SWFolderInterface newFolder = (SWFolderInterface)EOUtilities.createAndInsertInstance( ec, folderEntityName );

		newFolder.setName( name );
		newFolder.setInheritsPrivileges( 1 );

		if( parentFolder != null ) {
			newFolder.addObjectToBothSidesOfRelationshipWithKey( parentFolder, "parentFolder" );
		}

		return newFolder;
	}
}