package concept.components.settings;

import is.rebbi.wo.util.Inspection;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOContext;
import com.webobjects.foundation.NSArray;

import concept.data.SWSite;

/**
 * Administration of web sites in the system.
 */

public class SWSettingsSites extends SWSettingsPanel {

	public SWSite currentSite;

	public SWSettingsSites( WOContext context ) {
		super( context );
	}

	public NSArray<SWSite> sites() {
		return SWSite.fetchAllSWSites( ec(), SWSite.NAME.ascs() );
	}

	/**
	 * Create a new Site.
	 */
	public WOActionResults newSite() {
		SWSite site = SWSite.create( ec() );
//		SWSiteListing.setSelectedSite( session(), site );
		ec().saveChanges();
		return Inspection.editObjectInContext( site, context() );
	}
}