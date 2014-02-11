// DO NOT EDIT.  Make changes to concept.data.SWAccessPrivilege.java instead.
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
public abstract class _SWAccessPrivilege extends  ERXGenericRecord {
  public static final String ENTITY_NAME = "SWAccessPrivilege";

  // Attribute Keys
  public static final ERXKey<Integer> NOT_INHERITED = new ERXKey<Integer>("notInherited");
  public static final ERXKey<String> TARGET_ENTITY_NAME = new ERXKey<String>("targetEntityName");
  public static final ERXKey<String> TARGET_ID = new ERXKey<String>("targetID");
  // Relationship Keys
  public static final ERXKey<concept.data.SWGroup> GROUP = new ERXKey<concept.data.SWGroup>("group");
  public static final ERXKey<concept.data.SWUser> USER = new ERXKey<concept.data.SWUser>("user");
  public static final ERXKey<concept.data.SWAccessPrivilegeValue> VALUES = new ERXKey<concept.data.SWAccessPrivilegeValue>("values");

  // Attributes
  public static final String NOT_INHERITED_KEY = NOT_INHERITED.key();
  public static final String TARGET_ENTITY_NAME_KEY = TARGET_ENTITY_NAME.key();
  public static final String TARGET_ID_KEY = TARGET_ID.key();
  // Relationships
  public static final String GROUP_KEY = GROUP.key();
  public static final String USER_KEY = USER.key();
  public static final String VALUES_KEY = VALUES.key();

  private static Logger LOG = Logger.getLogger(_SWAccessPrivilege.class);

  public concept.data.SWAccessPrivilege localInstanceIn(EOEditingContext editingContext) {
    concept.data.SWAccessPrivilege localInstance = (concept.data.SWAccessPrivilege)EOUtilities.localInstanceOfObject(editingContext, this);
    if (localInstance == null) {
      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
    }
    return localInstance;
  }

  public Integer notInherited() {
    return (Integer) storedValueForKey(_SWAccessPrivilege.NOT_INHERITED_KEY);
  }

  public void setNotInherited(Integer value) {
    if (_SWAccessPrivilege.LOG.isDebugEnabled()) {
    	_SWAccessPrivilege.LOG.debug( "updating notInherited from " + notInherited() + " to " + value);
    }
    takeStoredValueForKey(value, _SWAccessPrivilege.NOT_INHERITED_KEY);
  }

  public String targetEntityName() {
    return (String) storedValueForKey(_SWAccessPrivilege.TARGET_ENTITY_NAME_KEY);
  }

  public void setTargetEntityName(String value) {
    if (_SWAccessPrivilege.LOG.isDebugEnabled()) {
    	_SWAccessPrivilege.LOG.debug( "updating targetEntityName from " + targetEntityName() + " to " + value);
    }
    takeStoredValueForKey(value, _SWAccessPrivilege.TARGET_ENTITY_NAME_KEY);
  }

  public String targetID() {
    return (String) storedValueForKey(_SWAccessPrivilege.TARGET_ID_KEY);
  }

  public void setTargetID(String value) {
    if (_SWAccessPrivilege.LOG.isDebugEnabled()) {
    	_SWAccessPrivilege.LOG.debug( "updating targetID from " + targetID() + " to " + value);
    }
    takeStoredValueForKey(value, _SWAccessPrivilege.TARGET_ID_KEY);
  }

  public concept.data.SWGroup group() {
    return (concept.data.SWGroup)storedValueForKey(_SWAccessPrivilege.GROUP_KEY);
  }
  
  public void setGroup(concept.data.SWGroup value) {
    takeStoredValueForKey(value, _SWAccessPrivilege.GROUP_KEY);
  }

  public void setGroupRelationship(concept.data.SWGroup value) {
    if (_SWAccessPrivilege.LOG.isDebugEnabled()) {
      _SWAccessPrivilege.LOG.debug("updating group from " + group() + " to " + value);
    }
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
    	setGroup(value);
    }
    else if (value == null) {
    	concept.data.SWGroup oldValue = group();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, _SWAccessPrivilege.GROUP_KEY);
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, _SWAccessPrivilege.GROUP_KEY);
    }
  }
  
  public concept.data.SWUser user() {
    return (concept.data.SWUser)storedValueForKey(_SWAccessPrivilege.USER_KEY);
  }
  
  public void setUser(concept.data.SWUser value) {
    takeStoredValueForKey(value, _SWAccessPrivilege.USER_KEY);
  }

  public void setUserRelationship(concept.data.SWUser value) {
    if (_SWAccessPrivilege.LOG.isDebugEnabled()) {
      _SWAccessPrivilege.LOG.debug("updating user from " + user() + " to " + value);
    }
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
    	setUser(value);
    }
    else if (value == null) {
    	concept.data.SWUser oldValue = user();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, _SWAccessPrivilege.USER_KEY);
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, _SWAccessPrivilege.USER_KEY);
    }
  }
  
  public NSArray<concept.data.SWAccessPrivilegeValue> values() {
    return (NSArray<concept.data.SWAccessPrivilegeValue>)storedValueForKey(_SWAccessPrivilege.VALUES_KEY);
  }

  public NSArray<concept.data.SWAccessPrivilegeValue> values(EOQualifier qualifier) {
    return values(qualifier, null, false);
  }

  public NSArray<concept.data.SWAccessPrivilegeValue> values(EOQualifier qualifier, boolean fetch) {
    return values(qualifier, null, fetch);
  }

  public NSArray<concept.data.SWAccessPrivilegeValue> values(EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings, boolean fetch) {
    NSArray<concept.data.SWAccessPrivilegeValue> results;
    if (fetch) {
      EOQualifier fullQualifier;
      EOQualifier inverseQualifier = new EOKeyValueQualifier(concept.data.SWAccessPrivilegeValue.ACCESS_PRIVILEGE_KEY, EOQualifier.QualifierOperatorEqual, this);
    	
      if (qualifier == null) {
        fullQualifier = inverseQualifier;
      }
      else {
        NSMutableArray<EOQualifier> qualifiers = new NSMutableArray<EOQualifier>();
        qualifiers.addObject(qualifier);
        qualifiers.addObject(inverseQualifier);
        fullQualifier = new EOAndQualifier(qualifiers);
      }

      results = concept.data.SWAccessPrivilegeValue.fetchSWAccessPrivilegeValues(editingContext(), fullQualifier, sortOrderings);
    }
    else {
      results = values();
      if (qualifier != null) {
        results = (NSArray<concept.data.SWAccessPrivilegeValue>)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray<concept.data.SWAccessPrivilegeValue>)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    }
    return results;
  }
  
  public void addToValues(concept.data.SWAccessPrivilegeValue object) {
    includeObjectIntoPropertyWithKey(object, _SWAccessPrivilege.VALUES_KEY);
  }

  public void removeFromValues(concept.data.SWAccessPrivilegeValue object) {
    excludeObjectFromPropertyWithKey(object, _SWAccessPrivilege.VALUES_KEY);
  }

  public void addToValuesRelationship(concept.data.SWAccessPrivilegeValue object) {
    if (_SWAccessPrivilege.LOG.isDebugEnabled()) {
      _SWAccessPrivilege.LOG.debug("adding " + object + " to values relationship");
    }
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
    	addToValues(object);
    }
    else {
    	addObjectToBothSidesOfRelationshipWithKey(object, _SWAccessPrivilege.VALUES_KEY);
    }
  }

  public void removeFromValuesRelationship(concept.data.SWAccessPrivilegeValue object) {
    if (_SWAccessPrivilege.LOG.isDebugEnabled()) {
      _SWAccessPrivilege.LOG.debug("removing " + object + " from values relationship");
    }
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
    	removeFromValues(object);
    }
    else {
    	removeObjectFromBothSidesOfRelationshipWithKey(object, _SWAccessPrivilege.VALUES_KEY);
    }
  }

  public concept.data.SWAccessPrivilegeValue createValuesRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName( concept.data.SWAccessPrivilegeValue.ENTITY_NAME );
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, _SWAccessPrivilege.VALUES_KEY);
    return (concept.data.SWAccessPrivilegeValue) eo;
  }

  public void deleteValuesRelationship(concept.data.SWAccessPrivilegeValue object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, _SWAccessPrivilege.VALUES_KEY);
    editingContext().deleteObject(object);
  }

  public void deleteAllValuesRelationships() {
    Enumeration<concept.data.SWAccessPrivilegeValue> objects = values().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteValuesRelationship(objects.nextElement());
    }
  }


  public static concept.data.SWAccessPrivilege createSWAccessPrivilege(EOEditingContext editingContext) {
    concept.data.SWAccessPrivilege eo = (concept.data.SWAccessPrivilege) EOUtilities.createAndInsertInstance(editingContext, _SWAccessPrivilege.ENTITY_NAME);    
    return eo;
  }

  public static ERXFetchSpecification<concept.data.SWAccessPrivilege> fetchSpec() {
    return new ERXFetchSpecification<concept.data.SWAccessPrivilege>(_SWAccessPrivilege.ENTITY_NAME, null, null, false, true, null);
  }

  public static NSArray<concept.data.SWAccessPrivilege> fetchAllSWAccessPrivileges(EOEditingContext editingContext) {
    return _SWAccessPrivilege.fetchAllSWAccessPrivileges(editingContext, null);
  }

  public static NSArray<concept.data.SWAccessPrivilege> fetchAllSWAccessPrivileges(EOEditingContext editingContext, NSArray<EOSortOrdering> sortOrderings) {
    return _SWAccessPrivilege.fetchSWAccessPrivileges(editingContext, null, sortOrderings);
  }

  public static NSArray<concept.data.SWAccessPrivilege> fetchSWAccessPrivileges(EOEditingContext editingContext, EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings) {
    ERXFetchSpecification<concept.data.SWAccessPrivilege> fetchSpec = new ERXFetchSpecification<concept.data.SWAccessPrivilege>(_SWAccessPrivilege.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray<concept.data.SWAccessPrivilege> eoObjects = fetchSpec.fetchObjects(editingContext);
    return eoObjects;
  }

  public static concept.data.SWAccessPrivilege fetchSWAccessPrivilege(EOEditingContext editingContext, String keyName, Object value) {
    return _SWAccessPrivilege.fetchSWAccessPrivilege(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static concept.data.SWAccessPrivilege fetchSWAccessPrivilege(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray<concept.data.SWAccessPrivilege> eoObjects = _SWAccessPrivilege.fetchSWAccessPrivileges(editingContext, qualifier, null);
    concept.data.SWAccessPrivilege eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one SWAccessPrivilege that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static concept.data.SWAccessPrivilege fetchRequiredSWAccessPrivilege(EOEditingContext editingContext, String keyName, Object value) {
    return _SWAccessPrivilege.fetchRequiredSWAccessPrivilege(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static concept.data.SWAccessPrivilege fetchRequiredSWAccessPrivilege(EOEditingContext editingContext, EOQualifier qualifier) {
    concept.data.SWAccessPrivilege eoObject = _SWAccessPrivilege.fetchSWAccessPrivilege(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no SWAccessPrivilege that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static concept.data.SWAccessPrivilege localInstanceIn(EOEditingContext editingContext, concept.data.SWAccessPrivilege eo) {
    concept.data.SWAccessPrivilege localInstance = (eo == null) ? null : ERXEOControlUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
