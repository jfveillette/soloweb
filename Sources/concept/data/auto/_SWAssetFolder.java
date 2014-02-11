// DO NOT EDIT.  Make changes to concept.data.SWAssetFolder.java instead.
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
public abstract class _SWAssetFolder extends  ERXGenericRecord {
  public static final String ENTITY_NAME = "SWAssetFolder";

  // Attribute Keys
  public static final ERXKey<Integer> ID = new ERXKey<Integer>("id");
  public static final ERXKey<Integer> INHERITS_PRIVILEGES = new ERXKey<Integer>("inheritsPrivileges");
  public static final ERXKey<String> NAME = new ERXKey<String>("name");
  public static final ERXKey<Integer> PARENT_FOLDER_ID = new ERXKey<Integer>("parentFolderID");
  // Relationship Keys
  public static final ERXKey<concept.data.SWAssetFolder> PARENT_FOLDER = new ERXKey<concept.data.SWAssetFolder>("parentFolder");
  public static final ERXKey<concept.data.SWPicture> PICTURES = new ERXKey<concept.data.SWPicture>("pictures");
  public static final ERXKey<concept.data.SWAssetFolder> SUB_FOLDERS = new ERXKey<concept.data.SWAssetFolder>("subFolders");

  // Attributes
  public static final String ID_KEY = ID.key();
  public static final String INHERITS_PRIVILEGES_KEY = INHERITS_PRIVILEGES.key();
  public static final String NAME_KEY = NAME.key();
  public static final String PARENT_FOLDER_ID_KEY = PARENT_FOLDER_ID.key();
  // Relationships
  public static final String PARENT_FOLDER_KEY = PARENT_FOLDER.key();
  public static final String PICTURES_KEY = PICTURES.key();
  public static final String SUB_FOLDERS_KEY = SUB_FOLDERS.key();

  private static Logger LOG = Logger.getLogger(_SWAssetFolder.class);

  public concept.data.SWAssetFolder localInstanceIn(EOEditingContext editingContext) {
    concept.data.SWAssetFolder localInstance = (concept.data.SWAssetFolder)EOUtilities.localInstanceOfObject(editingContext, this);
    if (localInstance == null) {
      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
    }
    return localInstance;
  }

  public Integer id() {
    return (Integer) storedValueForKey(_SWAssetFolder.ID_KEY);
  }

  public void setId(Integer value) {
    if (_SWAssetFolder.LOG.isDebugEnabled()) {
    	_SWAssetFolder.LOG.debug( "updating id from " + id() + " to " + value);
    }
    takeStoredValueForKey(value, _SWAssetFolder.ID_KEY);
  }

  public Integer inheritsPrivileges() {
    return (Integer) storedValueForKey(_SWAssetFolder.INHERITS_PRIVILEGES_KEY);
  }

  public void setInheritsPrivileges(Integer value) {
    if (_SWAssetFolder.LOG.isDebugEnabled()) {
    	_SWAssetFolder.LOG.debug( "updating inheritsPrivileges from " + inheritsPrivileges() + " to " + value);
    }
    takeStoredValueForKey(value, _SWAssetFolder.INHERITS_PRIVILEGES_KEY);
  }

  public String name() {
    return (String) storedValueForKey(_SWAssetFolder.NAME_KEY);
  }

  public void setName(String value) {
    if (_SWAssetFolder.LOG.isDebugEnabled()) {
    	_SWAssetFolder.LOG.debug( "updating name from " + name() + " to " + value);
    }
    takeStoredValueForKey(value, _SWAssetFolder.NAME_KEY);
  }

  public Integer parentFolderID() {
    return (Integer) storedValueForKey(_SWAssetFolder.PARENT_FOLDER_ID_KEY);
  }

  public void setParentFolderID(Integer value) {
    if (_SWAssetFolder.LOG.isDebugEnabled()) {
    	_SWAssetFolder.LOG.debug( "updating parentFolderID from " + parentFolderID() + " to " + value);
    }
    takeStoredValueForKey(value, _SWAssetFolder.PARENT_FOLDER_ID_KEY);
  }

  public concept.data.SWAssetFolder parentFolder() {
    return (concept.data.SWAssetFolder)storedValueForKey(_SWAssetFolder.PARENT_FOLDER_KEY);
  }
  
  public void setParentFolder(concept.data.SWAssetFolder value) {
    takeStoredValueForKey(value, _SWAssetFolder.PARENT_FOLDER_KEY);
  }

  public void setParentFolderRelationship(concept.data.SWAssetFolder value) {
    if (_SWAssetFolder.LOG.isDebugEnabled()) {
      _SWAssetFolder.LOG.debug("updating parentFolder from " + parentFolder() + " to " + value);
    }
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
    	setParentFolder(value);
    }
    else if (value == null) {
    	concept.data.SWAssetFolder oldValue = parentFolder();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, _SWAssetFolder.PARENT_FOLDER_KEY);
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, _SWAssetFolder.PARENT_FOLDER_KEY);
    }
  }
  
  public NSArray<concept.data.SWPicture> pictures() {
    return (NSArray<concept.data.SWPicture>)storedValueForKey(_SWAssetFolder.PICTURES_KEY);
  }

  public NSArray<concept.data.SWPicture> pictures(EOQualifier qualifier) {
    return pictures(qualifier, null, false);
  }

  public NSArray<concept.data.SWPicture> pictures(EOQualifier qualifier, boolean fetch) {
    return pictures(qualifier, null, fetch);
  }

  public NSArray<concept.data.SWPicture> pictures(EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings, boolean fetch) {
    NSArray<concept.data.SWPicture> results;
    if (fetch) {
      EOQualifier fullQualifier;
      EOQualifier inverseQualifier = new EOKeyValueQualifier(concept.data.SWPicture.FOLDER_KEY, EOQualifier.QualifierOperatorEqual, this);
    	
      if (qualifier == null) {
        fullQualifier = inverseQualifier;
      }
      else {
        NSMutableArray<EOQualifier> qualifiers = new NSMutableArray<EOQualifier>();
        qualifiers.addObject(qualifier);
        qualifiers.addObject(inverseQualifier);
        fullQualifier = new EOAndQualifier(qualifiers);
      }

      results = concept.data.SWPicture.fetchSWPictures(editingContext(), fullQualifier, sortOrderings);
    }
    else {
      results = pictures();
      if (qualifier != null) {
        results = (NSArray<concept.data.SWPicture>)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray<concept.data.SWPicture>)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    }
    return results;
  }
  
  public void addToPictures(concept.data.SWPicture object) {
    includeObjectIntoPropertyWithKey(object, _SWAssetFolder.PICTURES_KEY);
  }

  public void removeFromPictures(concept.data.SWPicture object) {
    excludeObjectFromPropertyWithKey(object, _SWAssetFolder.PICTURES_KEY);
  }

  public void addToPicturesRelationship(concept.data.SWPicture object) {
    if (_SWAssetFolder.LOG.isDebugEnabled()) {
      _SWAssetFolder.LOG.debug("adding " + object + " to pictures relationship");
    }
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
    	addToPictures(object);
    }
    else {
    	addObjectToBothSidesOfRelationshipWithKey(object, _SWAssetFolder.PICTURES_KEY);
    }
  }

  public void removeFromPicturesRelationship(concept.data.SWPicture object) {
    if (_SWAssetFolder.LOG.isDebugEnabled()) {
      _SWAssetFolder.LOG.debug("removing " + object + " from pictures relationship");
    }
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
    	removeFromPictures(object);
    }
    else {
    	removeObjectFromBothSidesOfRelationshipWithKey(object, _SWAssetFolder.PICTURES_KEY);
    }
  }

  public concept.data.SWPicture createPicturesRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName( concept.data.SWPicture.ENTITY_NAME );
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, _SWAssetFolder.PICTURES_KEY);
    return (concept.data.SWPicture) eo;
  }

  public void deletePicturesRelationship(concept.data.SWPicture object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, _SWAssetFolder.PICTURES_KEY);
  }

  public void deleteAllPicturesRelationships() {
    Enumeration<concept.data.SWPicture> objects = pictures().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deletePicturesRelationship(objects.nextElement());
    }
  }

  public NSArray<concept.data.SWAssetFolder> subFolders() {
    return (NSArray<concept.data.SWAssetFolder>)storedValueForKey(_SWAssetFolder.SUB_FOLDERS_KEY);
  }

  public NSArray<concept.data.SWAssetFolder> subFolders(EOQualifier qualifier) {
    return subFolders(qualifier, null, false);
  }

  public NSArray<concept.data.SWAssetFolder> subFolders(EOQualifier qualifier, boolean fetch) {
    return subFolders(qualifier, null, fetch);
  }

  public NSArray<concept.data.SWAssetFolder> subFolders(EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings, boolean fetch) {
    NSArray<concept.data.SWAssetFolder> results;
    if (fetch) {
      EOQualifier fullQualifier;
      EOQualifier inverseQualifier = new EOKeyValueQualifier(concept.data.SWAssetFolder.PARENT_FOLDER_KEY, EOQualifier.QualifierOperatorEqual, this);
    	
      if (qualifier == null) {
        fullQualifier = inverseQualifier;
      }
      else {
        NSMutableArray<EOQualifier> qualifiers = new NSMutableArray<EOQualifier>();
        qualifiers.addObject(qualifier);
        qualifiers.addObject(inverseQualifier);
        fullQualifier = new EOAndQualifier(qualifiers);
      }

      results = concept.data.SWAssetFolder.fetchSWAssetFolders(editingContext(), fullQualifier, sortOrderings);
    }
    else {
      results = subFolders();
      if (qualifier != null) {
        results = (NSArray<concept.data.SWAssetFolder>)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray<concept.data.SWAssetFolder>)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    }
    return results;
  }
  
  public void addToSubFolders(concept.data.SWAssetFolder object) {
    includeObjectIntoPropertyWithKey(object, _SWAssetFolder.SUB_FOLDERS_KEY);
  }

  public void removeFromSubFolders(concept.data.SWAssetFolder object) {
    excludeObjectFromPropertyWithKey(object, _SWAssetFolder.SUB_FOLDERS_KEY);
  }

  public void addToSubFoldersRelationship(concept.data.SWAssetFolder object) {
    if (_SWAssetFolder.LOG.isDebugEnabled()) {
      _SWAssetFolder.LOG.debug("adding " + object + " to subFolders relationship");
    }
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
    	addToSubFolders(object);
    }
    else {
    	addObjectToBothSidesOfRelationshipWithKey(object, _SWAssetFolder.SUB_FOLDERS_KEY);
    }
  }

  public void removeFromSubFoldersRelationship(concept.data.SWAssetFolder object) {
    if (_SWAssetFolder.LOG.isDebugEnabled()) {
      _SWAssetFolder.LOG.debug("removing " + object + " from subFolders relationship");
    }
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
    	removeFromSubFolders(object);
    }
    else {
    	removeObjectFromBothSidesOfRelationshipWithKey(object, _SWAssetFolder.SUB_FOLDERS_KEY);
    }
  }

  public concept.data.SWAssetFolder createSubFoldersRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName( concept.data.SWAssetFolder.ENTITY_NAME );
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, _SWAssetFolder.SUB_FOLDERS_KEY);
    return (concept.data.SWAssetFolder) eo;
  }

  public void deleteSubFoldersRelationship(concept.data.SWAssetFolder object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, _SWAssetFolder.SUB_FOLDERS_KEY);
    editingContext().deleteObject(object);
  }

  public void deleteAllSubFoldersRelationships() {
    Enumeration<concept.data.SWAssetFolder> objects = subFolders().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteSubFoldersRelationship(objects.nextElement());
    }
  }


  public static concept.data.SWAssetFolder createSWAssetFolder(EOEditingContext editingContext, Integer id
) {
    concept.data.SWAssetFolder eo = (concept.data.SWAssetFolder) EOUtilities.createAndInsertInstance(editingContext, _SWAssetFolder.ENTITY_NAME);    
		eo.setId(id);
    return eo;
  }

  public static ERXFetchSpecification<concept.data.SWAssetFolder> fetchSpec() {
    return new ERXFetchSpecification<concept.data.SWAssetFolder>(_SWAssetFolder.ENTITY_NAME, null, null, false, true, null);
  }

  public static NSArray<concept.data.SWAssetFolder> fetchAllSWAssetFolders(EOEditingContext editingContext) {
    return _SWAssetFolder.fetchAllSWAssetFolders(editingContext, null);
  }

  public static NSArray<concept.data.SWAssetFolder> fetchAllSWAssetFolders(EOEditingContext editingContext, NSArray<EOSortOrdering> sortOrderings) {
    return _SWAssetFolder.fetchSWAssetFolders(editingContext, null, sortOrderings);
  }

  public static NSArray<concept.data.SWAssetFolder> fetchSWAssetFolders(EOEditingContext editingContext, EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings) {
    ERXFetchSpecification<concept.data.SWAssetFolder> fetchSpec = new ERXFetchSpecification<concept.data.SWAssetFolder>(_SWAssetFolder.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray<concept.data.SWAssetFolder> eoObjects = fetchSpec.fetchObjects(editingContext);
    return eoObjects;
  }

  public static concept.data.SWAssetFolder fetchSWAssetFolder(EOEditingContext editingContext, String keyName, Object value) {
    return _SWAssetFolder.fetchSWAssetFolder(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static concept.data.SWAssetFolder fetchSWAssetFolder(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray<concept.data.SWAssetFolder> eoObjects = _SWAssetFolder.fetchSWAssetFolders(editingContext, qualifier, null);
    concept.data.SWAssetFolder eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one SWAssetFolder that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static concept.data.SWAssetFolder fetchRequiredSWAssetFolder(EOEditingContext editingContext, String keyName, Object value) {
    return _SWAssetFolder.fetchRequiredSWAssetFolder(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static concept.data.SWAssetFolder fetchRequiredSWAssetFolder(EOEditingContext editingContext, EOQualifier qualifier) {
    concept.data.SWAssetFolder eoObject = _SWAssetFolder.fetchSWAssetFolder(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no SWAssetFolder that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static concept.data.SWAssetFolder localInstanceIn(EOEditingContext editingContext, concept.data.SWAssetFolder eo) {
    concept.data.SWAssetFolder localInstance = (eo == null) ? null : ERXEOControlUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
