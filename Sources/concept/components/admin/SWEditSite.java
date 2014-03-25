package concept.components.admin;

import is.rebbi.wo.util.SWSettings;

import com.webobjects.appserver.WOComponent;
import com.webobjects.appserver.WOContext;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSDictionary;
import com.webobjects.foundation.NSMutableDictionary;

import concept.Concept;
import concept.SWAdminComponent;
import concept.SWSessionHelper;
import concept.data.SWSite;
import concept.util.SWLoc;
import er.extensions.appserver.ERXSession;

public class SWEditSite extends SWAdminComponent {

	public String selectedTab = SWLoc.string( "eSiteTabGeneral", session() );
	public NSArray<String> tabs = tabDictionary().allKeys();

	public SWEditSite( WOContext context ) {
		super( context );
	}

	public NSDictionary<String, String> tabDictionary() {
		NSMutableDictionary<String, String> activeSiteEditingComponents = new NSMutableDictionary<>( Concept.sw().activeSiteEditingComponents() );
		activeSiteEditingComponents.setObjectForKey( SWEditSiteGeneralInfo.class.getSimpleName(), SWLoc.string( "eSiteTabGeneral", session() ) );

		if( SWSettings.booleanForKey( "enablePrivileges" ) ) {
			activeSiteEditingComponents.setObjectForKey( SWEditSitePrivileges.class.getSimpleName(), SWLoc.string( "eSiteTabAccessPrivileges", session() ) );
		}

		if( SWSessionHelper.userInSession( session() ).isAdministrator() ) {
			activeSiteEditingComponents.setObjectForKey( SWDefaultLook6Admin.class.getSimpleName(), "SWDefaultLook6" );
		}

		return activeSiteEditingComponents;
	}

	public String selectedEditingComponentName() {
		return (String)tabDictionary().valueForKey( selectedTab );
	}

	public SWSite selectedSite() {
		return (SWSite)((ERXSession)session()).objectStore().valueForKey( "selectedSite" );
	}

	public void setSelectedSite( SWSite value ) {
		((ERXSession)session()).objectStore().takeValueForKey( value, "selectedSite" );
	}

	@Override
	public WOComponent saveChanges() {
		session().defaultEditingContext().saveChanges();
		return null;
	}

	//	public NSArray<String> availableLocaleNames() {
	//		NSMutableArray<String> localeNames = new NSMutableArray<>();
	//
	//		for( Locale l : Locale.getAvailableLocales() ) {
	//			localeNames.addObject( l.getLanguage() );
	//		}
	//
	//		NSArray<String> a = ERXArrayUtilities.arrayWithoutDuplicates( localeNames );
	//		return ERXArrayUtilities.sortedArrayUsingComparator( a, NSComparator.AscendingCaseInsensitiveStringComparator );
	//	}
	//
	//	public String currentLocaleDisplayName() {
	//		return currentLocaleName + " (" + new Locale( currentLocaleName ).getDisplayName() + ")";
	//	}

}