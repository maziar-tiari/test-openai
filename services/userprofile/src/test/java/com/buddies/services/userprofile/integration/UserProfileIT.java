
package com.buddies.services.userprofile.integration;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static com.buddies.services.userprofile.util.UserProfileJsonDtoUtils.*;
import static com.buddies.services.userprofile.util.UserProfileTestBuilder.*;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import static org.springframework.http.HttpMethod.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import org.springframework.http.ResponseEntity;

import com.buddies.services.userprofile.UserProfileApplication;
import com.buddies.services.userprofile.entitymodels.UserProfile;
import com.buddies.services.userprofile.persistence.UserProfileRepository;
import com.buddies.services.userprofile.util.PostgresContainerProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

@Tag("IntegrationTest")
@SpringBootTest(classes = UserProfileApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserProfileIT extends PostgresContainerProvider {
	private final static ObjectMapper mapper = new ObjectMapper();
	private final String SECURED_PATH = "/auth/user/user-profile";
	private final String PUBLIC_PATH = "/user-profile";
	
	private final HttpHeaders headers = subjectHeaders();
	
	@Autowired
	protected TestRestTemplate restTemplate;

	@Autowired
	UserProfileRepository userProfileRepository;
	
	@AfterEach
	void tearDown() {
		userProfileRepository.deleteByUserId(USER_ID);
	}
	
	@BeforeAll
	static void setup() {
		JavaTimeModule javaTimeModule = new JavaTimeModule();
		javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ISO_DATE));
		mapper.registerModule(javaTimeModule);
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
	}

	@Nested
	@DisplayName("POST /auth/user/user-profile")
	class CreateUserProfile {
		@Test
		void createUserProfileWithValidRequest_shouldReturnCreatedUserProfile_andPersistedInDb() throws Exception {
			var response = userRequest(getCreateUserProfileBody(), POST);
			assertThat(response.getStatusCode().value()).isEqualTo(CREATED.value());
			var expected = getExpectedCreatedUserProfile();
			var actual = response.getBody();
			JSONAssert.assertEquals(expected, actual, JSONCompareMode.LENIENT);
			Optional<UserProfile> created = userProfileRepository.findByUserId(USER_ID);
			assertThat(created).isPresent();
		}
	}

	@Nested
	@DisplayName("PUT /auth/user/user-profile")
	class UpdateUserProfile {
		@Test
		void updateUserProfile_shouldReturnUpdatedUserProfile() throws Exception {
			var createdUserProfile = saveNewUserProfile();
			var response = userRequest(getUpdateUserProfileBody(), PUT);
			Optional<UserProfile> updatedUserProfile = userProfileRepository.findById(createdUserProfile.getId());
			var actual = mapper.writeValueAsString(updatedUserProfile.get());
			var expected = getUserProfileJson(getExpectedUpdatedUserProfile(), createdUserProfile.getId());
			
			assertThat(response.getStatusCode().value()).isEqualTo(ACCEPTED.value());
			JSONAssert.assertEquals(expected, actual, JSONCompareMode.LENIENT);
		}
		
		@Test
		void updateNonExistingUserProfile_shouldReturnNewUserProfile() throws Exception {
			var response = userRequest(getUpdateUserProfileBody(), PUT);
			var expected = getExpectedUpdatedUserProfile();
			assertThat(response.getStatusCode().value()).isEqualTo(ACCEPTED.value());
			JSONAssert.assertEquals(expected, response.getBody(), JSONCompareMode.LENIENT);
		}
	}
	
	@Nested
	@DisplayName("DELETE /auth/user/user-profile")
	class DeleteUserProfile {		
		@Test
		void deleteExistingUserProfile_shouldReturnNoContent_andRemoveFromDB() throws Exception {
			UserProfile u = saveNewUserProfile();
			var response = userRequest(DELETE);
			assertThat(response.getStatusCode().value()).isEqualTo(NO_CONTENT.value());
			Optional<UserProfile> deletedUserProfile = userProfileRepository.findById(u.getId());
			assertThat(deletedUserProfile).isEmpty();
		}
		
		@Test
		void deleteNonExistingUserProfile_shouldReturnNoContent() throws Exception {
			var response = userRequest(DELETE);
			assertThat(response.getStatusCode().value()).isEqualTo(NO_CONTENT.value());
		}
	}
	
	@Nested
	@DisplayName("GET /auth/user/user-profile")
	class GetByUserId {
		@Test
		void getExistingUserProfileByUserId_returnsUserProfileWithStatusOk() throws Exception {
			var u = saveNewUserProfile();
			var expected = mapper.writeValueAsString(u);
			var response = userRequest(GET);
			assertThat(response.getStatusCode().value()).isEqualTo(OK.value());
			JSONAssert.assertEquals(expected, response.getBody(), JSONCompareMode.LENIENT);
		}
		
		@Test
		void getNonExistingUserProfileByUserId_returnsNotFound() throws Exception {
			var response = userRequest(GET);
			assertThat(response.getStatusCode().value()).isEqualTo(NOT_FOUND.value());
		}
	}
	
	@Nested
	@DisplayName("GET /user-profile/username")
	class GetByUsername {
		private final String PATH = PUBLIC_PATH + "/username/" + USERNAME;
		@Test
		void getExistingUserProfileByUsername_returnsUserProfileWithStatusOk() throws Exception {
			var u = saveNewUserProfile();
			var expected = mapper.writeValueAsString(u);
			var response = restTemplate.getForEntity(PATH, String.class);
			assertThat(response.getStatusCode().value()).isEqualTo(OK.value());
			JSONAssert.assertEquals(expected, response.getBody(), JSONCompareMode.LENIENT);
		}
		
		@Test
		void getNonExistingUserProfileByUsername_returnsNotFound() throws Exception {
			var response = restTemplate.getForEntity(PATH, String.class);
			assertThat(response.getStatusCode().value()).isEqualTo(NOT_FOUND.value());
		}
	}
	
	@Nested
	@DisplayName("GET /user-profile/id")
	class GetById {
		private ResponseEntity<String> getRequest(Long id) {
			return restTemplate.getForEntity(PUBLIC_PATH + "/id/" + id.toString(), String.class);
		}
		@Test
		void getExistingUserProfileById_returnsUserProfileWithStatusOk() throws Exception {
			var u = saveNewUserProfile();
			var expected = mapper.writeValueAsString(u);
			var response = getRequest(u.getId());
			assertThat(response.getStatusCode().value()).isEqualTo(OK.value());
			JSONAssert.assertEquals(expected, response.getBody(), JSONCompareMode.LENIENT);
		}
		
		@Test
		void getNonExistingUserProfileById_returnsNotFound() throws Exception {
			var response = getRequest(1000L);
			assertThat(response.getStatusCode().value()).isEqualTo(NOT_FOUND.value());
		}
	}
	
	private ResponseEntity<String> userRequest(String body, HttpMethod method) {
		var h = headers;
		if (body != null) {
			h.setContentType(APPLICATION_JSON);
		}
		return restTemplate.exchange(SECURED_PATH, method, new HttpEntity<>(body, h), String.class);
	}
	
	private ResponseEntity<String> userRequest(HttpMethod method) {
		return userRequest(null, method);
	}
	
	
	private String getUserProfileJson(String dto, Long id) throws Exception {
		UserProfile u = mapper.readValue(dto, UserProfile.class);
		u.setId(id);
		return mapper.writeValueAsString(u);
	}
	
	private UserProfile saveNewUserProfile() throws Exception {
		UserProfile userProfile = mapper.readValue(getCreateUserProfileBody(), UserProfile.class);
		userProfile.setUserId(USER_ID);
		userProfile.setUsername(USERNAME);
		return userProfileRepository.save(userProfile);
	}
}
