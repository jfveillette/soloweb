package concept.data;

import is.rebbi.core.util.StringUtilities;
import is.rebbi.wo.interfaces.SWHasCustomInfo;
import is.rebbi.wo.util.HumanReadable;
import is.rebbi.wo.util.SWCustomInfo;
import is.rebbi.wo.util.USArrayUtilities;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOFetchSpecification;
import com.webobjects.eocontrol.EOKeyValueQualifier;
import com.webobjects.eocontrol.EOQualifier;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableArray;

import concept.data.auto._SWSite;

/**
 * An SWSite represents a site, and contains a tree of pages
 */

public class SWSite extends _SWSite implements HumanReadable, SWHasCustomInfo {

	private static final Logger logger = LoggerFactory.getLogger( SWSite.class );
	public static final String SITENAME_DELIMITER = String.valueOf( (char)13 ) + String.valueOf( (char)10 );
	private SWCustomInfo _customInfo;
	private Locale _locale;

	/**
	 * @return A new site with a published frontpage.
	 */
	public static SWSite create( EOEditingContext ec ) {
		SWSite site = SWSite.createSWSite( ec );
		SWPage page = new SWPage();
		ec.insertObject( page );
		site.setFrontpage( page );
		page.setSortNumber( 0 );
		page.setPublished( 1 );
		page.setAccessible( 1 );
		return site;
	}

	/**
	 * @return All sites in the database.
	 */
	public static NSArray<SWSite> allSites( EOEditingContext ec ) {
		return SWSite.fetchAllSWSites( ec, SWSite.NAME.ascInsensitives() );
	}

	public SWCustomInfo customInfo() {
		if( _customInfo == null ) {
			_customInfo = new SWCustomInfo( this );
		}

		return _customInfo;
	}

	/**
	 * Sets the string containing the host names this Site applies to
	 */
	@Override
	public void setQual( String value ) {

		if( StringUtilities.hasValue( value ) ) {
			if( !value.endsWith( SITENAME_DELIMITER ) ) {
				value = value + SITENAME_DELIMITER;
			}
		}

		super.setQual( value );
	}

	/**
	 * @return The site's front page in an Array (for use with nested lists and such)
	 */
	public NSArray<SWPage> frontPageInArray() {
		return new NSArray<>( frontpage() );
	}

	/**
	 * @return The host names this site handles
	 */
	public NSArray<String> hostNames() {
		NSMutableArray<String> hostNames = new NSMutableArray<>();

		if( !StringUtilities.hasValue( qual() ) ) {
			return NSArray.emptyArray();
		}

		for( String next : qual().split( SITENAME_DELIMITER ) ) {
			String hostName = next.trim();

			if( !hostName.isEmpty() ) {
				hostNames.add( hostName.toLowerCase() );
			}
		}

		return hostNames;
	}

	/**
	 * @return The site's primary host name.
	 */
	public String primaryHostName() {
		NSArray<String> hostNames = hostNames();

		if( USArrayUtilities.hasObjects( hostNames() ) ) {
			return hostNames.objectAtIndex( 0 );
		}

		return null;
	}

	/**
	 * @return true if the site has the exact host name specified.
	 */
	public boolean hasHostName( String hostName ) {
		return hostNames().containsObject( hostName );
	}

	/**
	 * @return The site's locale.
	 */
	public Locale locale() {

		if( _locale == null && language() != null ) {
			try {
				_locale = new Locale( language() );
			}
			catch( Exception e ) {
				logger.error( "Couldn't construct locale for code: " + language(), e );
			}
		}

		return _locale;
	}

	/**
	 * @return Name of the site's locale in english.
	 */
	public String englishLanguageName() {

		if( locale() == null ) {
			return null;
		}

		return locale().getDisplayName( Locale.ENGLISH );
	}

	@Override
	public void setLanguage( String value ) {
		super.setLanguage( value );
		_locale = null;
	}

	@Override
	public String toStringHuman() {
		return name();
	}

	public static SWSite siteForHostName( EOEditingContext ec, String hostName ) {
		hostName = hostName.toLowerCase();
		EOQualifier q = new EOKeyValueQualifier( SWSite.QUAL_KEY, EOQualifier.QualifierOperatorLike, "*" + hostName + SITENAME_DELIMITER + "*" );
		EOFetchSpecification fs = new EOFetchSpecification( SWSite.ENTITY_NAME, q, null );

		NSArray<SWSite> sites = ec.objectsWithFetchSpecification( fs );

		if( !USArrayUtilities.hasObjects( sites ) ) {
			return null;
		}

		if( sites.count() == 1 ) {
			return sites.objectAtIndex( 0 );
		}

		logger.warn( "There are more than one sites matching the host name: " + hostName );

		for( SWSite current : sites ) {
			if( current.hasHostName( hostName ) ) {
				return current;
			}
		}

		return null;
	}
}