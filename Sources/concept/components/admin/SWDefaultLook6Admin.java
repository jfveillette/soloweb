package concept.components.admin;

import is.rebbi.wo.util.SWCustomInfo;

import com.webobjects.appserver.WOComponent;
import com.webobjects.appserver.WOContext;

import concept.data.SWPage;
import concept.data.SWSite;

public class SWDefaultLook6Admin extends WOComponent {

	public SWCustomInfo customInfo;

	private SWPage selectedPage;
	private SWSite selectedSite;

	public SWDefaultLook6Admin( WOContext context ) {
		super( context );
	}

	public SWSite getSelectedSite() {
		return selectedSite;
	}

	public void setSelectedSite( SWSite site ) {
		selectedSite = site;
		customInfo = site.customInfo();
	}

	public SWPage getSelectedPage() {
		return selectedPage;
	}

	public void setSelectedPage( SWPage page ) {
		selectedPage = page;
		customInfo = page.customInfo();
	}
}