package concept.components;

import is.rebbi.wo.util.USEOUtilities;

import com.webobjects.appserver.WOComponent;
import com.webobjects.appserver.WOContext;
import com.webobjects.appserver.WORequest;
import com.webobjects.foundation.NSArray;

import concept.data.SWPicture;

public class SWECard extends WOComponent {

	public SWPicture selectedPicture;
	public String senderName, receiverName, messageText, kvedjaText = "", pictureID;
	public boolean error = false;
	public NSArray<SWPicture> images;

	public SWECard( WOContext context, WORequest request ) {
		super( context );
		try {
			senderName = request.stringFormValueForKey( "senderName" );
			receiverName = request.stringFormValueForKey( "receiverName" );
			messageText = request.stringFormValueForKey( "text" );
			messageText = messageText.replaceAll( "\n", "\n<br />" );
			kvedjaText = request.stringFormValueForKey( "kvedja" );
			pictureID = request.stringFormValueForKey( "pictureID" );
			selectedPicture = (SWPicture)USEOUtilities.objectWithPK( session().defaultEditingContext(), SWPicture.ENTITY_NAME, Integer.valueOf( pictureID ) );
			error = senderName == null || receiverName == null || messageText == null || selectedPicture == null;
		}
		catch( Exception e ) {
			error = true;
		}
	}

	public void setImages( NSArray<SWPicture> value ) {
		images = value;
	}

	public String pictUrl( int imageNo ) {
		return images.objectAtIndex( imageNo ).emailEmbedURL();
	}

	public String pict1Url() {
		return pictUrl( 0 );
	}

	public String pict2Url() {
		return pictUrl( 1 );
	}

	public String pict3Url() {
		return pictUrl( 2 );
	}

	public String pict4Url() {
		return pictUrl( 3 );
	}

	public String pict5Url() {
		return pictUrl( 4 );
	}

	public String pict6Url() {
		return pictUrl( 5 );
	}

	public String pict7Url() {
		return pictUrl( 6 );
	}

	public String pict8Url() {
		return pictUrl( 7 );
	}

	public String pict9Url() {
		return pictUrl( 8 );
	}
}