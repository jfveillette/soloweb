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

import concept.SWPictureRequestHandler;
import concept.data.auto._SWPicture;
import concept.util.SWPictureUtilities;
import concept.util.SWStringUtilities;
import er.extensions.appserver.ERXWOContext;

public class SWPicture extends _SWPicture implements SWDataAsset<SWPicture, SWAssetFolder>, SWHasCustomInfo {

	private static final Logger logger = LoggerFactory.getLogger( SWPicture.class );

	private static final NSArray<String> IMAGE_EXTENSIONS = NSArray.componentsSeparatedByString( "jpg,jpeg,png,gif,bmp,tif,tiff", "," );

	private static final String LOCATION_ON_SERVER = SWSettings.imageURL() + "/";
	private static final String LOCATION_ON_DISK = SWSettings.imagePath() + "/";
	private NSData temporaryData = null;

	private SWCustomInfo _customInfo;

	public SWPicture() {

		if( LOCATION_ON_DISK == null ) {
			throw new RuntimeException( "You must specify image location on disk in the SoloWeb settings" );
		}

		NSSelector saveSelector = new NSSelector( "writeDataToDisk", new Class[] { NSNotification.class } );
		NSNotificationCenter.defaultCenter().addObserver( this, saveSelector, EOEditingContext.EditingContextDidSaveChangesNotification, null );
	}

	/**
	 * Set the picture's name
	 */
	@Override
	public void setName( String value ) {
		if( !value.equals( name() ) ) { // file has (possibly) been renamed - try renaming preview files
			String[] list = file().getParentFile().list();

			for( int i = 0; list != null && i < list.length; i++ ) {
				String name = list[i];
				if( !name.startsWith( "." ) ) { // skip system files

					// rename name part
					String newName = name.replace( nameOnly( name() ), nameOnly( value ) );

					// rename extension - if present
					String oldExt = extension( name, true );
					String newExt = extension( value, true );

					if( !oldExt.equals( newExt ) ) { // extensions are not the same - set the one that's being assigned (value)
						newName = nameOnly( newName ) + newExt;
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
	private String nameOnly( String value ) {
		String e = extension( value, true );
		String n = value;

		if( value != null && value.indexOf( e ) > -1 ) {
			n = value.substring( 0, value.length() - e.length() );
		}

		return n;
	}

	/**
	 * @return The file's extension.
	 */
	private String extension( String value, boolean withDot ) {
		NSArray<String> parts = NSArray.componentsSeparatedByString( value, "." );
		String ext = parts.lastObject();

		if( !IMAGE_EXTENSIONS.containsObject( ext ) ) {
			if( documentType() != null ) {
				ext = documentType().extension();
			}
			else {
				ext = "";
			}
		}
		if( withDot && StringUtilities.hasValue( ext ) ) {
			ext = "." + ext;
		}

		return ext;
	}

	@Override
	public String displayName() {
		String nafn = super.displayName();

		if( !StringUtilities.hasValue( nafn ) ) {
			nafn = name();
		}

		return nafn;
	}

	@Override
	public void setDisplayName( String value ) {
		super.setDisplayName( value );

		String newName = SWStringUtilities.legalName( nameOnly( value ) ) + extension( value, true );

		if( newName != null && !newName.equals( value ) ) { // legal name is different - rename original file
			if( file().exists() ) {
				File newFile = new File( folderOnDisk() + newName );
				file().renameTo( newFile );
			}
		}

		setName( newName );
	}

	public SWCustomInfo customInfo() {
		if( _customInfo == null ) {
			_customInfo = new SWCustomInfo( this );
		}

		return _customInfo;
	}

	public String altTextOrName() {

		if( StringUtilities.hasValue( altText() ) ) {
			return altText();
		}

		return name();
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

	public String path() {
		return folderOnDisk() + name();
	}

	/**
	 * @return path on disk to preview size or original
	 */
	private String path( String size ) {

		if( size == null || "original".equals( size ) || "0".equals( size ) || "".equals( size ) ) {
			return path();
		}

		return folderOnDisk() + nameOnly( name() ) + "_" + size + extension( name(), true );
	}

	private String folderOnDisk() {
		return LOCATION_ON_DISK + id() + "/";
	}

	public File file() {
		File file = new File( path() );
		file.getParentFile().mkdir();
		return file;
	}

	public File file( String size ) {
		return new File( path( size ) );
	}

	/**
	 * The image's data, as stored on disk
	 */
	@Override
	public NSData data() {
		if( id() == null ) {
			return temporaryData;
		}
		else {
			return USDataUtilities.readDataFromFile( file() );
		}
	}

	/**
	 * Sets the image's data
	 */
	@Override
	public void setData( NSData newData ) {

		if( id() == null ) {
			temporaryData = newData;
		}
		else {
			USDataUtilities.writeDataToFile( newData, file() );
			updatePreviews();
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
		if( temporaryData != null && id() != null ) {
			USDataUtilities.writeDataToFile( temporaryData, file() );
			updatePreviews();
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
		return LOCATION_ON_SERVER + id() + "/" + name();
	}

	public String previewURL( String size ) {
		String url = "";

		if( size == null || "original".equals( size ) || "0".equals( size ) ) {
			url = pictureURL();
		}
		else if( file( size ).exists() ) {
			if( LOCATION_ON_SERVER != null ) {
				url = LOCATION_ON_SERVER + id() + "/" + nameOnly( name() ) + "_" + size + extension( name(), true );
			}
			else {
				url = ERXWOContext.currentContext().urlWithRequestHandlerKey( SWPictureRequestHandler.KEY, primaryKey() + "/" + "smu", null );
			}
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
		return "cid:swpicture_" + id();
	}

	public String emailEmbedURLWithoutCID() {
		return "swpicture_" + id();
	}

	/**
	 * An ImageInfo object for retrieving information about the picture.
	 */
	private ImageInfo imageInfo() {

		try {
			ImageInfo ii = new ImageInfo();
			ii.setInput( new FileInputStream( file() ) );

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

	/**
	 * The picture's width
	 */
	public int width() {
		try {
			return imageInfo().getWidth();
		}
		catch( Exception e ) {
			return 0;
		}
	}

	/**
	 * The picture's height
	 */
	public int height() {
		try {
			return imageInfo().getHeight();
		}
		catch( Exception e ) {
			return 0;
		}
	}

	public int heightForPictureSize( String size ) {
		int height = 0;

		try {
			ImageInfo ii = new ImageInfo();
			ii.setInput( new FileInputStream( file( size ) ) );

			if( ii.check() ) {
				height = ii.getHeight();
			}
		}
		catch( Exception ex ) {
			logger.error( "Unable to get picture height: ", ex );
		}

		return height;
	}

	public int widthForPictureSize( String size ) {
		int width = 0;

		try {
			ImageInfo ii = new ImageInfo();
			ii.setInput( new FileInputStream( file( size ) ) );

			if( ii.check() ) {
				width = ii.getWidth();
			}
		}
		catch( Exception ex ) {
			logger.error( "Unable to get picture height: ", ex );
		}

		return width;
	}

	public static SWPicture pictureWithID( EOEditingContext anEC, Integer anID ) {
		return (SWPicture)USEOUtilities.objectWithPK( anEC, SWPicture.ENTITY_NAME, anID );
	}

	public void updatePreviews() {
		createPreviews();
	}

	@Override
	public void deleteAsset() {
		String[] list = file().getParentFile().list();

		if( list != null ) {
			for( int i = 0; i < list.length; i++ ) {
				File file = new File( LOCATION_ON_DISK + id() + "/" + list[i] );

				try {
					file.delete();
				}
				catch( Exception e ) {
					logger.debug( "Error deleting asset", e );
				}
			}

			// delete the directory ?
			try {
				file().getParentFile().delete();
			}
			catch( Exception e ) {
				logger.debug( "Error deleting parent file", e );
			}

			// delete picture links
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
		// FIXME: implement
		/*
		SWZipUtilities.expandZipFileAndInsertIntoFolder( editingContext(), file(), containingFolder(), SWPicture.ENTITY_NAME, SWAssetFolder.ENTITY_NAME );
		String sizes = (String)customInfo().valueForKey( "sizes" );

		for( Enumeration<SWPicture> e = containingFolder().sortedDocuments().objectEnumerator(); e.hasMoreElements(); ) {
			SWPicture pic = e.nextElement();
			if( !"zip".equals( pic.extension( pic.name(), false ) ) ) {
				pic.setDisplayName( pic.name() );
				pic.customInfo().takeValueForKey( sizes, "sizes" );
				pic.updatePreviews();
			}
		}

		editingContext().saveChanges();
		*/
	}

	public boolean hasData() {
		return file().exists() || temporaryData != null;
	}

	public boolean isLandscape() {
		return width() > height();
	}

	public void createPreviews() {
		logger.debug( "Creating images for pictureId: " + id() );

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
}