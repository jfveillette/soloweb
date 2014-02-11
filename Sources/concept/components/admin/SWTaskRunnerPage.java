package concept.components.admin;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOContext;
import com.webobjects.foundation.NSMutableArray;

import concept.tasks.SWTask;
import er.extensions.components.ERXComponent;

public class SWTaskRunnerPage extends ERXComponent {

	public Class currentTaskClass;

	public SWTaskRunnerPage( WOContext context ) {
		super( context );
	}

	public NSMutableArray<Class<? extends SWTask>> taskClasses() {
		return SWTask.taskClasses();
	}

	public WOActionResults run() {
		SWTask.runTaskOfClass( currentTaskClass );
		return null;
	}
}