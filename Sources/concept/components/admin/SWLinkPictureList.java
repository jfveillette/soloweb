package concept.components.admin;

import is.rebbi.core.util.StringUtilities;
import is.rebbi.wo.util.SWCustomInfo;

import java.util.Enumeration;


import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOComponent;
import com.webobjects.appserver.WOContext;
import com.webobjects.appserver.WOResponse;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableDictionary;

import concept.data.SWAssetFolder;
import concept.data.SWComponent;
import concept.data.SWNewsItem;
import concept.data.SWPicture;
import concept.data.SWPictureLink;

import er.extensions.components.ERXComponent;

public class SWLinkPictureList extends ERXComponent {

	// used when embedded in Butur editing
	public SWComponent currentComponent;
	// used when embedded in News editing
	public SWNewsItem newsItem;

	public SWPictureLink currentPictureLink;

	public SWLinkPictureList( WOContext context ) {
		super( context );
	}

	public static final String PICTURE_COUNT_KEY = "pictureCount";

	private String _placeHolder = ""; // the next [Mynd x] to be added
	private int placeHolderCount = 0; // the counter for [Mynd x]

	/**
	 * A dict sent to SWAssetManagement to
	 * hold a single key value pair where key is the picture placeholder [Mynd x]
	 * and value is the picture or null if no picture was selected
	 */
	private NSMutableDictionary link = new NSMutableDictionary();

	/**
	 * Construct a new placeholder; used by javascript and browsePictures()
	 */
	public String placeHolder() {
		placeHolderCount = 0;
		Object picCount = customInfo().valueForKey( PICTURE_COUNT_KEY );
		if( picCount != null && !"".equals( picCount ) ) {
			placeHolderCount = new Integer( picCount.toString() ).intValue();
		}
		placeHolderCount++;
		_placeHolder = "[Mynd " + placeHolderCount + "]";
		return _placeHolder;
	}

	public WOComponent browsePictures() {
		/*SWPictureLink link = new SWPictureLink();
		link.setName( _placeHolder );
		link.setAlign(0);
		//link.setComponent(currentComponent);
		ec.insertObject(link);
		link.addObjectToBothSidesOfRelationshipWithKey( currentComponent, "component" );
		//ec.saveChanges();
		*/

		SWAssetManagement nextPage = pageWithName( SWAssetManagement.class );
		nextPage.setEntityName( SWPicture.ENTITY_NAME );
		nextPage.setFolderEntityName( SWAssetFolder.ENTITY_NAME );
		nextPage.setEditingComponentName( SWEditDataAsset.class.getName() );
		nextPage.componentToReturn = context().page();
		nextPage.record = link; // NB link is a dictionary - see comment at top
		nextPage.fieldName = _placeHolder;
		nextPage.useID = false;

		return nextPage;
	}

	public WOActionResults removeCurrentPictureLink() {
		if( isComponent() ) {
			String txt = currentComponent.textTwo();
			if( StringUtilities.hasValue( txt ) ) {
				txt = txt.replace( currentPictureLink.name(), "" );
				currentComponent.setTextTwo( txt );
			}
			currentPictureLink.removeObjectFromBothSidesOfRelationshipWithKey( currentComponent, "component" );

		}
		else {
			String txt = newsItem.text();
			if( StringUtilities.hasValue( txt ) ) {
				txt = txt.replace( currentPictureLink.name(), "" );
				newsItem.setText( txt );
			}
			currentPictureLink.removeObjectFromBothSidesOfRelationshipWithKey( newsItem, "newsItem" );
		}
		session().defaultEditingContext().deleteObject( currentPictureLink );
		session().defaultEditingContext().saveChanges();
		return context().page();
	}

	@Override
	public void appendToResponse( WOResponse r, WOContext c ) {
		if( isComponent() ) {
			String s = currentComponent.templateName();

			if( !"ButurTemplate001".equals( s ) && !"ButurTemplate002".equals( s ) && !"ButurTemplate003".equals( s ) && !"ButurTemplate004".equals( s ) ) {
				currentComponent.setTemplateName( "ButurTemplate004" );
			}
		}
		// add new picture link if link dictionary has picture value
		// using a loop though the link should only contain one key/value
		for( Enumeration<String> e = link.keyEnumerator(); e.hasMoreElements(); ) {
			String key = e.nextElement();
			SWPicture pic = (SWPicture)link.objectForKey( key );
			if( pic != null ) {
				SWPictureLink link = new SWPictureLink();
				session().defaultEditingContext().insertObject( link );
				link.setName( key );
				link.setAlign( 0 );
				link.setPicture( pic );
				link.setLinkToLarge( 0 );
				//link.setComponent(currentComponent);
				if( isComponent() ) {
					link.addObjectToBothSidesOfRelationshipWithKey( currentComponent, "component" );
				}
				else {
					link.addObjectToBothSidesOfRelationshipWithKey( newsItem, "newsItem" );
				}
				customInfo().takeValueForKey( placeHolderCount, PICTURE_COUNT_KEY );

			}
			else { // pic selection canceled - remove the placeholder from text
				if( isComponent() ) {
					String txt = currentComponent.textTwo();
					txt = txt.replace( key, "" );
					currentComponent.setTextTwo( txt );
				}
				else {
					String txt = newsItem.text();
					txt = txt.replace( key, "" );
					newsItem.setText( txt );
				}
			}
		}
		link = new NSMutableDictionary();
		super.appendToResponse( r, c );
	}

	private boolean isComponent() {
		boolean yes = currentComponent != null;
		return yes;
	}

	private boolean isNewsItem() {
		boolean yes = newsItem != null;
		return yes;
	}

	public SWCustomInfo customInfo() {
		if( isComponent() ) {
			return currentComponent.customInfo();
		}
		else {
			return newsItem.customInfo();
		}
	}

	public NSArray swPictureLinks() {
		if( isComponent() ) {
			return currentComponent.swPictureLinks();
		}
		else {
			return newsItem.swPictureLinks();
		}
	}
}