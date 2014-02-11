// DO NOT EDIT.  Make changes to concept.data.SWComponent.java instead.
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
public abstract class _SWComponent extends  ERXGenericRecord {
  public static final String ENTITY_NAME = "SWComponent";

  // Attribute Keys
  public static final ERXKey<String> CUSTOM_INFO_DATA = new ERXKey<String>("customInfoData");
  public static final ERXKey<Integer> ENCODE_BREAKS = new ERXKey<Integer>("encodeBreaks");
  public static final ERXKey<Integer> ID = new ERXKey<Integer>("id");
  public static final ERXKey<Integer> PAGE_ID = new ERXKey<Integer>("pageID");
  public static final ERXKey<Integer> PICTURE_ID = new ERXKey<Integer>("pictureID");
  public static final ERXKey<Integer> PUBLISHED = new ERXKey<Integer>("published");
  public static final ERXKey<Integer> SORT_NUMBER = new ERXKey<Integer>("sortNumber");
  public static final ERXKey<String> TEMPLATE_NAME = new ERXKey<String>("templateName");
  public static final ERXKey<String> TEXT_ONE = new ERXKey<String>("textOne");
  public static final ERXKey<String> TEXT_TWO = new ERXKey<String>("textTwo");
  public static final ERXKey<NSTimestamp> TIME_IN = new ERXKey<NSTimestamp>("timeIn");
  public static final ERXKey<NSTimestamp> TIME_OUT = new ERXKey<NSTimestamp>("timeOut");
  // Relationship Keys
  public static final ERXKey<concept.data.SWPage> PAGE = new ERXKey<concept.data.SWPage>("page");
  public static final ERXKey<concept.data.SWPicture> PICTURE = new ERXKey<concept.data.SWPicture>("picture");
  public static final ERXKey<concept.data.SWPictureLink> SW_PICTURE_LINKS = new ERXKey<concept.data.SWPictureLink>("swPictureLinks");

  // Attributes
  public static final String CUSTOM_INFO_DATA_KEY = CUSTOM_INFO_DATA.key();
  public static final String ENCODE_BREAKS_KEY = ENCODE_BREAKS.key();
  public static final String ID_KEY = ID.key();
  public static final String PAGE_ID_KEY = PAGE_ID.key();
  public static final String PICTURE_ID_KEY = PICTURE_ID.key();
  public static final String PUBLISHED_KEY = PUBLISHED.key();
  public static final String SORT_NUMBER_KEY = SORT_NUMBER.key();
  public static final String TEMPLATE_NAME_KEY = TEMPLATE_NAME.key();
  public static final String TEXT_ONE_KEY = TEXT_ONE.key();
  public static final String TEXT_TWO_KEY = TEXT_TWO.key();
  public static final String TIME_IN_KEY = TIME_IN.key();
  public static final String TIME_OUT_KEY = TIME_OUT.key();
  // Relationships
  public static final String PAGE_KEY = PAGE.key();
  public static final String PICTURE_KEY = PICTURE.key();
  public static final String SW_PICTURE_LINKS_KEY = SW_PICTURE_LINKS.key();

  private static Logger LOG = Logger.getLogger(_SWComponent.class);

  public concept.data.SWComponent localInstanceIn(EOEditingContext editingContext) {
    concept.data.SWComponent localInstance = (concept.data.SWComponent)EOUtilities.localInstanceOfObject(editingContext, this);
    if (localInstance == null) {
      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
    }
    return localInstance;
  }

  public String customInfoData() {
    return (String) storedValueForKey(_SWComponent.CUSTOM_INFO_DATA_KEY);
  }

  public void setCustomInfoData(String value) {
    if (_SWComponent.LOG.isDebugEnabled()) {
    	_SWComponent.LOG.debug( "updating customInfoData from " + customInfoData() + " to " + value);
    }
    takeStoredValueForKey(value, _SWComponent.CUSTOM_INFO_DATA_KEY);
  }

  public Integer encodeBreaks() {
    return (Integer) storedValueForKey(_SWComponent.ENCODE_BREAKS_KEY);
  }

  public void setEncodeBreaks(Integer value) {
    if (_SWComponent.LOG.isDebugEnabled()) {
    	_SWComponent.LOG.debug( "updating encodeBreaks from " + encodeBreaks() + " to " + value);
    }
    takeStoredValueForKey(value, _SWComponent.ENCODE_BREAKS_KEY);
  }

  public Integer id() {
    return (Integer) storedValueForKey(_SWComponent.ID_KEY);
  }

  public void setId(Integer value) {
    if (_SWComponent.LOG.isDebugEnabled()) {
    	_SWComponent.LOG.debug( "updating id from " + id() + " to " + value);
    }
    takeStoredValueForKey(value, _SWComponent.ID_KEY);
  }

  public Integer pageID() {
    return (Integer) storedValueForKey(_SWComponent.PAGE_ID_KEY);
  }

  public void setPageID(Integer value) {
    if (_SWComponent.LOG.isDebugEnabled()) {
    	_SWComponent.LOG.debug( "updating pageID from " + pageID() + " to " + value);
    }
    takeStoredValueForKey(value, _SWComponent.PAGE_ID_KEY);
  }

  public Integer pictureID() {
    return (Integer) storedValueForKey(_SWComponent.PICTURE_ID_KEY);
  }

  public void setPictureID(Integer value) {
    if (_SWComponent.LOG.isDebugEnabled()) {
    	_SWComponent.LOG.debug( "updating pictureID from " + pictureID() + " to " + value);
    }
    takeStoredValueForKey(value, _SWComponent.PICTURE_ID_KEY);
  }

  public Integer published() {
    return (Integer) storedValueForKey(_SWComponent.PUBLISHED_KEY);
  }

  public void setPublished(Integer value) {
    if (_SWComponent.LOG.isDebugEnabled()) {
    	_SWComponent.LOG.debug( "updating published from " + published() + " to " + value);
    }
    takeStoredValueForKey(value, _SWComponent.PUBLISHED_KEY);
  }

  public Integer sortNumber() {
    return (Integer) storedValueForKey(_SWComponent.SORT_NUMBER_KEY);
  }

  public void setSortNumber(Integer value) {
    if (_SWComponent.LOG.isDebugEnabled()) {
    	_SWComponent.LOG.debug( "updating sortNumber from " + sortNumber() + " to " + value);
    }
    takeStoredValueForKey(value, _SWComponent.SORT_NUMBER_KEY);
  }

  public String templateName() {
    return (String) storedValueForKey(_SWComponent.TEMPLATE_NAME_KEY);
  }

  public void setTemplateName(String value) {
    if (_SWComponent.LOG.isDebugEnabled()) {
    	_SWComponent.LOG.debug( "updating templateName from " + templateName() + " to " + value);
    }
    takeStoredValueForKey(value, _SWComponent.TEMPLATE_NAME_KEY);
  }

  public String textOne() {
    return (String) storedValueForKey(_SWComponent.TEXT_ONE_KEY);
  }

  public void setTextOne(String value) {
    if (_SWComponent.LOG.isDebugEnabled()) {
    	_SWComponent.LOG.debug( "updating textOne from " + textOne() + " to " + value);
    }
    takeStoredValueForKey(value, _SWComponent.TEXT_ONE_KEY);
  }

  public String textTwo() {
    return (String) storedValueForKey(_SWComponent.TEXT_TWO_KEY);
  }

  public void setTextTwo(String value) {
    if (_SWComponent.LOG.isDebugEnabled()) {
    	_SWComponent.LOG.debug( "updating textTwo from " + textTwo() + " to " + value);
    }
    takeStoredValueForKey(value, _SWComponent.TEXT_TWO_KEY);
  }

  public NSTimestamp timeIn() {
    return (NSTimestamp) storedValueForKey(_SWComponent.TIME_IN_KEY);
  }

  public void setTimeIn(NSTimestamp value) {
    if (_SWComponent.LOG.isDebugEnabled()) {
    	_SWComponent.LOG.debug( "updating timeIn from " + timeIn() + " to " + value);
    }
    takeStoredValueForKey(value, _SWComponent.TIME_IN_KEY);
  }

  public NSTimestamp timeOut() {
    return (NSTimestamp) storedValueForKey(_SWComponent.TIME_OUT_KEY);
  }

  public void setTimeOut(NSTimestamp value) {
    if (_SWComponent.LOG.isDebugEnabled()) {
    	_SWComponent.LOG.debug( "updating timeOut from " + timeOut() + " to " + value);
    }
    takeStoredValueForKey(value, _SWComponent.TIME_OUT_KEY);
  }

  public concept.data.SWPage page() {
    return (concept.data.SWPage)storedValueForKey(_SWComponent.PAGE_KEY);
  }
  
  public void setPage(concept.data.SWPage value) {
    takeStoredValueForKey(value, _SWComponent.PAGE_KEY);
  }

  public void setPageRelationship(concept.data.SWPage value) {
    if (_SWComponent.LOG.isDebugEnabled()) {
      _SWComponent.LOG.debug("updating page from " + page() + " to " + value);
    }
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
    	setPage(value);
    }
    else if (value == null) {
    	concept.data.SWPage oldValue = page();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, _SWComponent.PAGE_KEY);
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, _SWComponent.PAGE_KEY);
    }
  }
  
  public concept.data.SWPicture picture() {
    return (concept.data.SWPicture)storedValueForKey(_SWComponent.PICTURE_KEY);
  }
  
  public void setPicture(concept.data.SWPicture value) {
    takeStoredValueForKey(value, _SWComponent.PICTURE_KEY);
  }

  public void setPictureRelationship(concept.data.SWPicture value) {
    if (_SWComponent.LOG.isDebugEnabled()) {
      _SWComponent.LOG.debug("updating picture from " + picture() + " to " + value);
    }
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
    	setPicture(value);
    }
    else if (value == null) {
    	concept.data.SWPicture oldValue = picture();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, _SWComponent.PICTURE_KEY);
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, _SWComponent.PICTURE_KEY);
    }
  }
  
  public NSArray<concept.data.SWPictureLink> swPictureLinks() {
    return (NSArray<concept.data.SWPictureLink>)storedValueForKey(_SWComponent.SW_PICTURE_LINKS_KEY);
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
      EOQualifier inverseQualifier = new EOKeyValueQualifier(concept.data.SWPictureLink.COMPONENT_KEY, EOQualifier.QualifierOperatorEqual, this);
    	
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
    includeObjectIntoPropertyWithKey(object, _SWComponent.SW_PICTURE_LINKS_KEY);
  }

  public void removeFromSwPictureLinks(concept.data.SWPictureLink object) {
    excludeObjectFromPropertyWithKey(object, _SWComponent.SW_PICTURE_LINKS_KEY);
  }

  public void addToSwPictureLinksRelationship(concept.data.SWPictureLink object) {
    if (_SWComponent.LOG.isDebugEnabled()) {
      _SWComponent.LOG.debug("adding " + object + " to swPictureLinks relationship");
    }
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
    	addToSwPictureLinks(object);
    }
    else {
    	addObjectToBothSidesOfRelationshipWithKey(object, _SWComponent.SW_PICTURE_LINKS_KEY);
    }
  }

  public void removeFromSwPictureLinksRelationship(concept.data.SWPictureLink object) {
    if (_SWComponent.LOG.isDebugEnabled()) {
      _SWComponent.LOG.debug("removing " + object + " from swPictureLinks relationship");
    }
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
    	removeFromSwPictureLinks(object);
    }
    else {
    	removeObjectFromBothSidesOfRelationshipWithKey(object, _SWComponent.SW_PICTURE_LINKS_KEY);
    }
  }

  public concept.data.SWPictureLink createSwPictureLinksRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName( concept.data.SWPictureLink.ENTITY_NAME );
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, _SWComponent.SW_PICTURE_LINKS_KEY);
    return (concept.data.SWPictureLink) eo;
  }

  public void deleteSwPictureLinksRelationship(concept.data.SWPictureLink object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, _SWComponent.SW_PICTURE_LINKS_KEY);
    editingContext().deleteObject(object);
  }

  public void deleteAllSwPictureLinksRelationships() {
    Enumeration<concept.data.SWPictureLink> objects = swPictureLinks().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteSwPictureLinksRelationship(objects.nextElement());
    }
  }


  public static concept.data.SWComponent createSWComponent(EOEditingContext editingContext, Integer id
) {
    concept.data.SWComponent eo = (concept.data.SWComponent) EOUtilities.createAndInsertInstance(editingContext, _SWComponent.ENTITY_NAME);    
		eo.setId(id);
    return eo;
  }

  public static ERXFetchSpecification<concept.data.SWComponent> fetchSpec() {
    return new ERXFetchSpecification<concept.data.SWComponent>(_SWComponent.ENTITY_NAME, null, null, false, true, null);
  }

  public static NSArray<concept.data.SWComponent> fetchAllSWComponents(EOEditingContext editingContext) {
    return _SWComponent.fetchAllSWComponents(editingContext, null);
  }

  public static NSArray<concept.data.SWComponent> fetchAllSWComponents(EOEditingContext editingContext, NSArray<EOSortOrdering> sortOrderings) {
    return _SWComponent.fetchSWComponents(editingContext, null, sortOrderings);
  }

  public static NSArray<concept.data.SWComponent> fetchSWComponents(EOEditingContext editingContext, EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings) {
    ERXFetchSpecification<concept.data.SWComponent> fetchSpec = new ERXFetchSpecification<concept.data.SWComponent>(_SWComponent.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray<concept.data.SWComponent> eoObjects = fetchSpec.fetchObjects(editingContext);
    return eoObjects;
  }

  public static concept.data.SWComponent fetchSWComponent(EOEditingContext editingContext, String keyName, Object value) {
    return _SWComponent.fetchSWComponent(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static concept.data.SWComponent fetchSWComponent(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray<concept.data.SWComponent> eoObjects = _SWComponent.fetchSWComponents(editingContext, qualifier, null);
    concept.data.SWComponent eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one SWComponent that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static concept.data.SWComponent fetchRequiredSWComponent(EOEditingContext editingContext, String keyName, Object value) {
    return _SWComponent.fetchRequiredSWComponent(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static concept.data.SWComponent fetchRequiredSWComponent(EOEditingContext editingContext, EOQualifier qualifier) {
    concept.data.SWComponent eoObject = _SWComponent.fetchSWComponent(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no SWComponent that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static concept.data.SWComponent localInstanceIn(EOEditingContext editingContext, concept.data.SWComponent eo) {
    concept.data.SWComponent localInstance = (eo == null) ? null : ERXEOControlUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
