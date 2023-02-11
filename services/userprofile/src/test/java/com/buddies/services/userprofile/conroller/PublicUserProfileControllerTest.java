package com.buddies.services.userprofile.conroller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.buddies.services.userprofile.service.UserProfileService;
import com.buddies.common.shared.TestUtils;

@WebMvcTest(controllers = PublicUserProfileController.class)
public class PublicUserProfileControllerTest {
	private final String PATH = "/user-profile";
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	UserProfileService userProfileService;
	
	@Nested
	@DisplayName("Get user profile by username")
	class GetUserProfileByUsername {		
		private final String USERNAME_PATH = PATH + "/username/";
		@Test
		void getUserProfileByUsername_shouldReturnOk() throws Exception {
			mockMvc.perform(get(USERNAME_PATH + "test-user-name")).andExpect(status().isOk());
		}
		
		@Test
		void getUserProfileByUsernameWithUnvalidLength_shouldBadRequest() throws Exception {
			mockMvc.perform(get(USERNAME_PATH + TestUtils.randomString(101))).andExpect(status().isBadRequest());
		}
	}
	
	@Nested
	@DisplayName("Get user profile by id")
	class GetUserProfileById {
		private final String BY_ID_PATH = PATH + "/id/";
		@Test
		void getUserProfileByValidId_shouldReturnOk() throws Exception {
			mockMvc.perform(get(BY_ID_PATH + "1")).andExpect(status().isOk());
			mockMvc.perform(get(BY_ID_PATH + "999999999999")).andExpect(status().isOk());
		}
		
		@Test
		void getUserProfileByIdWithInvalidFormat_shouldReturnBadRequest() throws Exception {
			mockMvc.perform(get(BY_ID_PATH + "test")).andExpect(status().isBadRequest());
		}
		
		@Test
		void getUserProfileByIdWithInvalidValue_shouldReturnBadRequest() throws Exception {
			mockMvc.perform(get(BY_ID_PATH + "0")).andExpect(status().isBadRequest());
			mockMvc.perform(get(BY_ID_PATH + "-1")).andExpect(status().isBadRequest());
			mockMvc.perform(get(BY_ID_PATH + "-999999999999")).andExpect(status().isBadRequest());
		}
	}
}
