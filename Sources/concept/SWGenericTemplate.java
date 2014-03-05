package concept;

import com.webobjects.appserver.WOComponent;
import com.webobjects.appserver.WOContext;
import com.webobjects.appserver.WOCookie;
import com.webobjects.appserver.WOResponse;

import concept.components.client.SWStandardSiteLook;
import concept.data.SWComponent;
import concept.data.SWPage;

/**
 * SWGenericTemplate is the common ancestor of all Templates created in SoloWeb.
 * In version 2.5, there is only one subclass, SWStandardTemplate, the default template used to display components.
 */

public abstract class SWGenericTemplate extends WOComponent {

	/**
	 * The currently selected page
	 */
	private SWPage _selectedPage;

	private WOCookie cookie;

	/**
	 * An iteration variable for the repetition of components on the page.
	 */
	public SWComponent currentComponent;

	public SWGenericTemplate( WOContext context ) {
		super( context );

		cookie = null;
	}

	public void setCookie( WOCookie theCookie ) {
		cookie = theCookie;
	}

	public WOCookie getCookie() {
		return cookie;
	}

	/**
	 * Returns the name of the SiteLook to display for this page, specified in SWSite. If no look is specified, the "SWStandardSiteLook" is displayed.
	 */
	public String lookName() {

		String lookName = context().request().stringFormValueForKey( "look" );

		if( lookName != null ) {
			return lookName;
		}

		lookName = selectedPage().look();

		if( lookName != null ) {
			return lookName;
		}

		lookName = selectedPage().siteForThisPage().look();

		if( lookName != null ) {
			return lookName;
		}

		return SWStandardSiteLook.class.getSimpleName();
	}

	/**
	 * Returns the selected page.
	 */
	public SWPage selectedPage() {
		return _selectedPage;
	}

	/**
	 * Sets the selected page.
	 */
	public void setSelectedPage( SWPage p ) {
		_selectedPage = p;
	}

	@Override
	public void appendToResponse( WOResponse response, WOContext context ) {
		super.appendToResponse( response, context );

		if( cookie != null ) {
			response.addCookie( cookie );
		}
	}
}