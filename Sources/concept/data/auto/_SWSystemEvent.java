// DO NOT EDIT.  Make changes to concept.data.SWSystemEvent.java instead.
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
public abstract class _SWSystemEvent extends  ERXGenericRecord {
  public static final String ENTITY_NAME = "SWSystemEvent";

  // Attribute Keys
  public static final ERXKey<NSTimestamp> CREATION_DATE = new ERXKey<NSTimestamp>("creationDate");
  public static final ERXKey<NSTimestamp> MODIFICATION_DATE = new ERXKey<NSTimestamp>("modificationDate");
  public static final ERXKey<String> TEXT = new ERXKey<String>("text");
  public static final ERXKey<String> TYPE = new ERXKey<String>("type");
  // Relationship Keys
  public static final ERXKey<concept.data.SWUser> USER = new ERXKey<concept.data.SWUser>("user");

  // Attributes
  public static final String CREATION_DATE_KEY = CREATION_DATE.key();
  public static final String MODIFICATION_DATE_KEY = MODIFICATION_DATE.key();
  public static final String TEXT_KEY = TEXT.key();
  public static final String TYPE_KEY = TYPE.key();
  // Relationships
  public static final String USER_KEY = USER.key();

  private static Logger LOG = Logger.getLogger(_SWSystemEvent.class);

  public concept.data.SWSystemEvent localInstanceIn(EOEditingContext editingContext) {
    concept.data.SWSystemEvent localInstance = (concept.data.SWSystemEvent)EOUtilities.localInstanceOfObject(editingContext, this);
    if (localInstance == null) {
      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
    }
    return localInstance;
  }

  public NSTimestamp creationDate() {
    return (NSTimestamp) storedValueForKey(_SWSystemEvent.CREATION_DATE_KEY);
  }

  public void setCreationDate(NSTimestamp value) {
    if (_SWSystemEvent.LOG.isDebugEnabled()) {
    	_SWSystemEvent.LOG.debug( "updating creationDate from " + creationDate() + " to " + value);
    }
    takeStoredValueForKey(value, _SWSystemEvent.CREATION_DATE_KEY);
  }

  public NSTimestamp modificationDate() {
    return (NSTimestamp) storedValueForKey(_SWSystemEvent.MODIFICATION_DATE_KEY);
  }

  public void setModificationDate(NSTimestamp value) {
    if (_SWSystemEvent.LOG.isDebugEnabled()) {
    	_SWSystemEvent.LOG.debug( "updating modificationDate from " + modificationDate() + " to " + value);
    }
    takeStoredValueForKey(value, _SWSystemEvent.MODIFICATION_DATE_KEY);
  }

  public String text() {
    return (String) storedValueForKey(_SWSystemEvent.TEXT_KEY);
  }

  public void setText(String value) {
    if (_SWSystemEvent.LOG.isDebugEnabled()) {
    	_SWSystemEvent.LOG.debug( "updating text from " + text() + " to " + value);
    }
    takeStoredValueForKey(value, _SWSystemEvent.TEXT_KEY);
  }

  public String type() {
    return (String) storedValueForKey(_SWSystemEvent.TYPE_KEY);
  }

  public void setType(String value) {
    if (_SWSystemEvent.LOG.isDebugEnabled()) {
    	_SWSystemEvent.LOG.debug( "updating type from " + type() + " to " + value);
    }
    takeStoredValueForKey(value, _SWSystemEvent.TYPE_KEY);
  }

  public concept.data.SWUser user() {
    return (concept.data.SWUser)storedValueForKey(_SWSystemEvent.USER_KEY);
  }
  
  public void setUser(concept.data.SWUser value) {
    takeStoredValueForKey(value, _SWSystemEvent.USER_KEY);
  }

  public void setUserRelationship(concept.data.SWUser value) {
    if (_SWSystemEvent.LOG.isDebugEnabled()) {
      _SWSystemEvent.LOG.debug("updating user from " + user() + " to " + value);
    }
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
    	setUser(value);
    }
    else if (value == null) {
    	concept.data.SWUser oldValue = user();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, _SWSystemEvent.USER_KEY);
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, _SWSystemEvent.USER_KEY);
    }
  }
  

  public static concept.data.SWSystemEvent createSWSystemEvent(EOEditingContext editingContext) {
    concept.data.SWSystemEvent eo = (concept.data.SWSystemEvent) EOUtilities.createAndInsertInstance(editingContext, _SWSystemEvent.ENTITY_NAME);    
    return eo;
  }

  public static ERXFetchSpecification<concept.data.SWSystemEvent> fetchSpec() {
    return new ERXFetchSpecification<concept.data.SWSystemEvent>(_SWSystemEvent.ENTITY_NAME, null, null, false, true, null);
  }

  public static NSArray<concept.data.SWSystemEvent> fetchAllSWSystemEvents(EOEditingContext editingContext) {
    return _SWSystemEvent.fetchAllSWSystemEvents(editingContext, null);
  }

  public static NSArray<concept.data.SWSystemEvent> fetchAllSWSystemEvents(EOEditingContext editingContext, NSArray<EOSortOrdering> sortOrderings) {
    return _SWSystemEvent.fetchSWSystemEvents(editingContext, null, sortOrderings);
  }

  public static NSArray<concept.data.SWSystemEvent> fetchSWSystemEvents(EOEditingContext editingContext, EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings) {
    ERXFetchSpecification<concept.data.SWSystemEvent> fetchSpec = new ERXFetchSpecification<concept.data.SWSystemEvent>(_SWSystemEvent.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray<concept.data.SWSystemEvent> eoObjects = fetchSpec.fetchObjects(editingContext);
    return eoObjects;
  }

  public static concept.data.SWSystemEvent fetchSWSystemEvent(EOEditingContext editingContext, String keyName, Object value) {
    return _SWSystemEvent.fetchSWSystemEvent(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static concept.data.SWSystemEvent fetchSWSystemEvent(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray<concept.data.SWSystemEvent> eoObjects = _SWSystemEvent.fetchSWSystemEvents(editingContext, qualifier, null);
    concept.data.SWSystemEvent eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one SWSystemEvent that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static concept.data.SWSystemEvent fetchRequiredSWSystemEvent(EOEditingContext editingContext, String keyName, Object value) {
    return _SWSystemEvent.fetchRequiredSWSystemEvent(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static concept.data.SWSystemEvent fetchRequiredSWSystemEvent(EOEditingContext editingContext, EOQualifier qualifier) {
    concept.data.SWSystemEvent eoObject = _SWSystemEvent.fetchSWSystemEvent(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no SWSystemEvent that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static concept.data.SWSystemEvent localInstanceIn(EOEditingContext editingContext, concept.data.SWSystemEvent eo) {
    concept.data.SWSystemEvent localInstance = (eo == null) ? null : ERXEOControlUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
