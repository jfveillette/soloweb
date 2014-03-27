package concept.data;

import static org.junit.Assert.assertEquals;
import is.rebbi.wo.util.SWSettings;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import com.wounit.rules.MockEditingContext;

public class TestSWPicture {

	private MockEditingContext ec = new MockEditingContext( "SoloWeb", "erprototypes" );
	private String testHome = "/tmp/testHome";

	@Before
	public void setup() {
		File f = new File( testHome );
		f.mkdir();
		SWSettings.register( testHome );
		assertEquals( testHome, SWSettings.home() );
	}

	@Test
	public void paths() {
		SWPicture picture = new SWPicture();
		ec.insertSavedObject( picture );
		picture.setName( "hugi.jpg" );
//		System.out.println( picture.displayName() );

		/*
		CACompany company = CACompany.createCACompany( ec );
		CAUser user = CAUser.createCAUser( ec );

		CACompanyUser companyUser = CACompanyUser.createCACompanyUser( ec );
		companyUser.setCompany( company );
		companyUser.setUser( user );

		CAGroup group = CAGroup.createCAGroup( ec );

		user.addToGroups( group );

		assertTrue( user.isInGroup( group ) );
		assertFalse( user.hasPrivilegeFor( company, "admin" ) );
		user.addPrivilege( company, "admin" );
		assertTrue( user.hasPrivilegeFor( company, "admin" ) );
		*/
	}
}