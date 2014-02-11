// DO NOT EDIT.  Make changes to concept.data.SWDocumentFolder.java instead.
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
public abstract class _SWDocumentFolder extends  ERXGenericRecord {
  public static final String ENTITY_NAME = "SWDocumentFolder";

  // Attribute Keys
  public static final ERXKey<Integer> ID = new ERXKey<Integer>("id");
  public static final ERXKey<Integer> INHERITS_PRIVILEGES = new ERXKey<Integer>("inheritsPrivileges");
  public static final ERXKey<String> NAME = new ERXKey<String>("name");
  public static final ERXKey<Integer> PARENT_FOLDER_ID = new ERXKey<Integer>("parentFolderID");
  // Relationship Keys
  public static final ERXKey<concept.data.SWDocument> DOCUMENTS = new ERXKey<concept.data.SWDocument>("documents");
  public static final ERXKey<concept.data.SWDocumentFolder> PARENT_FOLDER = new ERXKey<concept.data.SWDocumentFolder>("parentFolder");
  public static final ERXKey<concept.data.SWDocumentFolder> SUB_FOLDERS = new ERXKey<concept.data.SWDocumentFolder>("subFolders");

  // Attributes
  public static final String ID_KEY = ID.key();
  public static final String INHERITS_PRIVILEGES_KEY = INHERITS_PRIVILEGES.key();
  public static final String NAME_KEY = NAME.key();
  public static final String PARENT_FOLDER_ID_KEY = PARENT_FOLDER_ID.key();
  // Relationships
  public static final String DOCUMENTS_KEY = DOCUMENTS.key();
  public static final String PARENT_FOLDER_KEY = PARENT_FOLDER.key();
  public static final String SUB_FOLDERS_KEY = SUB_FOLDERS.key();

  private static Logger LOG = Logger.getLogger(_SWDocumentFolder.class);

  public concept.data.SWDocumentFolder localInstanceIn(EOEditingContext editingContext) {
    concept.data.SWDocumentFolder localInstance = (concept.data.SWDocumentFolder)EOUtilities.localInstanceOfObject(editingContext, this);
    if (localInstance == null) {
      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
    }
    return localInstance;
  }

  public Integer id() {
    return (Integer) storedValueForKey(_SWDocumentFolder.ID_KEY);
  }

  public void setId(Integer value) {
    if (_SWDocumentFolder.LOG.isDebugEnabled()) {
    	_SWDocumentFolder.LOG.debug( "updating id from " + id() + " to " + value);
    }
    takeStoredValueForKey(value, _SWDocumentFolder.ID_KEY);
  }

  public Integer inheritsPrivileges() {
    return (Integer) storedValueForKey(_SWDocumentFolder.INHERITS_PRIVILEGES_KEY);
  }

  public void setInheritsPrivileges(Integer value) {
    if (_SWDocumentFolder.LOG.isDebugEnabled()) {
    	_SWDocumentFolder.LOG.debug( "updating inheritsPrivileges from " + inheritsPrivileges() + " to " + value);
    }
    takeStoredValueForKey(value, _SWDocumentFolder.INHERITS_PRIVILEGES_KEY);
  }

  public String name() {
    return (String) storedValueForKey(_SWDocumentFolder.NAME_KEY);
  }

  public void setName(String value) {
    if (_SWDocumentFolder.LOG.isDebugEnabled()) {
    	_SWDocumentFolder.LOG.debug( "updating name from " + name() + " to " + value);
    }
    takeStoredValueForKey(value, _SWDocumentFolder.NAME_KEY);
  }

  public Integer parentFolderID() {
    return (Integer) storedValueForKey(_SWDocumentFolder.PARENT_FOLDER_ID_KEY);
  }

  public void setParentFolderID(Integer value) {
    if (_SWDocumentFolder.LOG.isDebugEnabled()) {
    	_SWDocumentFolder.LOG.debug( "updating parentFolderID from " + parentFolderID() + " to " + value);
    }
    takeStoredValueForKey(value, _SWDocumentFolder.PARENT_FOLDER_ID_KEY);
  }

  public concept.data.SWDocumentFolder parentFolder() {
    return (concept.data.SWDocumentFolder)storedValueForKey(_SWDocumentFolder.PARENT_FOLDER_KEY);
  }
  
  public void setParentFolder(concept.data.SWDocumentFolder value) {
    takeStoredValueForKey(value, _SWDocumentFolder.PARENT_FOLDER_KEY);
  }

  public void setParentFolderRelationship(concept.data.SWDocumentFolder value) {
    if (_SWDocumentFolder.LOG.isDebugEnabled()) {
      _SWDocumentFolder.LOG.debug("updating parentFolder from " + parentFolder() + " to " + value);
    }
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
    	setParentFolder(value);
    }
    else if (value == null) {
    	concept.data.SWDocumentFolder oldValue = parentFolder();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, _SWDocumentFolder.PARENT_FOLDER_KEY);
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, _SWDocumentFolder.PARENT_FOLDER_KEY);
    }
  }
  
  public NSArray<concept.data.SWDocument> documents() {
    return (NSArray<concept.data.SWDocument>)storedValueForKey(_SWDocumentFolder.DOCUMENTS_KEY);
  }

  public NSArray<concept.data.SWDocument> documents(EOQualifier qualifier) {
    return documents(qualifier, null, false);
  }

  public NSArray<concept.data.SWDocument> documents(EOQualifier qualifier, boolean fetch) {
    return documents(qualifier, null, fetch);
  }

  public NSArray<concept.data.SWDocument> documents(EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings, boolean fetch) {
    NSArray<concept.data.SWDocument> results;
    if (fetch) {
      EOQualifier fullQualifier;
      EOQualifier inverseQualifier = new EOKeyValueQualifier(concept.data.SWDocument.FOLDER_KEY, EOQualifier.QualifierOperatorEqual, this);
    	
      if (qualifier == null) {
        fullQualifier = inverseQualifier;
      }
      else {
        NSMutableArray<EOQualifier> qualifiers = new NSMutableArray<EOQualifier>();
        qualifiers.addObject(qualifier);
        qualifiers.addObject(inverseQualifier);
        fullQualifier = new EOAndQualifier(qualifiers);
      }

      results = concept.data.SWDocument.fetchSWDocuments(editingContext(), fullQualifier, sortOrderings);
    }
    else {
      results = documents();
      if (qualifier != null) {
        results = (NSArray<concept.data.SWDocument>)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray<concept.data.SWDocument>)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    }
    return results;
  }
  
  public void addToDocuments(concept.data.SWDocument object) {
    includeObjectIntoPropertyWithKey(object, _SWDocumentFolder.DOCUMENTS_KEY);
  }

  public void removeFromDocuments(concept.data.SWDocument object) {
    excludeObjectFromPropertyWithKey(object, _SWDocumentFolder.DOCUMENTS_KEY);
  }

  public void addToDocumentsRelationship(concept.data.SWDocument object) {
    if (_SWDocumentFolder.LOG.isDebugEnabled()) {
      _SWDocumentFolder.LOG.debug("adding " + object + " to documents relationship");
    }
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
    	addToDocuments(object);
    }
    else {
    	addObjectToBothSidesOfRelationshipWithKey(object, _SWDocumentFolder.DOCUMENTS_KEY);
    }
  }

  public void removeFromDocumentsRelationship(concept.data.SWDocument object) {
    if (_SWDocumentFolder.LOG.isDebugEnabled()) {
      _SWDocumentFolder.LOG.debug("removing " + object + " from documents relationship");
    }
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
    	removeFromDocuments(object);
    }
    else {
    	removeObjectFromBothSidesOfRelationshipWithKey(object, _SWDocumentFolder.DOCUMENTS_KEY);
    }
  }

  public concept.data.SWDocument createDocumentsRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName( concept.data.SWDocument.ENTITY_NAME );
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, _SWDocumentFolder.DOCUMENTS_KEY);
    return (concept.data.SWDocument) eo;
  }

  public void deleteDocumentsRelationship(concept.data.SWDocument object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, _SWDocumentFolder.DOCUMENTS_KEY);
    editingContext().deleteObject(object);
  }

  public void deleteAllDocumentsRelationships() {
    Enumeration<concept.data.SWDocument> objects = documents().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteDocumentsRelationship(objects.nextElement());
    }
  }

  public NSArray<concept.data.SWDocumentFolder> subFolders() {
    return (NSArray<concept.data.SWDocumentFolder>)storedValueForKey(_SWDocumentFolder.SUB_FOLDERS_KEY);
  }

  public NSArray<concept.data.SWDocumentFolder> subFolders(EOQualifier qualifier) {
    return subFolders(qualifier, null, false);
  }

  public NSArray<concept.data.SWDocumentFolder> subFolders(EOQualifier qualifier, boolean fetch) {
    return subFolders(qualifier, null, fetch);
  }

  public NSArray<concept.data.SWDocumentFolder> subFolders(EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings, boolean fetch) {
    NSArray<concept.data.SWDocumentFolder> results;
    if (fetch) {
      EOQualifier fullQualifier;
      EOQualifier inverseQualifier = new EOKeyValueQualifier(concept.data.SWDocumentFolder.PARENT_FOLDER_KEY, EOQualifier.QualifierOperatorEqual, this);
    	
      if (qualifier == null) {
        fullQualifier = inverseQualifier;
      }
      else {
        NSMutableArray<EOQualifier> qualifiers = new NSMutableArray<EOQualifier>();
        qualifiers.addObject(qualifier);
        qualifiers.addObject(inverseQualifier);
        fullQualifier = new EOAndQualifier(qualifiers);
      }

      results = concept.data.SWDocumentFolder.fetchSWDocumentFolders(editingContext(), fullQualifier, sortOrderings);
    }
    else {
      results = subFolders();
      if (qualifier != null) {
        results = (NSArray<concept.data.SWDocumentFolder>)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray<concept.data.SWDocumentFolder>)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    }
    return results;
  }
  
  public void addToSubFolders(concept.data.SWDocumentFolder object) {
    includeObjectIntoPropertyWithKey(object, _SWDocumentFolder.SUB_FOLDERS_KEY);
  }

  public void removeFromSubFolders(concept.data.SWDocumentFolder object) {
    excludeObjectFromPropertyWithKey(object, _SWDocumentFolder.SUB_FOLDERS_KEY);
  }

  public void addToSubFoldersRelationship(concept.data.SWDocumentFolder object) {
    if (_SWDocumentFolder.LOG.isDebugEnabled()) {
      _SWDocumentFolder.LOG.debug("adding " + object + " to subFolders relationship");
    }
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
    	addToSubFolders(object);
    }
    else {
    	addObjectToBothSidesOfRelationshipWithKey(object, _SWDocumentFolder.SUB_FOLDERS_KEY);
    }
  }

  public void removeFromSubFoldersRelationship(concept.data.SWDocumentFolder object) {
    if (_SWDocumentFolder.LOG.isDebugEnabled()) {
      _SWDocumentFolder.LOG.debug("removing " + object + " from subFolders relationship");
    }
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
    	removeFromSubFolders(object);
    }
    else {
    	removeObjectFromBothSidesOfRelationshipWithKey(object, _SWDocumentFolder.SUB_FOLDERS_KEY);
    }
  }

  public concept.data.SWDocumentFolder createSubFoldersRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName( concept.data.SWDocumentFolder.ENTITY_NAME );
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, _SWDocumentFolder.SUB_FOLDERS_KEY);
    return (concept.data.SWDocumentFolder) eo;
  }

  public void deleteSubFoldersRelationship(concept.data.SWDocumentFolder object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, _SWDocumentFolder.SUB_FOLDERS_KEY);
    editingContext().deleteObject(object);
  }

  public void deleteAllSubFoldersRelationships() {
    Enumeration<concept.data.SWDocumentFolder> objects = subFolders().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteSubFoldersRelationship(objects.nextElement());
    }
  }


  public static concept.data.SWDocumentFolder createSWDocumentFolder(EOEditingContext editingContext, Integer id
) {
    concept.data.SWDocumentFolder eo = (concept.data.SWDocumentFolder) EOUtilities.createAndInsertInstance(editingContext, _SWDocumentFolder.ENTITY_NAME);    
		eo.setId(id);
    return eo;
  }

  public static ERXFetchSpecification<concept.data.SWDocumentFolder> fetchSpec() {
    return new ERXFetchSpecification<concept.data.SWDocumentFolder>(_SWDocumentFolder.ENTITY_NAME, null, null, false, true, null);
  }

  public static NSArray<concept.data.SWDocumentFolder> fetchAllSWDocumentFolders(EOEditingContext editingContext) {
    return _SWDocumentFolder.fetchAllSWDocumentFolders(editingContext, null);
  }

  public static NSArray<concept.data.SWDocumentFolder> fetchAllSWDocumentFolders(EOEditingContext editingContext, NSArray<EOSortOrdering> sortOrderings) {
    return _SWDocumentFolder.fetchSWDocumentFolders(editingContext, null, sortOrderings);
  }

  public static NSArray<concept.data.SWDocumentFolder> fetchSWDocumentFolders(EOEditingContext editingContext, EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings) {
    ERXFetchSpecification<concept.data.SWDocumentFolder> fetchSpec = new ERXFetchSpecification<concept.data.SWDocumentFolder>(_SWDocumentFolder.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray<concept.data.SWDocumentFolder> eoObjects = fetchSpec.fetchObjects(editingContext);
    return eoObjects;
  }

  public static concept.data.SWDocumentFolder fetchSWDocumentFolder(EOEditingContext editingContext, String keyName, Object value) {
    return _SWDocumentFolder.fetchSWDocumentFolder(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static concept.data.SWDocumentFolder fetchSWDocumentFolder(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray<concept.data.SWDocumentFolder> eoObjects = _SWDocumentFolder.fetchSWDocumentFolders(editingContext, qualifier, null);
    concept.data.SWDocumentFolder eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one SWDocumentFolder that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static concept.data.SWDocumentFolder fetchRequiredSWDocumentFolder(EOEditingContext editingContext, String keyName, Object value) {
    return _SWDocumentFolder.fetchRequiredSWDocumentFolder(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static concept.data.SWDocumentFolder fetchRequiredSWDocumentFolder(EOEditingContext editingContext, EOQualifier qualifier) {
    concept.data.SWDocumentFolder eoObject = _SWDocumentFolder.fetchSWDocumentFolder(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no SWDocumentFolder that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static concept.data.SWDocumentFolder localInstanceIn(EOEditingContext editingContext, concept.data.SWDocumentFolder eo) {
    concept.data.SWDocumentFolder localInstance = (eo == null) ? null : ERXEOControlUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
