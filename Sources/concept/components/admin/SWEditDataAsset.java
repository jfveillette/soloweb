package concept.components.admin;

import is.rebbi.core.util.StringUtilities;
import is.rebbi.wo.interfaces.SWDataAsset;
import is.rebbi.wo.util.FileType;
import is.rebbi.wo.util.FileTypes;
import is.rebbi.wo.util.SWCustomInfo;
import is.rebbi.wo.util.SWSettings;
import is.rebbi.wo.util.USDataUtilities;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOComponent;
import com.webobjects.appserver.WOContext;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableArray;

import concept.SWAdminComponent;
import concept.data.SWDocument;
import concept.data.SWPicture;

public class SWEditDataAsset extends SWAdminComponent {

	public String filename;
	public String url;
	private SWDataAsset<?, ?> _selectedAsset;
	public FileType currentDocumentType;
	public NSArray<String> allSizesList = NSArray.componentsSeparatedByString( SWSettings.stringForKey( "pictureSizes" ), "," );
	public NSMutableArray<String> selectedSizes;
	public String currentSize;

	public SWEditDataAsset( WOContext context ) {
		super( context );
	}

	public NSArray<FileType> documentTypes() {
		return FileTypes.types();
	}

	public void setSelectedAsset( SWDataAsset<?, ?> value ) {
		_selectedAsset = value;

		if( isPicture() && selectedSizes == null ) {
			String sizesFromPicture = (String)((SWPicture)_selectedAsset).customInfo().valueForKey( "sizes" );

			if( sizesFromPicture != null ) {
				selectedSizes = NSArray.componentsSeparatedByString( sizesFromPicture, "," ).mutableClone();
			}
			else {
				selectedSizes = new NSMutableArray<>();
			}
		}
	}

	public SWDataAsset<?, ?> selectedAsset() {
		return _selectedAsset;
	}

	private String selectedSizesString() {
		String str = selectedSizes.componentsJoinedByString( "," );
		return str;
	}

	public boolean currentSizeSelected() {
		return selectedSizes.containsObject( currentSize );
	}

	public void setCurrentSizeSelected( boolean value ) {
		if( value && !selectedSizes.containsObject( currentSize ) ) {
			selectedSizes.addObject( currentSize );
		}
		else if( !value && selectedSizes.containsObject( currentSize ) ) {
			selectedSizes.removeObject( currentSize );
		}
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
		System.out.println( "SIZES: " + selectedSizesString() );
		selectedAsset().setDisplayName( filename( _assetName, url, filename ) );

		if( isPicture() ) {
			((SWPicture)selectedAsset()).customInfo().takeValueForKey( selectedSizesString(), "sizes" );
		}

		if( StringUtilities.hasValue( url ) ) {
			selectedAsset().setData( USDataUtilities.readDataFromURL( url ) );
		}

		selectedAsset().updateThumbnails();
		session().defaultEditingContext().saveChanges();

		_assetName = null;
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

	public String _assetName;

	public String assetName() {
		if( _assetName == null ) {
			_assetName = selectedAsset().name();
		}

		return _assetName;
	}

	public boolean hasDataButNoType() {
		return selectedAsset().hasData() && selectedAsset().documentType() == null;
	}
}