package concept;

import is.rebbi.core.util.StringUtilities;
import is.rebbi.wo.util.SWSettings;

import java.util.Enumeration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.webobjects.appserver.WOComponent;
import com.webobjects.appserver.WOContext;
import com.webobjects.appserver.WORequest;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOFetchSpecification;
import com.webobjects.eocontrol.EOKeyValueQualifier;
import com.webobjects.eocontrol.EOQualifier;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableDictionary;

import concept.components.client.SWStandardTemplate;
import concept.data.SWPage;
import concept.data.SWSite;
import er.extensions.appserver.ERXApplication;

/**
 * SWPageUtilities is the preferred way to fetch SWPage objects from the DB.
 */

public class SWPageUtilities {

	private static final Logger logger = LoggerFactory.getLogger( SWPageUtilities.class );

	private static NSArray<String> PREFETCH_PATHS = new NSArray<>( new String[] { SWPage.SITE_KEY, SWPage.COMPONENTS_KEY } );

	/**
	 * @return The specified page from the DB. If no page is found, null is returned.
	 */
	public static SWPage pageWithID( EOEditingContext ec, Integer anID ) {
		return pageMatchingKeyAndValue( ec, SWPage.ID_KEY, anID );
	}

	/**
	 * @return The specified page from the DB. If no page is found, null is returned.
	 */
	public static SWPage pageNamed( EOEditingContext ec, String name ) {
		return pageWithName( ec, name );
	}

	/**
	 * @return The named page from the DB. If no page is found, null is returned.
	 */
	public static SWPage pageWithName( EOEditingContext ec, String name ) {

		if( name.endsWith( "/" ) ) {
			name = name.substring( 0, name.length() - 1 );
		}

		return pageMatchingKeyAndValue( ec, SWPage.SYMBOL_KEY, name );
	}

	/**
	 * @return The named page from the DB. If no page is found, null is returned.
	 */
	public static SWPage pageWithNameAndRequest( EOEditingContext ec, String name, WORequest request ) {
		SWPage thePage = null;

		if( name.endsWith( "/" ) ) {
			name = name.substring( 0, name.length() - 1 );
		}

		try {
			EOQualifier q = SWPage.SYMBOL.eq( name );
			EOFetchSpecification fs = new EOFetchSpecification( SWPage.ENTITY_NAME, q, null );
			fs.setPrefetchingRelationshipKeyPaths( PREFETCH_PATHS );
			NSArray<SWPage> fetchedPages = ec.objectsWithFetchSpecification( fs );

			// Account for multiple linking names in the DB by matching with the domain name.
			if( fetchedPages.count() > 1 ) {
				String host = SWDirectAction.hostForRequest( request );
				Enumeration<SWPage> e = fetchedPages.objectEnumerator();

				while( e.hasMoreElements() ) {
					SWPage p = e.nextElement();
					SWSite site = p.siteForThisPage();

					if( site != null ) {
						String currentHost = site.qual();

						if( StringUtilities.hasValue( currentHost ) ) {
							if( currentHost.indexOf( host ) > -1 ) {
								return p;
							}
						}
					}
				}
			}

			thePage = fetchedPages.lastObject();
		}
		catch( Exception e2 ) {
			logger.debug( "Exception in pageWithNameAndRequest", e2 );
			thePage = null;
		}

		return thePage;
	}

	/**
	 * @return The matching page from the DB. If no page is found, null is returned.
	 */
	private static SWPage pageMatchingKeyAndValue( EOEditingContext ec, String key, Object value ) {
		SWPage thePage = null;

		try {
			EOQualifier q = new EOKeyValueQualifier( key, EOQualifier.QualifierOperatorEqual, value );
			EOFetchSpecification fs = new EOFetchSpecification( SWPage.ENTITY_NAME, q, null );
			fs.setPrefetchingRelationshipKeyPaths( PREFETCH_PATHS );
			thePage = (SWPage)ec.objectsWithFetchSpecification( fs ).lastObject();
		}
		catch( Exception e ) {
			thePage = null;
			logger.error( "SWPageUtilities.pageMatchingKeyAndValue: Exception when getting page with key=" + key + " and value=" + value, e );
		}

		return thePage;
	}

	public static SWPage pageFromRequest( EOEditingContext ec, WORequest r ) {
		SWPage pageToDisplay = null;
		String idString = r.stringFormValueForKey( "id" );
		String nameString = r.stringFormValueForKey( "name" );
		String detailString = r.stringFormValueForKey( "detail" );

		if( idString != null ) {
			pageToDisplay = pageWithID( ec, Integer.parseInt( idString ) );
		}
		else if( nameString != null ) {
			pageToDisplay = pageWithNameAndRequest( ec, nameString, r );
		}
		else if( detailString != null ) {
			String pageName = "frettasida";

			NSArray<String> a = NSArray.componentsSeparatedByString( detailString, "," );

			if( a != null && a.count() > 1 ) {
				pageName = a.objectAtIndex( 1 );
			}

			pageToDisplay = pageWithNameAndRequest( ec, pageName, r );
		}
		else if( r.headerForKey( "redirect_url" ) != null ) {
			String s = r.headerForKey( "redirect_url" );

			if( s.startsWith( "/" ) ) {
				s = s.substring( 1, s.length() );
			}

			pageToDisplay = SWPageUtilities.pageMatchingKeyAndValue( ec, "symbol", s );
		}

		// If we are using strict site checking then the requesting domain MUST exist in the list of
		// domains in the page's site domain list, otherwise an error should be returned.
		boolean strictSiteChecking = SWSettings.booleanForKey( "strictSiteChecking" );

		if( pageToDisplay != null && strictSiteChecking == true ) {
			boolean allowPageDisplay = false;
			SWSite theSite = pageToDisplay.siteForThisPage();
			NSArray<String> siteDomains = NSArray.componentsSeparatedByString( theSite.qual(), SWSite.SITENAME_DELIMITER );
			String requestHost = SWDirectAction.hostForRequest( r );

			if( siteDomains != null && siteDomains.count() > 0 ) {
				Enumeration<String> siteDom = siteDomains.objectEnumerator();

				while( siteDom.hasMoreElements() && allowPageDisplay == false ) {
					String siteDomName = siteDom.nextElement();

					if( siteDomName.equalsIgnoreCase( requestHost ) ) {
						allowPageDisplay = true;
					}
				}
			}

			if( allowPageDisplay == false ) {
				pageToDisplay = null;
			}
		}

		return pageToDisplay;
	}

	/**
	 * This method is intentionally undocumented
	 */
	public static WOComponent pageMatchingName( EOEditingContext ec, WOContext aContext, String pageName ) {
		SWStandardTemplate nextPage = ERXApplication.erxApplication().pageWithName( SWStandardTemplate.class, aContext );
		nextPage.setSelectedPage( pageNamed( ec, pageName ) );
		return nextPage;
	}

	/**
	 * @return A URL for this page. Use the page's linking name if it has one.
	 */
	public static String urlForPage( SWPage page, WOContext context ) {

		if( page == null || context == null ) {
			return null;
		}

		String look = context.request().stringFormValueForKey( "look" );
		String url = null;

		if( StringUtilities.hasValue( look ) ) {
			NSMutableDictionary<String,Object> d = new NSMutableDictionary<>();
			d.setObjectForKey( look, "look" );
			d.setObjectForKey( Integer.valueOf( page.primaryKey() ), "id" );
			url = context.directActionURLForActionNamed( "dp", d );
		}

		if( !StringUtilities.hasValue( url ) ) {
			if( StringUtilities.hasValue( page.symbol() ) ) {
				url = "/page/" + page.symbol();
			}
			else {
				url = "/id/" + page.primaryKey();
			}
		}

		return url;
	}
}