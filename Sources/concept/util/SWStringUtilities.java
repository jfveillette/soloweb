package concept.util;

import org.htmlparser.Parser;
import org.htmlparser.visitors.TextExtractingVisitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSDictionary;
import com.webobjects.foundation.NSMutableDictionary;
import com.webobjects.foundation.NSRange;
import com.webobjects.foundation.NSTimestamp;
import com.webobjects.foundation.NSTimestampFormatter;

/**
 * SWStringUtilities contains various utility methods for handling strings,
 * reading and writing strings to and from disks etc.
 */

@Deprecated
public class SWStringUtilities extends Object {

	private static final Logger logger = LoggerFactory.getLogger( SWStringUtilities.class );

	private static NSDictionary LEGAL_MAP = null;

	private SWStringUtilities() {}

	/**
	 * @return The first [wordCount] words from [string]
	 */
	public static String wordsFromString( String string, Integer wordCount ) {

		if( string == null || wordCount == null || wordCount.intValue() == 0 ) {
			return "";
		}

		NSArray<String> words = NSArray.componentsSeparatedByString( string, " " );

		if( words.count() <= wordCount ) {
			return string;
		}

		NSArray<String> ma = words.subarrayWithRange( new NSRange( 0, wordCount ) );
		String result = ma.componentsJoinedByString( " " );
		return result;
	}

	public static String stripHtmlFromString( String string ) {
		String newString = "";

		if( string != null && string.length() > 0 ) {
			try {
				Parser parser = new Parser();
				parser.setInputHTML( string );
				TextExtractingVisitor visitor = new TextExtractingVisitor();
				parser.visitAllNodesWith( visitor );
				newString = visitor.getExtractedText();
			}
			catch( Exception ex ) {
				logger.error( "Error in stripHtmlFromString: ", ex );
			}
		}

		return newString;
	}

	public static NSTimestamp stringToTimestamp( String string, String format ) {
		try {
			return (NSTimestamp)new NSTimestampFormatter( format ).parseObject( string );
		}
		catch( Exception ex ) {
			return null;
		}
	}

	/**
	 * Converts text to ascii, spaces and slashes to underscore etc.
	 */
	public static String legalName( String name ) {

		if( name == null ) {
			return "";
		}

		if( LEGAL_MAP == null ) {
			NSMutableDictionary d = new NSMutableDictionary();
			d.setObjectForKey( "a", "á".charAt( 0 ) );
			d.setObjectForKey( "A", "Á".charAt( 0 ) );
			d.setObjectForKey( "e", "é".charAt( 0 ) );
			d.setObjectForKey( "E", "É".charAt( 0 ) );
			d.setObjectForKey( "i", "í".charAt( 0 ) );
			d.setObjectForKey( "I", "Í".charAt( 0 ) );
			d.setObjectForKey( "o", "ó".charAt( 0 ) );
			d.setObjectForKey( "O", "Ó".charAt( 0 ) );
			d.setObjectForKey( "u", "ú".charAt( 0 ) );
			d.setObjectForKey( "U", "Ú".charAt( 0 ) );
			d.setObjectForKey( "y", "ý".charAt( 0 ) );
			d.setObjectForKey( "Y", "Ý".charAt( 0 ) );
			d.setObjectForKey( "th", "þ".charAt( 0 ) );
			d.setObjectForKey( "Th", "Þ".charAt( 0 ) );
			d.setObjectForKey( "d", "ð".charAt( 0 ) );
			d.setObjectForKey( "D", "Ð".charAt( 0 ) );
			d.setObjectForKey( "ae", "æ".charAt( 0 ) );
			d.setObjectForKey( "AE", "Æ".charAt( 0 ) );
			d.setObjectForKey( "o", "ö".charAt( 0 ) );
			d.setObjectForKey( "O", "Ö".charAt( 0 ) );
			d.setObjectForKey( "_", " ".charAt( 0 ) );
			d.setObjectForKey( "_", "/".charAt( 0 ) );
			d.setObjectForKey( "_", "*".charAt( 0 ) );
			d.setObjectForKey( "_", "?".charAt( 0 ) );
			d.setObjectForKey( "_", ":".charAt( 0 ) );
			d.setObjectForKey( "_", ";".charAt( 0 ) );
			d.setObjectForKey( "_", ".".charAt( 0 ) );
			d.setObjectForKey( "_", "-".charAt( 0 ) );
			d.setObjectForKey( "", (char)0x301 ); // accent marker ' appearing in zip files
			d.setObjectForKey( "", (char)0x308 ); // umlout ¨
			LEGAL_MAP = d.immutableClone();
		}

		StringBuilder b = new StringBuilder();

		for( int i = 0; i < name.length(); i++ ) {
			char c = name.charAt( i );

			Object o = LEGAL_MAP.objectForKey( c );

			if( o != null ) {
				b.append( o );
			}
			else {
				b.append( c );
			}
		}

		return b.toString();
	}
}