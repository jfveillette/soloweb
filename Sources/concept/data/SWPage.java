package concept.data;

import is.rebbi.core.util.StringUtilities;
import is.rebbi.wo.interfaces.HumanReadable;
import is.rebbi.wo.interfaces.SWCopyable;
import is.rebbi.wo.interfaces.SWHasCustomInfo;
import is.rebbi.wo.interfaces.SWInheritsPrivileges;
import is.rebbi.wo.interfaces.SWTimedContent;
import is.rebbi.wo.util.SWCustomInfo;
import is.rebbi.wo.util.SWTimedContentUtilities;
import is.rebbi.wo.util.USArrayUtilities;
import is.rebbi.wo.util.USHierarchy;
import is.rebbi.wo.util.USHierarchyUtilities;
import is.rebbi.wo.util.USSortable;
import is.rebbi.wo.util.USSortableUtilities;
import is.rebbi.wo.util.USUtilities;

import java.util.Enumeration;
import java.util.Locale;

import com.webobjects.eocontrol.EOEnterpriseObject;
import com.webobjects.eocontrol.EOQualifier;
import com.webobjects.eocontrol.EOSortOrdering;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSMutableDictionary;

import concept.components.client.SWComponentDetail;
import concept.data.auto._SWPage;

public class SWPage extends _SWPage implements USHierarchy<SWPage>, SWTimedContent, SWInheritsPrivileges, USSortable, SWHasCustomInfo, HumanReadable, SWCopyable<SWPage> {

	private SWCustomInfo _customInfo;

	private static final String DEFAULT_COMPONENT_TEMPLATE_NAME = SWComponentDetail.class.getSimpleName();

	/**
	 * Registered SWPage.Operators in the system.
	 */
	private static NSMutableDictionary<String, SWPageOperator> _operators = new NSMutableDictionary<>();

	private static final NSArray<EOSortOrdering> DEFAULT_SORT_ORDERINGS = SORT_NUMBER.ascs();
	private static final EOQualifier PUBLISHED_QUALIFIER = PUBLISHED.eq( 1 );

	/**
	 * Deletes this page.
	 */
	public void deletePage() {
		if( parent() != null ) {
			parent().removeSubPage( this );
		}

		editingContext().deleteObject( this );
	}

	/**
	 * @return True if this page has subpages.
	 */
	public boolean hasChildren() {
		return USHierarchyUtilities.hasChildren( this );
	}

	/**
	 * @return True if this page has no subpages.
	 */
	public boolean hasNoChildren() {
		return !hasChildren();
	}

	/**
	 * tells us if this page has any approved subpages.
	 */
	public boolean hasApprovedSubPages() {
		return USArrayUtilities.hasObjects( sortedAndApprovedSubPages() );
	}

	/**
	 * Tells us if this page is the front page of the site
	 */
	public boolean isTopLevel() {
		return isRoot();
	}

	/**
	 * Tells us if this page is the front page of the site
	 */
	public boolean isRoot() {
		return USHierarchyUtilities.isRoot( this );
	}

	/**
	 * All subpages, sorted by their sortNumber.
	 */
	public NSArray<SWPage> sortedSubPages() {
		return EOSortOrdering.sortedArrayUsingKeyOrderArray( children(), DEFAULT_SORT_ORDERINGS );
	}

	/**
	 * All approved subpages, sorted by their sortingnumber.
	 */

	public NSArray<SWPage> sortedAndApprovedSubPages() {
		NSArray<SWPage> anArray = EOQualifier.filteredArrayWithQualifier( sortedSubPages(), PUBLISHED_QUALIFIER );
		return SWTimedContentUtilities.validateDisplayTimeForArray( anArray );
	}

	/**
	 * pages at the same level as this one in the site tree.
	 */
	public NSArray<SWPage> siblings() {
		return USHierarchyUtilities.siblings( this );
	}

	/**
	 * Pages at the same level as this one in the site tree, sorted
	 */
	public NSArray<SWPage> sortedSiblings() {
		return EOSortOrdering.sortedArrayUsingKeyOrderArray( siblings(), DEFAULT_SORT_ORDERINGS );
	}

	/**
	 * A convenience method to insert a new subpage at the specified index.
	 * Takes care of resorting all pages with sortnumber above the specified
	 * index.
	 *
	 * @param aPAge an SWPage object to insert
	 * @param index sortingnumber for the new page
	 */
	public void insertSubPageAtIndex( SWPage aPage, int index ) {

		if( hasChildren() ) {
			NSArray<SWPage> pages = sortedSubPages();
			Enumeration<SWPage> e = pages.objectEnumerator();

			while( e.hasMoreElements() ) {
				SWPage p = e.nextElement();
				int h = pages.indexOfObject( p );

				if( h >= index ) {
					p.setSortNumber( new Integer( h + 1 ) );
				}
			}
		}

		aPage.setSortNumber( new Integer( index ) );
		addObjectToBothSidesOfRelationshipWithKey( aPage, CHILDREN_KEY );
	}

	/**
	 * A convenience method to remove the specified subpage. Takes care of
	 * resorting all pages with sortnumbers above the specified index.
	 *
	 * @param page an SWPage object to remove from subPages
	 */
	public void removeSubPage( SWPage page ) {

		NSArray<SWPage> pages = sortedSubPages();
		int index = pages.indexOfObject( page );
		Enumeration<SWPage> e = pages.objectEnumerator();

		while( e.hasMoreElements() ) {
			SWPage p = e.nextElement();
			int h = pages.indexOfObject( p );

			if( h >= index ) {
				p.setSortNumber( new Integer( h - 1 ) );
			}
		}

		removeObjectFromBothSidesOfRelationshipWithKey( page, CHILDREN_KEY );
	}

	/**
	 * Used to shift the page's sort ordering (only done by "1" or "-1" in the
	 * current version
	 */
	public void changeSortOrder( int offset ) {

		if( !isTopLevel() ) {
			NSMutableArray<SWPage> siblings = sortedSiblings().mutableClone();

			// Check where the page is in the Array, remove it, and insert it again
			int i = siblings.indexOfObject( this );
			siblings.removeObjectAtIndex( i );
			siblings.insertObjectAtIndex( this, i + offset );

			// go through all pages and resort them
			Enumeration<SWPage> e = siblings.objectEnumerator();

			while( e.hasMoreElements() ) {
				SWPage aPage = e.nextElement();
				aPage.setSortNumber( new Integer( siblings.indexOfObject( aPage ) ) );
			}
		}
	}

	/**
	 * A boolean telling us if this page is the uppermost of it`s siblingPages
	 * array
	 */
	public boolean isFirst() {
		return USSortableUtilities.isFirst( this );
	}

	/**
	 * A boolean telling us if this page is the bottom of it`s siblingPages
	 * array
	 */
	public boolean isLast() {
		return USSortableUtilities.isLast( this, siblings() );
	}

	/**
	 * All SWComponents related to this page, sorted by their sortnumber
	 */
	public NSArray<SWComponent> sortedComponents() {
		return EOSortOrdering.sortedArrayUsingKeyOrderArray( components(), DEFAULT_SORT_ORDERINGS );
	}

	/**
	 * All published SWComponents related to this page, sorted by their
	 * sortnumber
	 */
	public NSArray<SWComponent> sortedAndApprovedComponents() {
		NSArray<SWComponent> a = EOQualifier.filteredArrayWithQualifier( sortedComponents(), PUBLISHED_QUALIFIER );
		return SWTimedContentUtilities.validateDisplayTimeForArray( a );
	}

	/**
	 * A convenience method to insert a component at the specified index
	 */
	public void insertComponentAtIndex( SWComponent componentToAdd, int index ) {

		NSMutableArray<SWComponent> components = (NSMutableArray<SWComponent>)sortedComponents();
		Enumeration<SWComponent> e = components.objectEnumerator();

		while( e.hasMoreElements() ) {
			SWComponent tc = e.nextElement();
			int h = components.indexOfObject( tc );
			if( h >= index ) {
				tc.setSortNumber( new Integer( h + 1 ) );
			}
		}

		if( componentToAdd.templateName() == null ) {
			componentToAdd.setTemplateName( DEFAULT_COMPONENT_TEMPLATE_NAME );
		}

		componentToAdd.setSortNumber( new Integer( index ) );
		addObjectToBothSidesOfRelationshipWithKey( componentToAdd, COMPONENTS_KEY );
	}

	/**
	 * A convenience method to remove a component from the page. Takes care of
	 * keeping the sortnumbers correct.
	 */
	public void removeComponent( SWComponent componentToRemove ) {

		NSMutableArray<SWComponent> components = (NSMutableArray<SWComponent>)sortedComponents();
		int index = components.indexOfObject( componentToRemove );
		Enumeration<SWComponent> e = components.objectEnumerator();

		while( e.hasMoreElements() ) {
			SWComponent tc = e.nextElement();
			int h = components.indexOfObject( tc );
			if( h >= index ) {
				tc.setSortNumber( new Integer( h - 1 ) );
			}
		}

		removeObjectFromBothSidesOfRelationshipWithKey( componentToRemove, COMPONENTS_KEY );
	}

	/**
	 * Checks if aPage is a subpage of this page. Includingself indicates if
	 * aPage should be included in the check.
	 */
	public boolean isParentPageOfPage( SWPage page, boolean includingSelf ) {
		return USHierarchyUtilities.isParentNodeOfNode( this, page, includingSelf );
	}

	/**
	 * returns an array with all pages owing inheritance to this page, down the
	 * entire site tree.
	 *
	 * @param includingTopLevel indicates if the topLevePage should be included
	 *        in the array.
	 */
	public NSArray<SWPage> everySubPage( boolean includingSelf ) {
		return USHierarchyUtilities.everyChild( this, includingSelf );
	}

	/**
	 * A method to retrieve every subpage
	 */
	public NSArray<SWPage> everySubPage() {
		return USHierarchyUtilities.everyChild( this, true );
	}

	/**
	 * Tells us if the specified page owes inheritance to the specified page
	 *
	 * @param page the page to check against
	 * @param includingTopLevel if this is false, aPage will not be checked
	 *        against, only it`s subpages
	 */
	public boolean isSubPageOfPage( SWPage page, boolean includingTopLevel ) {
		return USHierarchyUtilities.isChildOfNode( this, page, true );
	}

	/**
	 * A convenience method to transfer one page to another page. Takes care of
	 * maintaining sortorder and relationships
	 *
	 * @param newOwner the page to transfer ownership to
	 */
	public void transferOwnership( EOEnterpriseObject newOwner ) {
		transferOwnershipWithIndex( newOwner, 0 );
	}

	/**
	 * Addition for SWPage, to insert the page at a given index.
	 */
	public void transferOwnershipWithIndex( EOEnterpriseObject newOwner, int index ) {
		if( newOwner.equals( parent() ) && index >= parent().sortedSubPages().indexOfObject( this ) ) {
			index--;
		}

		parent().removeSubPage( this );
		((SWPage)newOwner).insertSubPageAtIndex( this, index );
	}

	/**
	 * Return the frontpage for this site
	 */
	public SWPage topLevelPage() {
		return (SWPage)USHierarchyUtilities.root( this );
	}

	/**
	 * Returns every published subpage of this page, sorted.
	 */
	public NSArray<SWPage> sortedAndApprovedEverySubPage() {

		NSMutableArray<SWPage> pageArray = new NSMutableArray<SWPage>();

		if( this.hasApprovedSubPages() ) {
			Enumeration<SWPage> e = sortedAndApprovedSubPages().objectEnumerator();

			while( e.hasMoreElements() ) {
				SWPage a = e.nextElement();
				pageArray.addObject( a );

				if( a.hasApprovedSubPages() ) {
					pageArray.addObjectsFromArray( a.sortedAndApprovedEverySubPage() );
				}
			}
		}

		return pageArray;
	}

	/**
	 * A convenience method, returns the SWSite this page belongs to
	 */
	public SWSite siteForThisPage() {
		return topLevelPage().site().lastObject();
	}

	/**
	 * Same as everyParentPage( true )
	 */
	public NSArray<SWPage> everyParentPage() {
		return USHierarchyUtilities.everyParentNode( this, true );
	}

	/**
	 * Returns an array of all parent pages. includeSelf indicates if the
	 * calling page should be included.
	 */
	public NSArray<SWPage> everyParentPage( boolean includingSelf ) {
		return USHierarchyUtilities.everyParentNode( this, includingSelf );
	}

	/**
	 * Same as breadcrumb( true )
	 */
	public NSArray<SWPage> breadcrumb() {
		return USHierarchyUtilities.everyParentNodeReversed( this, true );
	}

	/**
	 * Returns an array containing all parent pages in reverse order.
	 * includeSelf indicates if the calling page should be included.
	 */
	public NSArray<SWPage> breadcrumb( boolean includeSelf ) {
		return USHierarchyUtilities.everyParentNodeReversed( this, includeSelf );
	}

	/**
	 * A boolean telling us if this page has been published.
	 */
	public boolean isPublished() {
		return USUtilities.numberIsTrue( published() ) && isTimeValid();
	}

	/**
	 * A boolean telling us if this page is accessible to the public.
	 */
	public boolean isAccessible() {
		return USUtilities.numberIsTrue( accessible() ) && isTimeValid();
	}

	/**
	 * Returns the second page in the page hierarchy of this page`s parent array
	 */
	public SWPage secondLevelPage() {
		return (SWPage)USHierarchyUtilities.parentNodeAtLevel( this, 2 );
	}

	/**
	 * Returns true if this page`s password field is not empty
	 */
	public boolean isPasswordProtected() {
		return StringUtilities.hasValue( password() );
	}

	/**
	 * Returns the nearest password protected parent page in the hierarchy
	 */
	public SWPage passwordProtectedParent() {

		Enumeration<SWPage> e = everyParentPage().objectEnumerator();

		while( e.hasMoreElements() ) {
			SWPage p = e.nextElement();

			if( p.isPasswordProtected() ) {
				return p;
			}
		}

		return null;
	}

	/**
	 * Returns this pages name, plus the prefix specified in a parent page. If
	 * no prefix is specified, returns the page`s name
	 */
	public String nameWithPrefix() {
		String aString = (String)USHierarchyUtilities.valueInHierarchyForKeyPath( this, NAME_PREFIX_KEY );
		return StringUtilities.hasValue( aString ) ? (aString + name()) : name();
	}

	/**
	 * Returns this pages name, plus all prefixes specified for parent pages. If
	 * no prefixes are specified, returns the page's name
	 */
	public String nameWithAccumulatedPrefix() {

		Enumeration<SWPage> e = breadcrumb().objectEnumerator();
		StringBuilder b = new StringBuilder();

		while( e.hasMoreElements() ) {
			String s = e.nextElement().namePrefix();
			if( StringUtilities.hasValue( s ) ) {
				b.append( s );
			}
		}

		String aString = b.toString();

		return StringUtilities.hasValue( aString ) ? (aString + name()) : name();
	}

	/**
	 * Returns true if this page`s display time has come, and has not expired.
	 * Returns true if no values are specified for timeIn or timeOut.
	 */
	@Override
	public boolean isTimeValid() {
		return SWTimedContentUtilities.validateDisplayTime( this );
	}

	/**
	 * Returns the page at the specified index in the parent page hierarchy. 0
	 * is the front page of the site, 1 is the subpage of that page etc.
	 */
	public SWPage parentPageAtLevel( int aLevel ) {
		return (SWPage)USHierarchyUtilities.parentNodeAtLevel( this, aLevel );
	}

	/**
	 * Implementation of SWInheritsPrivileges - return the parent page
	 */
	@Override
	public SWInheritsPrivileges inheritsPrivilegesFrom() {
		return parent();
	}

	/**
	 * ???
	 */
	public NSArray<SWPage> expandedSiteTree() {
		return expandedSiteTreeFromLevel( 1 );
	}

	/**
	 * ???
	 */
	public NSArray<SWPage> expandedSiteTreeFromLevel( int level ) {

		SWPage theParentPage = parentPageAtLevel( level );

		if( theParentPage == null ) {
			return null;
		}

		NSArray<SWPage> subPages = theParentPage.sortedAndApprovedSubPages();
		Enumeration<SWPage> e = subPages.objectEnumerator();

		NSMutableArray<SWPage> returnArray = new NSMutableArray<SWPage>();

		while( e.hasMoreElements() ) {
			SWPage currentSubPage = e.nextElement();
			returnArray.addObject( currentSubPage );

			if( this.isSubPageOfPage( currentSubPage, true ) ) {
				returnArray.addObjectsFromArray( everySubPageForSelectedPage( currentSubPage ) );
			}
		}

		return returnArray;
	}

	/**
	 * ???
	 */
	private NSArray<SWPage> everySubPageForSelectedPage( SWPage aPage ) {
		NSArray<SWPage> subPages = aPage.sortedAndApprovedSubPages();
		Enumeration<SWPage> e = subPages.objectEnumerator();

		NSMutableArray<SWPage> returnArray = new NSMutableArray<SWPage>();

		while( e.hasMoreElements() ) {
			SWPage currentPage = e.nextElement();

			returnArray.addObject( currentPage );
			if( this.isSubPageOfPage( currentPage, true ) ) {
				returnArray.addObjectsFromArray( everySubPageForSelectedPage( currentPage ) );
			}
		}

		return returnArray;
	}

	/**
	 * Creates a new subpage of this page with the given name.
	 */
	public SWPage createSubPage() {
		SWPage newPage = new SWPage();
		editingContext().insertObject( newPage );

		newPage.setInheritsPrivileges( 1 );
		newPage.setAccessible( 1 );
		newPage.setPublished( 0 );

		insertSubPageAtIndex( newPage, children().count() );

		return newPage;
	}

	/**
	 * FIXME: Implement generic way to copy stuff.
	 */
	public SWPage createCopy() {

		SWPage newPage = new SWPage();
		editingContext().insertObject( newPage );

		newPage.setName( name() );
		newPage.setInheritsPrivileges( inheritsPrivileges() );
		newPage.setCustomInfoData( customInfoData() );
		newPage.setPublished( published() );
		newPage.setAccessible( accessible() );
		newPage.setSymbol( symbol() );
		newPage.setSortNumber( sortNumber() );
		newPage.setKeywords( keywords() );
		newPage.setText( text() );
		newPage.setLanguage( language() );
		newPage.setTimeIn( timeIn() );
		newPage.setTimeOut( timeOut() );

		if( USArrayUtilities.hasObjects( components() ) ) {
			Enumeration<SWComponent> e = components().objectEnumerator();

			while( e.hasMoreElements() ) {
				SWComponent oldComponent = e.nextElement();
				SWComponent newComponent = oldComponent.createCopy();
				newPage.addObjectToBothSidesOfRelationshipWithKey( newComponent, "components" );
			}
		}

		if( hasChildren() ) {
			Enumeration<SWPage> e = children().objectEnumerator();

			while( e.hasMoreElements() ) {
				newPage.addObjectToBothSidesOfRelationshipWithKey( e.nextElement().createCopy(), "subPages" );
			}
		}

		return newPage;
	}

	/**
	 * ???
	 */
	@Override
	public Object valueForKeyPath( String s ) {

		if( s != null && s.charAt( 0 ) == '@' ) {
			return valueForKeyPathWithOperator( s );
		}
		else {
			return super.valueForKeyPath( s );
		}
	}

	/**
	 * ???
	 */
	@Override
	public Object valueForKey( String s ) {
		if( s != null && s.charAt( 0 ) == '@' ) {
			return valueForKeyPathWithOperator( s );
		}
		else {
			return super.valueForKey( s );
		}
	}

	/**
	 * ???
	 */
	private Object valueForKeyPathWithOperator( String s ) {

		int i = s.indexOf( '.' );
		String s1;
		String s2;
		if( i < 0 ) {
			s1 = s.substring( 1 );
			s2 = "";
		}
		else {
			s1 = s.substring( 1, i );
			s2 = i >= s.length() - 1 ? "" : s.substring( i + 1 );
		}

		SWPageOperator operator = operatorForKey( s1 );

		if( operator != null ) {
			return operator.compute( this, s2 );
		}
		else {
			throw new IllegalArgumentException( "the operator " + s + " is not available " );
		}
	}

	/**
	 * ???
	 */
	static class _ValueHierarchyOperator implements SWPageOperator {

		public _ValueHierarchyOperator() {}

		@Override
		public Object compute( SWPage page, String s ) {
			return USHierarchyUtilities.valueInHierarchyForKeyPath( page, s );
		}
	}

	/**
	 * ???
	 */
	static class _LevelInHierarchyOperator implements SWPageOperator {

		public _LevelInHierarchyOperator() {}

		@Override
		public Object compute( SWPage page, String s ) {
			int i = s.indexOf( '.' );
			String s1;
			String s2;

			if( i < 0 ) {
				s1 = s;
				s2 = null;
			}
			else {
				s1 = s.substring( 0, i );
				s2 = (i >= (s.length() - 1)) ? null : s.substring( i + 1 );
			}

			int pageLevel = Integer.parseInt( s1 );
			SWPage returnPage = page.parentPageAtLevel( pageLevel );

			if( s2 == null ) {
				return returnPage;
			}

			if( s2 != null && returnPage != null ) {
				return returnPage.valueForKeyPath( s2 );
			}

			return null;
		}
	}

	/**
	 * ???
	 */
	static class _ExpandedHierarchyOperator implements SWPageOperator {

		public _ExpandedHierarchyOperator() {}

		@Override
		public Object compute( SWPage page, String s ) {
			int i = s.indexOf( '.' );
			String s1;

			if( i < 0 ) {
				s1 = s;
			}
			else {
				s1 = s.substring( 0, i );
			}

			int pageLevel = Integer.parseInt( s1 );
			return page.expandedSiteTreeFromLevel( pageLevel );
		}
	}

	/**
	 * ???
	 */
	static interface SWPageOperator {

		public abstract Object compute( SWPage page, String keyPath );
	}

	/**
	 * ???
	 */
	public static void setOperatorForKey( String s, SWPageOperator operator ) {

		if( s == null ) {
			throw new IllegalArgumentException( "Operator key cannot be null" );
		}
		if( operator == null ) {
			throw new IllegalArgumentException( "Operator cannot be null for " + s );
		}

		synchronized( _operators ) {
			_operators.setObjectForKey( operator, s );
		}
	}

	/**
	 * ???
	 */
	public static SWPageOperator operatorForKey( String s ) {

		SWPageOperator operator;

		synchronized( _operators ) {
			operator = _operators.objectForKey( s );
		}

		return operator;
	}

	/**
	 * ???
	 */
	static {
		setOperatorForKey( "valueInHierarchyForKeyPath", new _ValueHierarchyOperator() );
		setOperatorForKey( "parentPageAtLevel", new _LevelInHierarchyOperator() );
		setOperatorForKey( "expandedSiteTreeFromLevel", new _ExpandedHierarchyOperator() );
	}

	/**
	 * An object for accessing extended settings
	 */
	public SWCustomInfo customInfo() {
		if( _customInfo == null ) {
			_customInfo = new SWCustomInfo( this );
		}

		return _customInfo;
	}

	/**
	 * Returns the primary language of this page, english if no language is set.
	 */
	public String primaryLanguage() {
		String language = language();

		if( StringUtilities.hasValue( language ) ) {
			return language;
		}

		return siteForThisPage().englishLanguageName();
	}

	/**
	 * Returns the primary language of this page, english if no language is set.
	 */
	public Locale locale() {
		return siteForThisPage().locale();
	}

	/**
	 * Unique ID for this object to use in ajax-stuff.
	 */
	public String uniqueID() {
		return "id" + primaryKey();
	}

	@Override
	public String toStringHuman() {
		return name();
	}
}