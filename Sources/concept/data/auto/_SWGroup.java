// DO NOT EDIT.  Make changes to concept.data.SWGroup.java instead.
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
public abstract class _SWGroup extends  ERXGenericRecord {
  public static final String ENTITY_NAME = "SWGroup";

  // Attribute Keys
  public static final ERXKey<Integer> CRM_GROUP = new ERXKey<Integer>("crmGroup");
  public static final ERXKey<Integer> ID = new ERXKey<Integer>("id");
  public static final ERXKey<String> NAME = new ERXKey<String>("name");
  // Relationship Keys
  public static final ERXKey<concept.data.SWAccessPrivilege> ACCESS_PRIVILEGES = new ERXKey<concept.data.SWAccessPrivilege>("accessPrivileges");
  public static final ERXKey<concept.data.SWUser> USERS = new ERXKey<concept.data.SWUser>("users");

  // Attributes
  public static final String CRM_GROUP_KEY = CRM_GROUP.key();
  public static final String ID_KEY = ID.key();
  public static final String NAME_KEY = NAME.key();
  // Relationships
  public static final String ACCESS_PRIVILEGES_KEY = ACCESS_PRIVILEGES.key();
  public static final String USERS_KEY = USERS.key();

  private static Logger LOG = Logger.getLogger(_SWGroup.class);

  public concept.data.SWGroup localInstanceIn(EOEditingContext editingContext) {
    concept.data.SWGroup localInstance = (concept.data.SWGroup)EOUtilities.localInstanceOfObject(editingContext, this);
    if (localInstance == null) {
      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
    }
    return localInstance;
  }

  public Integer crmGroup() {
    return (Integer) storedValueForKey(_SWGroup.CRM_GROUP_KEY);
  }

  public void setCrmGroup(Integer value) {
    if (_SWGroup.LOG.isDebugEnabled()) {
    	_SWGroup.LOG.debug( "updating crmGroup from " + crmGroup() + " to " + value);
    }
    takeStoredValueForKey(value, _SWGroup.CRM_GROUP_KEY);
  }

  public Integer id() {
    return (Integer) storedValueForKey(_SWGroup.ID_KEY);
  }

  public void setId(Integer value) {
    if (_SWGroup.LOG.isDebugEnabled()) {
    	_SWGroup.LOG.debug( "updating id from " + id() + " to " + value);
    }
    takeStoredValueForKey(value, _SWGroup.ID_KEY);
  }

  public String name() {
    return (String) storedValueForKey(_SWGroup.NAME_KEY);
  }

  public void setName(String value) {
    if (_SWGroup.LOG.isDebugEnabled()) {
    	_SWGroup.LOG.debug( "updating name from " + name() + " to " + value);
    }
    takeStoredValueForKey(value, _SWGroup.NAME_KEY);
  }

  public NSArray<concept.data.SWAccessPrivilege> accessPrivileges() {
    return (NSArray<concept.data.SWAccessPrivilege>)storedValueForKey(_SWGroup.ACCESS_PRIVILEGES_KEY);
  }

  public NSArray<concept.data.SWAccessPrivilege> accessPrivileges(EOQualifier qualifier) {
    return accessPrivileges(qualifier, null, false);
  }

  public NSArray<concept.data.SWAccessPrivilege> accessPrivileges(EOQualifier qualifier, boolean fetch) {
    return accessPrivileges(qualifier, null, fetch);
  }

  public NSArray<concept.data.SWAccessPrivilege> accessPrivileges(EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings, boolean fetch) {
    NSArray<concept.data.SWAccessPrivilege> results;
    if (fetch) {
      EOQualifier fullQualifier;
      EOQualifier inverseQualifier = new EOKeyValueQualifier(concept.data.SWAccessPrivilege.GROUP_KEY, EOQualifier.QualifierOperatorEqual, this);
    	
      if (qualifier == null) {
        fullQualifier = inverseQualifier;
      }
      else {
        NSMutableArray<EOQualifier> qualifiers = new NSMutableArray<EOQualifier>();
        qualifiers.addObject(qualifier);
        qualifiers.addObject(inverseQualifier);
        fullQualifier = new EOAndQualifier(qualifiers);
      }

      results = concept.data.SWAccessPrivilege.fetchSWAccessPrivileges(editingContext(), fullQualifier, sortOrderings);
    }
    else {
      results = accessPrivileges();
      if (qualifier != null) {
        results = (NSArray<concept.data.SWAccessPrivilege>)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray<concept.data.SWAccessPrivilege>)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    }
    return results;
  }
  
  public void addToAccessPrivileges(concept.data.SWAccessPrivilege object) {
    includeObjectIntoPropertyWithKey(object, _SWGroup.ACCESS_PRIVILEGES_KEY);
  }

  public void removeFromAccessPrivileges(concept.data.SWAccessPrivilege object) {
    excludeObjectFromPropertyWithKey(object, _SWGroup.ACCESS_PRIVILEGES_KEY);
  }

  public void addToAccessPrivilegesRelationship(concept.data.SWAccessPrivilege object) {
    if (_SWGroup.LOG.isDebugEnabled()) {
      _SWGroup.LOG.debug("adding " + object + " to accessPrivileges relationship");
    }
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
    	addToAccessPrivileges(object);
    }
    else {
    	addObjectToBothSidesOfRelationshipWithKey(object, _SWGroup.ACCESS_PRIVILEGES_KEY);
    }
  }

  public void removeFromAccessPrivilegesRelationship(concept.data.SWAccessPrivilege object) {
    if (_SWGroup.LOG.isDebugEnabled()) {
      _SWGroup.LOG.debug("removing " + object + " from accessPrivileges relationship");
    }
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
    	removeFromAccessPrivileges(object);
    }
    else {
    	removeObjectFromBothSidesOfRelationshipWithKey(object, _SWGroup.ACCESS_PRIVILEGES_KEY);
    }
  }

  public concept.data.SWAccessPrivilege createAccessPrivilegesRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName( concept.data.SWAccessPrivilege.ENTITY_NAME );
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, _SWGroup.ACCESS_PRIVILEGES_KEY);
    return (concept.data.SWAccessPrivilege) eo;
  }

  public void deleteAccessPrivilegesRelationship(concept.data.SWAccessPrivilege object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, _SWGroup.ACCESS_PRIVILEGES_KEY);
    editingContext().deleteObject(object);
  }

  public void deleteAllAccessPrivilegesRelationships() {
    Enumeration<concept.data.SWAccessPrivilege> objects = accessPrivileges().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteAccessPrivilegesRelationship(objects.nextElement());
    }
  }

  public NSArray<concept.data.SWUser> users() {
    return (NSArray<concept.data.SWUser>)storedValueForKey(_SWGroup.USERS_KEY);
  }

  public NSArray<concept.data.SWUser> users(EOQualifier qualifier) {
    return users(qualifier, null);
  }

  public NSArray<concept.data.SWUser> users(EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings) {
    NSArray<concept.data.SWUser> results;
      results = users();
      if (qualifier != null) {
        results = (NSArray<concept.data.SWUser>)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray<concept.data.SWUser>)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    return results;
  }
  
  public void addToUsers(concept.data.SWUser object) {
    includeObjectIntoPropertyWithKey(object, _SWGroup.USERS_KEY);
  }

  public void removeFromUsers(concept.data.SWUser object) {
    excludeObjectFromPropertyWithKey(object, _SWGroup.USERS_KEY);
  }

  public void addToUsersRelationship(concept.data.SWUser object) {
    if (_SWGroup.LOG.isDebugEnabled()) {
      _SWGroup.LOG.debug("adding " + object + " to users relationship");
    }
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
    	addToUsers(object);
    }
    else {
    	addObjectToBothSidesOfRelationshipWithKey(object, _SWGroup.USERS_KEY);
    }
  }

  public void removeFromUsersRelationship(concept.data.SWUser object) {
    if (_SWGroup.LOG.isDebugEnabled()) {
      _SWGroup.LOG.debug("removing " + object + " from users relationship");
    }
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
    	removeFromUsers(object);
    }
    else {
    	removeObjectFromBothSidesOfRelationshipWithKey(object, _SWGroup.USERS_KEY);
    }
  }

  public concept.data.SWUser createUsersRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName( concept.data.SWUser.ENTITY_NAME );
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, _SWGroup.USERS_KEY);
    return (concept.data.SWUser) eo;
  }

  public void deleteUsersRelationship(concept.data.SWUser object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, _SWGroup.USERS_KEY);
    editingContext().deleteObject(object);
  }

  public void deleteAllUsersRelationships() {
    Enumeration<concept.data.SWUser> objects = users().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteUsersRelationship(objects.nextElement());
    }
  }


  public static concept.data.SWGroup createSWGroup(EOEditingContext editingContext, Integer id
) {
    concept.data.SWGroup eo = (concept.data.SWGroup) EOUtilities.createAndInsertInstance(editingContext, _SWGroup.ENTITY_NAME);    
		eo.setId(id);
    return eo;
  }

  public static ERXFetchSpecification<concept.data.SWGroup> fetchSpec() {
    return new ERXFetchSpecification<concept.data.SWGroup>(_SWGroup.ENTITY_NAME, null, null, false, true, null);
  }

  public static NSArray<concept.data.SWGroup> fetchAllSWGroups(EOEditingContext editingContext) {
    return _SWGroup.fetchAllSWGroups(editingContext, null);
  }

  public static NSArray<concept.data.SWGroup> fetchAllSWGroups(EOEditingContext editingContext, NSArray<EOSortOrdering> sortOrderings) {
    return _SWGroup.fetchSWGroups(editingContext, null, sortOrderings);
  }

  public static NSArray<concept.data.SWGroup> fetchSWGroups(EOEditingContext editingContext, EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings) {
    ERXFetchSpecification<concept.data.SWGroup> fetchSpec = new ERXFetchSpecification<concept.data.SWGroup>(_SWGroup.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray<concept.data.SWGroup> eoObjects = fetchSpec.fetchObjects(editingContext);
    return eoObjects;
  }

  public static concept.data.SWGroup fetchSWGroup(EOEditingContext editingContext, String keyName, Object value) {
    return _SWGroup.fetchSWGroup(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static concept.data.SWGroup fetchSWGroup(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray<concept.data.SWGroup> eoObjects = _SWGroup.fetchSWGroups(editingContext, qualifier, null);
    concept.data.SWGroup eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one SWGroup that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static concept.data.SWGroup fetchRequiredSWGroup(EOEditingContext editingContext, String keyName, Object value) {
    return _SWGroup.fetchRequiredSWGroup(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static concept.data.SWGroup fetchRequiredSWGroup(EOEditingContext editingContext, EOQualifier qualifier) {
    concept.data.SWGroup eoObject = _SWGroup.fetchSWGroup(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no SWGroup that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static concept.data.SWGroup localInstanceIn(EOEditingContext editingContext, concept.data.SWGroup eo) {
    concept.data.SWGroup localInstance = (eo == null) ? null : ERXEOControlUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
