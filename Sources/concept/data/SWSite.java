package concept.data;

import is.rebbi.core.util.StringUtilities;
import is.rebbi.wo.interfaces.HumanReadable;
import is.rebbi.wo.interfaces.SWHasCustomInfo;
import is.rebbi.wo.util.SWCustomInfo;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.webobjects.eocontrol.EOEditingContext;
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
		return SWSite.fetchAllSWSites( ec );
	}

	/**
	 * @return customInfo
	 */
	public SWCustomInfo customInfo() {
		if( _customInfo == null ) {
			_customInfo = new SWCustomInfo( this );
		}

		return _customInfo;
	}

	/**
	 * @return an array containing all domains this site handles.
	 */
	public NSArray<String> hosts() {
		NSMutableArray<String> list = new NSMutableArray<>();

		if( !StringUtilities.hasValue( qual() ) ) {
			return NSArray.emptyArray();
		}

		for( String next : qual().split( SITENAME_DELIMITER ) ) {
			String domain = next.trim();
			list.add( domain );
		}

		return list;
	}

	/**
	 * Sets the string containing the domain names this Site applies to
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
	 * @return The site's frontpage in an Array (for use with nested lists and such)
	 */
	public NSArray<SWPage> frontPageInArray() {
		return new NSArray<>( frontpage() );
	}

	/**
	 * This site's primary domain
	 */
	public String primaryDomain() {
		try {
			NSArray<String> hosts = NSArray.componentsSeparatedByString( qual(), "\n" );
			return hosts.objectAtIndex( 0 ).trim().toLowerCase();
		}
		catch( Exception e ) {
			return null;
		}
	}

	/**
	 * @return true if the site has the exact host name specified.
	 */
	public boolean hasHost( String host ) {
		return hosts().containsObject( host );
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
}