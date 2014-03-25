package concept.components.admin;

import is.rebbi.core.util.StringUtilities;

import java.util.Enumeration;

import com.webobjects.appserver.WOComponent;
import com.webobjects.appserver.WOContext;
import com.webobjects.foundation.NSArray;

import concept.SWGenericComponent;
import concept.components.client.SWMedia;
import concept.data.SWComponent;
import concept.data.SWDocument;

public class SWMediaAdmin extends SWGenericComponent {

	private static final String DOC_ID_KEY = "documentID";

	public static final NSArray<String> videoExtensions = NSArray.componentsSeparatedByString( "mpg,avi,mov,mp4,wmv,flv", "," );
	public static final NSArray<String> graphicsExtensions = NSArray.componentsSeparatedByString( "swf", "," );
	public static final NSArray<String> audioExtensions = NSArray.componentsSeparatedByString( "mp3,m4a,wma,wav,aif", "," );

	public SWMediaAdmin( WOContext context ) {
		super( context );
	}

	@Override
	public void setCurrentComponent( SWComponent newOne ) {
		super.setCurrentComponent( newOne );

		currentComponent().setTemplateName( SWMedia.class.getSimpleName() );

		if( !StringUtilities.hasValue( (String)currentComponent().customInfo().valueForKey( "mediaType" ) ) ) {
			currentComponent().customInfo().takeValueForKey( "file", "mediaType" );
		}
	}

	public SWDocument selectedDocument() {
		return SWDocument.documentWithID( session().defaultEditingContext(), documentIDFromDictionary() );
	}

	public void setSelectedDocument( SWDocument document ) {
		if( document == null ) {
			currentComponent().customInfo().takeValueForKey( null, DOC_ID_KEY );
		}
		else {
			currentComponent().customInfo().takeValueForKey( document.primaryKey().toString(), DOC_ID_KEY );
		}
	}

	public Integer documentIDFromDictionary() {
		return currentComponent().customInfo().integerValueForKey( DOC_ID_KEY );
	}

	public WOComponent self() {
		return this;
	}

	public boolean isVideoOrGraphics() {
		boolean yes = false;
		SWDocument doc = selectedDocument();
		if( doc != null ) {
			String ext = doc.extension();
			yes = videoExtensions.containsObject( ext ) || graphicsExtensions.containsObject( ext );
		}
		return yes;
	}

	public boolean isAudioOrVideo() {
		String ext = selectedDocument().extension();
		return videoExtensions.containsObject( ext ) || audioExtensions.containsObject( ext );
	}

	public boolean isValidMediaFile() {
		boolean yes = false;
		SWDocument doc = selectedDocument();

		if( doc != null ) {
			String ext = doc.extension();
			yes = videoExtensions.containsObject( ext ) || graphicsExtensions.containsObject( ext ) || audioExtensions.containsObject( ext );
		}

		return yes;
	}

	public boolean isAudio() {
		return audioExtensions.containsObject( selectedDocument().extension() );
	}

	public String videoExtensions() {
		return extensions( videoExtensions );
	}

	public String audioExtensions() {
		return extensions( audioExtensions );
	}

	public String graphicExtensions() {
		return extensions( graphicsExtensions );
	}

	private String extensions( NSArray<String> list ) {
		String ext = "";
		String komma = "";

		for( Enumeration<String> e = list.objectEnumerator(); e.hasMoreElements(); ) {
			ext = ext + komma + e.nextElement();
			komma = ", ";
		}

		return ext;
	}
}