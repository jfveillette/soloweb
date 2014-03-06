package concept;

import com.webobjects.appserver.WOApplication;
import com.webobjects.appserver.WOContext;
import com.webobjects.appserver.WOResponse;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSDictionary;
import com.webobjects.foundation.NSMutableArray;

import concept.components.SWErrorMessage;
import er.extensions.appserver.ERXApplication;

public class SWApplication extends ERXApplication {

	private NSMutableArray<String> _pluginModels = new NSMutableArray<>( "SoloWeb" );

	public SWApplication() {
		setIncludeCommentsInResponses( true );
	}

	public static SWApplication swapplication() {
		return (SWApplication)WOApplication.application();
	}

	@Override
	public WOResponse handleSessionRestorationErrorInContext( WOContext aContext ) {
		return SWErrorMessage.handleSessionRestorationErrorInContext( aContext );
	}

	public NSDictionary<String, String> additionalSystems() {
		return NSDictionary.emptyDictionary();
	}

	public NSDictionary<String, String> additionalComponents() {
		return NSDictionary.emptyDictionary();
	}

	public NSDictionary<String, String> additionalSystemsAndComponents() {
		return NSDictionary.emptyDictionary();
	}

	public NSDictionary<String, String> additionalSettingsTabs() {
		return NSDictionary.emptyDictionary();
	}

	public NSDictionary<String, String> additionalPageEditingComponents() {
		return NSDictionary.emptyDictionary();
	}

	public NSDictionary<String, String> additionalSiteEditingComponents() {
		return NSDictionary.emptyDictionary();
	}

	public NSArray<String> additionalModels() {
		return NSArray.emptyArray();
	}

	public NSMutableArray<String> pluginModels() {
		return _pluginModels;
	}

	public NSArray<String> activeModels() {
		NSMutableArray<String> m = new NSMutableArray<>();
		m.addObject( "SoloForms" );
		m.addObject( "SoloMail" );
		m.addObject( "SoloPoll" );
		m.addObject( "SoloStaff" );
		m.addObject( "SoloThreads" );
		m.addObjectsFromArray( pluginModels() );
		m.addObjectsFromArray( additionalModels() );
		return m;
	}

	public String frameworkBundleName() {
		return Concept.sw().frameworkBundleName();
	}
}