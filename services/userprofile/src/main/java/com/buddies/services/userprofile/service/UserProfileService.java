package com.buddies.services.userprofile.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.buddies.services.userprofile.dto.NewUserProfile;
import com.buddies.services.userprofile.persistence.UserProfile;
import com.buddies.services.userprofile.persistence.UserProfileRepository;
import com.buddies.services.userprofile.types.Error;
import com.buddies.common.shared.Utils;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Validated
@Slf4j
public class UserProfileService {

	private final UserProfileRepository userProfileRepository;

	private final ModelMapper modelMapper = new ModelMapper();

	public UserProfile create(NewUserProfile newUserProfile, String username, String userId) {
		userProfileRepository.findByUserId(nonNullUserId(userId)).ifPresent(u -> { 
			throw new DataIntegrityViolationException(Error.USER_PROFILE_ALREADY_EXIST);
		});
		UserProfile userProfile = modelMapper.map(newUserProfile, UserProfile.class);
		userProfile.setUsername(nonNullUsername(username));
		userProfile.setUserId(userId);
		return userProfileRepository.save(userProfile);
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

	public UserProfile update(NewUserProfile newUserProfile, String userId) {
		UserProfile userProfile = userProfileRepository.findByUserId(nonNullUserId(userId)).orElseThrow();
		userProfile = Utils.updateObject(newUserProfile, userProfile, UserProfile.class);
		return userProfileRepository.save(userProfile);
	}

	public void addActivity(String userId, Long activityId) {
		// TODO: handle if null
		if (activityId == null || userId == null) {
			log.error("Activity ID or User ID is null");
			return;
		};
		Optional<UserProfile> userProfile = userProfileRepository.findByUserId(userId);
		userProfile.ifPresent(u -> {
			u.addActivityId(activityId);
			userProfileRepository.save(u);
		});
		if (userProfile.isEmpty()) {
			UserProfile newProfile = UserProfile.builder()
					.userId(userId)
					.activityIds(List.of(activityId))
					.build();
			try {
				userProfileRepository.save(newProfile);
			} catch (Exception e) {
				log.error("Error saving new user profile: {}", e.getMessage());
			}
			userProfileRepository.save(newProfile);
		}
	}

	public void deleteByUserId(String userId) {
		userProfileRepository.deleteByUserId(nonNullUserId(userId));
	}
	
	private String nonNullUserId(String userId) throws NullPointerException {
		return Objects.requireNonNull(userId, "User-ID not defined");
	}
	
	private String nonNullUsername(String username) throws NullPointerException {
		return Objects.requireNonNull(username, "Username not defined");
	}

}
