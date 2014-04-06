package concept.components.admin.sanity;

import is.rebbi.core.util.StringUtilities;
import is.rebbi.wo.util.USEOUtilities;

import java.io.File;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WORequest;
import com.webobjects.eoaccess.EOEntity;
import com.webobjects.eoaccess.EOModelGroup;
import com.webobjects.eoaccess.EORelationship;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOFetchSpecification;
import com.webobjects.eocontrol.EOQualifier;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSDictionary;
import com.webobjects.foundation.NSKeyValueCoding;
import com.webobjects.foundation.NSMutableDictionary;

import concept.data.SWEntityMeta;
import er.extensions.appserver.ERXDirectAction;
import er.extensions.eof.ERXEOAccessUtilities;
import er.extensions.eof.ERXEOControlUtilities;

public class FindMissingRelatedRecords extends ERXDirectAction {

	private StringBuilder report = new StringBuilder();
	private int totalCount = 0;
	private EOEditingContext ec = session().defaultEditingContext();

	public FindMissingRelatedRecords( WORequest r ) {
		super( r );
	}

	@Override
	public WOActionResults defaultAction() {
		NSArray<String> sourceEntityNames = (NSArray<String>)SWEntityMeta.fetchAllSWEntityMetas( ec ).valueForKey( SWEntityMeta.NAME_KEY );

		for( String sourceEntityName : sourceEntityNames ) {
			System.out.println( "------------------- " + sourceEntityName );
			EOEntity sourceEntity = EOModelGroup.defaultGroup().entityNamed( sourceEntityName );

			EOFetchSpecification fs = new EOFetchSpecification( sourceEntityName, null, null );
			fs.setFetchesRawRows( true );
			fs.setRawRowKeyPaths( (NSArray<String>)sourceEntity.attributes().valueForKey( "name" ) );
			NSArray<NSDictionary> records = ec.objectsWithFetchSpecification( fs );

			NSArray<EORelationship> relationships = USEOUtilities.relationships( sourceEntity );

			for( NSDictionary record : records ) {
				for( EORelationship relationship : relationships ) {
					handle( record, relationship );
				}
			}
		}

		System.out.println( "Deleted total: " + totalCount );

		return null;
	}

	private void handle( NSDictionary record, EORelationship relationship ) {
		if( !relationship.isToMany() && !relationshipIsNull( record, relationship ) ) {
			String sourceEntityName = relationship.entity().name();
			String destinationEntityName = relationship.destinationEntity().name();
			EOQualifier q = relationship.qualifierWithSourceRow( record );

			int count = ERXEOControlUtilities.objectCountWithQualifier( ec, destinationEntityName, q );

			if( count == 0 ) {
				//			EOEnterpriseObject eo = EOUtilities.objectFromRawRow( ec, sourceEntityName, record );
				NSDictionary newValues = nullForRelationship( relationship );
				System.out.println( "Deleting: " + sourceEntityName + " ;;; " + record + " ;;; " + newValues );
				ERXEOAccessUtilities.deleteRowsDescribedByQualifier( ec, sourceEntityName, EOQualifier.qualifierToMatchAllValues( record ) );
				//				ERXEOAccessUtilities.updateRowsDescribedByQualifier( ec, sourceEntityName, EOQualifier.qualifierToMatchAllValues( record ), newValues );
				totalCount++;
			}
		}
	}

	private NSDictionary nullForRelationship( EORelationship relationship ) {
		String key = relationship.joins().objectAtIndex( 0 ).sourceAttribute().name();
		Object value = NSKeyValueCoding.NullValue;
		NSMutableDictionary d = new NSMutableDictionary();
		d.setObjectForKey( value, key );
		return d;
	}

	private boolean relationshipIsNull( NSDictionary record, EORelationship relationship ) {
		String key = relationship.joins().objectAtIndex( 0 ).sourceAttribute().name();
		Object value = record.valueForKey( key );
		return value == null || value instanceof NSKeyValueCoding.Null;
	}

	public WOActionResults countAction() {
		NSArray<String> sourceEntityNames = (NSArray<String>)SWEntityMeta.fetchAllSWEntityMetas( ec ).valueForKey( SWEntityMeta.NAME_KEY );
		StringBuilder b = new StringBuilder();

		for( String entityName : sourceEntityNames ) {
			int count = ERXEOControlUtilities.objectCountWithQualifier( ec, entityName, null );
			b.append( entityName );
			b.append( ";" );
			b.append( count );
			b.append( "\n" );
		}

		StringUtilities.writeStringToFileUsingEncoding( b.toString(), new File( "/Users/hugi/Desktop/delete_after.txt" ), "UTF-8" );

		return null;
	}
}