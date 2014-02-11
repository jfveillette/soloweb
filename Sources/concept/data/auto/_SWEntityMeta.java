// DO NOT EDIT.  Make changes to concept.data.SWEntityMeta.java instead.
package concept.data.auto;

import com.webobjects.eoaccess.*;
import com.webobjects.eocontrol.*;
import com.webobjects.foundation.*;
import java.math.*;
import java.util.*;
import org.apache.log4j.Logger;

import er.extensions.eof.*;
import er.extensions.foundation.*;

@SuppressWarnings("all")
public abstract class _SWEntityMeta extends  ERXGenericRecord {
  public static final String ENTITY_NAME = "SWEntityMeta";

  // Attribute Keys
  public static final ERXKey<String> CATEGORY_NAME = new ERXKey<String>("categoryName");
  public static final ERXKey<String> EDIT_COMPONENT_NAME = new ERXKey<String>("editComponentName");
  public static final ERXKey<String> ICELANDIC_NAME = new ERXKey<String>("icelandicName");
  public static final ERXKey<String> ICELANDIC_NAME_PLURAL = new ERXKey<String>("icelandicNamePlural");
  public static final ERXKey<String> ICON_FILE_NAME = new ERXKey<String>("iconFileName");
  public static final ERXKey<String> NAME = new ERXKey<String>("name");
  public static final ERXKey<Integer> SHOW_IN_LISTS = new ERXKey<Integer>("showInLists");
  public static final ERXKey<String> TEXT = new ERXKey<String>("text");
  public static final ERXKey<String> URL_PREFIX = new ERXKey<String>("urlPrefix");
  public static final ERXKey<String> VIEW_COMPONENT_NAME = new ERXKey<String>("viewComponentName");
  // Relationship Keys
  public static final ERXKey<concept.data.SWAttributeMeta> COLUMNS = new ERXKey<concept.data.SWAttributeMeta>("columns");

  // Attributes
  public static final String CATEGORY_NAME_KEY = CATEGORY_NAME.key();
  public static final String EDIT_COMPONENT_NAME_KEY = EDIT_COMPONENT_NAME.key();
  public static final String ICELANDIC_NAME_KEY = ICELANDIC_NAME.key();
  public static final String ICELANDIC_NAME_PLURAL_KEY = ICELANDIC_NAME_PLURAL.key();
  public static final String ICON_FILE_NAME_KEY = ICON_FILE_NAME.key();
  public static final String NAME_KEY = NAME.key();
  public static final String SHOW_IN_LISTS_KEY = SHOW_IN_LISTS.key();
  public static final String TEXT_KEY = TEXT.key();
  public static final String URL_PREFIX_KEY = URL_PREFIX.key();
  public static final String VIEW_COMPONENT_NAME_KEY = VIEW_COMPONENT_NAME.key();
  // Relationships
  public static final String COLUMNS_KEY = COLUMNS.key();

  private static Logger LOG = Logger.getLogger(_SWEntityMeta.class);

  public concept.data.SWEntityMeta localInstanceIn(EOEditingContext editingContext) {
    concept.data.SWEntityMeta localInstance = (concept.data.SWEntityMeta)EOUtilities.localInstanceOfObject(editingContext, this);
    if (localInstance == null) {
      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
    }
    return localInstance;
  }

  public String categoryName() {
    return (String) storedValueForKey(_SWEntityMeta.CATEGORY_NAME_KEY);
  }

  public void setCategoryName(String value) {
    if (_SWEntityMeta.LOG.isDebugEnabled()) {
    	_SWEntityMeta.LOG.debug( "updating categoryName from " + categoryName() + " to " + value);
    }
    takeStoredValueForKey(value, _SWEntityMeta.CATEGORY_NAME_KEY);
  }

  public String editComponentName() {
    return (String) storedValueForKey(_SWEntityMeta.EDIT_COMPONENT_NAME_KEY);
  }

  public void setEditComponentName(String value) {
    if (_SWEntityMeta.LOG.isDebugEnabled()) {
    	_SWEntityMeta.LOG.debug( "updating editComponentName from " + editComponentName() + " to " + value);
    }
    takeStoredValueForKey(value, _SWEntityMeta.EDIT_COMPONENT_NAME_KEY);
  }

  public String icelandicName() {
    return (String) storedValueForKey(_SWEntityMeta.ICELANDIC_NAME_KEY);
  }

  public void setIcelandicName(String value) {
    if (_SWEntityMeta.LOG.isDebugEnabled()) {
    	_SWEntityMeta.LOG.debug( "updating icelandicName from " + icelandicName() + " to " + value);
    }
    takeStoredValueForKey(value, _SWEntityMeta.ICELANDIC_NAME_KEY);
  }

  public String icelandicNamePlural() {
    return (String) storedValueForKey(_SWEntityMeta.ICELANDIC_NAME_PLURAL_KEY);
  }

  public void setIcelandicNamePlural(String value) {
    if (_SWEntityMeta.LOG.isDebugEnabled()) {
    	_SWEntityMeta.LOG.debug( "updating icelandicNamePlural from " + icelandicNamePlural() + " to " + value);
    }
    takeStoredValueForKey(value, _SWEntityMeta.ICELANDIC_NAME_PLURAL_KEY);
  }

  public String iconFileName() {
    return (String) storedValueForKey(_SWEntityMeta.ICON_FILE_NAME_KEY);
  }

  public void setIconFileName(String value) {
    if (_SWEntityMeta.LOG.isDebugEnabled()) {
    	_SWEntityMeta.LOG.debug( "updating iconFileName from " + iconFileName() + " to " + value);
    }
    takeStoredValueForKey(value, _SWEntityMeta.ICON_FILE_NAME_KEY);
  }

  public String name() {
    return (String) storedValueForKey(_SWEntityMeta.NAME_KEY);
  }

  public void setName(String value) {
    if (_SWEntityMeta.LOG.isDebugEnabled()) {
    	_SWEntityMeta.LOG.debug( "updating name from " + name() + " to " + value);
    }
    takeStoredValueForKey(value, _SWEntityMeta.NAME_KEY);
  }

  public Integer showInLists() {
    return (Integer) storedValueForKey(_SWEntityMeta.SHOW_IN_LISTS_KEY);
  }

  public void setShowInLists(Integer value) {
    if (_SWEntityMeta.LOG.isDebugEnabled()) {
    	_SWEntityMeta.LOG.debug( "updating showInLists from " + showInLists() + " to " + value);
    }
    takeStoredValueForKey(value, _SWEntityMeta.SHOW_IN_LISTS_KEY);
  }

  public String text() {
    return (String) storedValueForKey(_SWEntityMeta.TEXT_KEY);
  }

  public void setText(String value) {
    if (_SWEntityMeta.LOG.isDebugEnabled()) {
    	_SWEntityMeta.LOG.debug( "updating text from " + text() + " to " + value);
    }
    takeStoredValueForKey(value, _SWEntityMeta.TEXT_KEY);
  }

  public String urlPrefix() {
    return (String) storedValueForKey(_SWEntityMeta.URL_PREFIX_KEY);
  }

  public void setUrlPrefix(String value) {
    if (_SWEntityMeta.LOG.isDebugEnabled()) {
    	_SWEntityMeta.LOG.debug( "updating urlPrefix from " + urlPrefix() + " to " + value);
    }
    takeStoredValueForKey(value, _SWEntityMeta.URL_PREFIX_KEY);
  }

  public String viewComponentName() {
    return (String) storedValueForKey(_SWEntityMeta.VIEW_COMPONENT_NAME_KEY);
  }

  public void setViewComponentName(String value) {
    if (_SWEntityMeta.LOG.isDebugEnabled()) {
    	_SWEntityMeta.LOG.debug( "updating viewComponentName from " + viewComponentName() + " to " + value);
    }
    takeStoredValueForKey(value, _SWEntityMeta.VIEW_COMPONENT_NAME_KEY);
  }

  public NSArray<concept.data.SWAttributeMeta> columns() {
    return (NSArray<concept.data.SWAttributeMeta>)storedValueForKey(_SWEntityMeta.COLUMNS_KEY);
  }

  public NSArray<concept.data.SWAttributeMeta> columns(EOQualifier qualifier) {
    return columns(qualifier, null, false);
  }

  public NSArray<concept.data.SWAttributeMeta> columns(EOQualifier qualifier, boolean fetch) {
    return columns(qualifier, null, fetch);
  }

  public NSArray<concept.data.SWAttributeMeta> columns(EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings, boolean fetch) {
    NSArray<concept.data.SWAttributeMeta> results;
    if (fetch) {
      EOQualifier fullQualifier;
      EOQualifier inverseQualifier = new EOKeyValueQualifier(concept.data.SWAttributeMeta.TABLE_KEY, EOQualifier.QualifierOperatorEqual, this);
    	
      if (qualifier == null) {
        fullQualifier = inverseQualifier;
      }
      else {
        NSMutableArray<EOQualifier> qualifiers = new NSMutableArray<EOQualifier>();
        qualifiers.addObject(qualifier);
        qualifiers.addObject(inverseQualifier);
        fullQualifier = new EOAndQualifier(qualifiers);
      }

      results = concept.data.SWAttributeMeta.fetchSWAttributeMetas(editingContext(), fullQualifier, sortOrderings);
    }
    else {
      results = columns();
      if (qualifier != null) {
        results = (NSArray<concept.data.SWAttributeMeta>)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray<concept.data.SWAttributeMeta>)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    }
    return results;
  }
  
  public void addToColumns(concept.data.SWAttributeMeta object) {
    includeObjectIntoPropertyWithKey(object, _SWEntityMeta.COLUMNS_KEY);
  }

  public void removeFromColumns(concept.data.SWAttributeMeta object) {
    excludeObjectFromPropertyWithKey(object, _SWEntityMeta.COLUMNS_KEY);
  }

  public void addToColumnsRelationship(concept.data.SWAttributeMeta object) {
    if (_SWEntityMeta.LOG.isDebugEnabled()) {
      _SWEntityMeta.LOG.debug("adding " + object + " to columns relationship");
    }
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
    	addToColumns(object);
    }
    else {
    	addObjectToBothSidesOfRelationshipWithKey(object, _SWEntityMeta.COLUMNS_KEY);
    }
  }

  public void removeFromColumnsRelationship(concept.data.SWAttributeMeta object) {
    if (_SWEntityMeta.LOG.isDebugEnabled()) {
      _SWEntityMeta.LOG.debug("removing " + object + " from columns relationship");
    }
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
    	removeFromColumns(object);
    }
    else {
    	removeObjectFromBothSidesOfRelationshipWithKey(object, _SWEntityMeta.COLUMNS_KEY);
    }
  }

  public concept.data.SWAttributeMeta createColumnsRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName( concept.data.SWAttributeMeta.ENTITY_NAME );
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, _SWEntityMeta.COLUMNS_KEY);
    return (concept.data.SWAttributeMeta) eo;
  }

  public void deleteColumnsRelationship(concept.data.SWAttributeMeta object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, _SWEntityMeta.COLUMNS_KEY);
    editingContext().deleteObject(object);
  }

  public void deleteAllColumnsRelationships() {
    Enumeration<concept.data.SWAttributeMeta> objects = columns().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteColumnsRelationship(objects.nextElement());
    }
  }


  public static concept.data.SWEntityMeta createSWEntityMeta(EOEditingContext editingContext) {
    concept.data.SWEntityMeta eo = (concept.data.SWEntityMeta) EOUtilities.createAndInsertInstance(editingContext, _SWEntityMeta.ENTITY_NAME);    
    return eo;
  }

  public static ERXFetchSpecification<concept.data.SWEntityMeta> fetchSpec() {
    return new ERXFetchSpecification<concept.data.SWEntityMeta>(_SWEntityMeta.ENTITY_NAME, null, null, false, true, null);
  }

  public static NSArray<concept.data.SWEntityMeta> fetchAllSWEntityMetas(EOEditingContext editingContext) {
    return _SWEntityMeta.fetchAllSWEntityMetas(editingContext, null);
  }

  public static NSArray<concept.data.SWEntityMeta> fetchAllSWEntityMetas(EOEditingContext editingContext, NSArray<EOSortOrdering> sortOrderings) {
    return _SWEntityMeta.fetchSWEntityMetas(editingContext, null, sortOrderings);
  }

  public static NSArray<concept.data.SWEntityMeta> fetchSWEntityMetas(EOEditingContext editingContext, EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings) {
    ERXFetchSpecification<concept.data.SWEntityMeta> fetchSpec = new ERXFetchSpecification<concept.data.SWEntityMeta>(_SWEntityMeta.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray<concept.data.SWEntityMeta> eoObjects = fetchSpec.fetchObjects(editingContext);
    return eoObjects;
  }

  public static concept.data.SWEntityMeta fetchSWEntityMeta(EOEditingContext editingContext, String keyName, Object value) {
    return _SWEntityMeta.fetchSWEntityMeta(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static concept.data.SWEntityMeta fetchSWEntityMeta(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray<concept.data.SWEntityMeta> eoObjects = _SWEntityMeta.fetchSWEntityMetas(editingContext, qualifier, null);
    concept.data.SWEntityMeta eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one SWEntityMeta that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static concept.data.SWEntityMeta fetchRequiredSWEntityMeta(EOEditingContext editingContext, String keyName, Object value) {
    return _SWEntityMeta.fetchRequiredSWEntityMeta(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static concept.data.SWEntityMeta fetchRequiredSWEntityMeta(EOEditingContext editingContext, EOQualifier qualifier) {
    concept.data.SWEntityMeta eoObject = _SWEntityMeta.fetchSWEntityMeta(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no SWEntityMeta that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static concept.data.SWEntityMeta localInstanceIn(EOEditingContext editingContext, concept.data.SWEntityMeta eo) {
    concept.data.SWEntityMeta localInstance = (eo == null) ? null : ERXEOControlUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
