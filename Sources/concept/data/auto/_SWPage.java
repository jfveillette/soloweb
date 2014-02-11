// DO NOT EDIT.  Make changes to concept.data.SWPage.java instead.
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
public abstract class _SWPage extends  ERXGenericRecord {
  public static final String ENTITY_NAME = "SWPage";

  // Attribute Keys
  public static final ERXKey<Integer> ACCESSIBLE = new ERXKey<Integer>("accessible");
  public static final ERXKey<String> CUSTOM_INFO_DATA = new ERXKey<String>("customInfoData");
  public static final ERXKey<String> EXTERNAL_URL = new ERXKey<String>("externalURL");
  public static final ERXKey<Integer> ID = new ERXKey<Integer>("id");
  public static final ERXKey<Integer> INHERITS_PRIVILEGES = new ERXKey<Integer>("inheritsPrivileges");
  public static final ERXKey<String> KEYWORDS = new ERXKey<String>("keywords");
  public static final ERXKey<String> LANGUAGE = new ERXKey<String>("language");
  public static final ERXKey<String> LOOK = new ERXKey<String>("look");
  public static final ERXKey<String> NAME = new ERXKey<String>("name");
  public static final ERXKey<String> NAME_PREFIX = new ERXKey<String>("namePrefix");
  public static final ERXKey<Integer> PARENT_ID = new ERXKey<Integer>("parentID");
  public static final ERXKey<String> PASSWORD = new ERXKey<String>("password");
  public static final ERXKey<Integer> PICTURE_ONE_ID = new ERXKey<Integer>("pictureOneID");
  public static final ERXKey<Integer> PICTURE_TWO_ID = new ERXKey<Integer>("pictureTwoID");
  public static final ERXKey<Integer> PUBLISHED = new ERXKey<Integer>("published");
  public static final ERXKey<Integer> SORT_NUMBER = new ERXKey<Integer>("sortNumber");
  public static final ERXKey<String> SYMBOL = new ERXKey<String>("symbol");
  public static final ERXKey<String> TEXT = new ERXKey<String>("text");
  public static final ERXKey<NSTimestamp> TIME_IN = new ERXKey<NSTimestamp>("timeIn");
  public static final ERXKey<NSTimestamp> TIME_OUT = new ERXKey<NSTimestamp>("timeOut");
  // Relationship Keys
  public static final ERXKey<concept.data.SWPage> CHILDREN = new ERXKey<concept.data.SWPage>("children");
  public static final ERXKey<concept.data.SWComponent> COMPONENTS = new ERXKey<concept.data.SWComponent>("components");
  public static final ERXKey<concept.data.SWPicture> IMAGE_ONE = new ERXKey<concept.data.SWPicture>("imageOne");
  public static final ERXKey<concept.data.SWPicture> IMAGE_TWO = new ERXKey<concept.data.SWPicture>("imageTwo");
  public static final ERXKey<concept.data.SWPage> PARENT = new ERXKey<concept.data.SWPage>("parent");
  public static final ERXKey<concept.data.SWSite> SITE = new ERXKey<concept.data.SWSite>("site");

  // Attributes
  public static final String ACCESSIBLE_KEY = ACCESSIBLE.key();
  public static final String CUSTOM_INFO_DATA_KEY = CUSTOM_INFO_DATA.key();
  public static final String EXTERNAL_URL_KEY = EXTERNAL_URL.key();
  public static final String ID_KEY = ID.key();
  public static final String INHERITS_PRIVILEGES_KEY = INHERITS_PRIVILEGES.key();
  public static final String KEYWORDS_KEY = KEYWORDS.key();
  public static final String LANGUAGE_KEY = LANGUAGE.key();
  public static final String LOOK_KEY = LOOK.key();
  public static final String NAME_KEY = NAME.key();
  public static final String NAME_PREFIX_KEY = NAME_PREFIX.key();
  public static final String PARENT_ID_KEY = PARENT_ID.key();
  public static final String PASSWORD_KEY = PASSWORD.key();
  public static final String PICTURE_ONE_ID_KEY = PICTURE_ONE_ID.key();
  public static final String PICTURE_TWO_ID_KEY = PICTURE_TWO_ID.key();
  public static final String PUBLISHED_KEY = PUBLISHED.key();
  public static final String SORT_NUMBER_KEY = SORT_NUMBER.key();
  public static final String SYMBOL_KEY = SYMBOL.key();
  public static final String TEXT_KEY = TEXT.key();
  public static final String TIME_IN_KEY = TIME_IN.key();
  public static final String TIME_OUT_KEY = TIME_OUT.key();
  // Relationships
  public static final String CHILDREN_KEY = CHILDREN.key();
  public static final String COMPONENTS_KEY = COMPONENTS.key();
  public static final String IMAGE_ONE_KEY = IMAGE_ONE.key();
  public static final String IMAGE_TWO_KEY = IMAGE_TWO.key();
  public static final String PARENT_KEY = PARENT.key();
  public static final String SITE_KEY = SITE.key();

  private static Logger LOG = Logger.getLogger(_SWPage.class);

  public concept.data.SWPage localInstanceIn(EOEditingContext editingContext) {
    concept.data.SWPage localInstance = (concept.data.SWPage)EOUtilities.localInstanceOfObject(editingContext, this);
    if (localInstance == null) {
      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
    }
    return localInstance;
  }

  public Integer accessible() {
    return (Integer) storedValueForKey(_SWPage.ACCESSIBLE_KEY);
  }

  public void setAccessible(Integer value) {
    if (_SWPage.LOG.isDebugEnabled()) {
    	_SWPage.LOG.debug( "updating accessible from " + accessible() + " to " + value);
    }
    takeStoredValueForKey(value, _SWPage.ACCESSIBLE_KEY);
  }

  public String customInfoData() {
    return (String) storedValueForKey(_SWPage.CUSTOM_INFO_DATA_KEY);
  }

  public void setCustomInfoData(String value) {
    if (_SWPage.LOG.isDebugEnabled()) {
    	_SWPage.LOG.debug( "updating customInfoData from " + customInfoData() + " to " + value);
    }
    takeStoredValueForKey(value, _SWPage.CUSTOM_INFO_DATA_KEY);
  }

  public String externalURL() {
    return (String) storedValueForKey(_SWPage.EXTERNAL_URL_KEY);
  }

  public void setExternalURL(String value) {
    if (_SWPage.LOG.isDebugEnabled()) {
    	_SWPage.LOG.debug( "updating externalURL from " + externalURL() + " to " + value);
    }
    takeStoredValueForKey(value, _SWPage.EXTERNAL_URL_KEY);
  }

  public Integer id() {
    return (Integer) storedValueForKey(_SWPage.ID_KEY);
  }

  public void setId(Integer value) {
    if (_SWPage.LOG.isDebugEnabled()) {
    	_SWPage.LOG.debug( "updating id from " + id() + " to " + value);
    }
    takeStoredValueForKey(value, _SWPage.ID_KEY);
  }

  public Integer inheritsPrivileges() {
    return (Integer) storedValueForKey(_SWPage.INHERITS_PRIVILEGES_KEY);
  }

  public void setInheritsPrivileges(Integer value) {
    if (_SWPage.LOG.isDebugEnabled()) {
    	_SWPage.LOG.debug( "updating inheritsPrivileges from " + inheritsPrivileges() + " to " + value);
    }
    takeStoredValueForKey(value, _SWPage.INHERITS_PRIVILEGES_KEY);
  }

  public String keywords() {
    return (String) storedValueForKey(_SWPage.KEYWORDS_KEY);
  }

  public void setKeywords(String value) {
    if (_SWPage.LOG.isDebugEnabled()) {
    	_SWPage.LOG.debug( "updating keywords from " + keywords() + " to " + value);
    }
    takeStoredValueForKey(value, _SWPage.KEYWORDS_KEY);
  }

  public String language() {
    return (String) storedValueForKey(_SWPage.LANGUAGE_KEY);
  }

  public void setLanguage(String value) {
    if (_SWPage.LOG.isDebugEnabled()) {
    	_SWPage.LOG.debug( "updating language from " + language() + " to " + value);
    }
    takeStoredValueForKey(value, _SWPage.LANGUAGE_KEY);
  }

  public String look() {
    return (String) storedValueForKey(_SWPage.LOOK_KEY);
  }

  public void setLook(String value) {
    if (_SWPage.LOG.isDebugEnabled()) {
    	_SWPage.LOG.debug( "updating look from " + look() + " to " + value);
    }
    takeStoredValueForKey(value, _SWPage.LOOK_KEY);
  }

  public String name() {
    return (String) storedValueForKey(_SWPage.NAME_KEY);
  }

  public void setName(String value) {
    if (_SWPage.LOG.isDebugEnabled()) {
    	_SWPage.LOG.debug( "updating name from " + name() + " to " + value);
    }
    takeStoredValueForKey(value, _SWPage.NAME_KEY);
  }

  public String namePrefix() {
    return (String) storedValueForKey(_SWPage.NAME_PREFIX_KEY);
  }

  public void setNamePrefix(String value) {
    if (_SWPage.LOG.isDebugEnabled()) {
    	_SWPage.LOG.debug( "updating namePrefix from " + namePrefix() + " to " + value);
    }
    takeStoredValueForKey(value, _SWPage.NAME_PREFIX_KEY);
  }

  public Integer parentID() {
    return (Integer) storedValueForKey(_SWPage.PARENT_ID_KEY);
  }

  public void setParentID(Integer value) {
    if (_SWPage.LOG.isDebugEnabled()) {
    	_SWPage.LOG.debug( "updating parentID from " + parentID() + " to " + value);
    }
    takeStoredValueForKey(value, _SWPage.PARENT_ID_KEY);
  }

  public String password() {
    return (String) storedValueForKey(_SWPage.PASSWORD_KEY);
  }

  public void setPassword(String value) {
    if (_SWPage.LOG.isDebugEnabled()) {
    	_SWPage.LOG.debug( "updating password from " + password() + " to " + value);
    }
    takeStoredValueForKey(value, _SWPage.PASSWORD_KEY);
  }

  public Integer pictureOneID() {
    return (Integer) storedValueForKey(_SWPage.PICTURE_ONE_ID_KEY);
  }

  public void setPictureOneID(Integer value) {
    if (_SWPage.LOG.isDebugEnabled()) {
    	_SWPage.LOG.debug( "updating pictureOneID from " + pictureOneID() + " to " + value);
    }
    takeStoredValueForKey(value, _SWPage.PICTURE_ONE_ID_KEY);
  }

  public Integer pictureTwoID() {
    return (Integer) storedValueForKey(_SWPage.PICTURE_TWO_ID_KEY);
  }

  public void setPictureTwoID(Integer value) {
    if (_SWPage.LOG.isDebugEnabled()) {
    	_SWPage.LOG.debug( "updating pictureTwoID from " + pictureTwoID() + " to " + value);
    }
    takeStoredValueForKey(value, _SWPage.PICTURE_TWO_ID_KEY);
  }

  public Integer published() {
    return (Integer) storedValueForKey(_SWPage.PUBLISHED_KEY);
  }

  public void setPublished(Integer value) {
    if (_SWPage.LOG.isDebugEnabled()) {
    	_SWPage.LOG.debug( "updating published from " + published() + " to " + value);
    }
    takeStoredValueForKey(value, _SWPage.PUBLISHED_KEY);
  }

  public Integer sortNumber() {
    return (Integer) storedValueForKey(_SWPage.SORT_NUMBER_KEY);
  }

  public void setSortNumber(Integer value) {
    if (_SWPage.LOG.isDebugEnabled()) {
    	_SWPage.LOG.debug( "updating sortNumber from " + sortNumber() + " to " + value);
    }
    takeStoredValueForKey(value, _SWPage.SORT_NUMBER_KEY);
  }

  public String symbol() {
    return (String) storedValueForKey(_SWPage.SYMBOL_KEY);
  }

  public void setSymbol(String value) {
    if (_SWPage.LOG.isDebugEnabled()) {
    	_SWPage.LOG.debug( "updating symbol from " + symbol() + " to " + value);
    }
    takeStoredValueForKey(value, _SWPage.SYMBOL_KEY);
  }

  public String text() {
    return (String) storedValueForKey(_SWPage.TEXT_KEY);
  }

  public void setText(String value) {
    if (_SWPage.LOG.isDebugEnabled()) {
    	_SWPage.LOG.debug( "updating text from " + text() + " to " + value);
    }
    takeStoredValueForKey(value, _SWPage.TEXT_KEY);
  }

  public NSTimestamp timeIn() {
    return (NSTimestamp) storedValueForKey(_SWPage.TIME_IN_KEY);
  }

  public void setTimeIn(NSTimestamp value) {
    if (_SWPage.LOG.isDebugEnabled()) {
    	_SWPage.LOG.debug( "updating timeIn from " + timeIn() + " to " + value);
    }
    takeStoredValueForKey(value, _SWPage.TIME_IN_KEY);
  }

  public NSTimestamp timeOut() {
    return (NSTimestamp) storedValueForKey(_SWPage.TIME_OUT_KEY);
  }

  public void setTimeOut(NSTimestamp value) {
    if (_SWPage.LOG.isDebugEnabled()) {
    	_SWPage.LOG.debug( "updating timeOut from " + timeOut() + " to " + value);
    }
    takeStoredValueForKey(value, _SWPage.TIME_OUT_KEY);
  }

  public concept.data.SWPicture imageOne() {
    return (concept.data.SWPicture)storedValueForKey(_SWPage.IMAGE_ONE_KEY);
  }
  
  public void setImageOne(concept.data.SWPicture value) {
    takeStoredValueForKey(value, _SWPage.IMAGE_ONE_KEY);
  }

  public void setImageOneRelationship(concept.data.SWPicture value) {
    if (_SWPage.LOG.isDebugEnabled()) {
      _SWPage.LOG.debug("updating imageOne from " + imageOne() + " to " + value);
    }
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
    	setImageOne(value);
    }
    else if (value == null) {
    	concept.data.SWPicture oldValue = imageOne();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, _SWPage.IMAGE_ONE_KEY);
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, _SWPage.IMAGE_ONE_KEY);
    }
  }
  
  public concept.data.SWPicture imageTwo() {
    return (concept.data.SWPicture)storedValueForKey(_SWPage.IMAGE_TWO_KEY);
  }
  
  public void setImageTwo(concept.data.SWPicture value) {
    takeStoredValueForKey(value, _SWPage.IMAGE_TWO_KEY);
  }

  public void setImageTwoRelationship(concept.data.SWPicture value) {
    if (_SWPage.LOG.isDebugEnabled()) {
      _SWPage.LOG.debug("updating imageTwo from " + imageTwo() + " to " + value);
    }
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
    	setImageTwo(value);
    }
    else if (value == null) {
    	concept.data.SWPicture oldValue = imageTwo();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, _SWPage.IMAGE_TWO_KEY);
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, _SWPage.IMAGE_TWO_KEY);
    }
  }
  
  public concept.data.SWPage parent() {
    return (concept.data.SWPage)storedValueForKey(_SWPage.PARENT_KEY);
  }
  
  public void setParent(concept.data.SWPage value) {
    takeStoredValueForKey(value, _SWPage.PARENT_KEY);
  }

  public void setParentRelationship(concept.data.SWPage value) {
    if (_SWPage.LOG.isDebugEnabled()) {
      _SWPage.LOG.debug("updating parent from " + parent() + " to " + value);
    }
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
    	setParent(value);
    }
    else if (value == null) {
    	concept.data.SWPage oldValue = parent();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, _SWPage.PARENT_KEY);
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, _SWPage.PARENT_KEY);
    }
  }
  
  public NSArray<concept.data.SWPage> children() {
    return (NSArray<concept.data.SWPage>)storedValueForKey(_SWPage.CHILDREN_KEY);
  }

  public NSArray<concept.data.SWPage> children(EOQualifier qualifier) {
    return children(qualifier, null, false);
  }

  public NSArray<concept.data.SWPage> children(EOQualifier qualifier, boolean fetch) {
    return children(qualifier, null, fetch);
  }

  public NSArray<concept.data.SWPage> children(EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings, boolean fetch) {
    NSArray<concept.data.SWPage> results;
    if (fetch) {
      EOQualifier fullQualifier;
      EOQualifier inverseQualifier = new EOKeyValueQualifier(concept.data.SWPage.PARENT_KEY, EOQualifier.QualifierOperatorEqual, this);
    	
      if (qualifier == null) {
        fullQualifier = inverseQualifier;
      }
      else {
        NSMutableArray<EOQualifier> qualifiers = new NSMutableArray<EOQualifier>();
        qualifiers.addObject(qualifier);
        qualifiers.addObject(inverseQualifier);
        fullQualifier = new EOAndQualifier(qualifiers);
      }

      results = concept.data.SWPage.fetchSWPages(editingContext(), fullQualifier, sortOrderings);
    }
    else {
      results = children();
      if (qualifier != null) {
        results = (NSArray<concept.data.SWPage>)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray<concept.data.SWPage>)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    }
    return results;
  }
  
  public void addToChildren(concept.data.SWPage object) {
    includeObjectIntoPropertyWithKey(object, _SWPage.CHILDREN_KEY);
  }

  public void removeFromChildren(concept.data.SWPage object) {
    excludeObjectFromPropertyWithKey(object, _SWPage.CHILDREN_KEY);
  }

  public void addToChildrenRelationship(concept.data.SWPage object) {
    if (_SWPage.LOG.isDebugEnabled()) {
      _SWPage.LOG.debug("adding " + object + " to children relationship");
    }
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
    	addToChildren(object);
    }
    else {
    	addObjectToBothSidesOfRelationshipWithKey(object, _SWPage.CHILDREN_KEY);
    }
  }

  public void removeFromChildrenRelationship(concept.data.SWPage object) {
    if (_SWPage.LOG.isDebugEnabled()) {
      _SWPage.LOG.debug("removing " + object + " from children relationship");
    }
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
    	removeFromChildren(object);
    }
    else {
    	removeObjectFromBothSidesOfRelationshipWithKey(object, _SWPage.CHILDREN_KEY);
    }
  }

  public concept.data.SWPage createChildrenRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName( concept.data.SWPage.ENTITY_NAME );
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, _SWPage.CHILDREN_KEY);
    return (concept.data.SWPage) eo;
  }

  public void deleteChildrenRelationship(concept.data.SWPage object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, _SWPage.CHILDREN_KEY);
    editingContext().deleteObject(object);
  }

  public void deleteAllChildrenRelationships() {
    Enumeration<concept.data.SWPage> objects = children().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteChildrenRelationship(objects.nextElement());
    }
  }

  public NSArray<concept.data.SWComponent> components() {
    return (NSArray<concept.data.SWComponent>)storedValueForKey(_SWPage.COMPONENTS_KEY);
  }

  public NSArray<concept.data.SWComponent> components(EOQualifier qualifier) {
    return components(qualifier, null, false);
  }

  public NSArray<concept.data.SWComponent> components(EOQualifier qualifier, boolean fetch) {
    return components(qualifier, null, fetch);
  }

  public NSArray<concept.data.SWComponent> components(EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings, boolean fetch) {
    NSArray<concept.data.SWComponent> results;
    if (fetch) {
      EOQualifier fullQualifier;
      EOQualifier inverseQualifier = new EOKeyValueQualifier(concept.data.SWComponent.PAGE_KEY, EOQualifier.QualifierOperatorEqual, this);
    	
      if (qualifier == null) {
        fullQualifier = inverseQualifier;
      }
      else {
        NSMutableArray<EOQualifier> qualifiers = new NSMutableArray<EOQualifier>();
        qualifiers.addObject(qualifier);
        qualifiers.addObject(inverseQualifier);
        fullQualifier = new EOAndQualifier(qualifiers);
      }

      results = concept.data.SWComponent.fetchSWComponents(editingContext(), fullQualifier, sortOrderings);
    }
    else {
      results = components();
      if (qualifier != null) {
        results = (NSArray<concept.data.SWComponent>)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray<concept.data.SWComponent>)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    }
    return results;
  }
  
  public void addToComponents(concept.data.SWComponent object) {
    includeObjectIntoPropertyWithKey(object, _SWPage.COMPONENTS_KEY);
  }

  public void removeFromComponents(concept.data.SWComponent object) {
    excludeObjectFromPropertyWithKey(object, _SWPage.COMPONENTS_KEY);
  }

  public void addToComponentsRelationship(concept.data.SWComponent object) {
    if (_SWPage.LOG.isDebugEnabled()) {
      _SWPage.LOG.debug("adding " + object + " to components relationship");
    }
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
    	addToComponents(object);
    }
    else {
    	addObjectToBothSidesOfRelationshipWithKey(object, _SWPage.COMPONENTS_KEY);
    }
  }

  public void removeFromComponentsRelationship(concept.data.SWComponent object) {
    if (_SWPage.LOG.isDebugEnabled()) {
      _SWPage.LOG.debug("removing " + object + " from components relationship");
    }
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
    	removeFromComponents(object);
    }
    else {
    	removeObjectFromBothSidesOfRelationshipWithKey(object, _SWPage.COMPONENTS_KEY);
    }
  }

  public concept.data.SWComponent createComponentsRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName( concept.data.SWComponent.ENTITY_NAME );
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, _SWPage.COMPONENTS_KEY);
    return (concept.data.SWComponent) eo;
  }

  public void deleteComponentsRelationship(concept.data.SWComponent object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, _SWPage.COMPONENTS_KEY);
    editingContext().deleteObject(object);
  }

  public void deleteAllComponentsRelationships() {
    Enumeration<concept.data.SWComponent> objects = components().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteComponentsRelationship(objects.nextElement());
    }
  }

  public NSArray<concept.data.SWSite> site() {
    return (NSArray<concept.data.SWSite>)storedValueForKey(_SWPage.SITE_KEY);
  }

  public NSArray<concept.data.SWSite> site(EOQualifier qualifier) {
    return site(qualifier, null, false);
  }

  public NSArray<concept.data.SWSite> site(EOQualifier qualifier, boolean fetch) {
    return site(qualifier, null, fetch);
  }

  public NSArray<concept.data.SWSite> site(EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings, boolean fetch) {
    NSArray<concept.data.SWSite> results;
    if (fetch) {
      EOQualifier fullQualifier;
      EOQualifier inverseQualifier = new EOKeyValueQualifier(concept.data.SWSite.FRONTPAGE_KEY, EOQualifier.QualifierOperatorEqual, this);
    	
      if (qualifier == null) {
        fullQualifier = inverseQualifier;
      }
      else {
        NSMutableArray<EOQualifier> qualifiers = new NSMutableArray<EOQualifier>();
        qualifiers.addObject(qualifier);
        qualifiers.addObject(inverseQualifier);
        fullQualifier = new EOAndQualifier(qualifiers);
      }

      results = concept.data.SWSite.fetchSWSites(editingContext(), fullQualifier, sortOrderings);
    }
    else {
      results = site();
      if (qualifier != null) {
        results = (NSArray<concept.data.SWSite>)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray<concept.data.SWSite>)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    }
    return results;
  }
  
  public void addToSite(concept.data.SWSite object) {
    includeObjectIntoPropertyWithKey(object, _SWPage.SITE_KEY);
  }

  public void removeFromSite(concept.data.SWSite object) {
    excludeObjectFromPropertyWithKey(object, _SWPage.SITE_KEY);
  }

  public void addToSiteRelationship(concept.data.SWSite object) {
    if (_SWPage.LOG.isDebugEnabled()) {
      _SWPage.LOG.debug("adding " + object + " to site relationship");
    }
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
    	addToSite(object);
    }
    else {
    	addObjectToBothSidesOfRelationshipWithKey(object, _SWPage.SITE_KEY);
    }
  }

  public void removeFromSiteRelationship(concept.data.SWSite object) {
    if (_SWPage.LOG.isDebugEnabled()) {
      _SWPage.LOG.debug("removing " + object + " from site relationship");
    }
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
    	removeFromSite(object);
    }
    else {
    	removeObjectFromBothSidesOfRelationshipWithKey(object, _SWPage.SITE_KEY);
    }
  }

  public concept.data.SWSite createSiteRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName( concept.data.SWSite.ENTITY_NAME );
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, _SWPage.SITE_KEY);
    return (concept.data.SWSite) eo;
  }

  public void deleteSiteRelationship(concept.data.SWSite object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, _SWPage.SITE_KEY);
    editingContext().deleteObject(object);
  }

  public void deleteAllSiteRelationships() {
    Enumeration<concept.data.SWSite> objects = site().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteSiteRelationship(objects.nextElement());
    }
  }


  public static concept.data.SWPage createSWPage(EOEditingContext editingContext, Integer id
) {
    concept.data.SWPage eo = (concept.data.SWPage) EOUtilities.createAndInsertInstance(editingContext, _SWPage.ENTITY_NAME);    
		eo.setId(id);
    return eo;
  }

  public static ERXFetchSpecification<concept.data.SWPage> fetchSpec() {
    return new ERXFetchSpecification<concept.data.SWPage>(_SWPage.ENTITY_NAME, null, null, false, true, null);
  }

  public static NSArray<concept.data.SWPage> fetchAllSWPages(EOEditingContext editingContext) {
    return _SWPage.fetchAllSWPages(editingContext, null);
  }

  public static NSArray<concept.data.SWPage> fetchAllSWPages(EOEditingContext editingContext, NSArray<EOSortOrdering> sortOrderings) {
    return _SWPage.fetchSWPages(editingContext, null, sortOrderings);
  }

  public static NSArray<concept.data.SWPage> fetchSWPages(EOEditingContext editingContext, EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings) {
    ERXFetchSpecification<concept.data.SWPage> fetchSpec = new ERXFetchSpecification<concept.data.SWPage>(_SWPage.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray<concept.data.SWPage> eoObjects = fetchSpec.fetchObjects(editingContext);
    return eoObjects;
  }

  public static concept.data.SWPage fetchSWPage(EOEditingContext editingContext, String keyName, Object value) {
    return _SWPage.fetchSWPage(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static concept.data.SWPage fetchSWPage(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray<concept.data.SWPage> eoObjects = _SWPage.fetchSWPages(editingContext, qualifier, null);
    concept.data.SWPage eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one SWPage that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static concept.data.SWPage fetchRequiredSWPage(EOEditingContext editingContext, String keyName, Object value) {
    return _SWPage.fetchRequiredSWPage(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static concept.data.SWPage fetchRequiredSWPage(EOEditingContext editingContext, EOQualifier qualifier) {
    concept.data.SWPage eoObject = _SWPage.fetchSWPage(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no SWPage that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static concept.data.SWPage localInstanceIn(EOEditingContext editingContext, concept.data.SWPage eo) {
    concept.data.SWPage localInstance = (eo == null) ? null : ERXEOControlUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
