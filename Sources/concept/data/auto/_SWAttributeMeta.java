// DO NOT EDIT.  Make changes to concept.data.SWAttributeMeta.java instead.
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
public abstract class _SWAttributeMeta extends  ERXGenericRecord {
  public static final String ENTITY_NAME = "SWAttributeMeta";

  // Attribute Keys
  public static final ERXKey<String> ACTIONS = new ERXKey<String>("actions");
  public static final ERXKey<String> COMMENTS = new ERXKey<String>("comments");
  public static final ERXKey<String> EDIT_COMPONENT_NAME = new ERXKey<String>("editComponentName");
  public static final ERXKey<String> FIELD_TYPE = new ERXKey<String>("fieldType");
  public static final ERXKey<String> ICELANDIC_NAME = new ERXKey<String>("icelandicName");
  public static final ERXKey<String> NAME = new ERXKey<String>("name");
  public static final ERXKey<Integer> SHOW_NUMERIC = new ERXKey<Integer>("showNumeric");
  public static final ERXKey<Integer> SORT_ORDER = new ERXKey<Integer>("sortOrder");
  public static final ERXKey<String> TEXT = new ERXKey<String>("text");
  public static final ERXKey<String> VIEW_COMPONENT_NAME = new ERXKey<String>("viewComponentName");
  // Relationship Keys
  public static final ERXKey<concept.data.SWEntityMeta> TABLE = new ERXKey<concept.data.SWEntityMeta>("table");

  // Attributes
  public static final String ACTIONS_KEY = ACTIONS.key();
  public static final String COMMENTS_KEY = COMMENTS.key();
  public static final String EDIT_COMPONENT_NAME_KEY = EDIT_COMPONENT_NAME.key();
  public static final String FIELD_TYPE_KEY = FIELD_TYPE.key();
  public static final String ICELANDIC_NAME_KEY = ICELANDIC_NAME.key();
  public static final String NAME_KEY = NAME.key();
  public static final String SHOW_NUMERIC_KEY = SHOW_NUMERIC.key();
  public static final String SORT_ORDER_KEY = SORT_ORDER.key();
  public static final String TEXT_KEY = TEXT.key();
  public static final String VIEW_COMPONENT_NAME_KEY = VIEW_COMPONENT_NAME.key();
  // Relationships
  public static final String TABLE_KEY = TABLE.key();

  private static Logger LOG = Logger.getLogger(_SWAttributeMeta.class);

  public concept.data.SWAttributeMeta localInstanceIn(EOEditingContext editingContext) {
    concept.data.SWAttributeMeta localInstance = (concept.data.SWAttributeMeta)EOUtilities.localInstanceOfObject(editingContext, this);
    if (localInstance == null) {
      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
    }
    return localInstance;
  }

  public String actions() {
    return (String) storedValueForKey(_SWAttributeMeta.ACTIONS_KEY);
  }

  public void setActions(String value) {
    if (_SWAttributeMeta.LOG.isDebugEnabled()) {
    	_SWAttributeMeta.LOG.debug( "updating actions from " + actions() + " to " + value);
    }
    takeStoredValueForKey(value, _SWAttributeMeta.ACTIONS_KEY);
  }

  public String comments() {
    return (String) storedValueForKey(_SWAttributeMeta.COMMENTS_KEY);
  }

  public void setComments(String value) {
    if (_SWAttributeMeta.LOG.isDebugEnabled()) {
    	_SWAttributeMeta.LOG.debug( "updating comments from " + comments() + " to " + value);
    }
    takeStoredValueForKey(value, _SWAttributeMeta.COMMENTS_KEY);
  }

  public String editComponentName() {
    return (String) storedValueForKey(_SWAttributeMeta.EDIT_COMPONENT_NAME_KEY);
  }

  public void setEditComponentName(String value) {
    if (_SWAttributeMeta.LOG.isDebugEnabled()) {
    	_SWAttributeMeta.LOG.debug( "updating editComponentName from " + editComponentName() + " to " + value);
    }
    takeStoredValueForKey(value, _SWAttributeMeta.EDIT_COMPONENT_NAME_KEY);
  }

  public String fieldType() {
    return (String) storedValueForKey(_SWAttributeMeta.FIELD_TYPE_KEY);
  }

  public void setFieldType(String value) {
    if (_SWAttributeMeta.LOG.isDebugEnabled()) {
    	_SWAttributeMeta.LOG.debug( "updating fieldType from " + fieldType() + " to " + value);
    }
    takeStoredValueForKey(value, _SWAttributeMeta.FIELD_TYPE_KEY);
  }

  public String icelandicName() {
    return (String) storedValueForKey(_SWAttributeMeta.ICELANDIC_NAME_KEY);
  }

  public void setIcelandicName(String value) {
    if (_SWAttributeMeta.LOG.isDebugEnabled()) {
    	_SWAttributeMeta.LOG.debug( "updating icelandicName from " + icelandicName() + " to " + value);
    }
    takeStoredValueForKey(value, _SWAttributeMeta.ICELANDIC_NAME_KEY);
  }

  public String name() {
    return (String) storedValueForKey(_SWAttributeMeta.NAME_KEY);
  }

  public void setName(String value) {
    if (_SWAttributeMeta.LOG.isDebugEnabled()) {
    	_SWAttributeMeta.LOG.debug( "updating name from " + name() + " to " + value);
    }
    takeStoredValueForKey(value, _SWAttributeMeta.NAME_KEY);
  }

  public Integer showNumeric() {
    return (Integer) storedValueForKey(_SWAttributeMeta.SHOW_NUMERIC_KEY);
  }

  public void setShowNumeric(Integer value) {
    if (_SWAttributeMeta.LOG.isDebugEnabled()) {
    	_SWAttributeMeta.LOG.debug( "updating showNumeric from " + showNumeric() + " to " + value);
    }
    takeStoredValueForKey(value, _SWAttributeMeta.SHOW_NUMERIC_KEY);
  }

  public Integer sortOrder() {
    return (Integer) storedValueForKey(_SWAttributeMeta.SORT_ORDER_KEY);
  }

  public void setSortOrder(Integer value) {
    if (_SWAttributeMeta.LOG.isDebugEnabled()) {
    	_SWAttributeMeta.LOG.debug( "updating sortOrder from " + sortOrder() + " to " + value);
    }
    takeStoredValueForKey(value, _SWAttributeMeta.SORT_ORDER_KEY);
  }

  public String text() {
    return (String) storedValueForKey(_SWAttributeMeta.TEXT_KEY);
  }

  public void setText(String value) {
    if (_SWAttributeMeta.LOG.isDebugEnabled()) {
    	_SWAttributeMeta.LOG.debug( "updating text from " + text() + " to " + value);
    }
    takeStoredValueForKey(value, _SWAttributeMeta.TEXT_KEY);
  }

  public String viewComponentName() {
    return (String) storedValueForKey(_SWAttributeMeta.VIEW_COMPONENT_NAME_KEY);
  }

  public void setViewComponentName(String value) {
    if (_SWAttributeMeta.LOG.isDebugEnabled()) {
    	_SWAttributeMeta.LOG.debug( "updating viewComponentName from " + viewComponentName() + " to " + value);
    }
    takeStoredValueForKey(value, _SWAttributeMeta.VIEW_COMPONENT_NAME_KEY);
  }

  public concept.data.SWEntityMeta table() {
    return (concept.data.SWEntityMeta)storedValueForKey(_SWAttributeMeta.TABLE_KEY);
  }
  
  public void setTable(concept.data.SWEntityMeta value) {
    takeStoredValueForKey(value, _SWAttributeMeta.TABLE_KEY);
  }

  public void setTableRelationship(concept.data.SWEntityMeta value) {
    if (_SWAttributeMeta.LOG.isDebugEnabled()) {
      _SWAttributeMeta.LOG.debug("updating table from " + table() + " to " + value);
    }
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
    	setTable(value);
    }
    else if (value == null) {
    	concept.data.SWEntityMeta oldValue = table();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, _SWAttributeMeta.TABLE_KEY);
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, _SWAttributeMeta.TABLE_KEY);
    }
  }
  

  public static concept.data.SWAttributeMeta createSWAttributeMeta(EOEditingContext editingContext) {
    concept.data.SWAttributeMeta eo = (concept.data.SWAttributeMeta) EOUtilities.createAndInsertInstance(editingContext, _SWAttributeMeta.ENTITY_NAME);    
    return eo;
  }

  public static ERXFetchSpecification<concept.data.SWAttributeMeta> fetchSpec() {
    return new ERXFetchSpecification<concept.data.SWAttributeMeta>(_SWAttributeMeta.ENTITY_NAME, null, null, false, true, null);
  }

  public static NSArray<concept.data.SWAttributeMeta> fetchAllSWAttributeMetas(EOEditingContext editingContext) {
    return _SWAttributeMeta.fetchAllSWAttributeMetas(editingContext, null);
  }

  public static NSArray<concept.data.SWAttributeMeta> fetchAllSWAttributeMetas(EOEditingContext editingContext, NSArray<EOSortOrdering> sortOrderings) {
    return _SWAttributeMeta.fetchSWAttributeMetas(editingContext, null, sortOrderings);
  }

  public static NSArray<concept.data.SWAttributeMeta> fetchSWAttributeMetas(EOEditingContext editingContext, EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings) {
    ERXFetchSpecification<concept.data.SWAttributeMeta> fetchSpec = new ERXFetchSpecification<concept.data.SWAttributeMeta>(_SWAttributeMeta.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray<concept.data.SWAttributeMeta> eoObjects = fetchSpec.fetchObjects(editingContext);
    return eoObjects;
  }

  public static concept.data.SWAttributeMeta fetchSWAttributeMeta(EOEditingContext editingContext, String keyName, Object value) {
    return _SWAttributeMeta.fetchSWAttributeMeta(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static concept.data.SWAttributeMeta fetchSWAttributeMeta(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray<concept.data.SWAttributeMeta> eoObjects = _SWAttributeMeta.fetchSWAttributeMetas(editingContext, qualifier, null);
    concept.data.SWAttributeMeta eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one SWAttributeMeta that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static concept.data.SWAttributeMeta fetchRequiredSWAttributeMeta(EOEditingContext editingContext, String keyName, Object value) {
    return _SWAttributeMeta.fetchRequiredSWAttributeMeta(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static concept.data.SWAttributeMeta fetchRequiredSWAttributeMeta(EOEditingContext editingContext, EOQualifier qualifier) {
    concept.data.SWAttributeMeta eoObject = _SWAttributeMeta.fetchSWAttributeMeta(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no SWAttributeMeta that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static concept.data.SWAttributeMeta localInstanceIn(EOEditingContext editingContext, concept.data.SWAttributeMeta eo) {
    concept.data.SWAttributeMeta localInstance = (eo == null) ? null : ERXEOControlUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
