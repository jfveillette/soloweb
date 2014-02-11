package concept;

import is.rebbi.core.util.StringUtilities;
import is.rebbi.wo.util.USHierarchyUtilities;
import is.rebbi.wo.util.USUtilities;

import com.webobjects.appserver.WOComponent;
import com.webobjects.appserver.WOContext;
import com.webobjects.appserver.WOResponse;

import concept.data.SWPage;
import er.extensions.appserver.ERXResponseRewriter;
import er.extensions.appserver.ERXResponseRewriter.TagMissingBehavior;

/**
 * SWGenericSiteLook is the common ancestor of all Site Looks created for SoloWeb. Subclass this to create your own custom look component.
 */

public abstract class SWGenericSiteLook extends WOComponent {

	/**
	 * The currently selected page.
	 */
	public SWPage _selectedPage;

	public SWGenericSiteLook( WOContext context ) {
		super( context );
	}

	/**
	 * The currently selected page.
	 */
	public SWPage selectedPage() {
		return _selectedPage;
	}

	/**
	 * Sets the currently selected page.
	 */
	public void setSelectedPage( SWPage p ) {
		_selectedPage = p;
	}

	@Override
	public void appendToResponse( WOResponse response, WOContext context ) {
		super.appendToResponse( response, context );

		String code = (String)USHierarchyUtilities.valueInHierarchyForKeyPath( selectedPage(), "customInfo.googleAnalyticsCode" );

		if( !StringUtilities.hasValue( code ) ) {
			code = selectedPage().siteForThisPage().customInfo().stringValueForKey( "googleAnalyticsCode" );
		}

		if( StringUtilities.hasValue( code ) ) {
			String googleHtml = USUtilities.stringFromResource( "GoogleAnalyticsTrackingCode.txt", Concept.sw().frameworkBundleName() );
			googleHtml = StringUtilities.replace( googleHtml, "UA-XXXXX-X", "UA-" + code );
			ERXResponseRewriter.insertInResponseBeforeHead( response, context, googleHtml, TagMissingBehavior.SkipAndWarn );
		}
	}
}