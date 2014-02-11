// DO NOT EDIT.  Make changes to concept.data.SWTag.java instead.
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
public abstract class _SWTag extends  ERXGenericRecord {
  public static final String ENTITY_NAME = "SWTag";

  // Attribute Keys
  public static final ERXKey<NSTimestamp> CREATION_DATE = new ERXKey<NSTimestamp>("creationDate");
  public static final ERXKey<NSTimestamp> MODIFICATION_DATE = new ERXKey<NSTimestamp>("modificationDate");
  public static final ERXKey<String> NAME = new ERXKey<String>("name");
  // Relationship Keys
  public static final ERXKey<concept.data.SWTagging> TAGGINGS = new ERXKey<concept.data.SWTagging>("taggings");

  // Attributes
  public static final String CREATION_DATE_KEY = CREATION_DATE.key();
  public static final String MODIFICATION_DATE_KEY = MODIFICATION_DATE.key();
  public static final String NAME_KEY = NAME.key();
  // Relationships
  public static final String TAGGINGS_KEY = TAGGINGS.key();

  private static Logger LOG = Logger.getLogger(_SWTag.class);

  public concept.data.SWTag localInstanceIn(EOEditingContext editingContext) {
    concept.data.SWTag localInstance = (concept.data.SWTag)EOUtilities.localInstanceOfObject(editingContext, this);
    if (localInstance == null) {
      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
    }
    return localInstance;
  }

  public NSTimestamp creationDate() {
    return (NSTimestamp) storedValueForKey(_SWTag.CREATION_DATE_KEY);
  }

  public void setCreationDate(NSTimestamp value) {
    if (_SWTag.LOG.isDebugEnabled()) {
    	_SWTag.LOG.debug( "updating creationDate from " + creationDate() + " to " + value);
    }
    takeStoredValueForKey(value, _SWTag.CREATION_DATE_KEY);
  }

  public NSTimestamp modificationDate() {
    return (NSTimestamp) storedValueForKey(_SWTag.MODIFICATION_DATE_KEY);
  }

  public void setModificationDate(NSTimestamp value) {
    if (_SWTag.LOG.isDebugEnabled()) {
    	_SWTag.LOG.debug( "updating modificationDate from " + modificationDate() + " to " + value);
    }
    takeStoredValueForKey(value, _SWTag.MODIFICATION_DATE_KEY);
  }

  public String name() {
    return (String) storedValueForKey(_SWTag.NAME_KEY);
  }

  public void setName(String value) {
    if (_SWTag.LOG.isDebugEnabled()) {
    	_SWTag.LOG.debug( "updating name from " + name() + " to " + value);
    }
    takeStoredValueForKey(value, _SWTag.NAME_KEY);
  }

  public NSArray<concept.data.SWTagging> taggings() {
    return (NSArray<concept.data.SWTagging>)storedValueForKey(_SWTag.TAGGINGS_KEY);
  }

  public NSArray<concept.data.SWTagging> taggings(EOQualifier qualifier) {
    return taggings(qualifier, null, false);
  }

  public NSArray<concept.data.SWTagging> taggings(EOQualifier qualifier, boolean fetch) {
    return taggings(qualifier, null, fetch);
  }

  public NSArray<concept.data.SWTagging> taggings(EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings, boolean fetch) {
    NSArray<concept.data.SWTagging> results;
    if (fetch) {
      EOQualifier fullQualifier;
      EOQualifier inverseQualifier = new EOKeyValueQualifier(concept.data.SWTagging.TAG_KEY, EOQualifier.QualifierOperatorEqual, this);
    	
      if (qualifier == null) {
        fullQualifier = inverseQualifier;
      }
      else {
        NSMutableArray<EOQualifier> qualifiers = new NSMutableArray<EOQualifier>();
        qualifiers.addObject(qualifier);
        qualifiers.addObject(inverseQualifier);
        fullQualifier = new EOAndQualifier(qualifiers);
      }

      results = concept.data.SWTagging.fetchSWTaggings(editingContext(), fullQualifier, sortOrderings);
    }
    else {
      results = taggings();
      if (qualifier != null) {
        results = (NSArray<concept.data.SWTagging>)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray<concept.data.SWTagging>)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    }
    return results;
  }
  
  public void addToTaggings(concept.data.SWTagging object) {
    includeObjectIntoPropertyWithKey(object, _SWTag.TAGGINGS_KEY);
  }

  public void removeFromTaggings(concept.data.SWTagging object) {
    excludeObjectFromPropertyWithKey(object, _SWTag.TAGGINGS_KEY);
  }

  public void addToTaggingsRelationship(concept.data.SWTagging object) {
    if (_SWTag.LOG.isDebugEnabled()) {
      _SWTag.LOG.debug("adding " + object + " to taggings relationship");
    }
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
    	addToTaggings(object);
    }
    else {
    	addObjectToBothSidesOfRelationshipWithKey(object, _SWTag.TAGGINGS_KEY);
    }
  }

  public void removeFromTaggingsRelationship(concept.data.SWTagging object) {
    if (_SWTag.LOG.isDebugEnabled()) {
      _SWTag.LOG.debug("removing " + object + " from taggings relationship");
    }
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
    	removeFromTaggings(object);
    }
    else {
    	removeObjectFromBothSidesOfRelationshipWithKey(object, _SWTag.TAGGINGS_KEY);
    }
  }

  public concept.data.SWTagging createTaggingsRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName( concept.data.SWTagging.ENTITY_NAME );
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, _SWTag.TAGGINGS_KEY);
    return (concept.data.SWTagging) eo;
  }

  public void deleteTaggingsRelationship(concept.data.SWTagging object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, _SWTag.TAGGINGS_KEY);
    editingContext().deleteObject(object);
  }

  public void deleteAllTaggingsRelationships() {
    Enumeration<concept.data.SWTagging> objects = taggings().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteTaggingsRelationship(objects.nextElement());
    }
  }


  public static concept.data.SWTag createSWTag(EOEditingContext editingContext) {
    concept.data.SWTag eo = (concept.data.SWTag) EOUtilities.createAndInsertInstance(editingContext, _SWTag.ENTITY_NAME);    
    return eo;
  }

  public static ERXFetchSpecification<concept.data.SWTag> fetchSpec() {
    return new ERXFetchSpecification<concept.data.SWTag>(_SWTag.ENTITY_NAME, null, null, false, true, null);
  }

  public static NSArray<concept.data.SWTag> fetchAllSWTags(EOEditingContext editingContext) {
    return _SWTag.fetchAllSWTags(editingContext, null);
  }

  public static NSArray<concept.data.SWTag> fetchAllSWTags(EOEditingContext editingContext, NSArray<EOSortOrdering> sortOrderings) {
    return _SWTag.fetchSWTags(editingContext, null, sortOrderings);
  }

  public static NSArray<concept.data.SWTag> fetchSWTags(EOEditingContext editingContext, EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings) {
    ERXFetchSpecification<concept.data.SWTag> fetchSpec = new ERXFetchSpecification<concept.data.SWTag>(_SWTag.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray<concept.data.SWTag> eoObjects = fetchSpec.fetchObjects(editingContext);
    return eoObjects;
  }

  public static concept.data.SWTag fetchSWTag(EOEditingContext editingContext, String keyName, Object value) {
    return _SWTag.fetchSWTag(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static concept.data.SWTag fetchSWTag(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray<concept.data.SWTag> eoObjects = _SWTag.fetchSWTags(editingContext, qualifier, null);
    concept.data.SWTag eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one SWTag that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static concept.data.SWTag fetchRequiredSWTag(EOEditingContext editingContext, String keyName, Object value) {
    return _SWTag.fetchRequiredSWTag(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static concept.data.SWTag fetchRequiredSWTag(EOEditingContext editingContext, EOQualifier qualifier) {
    concept.data.SWTag eoObject = _SWTag.fetchSWTag(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no SWTag that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static concept.data.SWTag localInstanceIn(EOEditingContext editingContext, concept.data.SWTag eo) {
    concept.data.SWTag localInstance = (eo == null) ? null : ERXEOControlUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
