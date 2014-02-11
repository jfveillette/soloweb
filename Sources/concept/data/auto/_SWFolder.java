// DO NOT EDIT.  Make changes to concept.data.SWFolder.java instead.
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
public abstract class _SWFolder extends  ERXGenericRecord {
  public static final String ENTITY_NAME = "SWFolder";

  // Attribute Keys
  public static final ERXKey<Integer> INHERITS_PRIVILEGES = new ERXKey<Integer>("inheritsPrivileges");
  public static final ERXKey<String> NAME = new ERXKey<String>("name");
  // Relationship Keys
  public static final ERXKey<concept.data.SWFolder> CHILDREN = new ERXKey<concept.data.SWFolder>("children");
  public static final ERXKey<concept.data.SWFolderLink> LINKS = new ERXKey<concept.data.SWFolderLink>("links");
  public static final ERXKey<concept.data.SWFolder> PARENT = new ERXKey<concept.data.SWFolder>("parent");

  // Attributes
  public static final String INHERITS_PRIVILEGES_KEY = INHERITS_PRIVILEGES.key();
  public static final String NAME_KEY = NAME.key();
  // Relationships
  public static final String CHILDREN_KEY = CHILDREN.key();
  public static final String LINKS_KEY = LINKS.key();
  public static final String PARENT_KEY = PARENT.key();

  private static Logger LOG = Logger.getLogger(_SWFolder.class);

  public concept.data.SWFolder localInstanceIn(EOEditingContext editingContext) {
    concept.data.SWFolder localInstance = (concept.data.SWFolder)EOUtilities.localInstanceOfObject(editingContext, this);
    if (localInstance == null) {
      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
    }
    return localInstance;
  }

  public Integer inheritsPrivileges() {
    return (Integer) storedValueForKey(_SWFolder.INHERITS_PRIVILEGES_KEY);
  }

  public void setInheritsPrivileges(Integer value) {
    if (_SWFolder.LOG.isDebugEnabled()) {
    	_SWFolder.LOG.debug( "updating inheritsPrivileges from " + inheritsPrivileges() + " to " + value);
    }
    takeStoredValueForKey(value, _SWFolder.INHERITS_PRIVILEGES_KEY);
  }

  public String name() {
    return (String) storedValueForKey(_SWFolder.NAME_KEY);
  }

  public void setName(String value) {
    if (_SWFolder.LOG.isDebugEnabled()) {
    	_SWFolder.LOG.debug( "updating name from " + name() + " to " + value);
    }
    takeStoredValueForKey(value, _SWFolder.NAME_KEY);
  }

  public concept.data.SWFolder parent() {
    return (concept.data.SWFolder)storedValueForKey(_SWFolder.PARENT_KEY);
  }
  
  public void setParent(concept.data.SWFolder value) {
    takeStoredValueForKey(value, _SWFolder.PARENT_KEY);
  }

  public void setParentRelationship(concept.data.SWFolder value) {
    if (_SWFolder.LOG.isDebugEnabled()) {
      _SWFolder.LOG.debug("updating parent from " + parent() + " to " + value);
    }
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
    	setParent(value);
    }
    else if (value == null) {
    	concept.data.SWFolder oldValue = parent();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, _SWFolder.PARENT_KEY);
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, _SWFolder.PARENT_KEY);
    }
  }
  
  public NSArray<concept.data.SWFolder> children() {
    return (NSArray<concept.data.SWFolder>)storedValueForKey(_SWFolder.CHILDREN_KEY);
  }

  public NSArray<concept.data.SWFolder> children(EOQualifier qualifier) {
    return children(qualifier, null, false);
  }

  public NSArray<concept.data.SWFolder> children(EOQualifier qualifier, boolean fetch) {
    return children(qualifier, null, fetch);
  }

  public NSArray<concept.data.SWFolder> children(EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings, boolean fetch) {
    NSArray<concept.data.SWFolder> results;
    if (fetch) {
      EOQualifier fullQualifier;
      EOQualifier inverseQualifier = new EOKeyValueQualifier(concept.data.SWFolder.PARENT_KEY, EOQualifier.QualifierOperatorEqual, this);
    	
      if (qualifier == null) {
        fullQualifier = inverseQualifier;
      }
      else {
        NSMutableArray<EOQualifier> qualifiers = new NSMutableArray<EOQualifier>();
        qualifiers.addObject(qualifier);
        qualifiers.addObject(inverseQualifier);
        fullQualifier = new EOAndQualifier(qualifiers);
      }

      results = concept.data.SWFolder.fetchSWFolders(editingContext(), fullQualifier, sortOrderings);
    }
    else {
      results = children();
      if (qualifier != null) {
        results = (NSArray<concept.data.SWFolder>)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray<concept.data.SWFolder>)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    }
    return results;
  }
  
  public void addToChildren(concept.data.SWFolder object) {
    includeObjectIntoPropertyWithKey(object, _SWFolder.CHILDREN_KEY);
  }

  public void removeFromChildren(concept.data.SWFolder object) {
    excludeObjectFromPropertyWithKey(object, _SWFolder.CHILDREN_KEY);
  }

  public void addToChildrenRelationship(concept.data.SWFolder object) {
    if (_SWFolder.LOG.isDebugEnabled()) {
      _SWFolder.LOG.debug("adding " + object + " to children relationship");
    }
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
    	addToChildren(object);
    }
    else {
    	addObjectToBothSidesOfRelationshipWithKey(object, _SWFolder.CHILDREN_KEY);
    }
  }

  public void removeFromChildrenRelationship(concept.data.SWFolder object) {
    if (_SWFolder.LOG.isDebugEnabled()) {
      _SWFolder.LOG.debug("removing " + object + " from children relationship");
    }
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
    	removeFromChildren(object);
    }
    else {
    	removeObjectFromBothSidesOfRelationshipWithKey(object, _SWFolder.CHILDREN_KEY);
    }
  }

  public concept.data.SWFolder createChildrenRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName( concept.data.SWFolder.ENTITY_NAME );
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, _SWFolder.CHILDREN_KEY);
    return (concept.data.SWFolder) eo;
  }

  public void deleteChildrenRelationship(concept.data.SWFolder object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, _SWFolder.CHILDREN_KEY);
    editingContext().deleteObject(object);
  }

  public void deleteAllChildrenRelationships() {
    Enumeration<concept.data.SWFolder> objects = children().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteChildrenRelationship(objects.nextElement());
    }
  }

  public NSArray<concept.data.SWFolderLink> links() {
    return (NSArray<concept.data.SWFolderLink>)storedValueForKey(_SWFolder.LINKS_KEY);
  }

  public NSArray<concept.data.SWFolderLink> links(EOQualifier qualifier) {
    return links(qualifier, null, false);
  }

  public NSArray<concept.data.SWFolderLink> links(EOQualifier qualifier, boolean fetch) {
    return links(qualifier, null, fetch);
  }

  public NSArray<concept.data.SWFolderLink> links(EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings, boolean fetch) {
    NSArray<concept.data.SWFolderLink> results;
    if (fetch) {
      EOQualifier fullQualifier;
      EOQualifier inverseQualifier = new EOKeyValueQualifier(concept.data.SWFolderLink.FOLDER_KEY, EOQualifier.QualifierOperatorEqual, this);
    	
      if (qualifier == null) {
        fullQualifier = inverseQualifier;
      }
      else {
        NSMutableArray<EOQualifier> qualifiers = new NSMutableArray<EOQualifier>();
        qualifiers.addObject(qualifier);
        qualifiers.addObject(inverseQualifier);
        fullQualifier = new EOAndQualifier(qualifiers);
      }

      results = concept.data.SWFolderLink.fetchSWFolderLinks(editingContext(), fullQualifier, sortOrderings);
    }
    else {
      results = links();
      if (qualifier != null) {
        results = (NSArray<concept.data.SWFolderLink>)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray<concept.data.SWFolderLink>)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    }
    return results;
  }
  
  public void addToLinks(concept.data.SWFolderLink object) {
    includeObjectIntoPropertyWithKey(object, _SWFolder.LINKS_KEY);
  }

  public void removeFromLinks(concept.data.SWFolderLink object) {
    excludeObjectFromPropertyWithKey(object, _SWFolder.LINKS_KEY);
  }

  public void addToLinksRelationship(concept.data.SWFolderLink object) {
    if (_SWFolder.LOG.isDebugEnabled()) {
      _SWFolder.LOG.debug("adding " + object + " to links relationship");
    }
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
    	addToLinks(object);
    }
    else {
    	addObjectToBothSidesOfRelationshipWithKey(object, _SWFolder.LINKS_KEY);
    }
  }

  public void removeFromLinksRelationship(concept.data.SWFolderLink object) {
    if (_SWFolder.LOG.isDebugEnabled()) {
      _SWFolder.LOG.debug("removing " + object + " from links relationship");
    }
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
    	removeFromLinks(object);
    }
    else {
    	removeObjectFromBothSidesOfRelationshipWithKey(object, _SWFolder.LINKS_KEY);
    }
  }

  public concept.data.SWFolderLink createLinksRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName( concept.data.SWFolderLink.ENTITY_NAME );
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, _SWFolder.LINKS_KEY);
    return (concept.data.SWFolderLink) eo;
  }

  public void deleteLinksRelationship(concept.data.SWFolderLink object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, _SWFolder.LINKS_KEY);
    editingContext().deleteObject(object);
  }

  public void deleteAllLinksRelationships() {
    Enumeration<concept.data.SWFolderLink> objects = links().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteLinksRelationship(objects.nextElement());
    }
  }


  public static concept.data.SWFolder createSWFolder(EOEditingContext editingContext) {
    concept.data.SWFolder eo = (concept.data.SWFolder) EOUtilities.createAndInsertInstance(editingContext, _SWFolder.ENTITY_NAME);    
    return eo;
  }

  public static ERXFetchSpecification<concept.data.SWFolder> fetchSpec() {
    return new ERXFetchSpecification<concept.data.SWFolder>(_SWFolder.ENTITY_NAME, null, null, false, true, null);
  }

  public static NSArray<concept.data.SWFolder> fetchAllSWFolders(EOEditingContext editingContext) {
    return _SWFolder.fetchAllSWFolders(editingContext, null);
  }

  public static NSArray<concept.data.SWFolder> fetchAllSWFolders(EOEditingContext editingContext, NSArray<EOSortOrdering> sortOrderings) {
    return _SWFolder.fetchSWFolders(editingContext, null, sortOrderings);
  }

  public static NSArray<concept.data.SWFolder> fetchSWFolders(EOEditingContext editingContext, EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings) {
    ERXFetchSpecification<concept.data.SWFolder> fetchSpec = new ERXFetchSpecification<concept.data.SWFolder>(_SWFolder.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray<concept.data.SWFolder> eoObjects = fetchSpec.fetchObjects(editingContext);
    return eoObjects;
  }

  public static concept.data.SWFolder fetchSWFolder(EOEditingContext editingContext, String keyName, Object value) {
    return _SWFolder.fetchSWFolder(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static concept.data.SWFolder fetchSWFolder(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray<concept.data.SWFolder> eoObjects = _SWFolder.fetchSWFolders(editingContext, qualifier, null);
    concept.data.SWFolder eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one SWFolder that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static concept.data.SWFolder fetchRequiredSWFolder(EOEditingContext editingContext, String keyName, Object value) {
    return _SWFolder.fetchRequiredSWFolder(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static concept.data.SWFolder fetchRequiredSWFolder(EOEditingContext editingContext, EOQualifier qualifier) {
    concept.data.SWFolder eoObject = _SWFolder.fetchSWFolder(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no SWFolder that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static concept.data.SWFolder localInstanceIn(EOEditingContext editingContext, concept.data.SWFolder eo) {
    concept.data.SWFolder localInstance = (eo == null) ? null : ERXEOControlUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
