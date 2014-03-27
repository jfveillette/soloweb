package concept.components.client;

import is.rebbi.core.util.StringUtilities;

import com.webobjects.appserver.WOContext;

import concept.SWGenericComponent;
import concept.components.admin.SWMediaAdmin;
import concept.data.SWDocument;
import concept.util.SWOldURLs;

public class SWMedia extends SWGenericComponent {

	public SWMedia( WOContext context ) {
		super( context );
	}

	public SWDocument selectedDocument() {
		Integer documentID = currentComponent().customInfo().integerValueForKey( "documentID" );
		return SWDocument.documentWithID( session().defaultEditingContext(), documentID );
	}

	public String mediaURL() {
		String url = null;

		if( selectedDocument() != null ) {
			url = SWOldURLs.urlForDocumentInContext( selectedDocument(), context() );
			String ext = selectedDocument().extension();
			if( "swf".equals( ext ) ) {
				url += "?" + currentComponent().customInfo().valueForKey( "flashParameters" );
			}
		}

		return url;
	}

	public boolean isAudio() {
		boolean yes = false;

		if( selectedDocument() != null ) {
			yes = SWMediaAdmin.audioExtensions.containsObject( selectedDocument().extension() );
		}

		return yes;
	}

	public boolean isVideo() {
		boolean yes = false;

		if( selectedDocument() != null ) {
			yes = SWMediaAdmin.videoExtensions.containsObject( selectedDocument().extension() );
		}

		return yes;
	}

	public String flowPlayerStyle() {
		String style = "display:block;margin:auto;";
		style += "width:" + currentComponent().customInfo().valueForKey( "mediaWidth" ) + "px;";
		style += "height:" + currentComponent().customInfo().valueForKey( "mediaHeight" ) + "px;";
		return style;
	}

	public String flowPlayerId() {
		String id = "player" + currentComponent().primaryKey();
		return id;
	}

	public String flowPlayerProperties() {
		String prop = "{ ";
		prop += "clip: { autoPlay: " + currentComponent().customInfo().valueForKey( "mediaAutostart" ) + " }";
		prop += "}";
		return prop;
	}

	public String mediaStyle() {
		String style = null;
		String align = (String)currentComponent().customInfo().valueForKey( "mediaAlign" );
		if( StringUtilities.hasValue( align ) ) {
			style = "text-align:" + align;
		}
		return style;
	}
}