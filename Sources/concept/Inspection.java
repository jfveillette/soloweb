package concept;

import is.rebbi.wo.definitions.EntityViewDefinition;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOContext;
import com.webobjects.eocontrol.EOEditingContext;

import concept.components.EditPageGeneric;
import concept.components.EditWrapper;
import concept.components.ViewPageGeneric;
import concept.components.ViewWrapper;
import er.extensions.appserver.ERXApplication;
import er.extensions.eof.ERXEOControlUtilities;
import er.extensions.eof.ERXGenericRecord;

/**
 * Central class for the inspection stuff.
 */

public class Inspection {

	/**
	 * @return The given object opened in the default view page.
	 */
	public static WOActionResults inspectObjectInContext( ERXGenericRecord object, WOContext context ) {
		Class<? extends ViewPage> componentClass = EntityViewDefinition.viewComponentClass( object.getClass() );

		if( componentClass == null ) {
			componentClass = ViewPageGeneric.class;
		}

		return inspectObjectInContextUsingComponent( object, context, componentClass );
	}

	/**
	 * @return The given object opened in the default edit page.
	 */
	public static WOActionResults editObjectInContext( ERXGenericRecord object, WOContext context ) {
		Class<? extends ViewPage> componentClass = EntityViewDefinition.editComponentClass( object.getClass() );

		if( componentClass == null ) {
			componentClass = EditPageGeneric.class;
		}

		return editObjectInContextUsingComponent( object, context, componentClass );
	}

	/**
	 * Takes an object of a supported type and returns an inspection page for it.
	 */
	public static WOActionResults inspectObjectInContextUsingComponent( ERXGenericRecord object, WOContext context, Class<? extends ViewPage> componentClass ) {
		return openObjectInContextUsingWrapperAndComponent( object, context, ViewWrapper.class, componentClass );
	}

	public static WOActionResults editObjectInContextUsingComponent( ERXGenericRecord object, WOContext context, Class<? extends ViewPage> componentClass ) {
		return openObjectInContextUsingWrapperAndComponent( object, context, EditWrapper.class, componentClass );
	}

	private static WOActionResults openObjectInContextUsingWrapperAndComponent( ERXGenericRecord object, WOContext context, Class<? extends ViewWrapper> wrapperClass, Class<? extends ViewPage> componentClass ) {
		ViewWrapper nextPage = ERXApplication.erxApplication().pageWithName( wrapperClass );
		nextPage.setDisplayComponentName( componentClass.getSimpleName() );
		nextPage.setSelectedObject( object );
		nextPage.setCallingComponent( context.page() );
		return nextPage;
	}

	public static <A extends ERXGenericRecord> WOActionResults createAndEditObject( EOEditingContext ec, Class<A> clazz, WOContext context ) {
		ERXGenericRecord eo = ERXEOControlUtilities.createAndInsertObject( ec, clazz );
		ec.processRecentChanges();
		WOActionResults nextPage = editObjectInContext( eo, context );
		return nextPage;
	}
}