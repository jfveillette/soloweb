package concept.data;

import is.rebbi.core.util.StringUtilities;
import is.rebbi.wo.interfaces.SWHasCustomInfo;
import is.rebbi.wo.interfaces.SWInheritsPrivileges;
import is.rebbi.wo.interfaces.SWTimedContent;
import is.rebbi.wo.interfaces.SWTransferable;
import is.rebbi.wo.util.SWCustomInfo;
import is.rebbi.wo.util.SWTimedContentUtilities;
import is.rebbi.wo.util.USArrayUtilities;
import is.rebbi.wo.util.USHierarchy;
import is.rebbi.wo.util.USHierarchyUtilities;
import is.rebbi.wo.util.USUtilities;

import java.util.Enumeration;
import java.util.Locale;

import com.webobjects.eocontrol.EOEnterpriseObject;
import com.webobjects.eocontrol.EOQualifier;
import com.webobjects.eocontrol.EOSortOrdering;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSMutableDictionary;

import concept.components.client.SWPageSearchResult;
import concept.data.auto._SWPage;
import concept.search.SWSearchItem;
import concept.util.HumanReadable;

/**
 * An SWPage represents a single page of content in SoloWeb
 */

public class SWPage extends _SWPage implements SWTransferable, USHierarchy<SWPage>, SWTimedContent, SWInheritsPrivileges, SWSearchItem, SWHasCustomInfo, HumanReadable {

	private static final NSArray<EOSortOrdering> DEFAULT_SORT_ORDERINGS = SORT_NUMBER.ascs();
	private static final EOQualifier PUBLISHED_QUALIFIER = PUBLISHED.eq( 1 );

	private static NSMutableDictionary<String,Operator> _operators = new NSMutableDictionary<>();
	private SWCustomInfo _customInfo;
	private String _searchItemContents;

	@Override
	public void setSearchItemContents( String contents ) {
		_searchItemContents = contents;
	}

	@Override
	public String searchItemText() {
		return _searchItemContents;
	}

	/**
	 * An object for accessing extended settings.
	 */
	public SWCustomInfo customInfo() {
		if( _customInfo == null ) {
			_customInfo = new SWCustomInfo( this );
		}

		return _customInfo;
	}

	/**
	 * Tells us if this page has any subpages.
	 */
	public boolean hasSubPages() {
		return hasChildren();
	}

	/**
	 * Tells us if this page has any subpages.
	 */
	public boolean hasChildren() {
		return USHierarchyUtilities.hasChildren( this );
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
	public NSArray<SWPage> siblingPages() {
		return USHierarchyUtilities.siblings( this );
	}

	/**
	* Pages at the same level as this one in the site tree, sorted
	*/
	public NSArray<SWPage> sortedSiblingPages() {
		return EOSortOrdering.sortedArrayUsingKeyOrderArray( siblingPages(), DEFAULT_SORT_ORDERINGS );
	}

	/**
	 * A convenience method to insert a new subpage at the specified index. Takes care of resorting all pages with sortnumber above the specified index.
	 *
	 * @param aPAge an SWPage object to insert
	 * @param index sortingnumber for the new page
	 */
	public void insertSubPageAtIndex( SWPage aPage, int index ) {

		if( hasSubPages() ) {
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
	 * A convenience method to remove the specified subpage. Takes care of resorting all pages with sortnumbers above the specified index.
	 *
	 * @param aPAge an SWPage object to remove from subPages
	 */
	public void removeSubPage( SWPage aPage ) {

		NSArray<SWPage> pages = sortedSubPages();
		int index = pages.indexOfObject( aPage );
		Enumeration<SWPage> e = pages.objectEnumerator();

		while( e.hasMoreElements() ) {
			SWPage p = e.nextElement();
			int h = pages.indexOfObject( p );

			if( h >= index ) {
				p.setSortNumber( new Integer( h - 1 ) );
			}
		}

		removeObjectFromBothSidesOfRelationshipWithKey( aPage, CHILDREN_KEY );
	}

	/**
	 * Used to shift the page's sort ordering (only done by "1" or "-1" in the current version
	 */
	public void changeSortOrder( int offset ) {

		if( !isTopLevel() ) {
			NSMutableArray<SWPage> siblings = sortedSiblingPages().mutableClone();

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
	 * A boolean telling us if this page is the uppermost of it`s siblingPages array
	 */
	public boolean isAtTop() {
		return sortNumber().intValue() == 0;
	}

	/**
	 * A boolean telling us if this page is the bottom of it`s siblingPages array
	 */
	public boolean isAtBottom() {
		return sortNumber().intValue() == (siblingPages().count() - 1);
	}

	/**
	 * All SWComponents related to this page, sorted by their sortnumber
	 */
	public NSArray<SWComponent> sortedComponents() {
		return EOSortOrdering.sortedArrayUsingKeyOrderArray( components(), DEFAULT_SORT_ORDERINGS );
	}

	/**
	 * All published SWComponents related to this page, sorted by their sortnumber
	 */
	public NSArray<SWComponent> sortedAndApprovedComponents() {
		NSArray<SWComponent> anArray = EOQualifier.filteredArrayWithQualifier( sortedComponents(), PUBLISHED_QUALIFIER );
		return SWTimedContentUtilities.validateDisplayTimeForArray( anArray );
	}

	/**
	 * A convenience method to insert a component at the specified index
	 */
	public void insertComponentAtIndex( SWComponent aComponent, int index ) {

		NSMutableArray<SWComponent> components = (NSMutableArray)sortedComponents();
		Enumeration<SWComponent> e = components.objectEnumerator();

		while( e.hasMoreElements() ) {
			SWComponent tc = e.nextElement();
			int h = components.indexOfObject( tc );
			if( h >= index ) {
				tc.setSortNumber( new Integer( h + 1 ) );
			}
		}

		if( aComponent.templateName() == null ) {
			aComponent.setTemplateName( "ButurTemplate004" );
		}

		aComponent.setSortNumber( new Integer( index ) );
		addObjectToBothSidesOfRelationshipWithKey( aComponent, COMPONENTS_KEY );
	}

	/**
	 * A convenience method to remove a component from the page. Takes care of keeping the sortnumbers correct.
	 */
	public void removeComponent( SWComponent aComponent ) {

		NSMutableArray<SWComponent> components = (NSMutableArray)sortedComponents();
		int index = components.indexOfObject( aComponent );
		Enumeration e = components.objectEnumerator();

		while( e.hasMoreElements() ) {
			SWComponent tc = (SWComponent)e.nextElement();
			int h = components.indexOfObject( tc );
			if( h >= index ) {
				tc.setSortNumber( new Integer( h - 1 ) );
			}
		}

		removeObjectFromBothSidesOfRelationshipWithKey( aComponent, "components" );
	}

	/**
	* Checks if aPage is a subpage of this page. Includingself indicates if aPage should be included in the check.
	*/
	public boolean isParentPageOfPage( SWPage aPage, boolean includingSelf ) {
		return USHierarchyUtilities.isParentNodeOfNode( this, aPage, includingSelf );
	}

	/**
	 * returns an array with all pages owing inheritance to this page, down the entire site tree.
	 *
	 * @param includingTopLevel indicates if the topLevePage should be included in the array.
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
	 * @param aPage the page to check against
	 * @param includingTopLevel if this is false, aPage will not be checked against, only it`s subpages
	 */
	public boolean isSubPageOfPage( SWPage aPage, boolean includingTopLevel ) {
		return USHierarchyUtilities.isChildOfNode( this, aPage, true );
	}

	/**
	 * A convenience method to transfer one page to another page. Takes care of maintaining sortorder and relationships
	 *
	 * @param newOwner the page to transfer ownership to
	 */
	@Override
	public void transferOwnership( EOEnterpriseObject newOwner ) {
		transferOwnershipWithIndex( newOwner, 0 );
	}

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

		NSMutableArray<SWPage> pageArray = new NSMutableArray<>();

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
	 * same as everyParentPage( true )
	 */
	public NSArray<SWPage> everyParentPage() {
		return USHierarchyUtilities.everyParentNode( this, true );
	}

	/**
	 * Returns an array of all parent pages. includeSelf indicates if the calling page should be included.
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
	 * Returns an array containing all parent pages in reverse order. includeSelf indicates if the calling page should be included.
	 */
	public NSArray<SWPage> breadcrumb( boolean includeSelf ) {
		return USHierarchyUtilities.everyParentNodeReversed( this, includeSelf );
	}

	/**
	 * A boolean telling us if this page has been published
	 */
	public boolean isPublished() {
		return USUtilities.numberIsTrue( published() ) && isTimeValid();
	}

	/**
	 * A boolean telling us if this page is accessible to the public
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
	 * Returns the nearest password public parent page in the hierarchy
	 *
	public SWPage passwordProtectedParent() {

		Enumeration e = this.everyParentPage().objectEnumerator();

		while( e.hasMoreElements() ) {
			SWPage p = (SWPage)e.nextElement();

			if( StringUtilities.hasValue( p.password() ) ) {
				return p;
			}
		}

		return null;
	}
	*/

	/**
	 * Returns this pages name, plus the prefix specified in a parent page. If no prefix is specified, returns the page`s name
	 */
	public String nameWithPrefix() {
		String aString = (String)USHierarchyUtilities.valueInHierarchyForKeyPath( this, NAME_PREFIX_KEY );
		return StringUtilities.hasValue( aString ) ? (aString + name()) : name();
	}

	/**
	 * Returns this pages name, plus all prefixes specified for parent pages. If no prefixes are specified, returns the page's name
	 */
	public String nameWithAccumulatedPrefix() {

		Enumeration<SWPage> e = breadcrumb().objectEnumerator();
		StringBuffer b = new StringBuffer();

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
	* Returns true if this page`s display time has come, and has not expired. Returns true if no values are specified for timeIn or timeOut.
	*/
	@Override
	public boolean isTimeValid() {
		return SWTimedContentUtilities.validateDisplayTime( this );
	}

	/**
	 * Returns the page at the specified index in the parent page hierarchy. 0 is the front page of the site, 1 is the subpage of that page etc.
	 */
	public SWPage parentPageAtLevel( int aLevel ) {
		return (SWPage)USHierarchyUtilities.parentNodeAtLevel( this, aLevel );
	}

	/**
	* Implementation of SWInheritsPrivileges - return the parent page
	*/
	@Override
	public SWPage inheritsPrivilegesFrom() {
		return parent();
	}

	public NSArray<SWPage> expandedSiteTree() {
		return expandedSiteTreeFromLevel( 1 );
	}

	public NSArray<SWPage> expandedSiteTreeFromLevel( int level ) {

		SWPage theParentPage = parentPageAtLevel( level );

		if( theParentPage == null ) {
			return null;
		}

		NSArray<SWPage> children = theParentPage.sortedAndApprovedSubPages();
		Enumeration<SWPage> e = children.objectEnumerator();

		NSMutableArray<SWPage> returnArray = new NSMutableArray<>();

		while( e.hasMoreElements() ) {
			SWPage currentSubPage = e.nextElement();
			returnArray.addObject( currentSubPage );

			if( this.isSubPageOfPage( currentSubPage, true ) ) {
				returnArray.addObjectsFromArray( everySubPageForSelectedPage( currentSubPage ) );
			}
		}

		return returnArray;
	}

	private NSArray<SWPage> everySubPageForSelectedPage( SWPage aPage ) {
		NSArray<SWPage> children = aPage.sortedAndApprovedSubPages();
		Enumeration<SWPage> e = children.objectEnumerator();

		NSMutableArray<SWPage> returnArray = new NSMutableArray<>();

		while( e.hasMoreElements() ) {
			SWPage currentPage = e.nextElement();

			returnArray.addObject( currentPage );
			if( this.isSubPageOfPage( currentPage, true ) ) {
				returnArray.addObjectsFromArray( everySubPageForSelectedPage( currentPage ) );
			}
		}

		return returnArray;
	}

	public int getUserGroupID() {
		NSArray<SWPage> pages = USHierarchyUtilities.everyParentNode( this, true );

		SWPage page;
		Object pageGroupIDObject;
		int pageGroupID;

		for( int i = 0; i < pages.count(); i++ ) {
			page = (pages.objectAtIndex( i ));
			pageGroupIDObject = page.customInfo().valueForKey( "assignedGroup" );

			if( pageGroupIDObject != null ) {
				pageGroupID = USUtilities.integerFromObject( pageGroupIDObject ).intValue();

				if( pageGroupID != -1 ) {
					return pageGroupID;
				}
			}
		}

		return -1;
	}

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
		newPage.setImageOne( imageOne() );
		newPage.setImageOne( imageTwo() );
		newPage.setLanguage( language() );
		newPage.setTimeIn( timeIn() );
		newPage.setTimeOut( timeOut() );

		if( USArrayUtilities.hasObjects( components() ) ) {
			Enumeration<SWComponent> e = components().objectEnumerator();

			while( e.hasMoreElements() ) {
				SWComponent oldComponent = e.nextElement();
				SWComponent newComponent = oldComponent.createCopy();
				newPage.addObjectToBothSidesOfRelationshipWithKey( newComponent, COMPONENTS_KEY );
			}
		}

		if( hasSubPages() ) {
			Enumeration<SWPage> e = children().objectEnumerator();

			while( e.hasMoreElements() ) {
				newPage.addObjectToBothSidesOfRelationshipWithKey( e.nextElement().createCopy(), CHILDREN_KEY );
			}
		}

		return newPage;
	}

	/**
	 * Returns the language field value for this page or the page's site
	 * or is if nothing has been entered in either page or site.
	 */
	public String pageLanguageCode() {
		String lang = language();

		if( lang == null || lang.length() == 0 ) {
			lang = siteForThisPage().language();
			if( lang == null || lang.length() == 0 ) {
				lang = "is";
			}
		}

		if( lang.equalsIgnoreCase( "icelandic" ) ) {
			lang = "is";
		}
		else if( lang.equalsIgnoreCase( "english" ) ) {
			lang = "en";
		}

		return lang;
	}

	public boolean hasSymbol() {
		return StringUtilities.hasValue( symbol() );
	}

	public String pageLink() {
		if( hasSymbol() ) {
			return "/page/" + symbol();
		}
		else {
			return "/id/" + primaryKey();
		}
	}

	@Override
	public Object valueForKeyPath( String s ) {

		if( s != null && s.charAt( 0 ) == '@' ) {
			return valueForKeyPathWithOperator( s );
		}
		else {
			return super.valueForKeyPath( s );
		}
	}

	@Override
	public Object valueForKey( String s ) {
		if( s != null && s.charAt( 0 ) == '@' ) {
			return valueForKeyPathWithOperator( s );
		}
		else {
			return super.valueForKey( s );
		}
	}

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

		Operator operator = operatorForKey( s1 );

		if( operator != null ) {
			return operator.compute( this, s2 );
		}
		else {
			throw new IllegalArgumentException( "the operator " + s + " is not available " );
		}
	}

	static class _ValueHierarchyOperator implements Operator {

		public _ValueHierarchyOperator() {}

		@Override
		public Object compute( SWPage page, String s ) {
			return USHierarchyUtilities.valueInHierarchyForKeyPath( page, s );
		}
	}

	static class _TrueValueInHierachyOperator implements Operator {

		public _TrueValueInHierachyOperator() {}

		@Override
		public Object compute( SWPage page, String s ) {
			return USHierarchyUtilities.trueValueInHierarchy( page, s );
		}
	}

	static class _TrueFalseInheritValueInHierarchyOperator implements Operator {

		public _TrueFalseInheritValueInHierarchyOperator() {}

		@Override
		public Object compute( SWPage page, String s ) {
			return USHierarchyUtilities.trueFalseInheritValueInHierarchy( page, s );
		}
	}

	static class _LevelInHierarchyOperator implements Operator {

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

	static class _ExpandedHierarchyOperator implements Operator {

		public _ExpandedHierarchyOperator() {}

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
			return page.expandedSiteTreeFromLevel( pageLevel );
		}
	}

	static interface Operator {
		public abstract Object compute( SWPage page, String keyPath );
	}

	public static void setOperatorForKey( String s, Operator operator ) {

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

	public static Operator operatorForKey( String s ) {

		Operator operator;

		synchronized( _operators ) {
			operator = _operators.objectForKey( s );
		}

		return operator;
	}

	static {
		setOperatorForKey( "valueInHierarchyForKeyPath", new _ValueHierarchyOperator() );
		setOperatorForKey( "parentPageAtLevel", new _LevelInHierarchyOperator() );
		setOperatorForKey( "expandedSiteTreeFromLevel", new _ExpandedHierarchyOperator() );
		setOperatorForKey( "trueValueInHierarchy", new _TrueValueInHierachyOperator() );
		setOperatorForKey( "trueFalseInheritValueInHierarchy", new _TrueFalseInheritValueInHierarchyOperator() );
	}

	@Override
	public String searchResultComponentName() {
		return SWPageSearchResult.class.getSimpleName();
	}

	@Override
	public boolean isValidResult() {
		return isPublished();
	}

	public Integer pageID () {
		return id();
	}

	public void setPageID( Integer value ) {
		setId( value );
	}

	public NSArray<SWPage> subPages() {
		return children();
	}

	public SWPage parentPage() {
		return parent();
	}

	public void setParentPage( SWPage value ) {
		setParent( value );
	}

	@Override
	public String toStringHuman() {
		return name();
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
}