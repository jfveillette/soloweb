// DO NOT EDIT.  Make changes to concept.data.SWNewsItem.java instead.
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
public abstract class _SWNewsItem extends  ERXGenericRecord {
  public static final String ENTITY_NAME = "SWNewsItem";

  // Attribute Keys
  public static final ERXKey<String> AUTHOR = new ERXKey<String>("author");
  public static final ERXKey<NSTimestamp> CREATION_DATE = new ERXKey<NSTimestamp>("creationDate");
  public static final ERXKey<Integer> CRM_NAFN_ID = new ERXKey<Integer>("crmNafnID");
  public static final ERXKey<String> CUSTOM_INFO_DATA = new ERXKey<String>("customInfoData");
  public static final ERXKey<String> CUSTOM_MORE_URL = new ERXKey<String>("customMoreURL");
  public static final ERXKey<NSTimestamp> DATE = new ERXKey<NSTimestamp>("date");
  public static final ERXKey<Integer> ENCODE_BREAKS = new ERXKey<Integer>("encodeBreaks");
  public static final ERXKey<String> EXCERPT = new ERXKey<String>("excerpt");
  public static final ERXKey<String> HEADING = new ERXKey<String>("heading");
  public static final ERXKey<Integer> ID = new ERXKey<Integer>("id");
  public static final ERXKey<NSTimestamp> MODIFICATION_DATE = new ERXKey<NSTimestamp>("modificationDate");
  public static final ERXKey<Integer> NEWS_CATEGORY_ID = new ERXKey<Integer>("newsCategoryID");
  public static final ERXKey<Integer> ORIGINAL_ITEM_ID = new ERXKey<Integer>("originalItemID");
  public static final ERXKey<Integer> PICTURE2_ID = new ERXKey<Integer>("picture2ID");
  public static final ERXKey<Integer> PICTURE_ID = new ERXKey<Integer>("pictureID");
  public static final ERXKey<Integer> PUBLISHED = new ERXKey<Integer>("published");
  public static final ERXKey<String> TEXT = new ERXKey<String>("text");
  public static final ERXKey<NSTimestamp> TIME_IN = new ERXKey<NSTimestamp>("timeIn");
  public static final ERXKey<NSTimestamp> TIME_OUT = new ERXKey<NSTimestamp>("timeOut");
  // Relationship Keys
  public static final ERXKey<concept.data.SWNewsCategory> CATEGORY = new ERXKey<concept.data.SWNewsCategory>("category");
  public static final ERXKey<concept.data.SWUser> CREATED_BY = new ERXKey<concept.data.SWUser>("createdBy");
  public static final ERXKey<concept.data.SWUser> MODIFIED_BY = new ERXKey<concept.data.SWUser>("modifiedBy");
  public static final ERXKey<concept.data.SWPicture> PICTURE = new ERXKey<concept.data.SWPicture>("picture");
  public static final ERXKey<concept.data.SWPicture> PICTURE2 = new ERXKey<concept.data.SWPicture>("picture2");
  public static final ERXKey<concept.data.SWPictureLink> SW_PICTURE_LINKS = new ERXKey<concept.data.SWPictureLink>("swPictureLinks");

  // Attributes
  public static final String AUTHOR_KEY = AUTHOR.key();
  public static final String CREATION_DATE_KEY = CREATION_DATE.key();
  public static final String CRM_NAFN_ID_KEY = CRM_NAFN_ID.key();
  public static final String CUSTOM_INFO_DATA_KEY = CUSTOM_INFO_DATA.key();
  public static final String CUSTOM_MORE_URL_KEY = CUSTOM_MORE_URL.key();
  public static final String DATE_KEY = DATE.key();
  public static final String ENCODE_BREAKS_KEY = ENCODE_BREAKS.key();
  public static final String EXCERPT_KEY = EXCERPT.key();
  public static final String HEADING_KEY = HEADING.key();
  public static final String ID_KEY = ID.key();
  public static final String MODIFICATION_DATE_KEY = MODIFICATION_DATE.key();
  public static final String NEWS_CATEGORY_ID_KEY = NEWS_CATEGORY_ID.key();
  public static final String ORIGINAL_ITEM_ID_KEY = ORIGINAL_ITEM_ID.key();
  public static final String PICTURE2_ID_KEY = PICTURE2_ID.key();
  public static final String PICTURE_ID_KEY = PICTURE_ID.key();
  public static final String PUBLISHED_KEY = PUBLISHED.key();
  public static final String TEXT_KEY = TEXT.key();
  public static final String TIME_IN_KEY = TIME_IN.key();
  public static final String TIME_OUT_KEY = TIME_OUT.key();
  // Relationships
  public static final String CATEGORY_KEY = CATEGORY.key();
  public static final String CREATED_BY_KEY = CREATED_BY.key();
  public static final String MODIFIED_BY_KEY = MODIFIED_BY.key();
  public static final String PICTURE_KEY = PICTURE.key();
  public static final String PICTURE2_KEY = PICTURE2.key();
  public static final String SW_PICTURE_LINKS_KEY = SW_PICTURE_LINKS.key();

  private static Logger LOG = Logger.getLogger(_SWNewsItem.class);

  public concept.data.SWNewsItem localInstanceIn(EOEditingContext editingContext) {
    concept.data.SWNewsItem localInstance = (concept.data.SWNewsItem)EOUtilities.localInstanceOfObject(editingContext, this);
    if (localInstance == null) {
      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
    }
    return localInstance;
  }

  public String author() {
    return (String) storedValueForKey(_SWNewsItem.AUTHOR_KEY);
  }

  public void setAuthor(String value) {
    if (_SWNewsItem.LOG.isDebugEnabled()) {
    	_SWNewsItem.LOG.debug( "updating author from " + author() + " to " + value);
    }
    takeStoredValueForKey(value, _SWNewsItem.AUTHOR_KEY);
  }

  public NSTimestamp creationDate() {
    return (NSTimestamp) storedValueForKey(_SWNewsItem.CREATION_DATE_KEY);
  }

  public void setCreationDate(NSTimestamp value) {
    if (_SWNewsItem.LOG.isDebugEnabled()) {
    	_SWNewsItem.LOG.debug( "updating creationDate from " + creationDate() + " to " + value);
    }
    takeStoredValueForKey(value, _SWNewsItem.CREATION_DATE_KEY);
  }

  public Integer crmNafnID() {
    return (Integer) storedValueForKey(_SWNewsItem.CRM_NAFN_ID_KEY);
  }

  public void setCrmNafnID(Integer value) {
    if (_SWNewsItem.LOG.isDebugEnabled()) {
    	_SWNewsItem.LOG.debug( "updating crmNafnID from " + crmNafnID() + " to " + value);
    }
    takeStoredValueForKey(value, _SWNewsItem.CRM_NAFN_ID_KEY);
  }

  public String customInfoData() {
    return (String) storedValueForKey(_SWNewsItem.CUSTOM_INFO_DATA_KEY);
  }

  public void setCustomInfoData(String value) {
    if (_SWNewsItem.LOG.isDebugEnabled()) {
    	_SWNewsItem.LOG.debug( "updating customInfoData from " + customInfoData() + " to " + value);
    }
    takeStoredValueForKey(value, _SWNewsItem.CUSTOM_INFO_DATA_KEY);
  }

  public String customMoreURL() {
    return (String) storedValueForKey(_SWNewsItem.CUSTOM_MORE_URL_KEY);
  }

  public void setCustomMoreURL(String value) {
    if (_SWNewsItem.LOG.isDebugEnabled()) {
    	_SWNewsItem.LOG.debug( "updating customMoreURL from " + customMoreURL() + " to " + value);
    }
    takeStoredValueForKey(value, _SWNewsItem.CUSTOM_MORE_URL_KEY);
  }

  public NSTimestamp date() {
    return (NSTimestamp) storedValueForKey(_SWNewsItem.DATE_KEY);
  }

  public void setDate(NSTimestamp value) {
    if (_SWNewsItem.LOG.isDebugEnabled()) {
    	_SWNewsItem.LOG.debug( "updating date from " + date() + " to " + value);
    }
    takeStoredValueForKey(value, _SWNewsItem.DATE_KEY);
  }

  public Integer encodeBreaks() {
    return (Integer) storedValueForKey(_SWNewsItem.ENCODE_BREAKS_KEY);
  }

  public void setEncodeBreaks(Integer value) {
    if (_SWNewsItem.LOG.isDebugEnabled()) {
    	_SWNewsItem.LOG.debug( "updating encodeBreaks from " + encodeBreaks() + " to " + value);
    }
    takeStoredValueForKey(value, _SWNewsItem.ENCODE_BREAKS_KEY);
  }

  public String excerpt() {
    return (String) storedValueForKey(_SWNewsItem.EXCERPT_KEY);
  }

  public void setExcerpt(String value) {
    if (_SWNewsItem.LOG.isDebugEnabled()) {
    	_SWNewsItem.LOG.debug( "updating excerpt from " + excerpt() + " to " + value);
    }
    takeStoredValueForKey(value, _SWNewsItem.EXCERPT_KEY);
  }

  public String heading() {
    return (String) storedValueForKey(_SWNewsItem.HEADING_KEY);
  }

  public void setHeading(String value) {
    if (_SWNewsItem.LOG.isDebugEnabled()) {
    	_SWNewsItem.LOG.debug( "updating heading from " + heading() + " to " + value);
    }
    takeStoredValueForKey(value, _SWNewsItem.HEADING_KEY);
  }

  public Integer id() {
    return (Integer) storedValueForKey(_SWNewsItem.ID_KEY);
  }

  public void setId(Integer value) {
    if (_SWNewsItem.LOG.isDebugEnabled()) {
    	_SWNewsItem.LOG.debug( "updating id from " + id() + " to " + value);
    }
    takeStoredValueForKey(value, _SWNewsItem.ID_KEY);
  }

  public NSTimestamp modificationDate() {
    return (NSTimestamp) storedValueForKey(_SWNewsItem.MODIFICATION_DATE_KEY);
  }

  public void setModificationDate(NSTimestamp value) {
    if (_SWNewsItem.LOG.isDebugEnabled()) {
    	_SWNewsItem.LOG.debug( "updating modificationDate from " + modificationDate() + " to " + value);
    }
    takeStoredValueForKey(value, _SWNewsItem.MODIFICATION_DATE_KEY);
  }

  public Integer newsCategoryID() {
    return (Integer) storedValueForKey(_SWNewsItem.NEWS_CATEGORY_ID_KEY);
  }

  public void setNewsCategoryID(Integer value) {
    if (_SWNewsItem.LOG.isDebugEnabled()) {
    	_SWNewsItem.LOG.debug( "updating newsCategoryID from " + newsCategoryID() + " to " + value);
    }
    takeStoredValueForKey(value, _SWNewsItem.NEWS_CATEGORY_ID_KEY);
  }

  public Integer originalItemID() {
    return (Integer) storedValueForKey(_SWNewsItem.ORIGINAL_ITEM_ID_KEY);
  }

  public void setOriginalItemID(Integer value) {
    if (_SWNewsItem.LOG.isDebugEnabled()) {
    	_SWNewsItem.LOG.debug( "updating originalItemID from " + originalItemID() + " to " + value);
    }
    takeStoredValueForKey(value, _SWNewsItem.ORIGINAL_ITEM_ID_KEY);
  }

  public Integer picture2ID() {
    return (Integer) storedValueForKey(_SWNewsItem.PICTURE2_ID_KEY);
  }

  public void setPicture2ID(Integer value) {
    if (_SWNewsItem.LOG.isDebugEnabled()) {
    	_SWNewsItem.LOG.debug( "updating picture2ID from " + picture2ID() + " to " + value);
    }
    takeStoredValueForKey(value, _SWNewsItem.PICTURE2_ID_KEY);
  }

  public Integer pictureID() {
    return (Integer) storedValueForKey(_SWNewsItem.PICTURE_ID_KEY);
  }

  public void setPictureID(Integer value) {
    if (_SWNewsItem.LOG.isDebugEnabled()) {
    	_SWNewsItem.LOG.debug( "updating pictureID from " + pictureID() + " to " + value);
    }
    takeStoredValueForKey(value, _SWNewsItem.PICTURE_ID_KEY);
  }

  public Integer published() {
    return (Integer) storedValueForKey(_SWNewsItem.PUBLISHED_KEY);
  }

  public void setPublished(Integer value) {
    if (_SWNewsItem.LOG.isDebugEnabled()) {
    	_SWNewsItem.LOG.debug( "updating published from " + published() + " to " + value);
    }
    takeStoredValueForKey(value, _SWNewsItem.PUBLISHED_KEY);
  }

  public String text() {
    return (String) storedValueForKey(_SWNewsItem.TEXT_KEY);
  }

  public void setText(String value) {
    if (_SWNewsItem.LOG.isDebugEnabled()) {
    	_SWNewsItem.LOG.debug( "updating text from " + text() + " to " + value);
    }
    takeStoredValueForKey(value, _SWNewsItem.TEXT_KEY);
  }

  public NSTimestamp timeIn() {
    return (NSTimestamp) storedValueForKey(_SWNewsItem.TIME_IN_KEY);
  }

  public void setTimeIn(NSTimestamp value) {
    if (_SWNewsItem.LOG.isDebugEnabled()) {
    	_SWNewsItem.LOG.debug( "updating timeIn from " + timeIn() + " to " + value);
    }
    takeStoredValueForKey(value, _SWNewsItem.TIME_IN_KEY);
  }

  public NSTimestamp timeOut() {
    return (NSTimestamp) storedValueForKey(_SWNewsItem.TIME_OUT_KEY);
  }

  public void setTimeOut(NSTimestamp value) {
    if (_SWNewsItem.LOG.isDebugEnabled()) {
    	_SWNewsItem.LOG.debug( "updating timeOut from " + timeOut() + " to " + value);
    }
    takeStoredValueForKey(value, _SWNewsItem.TIME_OUT_KEY);
  }

  public concept.data.SWNewsCategory category() {
    return (concept.data.SWNewsCategory)storedValueForKey(_SWNewsItem.CATEGORY_KEY);
  }
  
  public void setCategory(concept.data.SWNewsCategory value) {
    takeStoredValueForKey(value, _SWNewsItem.CATEGORY_KEY);
  }

  public void setCategoryRelationship(concept.data.SWNewsCategory value) {
    if (_SWNewsItem.LOG.isDebugEnabled()) {
      _SWNewsItem.LOG.debug("updating category from " + category() + " to " + value);
    }
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
    	setCategory(value);
    }
    else if (value == null) {
    	concept.data.SWNewsCategory oldValue = category();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, _SWNewsItem.CATEGORY_KEY);
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, _SWNewsItem.CATEGORY_KEY);
    }
  }
  
  public concept.data.SWUser createdBy() {
    return (concept.data.SWUser)storedValueForKey(_SWNewsItem.CREATED_BY_KEY);
  }
  
  public void setCreatedBy(concept.data.SWUser value) {
    takeStoredValueForKey(value, _SWNewsItem.CREATED_BY_KEY);
  }

  public void setCreatedByRelationship(concept.data.SWUser value) {
    if (_SWNewsItem.LOG.isDebugEnabled()) {
      _SWNewsItem.LOG.debug("updating createdBy from " + createdBy() + " to " + value);
    }
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
    	setCreatedBy(value);
    }
    else if (value == null) {
    	concept.data.SWUser oldValue = createdBy();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, _SWNewsItem.CREATED_BY_KEY);
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, _SWNewsItem.CREATED_BY_KEY);
    }
  }
  
  public concept.data.SWUser modifiedBy() {
    return (concept.data.SWUser)storedValueForKey(_SWNewsItem.MODIFIED_BY_KEY);
  }
  
  public void setModifiedBy(concept.data.SWUser value) {
    takeStoredValueForKey(value, _SWNewsItem.MODIFIED_BY_KEY);
  }

  public void setModifiedByRelationship(concept.data.SWUser value) {
    if (_SWNewsItem.LOG.isDebugEnabled()) {
      _SWNewsItem.LOG.debug("updating modifiedBy from " + modifiedBy() + " to " + value);
    }
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
    	setModifiedBy(value);
    }
    else if (value == null) {
    	concept.data.SWUser oldValue = modifiedBy();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, _SWNewsItem.MODIFIED_BY_KEY);
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, _SWNewsItem.MODIFIED_BY_KEY);
    }
  }
  
  public concept.data.SWPicture picture() {
    return (concept.data.SWPicture)storedValueForKey(_SWNewsItem.PICTURE_KEY);
  }
  
  public void setPicture(concept.data.SWPicture value) {
    takeStoredValueForKey(value, _SWNewsItem.PICTURE_KEY);
  }

  public void setPictureRelationship(concept.data.SWPicture value) {
    if (_SWNewsItem.LOG.isDebugEnabled()) {
      _SWNewsItem.LOG.debug("updating picture from " + picture() + " to " + value);
    }
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
    	setPicture(value);
    }
    else if (value == null) {
    	concept.data.SWPicture oldValue = picture();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, _SWNewsItem.PICTURE_KEY);
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, _SWNewsItem.PICTURE_KEY);
    }
  }
  
  public concept.data.SWPicture picture2() {
    return (concept.data.SWPicture)storedValueForKey(_SWNewsItem.PICTURE2_KEY);
  }
  
  public void setPicture2(concept.data.SWPicture value) {
    takeStoredValueForKey(value, _SWNewsItem.PICTURE2_KEY);
  }

  public void setPicture2Relationship(concept.data.SWPicture value) {
    if (_SWNewsItem.LOG.isDebugEnabled()) {
      _SWNewsItem.LOG.debug("updating picture2 from " + picture2() + " to " + value);
    }
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
    	setPicture2(value);
    }
    else if (value == null) {
    	concept.data.SWPicture oldValue = picture2();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, _SWNewsItem.PICTURE2_KEY);
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, _SWNewsItem.PICTURE2_KEY);
    }
  }
  
  public NSArray<concept.data.SWPictureLink> swPictureLinks() {
    return (NSArray<concept.data.SWPictureLink>)storedValueForKey(_SWNewsItem.SW_PICTURE_LINKS_KEY);
  }

  public NSArray<concept.data.SWPictureLink> swPictureLinks(EOQualifier qualifier) {
    return swPictureLinks(qualifier, null, false);
  }

  public NSArray<concept.data.SWPictureLink> swPictureLinks(EOQualifier qualifier, boolean fetch) {
    return swPictureLinks(qualifier, null, fetch);
  }

  public NSArray<concept.data.SWPictureLink> swPictureLinks(EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings, boolean fetch) {
    NSArray<concept.data.SWPictureLink> results;
    if (fetch) {
      EOQualifier fullQualifier;
      EOQualifier inverseQualifier = new EOKeyValueQualifier(concept.data.SWPictureLink.NEWS_ITEM_KEY, EOQualifier.QualifierOperatorEqual, this);
    	
      if (qualifier == null) {
        fullQualifier = inverseQualifier;
      }
      else {
        NSMutableArray<EOQualifier> qualifiers = new NSMutableArray<EOQualifier>();
        qualifiers.addObject(qualifier);
        qualifiers.addObject(inverseQualifier);
        fullQualifier = new EOAndQualifier(qualifiers);
      }

      results = concept.data.SWPictureLink.fetchSWPictureLinks(editingContext(), fullQualifier, sortOrderings);
    }
    else {
      results = swPictureLinks();
      if (qualifier != null) {
        results = (NSArray<concept.data.SWPictureLink>)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray<concept.data.SWPictureLink>)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    }
    return results;
  }
  
  public void addToSwPictureLinks(concept.data.SWPictureLink object) {
    includeObjectIntoPropertyWithKey(object, _SWNewsItem.SW_PICTURE_LINKS_KEY);
  }

  public void removeFromSwPictureLinks(concept.data.SWPictureLink object) {
    excludeObjectFromPropertyWithKey(object, _SWNewsItem.SW_PICTURE_LINKS_KEY);
  }

  public void addToSwPictureLinksRelationship(concept.data.SWPictureLink object) {
    if (_SWNewsItem.LOG.isDebugEnabled()) {
      _SWNewsItem.LOG.debug("adding " + object + " to swPictureLinks relationship");
    }
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
    	addToSwPictureLinks(object);
    }
    else {
    	addObjectToBothSidesOfRelationshipWithKey(object, _SWNewsItem.SW_PICTURE_LINKS_KEY);
    }
  }

  public void removeFromSwPictureLinksRelationship(concept.data.SWPictureLink object) {
    if (_SWNewsItem.LOG.isDebugEnabled()) {
      _SWNewsItem.LOG.debug("removing " + object + " from swPictureLinks relationship");
    }
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
    	removeFromSwPictureLinks(object);
    }
    else {
    	removeObjectFromBothSidesOfRelationshipWithKey(object, _SWNewsItem.SW_PICTURE_LINKS_KEY);
    }
  }

  public concept.data.SWPictureLink createSwPictureLinksRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName( concept.data.SWPictureLink.ENTITY_NAME );
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, _SWNewsItem.SW_PICTURE_LINKS_KEY);
    return (concept.data.SWPictureLink) eo;
  }

  public void deleteSwPictureLinksRelationship(concept.data.SWPictureLink object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, _SWNewsItem.SW_PICTURE_LINKS_KEY);
    editingContext().deleteObject(object);
  }

  public void deleteAllSwPictureLinksRelationships() {
    Enumeration<concept.data.SWPictureLink> objects = swPictureLinks().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteSwPictureLinksRelationship(objects.nextElement());
    }
  }


  public static concept.data.SWNewsItem createSWNewsItem(EOEditingContext editingContext, Integer id
) {
    concept.data.SWNewsItem eo = (concept.data.SWNewsItem) EOUtilities.createAndInsertInstance(editingContext, _SWNewsItem.ENTITY_NAME);    
		eo.setId(id);
    return eo;
  }

  public static ERXFetchSpecification<concept.data.SWNewsItem> fetchSpec() {
    return new ERXFetchSpecification<concept.data.SWNewsItem>(_SWNewsItem.ENTITY_NAME, null, null, false, true, null);
  }

  public static NSArray<concept.data.SWNewsItem> fetchAllSWNewsItems(EOEditingContext editingContext) {
    return _SWNewsItem.fetchAllSWNewsItems(editingContext, null);
  }

  public static NSArray<concept.data.SWNewsItem> fetchAllSWNewsItems(EOEditingContext editingContext, NSArray<EOSortOrdering> sortOrderings) {
    return _SWNewsItem.fetchSWNewsItems(editingContext, null, sortOrderings);
  }

  public static NSArray<concept.data.SWNewsItem> fetchSWNewsItems(EOEditingContext editingContext, EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings) {
    ERXFetchSpecification<concept.data.SWNewsItem> fetchSpec = new ERXFetchSpecification<concept.data.SWNewsItem>(_SWNewsItem.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray<concept.data.SWNewsItem> eoObjects = fetchSpec.fetchObjects(editingContext);
    return eoObjects;
  }

  public static concept.data.SWNewsItem fetchSWNewsItem(EOEditingContext editingContext, String keyName, Object value) {
    return _SWNewsItem.fetchSWNewsItem(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static concept.data.SWNewsItem fetchSWNewsItem(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray<concept.data.SWNewsItem> eoObjects = _SWNewsItem.fetchSWNewsItems(editingContext, qualifier, null);
    concept.data.SWNewsItem eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one SWNewsItem that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static concept.data.SWNewsItem fetchRequiredSWNewsItem(EOEditingContext editingContext, String keyName, Object value) {
    return _SWNewsItem.fetchRequiredSWNewsItem(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static concept.data.SWNewsItem fetchRequiredSWNewsItem(EOEditingContext editingContext, EOQualifier qualifier) {
    concept.data.SWNewsItem eoObject = _SWNewsItem.fetchSWNewsItem(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no SWNewsItem that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static concept.data.SWNewsItem localInstanceIn(EOEditingContext editingContext, concept.data.SWNewsItem eo) {
    concept.data.SWNewsItem localInstance = (eo == null) ? null : ERXEOControlUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
