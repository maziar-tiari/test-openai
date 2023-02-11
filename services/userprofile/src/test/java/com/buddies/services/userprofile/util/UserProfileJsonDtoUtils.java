package com.buddies.services.userprofile.util;


import java.io.IOException;
import java.nio.file.Files;

import org.springframework.core.io.ClassPathResource;

public interface UserProfileJsonDtoUtils {

	public static String getCreateUserProfileBody() throws IOException {
		return readFromFile("/createUserProfile/create_user_profile_dto.json");
	}
	
	public static String getCreateUserProfileBody_withInvalidValues() throws IOException {
		return readFromFile("/createUserProfile/create_user_profile_dto_with_invalid_values.json");
	}

	public static String getExpectedCreatedUserProfile() throws IOException {
		return readFromFile("/createUserProfile/created_user_profile.json");
	}

	public static String getUpdateUserProfileBody() throws IOException {
		return readFromFile("/updateUserProfile/update_user_profile_dto.json");
	}

	public static String getExpectedUpdatedUserProfile() throws IOException {
		return readFromFile("/updateUserProfile/updated_user_profile.json");
	}

	public static String getUpdateUserProfileDto_withInvalidValues() throws IOException {
		return readFromFile("/updateUserProfile/update_user_profile_dto_with_invalid_values.json");
	}
	
	private static String readFromFile(String filePath) throws IOException {
		return new String(Files.readAllBytes(new ClassPathResource(filePath).getFile().toPath()));
	}	
}
