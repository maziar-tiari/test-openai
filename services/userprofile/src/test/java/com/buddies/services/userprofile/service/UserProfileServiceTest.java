package com.buddies.services.userprofile.service;

import static com.buddies.services.userprofile.util.UserProfileTestBuilder.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import com.buddies.services.userprofile.dto.NewUserProfile;
import com.buddies.services.userprofile.persistence.UserProfile;
import com.buddies.services.userprofile.persistence.UserProfileRepository;

@ExtendWith(MockitoExtension.class)
class UserProfileServiceTest {

	@Mock
	private UserProfileRepository userProfileRepository;
	
	@InjectMocks
	private UserProfileService userProfileService;
	private final UserProfile getUserProfile = defaultGetUserProfile();
	private final UserProfile userProfile = defaultSaveUserProfile();
	private final NewUserProfile newUserProfile = defaultNewUserProfile();
	
	@Nested
	@DisplayName("Create user profile")
	class CreateUserProfile {		
		@Test
		void createNotExistingUserProfile_shouldReturnUserProfile() throws Exception {
			when(userProfileRepository.save(userProfile)).thenReturn(getUserProfile);			
			var result = userProfileService.create(newUserProfile, USERNAME, USER_ID);
			assertThat(result).isEqualTo(getUserProfile);
		}
		
		@Test
		void createUserProfileWhenAlreadyExists_shouldThrowDataIntegrityViolationException() {
			when(userProfileRepository.findByUserId(userProfile.getUserId()))
				.thenReturn(Optional.of(getUserProfile));
			assertThrows(
					DataIntegrityViolationException.class, 
					() ->  userProfileService.create(newUserProfile, USERNAME, USER_ID));
		}
		
		@Test
		void createUserProfileWithNullNewUserProfile_shouldIllegalArgumentException() {
			assertThrows(
					IllegalArgumentException.class, 
					() ->  userProfileService.create(null, USERNAME, USER_ID));
		}
		
		@Test
		void createUserProfileWithNullUsername_shouldNullPointerException() {
			assertThrows(
					NullPointerException.class, 
					() ->  userProfileService.create(newUserProfile, null, USER_ID));
		}
		
		@Test
		void createUserProfileWithNullUserId_shouldNullPointerException() {
			assertThrows(
					NullPointerException.class, 
					() ->  userProfileService.create(newUserProfile, USERNAME, null));
		}
	}
	
	@Nested
	@DisplayName("Get user profile by username")
	class GetUserProfileByUsername {
		@Test
		void getUserProfileByNullUsername_shouldReturnNullPointerException() {
			assertThrows(
					NullPointerException.class,
					() -> userProfileService.getByUsername(null));
		}
		
		@Test
		void getExistingUserProfileByUsername_shouldReturnUserProfile() {
			when(userProfileRepository.findByUsername(USERNAME)).thenReturn(Optional.of(getUserProfile));
			assertThat(userProfileService.getByUsername(USERNAME)).isEqualTo(getUserProfile);
		}
		
		@Test
		void getNonExistingUserProfileByUsername_shouldReturnNoSuchElementException() {
			when(userProfileRepository.findByUsername(USERNAME)).thenReturn(Optional.empty());
			assertThrows(
					NoSuchElementException.class,
					() -> userProfileService.getByUsername(USERNAME));
		}
	}
	
	@Nested
	@DisplayName("Get user profile by user id")
	class GetUserProfileByUserId {
		@Test
		void getUserProfileByNullUserId_shouldReturnNullPointerException() {
			assertThrows(
					NullPointerException.class,
					() -> userProfileService.getByUserId(null));
		}
		
		@Test
		void getNonExistingUserProfileByUserId_shouldReturnNoSuchElementException() {
			when(userProfileRepository.findByUserId(USER_ID)).thenReturn(Optional.empty());
			assertThrows(
					NoSuchElementException.class,
					() -> userProfileService.getByUserId(USER_ID));
		}
		
		@Test
		void getExistingUserProfileByUserId_shouldReturnUserProfile() {
			when(userProfileRepository.findByUserId(USER_ID)).thenReturn(Optional.of(getUserProfile));
			assertThat(userProfileService.getByUserId(USER_ID)).isEqualTo(getUserProfile);
		}
	}

	@Nested
	@DisplayName("Get user profile by id")
	class GetUserProfileById {
		@Test
		void getUserProfileByNullId_shouldReturnNullPointerException() {
			assertThrows(
					NullPointerException.class,
					() -> userProfileService.getById(null));
		}
		
		@Test
		void getNonExistingUserProfileById_shouldReturnNoSuchElementException() {
			when(userProfileRepository.findById(ID)).thenReturn(Optional.empty());
			assertThrows(
					NoSuchElementException.class,
					() -> userProfileService.getById(ID));
		}
		
		@Test
		void getExistingUserProfileById_shouldReturnUserProfile() {
			when(userProfileRepository.findById(ID)).thenReturn(Optional.of(getUserProfile));
			assertThat(userProfileService.getById(ID)).isEqualTo(getUserProfile);
		}
	}
	
	@Nested
	@DisplayName("Update user profile")
	class UpdateUserProfile {
		@Test
		void updateUserProfileByNullArguments_shouldReturnNullPointerException() {
			assertThrows(
					NullPointerException.class,
					() -> userProfileService.update(newUserProfile, null));
			when(userProfileRepository.findByUserId(USER_ID)).thenReturn(Optional.of(getUserProfile));
			assertThrows(
					NullPointerException.class,
					() -> userProfileService.update(null, USER_ID));
		}
		
		@Test
		void updateNonExistingUserProfile_shouldReturnNoSuchElementException() {
			when(userProfileRepository.findByUserId(USER_ID)).thenReturn(Optional.empty());
			assertThrows(
					NoSuchElementException.class,
					() -> userProfileService.update(newUserProfile, USER_ID));
		}
		
		@Test
		void updateUserProfile_shouldReturnUpdatedUserProfile() {
			when(userProfileRepository.findByUserId(USER_ID)).thenReturn(Optional.of(getUserProfile));
			when(userProfileRepository.save(updatedUserProfile())).thenReturn(updatedUserProfile());
			var result = userProfileService.update(updateUserProfileDto(), USER_ID);
			assertThat(result).isEqualTo(updatedUserProfile());
		}
	}
}
