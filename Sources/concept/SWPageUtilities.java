package concept;

import is.rebbi.core.util.StringUtilities;
import is.rebbi.wo.util.SWSettings;
import is.rebbi.wo.util.USArrayUtilities;
import is.rebbi.wo.util.USHTTPUtilities;

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

	private static NSArray<String> PAGE_PREFETCH_PATHS = new NSArray<>( SWPage.COMPONENTS_KEY, SWPage.SITE_KEY );

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
	private static SWPage pageWithNameAndRequest( EOEditingContext ec, String name, WORequest request ) {

		if( name.endsWith( "/" ) ) {
			name = name.substring( 0, name.length() - 1 );
		}

		EOFetchSpecification fs = new EOFetchSpecification( SWPage.ENTITY_NAME, SWPage.SYMBOL.eq( name ), null );
		fs.setPrefetchingRelationshipKeyPaths( PAGE_PREFETCH_PATHS );
		NSArray<SWPage> pages = ec.objectsWithFetchSpecification( fs );

		if( pages.count() == 1 ) {
			return pages.objectAtIndex( 0 );
		}

		if( pages.count() > 1 ) {
			String hostName = SWDirectAction.hostForRequest( request );

			for( SWPage p : pages ) {
				if( p.siteForThisPage().hasHostName( hostName ) ) {
					return p;
				}
			}

			boolean strictSiteChecking = SWSettings.booleanForKey( "strictSiteChecking" );

			if( !strictSiteChecking ) {
				return pages.objectAtIndex( 0 );
			}
		}

		return null;
	}

	/**
	 * @return The matching page from the DB. If no page is found, null is returned.
	 */
	private static SWPage pageMatchingKeyAndValue( EOEditingContext ec, String key, Object value ) {
		EOQualifier q = new EOKeyValueQualifier( key, EOQualifier.QualifierOperatorEqual, value );
		EOFetchSpecification fs = new EOFetchSpecification( SWPage.ENTITY_NAME, q, null );
		fs.setPrefetchingRelationshipKeyPaths( PAGE_PREFETCH_PATHS );
		return (SWPage)ec.objectsWithFetchSpecification( fs ).lastObject();
	}

	public static SWPage pageFromRequest( EOEditingContext ec, WORequest r ) {
		String idString = r.stringFormValueForKey( "id" );
		String nameString = r.stringFormValueForKey( "name" );
		String detailString = r.stringFormValueForKey( "detail" );

		if( idString != null ) {
			return pageWithID( ec, Integer.parseInt( idString ) );
		}

		if( nameString != null ) {
			return pageWithNameAndRequest( ec, nameString, r );
		}

		if( detailString != null ) {
			String pageName = "frettasida";

			NSArray<String> a = NSArray.componentsSeparatedByString( detailString, "," );

			if( a != null && a.count() > 1 ) {
				pageName = a.objectAtIndex( 1 );
			}

			return pageWithNameAndRequest( ec, pageName, r );
		}

		return null;
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
			NSMutableDictionary<String, Object> d = new NSMutableDictionary<>();
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

	/**
	 * Returns the site matching the host name specified.
	 */
	private static SWSite siteMatchingHostName( EOEditingContext ec, String hostName ) {
		EOQualifier q = SWSite.QUAL.like( "*-" + hostName + "-*" );

		NSArray<SWSite> sites = SWSite.fetchSWSites( ec, q, null );

		if( !USArrayUtilities.hasObjects( sites ) ) {
			return null;
		}

		return sites.objectAtIndex( 0 );
	}

	public static SWSite siteFromRequest( EOEditingContext ec, WORequest request ) {
		String hostName = request.stringFormValueForKey( "host" );

		if( hostName == null ) {
			hostName = USHTTPUtilities.host( request );
		}

		if( hostName != null ) {
			return siteMatchingHostName( ec, hostName );
		}

		return null;
	}
}