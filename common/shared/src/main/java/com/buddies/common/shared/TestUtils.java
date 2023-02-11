package com.buddies.common.shared;

import org.modelmapper.internal.bytebuddy.utility.RandomString;

public interface TestUtils {
	
	public static String randomString(int length) {
		return RandomString.make(length);
	}
}
