package concept.components.admin;

import java.util.Enumeration;


import com.webobjects.appserver.WOComponent;
import com.webobjects.appserver.WOContext;
import com.webobjects.foundation.NSArray;

import concept.data.SWPage;

public class SWEditPagePublishingOptions extends WOComponent {

	public SWPage selectedPage;

	public SWEditPagePublishingOptions( WOContext context ) {
		super( context );
	}

	public WOComponent applyPasswordToSubPages() {
		NSArray a = selectedPage.everySubPage();
		Enumeration e = a.objectEnumerator();

		while( e.hasMoreElements() ) {
			SWPage p = (SWPage)e.nextElement();
			p.setPassword( selectedPage.password() );
		}

		session().defaultEditingContext().saveChanges();

		return null;
	}
}