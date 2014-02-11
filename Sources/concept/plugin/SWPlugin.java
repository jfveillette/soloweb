package concept.plugin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.webobjects.foundation.NSArray;

/**
 * SWPlugin specifies meta information for a plugin in SoloWeb,
 * such as the names of EOModels, components it adds to the
 * component pool etc. Documentation forthcoming.
 */

public class SWPlugin extends Object {

	private static final Logger logger = LoggerFactory.getLogger( SWPlugin.class );

	private String _name;
	private NSArray<String> _components;
	private NSArray<String> _models;
	private String _mainAdminComponent;
	private String _componentAdminComponent;
	private String _settingsComponent;
	private String _version;

	public void registerWithApplication() {
		SWPluginHandler.defaultInstance().registerPlugin( this );
		logger.info( "Registered plugin: " + this.name() );
	}

	public void setName( String newName ) {
		_name = newName;
	}

	public String name() {
		return _name;
	}

	public void setComponents( NSArray<String> newComponents ) {
		_components = newComponents;
	}

	public NSArray<String> components() {
		return _components;
	}

	public void setModels( NSArray<String> newModels ) {
		_models = newModels;
	}

	public NSArray<String> models() {
		return _models;
	}

	public void setMainAdminComponent( String newMainAdminComponent ) {
		_mainAdminComponent = newMainAdminComponent;
	}

	public String mainAdminComponent() {
		return _mainAdminComponent;
	}

	public void setComponentAdminComponent( String newComponentAdminComponent ) {
		_componentAdminComponent = newComponentAdminComponent;
	}

	public String componentAdminComponent() {
		return _componentAdminComponent;
	}

	public void setSettingsComponent( String newSettingsComponent ) {
		_settingsComponent = newSettingsComponent;
	}

	public String settingsComponent() {
		return _settingsComponent;
	}

	public void setVersion( String newVersion ) {
		_version = newVersion;
	}

	public String version() {
		return _version;
	}

	@Override
	public String toString() {
		return "name: " + name() + "\ncomponents: " + components() + "\nmodels: " + models() + "\nmainAdminComponent: " + mainAdminComponent() + "\ncomponentAdminComponent: " + componentAdminComponent();
	}
}