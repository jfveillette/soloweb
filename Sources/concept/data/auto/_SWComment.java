// DO NOT EDIT.  Make changes to concept.data.SWComment.java instead.
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
public abstract class _SWComment extends  ERXGenericRecord {
  public static final String ENTITY_NAME = "SWComment";

  // Attribute Keys
  public static final ERXKey<NSTimestamp> DATE = new ERXKey<NSTimestamp>("date");
  public static final ERXKey<String> EMAIL_ADDRESS = new ERXKey<String>("emailAddress");
  public static final ERXKey<String> IP_ADDRESS = new ERXKey<String>("ipAddress");
  public static final ERXKey<String> NAME = new ERXKey<String>("name");
  public static final ERXKey<Integer> NOTIFY_ON_NEW_COMMENTS = new ERXKey<Integer>("notifyOnNewComments");
  public static final ERXKey<String> TARGET_ENTITY_NAME = new ERXKey<String>("targetEntityName");
  public static final ERXKey<String> TARGET_ID = new ERXKey<String>("targetID");
  public static final ERXKey<String> TEXT = new ERXKey<String>("text");
  public static final ERXKey<String> URL = new ERXKey<String>("url");
  public static final ERXKey<String> USER_AGENT = new ERXKey<String>("userAgent");
  // Relationship Keys
  public static final ERXKey<concept.data.SWUser> USER = new ERXKey<concept.data.SWUser>("user");

  // Attributes
  public static final String DATE_KEY = DATE.key();
  public static final String EMAIL_ADDRESS_KEY = EMAIL_ADDRESS.key();
  public static final String IP_ADDRESS_KEY = IP_ADDRESS.key();
  public static final String NAME_KEY = NAME.key();
  public static final String NOTIFY_ON_NEW_COMMENTS_KEY = NOTIFY_ON_NEW_COMMENTS.key();
  public static final String TARGET_ENTITY_NAME_KEY = TARGET_ENTITY_NAME.key();
  public static final String TARGET_ID_KEY = TARGET_ID.key();
  public static final String TEXT_KEY = TEXT.key();
  public static final String URL_KEY = URL.key();
  public static final String USER_AGENT_KEY = USER_AGENT.key();
  // Relationships
  public static final String USER_KEY = USER.key();

  private static Logger LOG = Logger.getLogger(_SWComment.class);

  public concept.data.SWComment localInstanceIn(EOEditingContext editingContext) {
    concept.data.SWComment localInstance = (concept.data.SWComment)EOUtilities.localInstanceOfObject(editingContext, this);
    if (localInstance == null) {
      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
    }
    return localInstance;
  }

  public NSTimestamp date() {
    return (NSTimestamp) storedValueForKey(_SWComment.DATE_KEY);
  }

  public void setDate(NSTimestamp value) {
    if (_SWComment.LOG.isDebugEnabled()) {
    	_SWComment.LOG.debug( "updating date from " + date() + " to " + value);
    }
    takeStoredValueForKey(value, _SWComment.DATE_KEY);
  }

  public String emailAddress() {
    return (String) storedValueForKey(_SWComment.EMAIL_ADDRESS_KEY);
  }

  public void setEmailAddress(String value) {
    if (_SWComment.LOG.isDebugEnabled()) {
    	_SWComment.LOG.debug( "updating emailAddress from " + emailAddress() + " to " + value);
    }
    takeStoredValueForKey(value, _SWComment.EMAIL_ADDRESS_KEY);
  }

  public String ipAddress() {
    return (String) storedValueForKey(_SWComment.IP_ADDRESS_KEY);
  }

  public void setIpAddress(String value) {
    if (_SWComment.LOG.isDebugEnabled()) {
    	_SWComment.LOG.debug( "updating ipAddress from " + ipAddress() + " to " + value);
    }
    takeStoredValueForKey(value, _SWComment.IP_ADDRESS_KEY);
  }

  public String name() {
    return (String) storedValueForKey(_SWComment.NAME_KEY);
  }

  public void setName(String value) {
    if (_SWComment.LOG.isDebugEnabled()) {
    	_SWComment.LOG.debug( "updating name from " + name() + " to " + value);
    }
    takeStoredValueForKey(value, _SWComment.NAME_KEY);
  }

  public Integer notifyOnNewComments() {
    return (Integer) storedValueForKey(_SWComment.NOTIFY_ON_NEW_COMMENTS_KEY);
  }

  public void setNotifyOnNewComments(Integer value) {
    if (_SWComment.LOG.isDebugEnabled()) {
    	_SWComment.LOG.debug( "updating notifyOnNewComments from " + notifyOnNewComments() + " to " + value);
    }
    takeStoredValueForKey(value, _SWComment.NOTIFY_ON_NEW_COMMENTS_KEY);
  }

  public String targetEntityName() {
    return (String) storedValueForKey(_SWComment.TARGET_ENTITY_NAME_KEY);
  }

  public void setTargetEntityName(String value) {
    if (_SWComment.LOG.isDebugEnabled()) {
    	_SWComment.LOG.debug( "updating targetEntityName from " + targetEntityName() + " to " + value);
    }
    takeStoredValueForKey(value, _SWComment.TARGET_ENTITY_NAME_KEY);
  }

  public String targetID() {
    return (String) storedValueForKey(_SWComment.TARGET_ID_KEY);
  }

  public void setTargetID(String value) {
    if (_SWComment.LOG.isDebugEnabled()) {
    	_SWComment.LOG.debug( "updating targetID from " + targetID() + " to " + value);
    }
    takeStoredValueForKey(value, _SWComment.TARGET_ID_KEY);
  }

  public String text() {
    return (String) storedValueForKey(_SWComment.TEXT_KEY);
  }

  public void setText(String value) {
    if (_SWComment.LOG.isDebugEnabled()) {
    	_SWComment.LOG.debug( "updating text from " + text() + " to " + value);
    }
    takeStoredValueForKey(value, _SWComment.TEXT_KEY);
  }

  public String url() {
    return (String) storedValueForKey(_SWComment.URL_KEY);
  }

  public void setUrl(String value) {
    if (_SWComment.LOG.isDebugEnabled()) {
    	_SWComment.LOG.debug( "updating url from " + url() + " to " + value);
    }
    takeStoredValueForKey(value, _SWComment.URL_KEY);
  }

  public String userAgent() {
    return (String) storedValueForKey(_SWComment.USER_AGENT_KEY);
  }

  public void setUserAgent(String value) {
    if (_SWComment.LOG.isDebugEnabled()) {
    	_SWComment.LOG.debug( "updating userAgent from " + userAgent() + " to " + value);
    }
    takeStoredValueForKey(value, _SWComment.USER_AGENT_KEY);
  }

  public concept.data.SWUser user() {
    return (concept.data.SWUser)storedValueForKey(_SWComment.USER_KEY);
  }
  
  public void setUser(concept.data.SWUser value) {
    takeStoredValueForKey(value, _SWComment.USER_KEY);
  }

  public void setUserRelationship(concept.data.SWUser value) {
    if (_SWComment.LOG.isDebugEnabled()) {
      _SWComment.LOG.debug("updating user from " + user() + " to " + value);
    }
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
    	setUser(value);
    }
    else if (value == null) {
    	concept.data.SWUser oldValue = user();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, _SWComment.USER_KEY);
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, _SWComment.USER_KEY);
    }
  }
  

  public static concept.data.SWComment createSWComment(EOEditingContext editingContext) {
    concept.data.SWComment eo = (concept.data.SWComment) EOUtilities.createAndInsertInstance(editingContext, _SWComment.ENTITY_NAME);    
    return eo;
  }

  public static ERXFetchSpecification<concept.data.SWComment> fetchSpec() {
    return new ERXFetchSpecification<concept.data.SWComment>(_SWComment.ENTITY_NAME, null, null, false, true, null);
  }

  public static NSArray<concept.data.SWComment> fetchAllSWComments(EOEditingContext editingContext) {
    return _SWComment.fetchAllSWComments(editingContext, null);
  }

  public static NSArray<concept.data.SWComment> fetchAllSWComments(EOEditingContext editingContext, NSArray<EOSortOrdering> sortOrderings) {
    return _SWComment.fetchSWComments(editingContext, null, sortOrderings);
  }

  public static NSArray<concept.data.SWComment> fetchSWComments(EOEditingContext editingContext, EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings) {
    ERXFetchSpecification<concept.data.SWComment> fetchSpec = new ERXFetchSpecification<concept.data.SWComment>(_SWComment.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray<concept.data.SWComment> eoObjects = fetchSpec.fetchObjects(editingContext);
    return eoObjects;
  }

  public static concept.data.SWComment fetchSWComment(EOEditingContext editingContext, String keyName, Object value) {
    return _SWComment.fetchSWComment(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static concept.data.SWComment fetchSWComment(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray<concept.data.SWComment> eoObjects = _SWComment.fetchSWComments(editingContext, qualifier, null);
    concept.data.SWComment eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one SWComment that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static concept.data.SWComment fetchRequiredSWComment(EOEditingContext editingContext, String keyName, Object value) {
    return _SWComment.fetchRequiredSWComment(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static concept.data.SWComment fetchRequiredSWComment(EOEditingContext editingContext, EOQualifier qualifier) {
    concept.data.SWComment eoObject = _SWComment.fetchSWComment(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no SWComment that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static concept.data.SWComment localInstanceIn(EOEditingContext editingContext, concept.data.SWComment eo) {
    concept.data.SWComment localInstance = (eo == null) ? null : ERXEOControlUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
