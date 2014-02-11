package concept.components.client;

import is.rebbi.core.util.StringUtilities;
import is.rebbi.wo.util.USEOUtilities;
import is.rebbi.wo.util.USUtilities;

import com.webobjects.appserver.WOComponent;
import com.webobjects.appserver.WOContext;
import com.webobjects.eoaccess.EOUtilities;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSData;
import com.webobjects.foundation.NSMutableDictionary;
import com.webobjects.foundation.NSTimestamp;
import com.webobjects.foundation.NSTimestampFormatter;

import concept.SWGenericComponent;
import concept.data.SWAssetFolder;
import concept.data.SWNewsCategory;
import concept.data.SWNewsItem;
import concept.data.SWPicture;
import concept.deprecated.SWImageMagick;
import concept.util.SWPictureUtilities;
import concept.util.SWStringUtilities;

public class SWNewsSubmit extends SWGenericComponent {

	public NSMutableDictionary data;
	public boolean doingSubmit = true;
	public int maxChars = 800;
	public int tmpCounter = maxChars;
	public String errorMessage;
	public SWNewsItem itemToEdit = null;

	public SWNewsSubmit( WOContext context ) {
		super( context );

		data = new NSMutableDictionary();

		// See if we are editing rather than inserting
		SWNewsItem newsItem = null;
		if( StringUtilities.hasValue( context.request().stringFormValueForKey( "detail" ) ) ) {
			// Get the news item specified in the url
			Integer newsItemId = USUtilities.integerFromObject( context.request().stringFormValueForKey( "detail" ) );
			newsItem = (SWNewsItem)USEOUtilities.objectWithPK( session().defaultEditingContext(), SWNewsItem.ENTITY_NAME, newsItemId );
			this.setItemToEdit( newsItem );
		}
		else {
			// Default date to today
			NSTimestampFormatter tsf = new NSTimestampFormatter( "%d.%m.%Y" );
			data.setObjectForKey( tsf.format( new NSTimestamp() ), "date" );
		}

		errorMessage = null;
	}

	public WOComponent saveChanges() {
		String title = (String)data.valueForKey( "title" );
		String date = (String)data.valueForKey( "date" );
		String time = (String)data.valueForKey( "time" );
		String author = (String)data.valueForKey( "author" );
		String excerpt = (String)data.valueForKey( "excerpt" );
		String text = (String)data.valueForKey( "text" );
		String weburl = (String)data.valueForKey( "weburl" );
		NSData picture = (NSData)data.valueForKey( "mynd" );
		SWPicture swpict = null;
		String cats = currentComponent().customInfo().stringValueForKey( "categoryIDs" );
		NSArray categoryIds = NSArray.componentsSeparatedByString( cats, " " );
		Integer newsCategoryID = Integer.valueOf( (String)categoryIds.objectAtIndex( 0 ) );
		EOEditingContext ec = session().defaultEditingContext();

		errorMessage = null;

		// Check for missing data
		if( !StringUtilities.hasValue( title ) ) {
			errorMessage = "Heiti vantar !";
		}
		else if( !StringUtilities.hasValue( date ) ) {
			errorMessage = "Dagsetningu vantar !";
		}
		else if( !StringUtilities.hasValue( text ) ) {
			errorMessage = "Texta vantar !";
		}
		if( errorMessage != null ) {
			return null;
		}

		// Parse the date/time
		NSTimestamp ts = null;
		if( StringUtilities.hasValue( time ) ) {
			ts = SWStringUtilities.stringToTimestamp( date + " " + time, "%d.%m.%Y %H:%M" );
		}
		else {
			ts = SWStringUtilities.stringToTimestamp( date, "%d.%m.%Y" );
		}
		if( ts == null ) {
			errorMessage = "Dagsetning og/e&eth;a t&iacute;mi er ekki &aacute; leyfilegu formi !";
			return null;
		}

		// Get the folder the new item is to be inserted into
		SWNewsCategory newsFolder = (SWNewsCategory)USEOUtilities.objectWithPK( ec, SWNewsCategory.ENTITY_NAME, newsCategoryID );
		if( newsFolder == null ) {
			errorMessage = "Fr&eacute;ttamappa fannst ekki !";
			return null;
		}

		// Create the picture
		if( picture != null && picture.length() > 0 ) {
			// Scale to max 250 by 175 pixels
			SWImageMagick im = new SWImageMagick( picture );
			if( im.scaleImage( 250, 175 ) ) {
				picture = im.getDataAsNSData();
			}
			else {
				picture = null;
			}
			im = null;

			if( picture != null ) {
				Integer folderId = currentComponent().customInfo().integerValueForKey( "newsReg_PictureFolderId" );
				SWAssetFolder pictFolder = (SWAssetFolder)USEOUtilities.objectWithPK( ec, SWAssetFolder.ENTITY_NAME, folderId );
				swpict = (SWPicture)EOUtilities.createAndInsertInstance( ec, SWPicture.ENTITY_NAME );
				swpict.setData( picture );
				swpict.setName( title );
				pictFolder.addObjectToBothSidesOfRelationshipWithKey( swpict, "pictures" );
			}
		}

		// Create the news item if we are not editing
		SWNewsItem item = itemToEdit;
		if( item == null ) {
			item = (SWNewsItem)EOUtilities.createAndInsertInstance( ec, SWNewsItem.ENTITY_NAME );
		}

		// Set the data fields
		item.setHeading( title );
		item.setDate( ts );
		item.setExcerpt( excerpt );
		if( itemToEdit == null && weburl != null && weburl.length() > 0 ) {
			String dispurl = weburl;

			// Add the url after the text
			if( weburl.indexOf( "http://" ) == -1 ) {
				weburl = "http://" + weburl;
			}
			text = text + "\r\n\r\n<a href=\"" + weburl + "\">" + dispurl + "</a>";
		}
		item.setText( text );
		item.addObjectToBothSidesOfRelationshipWithKey( swpict, "picture" );
		item.addObjectToBothSidesOfRelationshipWithKey( swpict, "picture2" );
		if( currentComponent().customInfo().booleanValueForKey( "newItemAutoPublished" ) == false ) {
			item.setPublished( new Integer( 0 ) );
		}
		else {
			item.setPublished( new Integer( 1 ) );
		}
		item.setEncodeBreaks( new Integer( 1 ) );
		item.setAuthor( author );

		/*
		if( loggedInCrmUser != null ) {
			item.setCrmNafnID( loggedInCrmUser.getNafnId() );
		}
		*/

		newsFolder.addObjectToBothSidesOfRelationshipWithKey( item, "news" );

		// Save the news item
		ec.saveChanges();

		// Resize the picture and re-save
		if( swpict != null ) {
			int width = swpict.width();
			int height = swpict.height();
			double wper = 250.0 / width;
			double hper = 175.0 / height;
			if( wper < 1.0 || hper < 1.0 ) {
				SWPictureUtilities.scale( swpict, (int)((wper < hper ? wper : hper) * 100.0), 90 );
				ec.saveChanges();
			}
		}

		// Cleanup
		data = null;
		doingSubmit = false;
		errorMessage = null;

		return null;
	}

	public SWNewsItem getItemToEdit() {
		return itemToEdit;
	}

	public void setItemToEdit( SWNewsItem theItem ) {
		itemToEdit = theItem;
		if( itemToEdit != null ) {
			data.setObjectForKey( itemToEdit.heading(), "title" );
			data.setObjectForKey( itemToEdit.date(), "date" );
			data.setObjectForKey( itemToEdit.date(), "time" );
			data.setObjectForKey( itemToEdit.author(), "author" );
			data.setObjectForKey( itemToEdit.excerpt(), "excerpt" );
			data.setObjectForKey( itemToEdit.text(), "text" );
		}
	}
}