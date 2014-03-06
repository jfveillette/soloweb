package concept.urls;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import er.extensions.eof.ERXKeyGlobalID;

public class TestSWPKSerialization {

	@Test
	public void serializeOneInteger() {
		ERXKeyGlobalID gid = new ERXKeyGlobalID( "AnEntityName", new Object[] { "aPrimaryKeyValue" } );
		String actual = SWPKSerialization.serialize( gid.globalID() );
		assertEquals( "aPrimaryKeyValue", actual );
	}

	@Test
	public void serializeOneString() {
		ERXKeyGlobalID gid = new ERXKeyGlobalID( "AnEntityName", new Object[] { 10 } );
		String actual = SWPKSerialization.serialize( gid.globalID() );
		assertEquals( "10", actual );
	}

	@Test
	public void serializeTwoMixed() {
		ERXKeyGlobalID gid = new ERXKeyGlobalID( "AnEntityName", new Object[] { 10, "smu" } );
		String actual = SWPKSerialization.serialize( gid.globalID() );
		assertEquals( "10|smu", actual );
	}
}