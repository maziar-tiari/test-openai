package com.buddies.services.userprofile.service;

import java.util.Objects;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.buddies.services.userprofile.dto.NewUserProfile;
import com.buddies.services.userprofile.entitymodels.UserProfile;
import com.buddies.services.userprofile.persistence.UserProfileRepository;
import com.buddies.services.userprofile.types.Error;
import com.buddies.common.shared.Utils;

@Service
@RequiredArgsConstructor
@Validated
@Slf4j
public class UserProfileService {

	private final UserProfileRepository userProfileRepository;

	private final ModelMapper modelMapper = new ModelMapper();

	public UserProfile create(NewUserProfile newUserProfile, String username, String userId) {
		userProfileRepository.findByUserId(nonNullUserId(userId)).ifPresent(u -> { 
			throw new DataIntegrityViolationException(Error.USER_PROFILE_ALREADY_EXIST);
		});
		return save(newUserProfile, userId, username);
	}

	public UserProfile getByUsername(String username) {
		return userProfileRepository.findByUsername(nonNullUsername(username)).orElseThrow();
	}

	public UserProfile getByUserId(String userId) {
		return userProfileRepository.findByUserId(nonNullUserId(userId)).orElseThrow();
	}

	public UserProfile getById(Long id) {
		return userProfileRepository.findById(Objects.requireNonNull(id, "ID not defined")).orElseThrow();
	}

	public UserProfile update(NewUserProfile newUserProfile, String userId, String username) {
		var userProfile = userProfileForUpdate(userId, username);
		userProfile = Utils.updateObject(newUserProfile, userProfile, UserProfile.class);
		return userProfileRepository.save(userProfile);
	}

	public void addActivity(Long activityId, String userId, String username) {
		UserProfile userProfile = userProfileForUpdate(userId, username);
		userProfile.addActivityId(nonNullActivityId(activityId));
		userProfileRepository.save(userProfile);
	}

	public void deleteByUserId(String userId) {
		userProfileRepository.deleteByUserId(nonNullUserId(userId));
	}

	private UserProfile userProfileForUpdate(String userId, String username) {
		Optional<UserProfile> userProfile = userProfileRepository.findByUserId(nonNullUserId(userId));
		if (userProfile.isEmpty()) {
			return UserProfile.builder()
					.userId(userId)
					.username(nonNullUsername(username))
					.build();
		} else {
			return userProfile.get();
		}
	}

	private UserProfile save(NewUserProfile newUserProfile, String userId, String username) {
		UserProfile userProfile = modelMapper.map(newUserProfile, UserProfile.class);
		userProfile.setUsername(nonNullUsername(username));
		userProfile.setUserId(nonNullUserId(userId));
		return userProfileRepository.save(userProfile);
	}
	
	private String nonNullUserId(String userId) throws NullPointerException {
		return Objects.requireNonNull(userId, "User-ID not defined");
	}
	
	private String nonNullUsername(String username) throws NullPointerException {
		return Objects.requireNonNull(username, "Username not defined");
	}

	private Long nonNullActivityId(Long activityId) throws NullPointerException {
		return Objects.requireNonNull(activityId, "Activity-ID not defined");
	}
}
