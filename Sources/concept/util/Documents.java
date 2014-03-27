package concept.util;

import is.rebbi.wo.interfaces.HasFakeRelationship;
import is.rebbi.wo.util.FileTypes;
import is.rebbi.wo.util.USUtilities;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOSortOrdering;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSData;
import com.webobjects.foundation.NSMutableArray;

import concept.data.SWDocument;
import concept.data.SWDocumentLink;
import er.extensions.eof.ERXGenericRecord;

/**
 * Utility methods for working with documents.
 */

public class Documents {

	/**
	 * @return All links to the given
	 */
	private static NSArray<SWDocumentLink> linksToRecord( ERXGenericRecord record ) {

		if( record == null ) {
			return NSArray.emptyArray();
		}

		return HasFakeRelationship.Util.relatedObjects( SWDocumentLink.class, record, null );
	}

	/**
	 * @return All the documents related to the given objects.
	 */
	public static NSArray<SWDocument> relatedDocuments( ERXGenericRecord record ) {

		if( record == null ) {
			return NSArray.emptyArray();
		}

		NSMutableArray<SWDocument> results = new NSMutableArray<>();

		NSArray<SWDocumentLink> links = linksToRecord( record );

		for( SWDocumentLink link : links ) {
			SWDocument d = link.document();

			if( d != null ) {
				results.addObject( d );
			}
		}

		EOSortOrdering.sortArrayUsingKeyOrderArray( results, SWDocument.NAME.ascInsensitives() );

		return results;
	}

	public static int relatedDocumentCount( ERXGenericRecord record ) {
		return HasFakeRelationship.Util.relatedObjectCount( SWDocumentLink.class, record );
	}

	/**
	 * @throws IllegalStateException If more than one document is related to the record.
	 * @return One related document, null if no document is related.
	 */
	public static SWDocument relatedDocument( ERXGenericRecord record ) {

		NSArray<SWDocument> results = relatedDocuments( record );

		if( results.count() == 0 ) {
			return null;
		}

		if( results.count() > 1 ) {
			throw new IllegalStateException( "Expected to find exactly one related document but found " + results.count() + ". Record: " + record.entityName() + ":" + record.primaryKey() );
		}

		return results.lastObject();
	}

	/**
	 * Removes any preexisting document links to the given record and links the given document.
	 */
	public static void setRelatedDocument( ERXGenericRecord record, SWDocument document ) {

		NSArray<SWDocumentLink> links = linksToRecord( record );

		for( SWDocumentLink link : links ) {
			link.delete();
		}

		linkRecordToDocument( record, document );
	}

	public static SWDocument create( EOEditingContext ec, String filename, InputStream stream ) {
		String name = FileTypes.filenameByRemovingExtension( filename );
		String extension = FileTypes.extensionFromFilename( filename );
		return create( ec, name, extension, stream );
	}

	public static SWDocument create( EOEditingContext ec, String name, String extension, InputStream stream ) {
		SWDocument document = new SWDocument();
		ec.insertObject( document );
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

	public static SWDocument create( EOEditingContext ec, String filename, NSData data ) {
		String name = FileTypes.filenameByRemovingExtension( filename );
		String extension = FileTypes.extensionFromFilename( filename );
		return create( ec, name, extension, data );
	}

	public static SWDocument create( EOEditingContext ec, String name, String extension, NSData data ) {
		SWDocument document = new SWDocument();
		ec.insertObject( document );
		document.setName( name );
		document.setExtension( extension );
		document.setData( data );
		return document;
	}

	public static SWDocument createAndLink( ERXGenericRecord record, String filename, NSData data ) {
		String name = FileTypes.filenameByRemovingExtension( filename );
		String extension = FileTypes.extensionFromFilename( filename );
		return createAndLink( record, name, extension, data );
	}

	public static SWDocument createAndLink( ERXGenericRecord record, String name, String extension, NSData data ) {
		SWDocument document = create( record.editingContext(), name, extension, data );
		Documents.linkRecordToDocument( record, document );
		return document;
	}

	/**
	 * @return All the EOs related to the given document.
	 */
	public static NSArray<ERXGenericRecord> relatedObjects( SWDocument document ) {

		if( document == null ) {
			return NSArray.emptyArray();
		}

		NSMutableArray<ERXGenericRecord> results = new NSMutableArray<>();

		NSArray<SWDocumentLink> links = document.links();

		for( SWDocumentLink link : links ) {
			ERXGenericRecord targetObject = HasFakeRelationship.Util.targetObject( link );

			if( targetObject != null ) {
				results.addObject( targetObject );
			}
		}

		return results;
	}

	public static void linkRecordToDocument( ERXGenericRecord record, SWDocument document ) {
		SWDocumentLink link = HasFakeRelationship.Util.create( SWDocumentLink.class, record );
		link.setDocument( document );
	}

	public static SWDocument primary( ERXGenericRecord record ) {

		for( SWDocumentLink d : linksToRecord( record ) ) {
			if( USUtilities.booleanFromObject( d.primaryMarker() ) ) {
				return d.document();
			}
		}

		return null;
	}

	public static boolean isPrimary( ERXGenericRecord record, SWDocument document ) {
		return document.equals( primary( record ) );
	}

	public static void setPrimary( ERXGenericRecord record, SWDocument document ) {
		for( SWDocumentLink link : linksToRecord( record ) ) {
			if( document.equals( link.document() ) ) {
				link.setPrimary( true );
			}
			else {
				link.setPrimary( false );
			}
		}
	}

	public static NSArray<SWDocument> filterForType( NSArray<SWDocument> documents, String prefix ) {
		NSMutableArray<SWDocument> results = new NSMutableArray<>();

		for( SWDocument d : documents ) {
			if( d.mimeType() != null && d.mimeType().startsWith( prefix ) ) {
				results.addObject( d );
			}
		}

		return results;
	}
}