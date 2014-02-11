package concept.util;

import is.rebbi.core.util.StringUtilities;
import is.rebbi.wo.util.USArrayUtilities;
import is.rebbi.wo.util.USEOUtilities;
import is.rebbi.wo.util.USHTTPUtilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.webobjects.appserver.WORequest;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOFetchSpecification;
import com.webobjects.eocontrol.EOKeyValueQualifier;
import com.webobjects.eocontrol.EOQualifier;
import com.webobjects.foundation.NSArray;

import concept.data.SWPage;
import concept.data.SWSite;

/**
 * SWPageUtilities is the preferred way to fetch SWPage objects from the DB.
 */

public class CPPageUtilities {

	private static final Logger logger = LoggerFactory.getLogger( CPPageUtilities.class );

	private static NSArray<String> PAGE_PREFETCH_PATHS = new NSArray<>( new String[] { SWPage.COMPONENTS_KEY, SWPage.SITE_KEY } );

	/**
	 * Fetches the specified page from the DB. If no page is found, null is returned.
	 */
	public static SWPage pageWithID( EOEditingContext ec, Number id ) {
		return USEOUtilities.objectWithPK( ec, SWPage.ENTITY_NAME, id );
	}

	/**
	 * Fetches the named page from the DB. If no page is found, null is returned.
	 */
	public static SWPage pageWithName( EOEditingContext ec, String pageName ) {
		return pageMatchingKeyAndValue( ec, SWPage.SYMBOL_KEY, pageName );
	}

	/**
	 * Fetches the named page from the DB. If no page is found, null is returned.
	 */
	private static SWPage pageWithNameAndHost( EOEditingContext ec, String pageName, String hostName ) {

		if( pageName.endsWith( "/" ) ) {
			pageName = pageName.substring( 0, pageName.length() - 1 );
		}

		EOQualifier q = new EOKeyValueQualifier( SWPage.SYMBOL_KEY, EOQualifier.QualifierOperatorEqual, pageName );
		EOFetchSpecification fs = new EOFetchSpecification( SWPage.ENTITY_NAME, q, null );
		fs.setPrefetchingRelationshipKeyPaths( PAGE_PREFETCH_PATHS );
		NSArray<SWPage> fetchedPages = ec.objectsWithFetchSpecification( fs );

		SWPage pagetoReturn = null;

		if( fetchedPages.count() == 1 ) {
			pagetoReturn = fetchedPages.objectAtIndex( 0 );
		}
		else if( fetchedPages.count() > 1 ) {
			for( SWPage page : fetchedPages ) {
				SWSite site = page.siteForThisPage();

				if( site != null ) {
					if( site.hosts().containsObject( hostName ) ) {
						pagetoReturn = page;
					}
				}
			}
		}

		return pagetoReturn;
	}

	/**
	 * Fetches the page matching the specified criteria from the DB. If no page is found, null is returned.
	 */
	private static SWPage pageMatchingKeyAndValue( EOEditingContext ec, String key, Object value ) {
		EOQualifier q = new EOKeyValueQualifier( key, EOQualifier.QualifierOperatorEqual, value );
		EOFetchSpecification fs = new EOFetchSpecification( SWPage.ENTITY_NAME, q, null );
		fs.setPrefetchingRelationshipKeyPaths( PAGE_PREFETCH_PATHS );
		fs.setFetchLimit( 1 );
		NSArray<SWPage> a = ec.objectsWithFetchSpecification( fs );

		if( !USArrayUtilities.hasObjects( a ) ) {
			return null;
		}

		return a.objectAtIndex( 0 );
	}

	/**
	 * Look at the request and see if it's hiding a request for a page.
	 */
	public static SWPage pageFromRequest( EOEditingContext ec, WORequest request ) {

		String hostName = request.stringFormValueForKey( "host" );

		if( hostName == null ) {
			hostName = USHTTPUtilities.host( request );
		}

		String pageID = request.stringFormValueForKey( "id" );

		if( pageID != null ) {
			if( StringUtilities.isDigitsOnly( pageID ) ) {
				return pageWithID( ec, new Integer( pageID ) );
			}
		}

		String pageName = request.stringFormValueForKey( "name" );

		if( pageName != null ) {
			return pageWithNameAndHost( ec, pageName, hostName );
		}

		if( hostName != null ) {
			SWSite site = CPPageUtilities.siteMatchingHostName( ec, hostName );

			if( site == null ) {
				site = CPPageUtilities.randomSite( ec );
			}

			if( site == null ) {
				return null;
			}

			return site.frontpage();
		}

		String redirectHeader = USHTTPUtilities.redirectURL( request );

		if( redirectHeader != null ) {
			if( redirectHeader.startsWith( "/" ) ) {
				redirectHeader = redirectHeader.substring( 1, redirectHeader.length() );
			}

			return CPPageUtilities.pageMatchingKeyAndValue( ec, SWPage.SYMBOL_KEY, redirectHeader );
		}

		return null;
	}

	public static SWSite siteFromRequest( EOEditingContext ec, WORequest request ) {
		String hostName = request.stringFormValueForKey( "host" );

		if( hostName == null ) {
			hostName = USHTTPUtilities.host( request );
		}

		if( hostName != null ) {
			return CPPageUtilities.siteMatchingHostName( ec, hostName );
		}

		return null;
	}

	/**
	 * Returns the site matching the host name specified.
	 */
	public static SWSite siteMatchingHostName( EOEditingContext ec, String hostName ) {
		logger.debug( "Fetching site for hostName: " + hostName );
		EOQualifier q = SWSite.QUAL.like( "*-" + hostName + "-*" );

		NSArray<SWSite> sites = SWSite.fetchSWSites( ec, q, null );

		if( !USArrayUtilities.hasObjects( sites ) ) {
			return null;
		}

		return sites.objectAtIndex( 0 );
	}

	/**
	 * If no site is found matching the host name, this method is used.
	 */
	public static SWSite randomSite( EOEditingContext ec ) {
		NSArray<SWSite> a = SWSite.fetchAllSWSites( ec );

		if( USArrayUtilities.hasObjects( a ) ) {
			return a.objectAtIndex( 0 );
		}

		return null;
	}
}