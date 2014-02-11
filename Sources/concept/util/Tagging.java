package concept.util;

import is.rebbi.wo.interfaces.HasFakeRelationship;

import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOSortOrdering;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableArray;

import concept.data.SWTag;
import concept.data.SWTagging;
import er.extensions.eof.ERXGenericRecord;

/**
 * Utility methods for working with documents.
 */

public class Tagging {

	/**
	 * @return All links to the given
	 */
	private static NSArray<SWTagging> linksToRecord( ERXGenericRecord record ) {

		if( record == null ) {
			return NSArray.emptyArray();
		}

		return HasFakeRelationship.Util.relatedObjects( SWTagging.class, record, null );
	}

	/**
	 * @return All the documents related to the given objects.
	 */
	public static NSArray<SWTag> tags( ERXGenericRecord record ) {

		if( record == null ) {
			return NSArray.emptyArray();
		}

		NSMutableArray<SWTag> results = new NSMutableArray<>();

		NSArray<SWTagging> links = linksToRecord( record );

		for( SWTagging link : links ) {
			SWTag d = link.tag();

			if( d != null ) {
				results.addObject( d );
			}
		}

		EOSortOrdering.sortArrayUsingKeyOrderArray( results, SWTag.NAME.ascInsensitives() );

		return results;
	}

	public static int relatedDocumentCount( ERXGenericRecord record ) {
		return HasFakeRelationship.Util.relatedObjectCount( SWTagging.class, record );
	}

	public static SWTagging addTag( SWTag tag, ERXGenericRecord object ) {
		SWTagging tagging = HasFakeRelationship.Util.create( SWTagging.class, object );
		tagging.setTag( tag );
		return tagging;
	}

	public static NSArray<SWTagging> addTags( NSArray<SWTag> tags, ERXGenericRecord object ) {
		NSMutableArray<SWTagging> results = new NSMutableArray<>();

		for( SWTag tag : tags ) {
			addTag( tag, object );
		}

		return results;
	}

	public static void setTags( NSArray<SWTag> tags, ERXGenericRecord object ) {
		untag( object );
		addTags( tags, object );
	}

	public static void untag( ERXGenericRecord object ) {
		for( SWTagging tagging : taggings( object ) ) {
			tagging.delete();
		}
	}

	/**
	 * @return All the EOs related to the given document.
	 */
	public static NSArray<ERXGenericRecord> objects( SWTag tag ) {

		if( tag == null ) {
			return NSArray.emptyArray();
		}

		NSMutableArray<ERXGenericRecord> results = new NSMutableArray<>();

		NSArray<SWTagging> links = tag.taggings();

		for( SWTagging link : links ) {
			ERXGenericRecord targetObject = HasFakeRelationship.Util.targetObject( link );

			if( targetObject != null ) {
				results.addObject( targetObject );
			}
		}

		return results;
	}

	public void untag( SWTag tag, ERXGenericRecord object ) {
		SWTagging t = tagging( tag, object );

		if( t != null ) {
			t.removeObjectFromBothSidesOfRelationshipWithKey( object, "tag" );
			t.delete();
		}
	}

	public static boolean isTagged( SWTag tag, ERXGenericRecord object ) {
		return tagging( tag, object ) != null;
	}

	private static SWTagging tagging( SWTag tag, ERXGenericRecord object ) {

		if( tag == null || object == null ) {
			return null;
		}

		EOEditingContext ec = object.editingContext();
		return SWTagging.fetchSWTagging( ec, SWTagging.TAG.eq( tag ).and( SWTagging.TARGET_ENTITY_NAME.eq( object.entityName() ).and( SWTagging.TARGET_ID.eq( object.primaryKey() ) ) ) );
	}

	private static NSArray<SWTagging> taggings( ERXGenericRecord object ) {
		return HasFakeRelationship.Util.relatedObjects( SWTagging.class, object, NSArray.<EOSortOrdering> emptyArray() );
	}
}