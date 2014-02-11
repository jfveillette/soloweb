package concept.components.client;

import is.rebbi.core.util.StringUtilities;
import is.rebbi.wo.util.SWSettings;
import is.rebbi.wo.util.USUtilities;

import com.webobjects.appserver.WOContext;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableDictionary;
import com.webobjects.foundation.NSTimestamp;

import concept.SWDirectAction;
import concept.SWGenericComponent;
import concept.SWPageUtilities;
import concept.data.SWComponent;
import concept.data.SWNewsItem;
import concept.data.SWPage;
import concept.util.SWStringUtilities;

/**
 * The default detail view for an SWNewsitem.
 */

public class SWNewsDetail extends SWGenericComponent {

	public boolean showForm = false, showError = false;
	public String spamText = "", spamInput = "", errorMessage = "", emailTo = "", emailMessage = "";
	public String newsTitle = "";
	public int spamValue = 0;
	public int backValue = -1;

	public SWNewsDetail( WOContext context ) {
		super( context );
		backValue = -1;
	}

	public String goBackText() {
		String s = currentComponent().customInfo().stringValueForKey( "goBackText" );

		if( StringUtilities.hasValue( s ) ) {
			return s;
		}

		return SWSettings.stringForKey( "defaultNewsGoBackString", "Go back..." );
	}

	public SWNewsItem selectedNewsItem() {
		return (SWNewsItem)valueForBinding( "selectedNewsItem" );
	}

	@Override
	public SWComponent currentComponent() {
		return (SWComponent)valueForBinding( "currentComponent" );
	}

	@Override
	public boolean synchronizesVariablesWithBindings() {
		return false;
	}

	@Override
	public boolean isStateless() {
		return true;
	}

	@Override
	public void reset() {}

	public String pictureURL() {
		return selectedNewsItem().picture().previewURL( (String)selectedNewsItem().customInfo().valueForKey( "size" ) );
	}

	public String picture2URL() {
		return selectedNewsItem().picture().previewURL( (String)selectedNewsItem().customInfo().valueForKey( "size2" ) );
	}

	public String tableStyle() {
		String pictAlign = (String)currentComponent().customInfo().valueForKey( "imageAlignment" );
		if( "left".equals( pictAlign ) ) {
			return "width: 1px; margin-right: 10px; margin-bottom: 10px;";
		}
		else if( "right".equals( pictAlign ) ) {
			return "width: 1px; margin-left: 10px; margin-bottom: 10px;";
		}
		else {
			return "width: 1px; margin-left: 10px; margin-right: 10px; margin-bottom: 10px;";
		}
	}

	public boolean showAuthor() {
		String author = selectedNewsItem().author();
		boolean shouldShow = USUtilities.booleanFromObject( currentComponent().customInfo().valueForKey( "showNewsAuthor" ) );
		return (shouldShow && author != null && author.length() > 0);
	}

	public String dateDisplayKey() {
		String key = (String)currentComponent().customInfo().valueForKey( "dateDisplay" );
		if( key == null || "".equals( key ) ) {
			key = "above";
		}
		return key;
	}

	public SWPage sendEmail() {
		showError = false;
		errorMessage = "";

		if( emailTo.equals( "" ) ) {
			errorMessage = "<p>Netfang vantar</p>\n";
			showError = true;
		}
		if( emailMessage.equals( "" ) ) {
			errorMessage += "<p>Skilabo&eth; vantar</p>\n";
			showError = true;
		}
		if( !spamInput.equals( String.valueOf( spamValue ) ) ) {
			errorMessage += "<p>Ruslp&oacute;stv&ouml;rn ekki r&eacute;tt</p>\n";
			showError = true;
		}
		if( !showError ) {
			NSMutableDictionary d = new NSMutableDictionary();
			d.setObjectForKey( currentComponent().pageID(), "id" );
			d.setObjectForKey( Integer.valueOf( selectedNewsItem().primaryKey() ), "detail" );
			String url = context().directActionURLForActionNamed( "dp", d );
			String host = SWDirectAction.hostForRequest( context().request() );

			String html = "";
			html += "<link rel='stylesheet' type='text/css' href='/sw32/default.css' />\n";
			html += "<p>&THORN;&eacute;r hefur veri&eth; send fr&eacute;tt fr&aacute; " + host + "</p>\n";
			html += "<p>Skilabo&eth; sendanda:</p>\n";
			html += "<p>" + emailMessage.replaceAll( "\n", "<br/>" ) + "</p><hr/>\n";
			html += "<div class='newsItem'>\n";
			html += newsTitle;
			html += "<div class='excerpt'>";
			if( selectedNewsItem().hasPicture2() ) {
				html += "<img src='" + picture2URL() + "'/>";
			}
			else if( selectedNewsItem().hasPicture() ) {
				html += "<img src='" + pictureURL() + "'/>";
			}
			html += selectedNewsItem().excerpt() + "</div>\n";
			html += "<div class='fleira'>";
			html += "<a href='http://" + host + url + "'>Lesa meira...</a>\n";
			html += "</div>";
			html += "</div>";
			String subject = selectedNewsItem().heading();
			// TODO: make the sender custom value
			is.rebbi.wo.util.USMailSender.composeEmail( "soloweb@lausn.is", new NSArray( emailTo ), null, null, subject, null, html );
			showForm = false;
		}
		backValue = -3;
		return SWPageUtilities.pageFromRequest( session().defaultEditingContext(), context().request() );
	}

	public boolean pageLangIcelandic() {
		return "is".equals( currentComponent().page().pageLanguageCode() );
	}

	public boolean pageLangEnglish() {
		return "en".equals( currentComponent().page().pageLanguageCode() );
	}

	public String currentUrl() {
		String host = context().request().headerForKey( "host" );
		String pageLink = currentComponent().page().pageLink();
		Integer newsItemId = Integer.valueOf( selectedNewsItem().primaryKey() );
		String url = "http://" + host + pageLink + "&detail=" + newsItemId;
		return url;
	}

	public String ogItemDescription() {
		String desc = selectedNewsItem().excerpt();
		String descNoHtml = SWStringUtilities.stripHtmlFromString( desc );
		if( !StringUtilities.hasValue( descNoHtml ) ) {
			descNoHtml = SWStringUtilities.stripHtmlFromString( selectedNewsItem().text() );
		}
		return descNoHtml;
	}

	public boolean showSkradDags() {
		return currentComponent().customInfo().booleanValueForKey( "showSkradDags" );
	}

	public String skradDagsLabel() {
		String label = currentComponent().customInfo().stringValueForKey( "skradDagsLabel" );
		if( !StringUtilities.hasValue( label ) ) {
			label = "Skráð";
		}
		return label;
	}

	public NSTimestamp skradDagsValue() {
		NSTimestamp d = selectedNewsItem().creationDate();
		if( d == null ) {
			d = selectedNewsItem().date();
		}
		return d;
	}
}