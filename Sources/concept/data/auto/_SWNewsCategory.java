// DO NOT EDIT.  Make changes to concept.data.SWNewsCategory.java instead.
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
public abstract class _SWNewsCategory extends  ERXGenericRecord {
  public static final String ENTITY_NAME = "SWNewsCategory";

  // Attribute Keys
  public static final ERXKey<Integer> ID = new ERXKey<Integer>("id");
  public static final ERXKey<Integer> INHERITS_PRIVILEGES = new ERXKey<Integer>("inheritsPrivileges");
  public static final ERXKey<String> NAME = new ERXKey<String>("name");
  public static final ERXKey<Integer> PARENT_CATEGORY_ID = new ERXKey<Integer>("parentCategoryID");
  public static final ERXKey<String> RSS_LINK = new ERXKey<String>("rssLink");
  public static final ERXKey<String> RSS_PAGE_NAME = new ERXKey<String>("rssPageName");
  public static final ERXKey<String> RSS_TITLE = new ERXKey<String>("rssTitle");
  // Relationship Keys
  public static final ERXKey<concept.data.SWNewsItem> NEWS = new ERXKey<concept.data.SWNewsItem>("news");
  public static final ERXKey<concept.data.SWNewsCategory> PARENT_FOLDER = new ERXKey<concept.data.SWNewsCategory>("parentFolder");
  public static final ERXKey<concept.data.SWNewsCategory> SUB_FOLDERS = new ERXKey<concept.data.SWNewsCategory>("subFolders");

  // Attributes
  public static final String ID_KEY = ID.key();
  public static final String INHERITS_PRIVILEGES_KEY = INHERITS_PRIVILEGES.key();
  public static final String NAME_KEY = NAME.key();
  public static final String PARENT_CATEGORY_ID_KEY = PARENT_CATEGORY_ID.key();
  public static final String RSS_LINK_KEY = RSS_LINK.key();
  public static final String RSS_PAGE_NAME_KEY = RSS_PAGE_NAME.key();
  public static final String RSS_TITLE_KEY = RSS_TITLE.key();
  // Relationships
  public static final String NEWS_KEY = NEWS.key();
  public static final String PARENT_FOLDER_KEY = PARENT_FOLDER.key();
  public static final String SUB_FOLDERS_KEY = SUB_FOLDERS.key();

  private static Logger LOG = Logger.getLogger(_SWNewsCategory.class);

  public concept.data.SWNewsCategory localInstanceIn(EOEditingContext editingContext) {
    concept.data.SWNewsCategory localInstance = (concept.data.SWNewsCategory)EOUtilities.localInstanceOfObject(editingContext, this);
    if (localInstance == null) {
      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
    }
    return localInstance;
  }

  public Integer id() {
    return (Integer) storedValueForKey(_SWNewsCategory.ID_KEY);
  }

  public void setId(Integer value) {
    if (_SWNewsCategory.LOG.isDebugEnabled()) {
    	_SWNewsCategory.LOG.debug( "updating id from " + id() + " to " + value);
    }
    takeStoredValueForKey(value, _SWNewsCategory.ID_KEY);
  }

  public Integer inheritsPrivileges() {
    return (Integer) storedValueForKey(_SWNewsCategory.INHERITS_PRIVILEGES_KEY);
  }

  public void setInheritsPrivileges(Integer value) {
    if (_SWNewsCategory.LOG.isDebugEnabled()) {
    	_SWNewsCategory.LOG.debug( "updating inheritsPrivileges from " + inheritsPrivileges() + " to " + value);
    }
    takeStoredValueForKey(value, _SWNewsCategory.INHERITS_PRIVILEGES_KEY);
  }

  public String name() {
    return (String) storedValueForKey(_SWNewsCategory.NAME_KEY);
  }

  public void setName(String value) {
    if (_SWNewsCategory.LOG.isDebugEnabled()) {
    	_SWNewsCategory.LOG.debug( "updating name from " + name() + " to " + value);
    }
    takeStoredValueForKey(value, _SWNewsCategory.NAME_KEY);
  }

  public Integer parentCategoryID() {
    return (Integer) storedValueForKey(_SWNewsCategory.PARENT_CATEGORY_ID_KEY);
  }

  public void setParentCategoryID(Integer value) {
    if (_SWNewsCategory.LOG.isDebugEnabled()) {
    	_SWNewsCategory.LOG.debug( "updating parentCategoryID from " + parentCategoryID() + " to " + value);
    }
    takeStoredValueForKey(value, _SWNewsCategory.PARENT_CATEGORY_ID_KEY);
  }

  public String rssLink() {
    return (String) storedValueForKey(_SWNewsCategory.RSS_LINK_KEY);
  }

  public void setRssLink(String value) {
    if (_SWNewsCategory.LOG.isDebugEnabled()) {
    	_SWNewsCategory.LOG.debug( "updating rssLink from " + rssLink() + " to " + value);
    }
    takeStoredValueForKey(value, _SWNewsCategory.RSS_LINK_KEY);
  }

  public String rssPageName() {
    return (String) storedValueForKey(_SWNewsCategory.RSS_PAGE_NAME_KEY);
  }

  public void setRssPageName(String value) {
    if (_SWNewsCategory.LOG.isDebugEnabled()) {
    	_SWNewsCategory.LOG.debug( "updating rssPageName from " + rssPageName() + " to " + value);
    }
    takeStoredValueForKey(value, _SWNewsCategory.RSS_PAGE_NAME_KEY);
  }

  public String rssTitle() {
    return (String) storedValueForKey(_SWNewsCategory.RSS_TITLE_KEY);
  }

  public void setRssTitle(String value) {
    if (_SWNewsCategory.LOG.isDebugEnabled()) {
    	_SWNewsCategory.LOG.debug( "updating rssTitle from " + rssTitle() + " to " + value);
    }
    takeStoredValueForKey(value, _SWNewsCategory.RSS_TITLE_KEY);
  }

  public concept.data.SWNewsCategory parentFolder() {
    return (concept.data.SWNewsCategory)storedValueForKey(_SWNewsCategory.PARENT_FOLDER_KEY);
  }
  
  public void setParentFolder(concept.data.SWNewsCategory value) {
    takeStoredValueForKey(value, _SWNewsCategory.PARENT_FOLDER_KEY);
  }

  public void setParentFolderRelationship(concept.data.SWNewsCategory value) {
    if (_SWNewsCategory.LOG.isDebugEnabled()) {
      _SWNewsCategory.LOG.debug("updating parentFolder from " + parentFolder() + " to " + value);
    }
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
    	setParentFolder(value);
    }
    else if (value == null) {
    	concept.data.SWNewsCategory oldValue = parentFolder();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, _SWNewsCategory.PARENT_FOLDER_KEY);
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, _SWNewsCategory.PARENT_FOLDER_KEY);
    }
  }
  
  public NSArray<concept.data.SWNewsItem> news() {
    return (NSArray<concept.data.SWNewsItem>)storedValueForKey(_SWNewsCategory.NEWS_KEY);
  }

  public NSArray<concept.data.SWNewsItem> news(EOQualifier qualifier) {
    return news(qualifier, null, false);
  }

  public NSArray<concept.data.SWNewsItem> news(EOQualifier qualifier, boolean fetch) {
    return news(qualifier, null, fetch);
  }

  public NSArray<concept.data.SWNewsItem> news(EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings, boolean fetch) {
    NSArray<concept.data.SWNewsItem> results;
    if (fetch) {
      EOQualifier fullQualifier;
      EOQualifier inverseQualifier = new EOKeyValueQualifier(concept.data.SWNewsItem.CATEGORY_KEY, EOQualifier.QualifierOperatorEqual, this);
    	
      if (qualifier == null) {
        fullQualifier = inverseQualifier;
      }
      else {
        NSMutableArray<EOQualifier> qualifiers = new NSMutableArray<EOQualifier>();
        qualifiers.addObject(qualifier);
        qualifiers.addObject(inverseQualifier);
        fullQualifier = new EOAndQualifier(qualifiers);
      }

      results = concept.data.SWNewsItem.fetchSWNewsItems(editingContext(), fullQualifier, sortOrderings);
    }
    else {
      results = news();
      if (qualifier != null) {
        results = (NSArray<concept.data.SWNewsItem>)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray<concept.data.SWNewsItem>)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    }
    return results;
  }
  
  public void addToNews(concept.data.SWNewsItem object) {
    includeObjectIntoPropertyWithKey(object, _SWNewsCategory.NEWS_KEY);
  }

  public void removeFromNews(concept.data.SWNewsItem object) {
    excludeObjectFromPropertyWithKey(object, _SWNewsCategory.NEWS_KEY);
  }

  public void addToNewsRelationship(concept.data.SWNewsItem object) {
    if (_SWNewsCategory.LOG.isDebugEnabled()) {
      _SWNewsCategory.LOG.debug("adding " + object + " to news relationship");
    }
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
    	addToNews(object);
    }
    else {
    	addObjectToBothSidesOfRelationshipWithKey(object, _SWNewsCategory.NEWS_KEY);
    }
  }

  public void removeFromNewsRelationship(concept.data.SWNewsItem object) {
    if (_SWNewsCategory.LOG.isDebugEnabled()) {
      _SWNewsCategory.LOG.debug("removing " + object + " from news relationship");
    }
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
    	removeFromNews(object);
    }
    else {
    	removeObjectFromBothSidesOfRelationshipWithKey(object, _SWNewsCategory.NEWS_KEY);
    }
  }

  public concept.data.SWNewsItem createNewsRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName( concept.data.SWNewsItem.ENTITY_NAME );
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, _SWNewsCategory.NEWS_KEY);
    return (concept.data.SWNewsItem) eo;
  }

  public void deleteNewsRelationship(concept.data.SWNewsItem object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, _SWNewsCategory.NEWS_KEY);
    editingContext().deleteObject(object);
  }

  public void deleteAllNewsRelationships() {
    Enumeration<concept.data.SWNewsItem> objects = news().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteNewsRelationship(objects.nextElement());
    }
  }

  public NSArray<concept.data.SWNewsCategory> subFolders() {
    return (NSArray<concept.data.SWNewsCategory>)storedValueForKey(_SWNewsCategory.SUB_FOLDERS_KEY);
  }

  public NSArray<concept.data.SWNewsCategory> subFolders(EOQualifier qualifier) {
    return subFolders(qualifier, null, false);
  }

  public NSArray<concept.data.SWNewsCategory> subFolders(EOQualifier qualifier, boolean fetch) {
    return subFolders(qualifier, null, fetch);
  }

  public NSArray<concept.data.SWNewsCategory> subFolders(EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings, boolean fetch) {
    NSArray<concept.data.SWNewsCategory> results;
    if (fetch) {
      EOQualifier fullQualifier;
      EOQualifier inverseQualifier = new EOKeyValueQualifier(concept.data.SWNewsCategory.PARENT_FOLDER_KEY, EOQualifier.QualifierOperatorEqual, this);
    	
      if (qualifier == null) {
        fullQualifier = inverseQualifier;
      }
      else {
        NSMutableArray<EOQualifier> qualifiers = new NSMutableArray<EOQualifier>();
        qualifiers.addObject(qualifier);
        qualifiers.addObject(inverseQualifier);
        fullQualifier = new EOAndQualifier(qualifiers);
      }

      results = concept.data.SWNewsCategory.fetchSWNewsCategories(editingContext(), fullQualifier, sortOrderings);
    }
    else {
      results = subFolders();
      if (qualifier != null) {
        results = (NSArray<concept.data.SWNewsCategory>)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray<concept.data.SWNewsCategory>)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    }
    return results;
  }
  
  public void addToSubFolders(concept.data.SWNewsCategory object) {
    includeObjectIntoPropertyWithKey(object, _SWNewsCategory.SUB_FOLDERS_KEY);
  }

  public void removeFromSubFolders(concept.data.SWNewsCategory object) {
    excludeObjectFromPropertyWithKey(object, _SWNewsCategory.SUB_FOLDERS_KEY);
  }

  public void addToSubFoldersRelationship(concept.data.SWNewsCategory object) {
    if (_SWNewsCategory.LOG.isDebugEnabled()) {
      _SWNewsCategory.LOG.debug("adding " + object + " to subFolders relationship");
    }
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
    	addToSubFolders(object);
    }
    else {
    	addObjectToBothSidesOfRelationshipWithKey(object, _SWNewsCategory.SUB_FOLDERS_KEY);
    }
  }

  public void removeFromSubFoldersRelationship(concept.data.SWNewsCategory object) {
    if (_SWNewsCategory.LOG.isDebugEnabled()) {
      _SWNewsCategory.LOG.debug("removing " + object + " from subFolders relationship");
    }
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
    	removeFromSubFolders(object);
    }
    else {
    	removeObjectFromBothSidesOfRelationshipWithKey(object, _SWNewsCategory.SUB_FOLDERS_KEY);
    }
  }

  public concept.data.SWNewsCategory createSubFoldersRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName( concept.data.SWNewsCategory.ENTITY_NAME );
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, _SWNewsCategory.SUB_FOLDERS_KEY);
    return (concept.data.SWNewsCategory) eo;
  }

  public void deleteSubFoldersRelationship(concept.data.SWNewsCategory object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, _SWNewsCategory.SUB_FOLDERS_KEY);
    editingContext().deleteObject(object);
  }

  public void deleteAllSubFoldersRelationships() {
    Enumeration<concept.data.SWNewsCategory> objects = subFolders().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteSubFoldersRelationship(objects.nextElement());
    }
  }


  public static concept.data.SWNewsCategory createSWNewsCategory(EOEditingContext editingContext, Integer id
) {
    concept.data.SWNewsCategory eo = (concept.data.SWNewsCategory) EOUtilities.createAndInsertInstance(editingContext, _SWNewsCategory.ENTITY_NAME);    
		eo.setId(id);
    return eo;
  }

  public static ERXFetchSpecification<concept.data.SWNewsCategory> fetchSpec() {
    return new ERXFetchSpecification<concept.data.SWNewsCategory>(_SWNewsCategory.ENTITY_NAME, null, null, false, true, null);
  }

  public static NSArray<concept.data.SWNewsCategory> fetchAllSWNewsCategories(EOEditingContext editingContext) {
    return _SWNewsCategory.fetchAllSWNewsCategories(editingContext, null);
  }

  public static NSArray<concept.data.SWNewsCategory> fetchAllSWNewsCategories(EOEditingContext editingContext, NSArray<EOSortOrdering> sortOrderings) {
    return _SWNewsCategory.fetchSWNewsCategories(editingContext, null, sortOrderings);
  }

  public static NSArray<concept.data.SWNewsCategory> fetchSWNewsCategories(EOEditingContext editingContext, EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings) {
    ERXFetchSpecification<concept.data.SWNewsCategory> fetchSpec = new ERXFetchSpecification<concept.data.SWNewsCategory>(_SWNewsCategory.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray<concept.data.SWNewsCategory> eoObjects = fetchSpec.fetchObjects(editingContext);
    return eoObjects;
  }

  public static concept.data.SWNewsCategory fetchSWNewsCategory(EOEditingContext editingContext, String keyName, Object value) {
    return _SWNewsCategory.fetchSWNewsCategory(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static concept.data.SWNewsCategory fetchSWNewsCategory(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray<concept.data.SWNewsCategory> eoObjects = _SWNewsCategory.fetchSWNewsCategories(editingContext, qualifier, null);
    concept.data.SWNewsCategory eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one SWNewsCategory that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static concept.data.SWNewsCategory fetchRequiredSWNewsCategory(EOEditingContext editingContext, String keyName, Object value) {
    return _SWNewsCategory.fetchRequiredSWNewsCategory(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static concept.data.SWNewsCategory fetchRequiredSWNewsCategory(EOEditingContext editingContext, EOQualifier qualifier) {
    concept.data.SWNewsCategory eoObject = _SWNewsCategory.fetchSWNewsCategory(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no SWNewsCategory that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static concept.data.SWNewsCategory localInstanceIn(EOEditingContext editingContext, concept.data.SWNewsCategory eo) {
    concept.data.SWNewsCategory localInstance = (eo == null) ? null : ERXEOControlUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
