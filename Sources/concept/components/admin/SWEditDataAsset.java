package concept.components.admin;

import is.rebbi.core.util.StringUtilities;
import is.rebbi.wo.interfaces.SWDataAsset;
import is.rebbi.wo.util.FileType;
import is.rebbi.wo.util.FileTypes;
import is.rebbi.wo.util.SWCustomInfo;
import is.rebbi.wo.util.SWSettings;
import is.rebbi.wo.util.USDataUtilities;

import java.util.Enumeration;


import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOComponent;
import com.webobjects.appserver.WOContext;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSData;
import com.webobjects.foundation.NSMutableDictionary;

import concept.SWAdminComponent;
import concept.data.SWDocument;
import concept.data.SWPicture;

public class SWEditDataAsset extends SWAdminComponent {

	/**
	 * The name of the file currently being uploaded from disk.
	 */
	public String filename;

	/**
	 * The content data of the picture being uploaded to the server.
	 */
	public NSData data;

	/**
	 * The url to fetch data from.
	 */
	public String url;

	/**
	 * The selected asset to work with
	 */
	private SWDataAsset<?,?> _selectedAsset;

	/**
	 * The asset's entity name
	 */
	public String entityName;

	/**
	 * The entity name of the folders used to store the asset
	 */
	public String folderEntityName;

	/**
	 * The document type currently being iterated through in the list
	 */
	public FileType currentDocumentType;

	public NSMutableDictionary pictureSizesDict = new NSMutableDictionary();
	public NSArray<String> allSizesList;
	public String currentString;
	public int count;

	public SWEditDataAsset( WOContext context ) {
		super( context );
	}

	public NSArray<FileType> documentTypes() {
		return FileTypes.types();
	}

	public void setSelectedAsset( SWDataAsset<?,?> value ) {
		_selectedAsset = value;
		// create a list of all preview sizes
		// and a dictionary for holding selected sizes
		if( isPicture() ) {
			SWPicture pic = (SWPicture)_selectedAsset;
			String allSizes = SWSettings.stringForKey( "pictureSizes" );
			allSizesList = NSArray.componentsSeparatedByString( allSizes, "," );
			String picSizes = (String)pic.customInfo().valueForKey( "sizes" );
			NSArray<String> picSizesList = NSArray.componentsSeparatedByString( picSizes, "," );

			for( int i = 0; i < allSizesList.count(); i++ ) {
				String key = allSizesList.objectAtIndex( i );
				boolean selected = true;
				// if pic has any sizes set then set the dictionary accordingly - else set all to true
				if( picSizesList.count() > 0 && !picSizesList.containsObject( key ) ) {
					selected = false;
				}
				pictureSizesDict.setObjectForKey( selected, key );
			}
		}
	}

	public SWDataAsset<?,?> selectedAsset() {
		return _selectedAsset;
	}

	private String selectedSizesString() {
		String str = "";
		String komma = "";

		for( Enumeration<String> e = allSizesList.objectEnumerator(); e.hasMoreElements(); ) {
			String key = e.nextElement();
			Boolean value = (Boolean)pictureSizesDict.objectForKey( key );
			if( value.booleanValue() ) {
				str += komma + key;
				komma = ",";
			}
		}
		return str;
	}

	/**
	 * Uploads data and saves changes
	 */
	public WOComponent saveChanges() {

		NSData finalData = null;
		String finalName = null;

		if( isPicture() ) {
			SWCustomInfo ci = ((SWPicture)_selectedAsset).customInfo();
			ci.takeValueForKey( selectedSizesString(), "sizes" );
		}

		if( StringUtilities.hasValue( url ) ) {
			finalData = USDataUtilities.readDataFromURL( url );
			finalName = url;
			url = null;
		}

		if( data != null ) {
			if( data.length() != 0 ) {
				finalData = data;
				finalName = filename;
			}
		}

		if( finalData != null ) {
			if( !StringUtilities.hasValue( _selectedAsset.name() ) && StringUtilities.hasValue( finalName ) ) {
				if( isPicture() ) {
					((SWPicture)_selectedAsset).setDisplayName( StringUtilities.fileNameFromPath( finalName ) );
				}
				else {
					_selectedAsset.setName( StringUtilities.fileNameFromPath( finalName ) );
				}
			}

			_selectedAsset.setData( finalData );
			_selectedAsset.setDocumentType( FileTypes.documentTypeFromPath( finalName ) );

		}
		else {
			if( isPicture() ) {
				((SWPicture)_selectedAsset).createPreviews();
			}
		}

		session().defaultEditingContext().saveChanges();

		return null;
	}

	public boolean isZipFile() {
		String name = _selectedAsset.name();

		if( name != null ) {
			return name.toLowerCase().endsWith( "zip" );
		}

		return false;
	}

	public boolean isDocument() {
		return isEntityNamed( SWDocument.ENTITY_NAME );
	}

	public boolean isPicture() {
		return isEntityNamed( SWPicture.ENTITY_NAME );
	}

	private boolean isEntityNamed( String entityName ) {
		return _selectedAsset.entityName().equals( entityName );
	}

	public WOComponent expandZipFile() {
		if( isPicture() ) {
			SWCustomInfo ci = ((SWPicture)_selectedAsset).customInfo();
			ci.takeValueForKey( selectedSizesString(), "sizes" );
		}
		_selectedAsset.expandZip();
		return null;
	}

	public WOComponent editAsText() {
		SWEditDocumentContentAsText nextPage = pageWithName( SWEditDocumentContentAsText.class );
		nextPage.selectedDocument = (SWDocument)_selectedAsset;
		return nextPage;
	}

	public WOActionResults editPicture() {
		SWPreviewPicture nextPage = pageWithName( SWPreviewPicture.class );
		nextPage.setPicture( (SWPicture)_selectedAsset );
		return nextPage;
	}

	public NSArray<String> pictureSizes() {
		String sizes = SWSettings.stringForKey( "pictureSizes" );
		NSArray<String> list = new NSArray<>( sizes.split( "," ) );

		for( int i = 0; i < list.count(); i++ ) {
			pictureSizesDict.setObjectForKey( "true", list.objectAtIndex( i ) );
		}

		return list;
	}

	public Object currentSizeSelection() {
		return pictureSizesDict.objectForKey( currentString );
	}

	public void setCurrentSizeSelection( Object value ) {
		pictureSizesDict.setObjectForKey( value, currentString );
	}

	public boolean hasDataButNoType() {
		boolean yes = false;
		FileType type;

		if( isDocument() ) {
			SWDocument doc = (SWDocument)_selectedAsset;
			type = doc.documentType();
			yes = doc.hasData() && type == null;

		}
		else if( isPicture() ) {
			SWPicture pic = (SWPicture)_selectedAsset;
			type = pic.documentType();
			yes = pic.hasData() && type == null;
		}

		return yes;
	}
}