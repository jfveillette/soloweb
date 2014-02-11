package concept.components.client;

import is.rebbi.wo.util.USEOUtilities;
import is.rebbi.wo.util.USUtilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.webobjects.appserver.WOContext;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableArray;

import concept.SWGenericComponent;
import concept.data.SWComponent;

public class SWSideBySide extends SWGenericComponent {

	private static final Logger logger = LoggerFactory.getLogger( SWSideBySide.class );

	public NSMutableArray _columnList = null;
	public SideBySideComponentInfo currentColumn = null;
	public int currentColumnIndex = 0;

	public class SideBySideComponentInfo {
		public Integer componentId;
		public SWComponent component = null;
		public String columnWidth;

		public SideBySideComponentInfo() {}

		public SideBySideComponentInfo( String componentIdStr, String colWidthStr ) {
			componentId = USUtilities.integerFromObject( componentIdStr );
			columnWidth = colWidthStr;

			// Get the component
			try {
				component = USEOUtilities.objectWithPK( session().defaultEditingContext(), SWComponent.ENTITY_NAME, componentId );
			}
			catch( Exception ex ) {
				// Not found or smth
				logger.error( "SWSideBySide: Component with id " + componentId + " not found" );
			}
		}
	}

	public SWSideBySide( WOContext context ) {
		super( context );
	}

	public NSArray columnList() {
		String compIdStr = (String)currentComponent().customInfo().valueForKey( "swsidebyside_partnumbers" );
		String colWidthsStr = (String)currentComponent().customInfo().valueForKey( "swsidebyside_columnwidths" );
		NSArray compIds = NSArray.componentsSeparatedByString( compIdStr, " " );
		NSArray colWidths = NSArray.componentsSeparatedByString( colWidthsStr, " " );

		if( compIds != null && colWidths != null && compIds.count() > 0 && colWidths.count() > 0 && compIds.count() == colWidths.count() ) {
			SideBySideComponentInfo theComponent;

			_columnList = new NSMutableArray();
			String ownerIdStr = currentComponent().primaryKey().toString();
			for( int i = 0; i < compIds.count(); i++ ) {
				compIdStr = (String)compIds.objectAtIndex( i );
				if( !compIdStr.equals( ownerIdStr ) ) {
					theComponent = new SideBySideComponentInfo( compIdStr, (String)colWidths.objectAtIndex( i ) );
					_columnList.addObject( theComponent );
				}
			}
		}

		return _columnList;
	}

	public String sideBySideId() {
		return "swsidebyside_" + currentComponent().primaryKey();
	}

	public String sideBySideCompNo() {
		return sideBySideId() + "_" + currentColumnIndex;
	}
}