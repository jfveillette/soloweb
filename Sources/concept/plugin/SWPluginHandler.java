package concept.plugin;

import java.util.Enumeration;

import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSMutableDictionary;

import concept.SWApplication;

/**
 * SWPluginHandler handles the loading of plugins, management of EOModel connections etc. Documentation forthcoming.
 */

public class SWPluginHandler extends Object {

	private static NSMutableArray<SWPlugin> _registeredPlugins = new NSMutableArray<>();
	private static SWPluginHandler _defaultInstance = new SWPluginHandler();

	private SWPluginHandler() {}

	public static SWPluginHandler defaultInstance() {
		return _defaultInstance;
	}

	public static NSMutableArray<SWPlugin> registeredPlugins() {
		return _registeredPlugins;
	}

	public void registerPlugin( SWPlugin aPlugin ) {
		registeredPlugins().addObject( aPlugin );
	}

	public void loadRegisteredPlugins() {
		Enumeration<SWPlugin> e = registeredPlugins().objectEnumerator();
		SWPlugin aPlugin;

		while( e.hasMoreElements() ) {
			aPlugin = e.nextElement();
			loadPlugin( aPlugin );
		}
	}

	public void loadPlugin( SWPlugin aPlugin ) {

		if( aPlugin.componentAdminComponent() != null ) {
			SWApplication.swapplication().activeComponents().setObjectForKey( aPlugin.componentAdminComponent(), aPlugin.name() );
		}

		if( aPlugin.mainAdminComponent() != null ) {
			SWApplication.swapplication().activeSystems().setObjectForKey( aPlugin.mainAdminComponent(), aPlugin.name() );
		}

		if( aPlugin.settingsComponent() != null ) {
			SWApplication.swapplication().activeSettingsTabs().setObjectForKey( aPlugin.settingsComponent(), aPlugin.name() );
		}

		if( aPlugin.components() != null ) {
			Enumeration<String> componentEnumerator = aPlugin.components().objectEnumerator();
			NSMutableDictionary<String, String> d = new NSMutableDictionary<>();
			String currentComponent;
			while( componentEnumerator.hasMoreElements() ) {
				currentComponent = componentEnumerator.nextElement();
				d.setObjectForKey( aPlugin.name(), currentComponent );
			}

			SWApplication.swapplication().activeSystemsAndComponents().addEntriesFromDictionary( d );
		}

		if( aPlugin.models() != null ) {
			Enumeration<String> modelEnumerator = aPlugin.models().objectEnumerator();
			String currentModelName;

			while( modelEnumerator.hasMoreElements() ) {
				currentModelName = modelEnumerator.nextElement();
				SWApplication.swapplication().pluginModels().addObject( currentModelName );
			}
		}
	}
}