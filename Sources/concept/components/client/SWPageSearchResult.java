package concept.components.client;

import is.rebbi.core.util.StringUtilities;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.webobjects.appserver.WOComponent;
import com.webobjects.appserver.WOContext;

import concept.data.SWPage;
import concept.search.SWLuceneUtilities;
import concept.util.SWStringUtilities;

public class SWPageSearchResult extends WOComponent {

	public SWPage item;
	public HashMap<String, Object> itemSettings;
	public String itemText;

	public SWPageSearchResult( WOContext context ) {
		super( context );
	}

	public String itemName() {
		return SWLuceneUtilities.hiliteSearchString( item.name(), (String)itemSettings.get( "searchString" ) );
	}

	public boolean hasItemText() {
		try {
			String s = "";
			String searchString = (String)itemSettings.get( "searchString" );
			boolean found = false;
			Pattern pattern = Pattern.compile( "(?iu)\\b" + searchString + "\\b" );
			Matcher matcher;

			if( StringUtilities.hasValue( item.searchItemText() ) ) {
				s = SWStringUtilities.stripHtmlFromString( item.searchItemText() );
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
				String[] words = s.split( " " );
				itemText = "";
				for( int w = 0; w < 30; w++ ) {
					itemText += words[w] + " ";
				}
			}

			return found;
		}
		catch( Exception e ) {
			return false;
		}
	}
}