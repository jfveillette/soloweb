package concept.components.client;

import is.rebbi.core.util.StringUtilities;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import com.webobjects.appserver.WOContext;

import concept.SWGenericComponent;
import concept.data.SWNewsItem;
import concept.search.SWLuceneUtilities;
import concept.util.SWStringUtilities;

public class SWNewsItemSearchResult extends SWGenericComponent {

	public SWNewsItem item;
	public HashMap<String,Object> itemSettings;
	public String itemText;

	public SWNewsItemSearchResult( WOContext context ) {
		super( context );
	}

	public String itemName() {
		return SWLuceneUtilities.hiliteSearchString( item.name(), (String)itemSettings.get( "searchString" ) );
	}

	public boolean hasItemText() {
		String s = "";
		String searchString = (String)itemSettings.get( "searchString" );
		boolean found = false;
		Pattern pattern = Pattern.compile( "(?iu)\\b" + searchString + "\\b" );
		Matcher matcher;

		if( StringUtilities.hasValue( item.excerpt() ) ) {
			s = SWStringUtilities.stripHtmlFromString( item.excerpt() );
			matcher = pattern.matcher( s );
			if( matcher.find() ) {
				found = true;
			}
		}

		if( !found ) {
			s = SWStringUtilities.stripHtmlFromString( item.text() );
			matcher = pattern.matcher( s );
			if( matcher.find() ) {
				found = true;
			}
		}

		if( found ) {
			itemText = s.replaceAll( "(?iu)\\b(" + searchString + ")\\b", "<span class=\"searchresultword\">$1</span>" );
			int words = 0;
			int p1 = itemText.indexOf( "<span class" );
			while( words < 10 && p1 > 0 ) {
				if( itemText.charAt( p1 ) == ' ' ) {
					words++;
				}
				p1--;
			}
			if( p1 > 0 ) {
				p1 += 2; // Not at start of text, go to start of next word
			}
			int p2 = itemText.indexOf( "</span>" );
			words = 0;
			while( words < 20 && p2 < itemText.length() - 1 ) {
				if( itemText.charAt( p2 ) == ' ' ) {
					words++;
				}
				p2++;
			}
			int replLength = 38 + searchString.length();
			int p3 = p2 - replLength;
			int p4 = p2 + replLength;
			if( p3 < 0 ) {
				p3 = 0;
			}
			if( p4 > itemText.length() - 1 ) {
				p4 = itemText.length() - 1;
			}
			int p5 = itemText.substring( p3, p4 ).indexOf( "<span class" );
			if( p5 > -1 ) {
				p2 += p5;
			}

			String before = "", after = "";
			if( p1 > 0 ) {
				before = "... ";
			}
			if( p2 < itemText.length() - 1 ) {
				after = " ...";
			}
			itemText = before + itemText.substring( p1, p2 ) + after;
		}
		else {
			itemText = "";
		}

		return found;
	}

	public String newsItemDetailLink() {
		String pageSymbol = "lucene_search_news_item";
		if( itemSettings != null ) {
			String itemSymbol = (String)itemSettings.get( "newsDisplayPageSymbol" );
			if( StringUtilities.hasValue( itemSymbol ) ) {
				pageSymbol = itemSymbol;
			}
		}
		pageSymbol += "&detail=" + item.primaryKey();
		return "/page/" + pageSymbol;
	}
}