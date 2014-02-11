package concept.definitions;

import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOFetchSpecification;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableArray;

import concept.data.SWEntityMeta;
import er.extensions.eof.ERXEC;

public class DatabaseDefinitions implements ProvidesEntityViewDefinitions {

	@Override
	public NSArray<EntityViewDefinition> entityViewDefinitions() {
		NSMutableArray<EntityViewDefinition> results = new NSMutableArray<>();

		EOEditingContext ec = ERXEC.newEditingContext();
		EOFetchSpecification fs = new EOFetchSpecification( SWEntityMeta.ENTITY_NAME, null, SWEntityMeta.NAME.ascInsensitives() );
		fs.setPrefetchingRelationshipKeyPaths( new NSArray<String>( SWEntityMeta.COLUMNS_KEY ) );
		NSArray<SWEntityMeta> a = ec.objectsWithFetchSpecification( fs );

		for( SWEntityMeta m : a ) {
			if( m.name() != null ) {
				results.addObject( m.toEntityViewDefinition() );
			}
		}

		return results;
	}
}