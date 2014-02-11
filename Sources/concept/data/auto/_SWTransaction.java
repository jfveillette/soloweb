// DO NOT EDIT.  Make changes to concept.data.SWTransaction.java instead.
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
public abstract class _SWTransaction extends  ERXGenericRecord {
  public static final String ENTITY_NAME = "SWTransaction";

  // Attribute Keys
  public static final ERXKey<String> ACTION = new ERXKey<String>("action");
  public static final ERXKey<String> AFTER = new ERXKey<String>("after");
  public static final ERXKey<String> BEFORE = new ERXKey<String>("before");
  public static final ERXKey<NSTimestamp> DATE = new ERXKey<NSTimestamp>("date");
  public static final ERXKey<String> TARGET_ENTITY_NAME = new ERXKey<String>("targetEntityName");
  public static final ERXKey<String> TARGET_ID = new ERXKey<String>("targetID");
  public static final ERXKey<Integer> USER_ID = new ERXKey<Integer>("userID");
  // Relationship Keys
  public static final ERXKey<concept.data.SWUser> USER = new ERXKey<concept.data.SWUser>("user");

  // Attributes
  public static final String ACTION_KEY = ACTION.key();
  public static final String AFTER_KEY = AFTER.key();
  public static final String BEFORE_KEY = BEFORE.key();
  public static final String DATE_KEY = DATE.key();
  public static final String TARGET_ENTITY_NAME_KEY = TARGET_ENTITY_NAME.key();
  public static final String TARGET_ID_KEY = TARGET_ID.key();
  public static final String USER_ID_KEY = USER_ID.key();
  // Relationships
  public static final String USER_KEY = USER.key();

  private static Logger LOG = Logger.getLogger(_SWTransaction.class);

  public concept.data.SWTransaction localInstanceIn(EOEditingContext editingContext) {
    concept.data.SWTransaction localInstance = (concept.data.SWTransaction)EOUtilities.localInstanceOfObject(editingContext, this);
    if (localInstance == null) {
      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
    }
    return localInstance;
  }

  public String action() {
    return (String) storedValueForKey(_SWTransaction.ACTION_KEY);
  }

  public void setAction(String value) {
    if (_SWTransaction.LOG.isDebugEnabled()) {
    	_SWTransaction.LOG.debug( "updating action from " + action() + " to " + value);
    }
    takeStoredValueForKey(value, _SWTransaction.ACTION_KEY);
  }

  public String after() {
    return (String) storedValueForKey(_SWTransaction.AFTER_KEY);
  }

  public void setAfter(String value) {
    if (_SWTransaction.LOG.isDebugEnabled()) {
    	_SWTransaction.LOG.debug( "updating after from " + after() + " to " + value);
    }
    takeStoredValueForKey(value, _SWTransaction.AFTER_KEY);
  }

  public String before() {
    return (String) storedValueForKey(_SWTransaction.BEFORE_KEY);
  }

  public void setBefore(String value) {
    if (_SWTransaction.LOG.isDebugEnabled()) {
    	_SWTransaction.LOG.debug( "updating before from " + before() + " to " + value);
    }
    takeStoredValueForKey(value, _SWTransaction.BEFORE_KEY);
  }

  public NSTimestamp date() {
    return (NSTimestamp) storedValueForKey(_SWTransaction.DATE_KEY);
  }

  public void setDate(NSTimestamp value) {
    if (_SWTransaction.LOG.isDebugEnabled()) {
    	_SWTransaction.LOG.debug( "updating date from " + date() + " to " + value);
    }
    takeStoredValueForKey(value, _SWTransaction.DATE_KEY);
  }

  public String targetEntityName() {
    return (String) storedValueForKey(_SWTransaction.TARGET_ENTITY_NAME_KEY);
  }

  public void setTargetEntityName(String value) {
    if (_SWTransaction.LOG.isDebugEnabled()) {
    	_SWTransaction.LOG.debug( "updating targetEntityName from " + targetEntityName() + " to " + value);
    }
    takeStoredValueForKey(value, _SWTransaction.TARGET_ENTITY_NAME_KEY);
  }

  public String targetID() {
    return (String) storedValueForKey(_SWTransaction.TARGET_ID_KEY);
  }

  public void setTargetID(String value) {
    if (_SWTransaction.LOG.isDebugEnabled()) {
    	_SWTransaction.LOG.debug( "updating targetID from " + targetID() + " to " + value);
    }
    takeStoredValueForKey(value, _SWTransaction.TARGET_ID_KEY);
  }

  public Integer userID() {
    return (Integer) storedValueForKey(_SWTransaction.USER_ID_KEY);
  }

  public void setUserID(Integer value) {
    if (_SWTransaction.LOG.isDebugEnabled()) {
    	_SWTransaction.LOG.debug( "updating userID from " + userID() + " to " + value);
    }
    takeStoredValueForKey(value, _SWTransaction.USER_ID_KEY);
  }

  public concept.data.SWUser user() {
    return (concept.data.SWUser)storedValueForKey(_SWTransaction.USER_KEY);
  }
  
  public void setUser(concept.data.SWUser value) {
    takeStoredValueForKey(value, _SWTransaction.USER_KEY);
  }

  public void setUserRelationship(concept.data.SWUser value) {
    if (_SWTransaction.LOG.isDebugEnabled()) {
      _SWTransaction.LOG.debug("updating user from " + user() + " to " + value);
    }
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
    	setUser(value);
    }
    else if (value == null) {
    	concept.data.SWUser oldValue = user();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, _SWTransaction.USER_KEY);
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, _SWTransaction.USER_KEY);
    }
  }
  

  public static concept.data.SWTransaction createSWTransaction(EOEditingContext editingContext) {
    concept.data.SWTransaction eo = (concept.data.SWTransaction) EOUtilities.createAndInsertInstance(editingContext, _SWTransaction.ENTITY_NAME);    
    return eo;
  }

  public static ERXFetchSpecification<concept.data.SWTransaction> fetchSpec() {
    return new ERXFetchSpecification<concept.data.SWTransaction>(_SWTransaction.ENTITY_NAME, null, null, false, true, null);
  }

  public static NSArray<concept.data.SWTransaction> fetchAllSWTransactions(EOEditingContext editingContext) {
    return _SWTransaction.fetchAllSWTransactions(editingContext, null);
  }

  public static NSArray<concept.data.SWTransaction> fetchAllSWTransactions(EOEditingContext editingContext, NSArray<EOSortOrdering> sortOrderings) {
    return _SWTransaction.fetchSWTransactions(editingContext, null, sortOrderings);
  }

  public static NSArray<concept.data.SWTransaction> fetchSWTransactions(EOEditingContext editingContext, EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings) {
    ERXFetchSpecification<concept.data.SWTransaction> fetchSpec = new ERXFetchSpecification<concept.data.SWTransaction>(_SWTransaction.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray<concept.data.SWTransaction> eoObjects = fetchSpec.fetchObjects(editingContext);
    return eoObjects;
  }

  public static concept.data.SWTransaction fetchSWTransaction(EOEditingContext editingContext, String keyName, Object value) {
    return _SWTransaction.fetchSWTransaction(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static concept.data.SWTransaction fetchSWTransaction(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray<concept.data.SWTransaction> eoObjects = _SWTransaction.fetchSWTransactions(editingContext, qualifier, null);
    concept.data.SWTransaction eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one SWTransaction that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static concept.data.SWTransaction fetchRequiredSWTransaction(EOEditingContext editingContext, String keyName, Object value) {
    return _SWTransaction.fetchRequiredSWTransaction(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static concept.data.SWTransaction fetchRequiredSWTransaction(EOEditingContext editingContext, EOQualifier qualifier) {
    concept.data.SWTransaction eoObject = _SWTransaction.fetchSWTransaction(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no SWTransaction that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static concept.data.SWTransaction localInstanceIn(EOEditingContext editingContext, concept.data.SWTransaction eo) {
    concept.data.SWTransaction localInstance = (eo == null) ? null : ERXEOControlUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
