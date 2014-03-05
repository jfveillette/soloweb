package concept.data;

import is.rebbi.core.util.StringUtilities;
import is.rebbi.wo.interfaces.HasFakeRelationship;
import is.rebbi.wo.interfaces.SWAsset;
import is.rebbi.wo.interfaces.SWCopyable;
import is.rebbi.wo.interfaces.SWHasCustomInfo;
import is.rebbi.wo.interfaces.SWTimedContent;
import is.rebbi.wo.interfaces.SWUserInterface;
import is.rebbi.wo.interfaces.TimeStamped;
import is.rebbi.wo.interfaces.UserStamped;
import is.rebbi.wo.util.SWCustomInfo;
import is.rebbi.wo.util.SWTimedContentUtilities;
import is.rebbi.wo.util.USEOUtilities;
import is.rebbi.wo.util.USUtilities;

import java.util.Enumeration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.webobjects.appserver.WOContext;
import com.webobjects.eoaccess.EOUtilities;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOEnterpriseObject;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSTimestamp;

import concept.components.client.SWNewsItemSearchResult;
import concept.components.client.SWPictureInsert;
import concept.data.auto._SWNewsItem;
import concept.search.SWSearchItem;
import concept.util.Documents;
import concept.util.HumanReadable;
import er.extensions.appserver.ERXWOContext;

public class SWNewsItem extends _SWNewsItem implements SWTimedContent, SWAsset<SWNewsItem, SWNewsCategory>, SWSearchItem, SWHasCustomInfo, SWCopyable<SWNewsItem>, TimeStamped, UserStamped, HumanReadable  {

	private static final Logger logger = LoggerFactory.getLogger( SWNewsItem.class );

	private static String flash = "<object classid=\"clsid:d27cdb6e-ae6d-11cf-96b8-444553540000\" align=\"middle\" id=\"SWGallery\" " + "codebase=\"http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=7,0,0,0\" " + "PWIDTH PHEIGHT>\n" + "<param name=\"allowScriptAccess\" value=\"sameDomain\" />\n" + "<param name=\"movie\" value=\"http://www.lausn.is/doc/2122?url=PURL\">\n" + "<param name=\"quality\" value=\"high\" />\n" + "<param name=\"bgcolor\" value=\"#ffffff\" />\n" + "<embed name=\"SWGallery\" type=\"application/x-shockwave-flash\" align=\"middle\" allowScriptAccess=\"sameDomain\" \n" + "pluginspage=\"http://www.macromedia.com/go/getflashplayer\" bgcolor=\"#ffffff\" quality=\"high\" PWIDTH PHEIGHT \n" + "src=\"http://www.lausn.is/doc/2122?url=PURL\">\n" + "</object>\n";

	private SWCustomInfo _customInfo;

	public SWCustomInfo customInfo() {
		if( _customInfo == null ) {
			_customInfo = new SWCustomInfo( this );
		}

		return _customInfo;
	}

	@Override
	public void awakeFromInsertion( EOEditingContext anEC ) {
		super.awakeFromInsertion( anEC );
		setDate( new NSTimestamp() );
	}

	public NSArray<SWDocument> documents() {
		return Documents.relatedDocuments( this );
	}

	public NSArray<SWComment> comments() {
		return HasFakeRelationship.Util.relatedObjects( SWComment.class, this, SWComment.DATE.descs() );
	}

	/**
	 * The newsitem's text
	 */
	@Override
	public String text() {
		String s = super.text();

		if( StringUtilities.hasValue( s ) ) {
			s = replaceSWTags( s );

			WOContext context = ERXWOContext.newContext();

			for( Enumeration<SWPictureLink> e = swPictureLinks().objectEnumerator(); e.hasMoreElements(); ) {
				SWPictureLink link = e.nextElement();
				SWPictureInsert insert = new SWPictureInsert( context );
				insert.link = link;
				String html = insert.generateResponse().contentString();
				s = s.replace( link.name(), html );
			}
		}

		return s;
	}

	public boolean hasText() {
		String s = super.text();
		return (s != null && s.length() > 0);
	}

	/**
	 * The newsitem's text without SWTags replacements
	 */
	public String textWithoutSWTagReplace() {
		return super.text();
	}

	/**
	 * Sets the newsitem's text
	 */
	public void setTextWithoutSWTagReplace( String value ) {
		setText( value );
	}

	@Override
	public void setSearchItemContents( String contents ) {}

	@Override
	public String searchItemText() {
		return "";
	}

	/**
	 * The newsitem's excerpt text
	 */
	@Override
	public String excerpt() {
		String s = super.excerpt();

		if( StringUtilities.hasValue( s ) ) {
			s = replaceSWTags( s );

			WOContext context = ERXWOContext.newContext();

			for( Enumeration<SWPictureLink> e = swPictureLinks().objectEnumerator(); e.hasMoreElements(); ) {
				SWPictureLink link = e.nextElement();
				SWPictureInsert insert = new SWPictureInsert( context );
				insert.link = link;
				String html = insert.generateResponse().contentString();
				s = s.replace( link.name(), html );
			}
		}

		return s;
	}

	public boolean hasExcerpt() {
		String excerpt = this.excerpt();
		return (excerpt != null && excerpt.length() > 0);
	}

	/**
	 * Returns picture2 if there is one, otherwise picture
	 */
	public SWPicture picture2OrPicture() {
		SWPicture pict = picture2();

		if( pict == null ) {
			pict = picture();
		}

		return pict;
	}

	public boolean hasAuthor() {
		String auth = this.author();
		return (auth != null && auth.length() > 0);
	}

	/**
	 * Indicates if a news item is an original item or a copy of another item.
	 * @return TRUE if the item is an original
	 */
	public boolean isOriginalItem() {
		Integer oid = this.originalItemID();
		return (oid == null || oid.intValue() == 0);
	}

	/**
	 * Returns the newsItem's excerpt with breaks encoded to BR-tags
	 */
	public String excerptWithBreaks() {
		return StringUtilities.convertBreakString( excerpt() );
	}

	/**
	 * Returns the newsItem's text with breaks encoded to BR-tags
	 */
	public String textWithBreaks() {
		return StringUtilities.convertBreakString( text() );
	}

	/**
	 * Indicates if this object has been published, and if it's display time has come
	 */
	public boolean isPublished() {
		return USUtilities.numberIsTrue( published() ) && isTimeValid();
	}

	/**
	 * Indicates if the text of this newsitem should be break encoded
	 */
	public boolean shouldEncodeBreaks() {
		return USUtilities.numberIsTrue( encodeBreaks() );
	}

	public SWNewsCategory topLevelCategory() {
		SWNewsCategory top = this.category();
		while( top != null && top.parentFolder() != null ) {
			top = top.parentFolder();
		}
		return top;
	}

	/**
	 * Implementation of SWTransferable
	 */
	@Override
	public void transferOwnership( EOEnterpriseObject newOwner ) {
		this.removeObjectFromBothSidesOfRelationshipWithKey( category(), "category" );
		this.addObjectToBothSidesOfRelationshipWithKey( newOwner, "category" );
	}

	/**
	 * Indicates if this object's display time has come and has not expired
	 */
	@Override
	public boolean isTimeValid() {
		return SWTimedContentUtilities.validateDisplayTime( this );
	}

	public boolean hasPicture() {
		return picture() != null;
	}

	public boolean hasPicture2() {
		return picture2() != null;
	}

	public boolean hasCustomMoreURL() {
		return customMoreURL() != null && customMoreURL() != "";
	}

	@Override
	public void deleteAsset() {
		editingContext().deleteObject( this );
	}

	@Override
	public String name() {
		return heading();
	}

	@Override
	public void setName( String s ) {
		setHeading( s );
	}

	@Override
	public SWNewsCategory containingFolder() {
		return category();
	}

	@Override
	public void setContainingFolder( SWNewsCategory folder ) {
		if( this.category() != null ) {
			this.removeObjectFromBothSidesOfRelationshipWithKey( this.category(), "category" );
		}

		this.addObjectToBothSidesOfRelationshipWithKey( folder, "category" );
	}

	public void createCopiesWithCategoryIDs( String newCategoryIds ) {
		if( newCategoryIds == null || newCategoryIds.equals( "" ) ) {
			return;
		}

		NSArray ids = NSArray.componentsSeparatedByString( newCategoryIds, " " );
		Enumeration iter = ids.objectEnumerator();
		Integer newCategoryId;
		SWNewsCategory folder;
		while( iter.hasMoreElements() ) {
			try {
				newCategoryId = Integer.valueOf( (String)iter.nextElement() );
				folder = (SWNewsCategory)USEOUtilities.objectWithPK( editingContext(), SWNewsCategory.ENTITY_NAME, newCategoryId );

				if( folder != null ) {
					SWNewsItem newItem = new SWNewsItem();
					editingContext().insertObject( newItem );
					newItem.setAuthor( this.author() );
					newItem.setDate( this.date() );
					newItem.setEncodeBreaks( this.encodeBreaks() );
					newItem.setExcerpt( this.excerpt() );
					newItem.setHeading( this.heading() );
					newItem.setOriginalItemID( Integer.parseInt( this.primaryKey() ) );
					newItem.setPicture( this.picture() );
					newItem.setPicture2( this.picture2() );
					newItem.setPublished( this.published() );
					newItem.setText( this.text() );
					newItem.setTimeIn( this.timeIn() );
					newItem.setTimeOut( this.timeOut() );
					newItem.setContainingFolder( folder );
					newItem.setCustomInfoData( this.customInfoData() );
				}
			}
			catch( Exception ex ) {
				// Nothing to do here
			}
		}
	}

	public void deleteCopies() {
		NSArray items = EOUtilities.objectsMatchingKeyAndValue( editingContext(), SWNewsItem.ENTITY_NAME, SWNewsItem.ORIGINAL_ITEM_ID_KEY, Integer.valueOf( this.primaryKey() ) );
		if( items != null && items.count() > 0 ) {
			Enumeration iter = items.objectEnumerator();
			SWNewsItem item;
			while( iter.hasMoreElements() ) {
				item = (SWNewsItem)iter.nextElement();
				item.setContainingFolder( null );
				editingContext().deleteObject( item );
			}
		}
	}

	@Override
	public SWNewsItem createCopy() {
		SWNewsItem nc = new SWNewsItem();
		editingContext().insertObject( nc );

		nc.setDate( date() );
		nc.setExcerpt( excerpt() );
		nc.setHeading( heading() );
		nc.setPicture( picture() );
		nc.setPublished( published() );
		nc.setText( text() );
		nc.setEncodeBreaks( encodeBreaks() );
		nc.setTimeIn( timeIn() );
		nc.setTimeOut( timeOut() );

		return nc;
	}

	@Override
	public String searchResultComponentName() {
		return SWNewsItemSearchResult.class.getSimpleName();
	}

	@Override
	public boolean isValidResult() {
		return USUtilities.booleanFromObject( published() );
	}

	public NSArray allParentCategoryIDs() {
		NSMutableArray ids = new NSMutableArray();
		SWNewsCategory cat = this.category();
		while( cat != null ) {
			ids.addObject( cat.id().intValue() );
			cat = cat.parentFolder();
		}
		return ids;
	}

	private static String replaceSWTags( String theText ) {
		String newString = theText;

		try {
			if( theText != null && theText.length() > 0 && theText.indexOf( "/myndasyning/" ) > -1 ) {
				StringBuffer sbuf = new StringBuffer( theText );
				int pos = theText.indexOf( "/myndasyning/" );

				// Find the start and end of the tag
				int tstart, tend;
				tstart = pos;
				while( tstart >= 0 && sbuf.charAt( tstart ) != '<' ) {
					tstart--;
				}
				tend = sbuf.indexOf( "</a>", pos ) + 4;

				// Get the parameters
				int hstart, hend;
				hstart = pos;
				while( hstart >= tstart && sbuf.charAt( hstart ) != '"' ) {
					hstart--;
				}
				hend = sbuf.indexOf( "\"", pos );
				String paramStr = sbuf.substring( hstart + 1, hend );

				// Parse the parameter list
				paramStr = paramStr.substring( paramStr.indexOf( '/', 1 ) + 1 );
				NSArray params = NSArray.componentsSeparatedByString( paramStr, "," );
				NSArray parama;
				String key, val;
				int id = 0, hsize = 0, vsize = 0, delay = 0;
				String sort = null;
				String description = "true";
				Enumeration iter = params.objectEnumerator();
				while( iter.hasMoreElements() ) {
					parama = NSArray.componentsSeparatedByString( (String)iter.nextElement(), "=" );
					key = (String)parama.objectAtIndex( 0 );
					val = (String)parama.objectAtIndex( 1 );
					if( "id".equals( key ) ) {
						id = integerFromObject( val, 0 ).intValue();
					}
					else if( "x".equals( key ) ) {
						hsize = integerFromObject( val, 0 ).intValue();
					}
					else if( "y".equals( key ) ) {
						vsize = integerFromObject( val, 0 ).intValue();
					}
					else if( "del".equals( key ) ) {
						delay = integerFromObject( val, 0 ).intValue();
					}
					else if( "sort".equals( key ) ) {
						sort = USUtilities.stringFromObject( val );
					}
					else if( "text".equals( key ) ) {
						description = USUtilities.stringFromObject( val );
					}
				}

				// Build the url
				String pm = "&amp;folderID=" + id;
				pm += "&amp;hold=" + delay;

				if( delay == 0 ) {
					pm += "&amp;control=manual";
					pm += "&amp;transition=";
				}
				else {
					pm += "&amp;control=auto";
					pm += "&amp;transition=dissolve";
				}
				if( StringUtilities.hasValue( sort ) ) {
					pm += "&amp;sort=" + sort;
				}

				if( StringUtilities.hasValue( description ) ) {
					pm += "&amp;description=" + description;
				}

				String url = ERXWOContext.newContext().directActionURLForActionNamed( "swPictureFolder", null ) + pm;

				// Build a pictureshow with the parameters
				String pictShow = flash;
				if( hsize > 0 ) {
					pictShow = pictShow.replaceAll( "PWIDTH", "width=\"" + hsize + "\"" );
				}
				else {
					pictShow = pictShow.replaceAll( "PWIDTH", "" );
				}
				if( vsize > 0 ) {
					pictShow = pictShow.replaceAll( "PHEIGHT", "height=\"" + vsize + "\"" );
				}
				else {
					pictShow = pictShow.replaceAll( "PHEIGHT", "" );
				}
				pictShow = pictShow.replaceAll( "PURL", url );

				// Replace the a href with the pictShow
				sbuf.replace( tstart, tend, pictShow );
				newString = sbuf.toString();
			}
		}
		catch( Exception ex ) {
			logger.error( "Exception while building flash gallery for: " + theText, ex );
		}

		return newString;
	}

	/**
	 * Finds out the value of Object and attempts to coerce it's value to an Integer
	 */
	private static Integer integerFromObject( Object o, Integer defaultValue ) {
		Integer value = USUtilities.integerFromObject( o );
		return (value != null ? value : defaultValue);
	}

	@Override
	public String toStringHuman() {
		return name();
	}

	@Override
	public void setCreatedBy( SWUserInterface user ) {
		super.setCreatedBy( (SWUser)user );

	}

	@Override
	public void setModifiedBy( SWUserInterface user ) {
		super.setModifiedBy( (SWUser)user );
	}
}