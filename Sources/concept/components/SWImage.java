package concept.components;

import com.webobjects.appserver.WOContext;

import concept.SWGenericComponent;
import concept.data.SWPicture;

public class SWImage extends SWGenericComponent {

	public SWImage( WOContext context ) {
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

	@Override
	public void reset() {}

	public String pictureURL() {
		String url = null;

		SWPicture pic = (SWPicture)valueForBinding( "selectedPicture" );

		if( pic == null ) {
			Integer pictureID = (Integer)valueForBinding( "selectedPictureID" );

			if( pictureID != null && pictureID.intValue() > 0 ) {
				pic = SWPicture.pictureWithID( session().defaultEditingContext(), pictureID );
			}
		}

		if( pic != null ) {
			url = pic.previewURL( (String)valueForBinding( "size" ) ); // returns original if size is not found
		}

		return url;
	}
}