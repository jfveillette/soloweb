// DO NOT EDIT.  Make changes to concept.data.SWPicture.java instead.
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
public abstract class _SWPicture extends  ERXGenericRecord {
  public static final String ENTITY_NAME = "SWPicture";

  // Attribute Keys
  public static final ERXKey<String> ALT_TEXT = new ERXKey<String>("altText");
  public static final ERXKey<Integer> ASSET_FOLDER_ID = new ERXKey<Integer>("assetFolderID");
  public static final ERXKey<String> CUSTOM_INFO_DATA = new ERXKey<String>("customInfoData");
  public static final ERXKey<String> DESCRIPTION = new ERXKey<String>("description");
  public static final ERXKey<String> DISPLAY_NAME = new ERXKey<String>("displayName");
  public static final ERXKey<String> EXTENSION = new ERXKey<String>("extension");
  public static final ERXKey<Integer> ID = new ERXKey<Integer>("id");
  public static final ERXKey<String> NAME = new ERXKey<String>("name");
  // Relationship Keys
  public static final ERXKey<concept.data.SWAssetFolder> FOLDER = new ERXKey<concept.data.SWAssetFolder>("folder");
  public static final ERXKey<concept.data.SWPictureLink> SW_PICTURE_LINKS = new ERXKey<concept.data.SWPictureLink>("swPictureLinks");

  // Attributes
  public static final String ALT_TEXT_KEY = ALT_TEXT.key();
  public static final String ASSET_FOLDER_ID_KEY = ASSET_FOLDER_ID.key();
  public static final String CUSTOM_INFO_DATA_KEY = CUSTOM_INFO_DATA.key();
  public static final String DESCRIPTION_KEY = DESCRIPTION.key();
  public static final String DISPLAY_NAME_KEY = DISPLAY_NAME.key();
  public static final String EXTENSION_KEY = EXTENSION.key();
  public static final String ID_KEY = ID.key();
  public static final String NAME_KEY = NAME.key();
  // Relationships
  public static final String FOLDER_KEY = FOLDER.key();
  public static final String SW_PICTURE_LINKS_KEY = SW_PICTURE_LINKS.key();

  private static Logger LOG = Logger.getLogger(_SWPicture.class);

  public concept.data.SWPicture localInstanceIn(EOEditingContext editingContext) {
    concept.data.SWPicture localInstance = (concept.data.SWPicture)EOUtilities.localInstanceOfObject(editingContext, this);
    if (localInstance == null) {
      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
    }
    return localInstance;
  }

  public String altText() {
    return (String) storedValueForKey(_SWPicture.ALT_TEXT_KEY);
  }

  public void setAltText(String value) {
    if (_SWPicture.LOG.isDebugEnabled()) {
    	_SWPicture.LOG.debug( "updating altText from " + altText() + " to " + value);
    }
    takeStoredValueForKey(value, _SWPicture.ALT_TEXT_KEY);
  }

  public Integer assetFolderID() {
    return (Integer) storedValueForKey(_SWPicture.ASSET_FOLDER_ID_KEY);
  }

  public void setAssetFolderID(Integer value) {
    if (_SWPicture.LOG.isDebugEnabled()) {
    	_SWPicture.LOG.debug( "updating assetFolderID from " + assetFolderID() + " to " + value);
    }
    takeStoredValueForKey(value, _SWPicture.ASSET_FOLDER_ID_KEY);
  }

  public String customInfoData() {
    return (String) storedValueForKey(_SWPicture.CUSTOM_INFO_DATA_KEY);
  }

  public void setCustomInfoData(String value) {
    if (_SWPicture.LOG.isDebugEnabled()) {
    	_SWPicture.LOG.debug( "updating customInfoData from " + customInfoData() + " to " + value);
    }
    takeStoredValueForKey(value, _SWPicture.CUSTOM_INFO_DATA_KEY);
  }

  public String description() {
    return (String) storedValueForKey(_SWPicture.DESCRIPTION_KEY);
  }

  public void setDescription(String value) {
    if (_SWPicture.LOG.isDebugEnabled()) {
    	_SWPicture.LOG.debug( "updating description from " + description() + " to " + value);
    }
    takeStoredValueForKey(value, _SWPicture.DESCRIPTION_KEY);
  }

  public String displayName() {
    return (String) storedValueForKey(_SWPicture.DISPLAY_NAME_KEY);
  }

  public void setDisplayName(String value) {
    if (_SWPicture.LOG.isDebugEnabled()) {
    	_SWPicture.LOG.debug( "updating displayName from " + displayName() + " to " + value);
    }
    takeStoredValueForKey(value, _SWPicture.DISPLAY_NAME_KEY);
  }

  public String extension() {
    return (String) storedValueForKey(_SWPicture.EXTENSION_KEY);
  }

  public void setExtension(String value) {
    if (_SWPicture.LOG.isDebugEnabled()) {
    	_SWPicture.LOG.debug( "updating extension from " + extension() + " to " + value);
    }
    takeStoredValueForKey(value, _SWPicture.EXTENSION_KEY);
  }

  public Integer id() {
    return (Integer) storedValueForKey(_SWPicture.ID_KEY);
  }

  public void setId(Integer value) {
    if (_SWPicture.LOG.isDebugEnabled()) {
    	_SWPicture.LOG.debug( "updating id from " + id() + " to " + value);
    }
    takeStoredValueForKey(value, _SWPicture.ID_KEY);
  }

  public String name() {
    return (String) storedValueForKey(_SWPicture.NAME_KEY);
  }

  public void setName(String value) {
    if (_SWPicture.LOG.isDebugEnabled()) {
    	_SWPicture.LOG.debug( "updating name from " + name() + " to " + value);
    }
    takeStoredValueForKey(value, _SWPicture.NAME_KEY);
  }

  public concept.data.SWAssetFolder folder() {
    return (concept.data.SWAssetFolder)storedValueForKey(_SWPicture.FOLDER_KEY);
  }
  
  public void setFolder(concept.data.SWAssetFolder value) {
    takeStoredValueForKey(value, _SWPicture.FOLDER_KEY);
  }

  public void setFolderRelationship(concept.data.SWAssetFolder value) {
    if (_SWPicture.LOG.isDebugEnabled()) {
      _SWPicture.LOG.debug("updating folder from " + folder() + " to " + value);
    }
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
    	setFolder(value);
    }
    else if (value == null) {
    	concept.data.SWAssetFolder oldValue = folder();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, _SWPicture.FOLDER_KEY);
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, _SWPicture.FOLDER_KEY);
    }
  }
  
  public NSArray<concept.data.SWPictureLink> swPictureLinks() {
    return (NSArray<concept.data.SWPictureLink>)storedValueForKey(_SWPicture.SW_PICTURE_LINKS_KEY);
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
      EOQualifier inverseQualifier = new EOKeyValueQualifier(concept.data.SWPictureLink.PICTURE_KEY, EOQualifier.QualifierOperatorEqual, this);
    	
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
    includeObjectIntoPropertyWithKey(object, _SWPicture.SW_PICTURE_LINKS_KEY);
  }

  public void removeFromSwPictureLinks(concept.data.SWPictureLink object) {
    excludeObjectFromPropertyWithKey(object, _SWPicture.SW_PICTURE_LINKS_KEY);
  }

  public void addToSwPictureLinksRelationship(concept.data.SWPictureLink object) {
    if (_SWPicture.LOG.isDebugEnabled()) {
      _SWPicture.LOG.debug("adding " + object + " to swPictureLinks relationship");
    }
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
    	addToSwPictureLinks(object);
    }
    else {
    	addObjectToBothSidesOfRelationshipWithKey(object, _SWPicture.SW_PICTURE_LINKS_KEY);
    }
  }

  public void removeFromSwPictureLinksRelationship(concept.data.SWPictureLink object) {
    if (_SWPicture.LOG.isDebugEnabled()) {
      _SWPicture.LOG.debug("removing " + object + " from swPictureLinks relationship");
    }
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
    	removeFromSwPictureLinks(object);
    }
    else {
    	removeObjectFromBothSidesOfRelationshipWithKey(object, _SWPicture.SW_PICTURE_LINKS_KEY);
    }
  }

  public concept.data.SWPictureLink createSwPictureLinksRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName( concept.data.SWPictureLink.ENTITY_NAME );
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, _SWPicture.SW_PICTURE_LINKS_KEY);
    return (concept.data.SWPictureLink) eo;
  }

  public void deleteSwPictureLinksRelationship(concept.data.SWPictureLink object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, _SWPicture.SW_PICTURE_LINKS_KEY);
    editingContext().deleteObject(object);
  }

  public void deleteAllSwPictureLinksRelationships() {
    Enumeration<concept.data.SWPictureLink> objects = swPictureLinks().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteSwPictureLinksRelationship(objects.nextElement());
    }
  }


  public static concept.data.SWPicture createSWPicture(EOEditingContext editingContext, Integer id
) {
    concept.data.SWPicture eo = (concept.data.SWPicture) EOUtilities.createAndInsertInstance(editingContext, _SWPicture.ENTITY_NAME);    
		eo.setId(id);
    return eo;
  }

  public static ERXFetchSpecification<concept.data.SWPicture> fetchSpec() {
    return new ERXFetchSpecification<concept.data.SWPicture>(_SWPicture.ENTITY_NAME, null, null, false, true, null);
  }

  public static NSArray<concept.data.SWPicture> fetchAllSWPictures(EOEditingContext editingContext) {
    return _SWPicture.fetchAllSWPictures(editingContext, null);
  }

  public static NSArray<concept.data.SWPicture> fetchAllSWPictures(EOEditingContext editingContext, NSArray<EOSortOrdering> sortOrderings) {
    return _SWPicture.fetchSWPictures(editingContext, null, sortOrderings);
  }

  public static NSArray<concept.data.SWPicture> fetchSWPictures(EOEditingContext editingContext, EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings) {
    ERXFetchSpecification<concept.data.SWPicture> fetchSpec = new ERXFetchSpecification<concept.data.SWPicture>(_SWPicture.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray<concept.data.SWPicture> eoObjects = fetchSpec.fetchObjects(editingContext);
    return eoObjects;
  }

  public static concept.data.SWPicture fetchSWPicture(EOEditingContext editingContext, String keyName, Object value) {
    return _SWPicture.fetchSWPicture(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static concept.data.SWPicture fetchSWPicture(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray<concept.data.SWPicture> eoObjects = _SWPicture.fetchSWPictures(editingContext, qualifier, null);
    concept.data.SWPicture eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one SWPicture that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static concept.data.SWPicture fetchRequiredSWPicture(EOEditingContext editingContext, String keyName, Object value) {
    return _SWPicture.fetchRequiredSWPicture(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static concept.data.SWPicture fetchRequiredSWPicture(EOEditingContext editingContext, EOQualifier qualifier) {
    concept.data.SWPicture eoObject = _SWPicture.fetchSWPicture(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no SWPicture that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static concept.data.SWPicture localInstanceIn(EOEditingContext editingContext, concept.data.SWPicture eo) {
    concept.data.SWPicture localInstance = (eo == null) ? null : ERXEOControlUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
