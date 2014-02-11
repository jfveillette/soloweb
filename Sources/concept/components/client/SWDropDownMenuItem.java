package concept.components.client;

import is.rebbi.wo.util.USHierarchyUtilities;
import is.rebbi.wo.util.USUtilities;

import com.webobjects.appserver.WOContext;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSRange;

import concept.SWGenericComponent;
import concept.data.SWPage;
import concept.deprecated.SWUtilities;

public class SWDropDownMenuItem extends SWGenericComponent {

	// Values that parent component must set
	public int itemNumber;
	public SWPage currentPage;

	// Other local stuff
	public int subPageCount, subPageIndex, subSubPageGroupIndex, subPageFillerCount;
	public SWPage currentSubPage;
	public SWPage currentSubSubPage;
	private NSArray currentSubSubPageGroup;
	public int subSubPageColumnNo;
	public int subPageColumnCount = 4;

	public SWDropDownMenuItem( WOContext context ) {
		super( context );
	}

	public String topNavigationDropDownItemId() {
		return "topnav_dropdownitem_" + itemNumber;
	}

	public boolean topNavigationShowVerticalSeparator() {
		boolean show = (subPageIndex + 1 < subPageCount) && ((subSubPageColumnNo % getSubPageColumnCount()) == 0);
		return show;
	}

	public int topNavigationFillerSubPagesCount() {
		int count = getSubPageColumnCount() - (subSubPageColumnNo % getSubPageColumnCount());
		if( count == getSubPageColumnCount() ) {
			count = 0;
		}
		return count;
	}

	public String topNavigationDropDownItemSubPageClass() {
		String klass = "topnav_dropdown_item_subpage";

		if( subSubPageColumnNo % getSubPageColumnCount() == 1 ) {
			klass += " first";
		}
		else if( subSubPageColumnNo % getSubPageColumnCount() == 0 ) {
			klass += " last";
		}

		return klass;
	}

	public String topNavigationDropDownItemSubPageFillerClass() {
		String klass = "topnav_dropdown_item_subpage";

		if( (subPageFillerCount + 1) == topNavigationFillerSubPagesCount() ) {
			klass += " last";
		}

		return klass;
	}

	public String topNavigationDropDownItemSubSubPageClass() {
		String klass = "topnav_dropdown_item_subsubpage";
		if( currentSubSubPage.isParentPageOfPage( selectedPage(), true ) ) {
			klass += " selected";
		}
		return klass;
	}

	public NSArray subPages() {
		subSubPageColumnNo = 0;
		NSArray pages = currentPage.sortedAndApprovedSubPages();
		subPageCount = pages.count();
		return pages;
	}

	public NSArray getCurrentSubSubPageGroup() {
		return currentSubSubPageGroup;
	}

	public void setCurrentSubSubPageGroup( NSArray a ) {
		currentSubSubPageGroup = a;
		if( a != null ) {
			subSubPageColumnNo++;
		}
	}

	/**
	 * Returns an array of arrays of subsub pages split up by the max number of subsub page per column setting.
	 * If the max setting is blank or zero then all subsub pages are returned in a single array within the returned array.
	 */
	public NSArray subSubPageGroups() {
		NSArray subSubPages = currentSubPage.sortedAndApprovedSubPages();
		NSMutableArray groups = new NSMutableArray();
		NSMutableArray group;
		Integer max;

		max = USUtilities.integerFromObject( USHierarchyUtilities.valueInHierarchyForKeyPath( selectedPage(), "customInfo.swTopNavigationDropDownMaxSubSubPagesPerColumn" ) );
		if( max == null || max.intValue() == 0 ) {
			max = selectedPage().siteForThisPage().customInfo().integerValueForKey( "swTopNavigationDropDownMaxSubSubPagesPerColumn" );
		}

		if( max == null || max == 0 ) {
			groups.addObject( subSubPages );
		}
		else {
			NSRange range;
			SWPage fillerPage = new SWPage();
			if( subSubPages.count() > 0 ) {
				while( subSubPages.count() > 0 ) {
					range = new NSRange( 0, subSubPages.count() > max ? max : subSubPages.count() );
					group = new NSMutableArray( subSubPages.subarrayWithRange( range ) );
					groups.addObject( group );
					if( subSubPages.count() < max ) {
						subSubPages = new NSArray();
						while( group.count() < max ) {
							group.addObject( fillerPage );
						}
					}
					else {
						range = new NSRange( range.length(), subSubPages.count() - max );
						subSubPages = subSubPages.subarrayWithRange( range );
					}
				}
			}
			else {
				NSMutableArray fp = new NSMutableArray();
				for( int fpi = 0; fpi < max; fpi++ ) {
					fp.addObject( fillerPage );
				}
				group = new NSMutableArray( fp );
				groups.addObject( group );
			}
		}

		return groups;
	}

	public boolean showSubPageName() {
		return (subSubPageGroupIndex == 0);
	}

	public String currentSubPageLinkTarget() {
		boolean newWindow = currentSubPage.customInfo().booleanValueForKey( "externalUrlInNewWindow" );
		return (newWindow ? "_blank" : "");
	}

	private int getSubPageColumnCount() {
		if( subPageColumnCount > 0 ) {
			return subPageColumnCount;
		}
		else {
			return 4;
		}
	}

	public String currentSubSubPageLinkTarget() {
		boolean newWindow = currentSubSubPage.customInfo().booleanValueForKey( "externalUrlInNewWindow" );
		return (newWindow ? "_blank" : "");
	}
}