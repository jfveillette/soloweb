package concept.tasks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.foundation.NSArray;

import concept.data.SWDocument;
import er.extensions.eof.ERXEC;

public class GenerateThumbnails extends SWTask {

	private static final Logger logger = LoggerFactory.getLogger( GenerateThumbnails.class );

	@Override
	public void run() {
		EOEditingContext ec = ERXEC.newEditingContext();
		NSArray<SWDocument> pictures = SWDocument.fetchAllSWDocuments( ec );

		int count = pictures.count();

		for( SWDocument picture : pictures ) {
			picture.updateThumbnails();

			if( count-- % 100 == 0 ) {
				logger.info( "Remaining: " + count );
			}
		}

		logger.info( "Done resizing images!" );
	}
}