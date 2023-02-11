package com.buddies.common.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestUtilsTest {
	
	@Test
	void testRandomString() {
	    assertEquals(TestUtils.randomString(1).length(), 1);
	    assertEquals(TestUtils.randomString(100).length(), 100);
	    assertEquals(TestUtils.randomString(99999).length(), 99999);
	}

}
