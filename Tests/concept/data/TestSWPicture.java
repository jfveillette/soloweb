package concept.data;

import static org.junit.Assert.assertEquals;
import is.rebbi.wo.util.SWSettings;

import java.io.File;
import java.io.UnsupportedEncodingException;

import org.junit.Before;
import org.junit.Test;

import com.webobjects.foundation.NSData;
import com.wounit.rules.MockEditingContext;

public class TestSWPicture {

	private MockEditingContext ec = new MockEditingContext( "SoloWeb", "erprototypes" );
	private String testHome = "/tmp/testHome";

	@Before
	public void setup() {
		File f = new File( testHome );
		f.mkdir();
		SWSettings.register( testHome );

		File imageLocation = new File( SWSettings.imagePath() );
		File settingsLocation = new File( SWSettings.settingsPath() );

		if( !imageLocation.exists() ) {
			imageLocation.mkdir();
		}

		if( !settingsLocation.exists() ) {
			settingsLocation.mkdir();
		}

		assertEquals( testHome, SWSettings.home() );
		SWSettings.setObjectForKey( "http://www.apple.com/", "imageLocationOnServer" );
	}

	@Test
	public void pictureURL() {
		SWPicture picture = new SWPicture();
		ec.insertSavedObject( picture );
//		picture.setName( "Name" );
		System.out.println( "pictureURL: " + picture.pictureURL() );
	}

	@Test
	public void paths() {
		SWPicture picture = new SWPicture();
		ec.insertSavedObject( picture );
		System.out.println( picture.primaryKey() );
		picture.setName( "hugi.jpg" );
		System.out.println( picture.pictureURL() );
		picture.setData( testData() );
		picture.deleteAsset();
	}

	private NSData testData() {
		try {
			return new NSData( "String".getBytes( "UTF-8" ) );
		}
		catch( UnsupportedEncodingException e ) {
			e.printStackTrace();
			return null;
		}
	}
}