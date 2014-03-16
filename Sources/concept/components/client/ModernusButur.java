package concept.components.client;

import is.rebbi.core.util.StringUtilities;

import com.webobjects.appserver.WOComponent;
import com.webobjects.appserver.WOContext;

import concept.data.SWPage;
import concept.data.SWSite;

public class ModernusButur extends WOComponent {

	private SWPage page;
	private SWSite site;
	private String modernusSiteId;
	private String modernusServiceId;
	private String modernusSubdomain;

	public ModernusButur( WOContext context ) {
		super( context );
	}

	public SWPage getPage() {
		return page;
	}

	public void setPage( SWPage page ) {
		this.page = page;
		site = page.siteForThisPage();
	}

	public boolean doShow() {
		modernusSiteId = page.customInfo().stringValueForKey( "modernusSiteId" );
		if( !StringUtilities.hasValue( modernusSiteId ) ) {
			modernusSiteId = site.customInfo().stringValueForKey( "modernusSiteId" );
		}
		if( !StringUtilities.hasValue( modernusSiteId ) ) {
			modernusSiteId = "NUMER VEFHLUTA";
			return false;
		}

		modernusServiceId = page.customInfo().stringValueForKey( "modernusServiceId" );
		if( !StringUtilities.hasValue( modernusServiceId ) ) {
			modernusServiceId = site.customInfo().stringValueForKey( "modernusServiceId" );
		}
		if( !StringUtilities.hasValue( modernusServiceId ) ) {
			return false;
		}

		modernusSubdomain = page.customInfo().stringValueForKey( "modernusSubdomain" );
		if( !StringUtilities.hasValue( modernusSubdomain ) ) {
			modernusSubdomain = site.customInfo().stringValueForKey( "modernusSubdomain" );
		}
		if( !StringUtilities.hasValue( modernusSubdomain ) ) {
			return false;
		}

		return true;
	}

	public String getSiteId() {
		return modernusSiteId;
	}

	public String getWebDomain() {
		String domain = site.primaryHostName();
		if( domain != null && domain.length() >= 3 && !domain.substring( 0, 3 ).equals( "www" ) ) {
			domain = "www." + domain;
		}
		return domain;
	}

	public String getServiceId() {
		return modernusServiceId;
	}

	public String getPageSymbolOrId() {
		String result = page.symbol();
		if( result == null || result.length() == 0 ) {
			result = page.primaryKey();
		}
		return result;
	}

	public String getSubdomain() {
		return modernusSubdomain;
	}

	public String noScriptUrl() {
		String s = "//" + getSubdomain() + ".teljari.is/potency/potency.php?o=" + getServiceId() + ";i=" + getSiteId() + ";p=" + getPageSymbolOrId();
		return s;
	}
}