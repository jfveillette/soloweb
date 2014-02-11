package concept.components.admin;

import is.rebbi.core.util.StringUtilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.webobjects.appserver.WOComponent;
import com.webobjects.appserver.WOContext;
import com.webobjects.foundation.NSData;

import concept.SWAdminComponent;
import concept.data.SWComponent;
import concept.data.SWDocument;
import concept.data.SWPage;

public class SWEditDocumentContentAsText extends SWAdminComponent {

	private static final Logger logger = LoggerFactory.getLogger( SWEditDocumentContentAsText.class );

	public SWDocument selectedDocument;
	public SWPage selectedPage;
	public SWComponent selectedComponent;

	public SWEditDocumentContentAsText( WOContext context ) {
		super( context );
	}

	public WOComponent saveChanges() {
		NSData d = context().request().content();
		String s = null;

		if( d != null ) {
			s = StringUtilities.stringFromDataUsingEncoding( d.bytes(), "UTF-8" );
		}

		setDataAsString( s );
		session().defaultEditingContext().saveChanges();
		return null;
	}

	public String dataAsString() {
		if( selectedDocument != null && selectedDocument.data() != null ) {
			return StringUtilities.stringFromDataUsingEncoding( selectedDocument.data().bytes(), "ISO-8859-1" );
		}
		else if( selectedPage != null ) {
			return selectedPage.customInfo().toString();
		}
		else if( selectedComponent != null ) {
			return selectedComponent.customInfo().toString();
		}
		else {
			return "";
		}
	}

	public void setDataAsString( String s ) {
		try {
			if( selectedDocument != null ) {
				selectedDocument.setData( new NSData( s.getBytes( "ISO-8859-1" ) ) );
			}
			else if( selectedPage != null ) {
				selectedPage.setCustomInfoData( s );
			}
			else if( selectedComponent != null ) {
				selectedComponent.setCustomInfoData( s );
			}
		}
		catch( Exception e ) {
			logger.debug( "Error setting data as string", e );
		}
	}
}