package concept.components.client;

import is.rebbi.core.util.StringUtilities;
import is.rebbi.wo.util.USEOUtilities;
import is.rebbi.wo.util.USHierarchyUtilities;

import java.util.HashMap;
import java.util.Iterator;

import com.webobjects.appserver.WOContext;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableArray;

import concept.SWGenericComponent;
import concept.data.SWPage;
import concept.data.SWSite;

public class SWPageInLanguageNavigator extends SWGenericComponent {

	private static HashMap<String, String> codeToLanguageMap = null;

	public String curLanguage;

	public SWPageInLanguageNavigator( WOContext context ) {
		super( context );

		if( codeToLanguageMap == null ) {
			codeToLanguageMap = new HashMap<>();
			codeToLanguageMap.put( "is", "Íslenska" );
			codeToLanguageMap.put( "en", "English" );
			codeToLanguageMap.put( "sk", "Skandinavisk" );
			codeToLanguageMap.put( "dk", "Dansk" );
			codeToLanguageMap.put( "se", "Svenska" );
			codeToLanguageMap.put( "no", "Norsk" );
		}
	}

	public NSArray<String> languages() {
		NSMutableArray<String> list = new NSMutableArray<>();
		String codes;

		codes = (String)USHierarchyUtilities.valueInHierarchyForKeyPath( selectedPage(), "customInfo.swPageLanguageNavigatorLanguageCodes" );
		if( !StringUtilities.hasValue( codes ) ) {
			codes = selectedPage().siteForThisPage().customInfo().stringValueForKey( "swPageLanguageNavigatorLanguageCodes" );
		}
		if( StringUtilities.hasValue( codes ) ) {
			String code, lang;
			String[] codeList = codes.split( "," );
			if( codeList.length > 0 ) {
				for( int codeNo = 0; codeNo < codeList.length; codeNo++ ) {
					code = codeList[codeNo];
					lang = codeToLanguageMap.get( code );
					if( lang == null ) {
						lang = "???";
					}
					list.addObject( lang );
				}
			}
		}

		return list;
	}

	public String curLanguageUrl() {
		String uri = context().request().uri();
		String url = null;
		if( uri.contains( "/dp?" ) && uri.contains( "name=" ) ) {
			String curSymbol = selectedPage().symbol();
			String newCode = codeForLanguageName( curLanguage );
			EOEditingContext ec = session().defaultEditingContext();
			SWPage nextPage = null;

			if( StringUtilities.hasValue( curSymbol ) && curSymbol.length() > 3 ) {
				SWSite site = selectedPage().siteForThisPage();
				String siteLang = site.language();
				String newSymbol = curSymbol;

				if( curSymbol.substring( curSymbol.length() - 3 ).equals( "_" + siteLang ) ) {
					newSymbol = curSymbol.substring( 0, curSymbol.length() - 3 );
				}
				if( newCode != null ) {
					nextPage = pageWithSymbolAndSite( ec, newSymbol + "_" + newCode, site );
					if( nextPage == null && newCode.equals( "is" ) ) {
						nextPage = pageWithSymbolAndSite( ec, newSymbol, site );
					}
					//if (page == null)
					//	page = SWPageUtilities.frontPageWithSitegroupAndLanguage(ec, site.sitegroup(), newCode);
					if( nextPage != null ) {
						url = "/page/" + nextPage.symbol();
					}
				}
			}

			if( nextPage != null ) {
				// Add current url parameters if there are any.  Remember that allthough the visible url in the browser is smth like
				// site.is/page/pagename, the queryString will get name=pagename or id=nnnnnn so we need to remove that.
				String queryString = context().request().queryString();
				if( queryString != null && queryString.length() > 0 ) {
					queryString = queryString.replaceAll( "name\\=\\w+", "" );
					queryString = queryString.replaceAll( "id\\=\\w+", "" );

					// Allow SoloStaff to check the url to see if another personID should be selected
//					throw new RuntimeException( "Not implemented!" );
//					FIXME: queryString = SWSUtilities.updatePersonIDInUrlForPage( ec, nextPage, queryString );
					// Finally add the query string back to the end of the url
					FIXME: url += queryString;
				}
			}
		}

		return url;
	}

	public boolean hasLanguageUrl() {
		String url = curLanguageUrl();
		return StringUtilities.hasValue( url );
	}

	private String codeForLanguageName( String language ) {
		String code = null, tempCode;
		Iterator<String> keyIter = codeToLanguageMap.keySet().iterator();
		while( code == null && keyIter.hasNext() ) {
			tempCode = keyIter.next();
			if( codeToLanguageMap.get( tempCode ).equals( language ) ) {
				code = tempCode;
			}
		}
		return code;
	}

	private SWPage pageWithSymbolAndSite( EOEditingContext ec, String symbol, SWSite site ) {
		SWPage page = null;
		NSArray<SWPage> pages;

		pages = USEOUtilities.objectsMatchingKeyAndValue( ec, SWPage.ENTITY_NAME, "symbol", symbol );

		if( pages != null ) {
			SWPage p;
			SWSite s;
			for( int i = 0; page == null && i < pages.size(); i++ ) {
				p = pages.objectAtIndex( i );
				s = p.siteForThisPage();
				if( s != null && s.sitegroup() != null && site.sitegroup() != null && s.sitegroup().equals( site.sitegroup() ) ) {
					page = p;
				}
			}
		}

		return page;
	}
}