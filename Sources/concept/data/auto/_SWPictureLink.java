// DO NOT EDIT.  Make changes to concept.data.SWPictureLink.java instead.
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
public abstract class _SWPictureLink extends  ERXGenericRecord {
  public static final String ENTITY_NAME = "SWPictureLink";

  // Attribute Keys
  public static final ERXKey<Integer> ALIGN = new ERXKey<Integer>("align");
  public static final ERXKey<Integer> COMPONENT_ID = new ERXKey<Integer>("componentID");
  public static final ERXKey<Integer> ID = new ERXKey<Integer>("id");
  public static final ERXKey<Integer> LINK_TO_LARGE = new ERXKey<Integer>("linkToLarge");
  public static final ERXKey<String> NAME = new ERXKey<String>("name");
  public static final ERXKey<Integer> NEWS_ITEM_ID = new ERXKey<Integer>("newsItemID");
  public static final ERXKey<Integer> PICTURE_ID = new ERXKey<Integer>("pictureID");
  public static final ERXKey<Integer> SHOW_CAPTION = new ERXKey<Integer>("showCaption");
  public static final ERXKey<String> SIZE = new ERXKey<String>("size");
  public static final ERXKey<String> SIZE_LARGE = new ERXKey<String>("sizeLarge");
  public static final ERXKey<String> URL = new ERXKey<String>("url");
  // Relationship Keys
  public static final ERXKey<concept.data.SWComponent> COMPONENT = new ERXKey<concept.data.SWComponent>("component");
  public static final ERXKey<concept.data.SWNewsItem> NEWS_ITEM = new ERXKey<concept.data.SWNewsItem>("newsItem");
  public static final ERXKey<concept.data.SWPicture> PICTURE = new ERXKey<concept.data.SWPicture>("picture");

  // Attributes
  public static final String ALIGN_KEY = ALIGN.key();
  public static final String COMPONENT_ID_KEY = COMPONENT_ID.key();
  public static final String ID_KEY = ID.key();
  public static final String LINK_TO_LARGE_KEY = LINK_TO_LARGE.key();
  public static final String NAME_KEY = NAME.key();
  public static final String NEWS_ITEM_ID_KEY = NEWS_ITEM_ID.key();
  public static final String PICTURE_ID_KEY = PICTURE_ID.key();
  public static final String SHOW_CAPTION_KEY = SHOW_CAPTION.key();
  public static final String SIZE_KEY = SIZE.key();
  public static final String SIZE_LARGE_KEY = SIZE_LARGE.key();
  public static final String URL_KEY = URL.key();
  // Relationships
  public static final String COMPONENT_KEY = COMPONENT.key();
  public static final String NEWS_ITEM_KEY = NEWS_ITEM.key();
  public static final String PICTURE_KEY = PICTURE.key();

  private static Logger LOG = Logger.getLogger(_SWPictureLink.class);

  public concept.data.SWPictureLink localInstanceIn(EOEditingContext editingContext) {
    concept.data.SWPictureLink localInstance = (concept.data.SWPictureLink)EOUtilities.localInstanceOfObject(editingContext, this);
    if (localInstance == null) {
      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
    }
    return localInstance;
  }

  public Integer align() {
    return (Integer) storedValueForKey(_SWPictureLink.ALIGN_KEY);
  }

  public void setAlign(Integer value) {
    if (_SWPictureLink.LOG.isDebugEnabled()) {
    	_SWPictureLink.LOG.debug( "updating align from " + align() + " to " + value);
    }
    takeStoredValueForKey(value, _SWPictureLink.ALIGN_KEY);
  }

  public Integer componentID() {
    return (Integer) storedValueForKey(_SWPictureLink.COMPONENT_ID_KEY);
  }

  public void setComponentID(Integer value) {
    if (_SWPictureLink.LOG.isDebugEnabled()) {
    	_SWPictureLink.LOG.debug( "updating componentID from " + componentID() + " to " + value);
    }
    takeStoredValueForKey(value, _SWPictureLink.COMPONENT_ID_KEY);
  }

  public Integer id() {
    return (Integer) storedValueForKey(_SWPictureLink.ID_KEY);
  }

  public void setId(Integer value) {
    if (_SWPictureLink.LOG.isDebugEnabled()) {
    	_SWPictureLink.LOG.debug( "updating id from " + id() + " to " + value);
    }
    takeStoredValueForKey(value, _SWPictureLink.ID_KEY);
  }

  public Integer linkToLarge() {
    return (Integer) storedValueForKey(_SWPictureLink.LINK_TO_LARGE_KEY);
  }

  public void setLinkToLarge(Integer value) {
    if (_SWPictureLink.LOG.isDebugEnabled()) {
    	_SWPictureLink.LOG.debug( "updating linkToLarge from " + linkToLarge() + " to " + value);
    }
    takeStoredValueForKey(value, _SWPictureLink.LINK_TO_LARGE_KEY);
  }

  public String name() {
    return (String) storedValueForKey(_SWPictureLink.NAME_KEY);
  }

  public void setName(String value) {
    if (_SWPictureLink.LOG.isDebugEnabled()) {
    	_SWPictureLink.LOG.debug( "updating name from " + name() + " to " + value);
    }
    takeStoredValueForKey(value, _SWPictureLink.NAME_KEY);
  }

  public Integer newsItemID() {
    return (Integer) storedValueForKey(_SWPictureLink.NEWS_ITEM_ID_KEY);
  }

  public void setNewsItemID(Integer value) {
    if (_SWPictureLink.LOG.isDebugEnabled()) {
    	_SWPictureLink.LOG.debug( "updating newsItemID from " + newsItemID() + " to " + value);
    }
    takeStoredValueForKey(value, _SWPictureLink.NEWS_ITEM_ID_KEY);
  }

  public Integer pictureID() {
    return (Integer) storedValueForKey(_SWPictureLink.PICTURE_ID_KEY);
  }

  public void setPictureID(Integer value) {
    if (_SWPictureLink.LOG.isDebugEnabled()) {
    	_SWPictureLink.LOG.debug( "updating pictureID from " + pictureID() + " to " + value);
    }
    takeStoredValueForKey(value, _SWPictureLink.PICTURE_ID_KEY);
  }

  public Integer showCaption() {
    return (Integer) storedValueForKey(_SWPictureLink.SHOW_CAPTION_KEY);
  }

  public void setShowCaption(Integer value) {
    if (_SWPictureLink.LOG.isDebugEnabled()) {
    	_SWPictureLink.LOG.debug( "updating showCaption from " + showCaption() + " to " + value);
    }
    takeStoredValueForKey(value, _SWPictureLink.SHOW_CAPTION_KEY);
  }

  public String size() {
    return (String) storedValueForKey(_SWPictureLink.SIZE_KEY);
  }

  public void setSize(String value) {
    if (_SWPictureLink.LOG.isDebugEnabled()) {
    	_SWPictureLink.LOG.debug( "updating size from " + size() + " to " + value);
    }
    takeStoredValueForKey(value, _SWPictureLink.SIZE_KEY);
  }

  public String sizeLarge() {
    return (String) storedValueForKey(_SWPictureLink.SIZE_LARGE_KEY);
  }

  public void setSizeLarge(String value) {
    if (_SWPictureLink.LOG.isDebugEnabled()) {
    	_SWPictureLink.LOG.debug( "updating sizeLarge from " + sizeLarge() + " to " + value);
    }
    takeStoredValueForKey(value, _SWPictureLink.SIZE_LARGE_KEY);
  }

  public String url() {
    return (String) storedValueForKey(_SWPictureLink.URL_KEY);
  }

  public void setUrl(String value) {
    if (_SWPictureLink.LOG.isDebugEnabled()) {
    	_SWPictureLink.LOG.debug( "updating url from " + url() + " to " + value);
    }
    takeStoredValueForKey(value, _SWPictureLink.URL_KEY);
  }

  public concept.data.SWComponent component() {
    return (concept.data.SWComponent)storedValueForKey(_SWPictureLink.COMPONENT_KEY);
  }
  
  public void setComponent(concept.data.SWComponent value) {
    takeStoredValueForKey(value, _SWPictureLink.COMPONENT_KEY);
  }

  public void setComponentRelationship(concept.data.SWComponent value) {
    if (_SWPictureLink.LOG.isDebugEnabled()) {
      _SWPictureLink.LOG.debug("updating component from " + component() + " to " + value);
    }
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
    	setComponent(value);
    }
    else if (value == null) {
    	concept.data.SWComponent oldValue = component();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, _SWPictureLink.COMPONENT_KEY);
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, _SWPictureLink.COMPONENT_KEY);
    }
  }
  
  public concept.data.SWNewsItem newsItem() {
    return (concept.data.SWNewsItem)storedValueForKey(_SWPictureLink.NEWS_ITEM_KEY);
  }
  
  public void setNewsItem(concept.data.SWNewsItem value) {
    takeStoredValueForKey(value, _SWPictureLink.NEWS_ITEM_KEY);
  }

  public void setNewsItemRelationship(concept.data.SWNewsItem value) {
    if (_SWPictureLink.LOG.isDebugEnabled()) {
      _SWPictureLink.LOG.debug("updating newsItem from " + newsItem() + " to " + value);
    }
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
    	setNewsItem(value);
    }
    else if (value == null) {
    	concept.data.SWNewsItem oldValue = newsItem();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, _SWPictureLink.NEWS_ITEM_KEY);
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, _SWPictureLink.NEWS_ITEM_KEY);
    }
  }
  
  public concept.data.SWPicture picture() {
    return (concept.data.SWPicture)storedValueForKey(_SWPictureLink.PICTURE_KEY);
  }
  
  public void setPicture(concept.data.SWPicture value) {
    takeStoredValueForKey(value, _SWPictureLink.PICTURE_KEY);
  }

  public void setPictureRelationship(concept.data.SWPicture value) {
    if (_SWPictureLink.LOG.isDebugEnabled()) {
      _SWPictureLink.LOG.debug("updating picture from " + picture() + " to " + value);
    }
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
    	setPicture(value);
    }
    else if (value == null) {
    	concept.data.SWPicture oldValue = picture();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, _SWPictureLink.PICTURE_KEY);
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, _SWPictureLink.PICTURE_KEY);
    }
  }
  

  public static concept.data.SWPictureLink createSWPictureLink(EOEditingContext editingContext, Integer id
) {
    concept.data.SWPictureLink eo = (concept.data.SWPictureLink) EOUtilities.createAndInsertInstance(editingContext, _SWPictureLink.ENTITY_NAME);    
		eo.setId(id);
    return eo;
  }

  public static ERXFetchSpecification<concept.data.SWPictureLink> fetchSpec() {
    return new ERXFetchSpecification<concept.data.SWPictureLink>(_SWPictureLink.ENTITY_NAME, null, null, false, true, null);
  }

  public static NSArray<concept.data.SWPictureLink> fetchAllSWPictureLinks(EOEditingContext editingContext) {
    return _SWPictureLink.fetchAllSWPictureLinks(editingContext, null);
  }

  public static NSArray<concept.data.SWPictureLink> fetchAllSWPictureLinks(EOEditingContext editingContext, NSArray<EOSortOrdering> sortOrderings) {
    return _SWPictureLink.fetchSWPictureLinks(editingContext, null, sortOrderings);
  }

  public static NSArray<concept.data.SWPictureLink> fetchSWPictureLinks(EOEditingContext editingContext, EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings) {
    ERXFetchSpecification<concept.data.SWPictureLink> fetchSpec = new ERXFetchSpecification<concept.data.SWPictureLink>(_SWPictureLink.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray<concept.data.SWPictureLink> eoObjects = fetchSpec.fetchObjects(editingContext);
    return eoObjects;
  }

  public static concept.data.SWPictureLink fetchSWPictureLink(EOEditingContext editingContext, String keyName, Object value) {
    return _SWPictureLink.fetchSWPictureLink(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static concept.data.SWPictureLink fetchSWPictureLink(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray<concept.data.SWPictureLink> eoObjects = _SWPictureLink.fetchSWPictureLinks(editingContext, qualifier, null);
    concept.data.SWPictureLink eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one SWPictureLink that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static concept.data.SWPictureLink fetchRequiredSWPictureLink(EOEditingContext editingContext, String keyName, Object value) {
    return _SWPictureLink.fetchRequiredSWPictureLink(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static concept.data.SWPictureLink fetchRequiredSWPictureLink(EOEditingContext editingContext, EOQualifier qualifier) {
    concept.data.SWPictureLink eoObject = _SWPictureLink.fetchSWPictureLink(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no SWPictureLink that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static concept.data.SWPictureLink localInstanceIn(EOEditingContext editingContext, concept.data.SWPictureLink eo) {
    concept.data.SWPictureLink localInstance = (eo == null) ? null : ERXEOControlUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
