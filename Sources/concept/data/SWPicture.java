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
import is.rebbi.wo.util.USArrayUtilities;
import is.rebbi.wo.util.USDataUtilities;
import is.rebbi.wo.util.USEOUtilities;
import is.rebbi.wo.util.USHTTPUtilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOEnterpriseObject;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSData;
import com.webobjects.foundation.NSMutableArray;

import concept.SWPictureRequestHandler;
import concept.data.auto._SWPicture;
import concept.util.SWPictureUtilities;
import concept.util.SWStringUtilities;
import concept.util.SWZipUtilities;
import er.extensions.appserver.ERXApplication;
import er.extensions.appserver.ERXWOContext;

public class SWPicture extends _SWPicture implements SWDataAsset<SWPicture, SWAssetFolder>, SWHasCustomInfo {

	private static final Logger logger = LoggerFactory.getLogger( SWPicture.class );

	private SWCustomInfo _customInfo;

	public SWPicture() {
		if( SWSettings.imagePath() == null ) {
			throw new RuntimeException( "You must specify image location on disk in the SoloWeb settings" );
		}
	}

	public SWCustomInfo customInfo() {
		if( _customInfo == null ) {
			_customInfo = new SWCustomInfo( this );
		}

		return _customInfo;
	}

	public String nameForDownload() {
		String name = name();

		if( name == null ) {
			name = "Untitled";
		}

		return USHTTPUtilities.makeFilenameURLFriendly( name(), extension() );
	}

	public String nameForDownloadURLEncoded() {
		try {
			return URLEncoder.encode( nameForDownload(), "UTF-8" );
		}
		catch( UnsupportedEncodingException e ) {
			throw new RuntimeException( "Could not URL-encode document name: " + nameForDownload(), e );
		}
	}

	@Override
	public void setName( String value ) {
		String filenameOnly = FileTypes.filenameByRemovingExtension( value );
		filenameOnly = SWStringUtilities.legalName( filenameOnly );
		String extensionOnly = FileTypes.extensionFromFilename( value );

		StringBuilder b = new StringBuilder();
		b.append( filenameOnly );

		if( extensionOnly != null ) {
			b.append( "." );
			b.append( extensionOnly );
		}

		String newFileName = filenameOnly + "." + extensionOnly;

		if( newFileName != null && !newFileName.equals( value ) ) {
			if( file().exists() ) {
				File newFile = new File( pictureRootFolder() + newFileName );
				file().renameTo( newFile );
			}
		}

		setExtension( extensionOnly );

		if( !Objects.equals( newFileName, name() ) ) {
			for( String name : file().getParentFile().list() ) {
				if( !name.startsWith( "." ) ) { // skip invisible files
					String oldNameWithoutExtension = FileTypes.filenameByRemovingExtension( name() );

					if( oldNameWithoutExtension == null ) {
						oldNameWithoutExtension = "";
					}

					String newName = name.replace( oldNameWithoutExtension, FileTypes.filenameByRemovingExtension( newFileName ) );

					String oldExtension = FileTypes.extensionFromFilename( name );
					String newExtension = FileTypes.extensionFromFilename( newFileName );

					if( !Objects.equals( oldExtension, newExtension ) ) {
						newName = FileTypes.filenameByRemovingExtension( newName ) + newExtension;
					}

					File src = new File( pictureRootFolder() + name );
					File dst = new File( pictureRootFolder() + newName );

					if( src.exists() ) {
						src.renameTo( dst );
					}
				}
			}

			File originalFile = file();
			super.setName( newFileName );
			File newFile = file();
			originalFile.renameTo( newFile );
		}
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
		setName( value );
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

	private String pictureRootFolder() {
		return SWSettings.imagePath() + "/" + pk() + "/";
	}

	public String path() {
		return pathForSize( null );
	}

	private File file() {
		File file = new File( path() );
		file.getParentFile().mkdir();
		return file;
	}

	private String pathForSize( String size ) {

		if( size == null || "original".equals( size ) || "0".equals( size ) || "".equals( size ) ) {
			return pictureRootFolder() + name();
		}

		String extension = FileTypes.extensionFromFilename( name() );

		if( extension == null ) {
			extension = extension();
		}

		return pictureRootFolder() + FileTypes.filenameByRemovingExtension( name() ) + "_" + size + "." + extension;
	}

	private File fileForSize( String size ) {
		return new File( pathForSize( size ) );
	}

	/**
	 * The image's data, as stored on disk
	 */
	@Override
	public NSData data() {
		return USDataUtilities.readDataFromFile( file() );
	}

	/**
	 * Sets the image's data
	 */
	@Override
	public void setData( NSData newData ) {
		USDataUtilities.writeDataToFile( newData, file() );
		updateThumbnails();
	}

	/**
	 * The image's data, as stored on disk for a certain size
	 */
	public NSData dataForSize( String size ) {
		return USDataUtilities.readDataFromFile( fileForSize( size ) );
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
		String imageURL = SWSettings.imageURL();

		if( imageURL == null ) {
			return ERXWOContext.currentContext().urlWithRequestHandlerKey( SWPictureRequestHandler.KEY, pk() + "/" + nameForDownloadURLEncoded(), null );
		}

		return imageURL + "/" + pk() + "/" + name();
	}

	private Object pk() {
		Object[] keyValues = permanentGlobalID().keyValues();
		return keyValues[0];
	}

	public String previewURL( String size ) {
		String url = "";

		if( size == null || "original".equals( size ) || "0".equals( size ) ) {
			url = pictureURL();
		}
		else if( fileForSize( size ).exists() ) {
			String extension = FileTypes.extensionFromFilename( name() );

			if( extension == null ) {
				extension = extension();
			}

			url = SWSettings.imageURL() + "/" + pk() + "/" + FileTypes.filenameByRemovingExtension( name() ) + "_" + size + "." + extension;
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
		String sizeString = (String)customInfo().valueForKey( "sizes" );

		if( sizeString == null ) {
			return NSArray.<String>emptyArray();
		}

		return NSArray.componentsSeparatedByString( sizeString, "," );
	}

	/**
	 * @return Available preview sizes as NSArray
	 */
	public void setPreviewSizesList( NSArray<String> sizeArray ) {
		String sizeString = null;

		if( sizeArray != null ) {
			sizeString = sizeArray.componentsJoinedByString( "," );
		}

		customInfo().takeValueForKey( sizeString, "sizes" );
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
		return "cid:swpicture_" + pk();
	}

	/**
	 * An ImageInfo object for retrieving information about the picture.
	 */
	public ImageInfo imageInfo( String size ) {

		try {
			File file = fileForSize( size );

			if( file.exists() && file.length() > 0 ) {
				ImageInfo ii = new ImageInfo();
				ii.setInput( new FileInputStream( file ) );

				if( !ii.check() ) {
					return null;
				}

				return ii;
			}
		}
		catch( Exception e ) {
			logger.error( "Failed to create ImageInfo instance for image: " + pk(), e );
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
				File file = new File( SWSettings.imagePath() + "/" + pk() + "/" + list[i] );

				if( file.exists() ) {
					logger.info( "Deleting file: " + file );
					file.delete();
				}
			}

			if( directory.exists() ) {
				logger.info( "Deleting directory: " + directory );
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

		for( SWPicture newPicture : containingFolder().sortedDocuments() ) {
			if( !"zip".equals( FileTypes.extensionFromFilename( newPicture.name() ) ) ) {
				newPicture.setDisplayName( newPicture.name() );
				newPicture.setPreviewSizesList( previewSizesList() );
				newPicture.updateThumbnails();
			}
		}

		editingContext().saveChanges();
	}

	@Override
	public boolean hasData() {
		return file().exists() && file().length() > 0;
	}

	private boolean isLandscape() {
		return width() > height();
	}

	@Override
	public void updateThumbnails() {
		logger.debug( "Creating images for pictureId: " + pk() );
		NSArray<String> sizes = previewSizesList();

		if( !USArrayUtilities.hasObjects( sizes ) ) {
			sizes = SWSettings.previewSizes();
		}

		NSMutableArray<String> madeSizes = new NSMutableArray<>();

		for( String size : sizes ) {
			logger.debug( "Processing image size: " + size );
			int max = Integer.parseInt( size );
			int w = width();
			int h = height();

			boolean shouldProcess = isLandscape() ? max < width() : max < height();// only make previews for sizes less than original
			logger.debug( "Width: " + w + ", Height: " + h + ", doProcess: " + shouldProcess );

			if( shouldProcess && file().exists() ) {
				try {
					String path = path();
					String previewPath = pathForSize( size );
					logger.debug( "Creating image with max size: " + max + ", path: " + path + ", previewPath: " + previewPath );

					if( !ERXApplication.erxApplication().isDevelopmentMode() ) {
						SWPictureUtilities.createThumbnail( path, previewPath, max, max, 50 );
					}

					madeSizes.addObject( size );
				}
				catch( Exception ex ) {
					logger.error( "Failed to create preview for: " + pathForSize( size ), ex );
				}
			}
		}

		logger.debug( "Setting sizes for pictureId: " + madeSizes );
		setPreviewSizesList( madeSizes );
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

	public InputStream inputStream() {
		try {
			File f = file();

			if( !f.exists() ) {
				f.createNewFile();
			}

			return new FileInputStream( f );
		}
		catch( IOException e ) {
			throw new RuntimeException( e );
		}
	}

	/**
	 * @return The mime type of this file.
	 */
	public String mimeType() {
		return FileTypes.mimeTypeForExtension( extension() );
	}
}