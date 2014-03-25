package concept.data;

import is.rebbi.wo.interfaces.SWFolderInterface;
import is.rebbi.wo.interfaces.SWInheritsPrivileges;
import is.rebbi.wo.util.USHierarchyUtilities;

import java.util.Enumeration;

import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOEnterpriseObject;
import com.webobjects.eocontrol.EOFetchSpecification;
import com.webobjects.eocontrol.EOKeyValueQualifier;
import com.webobjects.eocontrol.EOQualifier;
import com.webobjects.eocontrol.EOSortOrdering;
import com.webobjects.foundation.NSArray;

import concept.data.auto._SWNewsCategory;

/**
 * An SWGroup represents a group of users with access to the SoloWeb system
 */

public class SWNewsCategory extends _SWNewsCategory implements SWFolderInterface<SWNewsCategory, SWNewsItem> {

	private static final NSArray<EOSortOrdering> DEFAULT_SORT_ORDERINGS = new NSArray<>( new EOSortOrdering( "name", EOSortOrdering.CompareAscending ) );
	private static final NSArray<EOSortOrdering> NEWS_DEFAULT_SORT_ORDERINGS = new NSArray<>( new EOSortOrdering( "date", EOSortOrdering.CompareDescending ) );
	private static final EOQualifier ROOT_FOLDER_QUALIFIER = new EOKeyValueQualifier( "parentCategoryID", EOQualifier.QualifierOperatorEqual, null );
	private static final EOFetchSpecification ROOT_FOLDER_FS = new EOFetchSpecification( "SWNewsCategory", ROOT_FOLDER_QUALIFIER, DEFAULT_SORT_ORDERINGS );

	/**
	 * Returns all news in this category, sorted by date
	 */
	public NSArray<SWNewsItem> sortedNews() {
		return EOSortOrdering.sortedArrayUsingKeyOrderArray( news(), NEWS_DEFAULT_SORT_ORDERINGS );
	}

	/**
	 * Implementation of SWTransferable. Transfers this category to a new parent folder
	 */
	@Override
	public void transferOwnership( EOEnterpriseObject newOwner ) {
		this.removeObjectFromBothSidesOfRelationshipWithKey( parentFolder(), "parentFolder" );
		this.addObjectToBothSidesOfRelationshipWithKey( newOwner, "parentFolder" );
	}

	/**
	 * Creates a new folder with the given name and parent folder
	 */
	public static SWNewsCategory newFolderWithNameAndParentFolder( EOEditingContext ec, String aName, SWNewsCategory newParent ) {

		SWNewsCategory c = new SWNewsCategory();
		ec.insertObject( c );
		c.setName( aName );
		c.setInheritsPrivileges( 1 );

		if( newParent != null ) {
			c.addObjectToBothSidesOfRelationshipWithKey( newParent, "parentFolder" );
		}

		return c;
	}

	/**
	 * Creates a new newsitem in this folder with the given name
	 */
	public SWNewsItem createNewsItemWithHeading( String aName ) {

		SWNewsItem n = new SWNewsItem();

		editingContext().insertObject( n );
		n.setHeading( aName );
		n.addObjectToBothSidesOfRelationshipWithKey( this, "category" );

		return n;
	}

	/**
	 * Implementation of SWHierarchy - returns the parent folder
	 */
	@Override
	public SWNewsCategory parent() {
		return parentFolder();
	}

	/**
	 * Implementation of SWHierarchy - returns the subfolders
	 */
	@Override
	public NSArray<SWNewsCategory> children() {
		return subFolders();
	}

	/**
	 * Returns all subfolders sorted
	 */
	@Override
	public NSArray<SWNewsCategory> sortedSubFolders() {
		return EOSortOrdering.sortedArrayUsingKeyOrderArray( children(), DEFAULT_SORT_ORDERINGS );
	}

	/**
	 * Returns all root folders sorted by name
	 */
	@Override
	public NSArray<SWNewsCategory> sortedRootFolders( EOEditingContext ec ) {
		return ec.objectsWithFetchSpecification( ROOT_FOLDER_FS );
	}

	/**
	 * Attempt to construct a legible name, based on the hierarchy
	 */
	public String nameIncludingHierarchy() {
		NSArray a = USHierarchyUtilities.everyParentNodeReversed( this, true );
		StringBuffer b = new StringBuffer();

		Enumeration e = a.objectEnumerator();

		while( e.hasMoreElements() ) {
			SWNewsCategory nextElement = (SWNewsCategory)e.nextElement();
			b.append( " - " );
			b.append( nextElement.name() );
		}

		return b.toString();
	}

	/**
	 * Returns a subfolder with the specified name
	 */
	@Override
	public SWNewsCategory subFolderNamed( String folderName ) {

		if( folderName == null ) {
			return null;
		}

		Enumeration<SWNewsCategory> e = subFolders().objectEnumerator();

		while( e.hasMoreElements() ) {
			SWNewsCategory currentFolder = e.nextElement();

			if( folderName.equals( currentFolder.name() ) ) {
				return currentFolder;
			}
		}

		return null;
	}

	/**
	 * Returns the named newsitem from the folder
	 */
	public SWNewsItem itemNamed( String aName ) {

		if( aName == null ) {
			return null;
		}

		Enumeration e = this.news().objectEnumerator();

		while( e.hasMoreElements() ) {
			SWNewsItem nextItem = (SWNewsItem)e.nextElement();

			if( aName.equals( nextItem.heading() ) ) {
				return nextItem;
			}
		}

		return null;
	}

	/**
	 * Implementation of SWInheritsPrivileges - returns the parent folder
	 */
	@Override
	public SWInheritsPrivileges inheritsPrivilegesFrom() {
		return parentFolder();
	}

	@Override
	public NSArray<SWNewsItem> sortedDocuments() {
		return sortedNews();
	}

	@Override
	public Integer folderID() {
		return id();
	}

	@Override
	public boolean batchFetchContent() {
		return true;
	}

	@Override
	public boolean showThumbnails() {
		return false;
	}

	@Override
	public String title() {
		return "Fr&eacute;ttir";
	}

	@Override
	public String key() {
		return "frettir";
	}

	@Override
	public void setParent( SWNewsCategory parentFolder ) {
		setParentFolder( parentFolder );
	}

	@Override
	public void addItem( SWNewsItem item ) {
		addToNews( item );
	}
}