// DO NOT EDIT.  Make changes to concept.data.SWFolderLink.java instead.
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
public abstract class _SWFolderLink extends  ERXGenericRecord {
  public static final String ENTITY_NAME = "SWFolderLink";

  // Attribute Keys
  public static final ERXKey<String> TARGET_ENTITY_NAME = new ERXKey<String>("targetEntityName");
  public static final ERXKey<String> TARGET_ID = new ERXKey<String>("targetID");
  // Relationship Keys
  public static final ERXKey<concept.data.SWFolder> FOLDER = new ERXKey<concept.data.SWFolder>("folder");

  // Attributes
  public static final String TARGET_ENTITY_NAME_KEY = TARGET_ENTITY_NAME.key();
  public static final String TARGET_ID_KEY = TARGET_ID.key();
  // Relationships
  public static final String FOLDER_KEY = FOLDER.key();

  private static Logger LOG = Logger.getLogger(_SWFolderLink.class);

  public concept.data.SWFolderLink localInstanceIn(EOEditingContext editingContext) {
    concept.data.SWFolderLink localInstance = (concept.data.SWFolderLink)EOUtilities.localInstanceOfObject(editingContext, this);
    if (localInstance == null) {
      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
    }
    return localInstance;
  }

  public String targetEntityName() {
    return (String) storedValueForKey(_SWFolderLink.TARGET_ENTITY_NAME_KEY);
  }

  public void setTargetEntityName(String value) {
    if (_SWFolderLink.LOG.isDebugEnabled()) {
    	_SWFolderLink.LOG.debug( "updating targetEntityName from " + targetEntityName() + " to " + value);
    }
    takeStoredValueForKey(value, _SWFolderLink.TARGET_ENTITY_NAME_KEY);
  }

  public String targetID() {
    return (String) storedValueForKey(_SWFolderLink.TARGET_ID_KEY);
  }

  public void setTargetID(String value) {
    if (_SWFolderLink.LOG.isDebugEnabled()) {
    	_SWFolderLink.LOG.debug( "updating targetID from " + targetID() + " to " + value);
    }
    takeStoredValueForKey(value, _SWFolderLink.TARGET_ID_KEY);
  }

  public concept.data.SWFolder folder() {
    return (concept.data.SWFolder)storedValueForKey(_SWFolderLink.FOLDER_KEY);
  }
  
  public void setFolder(concept.data.SWFolder value) {
    takeStoredValueForKey(value, _SWFolderLink.FOLDER_KEY);
  }

  public void setFolderRelationship(concept.data.SWFolder value) {
    if (_SWFolderLink.LOG.isDebugEnabled()) {
      _SWFolderLink.LOG.debug("updating folder from " + folder() + " to " + value);
    }
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
    	setFolder(value);
    }
    else if (value == null) {
    	concept.data.SWFolder oldValue = folder();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, _SWFolderLink.FOLDER_KEY);
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, _SWFolderLink.FOLDER_KEY);
    }
  }
  

  public static concept.data.SWFolderLink createSWFolderLink(EOEditingContext editingContext) {
    concept.data.SWFolderLink eo = (concept.data.SWFolderLink) EOUtilities.createAndInsertInstance(editingContext, _SWFolderLink.ENTITY_NAME);    
    return eo;
  }

  public static ERXFetchSpecification<concept.data.SWFolderLink> fetchSpec() {
    return new ERXFetchSpecification<concept.data.SWFolderLink>(_SWFolderLink.ENTITY_NAME, null, null, false, true, null);
  }

  public static NSArray<concept.data.SWFolderLink> fetchAllSWFolderLinks(EOEditingContext editingContext) {
    return _SWFolderLink.fetchAllSWFolderLinks(editingContext, null);
  }

  public static NSArray<concept.data.SWFolderLink> fetchAllSWFolderLinks(EOEditingContext editingContext, NSArray<EOSortOrdering> sortOrderings) {
    return _SWFolderLink.fetchSWFolderLinks(editingContext, null, sortOrderings);
  }

  public static NSArray<concept.data.SWFolderLink> fetchSWFolderLinks(EOEditingContext editingContext, EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings) {
    ERXFetchSpecification<concept.data.SWFolderLink> fetchSpec = new ERXFetchSpecification<concept.data.SWFolderLink>(_SWFolderLink.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray<concept.data.SWFolderLink> eoObjects = fetchSpec.fetchObjects(editingContext);
    return eoObjects;
  }

  public static concept.data.SWFolderLink fetchSWFolderLink(EOEditingContext editingContext, String keyName, Object value) {
    return _SWFolderLink.fetchSWFolderLink(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static concept.data.SWFolderLink fetchSWFolderLink(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray<concept.data.SWFolderLink> eoObjects = _SWFolderLink.fetchSWFolderLinks(editingContext, qualifier, null);
    concept.data.SWFolderLink eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one SWFolderLink that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static concept.data.SWFolderLink fetchRequiredSWFolderLink(EOEditingContext editingContext, String keyName, Object value) {
    return _SWFolderLink.fetchRequiredSWFolderLink(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static concept.data.SWFolderLink fetchRequiredSWFolderLink(EOEditingContext editingContext, EOQualifier qualifier) {
    concept.data.SWFolderLink eoObject = _SWFolderLink.fetchSWFolderLink(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no SWFolderLink that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static concept.data.SWFolderLink localInstanceIn(EOEditingContext editingContext, concept.data.SWFolderLink eo) {
    concept.data.SWFolderLink localInstance = (eo == null) ? null : ERXEOControlUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
