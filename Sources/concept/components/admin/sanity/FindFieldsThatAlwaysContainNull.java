package concept.components.admin.sanity;

import is.rebbi.wo.util.USUtilities;

import com.webobjects.appserver.WOContext;
import com.webobjects.eoaccess.EOAttribute;
import com.webobjects.eoaccess.EOEntity;
import com.webobjects.eoaccess.EOModelGroup;
import com.webobjects.eoaccess.EOUtilities;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSDictionary;
import com.webobjects.foundation.NSMutableArray;

import concept.SWBaseComponent;

public class FindFieldsThatAlwaysContainNull extends SWBaseComponent {

	public EOAttribute currentAttribute;

	public FindFieldsThatAlwaysContainNull( WOContext context ) {
		super( context );
	}

	public NSArray<EOAttribute> attributes() {
		NSMutableArray<EOAttribute> attributes = new NSMutableArray<>();

		NSArray<EOEntity> entities = EOModelGroup.defaultGroup().modelNamed( "Tatu" ).entities();

		for( EOEntity entity : entities ) {
			for( EOAttribute attribute : entity.attributes() ) {
				int i = numberOfDistinctValuesForAttribute( attribute );

				if( i < 2 ) {
					attributes.addObject( attribute );
				}
			}
		}

		return attributes;
	}

	private int numberOfDistinctValuesForAttribute( EOAttribute attribute ) {
		String tableName = attribute.entity().externalName();
		String columnName = attribute.columnName();
		String sql = "select count(distinct `" + columnName + "`) from " + tableName;

		NSArray<NSDictionary<?, ?>> a = EOUtilities.rawRowsForSQL( session().defaultEditingContext(), "Tatu", sql );
		Object o = a.objectAtIndex( 0 ).allValues().objectAtIndex( 0 );
		int result = USUtilities.integerFromObject( o );

		System.out.println( sql + " : " + result );
		return result;
	}
}