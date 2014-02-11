package concept.components.admin.edit;

import java.util.Locale;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOContext;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSComparator;
import com.webobjects.foundation.NSMutableArray;

import concept.ViewPage;
import concept.components.admin.SWSiteListing;
import concept.data.SWSite;
import er.extensions.foundation.ERXArrayUtilities;

/**
 * SWEditSite is used to edit information for an SWSite.
 */

public class CPEditSite extends ViewPage<SWSite> {

	public String currentLocaleName;

	public CPEditSite( WOContext context ) {
		super( context );
	}

	public NSArray<String> availableLocaleNames() {
		NSMutableArray<String> localeNames = new NSMutableArray<String>();

		for( Locale l : Locale.getAvailableLocales() ) {
			localeNames.addObject( l.getLanguage() );
		}

		NSArray<String> a = ERXArrayUtilities.arrayWithoutDuplicates( localeNames );
		return ERXArrayUtilities.sortedArrayUsingComparator( a, NSComparator.AscendingCaseInsensitiveStringComparator );
	}

	public String currentLocaleDisplayName() {
		return currentLocaleName + " (" + new Locale( currentLocaleName ).getDisplayName() + ")";
	}

	/**
	 * Deletes the selected site
	 */
	public WOActionResults deleteSite() {
		ec().deleteObject( selectedObject() );
		SWSiteListing.setSelectedSite( session(), null );
		return saveChanges();
	}
}