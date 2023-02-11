package com.buddies.services.userprofile.util;

import java.util.List;

import org.springframework.http.HttpHeaders;

import com.buddies.common.shared.BuddiesHttpHeaders;
import com.buddies.services.userprofile.dto.NewUserProfile;
import com.buddies.services.userprofile.persistence.UserProfile;
import com.buddies.services.userprofile.types.Gender;
import com.buddies.services.userprofile.types.RelationshipStatus;

public interface UserProfileTestBuilder {
	
	public static final Long ID = 1L;
	public static final String USER_ID = "user_id";
	public static final String USERNAME = "john.doe";
	public static final String FIRST_NAME = "john";
	public static final String SURNAME = "doe";
	public static final Gender GENDER = Gender.M;
	public static final List<String> LANGUAGES = List.of("ENGLISH", "GERMAN");
	public static final RelationshipStatus RELATIONSHIP_STATUS = RelationshipStatus.MARRIED;
	
	public static final String UPDATED_SURNAME = "new surname";
	public static final RelationshipStatus UPDATED_RELATIONSHIP_STATUS = RelationshipStatus.DIVORCED;
	
	public static NewUserProfile defaultNewUserProfile() {
		var u = new NewUserProfile();
		u.setFirstName(FIRST_NAME);
		u.setSurname(SURNAME);
		u.setGender(GENDER);
		u.setLanguages(LANGUAGES);
		u.setRelationshipStatus(RELATIONSHIP_STATUS);
		return u;
	}
	
	public static UserProfile defaultSaveUserProfile() {
		return UserProfile.builder()
				.firstName(FIRST_NAME)
				.surname(SURNAME)
				.gender(GENDER)
				.languages(LANGUAGES)
				.relationshipStatus(RELATIONSHIP_STATUS)
				.username(USERNAME)
				.userId(USER_ID).build();
	}
	
	public static UserProfile defaultGetUserProfile() {
		var u = defaultSaveUserProfile();
		u.setId(ID);
		return u;
	}
	
	public static NewUserProfile updateUserProfileDto() {
		var u = defaultNewUserProfile();
		u.setSurname(UPDATED_SURNAME);
		u.setRelationshipStatus(UPDATED_RELATIONSHIP_STATUS);
		return u;
	}
	
	public static UserProfile updatedUserProfile() {
		var u = defaultGetUserProfile();
		u.setSurname(UPDATED_SURNAME);
		u.setRelationshipStatus(UPDATED_RELATIONSHIP_STATUS);
		return u;
	}
	
	private static HttpHeaders headers(String userId, String username) {
		HttpHeaders headers = new HttpHeaders();
		headers.add(BuddiesHttpHeaders.USER_ID, userId);
		headers.add(BuddiesHttpHeaders.USERNAME, username);
		return headers;
	}
	
	public static HttpHeaders subjectHeaders() {
		return headers(USER_ID, USERNAME);
	}
	
	public static HttpHeaders emptySubjectHeaders() {
		return headers("", "");
	}
}
