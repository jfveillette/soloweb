// DO NOT EDIT.  Make changes to concept.data.SWFavorite.java instead.
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
public abstract class _SWFavorite extends  ERXGenericRecord {
  public static final String ENTITY_NAME = "SWFavorite";

  // Attribute Keys
  public static final ERXKey<String> TARGET_ENTITY_NAME = new ERXKey<String>("targetEntityName");
  public static final ERXKey<String> TARGET_ID = new ERXKey<String>("targetID");
  public static final ERXKey<String> TEXT = new ERXKey<String>("text");
  // Relationship Keys
  public static final ERXKey<concept.data.SWUser> USER = new ERXKey<concept.data.SWUser>("user");

  // Attributes
  public static final String TARGET_ENTITY_NAME_KEY = TARGET_ENTITY_NAME.key();
  public static final String TARGET_ID_KEY = TARGET_ID.key();
  public static final String TEXT_KEY = TEXT.key();
  // Relationships
  public static final String USER_KEY = USER.key();

  private static Logger LOG = Logger.getLogger(_SWFavorite.class);

  public concept.data.SWFavorite localInstanceIn(EOEditingContext editingContext) {
    concept.data.SWFavorite localInstance = (concept.data.SWFavorite)EOUtilities.localInstanceOfObject(editingContext, this);
    if (localInstance == null) {
      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
    }
    return localInstance;
  }

  public String targetEntityName() {
    return (String) storedValueForKey(_SWFavorite.TARGET_ENTITY_NAME_KEY);
  }

  public void setTargetEntityName(String value) {
    if (_SWFavorite.LOG.isDebugEnabled()) {
    	_SWFavorite.LOG.debug( "updating targetEntityName from " + targetEntityName() + " to " + value);
    }
    takeStoredValueForKey(value, _SWFavorite.TARGET_ENTITY_NAME_KEY);
  }

  public String targetID() {
    return (String) storedValueForKey(_SWFavorite.TARGET_ID_KEY);
  }

  public void setTargetID(String value) {
    if (_SWFavorite.LOG.isDebugEnabled()) {
    	_SWFavorite.LOG.debug( "updating targetID from " + targetID() + " to " + value);
    }
    takeStoredValueForKey(value, _SWFavorite.TARGET_ID_KEY);
  }

  public String text() {
    return (String) storedValueForKey(_SWFavorite.TEXT_KEY);
  }

  public void setText(String value) {
    if (_SWFavorite.LOG.isDebugEnabled()) {
    	_SWFavorite.LOG.debug( "updating text from " + text() + " to " + value);
    }
    takeStoredValueForKey(value, _SWFavorite.TEXT_KEY);
  }

  public concept.data.SWUser user() {
    return (concept.data.SWUser)storedValueForKey(_SWFavorite.USER_KEY);
  }
  
  public void setUser(concept.data.SWUser value) {
    takeStoredValueForKey(value, _SWFavorite.USER_KEY);
  }

  public void setUserRelationship(concept.data.SWUser value) {
    if (_SWFavorite.LOG.isDebugEnabled()) {
      _SWFavorite.LOG.debug("updating user from " + user() + " to " + value);
    }
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
    	setUser(value);
    }
    else if (value == null) {
    	concept.data.SWUser oldValue = user();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, _SWFavorite.USER_KEY);
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, _SWFavorite.USER_KEY);
    }
  }
  

  public static concept.data.SWFavorite createSWFavorite(EOEditingContext editingContext) {
    concept.data.SWFavorite eo = (concept.data.SWFavorite) EOUtilities.createAndInsertInstance(editingContext, _SWFavorite.ENTITY_NAME);    
    return eo;
  }

  public static ERXFetchSpecification<concept.data.SWFavorite> fetchSpec() {
    return new ERXFetchSpecification<concept.data.SWFavorite>(_SWFavorite.ENTITY_NAME, null, null, false, true, null);
  }

  public static NSArray<concept.data.SWFavorite> fetchAllSWFavorites(EOEditingContext editingContext) {
    return _SWFavorite.fetchAllSWFavorites(editingContext, null);
  }

  public static NSArray<concept.data.SWFavorite> fetchAllSWFavorites(EOEditingContext editingContext, NSArray<EOSortOrdering> sortOrderings) {
    return _SWFavorite.fetchSWFavorites(editingContext, null, sortOrderings);
  }

  public static NSArray<concept.data.SWFavorite> fetchSWFavorites(EOEditingContext editingContext, EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings) {
    ERXFetchSpecification<concept.data.SWFavorite> fetchSpec = new ERXFetchSpecification<concept.data.SWFavorite>(_SWFavorite.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray<concept.data.SWFavorite> eoObjects = fetchSpec.fetchObjects(editingContext);
    return eoObjects;
  }

  public static concept.data.SWFavorite fetchSWFavorite(EOEditingContext editingContext, String keyName, Object value) {
    return _SWFavorite.fetchSWFavorite(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static concept.data.SWFavorite fetchSWFavorite(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray<concept.data.SWFavorite> eoObjects = _SWFavorite.fetchSWFavorites(editingContext, qualifier, null);
    concept.data.SWFavorite eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one SWFavorite that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static concept.data.SWFavorite fetchRequiredSWFavorite(EOEditingContext editingContext, String keyName, Object value) {
    return _SWFavorite.fetchRequiredSWFavorite(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static concept.data.SWFavorite fetchRequiredSWFavorite(EOEditingContext editingContext, EOQualifier qualifier) {
    concept.data.SWFavorite eoObject = _SWFavorite.fetchSWFavorite(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no SWFavorite that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static concept.data.SWFavorite localInstanceIn(EOEditingContext editingContext, concept.data.SWFavorite eo) {
    concept.data.SWFavorite localInstance = (eo == null) ? null : ERXEOControlUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
