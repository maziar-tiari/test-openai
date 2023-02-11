package com.buddies.services.userprofile.conroller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;

import static com.buddies.services.userprofile.util.UserProfileJsonDtoUtils.*;
import static com.buddies.services.userprofile.util.UserProfileTestBuilder.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import com.buddies.services.userprofile.service.UserProfileService;

@WebMvcTest(controllers = SecuredUserProfileController.class)
class SecuredUserProfileControllerTest {
	private final String PATH = "/auth/user/user-profile";
	private final HttpHeaders SUB_HEADERS = subjectHeaders();
	private final HttpHeaders BLANK_SUB_HEADERS = emptySubjectHeaders();
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	UserProfileService userProfileService;
	
	@Nested
	@DisplayName("Create UserProfile")
	class TestCreateUserProfile {
		@Test
		void createUserProfileWithValidDto_shouldReturnIsCreated() throws Exception {
			mockMvc.perform(
						post(PATH)
						.content(getCreateUserProfileBody())
						.contentType(APPLICATION_JSON)
						.headers(SUB_HEADERS))
				.andExpect(MockMvcResultMatchers.status().isCreated());
		}
		
		@Test
		void createUserProfileWithoutSubjectHeader_shouldReturnBadRequest() throws Exception {
			mockMvc.perform(
						post(PATH)
						.content(getCreateUserProfileBody())
						.contentType(APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());
			
			mockMvc.perform(
					post(PATH)
					.headers(BLANK_SUB_HEADERS)
					.content(getCreateUserProfileBody())
					.contentType(APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isBadRequest());
		}
		
		@Test
		void createUserProfileWithInvalidValues_shouldReturnBadRequest() throws Exception {
			mockMvc.perform(
						post(PATH)
						.content(getCreateUserProfileBody_withInvalidValues())
						.headers(SUB_HEADERS)
						.contentType(APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());
		}
	}
	
	@Nested
	@DisplayName("Update UserProfile")
	class UpdateUserProfile {
		@Test
		void updateUserProfileWithValidDto_shouldReturnIsAccepted() throws Exception {
			mockMvc.perform(
						put(PATH)
						.content(getUpdateUserProfileBody())
						.contentType(APPLICATION_JSON)
						.headers(SUB_HEADERS))
				.andExpect(MockMvcResultMatchers.status().isAccepted());
		}
		
		@Test
		void updateUserProfileWithoutSubjectHeader_shouldReturnBadRequest() throws Exception {
			mockMvc.perform(
						put(PATH)
						.content(getUpdateUserProfileBody())
						.contentType(APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());
			
			mockMvc.perform(
					put(PATH)
					.headers(BLANK_SUB_HEADERS)
					.content(getUpdateUserProfileBody())
					.contentType(APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isBadRequest());
		}
		
		@Test
		void updateUserProfileWithInvalidValues_shouldReturnBadRequest() throws Exception {
			mockMvc.perform(
						put(PATH)
						.content(getUpdateUserProfileDto_withInvalidValues())
						.headers(SUB_HEADERS)
						.contentType(APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());
		}
	}
	
	@Nested
	@DisplayName("Delete UserProfile")
	class DeleteUserProfile {
		@Test
		void deleteUserProfile_shouldReturnNoContent() throws Exception {
			mockMvc.perform(delete(PATH).headers(SUB_HEADERS))
				.andExpect(MockMvcResultMatchers.status().isNoContent());
		}
		@Test
		void deleteUserProfileWithoutSubjectHeader_shouldReturnBadRequest() throws Exception {
			mockMvc.perform(delete(PATH))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());
			
			mockMvc.perform(delete(PATH).headers(BLANK_SUB_HEADERS))
			.andExpect(MockMvcResultMatchers.status().isBadRequest());
		}
	}
	
	@Nested
	@DisplayName("Get UserProfile")
	class GetUserProfile {
		@Test
		void getUserProfile_shouldReturnOk() throws Exception {
			mockMvc.perform(get(PATH).headers(SUB_HEADERS))
				.andExpect(MockMvcResultMatchers.status().isOk());
		}
		
		@Test
		void getUserProfileWithoutSubjectHeader_shouldReturnBadRequest() throws Exception {
			mockMvc.perform(get(PATH))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());
			
			mockMvc.perform(get(PATH).headers(BLANK_SUB_HEADERS))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());
		}
	}
	
}
