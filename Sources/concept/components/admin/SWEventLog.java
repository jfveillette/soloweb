package concept.components.admin;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOContext;

import concept.CPAdminComponent;
import concept.data.SWSystemEvent;
import er.extensions.batching.ERXBatchingDisplayGroup;

public class SWEventLog extends CPAdminComponent {

	public ERXBatchingDisplayGroup dg;
	public SWSystemEvent currentObject;

	public SWEventLog( WOContext context ) {
		super( context );
	}

	public WOActionResults search() {
		dg.qualifyDataSource();
		return null;
	}
}