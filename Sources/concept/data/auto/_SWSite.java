// DO NOT EDIT.  Make changes to concept.data.SWSite.java instead.
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
public abstract class _SWSite extends  ERXGenericRecord {
  public static final String ENTITY_NAME = "SWSite";

  // Attribute Keys
  public static final ERXKey<Integer> ALTERNATE_DEFAULT_PAGE_ID = new ERXKey<Integer>("alternateDefaultPageID");
  public static final ERXKey<String> CLIENT_KEY = new ERXKey<String>("clientKey");
  public static final ERXKey<String> CUSTOM_INFO_DATA = new ERXKey<String>("customInfoData");
  public static final ERXKey<String> LANGUAGE = new ERXKey<String>("language");
  public static final ERXKey<String> LOOK = new ERXKey<String>("look");
  public static final ERXKey<String> NAME = new ERXKey<String>("name");
  public static final ERXKey<String> NO_PAGE_FOUND_ERROR_PAGE_LINKING_NAME = new ERXKey<String>("noPageFoundErrorPageLinkingName");
  public static final ERXKey<Integer> PAGE_ID = new ERXKey<Integer>("pageID");
  public static final ERXKey<String> PRINT_TEMPLATE = new ERXKey<String>("printTemplate");
  public static final ERXKey<String> QUAL = new ERXKey<String>("qual");
  public static final ERXKey<String> SITEGROUP = new ERXKey<String>("sitegroup");
  // Relationship Keys
  public static final ERXKey<concept.data.SWPage> ALTERNATE_DEFAULT_PAGE = new ERXKey<concept.data.SWPage>("alternateDefaultPage");
  public static final ERXKey<concept.data.SWPage> FRONTPAGE = new ERXKey<concept.data.SWPage>("frontpage");

  // Attributes
  public static final String ALTERNATE_DEFAULT_PAGE_ID_KEY = ALTERNATE_DEFAULT_PAGE_ID.key();
  public static final String CLIENT_KEY_KEY = CLIENT_KEY.key();
  public static final String CUSTOM_INFO_DATA_KEY = CUSTOM_INFO_DATA.key();
  public static final String LANGUAGE_KEY = LANGUAGE.key();
  public static final String LOOK_KEY = LOOK.key();
  public static final String NAME_KEY = NAME.key();
  public static final String NO_PAGE_FOUND_ERROR_PAGE_LINKING_NAME_KEY = NO_PAGE_FOUND_ERROR_PAGE_LINKING_NAME.key();
  public static final String PAGE_ID_KEY = PAGE_ID.key();
  public static final String PRINT_TEMPLATE_KEY = PRINT_TEMPLATE.key();
  public static final String QUAL_KEY = QUAL.key();
  public static final String SITEGROUP_KEY = SITEGROUP.key();
  // Relationships
  public static final String ALTERNATE_DEFAULT_PAGE_KEY = ALTERNATE_DEFAULT_PAGE.key();
  public static final String FRONTPAGE_KEY = FRONTPAGE.key();

  private static Logger LOG = Logger.getLogger(_SWSite.class);

  public concept.data.SWSite localInstanceIn(EOEditingContext editingContext) {
    concept.data.SWSite localInstance = (concept.data.SWSite)EOUtilities.localInstanceOfObject(editingContext, this);
    if (localInstance == null) {
      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
    }
    return localInstance;
  }

  public Integer alternateDefaultPageID() {
    return (Integer) storedValueForKey(_SWSite.ALTERNATE_DEFAULT_PAGE_ID_KEY);
  }

  public void setAlternateDefaultPageID(Integer value) {
    if (_SWSite.LOG.isDebugEnabled()) {
    	_SWSite.LOG.debug( "updating alternateDefaultPageID from " + alternateDefaultPageID() + " to " + value);
    }
    takeStoredValueForKey(value, _SWSite.ALTERNATE_DEFAULT_PAGE_ID_KEY);
  }

  public String clientKey() {
    return (String) storedValueForKey(_SWSite.CLIENT_KEY_KEY);
  }

  public void setClientKey(String value) {
    if (_SWSite.LOG.isDebugEnabled()) {
    	_SWSite.LOG.debug( "updating clientKey from " + clientKey() + " to " + value);
    }
    takeStoredValueForKey(value, _SWSite.CLIENT_KEY_KEY);
  }

  public String customInfoData() {
    return (String) storedValueForKey(_SWSite.CUSTOM_INFO_DATA_KEY);
  }

  public void setCustomInfoData(String value) {
    if (_SWSite.LOG.isDebugEnabled()) {
    	_SWSite.LOG.debug( "updating customInfoData from " + customInfoData() + " to " + value);
    }
    takeStoredValueForKey(value, _SWSite.CUSTOM_INFO_DATA_KEY);
  }

  public String language() {
    return (String) storedValueForKey(_SWSite.LANGUAGE_KEY);
  }

  public void setLanguage(String value) {
    if (_SWSite.LOG.isDebugEnabled()) {
    	_SWSite.LOG.debug( "updating language from " + language() + " to " + value);
    }
    takeStoredValueForKey(value, _SWSite.LANGUAGE_KEY);
  }

  public String look() {
    return (String) storedValueForKey(_SWSite.LOOK_KEY);
  }

  public void setLook(String value) {
    if (_SWSite.LOG.isDebugEnabled()) {
    	_SWSite.LOG.debug( "updating look from " + look() + " to " + value);
    }
    takeStoredValueForKey(value, _SWSite.LOOK_KEY);
  }

  public String name() {
    return (String) storedValueForKey(_SWSite.NAME_KEY);
  }

  public void setName(String value) {
    if (_SWSite.LOG.isDebugEnabled()) {
    	_SWSite.LOG.debug( "updating name from " + name() + " to " + value);
    }
    takeStoredValueForKey(value, _SWSite.NAME_KEY);
  }

  public String noPageFoundErrorPageLinkingName() {
    return (String) storedValueForKey(_SWSite.NO_PAGE_FOUND_ERROR_PAGE_LINKING_NAME_KEY);
  }

  public void setNoPageFoundErrorPageLinkingName(String value) {
    if (_SWSite.LOG.isDebugEnabled()) {
    	_SWSite.LOG.debug( "updating noPageFoundErrorPageLinkingName from " + noPageFoundErrorPageLinkingName() + " to " + value);
    }
    takeStoredValueForKey(value, _SWSite.NO_PAGE_FOUND_ERROR_PAGE_LINKING_NAME_KEY);
  }

  public Integer pageID() {
    return (Integer) storedValueForKey(_SWSite.PAGE_ID_KEY);
  }

  public void setPageID(Integer value) {
    if (_SWSite.LOG.isDebugEnabled()) {
    	_SWSite.LOG.debug( "updating pageID from " + pageID() + " to " + value);
    }
    takeStoredValueForKey(value, _SWSite.PAGE_ID_KEY);
  }

  public String printTemplate() {
    return (String) storedValueForKey(_SWSite.PRINT_TEMPLATE_KEY);
  }

  public void setPrintTemplate(String value) {
    if (_SWSite.LOG.isDebugEnabled()) {
    	_SWSite.LOG.debug( "updating printTemplate from " + printTemplate() + " to " + value);
    }
    takeStoredValueForKey(value, _SWSite.PRINT_TEMPLATE_KEY);
  }

  public String qual() {
    return (String) storedValueForKey(_SWSite.QUAL_KEY);
  }

  public void setQual(String value) {
    if (_SWSite.LOG.isDebugEnabled()) {
    	_SWSite.LOG.debug( "updating qual from " + qual() + " to " + value);
    }
    takeStoredValueForKey(value, _SWSite.QUAL_KEY);
  }

  public String sitegroup() {
    return (String) storedValueForKey(_SWSite.SITEGROUP_KEY);
  }

  public void setSitegroup(String value) {
    if (_SWSite.LOG.isDebugEnabled()) {
    	_SWSite.LOG.debug( "updating sitegroup from " + sitegroup() + " to " + value);
    }
    takeStoredValueForKey(value, _SWSite.SITEGROUP_KEY);
  }

  public concept.data.SWPage alternateDefaultPage() {
    return (concept.data.SWPage)storedValueForKey(_SWSite.ALTERNATE_DEFAULT_PAGE_KEY);
  }
  
  public void setAlternateDefaultPage(concept.data.SWPage value) {
    takeStoredValueForKey(value, _SWSite.ALTERNATE_DEFAULT_PAGE_KEY);
  }

  public void setAlternateDefaultPageRelationship(concept.data.SWPage value) {
    if (_SWSite.LOG.isDebugEnabled()) {
      _SWSite.LOG.debug("updating alternateDefaultPage from " + alternateDefaultPage() + " to " + value);
    }
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
    	setAlternateDefaultPage(value);
    }
    else if (value == null) {
    	concept.data.SWPage oldValue = alternateDefaultPage();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, _SWSite.ALTERNATE_DEFAULT_PAGE_KEY);
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, _SWSite.ALTERNATE_DEFAULT_PAGE_KEY);
    }
  }
  
  public concept.data.SWPage frontpage() {
    return (concept.data.SWPage)storedValueForKey(_SWSite.FRONTPAGE_KEY);
  }
  
  public void setFrontpage(concept.data.SWPage value) {
    takeStoredValueForKey(value, _SWSite.FRONTPAGE_KEY);
  }

  public void setFrontpageRelationship(concept.data.SWPage value) {
    if (_SWSite.LOG.isDebugEnabled()) {
      _SWSite.LOG.debug("updating frontpage from " + frontpage() + " to " + value);
    }
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
    	setFrontpage(value);
    }
    else if (value == null) {
    	concept.data.SWPage oldValue = frontpage();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, _SWSite.FRONTPAGE_KEY);
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, _SWSite.FRONTPAGE_KEY);
    }
  }
  

  public static concept.data.SWSite createSWSite(EOEditingContext editingContext) {
    concept.data.SWSite eo = (concept.data.SWSite) EOUtilities.createAndInsertInstance(editingContext, _SWSite.ENTITY_NAME);    
    return eo;
  }

  public static ERXFetchSpecification<concept.data.SWSite> fetchSpec() {
    return new ERXFetchSpecification<concept.data.SWSite>(_SWSite.ENTITY_NAME, null, null, false, true, null);
  }

  public static NSArray<concept.data.SWSite> fetchAllSWSites(EOEditingContext editingContext) {
    return _SWSite.fetchAllSWSites(editingContext, null);
  }

  public static NSArray<concept.data.SWSite> fetchAllSWSites(EOEditingContext editingContext, NSArray<EOSortOrdering> sortOrderings) {
    return _SWSite.fetchSWSites(editingContext, null, sortOrderings);
  }

  public static NSArray<concept.data.SWSite> fetchSWSites(EOEditingContext editingContext, EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings) {
    ERXFetchSpecification<concept.data.SWSite> fetchSpec = new ERXFetchSpecification<concept.data.SWSite>(_SWSite.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray<concept.data.SWSite> eoObjects = fetchSpec.fetchObjects(editingContext);
    return eoObjects;
  }

  public static concept.data.SWSite fetchSWSite(EOEditingContext editingContext, String keyName, Object value) {
    return _SWSite.fetchSWSite(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static concept.data.SWSite fetchSWSite(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray<concept.data.SWSite> eoObjects = _SWSite.fetchSWSites(editingContext, qualifier, null);
    concept.data.SWSite eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one SWSite that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static concept.data.SWSite fetchRequiredSWSite(EOEditingContext editingContext, String keyName, Object value) {
    return _SWSite.fetchRequiredSWSite(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static concept.data.SWSite fetchRequiredSWSite(EOEditingContext editingContext, EOQualifier qualifier) {
    concept.data.SWSite eoObject = _SWSite.fetchSWSite(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no SWSite that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static concept.data.SWSite localInstanceIn(EOEditingContext editingContext, concept.data.SWSite eo) {
    concept.data.SWSite localInstance = (eo == null) ? null : ERXEOControlUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
