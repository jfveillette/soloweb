package concept.components.client;

import is.rebbi.wo.util.USArrayUtilities;

import com.webobjects.appserver.WOContext;

import concept.SWBaseComponent;
import concept.data.SWComment;
import concept.data.SWNewsItem;

/**
 * The component displayed in the news list.
 */

public class SWCommentInfo extends SWBaseComponent {

	public SWCommentInfo( WOContext context ) {
		super( context );
	}

	@Override
	public boolean synchronizesVariablesWithBindings() {
		return false;
	}

	@Override
	public boolean isStateless() {
		return true;
	}

	public SWNewsItem selectedNewsItem() {
		return (SWNewsItem)valueForBinding( "selectedNewsItem" );
	}

	public SWComment lastComment() {
		return USArrayUtilities.hasObjects( selectedNewsItem().comments() ) ? selectedNewsItem().comments().lastObject() : null;
	}
}