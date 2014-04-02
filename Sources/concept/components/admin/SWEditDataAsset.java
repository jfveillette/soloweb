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
	 * The url to fetch data from.
	 */
	public String url;

	/**
	 * The selected asset to work with
	 */
	private SWDataAsset<?, ?> _selectedAsset;

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

	public void setSelectedAsset( SWDataAsset<?, ?> value ) {
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

	public SWDataAsset<?, ?> selectedAsset() {
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

	private static String filename( String currentName, String url, String uploadedFilename ) {

		if( StringUtilities.hasValue( currentName ) ) {
			return currentName;
		}

		if( StringUtilities.hasValue( url ) ) {
			return StringUtilities.fileNameFromPath( url );
		}

		return uploadedFilename;
	}

	@Override
	public WOComponent saveChanges() {

		if( isPicture() ) {
			SWCustomInfo ci = ((SWPicture)selectedAsset()).customInfo();
			ci.takeValueForKey( selectedSizesString(), "sizes" );
		}

		if( StringUtilities.hasValue( url ) ) {
			selectedAsset().setData( USDataUtilities.readDataFromURL( url ) );
		}

		selectedAsset().setDisplayName( filename( selectedAsset().name(), url, filename ) );
		selectedAsset().updateThumbnails();
		session().defaultEditingContext().saveChanges();

		filename = null;
		url = null;

		return null;
	}

	public boolean isZipFile() {
		String name = selectedAsset().name();

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
		return selectedAsset().entityName().equals( entityName );
	}

	public WOComponent expandZipFile() {
		if( isPicture() ) {
			SWCustomInfo ci = ((SWPicture)selectedAsset()).customInfo();
			ci.takeValueForKey( selectedSizesString(), "sizes" );
		}

		selectedAsset().expandZip();

		return null;
	}

	public WOComponent editAsText() {
		SWEditDocumentContentAsText nextPage = pageWithName( SWEditDocumentContentAsText.class );
		nextPage.selectedDocument = (SWDocument)selectedAsset();
		return nextPage;
	}

	public WOActionResults editPicture() {
		SWPreviewPicture nextPage = pageWithName( SWPreviewPicture.class );
		nextPage.setPicture( (SWPicture)selectedAsset() );
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
		return selectedAsset().hasData() && selectedAsset().documentType() == null;
	}
}