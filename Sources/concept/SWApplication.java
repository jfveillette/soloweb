package concept;

import is.rebbi.wo.util.SWDictionary;
import is.rebbi.wo.util.SWSettings;
import is.rebbi.wo.util.USUtilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.webobjects.appserver.WOApplication;
import com.webobjects.appserver.WOContext;
import com.webobjects.appserver.WOResponse;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSDictionary;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSMutableDictionary;
import com.webobjects.foundation.NSTimestamp;

import concept.components.SWErrorMessage;
import concept.components.admin.SWAdminCustomComponent;
import concept.components.client.SWDefaultLook1;
import concept.components.client.SWDefaultLook2;
import concept.components.client.SWDefaultLook3;
import concept.components.client.SWDefaultLook4;
import concept.components.client.SWDefaultLook5;
import concept.components.client.SWDefaultLook6;
import concept.plugin.SWPluginHandler;
import concept.search.SWLuceneUtilities;
import concept.search.SWNewsItemLuceneUtilities;
import concept.search.SWPageLuceneUtilities;
import er.extensions.appserver.ERXApplication;

public class SWApplication extends ERXApplication {

	private static final Logger logger = LoggerFactory.getLogger( SWApplication.class );

	private NSMutableArray<String> _pluginModels = new NSMutableArray<>( "SoloWeb" );
	private NSMutableDictionary<String, String> _activeSettingsTabs;
	private NSMutableDictionary<String, String> _activePageEditingComponents;
	private NSMutableDictionary<String, String> _activeSiteEditingComponents;
	private NSMutableDictionary<String, String> _activeSystems;
	private NSMutableDictionary<String, String> _activeComponents;
	private NSMutableDictionary<String, String> _activeSystemsAndComponents;
	private NSMutableArray<SWSession> _activeUserSessions = new NSMutableArray<>();
	private NSMutableDictionary<String, SWDictionary<String,String>> _localizedStrings = new NSMutableDictionary<>();
	private static NSMutableArray<String> _looks = new NSMutableArray<>();

	public SWApplication() {
		setIncludeCommentsInResponses( true );

		_activeSystems = new NSMutableDictionary<>( additionalSystems() );
		_activeComponents = new NSMutableDictionary<>( additionalComponents() );
		_activeComponents.setObjectForKey( SWAdminCustomComponent.class.getSimpleName(), "Custom" );
		_activePageEditingComponents = new NSMutableDictionary<>( additionalPageEditingComponents() );
		_activeSiteEditingComponents = new NSMutableDictionary<>( additionalSiteEditingComponents() );
		_activeSystemsAndComponents = new NSMutableDictionary<>( additionalSystemsAndComponents() );
		_activeSettingsTabs = new NSMutableDictionary<>( additionalSettingsTabs() );

		addLook( SWDefaultLook1.class.getSimpleName() );
		addLook( SWDefaultLook2.class.getSimpleName() );
		addLook( SWDefaultLook3.class.getSimpleName() );
		addLook( SWDefaultLook4.class.getSimpleName() );
		addLook( SWDefaultLook5.class.getSimpleName() );
		addLook( SWDefaultLook6.class.getSimpleName() );

		SWSettings.register();

		String englishString = USUtilities.stringFromResource( "sw32/lang/English.rsrc", Concept.sw().frameworkBundleName() );
		String icelandicString = USUtilities.stringFromResource( "sw32/lang/Icelandic.rsrc", Concept.sw().frameworkBundleName() );

		_localizedStrings.setObjectForKey( new SWDictionary( englishString ), "English" );
		_localizedStrings.setObjectForKey( new SWDictionary( icelandicString ), "Icelandic" );

		SWLuceneUtilities.addExtension( new SWPageLuceneUtilities() );
		SWLuceneUtilities.addExtension( new SWNewsItemLuceneUtilities() );

		SWYouTubeUtils.init();

		SWPluginHandler.defaultInstance().loadRegisteredPlugins();
		String defaultMailServer = SWSettings.stringForKey( "defaultMailServer" );

		if( defaultMailServer != null ) {
			setSMTPHost( defaultMailServer );
		}

		int sessionTimeOutInSeconds = new Integer( SWSettings.stringForKey( "sessionTimeOut", "30" ) ).intValue() * 60;
		setSessionTimeOut( new Integer( sessionTimeOutInSeconds ) );
		logger.info( "*** SoloWeb ready at " + new NSTimestamp() );
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

	public NSMutableDictionary<String, String> activeSettingsTabs() {
		return _activeSettingsTabs;
	}

	public NSMutableDictionary<String, String> activePageEditingComponents() {
		return _activePageEditingComponents;
	}

	public NSMutableDictionary<String, String> activeSiteEditingComponents() {
		return _activeSiteEditingComponents;
	}

	public NSMutableDictionary<String, String> activeSystems() {
		return _activeSystems;
	}

	public NSMutableDictionary<String, String> activeComponents() {
		return _activeComponents;
	}

	public NSMutableDictionary<String, String> activeSystemsAndComponents() {
		return _activeSystemsAndComponents;
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

	public NSMutableArray<SWSession> activeUserSessions() {
		return _activeUserSessions;
	}

	public SWDictionary<String, String> getLocalizedStringsForLanguage( String language ) {
		return _localizedStrings.objectForKey( language );
	}

	public boolean crmEnabled() {
		return SWSettings.booleanForKey( "crmEnabled" );
	}

	public String frameworkBundleName() {
		return Concept.sw().frameworkBundleName();
	}
}