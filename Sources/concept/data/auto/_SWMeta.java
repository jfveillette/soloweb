// DO NOT EDIT.  Make changes to concept.data.SWMeta.java instead.
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
public abstract class _SWMeta extends  ERXGenericRecord {
  public static final String ENTITY_NAME = "SWMeta";

  // Attribute Keys
  public static final ERXKey<String> NAME = new ERXKey<String>("name");
  public static final ERXKey<String> TARGET_ENTITY_NAME = new ERXKey<String>("targetEntityName");
  public static final ERXKey<String> TARGET_ID = new ERXKey<String>("targetID");
  public static final ERXKey<String> TEXT = new ERXKey<String>("text");
  // Relationship Keys

  // Attributes
  public static final String NAME_KEY = NAME.key();
  public static final String TARGET_ENTITY_NAME_KEY = TARGET_ENTITY_NAME.key();
  public static final String TARGET_ID_KEY = TARGET_ID.key();
  public static final String TEXT_KEY = TEXT.key();
  // Relationships

  private static Logger LOG = Logger.getLogger(_SWMeta.class);

  public concept.data.SWMeta localInstanceIn(EOEditingContext editingContext) {
    concept.data.SWMeta localInstance = (concept.data.SWMeta)EOUtilities.localInstanceOfObject(editingContext, this);
    if (localInstance == null) {
      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
    }
    return localInstance;
  }

  public String name() {
    return (String) storedValueForKey(_SWMeta.NAME_KEY);
  }

  public void setName(String value) {
    if (_SWMeta.LOG.isDebugEnabled()) {
    	_SWMeta.LOG.debug( "updating name from " + name() + " to " + value);
    }
    takeStoredValueForKey(value, _SWMeta.NAME_KEY);
  }

  public String targetEntityName() {
    return (String) storedValueForKey(_SWMeta.TARGET_ENTITY_NAME_KEY);
  }

  public void setTargetEntityName(String value) {
    if (_SWMeta.LOG.isDebugEnabled()) {
    	_SWMeta.LOG.debug( "updating targetEntityName from " + targetEntityName() + " to " + value);
    }
    takeStoredValueForKey(value, _SWMeta.TARGET_ENTITY_NAME_KEY);
  }

  public String targetID() {
    return (String) storedValueForKey(_SWMeta.TARGET_ID_KEY);
  }

  public void setTargetID(String value) {
    if (_SWMeta.LOG.isDebugEnabled()) {
    	_SWMeta.LOG.debug( "updating targetID from " + targetID() + " to " + value);
    }
    takeStoredValueForKey(value, _SWMeta.TARGET_ID_KEY);
  }

  public String text() {
    return (String) storedValueForKey(_SWMeta.TEXT_KEY);
  }

  public void setText(String value) {
    if (_SWMeta.LOG.isDebugEnabled()) {
    	_SWMeta.LOG.debug( "updating text from " + text() + " to " + value);
    }
    takeStoredValueForKey(value, _SWMeta.TEXT_KEY);
  }


  public static concept.data.SWMeta createSWMeta(EOEditingContext editingContext) {
    concept.data.SWMeta eo = (concept.data.SWMeta) EOUtilities.createAndInsertInstance(editingContext, _SWMeta.ENTITY_NAME);    
    return eo;
  }

  public static ERXFetchSpecification<concept.data.SWMeta> fetchSpec() {
    return new ERXFetchSpecification<concept.data.SWMeta>(_SWMeta.ENTITY_NAME, null, null, false, true, null);
  }

  public static NSArray<concept.data.SWMeta> fetchAllSWMetas(EOEditingContext editingContext) {
    return _SWMeta.fetchAllSWMetas(editingContext, null);
  }

  public static NSArray<concept.data.SWMeta> fetchAllSWMetas(EOEditingContext editingContext, NSArray<EOSortOrdering> sortOrderings) {
    return _SWMeta.fetchSWMetas(editingContext, null, sortOrderings);
  }

  public static NSArray<concept.data.SWMeta> fetchSWMetas(EOEditingContext editingContext, EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings) {
    ERXFetchSpecification<concept.data.SWMeta> fetchSpec = new ERXFetchSpecification<concept.data.SWMeta>(_SWMeta.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray<concept.data.SWMeta> eoObjects = fetchSpec.fetchObjects(editingContext);
    return eoObjects;
  }

  public static concept.data.SWMeta fetchSWMeta(EOEditingContext editingContext, String keyName, Object value) {
    return _SWMeta.fetchSWMeta(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static concept.data.SWMeta fetchSWMeta(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray<concept.data.SWMeta> eoObjects = _SWMeta.fetchSWMetas(editingContext, qualifier, null);
    concept.data.SWMeta eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one SWMeta that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static concept.data.SWMeta fetchRequiredSWMeta(EOEditingContext editingContext, String keyName, Object value) {
    return _SWMeta.fetchRequiredSWMeta(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static concept.data.SWMeta fetchRequiredSWMeta(EOEditingContext editingContext, EOQualifier qualifier) {
    concept.data.SWMeta eoObject = _SWMeta.fetchSWMeta(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no SWMeta that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static concept.data.SWMeta localInstanceIn(EOEditingContext editingContext, concept.data.SWMeta eo) {
    concept.data.SWMeta localInstance = (eo == null) ? null : ERXEOControlUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
