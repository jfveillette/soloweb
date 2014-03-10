package concept.definitions;

import is.rebbi.wo.util.USEOUtilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.webobjects.eoaccess.EOAttribute;
import com.webobjects.eoaccess.EOEntity;
import com.webobjects.eoaccess.EOModel;
import com.webobjects.eoaccess.EOModelGroup;
import com.webobjects.eocontrol.EOQualifier;
import com.webobjects.eocontrol.EOSortOrdering;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSMutableDictionary;

import concept.ViewPage;
import er.extensions.appserver.ERXApplication;
import er.extensions.eof.ERXGenericRecord;
import er.extensions.eof.ERXKey;

/**
 * Defines the viewing of a certain entity.
 */

public class EntityViewDefinition<E extends ERXGenericRecord, T extends ViewPage<E>, V extends ViewPage<E>> {

	private static final Logger logger = LoggerFactory.getLogger( EntityViewDefinition.class );

	/**
	 * Attributes are sorted in this way in lists.
	 */
	private static final EOSortOrdering SORTING_DEFINED = new EOSortOrdering( "sortOrder", EOSortOrdering.CompareAscending );
	private static final EOSortOrdering SORTING_NAME = new EOSortOrdering( "name", EOSortOrdering.CompareAscending );
	private static final NSArray<EOSortOrdering> USER_DEFINED_ATTRIBUTE_SORTING = new NSArray<>( SORTING_DEFINED, SORTING_NAME );

	/**
	 * All registered definitions.
	 */
	private static NSMutableDictionary<String, EntityViewDefinition> _definitions;

	/**
	 * All registered URL prefixes.
	 */
	private static NSMutableDictionary<String, String> _urlPrefixes = new NSMutableDictionary<>();

	/**
	 * The attributes of this entity
	 */
	private NSMutableDictionary<String, AttributeViewDefinition> _attributeViewDefinitions = new NSMutableDictionary<>();

	/**
	 * Name of the entity this view definition defines.
	 */
	private String _name;

	/**
	 * Icelandic name.
	 */
	private String _icelandicName;

	/**
	 * Icelandic plural name.
	 */
	private String _icelandicNamePlural;

	/**
	 * Icelandic description.
	 */
	private String _text;

	/**
	 * Class of component used to view objects if this type.
	 */
	private Class<T> _viewComponentClass;

	/**
	 * Class of component used to edit objects if this type.
	 */
	private Class<V> _editComponentClass;

	/**
	 * Prefix used in URLs to access objects of this type.
	 */
	private String _urlPrefix;

	/**
	 * Filename of icon used when showing objects of this type.
	 */
	private String _iconFileName;

	/**
	 * Filename of the icon used when this type of object is displayed.
	 */
	private boolean _showInList;

	/**
	 * Name of the category for this entity view definition.
	 */
	private String _categoryName;

	/**
	 * Cached list of attributes to show.
	 */
	private NSArray<AttributeViewDefinition> _attributesToShow;

	private static NSMutableArray<ProvidesEntityViewDefinitions> entityViewDefinitionProviders = new NSMutableArray<>();

	private EntityViewDefinition() {}

	public void addAttributeViewDefinition( AttributeViewDefinition a ) {
		if( a.name() != null ) {
			String key = a.name().toLowerCase();
			_attributeViewDefinitions.setObjectForKey( a, key );
		}
	}

	public static synchronized NSMutableDictionary<String, EntityViewDefinition> definitions() {
		if( _definitions == null ) {
			_definitions = new NSMutableDictionary<>();

			entityViewDefinitionProviders.addObject( new SystemDefinitions() );

			if( ERXApplication.application() instanceof ProvidesEntityViewDefinitions ) {
				entityViewDefinitionProviders.addObject( ((ProvidesEntityViewDefinitions)ERXApplication.application()) );
			}

			entityViewDefinitionProviders.addObject( new DatabaseDefinitions() );

			for( ProvidesEntityViewDefinitions provider : entityViewDefinitionProviders ) {
				for( EntityViewDefinition e : provider.entityViewDefinitions() ) {
					e.register();
				}
			}
		}

		return _definitions;
	}

	/**
	 * Define view definition for an entity.
	 */
	public static EntityViewDefinition create( String name, String icelandicName, String icelandicNamePlural, String categoryName, String text, String urlPrefix, String iconFileName, boolean showInLists, Class viewComponentClass, Class editComponentClass ) {
		EntityViewDefinition e = new EntityViewDefinition();
		e.setName( name );
		e.setIcelandicName( icelandicName );
		e.setIcelandicNamePlural( icelandicNamePlural );
		e.setCategoryName( categoryName );
		e.setText( text );
		e.setUrlPrefix( urlPrefix );
		e.setIconFileName( iconFileName );
		e.setShowInList( showInLists );
		e.setViewComponentClass( viewComponentClass );
		e.setEditComponentClass( editComponentClass );
		return e;
	}

	/**
	 * Define view definition for an entity.
	 */
	private void register() {
		logger.info( "Defining view for: {}", name() );

		EntityViewDefinition e = get( name() );

		if( icelandicName() != null ) {
			e.setIcelandicName( icelandicName() );
		}

		if( icelandicNamePlural() != null ) {
			e.setIcelandicNamePlural( icelandicNamePlural() );
		}

		if( categoryName() != null ) {
			e.setCategoryName( categoryName() );
		}

		if( text() != null ) {
			e.setText( text() );
		}

		if( urlPrefix() != null ) {
			e.setUrlPrefix( urlPrefix() );
		}

		if( iconFileName() != null ) {
			e.setIconFileName( iconFileName() );
		}

		if( e.showInList() != showInList() ) {
			e.setShowInList( showInList() );
		}

		if( viewComponentClass() != null ) {
			e.setViewComponentClass( viewComponentClass() );
		}

		if( editComponentClass() != null ) {
			e.setEditComponentClass( editComponentClass() );
		}

		if( viewComponentClass() != null ) {
			e._attributeViewDefinitions = _attributeViewDefinitions;
		}

		if( urlPrefix() != null ) {
			_urlPrefixes.setObjectForKey( name(), urlPrefix() );
		}
	}

	/**
	 * @return The definition for the given entityName
	 */
	public static EntityViewDefinition get( String entityName ) {

		if( entityName == null ) {
			throw new IllegalArgumentException( "[entityName] cannot be null" );
		}

		EntityViewDefinition e = definitions().objectForKey( entityName );

		if( e == null ) {
			e = new EntityViewDefinition();
			e.setName( entityName );
			_definitions.setObjectForKey( e, entityName );
		}

		return e;
	}

	/**
	 * @return The definition for the given object
	 */
	public static EntityViewDefinition get( ERXGenericRecord eo ) {
		return get( eo.entity() );
	}

	/**
	 * @return The definition for the given entity
	 */
	public static EntityViewDefinition get( EOEntity entity ) {
		return get( entity.name() );
	}

	/**
	 * @return The definition for the given class
	 */
	public static <T extends ERXGenericRecord> EntityViewDefinition get( Class<T> objectClass ) {
		return get( objectClass.getSimpleName() );
	}

	public String name() {
		return _name;
	}

	public void setName( String value ) {
		_name = value;
	}

	public String icelandicName() {

		if( _icelandicName == null ) {
			_icelandicName = name();
		}

		return _icelandicName;
	}

	public void setIcelandicName( String value ) {
		_icelandicName = value;
	}

	public String icelandicNamePlural() {
		if( _icelandicNamePlural == null ) {
			_icelandicNamePlural = icelandicName();
		}

		return _icelandicNamePlural;
	}

	public void setIcelandicNamePlural( String value ) {
		_icelandicNamePlural = value;
	}

	public Class<T> viewComponentClass() {
		return _viewComponentClass;
	}

	public void setViewComponentClass( Class<T> value ) {
		_viewComponentClass = value;
	}

	public Class<V> editComponentClass() {
		return _editComponentClass;
	}

	public void setEditComponentClass( Class<V> value ) {
		_editComponentClass = value;
	}

	public String urlPrefix() {
		return _urlPrefix;
	}

	public void setUrlPrefix( String value ) {
		_urlPrefix = value;
	}

	public String iconFileName() {
		return _iconFileName;
	}

	public void setIconFileName( String value ) {
		_iconFileName = value;
	}

	public boolean showInList() {
		return _showInList;
	}

	public void setShowInList( boolean showInList ) {
		this._showInList = showInList;
	}

	public String categoryName() {
		return _categoryName;
	}

	public void setCategoryName( String value ) {
		_categoryName = value;
	}

	public String text() {
		return _text;
	}

	public void setText( String value ) {
		_text = value;
	}

	public EOEntity entity() {
		return EOModelGroup.defaultGroup().entityNamed( name() );
	}

	public Class<E> objectClass() {
		return USEOUtilities.classForEntity( entity() );
	}

	public AttributeViewDefinition attributeNamed( String attributeName ) {

		if( attributeName == null ) {
			return null;
		}

		attributeName = attributeName.toLowerCase();
		AttributeViewDefinition result = _attributeViewDefinitions.objectForKey( attributeName );
		return result;
	}

	public String icelandicNameForAttribute( String attributeName ) {
		AttributeViewDefinition m = attributeNamed( attributeName );

		if( m == null ) {
			return attributeName;
		}

		return m.icelandicName();
	}

	public NSArray<AttributeViewDefinition> attributes() {
		return _attributeViewDefinitions.allValues();
	}

	public NSArray<AttributeViewDefinition> attributesToShow() {
		if( _attributesToShow == null ) {
			ERXKey<Boolean> showKey = new ERXKey<>( "show" );
			EOQualifier q = showKey.isTrue();

			_attributesToShow = _attributeViewDefinitions.allValues();
			_attributesToShow = EOQualifier.filteredArrayWithQualifier( _attributesToShow, q );
			_attributesToShow = EOSortOrdering.sortedArrayUsingKeyOrderArray( _attributesToShow, USER_DEFINED_ATTRIBUTE_SORTING );
		}

		return _attributesToShow;
	}

	/**
	 * @return Default sort orderings based on attributes shown.
	 */
	public NSArray<EOSortOrdering> defaultSortOrderings() {
		NSMutableArray<EOSortOrdering> a = new NSMutableArray<>();

		for( AttributeViewDefinition attributeDefinition : attributesToShow() ) {
			EOAttribute attribute = entity().attributeNamed( attributeDefinition.name() );

			if( attribute != null ) {
				EOSortOrdering s;

				if( USEOUtilities.attributeIsString( attribute ) ) {
					s = new EOSortOrdering( attributeDefinition.name(), EOSortOrdering.CompareCaseInsensitiveAscending );
				}
				else {
					s = new EOSortOrdering( attributeDefinition.name(), EOSortOrdering.CompareAscending );
				}

				a.addObject( s );
			}
		}

		return a;
	}

	public static NSArray<EntityViewDefinition> all() {

		NSMutableArray<EntityViewDefinition> all = new NSMutableArray<>();

		for( EOEntity entity : allEntities() ) {
			all.addObject( EntityViewDefinition.get( entity ) );
		}

		EOSortOrdering s = new EOSortOrdering( "name", EOSortOrdering.CompareCaseInsensitiveAscending );
		EOSortOrdering.sortArrayUsingKeyOrderArray( all, new NSArray<EOSortOrdering>( s ) );
		return all.immutableClone();
	}

	private static NSArray<EOEntity> allEntities() {
		NSMutableArray<EOEntity> allEntities = new NSMutableArray<>();

		for( EOModel model : EOModelGroup.defaultGroup().models() ) {
			for( EOEntity entity : model.entities() ) {
				if( !entity.name().startsWith( "EO" ) ) {
					allEntities.addObject( entity );
				}
			}
		}

		return allEntities.immutableClone();
	}

	/**
	 * @return Name of icon associated with the named entity.
	 */
	public static String iconFileName( String entityName ) {
		EntityViewDefinition type = get( entityName );

		if( type != null ) {
			String iconFileName = type.iconFileName();

			if( iconFileName != null ) {
				return iconFileName;
			}
		}

		return null;
	}

	/**
	 * @return Icelandic name of object associated with the named entity.
	 */
	public static String icelandicName( String entityName ) {
		EntityViewDefinition type = get( entityName );

		if( type != null ) {
			String name = type.icelandicName();

			if( name != null ) {
				return name;
			}
		}

		return null;
	}

	/**
	 * @return The component class used to view the given type of object.
	 */
	public static Class viewComponentClass( Class objectClass ) {
		EntityViewDefinition type = get( objectClass );

		if( type != null ) {
			Class clazz = type.viewComponentClass();

			if( clazz != null ) {
				return clazz;
			}
		}

		return null;
	}

	/**
	 * @return The component class used to edit the given type of object.
	 */
	public static Class editComponentClass( Class objectClass ) {
		EntityViewDefinition type = get( objectClass );

		if( type != null ) {
			Class clazz = type.editComponentClass();

			if( clazz != null ) {
				return clazz;
			}
		}

		return null;
	}

	/**
	 * @return The definition for the given URL prefix.
	 */
	public static EntityViewDefinition definitionForURLPrefix( String urlPrefix ) {
		String entityName = _urlPrefixes.objectForKey( urlPrefix );
		return definitions().objectForKey( entityName );
	}

	/**
	 * @return The class for the given URL prefix.
	 */
	public static Class<? extends ERXGenericRecord> classForURLPrefix( String urlPrefix ) {
		EntityViewDefinition e = definitionForURLPrefix( urlPrefix );

		if( e != null ) {
			return e.objectClass();
		}

		return null;
	}

	public static void invalidateCache() {
		logger.info( "Invalidating the EntityViewDefinition cache" );
		_definitions = null;
	}

	public static NSArray<EntityViewDefinition> typesToShowInList() {
		NSArray<EOSortOrdering> s = new NSArray<EOSortOrdering>( new EOSortOrdering( "icelandicName", EOSortOrdering.CompareCaseInsensitiveAscending ) );

		NSArray<EntityViewDefinition> a = EntityViewDefinition.definitions().allValues();
		NSMutableArray<EntityViewDefinition> results = new NSMutableArray<>();

		for( EntityViewDefinition t : a ) {
			if( t.showInList() ) {
				results.addObject( t );
			}
		}

		return EOSortOrdering.sortedArrayUsingKeyOrderArray( results, new NSArray<>( s ) );
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_name == null) ? 0 : _name.hashCode());
		return result;
	}

	@Override
	public boolean equals( Object obj ) {
		if( this == obj ) {
			return true;
		}
		if( obj == null ) {
			return false;
		}
		if( getClass() != obj.getClass() ) {
			return false;
		}
		EntityViewDefinition other = (EntityViewDefinition)obj;
		if( _name == null ) {
			if( other._name != null ) {
				return false;
			}
		}
		else if( !_name.equals( other._name ) ) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "EntityViewDefinition [_name=" + _name + ", _icelandicName=" + _icelandicName + ", USER_DEFINED_ATTRIBUTE_SORTING=" + USER_DEFINED_ATTRIBUTE_SORTING + ", _attributeViewDefinitions=" + _attributeViewDefinitions + ", _icelandicNamePlural=" + _icelandicNamePlural + ", _text=" + _text + ", _viewComponentClass=" + _viewComponentClass + ", _editComponentClass=" + _editComponentClass + ", _urlPrefix=" + _urlPrefix + ", _iconFileName=" + _iconFileName + ", _showInList=" + _showInList + ", _categoryName=" + _categoryName + ", _attributesToShow=" + _attributesToShow + "]";
	}
}