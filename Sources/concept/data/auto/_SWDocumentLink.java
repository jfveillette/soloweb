// DO NOT EDIT.  Make changes to concept.data.SWDocumentLink.java instead.
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
public abstract class _SWDocumentLink extends  ERXGenericRecord {
  public static final String ENTITY_NAME = "SWDocumentLink";

  // Attribute Keys
  public static final ERXKey<Integer> DOCUMENT_ID = new ERXKey<Integer>("documentID");
  public static final ERXKey<Integer> PRIMARY_MARKER = new ERXKey<Integer>("primaryMarker");
  public static final ERXKey<String> TARGET_ENTITY_NAME = new ERXKey<String>("targetEntityName");
  public static final ERXKey<String> TARGET_ID = new ERXKey<String>("targetID");
  // Relationship Keys
  public static final ERXKey<concept.data.SWDocument> DOCUMENT = new ERXKey<concept.data.SWDocument>("document");

  // Attributes
  public static final String DOCUMENT_ID_KEY = DOCUMENT_ID.key();
  public static final String PRIMARY_MARKER_KEY = PRIMARY_MARKER.key();
  public static final String TARGET_ENTITY_NAME_KEY = TARGET_ENTITY_NAME.key();
  public static final String TARGET_ID_KEY = TARGET_ID.key();
  // Relationships
  public static final String DOCUMENT_KEY = DOCUMENT.key();

  private static Logger LOG = Logger.getLogger(_SWDocumentLink.class);

  public concept.data.SWDocumentLink localInstanceIn(EOEditingContext editingContext) {
    concept.data.SWDocumentLink localInstance = (concept.data.SWDocumentLink)EOUtilities.localInstanceOfObject(editingContext, this);
    if (localInstance == null) {
      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
    }
    return localInstance;
  }

  public Integer documentID() {
    return (Integer) storedValueForKey(_SWDocumentLink.DOCUMENT_ID_KEY);
  }

  public void setDocumentID(Integer value) {
    if (_SWDocumentLink.LOG.isDebugEnabled()) {
    	_SWDocumentLink.LOG.debug( "updating documentID from " + documentID() + " to " + value);
    }
    takeStoredValueForKey(value, _SWDocumentLink.DOCUMENT_ID_KEY);
  }

  public Integer primaryMarker() {
    return (Integer) storedValueForKey(_SWDocumentLink.PRIMARY_MARKER_KEY);
  }

  public void setPrimaryMarker(Integer value) {
    if (_SWDocumentLink.LOG.isDebugEnabled()) {
    	_SWDocumentLink.LOG.debug( "updating primaryMarker from " + primaryMarker() + " to " + value);
    }
    takeStoredValueForKey(value, _SWDocumentLink.PRIMARY_MARKER_KEY);
  }

  public String targetEntityName() {
    return (String) storedValueForKey(_SWDocumentLink.TARGET_ENTITY_NAME_KEY);
  }

  public void setTargetEntityName(String value) {
    if (_SWDocumentLink.LOG.isDebugEnabled()) {
    	_SWDocumentLink.LOG.debug( "updating targetEntityName from " + targetEntityName() + " to " + value);
    }
    takeStoredValueForKey(value, _SWDocumentLink.TARGET_ENTITY_NAME_KEY);
  }

  public String targetID() {
    return (String) storedValueForKey(_SWDocumentLink.TARGET_ID_KEY);
  }

  public void setTargetID(String value) {
    if (_SWDocumentLink.LOG.isDebugEnabled()) {
    	_SWDocumentLink.LOG.debug( "updating targetID from " + targetID() + " to " + value);
    }
    takeStoredValueForKey(value, _SWDocumentLink.TARGET_ID_KEY);
  }

  public concept.data.SWDocument document() {
    return (concept.data.SWDocument)storedValueForKey(_SWDocumentLink.DOCUMENT_KEY);
  }
  
  public void setDocument(concept.data.SWDocument value) {
    takeStoredValueForKey(value, _SWDocumentLink.DOCUMENT_KEY);
  }

  public void setDocumentRelationship(concept.data.SWDocument value) {
    if (_SWDocumentLink.LOG.isDebugEnabled()) {
      _SWDocumentLink.LOG.debug("updating document from " + document() + " to " + value);
    }
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
    	setDocument(value);
    }
    else if (value == null) {
    	concept.data.SWDocument oldValue = document();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, _SWDocumentLink.DOCUMENT_KEY);
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, _SWDocumentLink.DOCUMENT_KEY);
    }
  }
  

  public static concept.data.SWDocumentLink createSWDocumentLink(EOEditingContext editingContext) {
    concept.data.SWDocumentLink eo = (concept.data.SWDocumentLink) EOUtilities.createAndInsertInstance(editingContext, _SWDocumentLink.ENTITY_NAME);    
    return eo;
  }

  public static ERXFetchSpecification<concept.data.SWDocumentLink> fetchSpec() {
    return new ERXFetchSpecification<concept.data.SWDocumentLink>(_SWDocumentLink.ENTITY_NAME, null, null, false, true, null);
  }

  public static NSArray<concept.data.SWDocumentLink> fetchAllSWDocumentLinks(EOEditingContext editingContext) {
    return _SWDocumentLink.fetchAllSWDocumentLinks(editingContext, null);
  }

  public static NSArray<concept.data.SWDocumentLink> fetchAllSWDocumentLinks(EOEditingContext editingContext, NSArray<EOSortOrdering> sortOrderings) {
    return _SWDocumentLink.fetchSWDocumentLinks(editingContext, null, sortOrderings);
  }

  public static NSArray<concept.data.SWDocumentLink> fetchSWDocumentLinks(EOEditingContext editingContext, EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings) {
    ERXFetchSpecification<concept.data.SWDocumentLink> fetchSpec = new ERXFetchSpecification<concept.data.SWDocumentLink>(_SWDocumentLink.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray<concept.data.SWDocumentLink> eoObjects = fetchSpec.fetchObjects(editingContext);
    return eoObjects;
  }

  public static concept.data.SWDocumentLink fetchSWDocumentLink(EOEditingContext editingContext, String keyName, Object value) {
    return _SWDocumentLink.fetchSWDocumentLink(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static concept.data.SWDocumentLink fetchSWDocumentLink(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray<concept.data.SWDocumentLink> eoObjects = _SWDocumentLink.fetchSWDocumentLinks(editingContext, qualifier, null);
    concept.data.SWDocumentLink eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one SWDocumentLink that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static concept.data.SWDocumentLink fetchRequiredSWDocumentLink(EOEditingContext editingContext, String keyName, Object value) {
    return _SWDocumentLink.fetchRequiredSWDocumentLink(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static concept.data.SWDocumentLink fetchRequiredSWDocumentLink(EOEditingContext editingContext, EOQualifier qualifier) {
    concept.data.SWDocumentLink eoObject = _SWDocumentLink.fetchSWDocumentLink(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no SWDocumentLink that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static concept.data.SWDocumentLink localInstanceIn(EOEditingContext editingContext, concept.data.SWDocumentLink eo) {
    concept.data.SWDocumentLink localInstance = (eo == null) ? null : ERXEOControlUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
