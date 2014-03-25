package concept.tasks;

import com.webobjects.foundation.NSMutableArray;

/**
 * Everyone needs to run some tasks at some point.
 */

public abstract class SWTask {

	private static NSMutableArray<Class<? extends SWTask>> _taskClasses;

	public static NSMutableArray<Class<? extends SWTask>> taskClasses() {
		_taskClasses = null;

		if( _taskClasses == null ) {
			 _taskClasses = new NSMutableArray<>();
			 _taskClasses.addObject( GenerateIndex.class );
			 _taskClasses.addObject( GenerateSynonymIndex.class );
			 _taskClasses.addObject( GenerateOldIndex.class );
			 _taskClasses.addObject( GenerateThumbnails.class );
		}

		return _taskClasses;
	}

	public abstract void run();

	public static void runTaskOfClass( Class<? extends SWTask> taskClass ) {
		try {
			SWTask c = taskClass.newInstance();
			c.run();
		}
		catch( InstantiationException e ) {
			throw new RuntimeException( e );
		}
		catch( IllegalAccessException e ) {
			throw new RuntimeException( e );
		}
	}
}