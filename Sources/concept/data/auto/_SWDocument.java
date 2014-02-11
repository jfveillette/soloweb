// DO NOT EDIT.  Make changes to concept.data.SWDocument.java instead.
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
public abstract class _SWDocument extends  ERXGenericRecord {
  public static final String ENTITY_NAME = "SWDocument";

  // Attribute Keys
  public static final ERXKey<NSTimestamp> CREATION_DATE = new ERXKey<NSTimestamp>("creationDate");
  public static final ERXKey<String> CREDIT = new ERXKey<String>("credit");
  public static final ERXKey<Integer> DOCUMENT_FOLDER_ID = new ERXKey<Integer>("documentFolderID");
  public static final ERXKey<String> EXTENSION = new ERXKey<String>("extension");
  public static final ERXKey<Integer> ID = new ERXKey<Integer>("id");
  public static final ERXKey<String> LINK_KEY = new ERXKey<String>("linkKey");
  public static final ERXKey<NSTimestamp> MODIFICATION_DATE = new ERXKey<NSTimestamp>("modificationDate");
  public static final ERXKey<String> NAME = new ERXKey<String>("name");
  public static final ERXKey<String> STORED_DIRECTORY = new ERXKey<String>("storedDirectory");
  public static final ERXKey<String> STORED_FILENAME = new ERXKey<String>("storedFilename");
  public static final ERXKey<String> TEXT = new ERXKey<String>("text");
  public static final ERXKey<String> UUID = new ERXKey<String>("uuid");
  // Relationship Keys
  public static final ERXKey<concept.data.SWDocumentFolder> FOLDER = new ERXKey<concept.data.SWDocumentFolder>("folder");
  public static final ERXKey<concept.data.SWDocumentLink> LINKS = new ERXKey<concept.data.SWDocumentLink>("links");

  // Attributes
  public static final String CREATION_DATE_KEY = CREATION_DATE.key();
  public static final String CREDIT_KEY = CREDIT.key();
  public static final String DOCUMENT_FOLDER_ID_KEY = DOCUMENT_FOLDER_ID.key();
  public static final String EXTENSION_KEY = EXTENSION.key();
  public static final String ID_KEY = ID.key();
  public static final String LINK_KEY_KEY = LINK_KEY.key();
  public static final String MODIFICATION_DATE_KEY = MODIFICATION_DATE.key();
  public static final String NAME_KEY = NAME.key();
  public static final String STORED_DIRECTORY_KEY = STORED_DIRECTORY.key();
  public static final String STORED_FILENAME_KEY = STORED_FILENAME.key();
  public static final String TEXT_KEY = TEXT.key();
  public static final String UUID_KEY = UUID.key();
  // Relationships
  public static final String FOLDER_KEY = FOLDER.key();
  public static final String LINKS_KEY = LINKS.key();

  private static Logger LOG = Logger.getLogger(_SWDocument.class);

  public concept.data.SWDocument localInstanceIn(EOEditingContext editingContext) {
    concept.data.SWDocument localInstance = (concept.data.SWDocument)EOUtilities.localInstanceOfObject(editingContext, this);
    if (localInstance == null) {
      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
    }
    return localInstance;
  }

  public NSTimestamp creationDate() {
    return (NSTimestamp) storedValueForKey(_SWDocument.CREATION_DATE_KEY);
  }

  public void setCreationDate(NSTimestamp value) {
    if (_SWDocument.LOG.isDebugEnabled()) {
    	_SWDocument.LOG.debug( "updating creationDate from " + creationDate() + " to " + value);
    }
    takeStoredValueForKey(value, _SWDocument.CREATION_DATE_KEY);
  }

  public String credit() {
    return (String) storedValueForKey(_SWDocument.CREDIT_KEY);
  }

  public void setCredit(String value) {
    if (_SWDocument.LOG.isDebugEnabled()) {
    	_SWDocument.LOG.debug( "updating credit from " + credit() + " to " + value);
    }
    takeStoredValueForKey(value, _SWDocument.CREDIT_KEY);
  }

  public Integer documentFolderID() {
    return (Integer) storedValueForKey(_SWDocument.DOCUMENT_FOLDER_ID_KEY);
  }

  public void setDocumentFolderID(Integer value) {
    if (_SWDocument.LOG.isDebugEnabled()) {
    	_SWDocument.LOG.debug( "updating documentFolderID from " + documentFolderID() + " to " + value);
    }
    takeStoredValueForKey(value, _SWDocument.DOCUMENT_FOLDER_ID_KEY);
  }

  public String extension() {
    return (String) storedValueForKey(_SWDocument.EXTENSION_KEY);
  }

  public void setExtension(String value) {
    if (_SWDocument.LOG.isDebugEnabled()) {
    	_SWDocument.LOG.debug( "updating extension from " + extension() + " to " + value);
    }
    takeStoredValueForKey(value, _SWDocument.EXTENSION_KEY);
  }

  public Integer id() {
    return (Integer) storedValueForKey(_SWDocument.ID_KEY);
  }

  public void setId(Integer value) {
    if (_SWDocument.LOG.isDebugEnabled()) {
    	_SWDocument.LOG.debug( "updating id from " + id() + " to " + value);
    }
    takeStoredValueForKey(value, _SWDocument.ID_KEY);
  }

  public String linkKey() {
    return (String) storedValueForKey(_SWDocument.LINK_KEY_KEY);
  }

  public void setLinkKey(String value) {
    if (_SWDocument.LOG.isDebugEnabled()) {
    	_SWDocument.LOG.debug( "updating linkKey from " + linkKey() + " to " + value);
    }
    takeStoredValueForKey(value, _SWDocument.LINK_KEY_KEY);
  }

  public NSTimestamp modificationDate() {
    return (NSTimestamp) storedValueForKey(_SWDocument.MODIFICATION_DATE_KEY);
  }

  public void setModificationDate(NSTimestamp value) {
    if (_SWDocument.LOG.isDebugEnabled()) {
    	_SWDocument.LOG.debug( "updating modificationDate from " + modificationDate() + " to " + value);
    }
    takeStoredValueForKey(value, _SWDocument.MODIFICATION_DATE_KEY);
  }

  public String name() {
    return (String) storedValueForKey(_SWDocument.NAME_KEY);
  }

  public void setName(String value) {
    if (_SWDocument.LOG.isDebugEnabled()) {
    	_SWDocument.LOG.debug( "updating name from " + name() + " to " + value);
    }
    takeStoredValueForKey(value, _SWDocument.NAME_KEY);
  }

  public String storedDirectory() {
    return (String) storedValueForKey(_SWDocument.STORED_DIRECTORY_KEY);
  }

  public void setStoredDirectory(String value) {
    if (_SWDocument.LOG.isDebugEnabled()) {
    	_SWDocument.LOG.debug( "updating storedDirectory from " + storedDirectory() + " to " + value);
    }
    takeStoredValueForKey(value, _SWDocument.STORED_DIRECTORY_KEY);
  }

  public String storedFilename() {
    return (String) storedValueForKey(_SWDocument.STORED_FILENAME_KEY);
  }

  public void setStoredFilename(String value) {
    if (_SWDocument.LOG.isDebugEnabled()) {
    	_SWDocument.LOG.debug( "updating storedFilename from " + storedFilename() + " to " + value);
    }
    takeStoredValueForKey(value, _SWDocument.STORED_FILENAME_KEY);
  }

  public String text() {
    return (String) storedValueForKey(_SWDocument.TEXT_KEY);
  }

  public void setText(String value) {
    if (_SWDocument.LOG.isDebugEnabled()) {
    	_SWDocument.LOG.debug( "updating text from " + text() + " to " + value);
    }
    takeStoredValueForKey(value, _SWDocument.TEXT_KEY);
  }

  public String uuid() {
    return (String) storedValueForKey(_SWDocument.UUID_KEY);
  }

  public void setUuid(String value) {
    if (_SWDocument.LOG.isDebugEnabled()) {
    	_SWDocument.LOG.debug( "updating uuid from " + uuid() + " to " + value);
    }
    takeStoredValueForKey(value, _SWDocument.UUID_KEY);
  }

  public concept.data.SWDocumentFolder folder() {
    return (concept.data.SWDocumentFolder)storedValueForKey(_SWDocument.FOLDER_KEY);
  }
  
  public void setFolder(concept.data.SWDocumentFolder value) {
    takeStoredValueForKey(value, _SWDocument.FOLDER_KEY);
  }

  public void setFolderRelationship(concept.data.SWDocumentFolder value) {
    if (_SWDocument.LOG.isDebugEnabled()) {
      _SWDocument.LOG.debug("updating folder from " + folder() + " to " + value);
    }
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
    	setFolder(value);
    }
    else if (value == null) {
    	concept.data.SWDocumentFolder oldValue = folder();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, _SWDocument.FOLDER_KEY);
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, _SWDocument.FOLDER_KEY);
    }
  }
  
  public NSArray<concept.data.SWDocumentLink> links() {
    return (NSArray<concept.data.SWDocumentLink>)storedValueForKey(_SWDocument.LINKS_KEY);
  }

  public NSArray<concept.data.SWDocumentLink> links(EOQualifier qualifier) {
    return links(qualifier, null, false);
  }

  public NSArray<concept.data.SWDocumentLink> links(EOQualifier qualifier, boolean fetch) {
    return links(qualifier, null, fetch);
  }

  public NSArray<concept.data.SWDocumentLink> links(EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings, boolean fetch) {
    NSArray<concept.data.SWDocumentLink> results;
    if (fetch) {
      EOQualifier fullQualifier;
      EOQualifier inverseQualifier = new EOKeyValueQualifier(concept.data.SWDocumentLink.DOCUMENT_KEY, EOQualifier.QualifierOperatorEqual, this);
    	
      if (qualifier == null) {
        fullQualifier = inverseQualifier;
      }
      else {
        NSMutableArray<EOQualifier> qualifiers = new NSMutableArray<EOQualifier>();
        qualifiers.addObject(qualifier);
        qualifiers.addObject(inverseQualifier);
        fullQualifier = new EOAndQualifier(qualifiers);
      }

      results = concept.data.SWDocumentLink.fetchSWDocumentLinks(editingContext(), fullQualifier, sortOrderings);
    }
    else {
      results = links();
      if (qualifier != null) {
        results = (NSArray<concept.data.SWDocumentLink>)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray<concept.data.SWDocumentLink>)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    }
    return results;
  }
  
  public void addToLinks(concept.data.SWDocumentLink object) {
    includeObjectIntoPropertyWithKey(object, _SWDocument.LINKS_KEY);
  }

  public void removeFromLinks(concept.data.SWDocumentLink object) {
    excludeObjectFromPropertyWithKey(object, _SWDocument.LINKS_KEY);
  }

  public void addToLinksRelationship(concept.data.SWDocumentLink object) {
    if (_SWDocument.LOG.isDebugEnabled()) {
      _SWDocument.LOG.debug("adding " + object + " to links relationship");
    }
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
    	addToLinks(object);
    }
    else {
    	addObjectToBothSidesOfRelationshipWithKey(object, _SWDocument.LINKS_KEY);
    }
  }

  public void removeFromLinksRelationship(concept.data.SWDocumentLink object) {
    if (_SWDocument.LOG.isDebugEnabled()) {
      _SWDocument.LOG.debug("removing " + object + " from links relationship");
    }
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
    	removeFromLinks(object);
    }
    else {
    	removeObjectFromBothSidesOfRelationshipWithKey(object, _SWDocument.LINKS_KEY);
    }
  }

  public concept.data.SWDocumentLink createLinksRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName( concept.data.SWDocumentLink.ENTITY_NAME );
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, _SWDocument.LINKS_KEY);
    return (concept.data.SWDocumentLink) eo;
  }

  public void deleteLinksRelationship(concept.data.SWDocumentLink object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, _SWDocument.LINKS_KEY);
    editingContext().deleteObject(object);
  }

  public void deleteAllLinksRelationships() {
    Enumeration<concept.data.SWDocumentLink> objects = links().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteLinksRelationship(objects.nextElement());
    }
  }


  public static concept.data.SWDocument createSWDocument(EOEditingContext editingContext, Integer id
) {
    concept.data.SWDocument eo = (concept.data.SWDocument) EOUtilities.createAndInsertInstance(editingContext, _SWDocument.ENTITY_NAME);    
		eo.setId(id);
    return eo;
  }

  public static ERXFetchSpecification<concept.data.SWDocument> fetchSpec() {
    return new ERXFetchSpecification<concept.data.SWDocument>(_SWDocument.ENTITY_NAME, null, null, false, true, null);
  }

  public static NSArray<concept.data.SWDocument> fetchAllSWDocuments(EOEditingContext editingContext) {
    return _SWDocument.fetchAllSWDocuments(editingContext, null);
  }

  public static NSArray<concept.data.SWDocument> fetchAllSWDocuments(EOEditingContext editingContext, NSArray<EOSortOrdering> sortOrderings) {
    return _SWDocument.fetchSWDocuments(editingContext, null, sortOrderings);
  }

  public static NSArray<concept.data.SWDocument> fetchSWDocuments(EOEditingContext editingContext, EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings) {
    ERXFetchSpecification<concept.data.SWDocument> fetchSpec = new ERXFetchSpecification<concept.data.SWDocument>(_SWDocument.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray<concept.data.SWDocument> eoObjects = fetchSpec.fetchObjects(editingContext);
    return eoObjects;
  }

  public static concept.data.SWDocument fetchSWDocument(EOEditingContext editingContext, String keyName, Object value) {
    return _SWDocument.fetchSWDocument(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static concept.data.SWDocument fetchSWDocument(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray<concept.data.SWDocument> eoObjects = _SWDocument.fetchSWDocuments(editingContext, qualifier, null);
    concept.data.SWDocument eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one SWDocument that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static concept.data.SWDocument fetchRequiredSWDocument(EOEditingContext editingContext, String keyName, Object value) {
    return _SWDocument.fetchRequiredSWDocument(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static concept.data.SWDocument fetchRequiredSWDocument(EOEditingContext editingContext, EOQualifier qualifier) {
    concept.data.SWDocument eoObject = _SWDocument.fetchSWDocument(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no SWDocument that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static concept.data.SWDocument localInstanceIn(EOEditingContext editingContext, concept.data.SWDocument eo) {
    concept.data.SWDocument localInstance = (eo == null) ? null : ERXEOControlUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
