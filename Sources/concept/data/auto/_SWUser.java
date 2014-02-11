// DO NOT EDIT.  Make changes to concept.data.SWUser.java instead.
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
public abstract class _SWUser extends  ERXGenericRecord {
  public static final String ENTITY_NAME = "SWUser";

  // Attribute Keys
  public static final ERXKey<String> ADDRESS = new ERXKey<String>("address");
  public static final ERXKey<Integer> ADMINISTRATOR = new ERXKey<Integer>("administrator");
  public static final ERXKey<String> CITY = new ERXKey<String>("city");
  public static final ERXKey<String> COMMENT = new ERXKey<String>("comment");
  public static final ERXKey<String> COUNTRY = new ERXKey<String>("country");
  public static final ERXKey<NSTimestamp> CREATION_DATE = new ERXKey<NSTimestamp>("creationDate");
  public static final ERXKey<Integer> CRM_NAFN_ID = new ERXKey<Integer>("crmNafnId");
  public static final ERXKey<Integer> DEFAULT_SITE_ID = new ERXKey<Integer>("defaultSiteID");
  public static final ERXKey<String> EMAIL_ADDRESS = new ERXKey<String>("emailAddress");
  public static final ERXKey<Integer> ID = new ERXKey<Integer>("id");
  public static final ERXKey<String> LAST_LOGIN_AGENT = new ERXKey<String>("lastLoginAgent");
  public static final ERXKey<NSTimestamp> LAST_LOGIN_DATE = new ERXKey<NSTimestamp>("lastLoginDate");
  public static final ERXKey<String> LAST_LOGIN_IP = new ERXKey<String>("lastLoginIP");
  public static final ERXKey<NSTimestamp> MODIFICATION_DATE = new ERXKey<NSTimestamp>("modificationDate");
  public static final ERXKey<String> NAME = new ERXKey<String>("name");
  public static final ERXKey<String> PASSWORD = new ERXKey<String>("password");
  public static final ERXKey<Integer> PICTURE_ID = new ERXKey<Integer>("pictureID");
  public static final ERXKey<String> SSN = new ERXKey<String>("ssn");
  public static final ERXKey<String> TELEPHONE = new ERXKey<String>("telephone");
  public static final ERXKey<String> USERNAME = new ERXKey<String>("username");
  public static final ERXKey<String> UUID = new ERXKey<String>("uuid");
  public static final ERXKey<Integer> ZIP = new ERXKey<Integer>("zip");
  // Relationship Keys
  public static final ERXKey<concept.data.SWAccessPrivilege> ACCESS_PRIVILEGES = new ERXKey<concept.data.SWAccessPrivilege>("accessPrivileges");
  public static final ERXKey<concept.data.SWSite> DEFAULT_SITE = new ERXKey<concept.data.SWSite>("defaultSite");
  public static final ERXKey<concept.data.SWGroup> GROUPS = new ERXKey<concept.data.SWGroup>("groups");
  public static final ERXKey<concept.data.SWPicture> PICTURE = new ERXKey<concept.data.SWPicture>("picture");
  public static final ERXKey<EOGenericRecord> S_W_GROUP_SW_USERS = new ERXKey<EOGenericRecord>("sWGroupSWUsers");

  // Attributes
  public static final String ADDRESS_KEY = ADDRESS.key();
  public static final String ADMINISTRATOR_KEY = ADMINISTRATOR.key();
  public static final String CITY_KEY = CITY.key();
  public static final String COMMENT_KEY = COMMENT.key();
  public static final String COUNTRY_KEY = COUNTRY.key();
  public static final String CREATION_DATE_KEY = CREATION_DATE.key();
  public static final String CRM_NAFN_ID_KEY = CRM_NAFN_ID.key();
  public static final String DEFAULT_SITE_ID_KEY = DEFAULT_SITE_ID.key();
  public static final String EMAIL_ADDRESS_KEY = EMAIL_ADDRESS.key();
  public static final String ID_KEY = ID.key();
  public static final String LAST_LOGIN_AGENT_KEY = LAST_LOGIN_AGENT.key();
  public static final String LAST_LOGIN_DATE_KEY = LAST_LOGIN_DATE.key();
  public static final String LAST_LOGIN_IP_KEY = LAST_LOGIN_IP.key();
  public static final String MODIFICATION_DATE_KEY = MODIFICATION_DATE.key();
  public static final String NAME_KEY = NAME.key();
  public static final String PASSWORD_KEY = PASSWORD.key();
  public static final String PICTURE_ID_KEY = PICTURE_ID.key();
  public static final String SSN_KEY = SSN.key();
  public static final String TELEPHONE_KEY = TELEPHONE.key();
  public static final String USERNAME_KEY = USERNAME.key();
  public static final String UUID_KEY = UUID.key();
  public static final String ZIP_KEY = ZIP.key();
  // Relationships
  public static final String ACCESS_PRIVILEGES_KEY = ACCESS_PRIVILEGES.key();
  public static final String DEFAULT_SITE_KEY = DEFAULT_SITE.key();
  public static final String GROUPS_KEY = GROUPS.key();
  public static final String PICTURE_KEY = PICTURE.key();
  public static final String S_W_GROUP_SW_USERS_KEY = S_W_GROUP_SW_USERS.key();

  private static Logger LOG = Logger.getLogger(_SWUser.class);

  public concept.data.SWUser localInstanceIn(EOEditingContext editingContext) {
    concept.data.SWUser localInstance = (concept.data.SWUser)EOUtilities.localInstanceOfObject(editingContext, this);
    if (localInstance == null) {
      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
    }
    return localInstance;
  }

  public String address() {
    return (String) storedValueForKey(_SWUser.ADDRESS_KEY);
  }

  public void setAddress(String value) {
    if (_SWUser.LOG.isDebugEnabled()) {
    	_SWUser.LOG.debug( "updating address from " + address() + " to " + value);
    }
    takeStoredValueForKey(value, _SWUser.ADDRESS_KEY);
  }

  public Integer administrator() {
    return (Integer) storedValueForKey(_SWUser.ADMINISTRATOR_KEY);
  }

  public void setAdministrator(Integer value) {
    if (_SWUser.LOG.isDebugEnabled()) {
    	_SWUser.LOG.debug( "updating administrator from " + administrator() + " to " + value);
    }
    takeStoredValueForKey(value, _SWUser.ADMINISTRATOR_KEY);
  }

  public String city() {
    return (String) storedValueForKey(_SWUser.CITY_KEY);
  }

  public void setCity(String value) {
    if (_SWUser.LOG.isDebugEnabled()) {
    	_SWUser.LOG.debug( "updating city from " + city() + " to " + value);
    }
    takeStoredValueForKey(value, _SWUser.CITY_KEY);
  }

  public String comment() {
    return (String) storedValueForKey(_SWUser.COMMENT_KEY);
  }

  public void setComment(String value) {
    if (_SWUser.LOG.isDebugEnabled()) {
    	_SWUser.LOG.debug( "updating comment from " + comment() + " to " + value);
    }
    takeStoredValueForKey(value, _SWUser.COMMENT_KEY);
  }

  public String country() {
    return (String) storedValueForKey(_SWUser.COUNTRY_KEY);
  }

  public void setCountry(String value) {
    if (_SWUser.LOG.isDebugEnabled()) {
    	_SWUser.LOG.debug( "updating country from " + country() + " to " + value);
    }
    takeStoredValueForKey(value, _SWUser.COUNTRY_KEY);
  }

  public NSTimestamp creationDate() {
    return (NSTimestamp) storedValueForKey(_SWUser.CREATION_DATE_KEY);
  }

  public void setCreationDate(NSTimestamp value) {
    if (_SWUser.LOG.isDebugEnabled()) {
    	_SWUser.LOG.debug( "updating creationDate from " + creationDate() + " to " + value);
    }
    takeStoredValueForKey(value, _SWUser.CREATION_DATE_KEY);
  }

  public Integer crmNafnId() {
    return (Integer) storedValueForKey(_SWUser.CRM_NAFN_ID_KEY);
  }

  public void setCrmNafnId(Integer value) {
    if (_SWUser.LOG.isDebugEnabled()) {
    	_SWUser.LOG.debug( "updating crmNafnId from " + crmNafnId() + " to " + value);
    }
    takeStoredValueForKey(value, _SWUser.CRM_NAFN_ID_KEY);
  }

  public Integer defaultSiteID() {
    return (Integer) storedValueForKey(_SWUser.DEFAULT_SITE_ID_KEY);
  }

  public void setDefaultSiteID(Integer value) {
    if (_SWUser.LOG.isDebugEnabled()) {
    	_SWUser.LOG.debug( "updating defaultSiteID from " + defaultSiteID() + " to " + value);
    }
    takeStoredValueForKey(value, _SWUser.DEFAULT_SITE_ID_KEY);
  }

  public String emailAddress() {
    return (String) storedValueForKey(_SWUser.EMAIL_ADDRESS_KEY);
  }

  public void setEmailAddress(String value) {
    if (_SWUser.LOG.isDebugEnabled()) {
    	_SWUser.LOG.debug( "updating emailAddress from " + emailAddress() + " to " + value);
    }
    takeStoredValueForKey(value, _SWUser.EMAIL_ADDRESS_KEY);
  }

  public Integer id() {
    return (Integer) storedValueForKey(_SWUser.ID_KEY);
  }

  public void setId(Integer value) {
    if (_SWUser.LOG.isDebugEnabled()) {
    	_SWUser.LOG.debug( "updating id from " + id() + " to " + value);
    }
    takeStoredValueForKey(value, _SWUser.ID_KEY);
  }

  public String lastLoginAgent() {
    return (String) storedValueForKey(_SWUser.LAST_LOGIN_AGENT_KEY);
  }

  public void setLastLoginAgent(String value) {
    if (_SWUser.LOG.isDebugEnabled()) {
    	_SWUser.LOG.debug( "updating lastLoginAgent from " + lastLoginAgent() + " to " + value);
    }
    takeStoredValueForKey(value, _SWUser.LAST_LOGIN_AGENT_KEY);
  }

  public NSTimestamp lastLoginDate() {
    return (NSTimestamp) storedValueForKey(_SWUser.LAST_LOGIN_DATE_KEY);
  }

  public void setLastLoginDate(NSTimestamp value) {
    if (_SWUser.LOG.isDebugEnabled()) {
    	_SWUser.LOG.debug( "updating lastLoginDate from " + lastLoginDate() + " to " + value);
    }
    takeStoredValueForKey(value, _SWUser.LAST_LOGIN_DATE_KEY);
  }

  public String lastLoginIP() {
    return (String) storedValueForKey(_SWUser.LAST_LOGIN_IP_KEY);
  }

  public void setLastLoginIP(String value) {
    if (_SWUser.LOG.isDebugEnabled()) {
    	_SWUser.LOG.debug( "updating lastLoginIP from " + lastLoginIP() + " to " + value);
    }
    takeStoredValueForKey(value, _SWUser.LAST_LOGIN_IP_KEY);
  }

  public NSTimestamp modificationDate() {
    return (NSTimestamp) storedValueForKey(_SWUser.MODIFICATION_DATE_KEY);
  }

  public void setModificationDate(NSTimestamp value) {
    if (_SWUser.LOG.isDebugEnabled()) {
    	_SWUser.LOG.debug( "updating modificationDate from " + modificationDate() + " to " + value);
    }
    takeStoredValueForKey(value, _SWUser.MODIFICATION_DATE_KEY);
  }

  public String name() {
    return (String) storedValueForKey(_SWUser.NAME_KEY);
  }

  public void setName(String value) {
    if (_SWUser.LOG.isDebugEnabled()) {
    	_SWUser.LOG.debug( "updating name from " + name() + " to " + value);
    }
    takeStoredValueForKey(value, _SWUser.NAME_KEY);
  }

  public String password() {
    return (String) storedValueForKey(_SWUser.PASSWORD_KEY);
  }

  public void setPassword(String value) {
    if (_SWUser.LOG.isDebugEnabled()) {
    	_SWUser.LOG.debug( "updating password from " + password() + " to " + value);
    }
    takeStoredValueForKey(value, _SWUser.PASSWORD_KEY);
  }

  public Integer pictureID() {
    return (Integer) storedValueForKey(_SWUser.PICTURE_ID_KEY);
  }

  public void setPictureID(Integer value) {
    if (_SWUser.LOG.isDebugEnabled()) {
    	_SWUser.LOG.debug( "updating pictureID from " + pictureID() + " to " + value);
    }
    takeStoredValueForKey(value, _SWUser.PICTURE_ID_KEY);
  }

  public String ssn() {
    return (String) storedValueForKey(_SWUser.SSN_KEY);
  }

  public void setSsn(String value) {
    if (_SWUser.LOG.isDebugEnabled()) {
    	_SWUser.LOG.debug( "updating ssn from " + ssn() + " to " + value);
    }
    takeStoredValueForKey(value, _SWUser.SSN_KEY);
  }

  public String telephone() {
    return (String) storedValueForKey(_SWUser.TELEPHONE_KEY);
  }

  public void setTelephone(String value) {
    if (_SWUser.LOG.isDebugEnabled()) {
    	_SWUser.LOG.debug( "updating telephone from " + telephone() + " to " + value);
    }
    takeStoredValueForKey(value, _SWUser.TELEPHONE_KEY);
  }

  public String username() {
    return (String) storedValueForKey(_SWUser.USERNAME_KEY);
  }

  public void setUsername(String value) {
    if (_SWUser.LOG.isDebugEnabled()) {
    	_SWUser.LOG.debug( "updating username from " + username() + " to " + value);
    }
    takeStoredValueForKey(value, _SWUser.USERNAME_KEY);
  }

  public String uuid() {
    return (String) storedValueForKey(_SWUser.UUID_KEY);
  }

  public void setUuid(String value) {
    if (_SWUser.LOG.isDebugEnabled()) {
    	_SWUser.LOG.debug( "updating uuid from " + uuid() + " to " + value);
    }
    takeStoredValueForKey(value, _SWUser.UUID_KEY);
  }

  public Integer zip() {
    return (Integer) storedValueForKey(_SWUser.ZIP_KEY);
  }

  public void setZip(Integer value) {
    if (_SWUser.LOG.isDebugEnabled()) {
    	_SWUser.LOG.debug( "updating zip from " + zip() + " to " + value);
    }
    takeStoredValueForKey(value, _SWUser.ZIP_KEY);
  }

  public concept.data.SWSite defaultSite() {
    return (concept.data.SWSite)storedValueForKey(_SWUser.DEFAULT_SITE_KEY);
  }
  
  public void setDefaultSite(concept.data.SWSite value) {
    takeStoredValueForKey(value, _SWUser.DEFAULT_SITE_KEY);
  }

  public void setDefaultSiteRelationship(concept.data.SWSite value) {
    if (_SWUser.LOG.isDebugEnabled()) {
      _SWUser.LOG.debug("updating defaultSite from " + defaultSite() + " to " + value);
    }
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
    	setDefaultSite(value);
    }
    else if (value == null) {
    	concept.data.SWSite oldValue = defaultSite();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, _SWUser.DEFAULT_SITE_KEY);
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, _SWUser.DEFAULT_SITE_KEY);
    }
  }
  
  public concept.data.SWPicture picture() {
    return (concept.data.SWPicture)storedValueForKey(_SWUser.PICTURE_KEY);
  }
  
  public void setPicture(concept.data.SWPicture value) {
    takeStoredValueForKey(value, _SWUser.PICTURE_KEY);
  }

  public void setPictureRelationship(concept.data.SWPicture value) {
    if (_SWUser.LOG.isDebugEnabled()) {
      _SWUser.LOG.debug("updating picture from " + picture() + " to " + value);
    }
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
    	setPicture(value);
    }
    else if (value == null) {
    	concept.data.SWPicture oldValue = picture();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, _SWUser.PICTURE_KEY);
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, _SWUser.PICTURE_KEY);
    }
  }
  
  public NSArray<concept.data.SWAccessPrivilege> accessPrivileges() {
    return (NSArray<concept.data.SWAccessPrivilege>)storedValueForKey(_SWUser.ACCESS_PRIVILEGES_KEY);
  }

  public NSArray<concept.data.SWAccessPrivilege> accessPrivileges(EOQualifier qualifier) {
    return accessPrivileges(qualifier, null, false);
  }

  public NSArray<concept.data.SWAccessPrivilege> accessPrivileges(EOQualifier qualifier, boolean fetch) {
    return accessPrivileges(qualifier, null, fetch);
  }

  public NSArray<concept.data.SWAccessPrivilege> accessPrivileges(EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings, boolean fetch) {
    NSArray<concept.data.SWAccessPrivilege> results;
    if (fetch) {
      EOQualifier fullQualifier;
      EOQualifier inverseQualifier = new EOKeyValueQualifier(concept.data.SWAccessPrivilege.USER_KEY, EOQualifier.QualifierOperatorEqual, this);
    	
      if (qualifier == null) {
        fullQualifier = inverseQualifier;
      }
      else {
        NSMutableArray<EOQualifier> qualifiers = new NSMutableArray<EOQualifier>();
        qualifiers.addObject(qualifier);
        qualifiers.addObject(inverseQualifier);
        fullQualifier = new EOAndQualifier(qualifiers);
      }

      results = concept.data.SWAccessPrivilege.fetchSWAccessPrivileges(editingContext(), fullQualifier, sortOrderings);
    }
    else {
      results = accessPrivileges();
      if (qualifier != null) {
        results = (NSArray<concept.data.SWAccessPrivilege>)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray<concept.data.SWAccessPrivilege>)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    }
    return results;
  }
  
  public void addToAccessPrivileges(concept.data.SWAccessPrivilege object) {
    includeObjectIntoPropertyWithKey(object, _SWUser.ACCESS_PRIVILEGES_KEY);
  }

  public void removeFromAccessPrivileges(concept.data.SWAccessPrivilege object) {
    excludeObjectFromPropertyWithKey(object, _SWUser.ACCESS_PRIVILEGES_KEY);
  }

  public void addToAccessPrivilegesRelationship(concept.data.SWAccessPrivilege object) {
    if (_SWUser.LOG.isDebugEnabled()) {
      _SWUser.LOG.debug("adding " + object + " to accessPrivileges relationship");
    }
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
    	addToAccessPrivileges(object);
    }
    else {
    	addObjectToBothSidesOfRelationshipWithKey(object, _SWUser.ACCESS_PRIVILEGES_KEY);
    }
  }

  public void removeFromAccessPrivilegesRelationship(concept.data.SWAccessPrivilege object) {
    if (_SWUser.LOG.isDebugEnabled()) {
      _SWUser.LOG.debug("removing " + object + " from accessPrivileges relationship");
    }
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
    	removeFromAccessPrivileges(object);
    }
    else {
    	removeObjectFromBothSidesOfRelationshipWithKey(object, _SWUser.ACCESS_PRIVILEGES_KEY);
    }
  }

  public concept.data.SWAccessPrivilege createAccessPrivilegesRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName( concept.data.SWAccessPrivilege.ENTITY_NAME );
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, _SWUser.ACCESS_PRIVILEGES_KEY);
    return (concept.data.SWAccessPrivilege) eo;
  }

  public void deleteAccessPrivilegesRelationship(concept.data.SWAccessPrivilege object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, _SWUser.ACCESS_PRIVILEGES_KEY);
    editingContext().deleteObject(object);
  }

  public void deleteAllAccessPrivilegesRelationships() {
    Enumeration<concept.data.SWAccessPrivilege> objects = accessPrivileges().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteAccessPrivilegesRelationship(objects.nextElement());
    }
  }

  public NSArray<concept.data.SWGroup> groups() {
    return (NSArray<concept.data.SWGroup>)storedValueForKey(_SWUser.GROUPS_KEY);
  }

  public NSArray<concept.data.SWGroup> groups(EOQualifier qualifier) {
    return groups(qualifier, null);
  }

  public NSArray<concept.data.SWGroup> groups(EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings) {
    NSArray<concept.data.SWGroup> results;
      results = groups();
      if (qualifier != null) {
        results = (NSArray<concept.data.SWGroup>)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray<concept.data.SWGroup>)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    return results;
  }
  
  public void addToGroups(concept.data.SWGroup object) {
    includeObjectIntoPropertyWithKey(object, _SWUser.GROUPS_KEY);
  }

  public void removeFromGroups(concept.data.SWGroup object) {
    excludeObjectFromPropertyWithKey(object, _SWUser.GROUPS_KEY);
  }

  public void addToGroupsRelationship(concept.data.SWGroup object) {
    if (_SWUser.LOG.isDebugEnabled()) {
      _SWUser.LOG.debug("adding " + object + " to groups relationship");
    }
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
    	addToGroups(object);
    }
    else {
    	addObjectToBothSidesOfRelationshipWithKey(object, _SWUser.GROUPS_KEY);
    }
  }

  public void removeFromGroupsRelationship(concept.data.SWGroup object) {
    if (_SWUser.LOG.isDebugEnabled()) {
      _SWUser.LOG.debug("removing " + object + " from groups relationship");
    }
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
    	removeFromGroups(object);
    }
    else {
    	removeObjectFromBothSidesOfRelationshipWithKey(object, _SWUser.GROUPS_KEY);
    }
  }

  public concept.data.SWGroup createGroupsRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName( concept.data.SWGroup.ENTITY_NAME );
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, _SWUser.GROUPS_KEY);
    return (concept.data.SWGroup) eo;
  }

  public void deleteGroupsRelationship(concept.data.SWGroup object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, _SWUser.GROUPS_KEY);
    editingContext().deleteObject(object);
  }

  public void deleteAllGroupsRelationships() {
    Enumeration<concept.data.SWGroup> objects = groups().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteGroupsRelationship(objects.nextElement());
    }
  }

  public NSArray<EOGenericRecord> sWGroupSWUsers() {
    return (NSArray<EOGenericRecord>)storedValueForKey(_SWUser.S_W_GROUP_SW_USERS_KEY);
  }

  public NSArray<EOGenericRecord> sWGroupSWUsers(EOQualifier qualifier) {
    return sWGroupSWUsers(qualifier, null);
  }

  public NSArray<EOGenericRecord> sWGroupSWUsers(EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings) {
    NSArray<EOGenericRecord> results;
      results = sWGroupSWUsers();
      if (qualifier != null) {
        results = (NSArray<EOGenericRecord>)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray<EOGenericRecord>)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    return results;
  }
  
  public void addToSWGroupSWUsers(EOGenericRecord object) {
    includeObjectIntoPropertyWithKey(object, _SWUser.S_W_GROUP_SW_USERS_KEY);
  }

  public void removeFromSWGroupSWUsers(EOGenericRecord object) {
    excludeObjectFromPropertyWithKey(object, _SWUser.S_W_GROUP_SW_USERS_KEY);
  }

  public void addToSWGroupSWUsersRelationship(EOGenericRecord object) {
    if (_SWUser.LOG.isDebugEnabled()) {
      _SWUser.LOG.debug("adding " + object + " to sWGroupSWUsers relationship");
    }
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
    	addToSWGroupSWUsers(object);
    }
    else {
    	addObjectToBothSidesOfRelationshipWithKey(object, _SWUser.S_W_GROUP_SW_USERS_KEY);
    }
  }

  public void removeFromSWGroupSWUsersRelationship(EOGenericRecord object) {
    if (_SWUser.LOG.isDebugEnabled()) {
      _SWUser.LOG.debug("removing " + object + " from sWGroupSWUsers relationship");
    }
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
    	removeFromSWGroupSWUsers(object);
    }
    else {
    	removeObjectFromBothSidesOfRelationshipWithKey(object, _SWUser.S_W_GROUP_SW_USERS_KEY);
    }
  }

  public EOGenericRecord createSWGroupSWUsersRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("SWGroupUser");
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, _SWUser.S_W_GROUP_SW_USERS_KEY);
    return (EOGenericRecord) eo;
  }

  public void deleteSWGroupSWUsersRelationship(EOGenericRecord object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, _SWUser.S_W_GROUP_SW_USERS_KEY);
    editingContext().deleteObject(object);
  }

  public void deleteAllSWGroupSWUsersRelationships() {
    Enumeration<EOGenericRecord> objects = sWGroupSWUsers().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteSWGroupSWUsersRelationship(objects.nextElement());
    }
  }


  public static concept.data.SWUser createSWUser(EOEditingContext editingContext, Integer id
) {
    concept.data.SWUser eo = (concept.data.SWUser) EOUtilities.createAndInsertInstance(editingContext, _SWUser.ENTITY_NAME);    
		eo.setId(id);
    return eo;
  }

  public static ERXFetchSpecification<concept.data.SWUser> fetchSpec() {
    return new ERXFetchSpecification<concept.data.SWUser>(_SWUser.ENTITY_NAME, null, null, false, true, null);
  }

  public static NSArray<concept.data.SWUser> fetchAllSWUsers(EOEditingContext editingContext) {
    return _SWUser.fetchAllSWUsers(editingContext, null);
  }

  public static NSArray<concept.data.SWUser> fetchAllSWUsers(EOEditingContext editingContext, NSArray<EOSortOrdering> sortOrderings) {
    return _SWUser.fetchSWUsers(editingContext, null, sortOrderings);
  }

  public static NSArray<concept.data.SWUser> fetchSWUsers(EOEditingContext editingContext, EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings) {
    ERXFetchSpecification<concept.data.SWUser> fetchSpec = new ERXFetchSpecification<concept.data.SWUser>(_SWUser.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray<concept.data.SWUser> eoObjects = fetchSpec.fetchObjects(editingContext);
    return eoObjects;
  }

  public static concept.data.SWUser fetchSWUser(EOEditingContext editingContext, String keyName, Object value) {
    return _SWUser.fetchSWUser(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static concept.data.SWUser fetchSWUser(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray<concept.data.SWUser> eoObjects = _SWUser.fetchSWUsers(editingContext, qualifier, null);
    concept.data.SWUser eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one SWUser that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static concept.data.SWUser fetchRequiredSWUser(EOEditingContext editingContext, String keyName, Object value) {
    return _SWUser.fetchRequiredSWUser(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static concept.data.SWUser fetchRequiredSWUser(EOEditingContext editingContext, EOQualifier qualifier) {
    concept.data.SWUser eoObject = _SWUser.fetchSWUser(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no SWUser that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static concept.data.SWUser localInstanceIn(EOEditingContext editingContext, concept.data.SWUser eo) {
    concept.data.SWUser localInstance = (eo == null) ? null : ERXEOControlUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
