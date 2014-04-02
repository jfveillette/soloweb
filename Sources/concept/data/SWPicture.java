package concept.data;

import is.rebbi.core.util.StringUtilities;
import is.rebbi.wo.formatters.FileSizeFormatter;
import is.rebbi.wo.interfaces.SWDataAsset;
import is.rebbi.wo.interfaces.SWHasCustomInfo;
import is.rebbi.wo.util.FileType;
import is.rebbi.wo.util.FileTypes;
import is.rebbi.wo.util.ImageInfo;
import is.rebbi.wo.util.SWCustomInfo;
import is.rebbi.wo.util.SWSettings;
import is.rebbi.wo.util.USDataUtilities;
import is.rebbi.wo.util.USEOUtilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Enumeration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOEnterpriseObject;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSData;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSMutableDictionary;
import com.webobjects.foundation.NSNotification;
import com.webobjects.foundation.NSNotificationCenter;
import com.webobjects.foundation.NSSelector;

import concept.data.auto._SWPicture;
import concept.util.SWPictureUtilities;
import concept.util.SWStringUtilities;
import concept.util.SWZipUtilities;

public class SWPicture extends _SWPicture implements SWDataAsset<SWPicture, SWAssetFolder>, SWHasCustomInfo {

	private static final Logger logger = LoggerFactory.getLogger( SWPicture.class );

	private NSData temporaryData = null;

	private SWCustomInfo _customInfo;

	public SWPicture() {

		if( SWSettings.imagePath() == null ) {
			throw new RuntimeException( "You must specify image location on disk in the SoloWeb settings" );
		}

		NSSelector<Void> saveSelector = new NSSelector<>( "writeDataToDisk", new Class[] { NSNotification.class } );
		NSNotificationCenter.defaultCenter().addObserver( this, saveSelector, EOEditingContext.EditingContextDidSaveChangesNotification, null );
	}

	public SWCustomInfo customInfo() {
		if( _customInfo == null ) {
			_customInfo = new SWCustomInfo( this );
		}

		return _customInfo;
	}

	@Override
	public void setName( String value ) {
		if( !value.equals( name() ) ) {
			for( String name : file().getParentFile().list() ) {
				if( !name.startsWith( "." ) ) { // skip system files

					// rename name part
					String newName = name.replace( nameWithoutExtension( name() ), nameWithoutExtension( value ) );

					// rename extension - if present
					String oldExtension = "." + extension( name );
					String newExtension = "." + extension( value );

					if( !oldExtension.equals( newExtension ) ) { // extensions are not the same - set the one that's being assigned (value)
						newName = nameWithoutExtension( newName ) + newExtension;
					}

					File src = new File( folderOnDisk() + name );
					File dst = new File( folderOnDisk() + newName );

					if( src.exists() ) {
						src.renameTo( dst );
					}
				}
			}

			File originalFile = file();
			super.setName( value );
			File newFile = file();
			originalFile.renameTo( newFile );
		}
	}

	/**
	 * @return Name without extension
	 */
	private static String nameWithoutExtension( String value ) {
		return FileTypes.filenameByRemovingExtension( value );
	}

	/**
	 * @return The file's extension.
	 */
	private static String extension( String value ) {
		return FileTypes.extensionFromFilename( value );
	}

	@Override
	public String displayName() {
		String displayName = super.displayName();

		if( !StringUtilities.hasValue( displayName ) ) {
			displayName = name();
		}

		return displayName;
	}

	@Override
	public void setDisplayName( String value ) {
		super.setDisplayName( value );

		String newName = SWStringUtilities.legalName( nameWithoutExtension( value ) ) + "." + extension( value );

		if( newName != null && !newName.equals( value ) ) {
			if( file().exists() ) {
				File newFile = new File( folderOnDisk() + newName );
				file().renameTo( newFile );
			}
		}

		setName( newName );
	}

	public String altTextOrName() {
		return StringUtilities.hasValue( altText() ) ? altText() : name();
	}

	@Override
	public FileType documentType() {
		return FileTypes.documentTypeWithExtension( extension() );
	}

	@Override
	public void setDocumentType( FileType value ) {
		if( value != null ) {
			setExtension( value.extension() );
		}
		else {
			setExtension( null );
		}
	}

	private String folderOnDisk() {
		return SWSettings.imagePath() + "/" + primaryKey() + "/";
	}

	public String path() {
		return folderOnDisk() + name();
	}

	private String path( String size ) {

		if( size == null || "original".equals( size ) || "0".equals( size ) || "".equals( size ) ) {
			return path();
		}

		return folderOnDisk() + nameWithoutExtension( name() ) + "_" + size + "." + extension( name() );
	}

	private File file() {
		File file = new File( path() );
		file.getParentFile().mkdir();
		return file;
	}

	private File file( String size ) {
		return new File( path( size ) );
	}

	/**
	 * The image's data, as stored on disk
	 */
	@Override
	public NSData data() {
		if( primaryKey() == null ) {
			return temporaryData;
		}

		return USDataUtilities.readDataFromFile( file() );
	}

	/**
	 * Sets the image's data
	 */
	@Override
	public void setData( NSData newData ) {

		if( primaryKey() == null ) {
			temporaryData = newData;
		}
		else {
			USDataUtilities.writeDataToFile( newData, file() );
			updateThumbnails();
		}
	}

	/**
	 * The image's data, as stored on disk for a certain size
	 */
	public NSData dataForSize( String size ) {
		return USDataUtilities.readDataFromFile( file( size ) );
	}

	/**
	 * Writes out this picture's temporary data buffer to disk. Invoked automatically by the system.
	 */
	public void writeDataToDisk( NSNotification n ) {
		if( temporaryData != null && primaryKey() != null ) {
			USDataUtilities.writeDataToFile( temporaryData, file() );
			updateThumbnails();
			temporaryData = null;
		}
	}

	@Override
	public long size() {
		File f = file();

		if( f != null && f.exists() ) {
			return f.length();
		}

		return 0;
	}

	/**
	 * @return The asset's size formatted for display.
	 */
	public String sizeFormatted() {
		return new FileSizeFormatter().format( size() );
	}

	/**
	 * Implementation of SWTransferable
	 */
	@Override
	public void transferOwnership( EOEnterpriseObject newOwner ) {
		removeObjectFromBothSidesOfRelationshipWithKey( folder(), FOLDER_KEY );
		addObjectToBothSidesOfRelationshipWithKey( newOwner, FOLDER_KEY );
	}

	public String pictureURL() {
		return SWSettings.imageURL() + "/" + primaryKey() + "/" + name();
	}

	public String previewURL( String size ) {
		String url = "";

		if( size == null || "original".equals( size ) || "0".equals( size ) ) {
			url = pictureURL();
		}
		else if( file( size ).exists() ) {
			url = SWSettings.imageURL() + "/" + primaryKey() + "/" + nameWithoutExtension( name() ) + "_" + size + "." + extension( name() );
		}
		else {
			url = pictureURL();
		}

		return url;
	}

	public String thumbnailURL() {
		String url = pictureURL();

		// if original is smaller than 120 then only the original exists
		if( previewSizesList().containsObject( "120" ) ) {
			url = previewURL( "120" );
		}

		return url;
	}

	/**
	 * @return Available preview sizes as NSArray
	 */
	public NSArray<String> previewSizesList() {
		return NSArray.componentsSeparatedByString( (String)customInfo().valueForKey( "sizes" ), "," );
	}

	/**
	 * @return Available picture sizes with 'original' as first item
	 */
	public NSArray<String> availablePictureSizes() {
		NSMutableArray<String> list = new NSMutableArray<>();
		list.addObject( "original" );
		list.addObjectsFromArray( previewSizesList() );
		return list;
	}

	public String emailEmbedURL() {
		return "cid:swpicture_" + primaryKey();
	}

	/**
	 * An ImageInfo object for retrieving information about the picture.
	 */
	public ImageInfo imageInfo( String size ) {

		try {
			ImageInfo ii = new ImageInfo();
			ii.setInput( new FileInputStream( file( size ) ) );

			if( !ii.check() ) {
				return null;
			}

			return ii;
		}
		catch( Exception e ) {
			logger.error( "Failed to create ImageInfo instance for image: " + primaryKey(), e );
		}

		return null;
	}

	public int width() {
		ImageInfo ii = imageInfo( null );
		return (ii != null) ? ii.getWidth() : 0;
	}

	public int height() {
		ImageInfo ii = imageInfo( null );
		return (ii != null) ? ii.getHeight() : 0;
	}

	public int widthForPictureSize( String size ) {
		ImageInfo ii = imageInfo( size );
		return (ii != null) ? ii.getWidth() : 0;
	}

	public int heightForPictureSize( String size ) {
		ImageInfo ii = imageInfo( size );
		return (ii != null) ? ii.getHeight() : 0;
	}

	public static SWPicture pictureWithID( EOEditingContext ec, Integer id ) {
		return (SWPicture)USEOUtilities.objectWithPK( ec, SWPicture.ENTITY_NAME, id );
	}

	@Override
	public void deleteAsset() {
		File directory = file().getParentFile();
		String[] list = directory.list();

		if( list != null ) {
			for( int i = 0; i < list.length; i++ ) {
				File file = new File( SWSettings.imagePath() + "/" + primaryKey() + "/" + list[i] );

				if( file.exists() ) {
					file.delete();
				}
			}

			if( directory.exists() ) {
				directory.delete();
			}

			Enumeration<SWPictureLink> e = swPictureLinks().objectEnumerator();

			while( e.hasMoreElements() ) {
				SWPictureLink link = e.nextElement();
				link.removeObjectFromBothSidesOfRelationshipWithKey( this, SWPictureLink.PICTURE_KEY );
				editingContext().deleteObject( link );
			}

			removeObjectFromBothSidesOfRelationshipWithKey( folder(), FOLDER_KEY );
			editingContext().deleteObject( this );
		}
	}

	@Override
	public SWAssetFolder containingFolder() {
		return folder();
	}

	@Override
	public void setContainingFolder( SWAssetFolder newFolder ) {
		if( folder() != null ) {
			removeObjectFromBothSidesOfRelationshipWithKey( folder(), FOLDER_KEY );
		}

		addObjectToBothSidesOfRelationshipWithKey( newFolder, FOLDER_KEY );
	}

	@Override
	public void expandZip() {
		SWZipUtilities.expandZipFileAndInsertIntoFolder( editingContext(), file(), folder(), entityName() );

		String sizes = (String)customInfo().valueForKey( "sizes" );

		for( SWPicture pic : containingFolder().sortedDocuments() ) {
			if( !"zip".equals( extension( pic.name() ) ) ) {
				pic.setDisplayName( pic.name() );
				pic.customInfo().takeValueForKey( sizes, "sizes" );
				pic.updateThumbnails();
			}
		}

		editingContext().saveChanges();
	}

	public boolean hasData() {
		return file().exists() || temporaryData != null;
	}

	private boolean isLandscape() {
		return width() > height();
	}

	@Override
	public void updateThumbnails() {
		logger.debug( "Creating images for pictureId: " + primaryKey() );

		String str = (String)customInfo().valueForKey( "sizes" ); // these are the sizes requested

		if( str == null || "".equals( str ) ) {
			str = SWSettings.stringForKey( "pictureSizes" );
		}

		String madeSizes = ""; // sizes that are created
		String komma = "";
		NSArray<String> sizes = NSArray.componentsSeparatedByString( str, "," );
		int w, h, max;
		String path, previewPath;

		for( int i = 0; i < sizes.count(); i++ ) {
			String size = sizes.objectAtIndex( i );
			logger.debug( "Processing image size: " + size );
			max = new Integer( size ).intValue();
			w = width();
			h = height();

			boolean doProcess = isLandscape() ? max < width() : max < height();// only make previews for sizes less than original
			logger.debug( "Width: " + w + ", Height: " + h + ", doProcess: " + doProcess );

			if( doProcess && file().exists() ) {
				try {
					// Create preview using ImageMagick's convert command line tool.
					path = path();
					previewPath = path( size );
					logger.debug( "Creating image with max size: " + max + ", path: " + path + ", previewPath: " + previewPath );
					SWPictureUtilities.createThumbnail( path, previewPath, max, max, 50 );
					madeSizes += komma + size;
					komma = ",";
				}
				catch( Exception ex ) {
					logger.error( "Failed to create preview for: " + path( size ), ex );
				}
			}
		}

		logger.debug( "Setting sizes for pictureId: " + madeSizes );
		customInfo().takeValueForKey( madeSizes, "sizes" ); // store actual sizes made
	}

	public void updateDimensions() {
		NSArray<String> sizes = availablePictureSizes();
		NSMutableDictionary<String, String> dimensions = new NSMutableDictionary<>();
		String dim, size;

		ImageInfo ii = new ImageInfo();

		for( int sizeNo = 0; sizeNo < sizes.size(); sizeNo++ ) {
			size = sizes.objectAtIndex( sizeNo );
			try {
				ii.setInput( new FileInputStream( file( size ) ) );
			}
			catch( Exception ex ) {}
			ii.check();
			dim = ii.getWidth() + "," + ii.getHeight();
			dimensions.takeValueForKey( dim, size );
		}

		customInfo().takeValueForKey( dimensions, "dimensions" );
	}

	public boolean hasSize( String size ) {
		return previewSizesList().containsObject( size );
	}

	public NSArray<SWComponent> components() {
		NSMutableArray<SWComponent> components = new NSMutableArray<>();

		for( SWPictureLink link : swPictureLinks() ) {
			components.addObject( link.component() );
		}

		return components;
	}

	@Override
	public OutputStream outputStream() {
		try {
			return new FileOutputStream( file() );
		}
		catch( FileNotFoundException e ) {
			throw new RuntimeException( "Failed to construct a FileOutputStream for file: " + file() );
		}
	}
}