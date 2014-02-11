package concept.components.admin;


import com.webobjects.appserver.WOComponent;
import com.webobjects.appserver.WOContext;
import com.webobjects.eoaccess.EOUtilities;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSTimestamp;

import concept.SWAdminComponent;
import concept.data.SWNewsItem;
import concept.search.SWLuceneUtilities;

public class SWEditNewsItem extends SWAdminComponent {

	private SWNewsItem _selectedAsset;
	public String extraCategoryIDs;
	public SWNewsItem extraItem;
	public boolean editNewsText = true;

	public SWEditNewsItem( WOContext context ) {
		super( context );
	}

	public SWNewsItem selectedAsset() {
		return _selectedAsset;
	}

	public void setSelectedAsset( SWNewsItem newSelectedAsset ) {
		if( _selectedAsset != newSelectedAsset ) {
			_selectedAsset = newSelectedAsset;

			NSArray items = this.extraItems();

			String ids = "";
			SWNewsItem item;

			for( int i = 0; i < items.count(); i++ ) {
				item = (SWNewsItem)items.objectAtIndex( i );

				if( i > 0 ) {
					ids += " ";
				}

				ids += item.category().primaryKey();
			}

			if( _selectedAsset.date() == null ) {
				_selectedAsset.setDate( new NSTimestamp() );
			}

			extraCategoryIDs = ids;
			editNewsText = true;
		}
	}

	public NSArray extraItems() {
		NSArray items = EOUtilities.objectsMatchingKeyAndValue( session().defaultEditingContext(), SWNewsItem.ENTITY_NAME, SWNewsItem.ORIGINAL_ITEM_ID_KEY, Integer.valueOf( _selectedAsset.primaryKey() ) );
		return items;
	}

	public WOComponent saveChanges() {
		if( extraCategoryIDs != null && !extraCategoryIDs.equals( "" ) ) {
			selectedAsset().deleteCopies();
			selectedAsset().createCopiesWithCategoryIDs( extraCategoryIDs );
		}
		session().defaultEditingContext().saveChanges();
		SWLuceneUtilities.indexObject( null, _selectedAsset );
		return null;
	}

	public WOComponent toggleEditNewsOrExcerptText() {
		editNewsText = !editNewsText;
		return null;
	}

	public String catSelWinUrl() {
		String url = context().directActionURLForActionNamed( "openNewsCategoriesSelect", null );
		url += "?extraCategories=";
		return url;
	}
}