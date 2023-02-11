package com.buddies.common.shared;

import static java.util.Objects.requireNonNull;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;

public final class Utils {
	private static final ObjectMapper mapper = new ObjectMapper();

	private Utils() {
	}

	public static <T> T updateObject(Object srcObj, T destObj, Class<T> destType) throws IllegalArgumentException {
		var errorMsgPrefix = "Update not possible. ";
		ObjectReader objectReader = mapper.readerForUpdating(requireNonNull(destObj, errorMsgPrefix + "Null destination object"));
		try {
			var json = mapper.writeValueAsString(requireNonNull(srcObj, errorMsgPrefix + "Null source object"));
			return objectReader.readValue(json, requireNonNull(destType, errorMsgPrefix + "Null Destination type"));
		}  catch (NullPointerException e) {
			throw e;
		} catch (Exception e) { 
			throw new IllegalArgumentException(e.getMessage());
		}
	}
}
