package concept.components.admin.edit;

import com.webobjects.appserver.WOContext;

import concept.ViewPage;
import concept.data.SWNewsItem;

/**
 * For editing of news items.
 */

public class CPEditNewsItem extends ViewPage<SWNewsItem> {

	public CPEditNewsItem( WOContext context ) {
		super( context );
	}
}