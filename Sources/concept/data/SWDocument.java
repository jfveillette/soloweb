package concept.data;

import is.rebbi.core.util.DataUtilities;
import is.rebbi.core.util.ImageUtilities;
import is.rebbi.core.util.StringUtilities;
import is.rebbi.wo.formatters.FileSizeFormatter;
import is.rebbi.wo.interfaces.SWDataAsset;
import is.rebbi.wo.interfaces.TimeStamped;
import is.rebbi.wo.interfaces.UUIDStamped;
import is.rebbi.wo.util.FileType;
import is.rebbi.wo.util.FileTypes;
import is.rebbi.wo.util.SWSettings;
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.webobjects.appserver.WOContext;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOEnterpriseObject;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSData;

import concept.Concept;
import concept.SWDocumentRequestHandler;
import concept.SWThumbnailRequestHandler;
import concept.data.auto._SWDocument;
import concept.documents.FileStorage;
import concept.documents.Storage;
import concept.util.SWZipUtilities;
import concept.util.HumanReadable;
import er.extensions.appserver.ERXApplication;
import er.extensions.appserver.ERXWOContext;

public class SWDocument extends _SWDocument implements SWDataAsset<SWDocument, SWDocumentFolder>, HumanReadable, TimeStamped, UUIDStamped {

	private static final Logger logger = LoggerFactory.getLogger( SWDocument.class );

	private static Storage _storage;

	@Override
	public void awakeFromInsertion( EOEditingContext ec ) {
		super.awakeFromInsertion( ec );
		setUuid( java.util.UUID.randomUUID().toString() );
	}

	private static <E extends Storage> Class<E> storageClass() {
		Class<E> storageClass;
		String storageClassName = SWSettings.storageClassName();

		if( storageClassName != null ) {
			try {
				storageClass = (Class<E>)Class.forName( storageClassName );
			}
			catch( Exception e ) {
				throw new IllegalArgumentException( "Failed to load storage class", e );
			}
		}
		else {
			storageClass = (Class<E>)FileStorage.class;
		}

		return storageClass;
	}

	private Storage storage() {
		if( _storage == null ) {
			try {
				_storage = storageClass().newInstance();
			}
			catch( Exception e ) {
				throw new RuntimeException( "Failed to instantiate storage class", e );
			}
		}

		return _storage;
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

	/**
	 * @return An InputStream that can be used to write to this document.
	 */
	public InputStream inputStream() {
		return storage().in( this );
	}

	/**
	 * @return An OutputStream that can be used to write to this document.
	 */
	@Override
	public OutputStream outputStream() {
		return storage().out( this );
	}

	/**
	 * @return The document's data
	 */
	@Override
	public NSData data() {
		return USDataUtilities.consumeStream( inputStream() );
	}

	/**
	 * Sets the document's data
	 */
	@Override
	public void setData( NSData newData ) {
		storage().writeData( this, newData );
		updateThumbnails();
	}

	/**
	 * @return Size in bytes.
	 */
	@Override
	public long size() {
		return storage().sizeOfData( this );
	}

	/**
	 * @return Size formatted for display.
	 */
	public String sizeFormatted() {
		return new FileSizeFormatter().format( size() );
	}

	/**
	 * @return The document matching the specified ID
	 */
	public static SWDocument documentWithID( EOEditingContext ec, Integer id ) {
		return USEOUtilities.objectWithPK( ec, SWDocument.ENTITY_NAME, id );
	}

	@Override
	public void deleteAsset() {
		removeObjectFromBothSidesOfRelationshipWithKey( folder(), FOLDER_KEY );
		editingContext().deleteObject( this );
	}

	@Override
	public void transferOwnership( EOEnterpriseObject newOwner ) {
		removeObjectFromBothSidesOfRelationshipWithKey( folder(), FOLDER_KEY );
		addObjectToBothSidesOfRelationshipWithKey( newOwner, FOLDER_KEY );
	}

	@Override
	public SWDocumentFolder containingFolder() {
		return folder();
	}

	@Override
	public void setContainingFolder( SWDocumentFolder newFolder ) {
		if( this.folder() != null ) {
			removeObjectFromBothSidesOfRelationshipWithKey( this.folder(), FOLDER_KEY );
		}

		addObjectToBothSidesOfRelationshipWithKey( newFolder, FOLDER_KEY );
	}

	/**
	 * @return A universally acceptable (downloadable) name for the document.
	 */
	public String nameForDownload() {
		String name = name();

		if( name == null ) {
			name = "Untitled";
		}

		return USHTTPUtilities.makeFilenameURLFriendly( name(), extension() );
	}

	/**
	 * @return A universally acceptable (downloadable) name for the document.
	 */
	public String nameForDownloadURLEncoded() {
		String nameForDownload = nameForDownload();

		try {
			nameForDownload = java.net.URLEncoder.encode( nameForDownload, "UTF-8" );
		}
		catch( Exception e ) {
			logger.error( "Could not URL-encode document name: " + nameForDownload, e );
		}

		return nameForDownload;
	}

	/**
	 * @return The stored filename of this document, otherwise the randomly generated UUID.
	 */
	public String filename() {
		if( storedFilename() != null ) {
			return storedFilename();
		}
		else {
			return uuid();
		}
	}

	/**
	 * Deletes this document from the DB and it's data from the disk
	 */
	@Override
	public void didDelete( EOEditingContext ec ) {
		storage().deleteData( this );
	}

	/**
	 * @return true if this document has any data.
	 */
	public boolean hasData() {
		return storage().hasData( this );
	}

	@Override
	public void expandZip() {
		try {
			File tempFile = File.createTempFile( "zip-" + java.util.UUID.randomUUID().toString(), "zip" );
			org.apache.commons.io.IOUtils.copy( inputStream(), new FileOutputStream( tempFile ) );
			SWZipUtilities.expandZipFileAndInsertIntoFolder( editingContext(), tempFile, containingFolder(), SWDocument.ENTITY_NAME );
			tempFile.delete();
		}
		catch( IOException e ) {
			throw new RuntimeException( "An error occurred while extracting a zip file", e );
		}
	}

	@Override
	public String extension() {
		String ext = super.extension();

		if( !StringUtilities.hasValue( ext ) ) {
			if( StringUtilities.hasValue( name() ) ) {
				NSArray<String> parts = NSArray.componentsSeparatedByString( name(), "." );

				if( parts.count() > 1 ) {
					ext = parts.lastObject();
				}

				if( ext != null && (ext.length() > 5 || ext.indexOf( " " ) > -1) ) {
					ext = null;
				}
			}
		}

		return ext;
	}

	/**
	 * @return The name of the icon file for this document.
	 */
	public String iconURL() {
		String iconURL = null;
		String filename = null;

		if( extension() != null ) {
			filename = "ext/" + extension() + ".png";
		}
		else {
			filename = "ext/html.png";
		}

		WOContext context = ERXWOContext.currentContext();

		iconURL = ERXApplication.erxApplication().resourceManager().urlForResourceNamed( filename, Concept.sw().frameworkBundleName(), NSArray.<String> emptyArray(), context.request() );

		if( iconURL.contains( "NOT_FOUND" ) ) {
			filename = "ext/html.png";
			iconURL = ERXApplication.erxApplication().resourceManager().urlForResourceNamed( filename, Concept.sw().frameworkBundleName(), NSArray.<String> emptyArray(), context.request() );
		}

		return iconURL;
	}

	/**
	 * @return The mime type of this file.
	 */
	public String mimeType() {
		return FileTypes.mimeTypeForExtension( extension() );
	}

	/**
	 * @return Public URL of this document.
	 */
	public String url() {
		return ERXWOContext.currentContext().urlWithRequestHandlerKey( SWDocumentRequestHandler.KEY, primaryKey() + "/" + nameForDownloadURLEncoded(), null );
	}

	@Override
	public String toStringHuman() {
		return name();
	}

	/* ----------------------------------- */

	private static int[] DEFAULT_THUMBNAIL_SIZES = { 32, 64, 128, 256 };

	public String urlThumbnail32() {
		return thumbnailURL( 32 );
	}

	public String urlThumbnail64() {
		return thumbnailURL( 64 );
	}

	public String urlThumbnail128() {
		return thumbnailURL( 128 );
	}

	public String urlThumbnail256() {
		return thumbnailURL( 256 );
	}

	public String thumbnailPath( int pixels ) {
		return SWSettings.thumbnailPath() + "/" + thumbnailName( pixels );
	}

	public String thumbnailURL( int pixels ) {
		return ERXWOContext.currentContext().urlWithRequestHandlerKey( SWThumbnailRequestHandler.KEY, primaryKey() + "/" + pixels + "/" + nameForDownloadURLEncoded(), null );
	}

	public String thumbnailName( int pixels ) {
		return "thumb-" + filename() + "-" + pixels + "px.jpg";
	}

	private NSData generateThumbnailData( int pixels ) {
		NSData data = data();

		if( data == null ) {
			return null;
		}

		return new NSData( ImageUtilities.createThumbnail( data._bytesNoCopy(), pixels, pixels, 80, ImageUtilities.CodecType.JPEG ) );
	}

	public InputStream thumbnailInputStream( int pixels ) {
		File file = thumbnailFile( pixels );

		if( file != null && file.exists() ) {
			try {
				return new FileInputStream( file );
			}
			catch( FileNotFoundException e ) {
				logger.error( "File not found for thumbnail", e );
			}
		}

		return null;
	}

	public OutputStream thumbnailOutputStream( int pixels ) {
		File file = thumbnailFile( pixels );

		if( file != null && file.exists() ) {
			try {
				return new FileOutputStream( file );
			}
			catch( FileNotFoundException e ) {
				logger.error( "File not found for thumbnail", e );
			}
		}

		return null;
	}

	public long thumbnailFileSize( int pixels ) {
		File f = thumbnailFile( pixels );

		if( f != null && f.exists() ) {
			return f.length();
		}

		return 0;
	}

	private File thumbnailFile( int pixels ) {
		return new File( thumbnailPath( pixels ) );
	}

	public void updateThumbnail( int pixels ) {
		NSData data = generateThumbnailData( pixels );

		if( data != null && data.length() > 0 ) {
			DataUtilities.writeBytesToFile( data.bytes(), thumbnailFile( pixels ) );
		}
	}

	@Override
	public void updateThumbnails() {
		if( isImage() ) {
			logger.info( "Updating thumbnail" );
			try {
				for( int size : DEFAULT_THUMBNAIL_SIZES ) {
					updateThumbnail( size );
				}
			}
			catch( Exception e ) {
				e.printStackTrace();
			}
		}
	}

	public boolean isImage() {
		return mimeType() != null && mimeType().startsWith( "image" );
	}

	public boolean isVideo() {
		return mimeType() != null && mimeType().startsWith( "video" );
	}

	public boolean isAudio() {
		return mimeType() != null && mimeType().startsWith( "audio" );
	}

	public boolean isOther() {
		return !isImage() && !isVideo() && !isAudio();
	}

	public String category() {
		if( isImage() ) {
			return "Mynd";
		}

		if( isVideo() ) {
			return "Myndband";
		}

		if( isAudio() ) {
			return "Hljóðskrá";
		}

		return "Skjal";
	}
}