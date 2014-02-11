package concept.plugin;

import com.webobjects.foundation.NSArray;

/**
 * A simple class that stores the name of a plugin, it's EOModels, and if schema should be dropped
 * or constructed for the current plugin. Just used in SWManageSettings.
 */

public class SWPluginItem extends Object {

	/**
	* The plugin's name
	 */
	public String name;

	/**
	* The plugin's EOModels
	 */
	public NSArray models;

	/**
	    * Specifies if schema should be dropped for this model
	 */
	public boolean dropSchema;

	/**
	 * Specifies if schema should be constructed for this model
	 */
	public boolean constructSchema;
	public String version;

	public void setName( String newName ) {
		name = newName;
	}

	public void setModels( NSArray newModels ) {
		models = newModels;
	}

	public void setVersion( String newVersion ) {
		version = newVersion;
	}

	@Override
	public String toString() {
		return "Name: " + name + "\nModels: " + models + "\ndropSchema: " + dropSchema + "\nconstructSchema: " + constructSchema + "\n";
	}
}