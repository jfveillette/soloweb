package concept;

import com.webobjects.appserver.WOApplication;
import com.webobjects.appserver.WOContext;
import com.webobjects.appserver.WOResponse;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSDictionary;
import com.webobjects.foundation.NSMutableArray;

import concept.components.SWErrorMessage;
import concept.components.client.SWDefaultLook1;
import concept.components.client.SWDefaultLook2;
import concept.components.client.SWDefaultLook3;
import concept.components.client.SWDefaultLook4;
import concept.components.client.SWDefaultLook5;
import concept.components.client.SWDefaultLook6;
import er.extensions.appserver.ERXApplication;

public class SWApplication extends ERXApplication {

	private NSMutableArray<String> _pluginModels = new NSMutableArray<>( "SoloWeb" );
	private static NSMutableArray<String> _looks = new NSMutableArray<>();

	public SWApplication() {
		setIncludeCommentsInResponses( true );

		addLook( SWDefaultLook1.class.getSimpleName() );
		addLook( SWDefaultLook2.class.getSimpleName() );
		addLook( SWDefaultLook3.class.getSimpleName() );
		addLook( SWDefaultLook4.class.getSimpleName() );
		addLook( SWDefaultLook5.class.getSimpleName() );
		addLook( SWDefaultLook6.class.getSimpleName() );
	}

	public static SWApplication swapplication() {
		return (SWApplication)WOApplication.application();
	}

	public static void addLook( String lookName ) {
		_looks.addObject( lookName );
	}

	public static NSArray<String> looks() {
		return _looks;
	}

	@Override
	public WOResponse handleSessionRestorationErrorInContext( WOContext aContext ) {
		return SWErrorMessage.handleSessionRestorationErrorInContext( aContext );
	}

	public NSDictionary<String, String> additionalSystems() {
		return new NSDictionary<>();
	}

	public NSDictionary<String, String> additionalComponents() {
		return new NSDictionary<>();
	}

	public NSDictionary<String, String> additionalSystemsAndComponents() {
		return new NSDictionary<>();
	}

	public NSDictionary<String, String> additionalSettingsTabs() {
		return new NSDictionary<>();
	}

	public NSDictionary<String, String> additionalPageEditingComponents() {
		return new NSDictionary<>();
	}

	public NSDictionary<String, String> additionalSiteEditingComponents() {
		return new NSDictionary<>();
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