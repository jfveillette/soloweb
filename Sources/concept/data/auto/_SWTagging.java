// DO NOT EDIT.  Make changes to concept.data.SWTagging.java instead.
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
public abstract class _SWTagging extends  ERXGenericRecord {
  public static final String ENTITY_NAME = "SWTagging";

  // Attribute Keys
  public static final ERXKey<NSTimestamp> CREATION_DATE = new ERXKey<NSTimestamp>("creationDate");
  public static final ERXKey<NSTimestamp> MODIFICATION_DATE = new ERXKey<NSTimestamp>("modificationDate");
  public static final ERXKey<String> TARGET_ENTITY_NAME = new ERXKey<String>("targetEntityName");
  public static final ERXKey<String> TARGET_ID = new ERXKey<String>("targetID");
  // Relationship Keys
  public static final ERXKey<concept.data.SWTag> TAG = new ERXKey<concept.data.SWTag>("tag");

  // Attributes
  public static final String CREATION_DATE_KEY = CREATION_DATE.key();
  public static final String MODIFICATION_DATE_KEY = MODIFICATION_DATE.key();
  public static final String TARGET_ENTITY_NAME_KEY = TARGET_ENTITY_NAME.key();
  public static final String TARGET_ID_KEY = TARGET_ID.key();
  // Relationships
  public static final String TAG_KEY = TAG.key();

  private static Logger LOG = Logger.getLogger(_SWTagging.class);

  public concept.data.SWTagging localInstanceIn(EOEditingContext editingContext) {
    concept.data.SWTagging localInstance = (concept.data.SWTagging)EOUtilities.localInstanceOfObject(editingContext, this);
    if (localInstance == null) {
      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
    }
    return localInstance;
  }

  public NSTimestamp creationDate() {
    return (NSTimestamp) storedValueForKey(_SWTagging.CREATION_DATE_KEY);
  }

  public void setCreationDate(NSTimestamp value) {
    if (_SWTagging.LOG.isDebugEnabled()) {
    	_SWTagging.LOG.debug( "updating creationDate from " + creationDate() + " to " + value);
    }
    takeStoredValueForKey(value, _SWTagging.CREATION_DATE_KEY);
  }

  public NSTimestamp modificationDate() {
    return (NSTimestamp) storedValueForKey(_SWTagging.MODIFICATION_DATE_KEY);
  }

  public void setModificationDate(NSTimestamp value) {
    if (_SWTagging.LOG.isDebugEnabled()) {
    	_SWTagging.LOG.debug( "updating modificationDate from " + modificationDate() + " to " + value);
    }
    takeStoredValueForKey(value, _SWTagging.MODIFICATION_DATE_KEY);
  }

  public String targetEntityName() {
    return (String) storedValueForKey(_SWTagging.TARGET_ENTITY_NAME_KEY);
  }

  public void setTargetEntityName(String value) {
    if (_SWTagging.LOG.isDebugEnabled()) {
    	_SWTagging.LOG.debug( "updating targetEntityName from " + targetEntityName() + " to " + value);
    }
    takeStoredValueForKey(value, _SWTagging.TARGET_ENTITY_NAME_KEY);
  }

  public String targetID() {
    return (String) storedValueForKey(_SWTagging.TARGET_ID_KEY);
  }

  public void setTargetID(String value) {
    if (_SWTagging.LOG.isDebugEnabled()) {
    	_SWTagging.LOG.debug( "updating targetID from " + targetID() + " to " + value);
    }
    takeStoredValueForKey(value, _SWTagging.TARGET_ID_KEY);
  }

  public concept.data.SWTag tag() {
    return (concept.data.SWTag)storedValueForKey(_SWTagging.TAG_KEY);
  }
  
  public void setTag(concept.data.SWTag value) {
    takeStoredValueForKey(value, _SWTagging.TAG_KEY);
  }

  public void setTagRelationship(concept.data.SWTag value) {
    if (_SWTagging.LOG.isDebugEnabled()) {
      _SWTagging.LOG.debug("updating tag from " + tag() + " to " + value);
    }
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
    	setTag(value);
    }
    else if (value == null) {
    	concept.data.SWTag oldValue = tag();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, _SWTagging.TAG_KEY);
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, _SWTagging.TAG_KEY);
    }
  }
  

  public static concept.data.SWTagging createSWTagging(EOEditingContext editingContext) {
    concept.data.SWTagging eo = (concept.data.SWTagging) EOUtilities.createAndInsertInstance(editingContext, _SWTagging.ENTITY_NAME);    
    return eo;
  }

  public static ERXFetchSpecification<concept.data.SWTagging> fetchSpec() {
    return new ERXFetchSpecification<concept.data.SWTagging>(_SWTagging.ENTITY_NAME, null, null, false, true, null);
  }

  public static NSArray<concept.data.SWTagging> fetchAllSWTaggings(EOEditingContext editingContext) {
    return _SWTagging.fetchAllSWTaggings(editingContext, null);
  }

  public static NSArray<concept.data.SWTagging> fetchAllSWTaggings(EOEditingContext editingContext, NSArray<EOSortOrdering> sortOrderings) {
    return _SWTagging.fetchSWTaggings(editingContext, null, sortOrderings);
  }

  public static NSArray<concept.data.SWTagging> fetchSWTaggings(EOEditingContext editingContext, EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings) {
    ERXFetchSpecification<concept.data.SWTagging> fetchSpec = new ERXFetchSpecification<concept.data.SWTagging>(_SWTagging.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray<concept.data.SWTagging> eoObjects = fetchSpec.fetchObjects(editingContext);
    return eoObjects;
  }

  public static concept.data.SWTagging fetchSWTagging(EOEditingContext editingContext, String keyName, Object value) {
    return _SWTagging.fetchSWTagging(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static concept.data.SWTagging fetchSWTagging(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray<concept.data.SWTagging> eoObjects = _SWTagging.fetchSWTaggings(editingContext, qualifier, null);
    concept.data.SWTagging eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one SWTagging that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static concept.data.SWTagging fetchRequiredSWTagging(EOEditingContext editingContext, String keyName, Object value) {
    return _SWTagging.fetchRequiredSWTagging(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static concept.data.SWTagging fetchRequiredSWTagging(EOEditingContext editingContext, EOQualifier qualifier) {
    concept.data.SWTagging eoObject = _SWTagging.fetchSWTagging(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no SWTagging that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static concept.data.SWTagging localInstanceIn(EOEditingContext editingContext, concept.data.SWTagging eo) {
    concept.data.SWTagging localInstance = (eo == null) ? null : ERXEOControlUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
