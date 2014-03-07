package concept.components.client.looks;

import is.rebbi.core.util.StringUtilities;
import is.rebbi.wo.util.USHierarchyUtilities;
import is.rebbi.wo.util.USUtilities;

import java.util.Enumeration;

import com.webobjects.appserver.WOContext;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableArray;

import concept.SWGenericSiteLook;
import concept.SWPageUtilities;
import concept.data.SWComponent;
import concept.data.SWPage;
import concept.data.SWSite;
import er.extensions.foundation.ERXStringUtilities;

public class SWDefaultLook6 extends SWGenericSiteLook {

	public SWPage currentPage;
	public SWPage currentBreadcrumbPage;
	public SWComponent currentComponent;
	public String currentCSS;
	public int itemCount;

	private SWPage _headerPage, _topNavigationPage, _topExtrasPage, _extrasPage, _leftColumnPage, _topLeftPage, _extrasAbovePage, _columnPage, _footerPage, _topperPage, _aboveContentPage, _belowContentPage;

	public SWDefaultLook6( WOContext context ) {
		super( context );
	}

	public NSArray customCSSList() {
		NSMutableArray list = new NSMutableArray();

		// site css
		SWSite site = selectedPage().siteForThisPage();
		String css = "" + (String)site.customInfo().valueForKey( "siteCSS" );

		// page css
		SWPage page = selectedPage();
		while( page != null ) { // collect all css from page hierarchy
			String value = (String)page.customInfo().valueForKey( "pageCSS" );
			if( StringUtilities.hasValue( value ) ) {
				boolean no = USUtilities.booleanFromObject( page.customInfo().valueForKey( "noDefLook6Inheritance" ) );
				if( no ) { // no inheritance - pass only if page is selected page
					if( selectedPage().equals( page ) ) {
						css += "\n" + value;
					}
				}
				else {
					css += "\n" + value;
				}

			}
			page = page.parent();
		}

		NSArray items = new NSArray( css.split( "\n" ) );
		for( Enumeration<String> e = items.objectEnumerator(); e.hasMoreElements(); ) {
			String item = e.nextElement().trim();
			if( !"".equals( item ) ) {
				list.addObject( item );
			}
		}
		return list;
	}

	public NSArray navigationList() {
		NSArray list = null;
		SWPage p = null;
		String name = "swNavigationRoot";

		// first check if any key is set in page hierarchy
		String key = (String)USHierarchyUtilities.valueInHierarchyForKeyPath( selectedPage(), "customInfo." + name );
		if( key == null || key.length() == 0 ) {
			// the site
			key = selectedPage().siteForThisPage().customInfo().stringValueForKey( name );
		}

		if( StringUtilities.hasValue( key ) ) { // use key if found
			p = SWPageUtilities.pageWithName( session().defaultEditingContext(), key );
		}
		else { // else use default root
			p = selectedPage().topLevelPage();
		}

		if( p != null ) {
			list = p.sortedAndApprovedSubPages();
		}

		return list;
	}

	public String itemClass() {
		String klass = "topNav";

		if( itemCount == 0 ) {
			klass += " first";
		}
		else if( itemCount == topNavigationPage().sortedAndApprovedSubPages().count() - 1 ) {
			klass += " last";
		}

		if( selectedPage().isSubPageOfPage( currentPage, false ) ) {
			klass += " subpage selected";

		}
		else {
			// get the page currentPage links too
			String url = currentPage.externalURL();
			if( StringUtilities.hasValue( url ) ) { // has url
				String link = NSArray.componentsSeparatedByString( url, "/" ).lastObject().toString();
				SWPage page = null;
				if( StringUtilities.hasValue( link ) ) {
					if( ERXStringUtilities.isDigitsOnly( link ) ) {
						page = SWPageUtilities.pageWithID( session().defaultEditingContext(), new Integer( link ) );
					}
					else {
						page = SWPageUtilities.pageNamed( session().defaultEditingContext(), link );
					}
				}
				// and get check if that's the selected page
				if( page != null && page.primaryKey().equals( selectedPage().primaryKey() ) ) {
					klass += " selected";
				}
				else if( selectedPage().isSubPageOfPage( page, false ) ) {
					klass += " subpage selected";
				}

			}
			else if( currentPage.primaryKey().equals( selectedPage().primaryKey() ) ) {
				// has no url - check if current page is same as selected page
				klass += " selected";
			}
		}
		return klass.trim();
	}

	public String itemId() {
		String id = "topNav" + new Integer( itemCount );
		return id;
	}

	public SWPage headerPage() {
		_headerPage = hasPage( _headerPage, "swHeader" );
		return _headerPage;
	}

	public SWPage topNavigationPage() {
		_topNavigationPage = hasPage( _topNavigationPage, "swTopNavigation" );
		return _topNavigationPage;
	}

	public SWPage topLeftPage() {
		_topLeftPage = hasPage( _topLeftPage, "swTopLeft" );
		return _topLeftPage;
	}

	public SWPage topExtrasPage() {
		_topExtrasPage = hasPage( _topExtrasPage, "swTopExtras" );
		return _topExtrasPage;
	}

	public SWPage extrasAbovePage() {
		_extrasAbovePage = hasPage( _extrasAbovePage, "swExtrasAbove" );
		return _extrasAbovePage;
	}

	public SWPage leftColumnPage() {
		_leftColumnPage = hasPage( _leftColumnPage, "swLeftColumn" );
		return _leftColumnPage;
	}

	public SWPage extrasPage() {
		_extrasPage = hasPage( _extrasPage, "swExtras" );
		return _extrasPage;
	}

	public SWPage topperPage() {
		_topperPage = hasPage( _topperPage, "swTopper" );
		return _topperPage;
	}

	public SWPage columnPage() {
		_columnPage = hasPage( _columnPage, "swColumn" );
		SWPage page = _columnPage;
		boolean hide = selectedPage().customInfo().booleanValueForKey( "hideRightColumn" );
		if( hide ) {
			page = null;
		}
		return page;
	}

	public SWPage aboveContentPage() {
		_aboveContentPage = hasPage( _aboveContentPage, "swAboveContent" );
		return _aboveContentPage;
	}

	public SWPage belowContentPage() {
		_belowContentPage = hasPage( _belowContentPage, "swBelowContent" );
		return _belowContentPage;
	}

	public SWPage footerPage() {
		_footerPage = hasPage( _footerPage, "swFooter" );
		return _footerPage;
	}

	public boolean hasLanguageNavigator() {
		boolean hasLN = USUtilities.booleanFromObject( USHierarchyUtilities.valueInHierarchyForKeyPath( selectedPage(), "customInfo.swPageInLanguageNavigator" ) );
		if( !hasLN ) {
			hasLN = selectedPage().siteForThisPage().customInfo().booleanValueForKey( "swPageInLanguageNavigator" );
		}
		return hasLN;
	}

	public boolean hasTopNavigationOrExtras() {
		return (topNavigationPage() != null) || (topExtrasPage() != null);
	}

	private SWPage hasPage( SWPage page, String name ) {
		if( page == null ) {
			SWPage p = null;
			SWPage pp = selectedPage();
			// first check if any key is set in page hierarchy
			String key = null; // =
								// (String)SWHierarchyUtilities.valueInHierarchyForKeyPath(
								// selectedPage(), "customInfo." + name );

			while( pp != null ) {
				String nm = (String)pp.customInfo().valueForKey( name );
				if( StringUtilities.hasValue( nm ) ) {
					String in = (String)pp.customInfo().valueForKey( "noDefLook6Inheritance" );
					if( "true".equals( in ) ) {
						if( selectedPage().equals( pp ) ) {
							key = nm;
							break;
						}
					}
					else {
						key = nm;
						break;
					}
				}
				pp = pp.parent();
			}

			if( key == null || key.length() == 0 ) {
				// for the site
				key = selectedPage().siteForThisPage().customInfo().stringValueForKey( name );
			}

			if( StringUtilities.hasValue( key ) ) { // use key if found
				p = SWPageUtilities.pageWithName( session().defaultEditingContext(), key );
			}
			else { // else use default names
				p = SWPageUtilities.pageWithName( session().defaultEditingContext(), name );
			}

			page = p;
		}
		return page;
	}

	public boolean showNavigation() {
		boolean show = false;

		return show;
	}

	public boolean showColumn() {
		boolean show = false;

		return show;
	}

	public String headerClass() {
		return classForList( headerPage().sortedAndApprovedComponents() );
	}

	public String topLeftClass() {
		return classForList( topLeftPage().sortedAndApprovedComponents() );
	}

	public String topExtrasClass() {
		return classForList( topExtrasPage().sortedAndApprovedComponents() );
	}

	public String topperClass() {
		return classForList( topperPage().sortedAndApprovedComponents() );
	}

	public String extrasAboveClass() {
		return classForList( extrasAbovePage().sortedAndApprovedComponents() );
	}

	public String extrasClass() {
		return classForList( extrasPage().sortedAndApprovedComponents() );
	}

	public String leftColumnClass() {
		return classForList( leftColumnPage().sortedAndApprovedComponents() );
	}

	public String columnClass() {
		return classForList( columnPage().sortedAndApprovedComponents() );
	}

	public String contentAboveClass() {
		return classForList( aboveContentPage().sortedAndApprovedComponents() );
	}

	public String contentBelowClass() {
		return classForList( belowContentPage().sortedAndApprovedComponents() );
	}

	public String footerClass() {
		return classForList( footerPage().sortedAndApprovedComponents() );
	}

	private String classForList( NSArray list ) {
		String klass = "item ";
		klass += currentComponent.templateName().toLowerCase();
		klass += " nr" + currentComponent.primaryKey();
		if( itemCount == 0 ) {
			klass += " first";
		}
		else if( itemCount == list.count() - 1 ) {
			klass += " last";
		}
		return klass;
	}

	public String topNavigationUrl() {
		String url = "#";
		boolean noSubpages;

		NSArray sp = currentPage.sortedAndApprovedSubPages();
		noSubpages = (sp == null || sp.count() == 0);
		if( hasTopNavigationDropDownHover() || noSubpages ) {
			if( StringUtilities.hasValue( currentPage.externalURL() ) ) {
				url = currentPage.externalURL();
			}
			else if( StringUtilities.hasValue( currentPage.symbol() ) ) {
				url = "/page/" + currentPage.symbol();
			}
			else {
				url = "/id/" + currentPage.primaryKey();
			}
		}

		return url;
	}

	public boolean hasTopNavigationDropDown() {
		boolean hasDD = USUtilities.booleanFromObject( USHierarchyUtilities.valueInHierarchyForKeyPath( selectedPage(), "customInfo.swTopNavigationDropDown" ) );
		if( !hasDD ) {
			hasDD = selectedPage().siteForThisPage().customInfo().booleanValueForKey( "swTopNavigationDropDown" );
		}
		return hasDD;
	}

	public boolean hasTopNavigationDropDownHover() {
		boolean hasHover = USUtilities.booleanFromObject( USHierarchyUtilities.valueInHierarchyForKeyPath( selectedPage(), "customInfo.swTopNavigationDropDownHover" ) );
		if( !hasHover ) {
			hasHover = selectedPage().siteForThisPage().customInfo().booleanValueForKey( "swTopNavigationDropDownHover" );
		}

		return hasHover;
	}

	public boolean hasTopNavigationDropDownWithItems() {
		boolean hasDD = hasTopNavigationDropDown();
		if( hasDD ) {
			NSArray navItems = currentPage.sortedAndApprovedSubPages();
			if( navItems == null || navItems.size() == 0 ) {
				hasDD = false;
			}
		}
		return hasDD;
	}

	public String topNavigationDropDownItemId() {
		return "topnav_dropdownitem_" + itemCount;
	}

	public int subPageColumnCount() {
		Integer cnt = USUtilities.integerFromObject( USHierarchyUtilities.valueInHierarchyForKeyPath( selectedPage(), "customInfo.swTopNavigationDropDownColumnCount" ) );
		if( cnt != null && cnt.intValue() > 0 ) {
			return cnt.intValue();
		}
		else {
			return 0;
		}
	}
}