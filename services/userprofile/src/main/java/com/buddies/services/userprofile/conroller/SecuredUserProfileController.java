package com.buddies.services.userprofile.conroller;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;

import static org.springframework.http.MediaType.*;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.buddies.services.userprofile.dto.NewUserProfile;
import com.buddies.services.userprofile.entitymodels.UserProfile;
import com.buddies.services.userprofile.service.UserProfileService;
import com.buddies.common.shared.BuddiesHttpHeaders;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/auth/user/user-profile")
@Validated
public class SecuredUserProfileController {
	private final UserProfileService userProfileService;

	@PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.CREATED)
	public UserProfile create(@Valid final @RequestBody NewUserProfile newUserProfile,
			@RequestHeader(BuddiesHttpHeaders.USER_ID) @NotBlank String userId,
			@RequestHeader(BuddiesHttpHeaders.USERNAME) @NotBlank String username) {
		return userProfileService.create(newUserProfile, username, userId);
	}

	@PutMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.ACCEPTED)
	public UserProfile update(
			final @Valid @RequestBody NewUserProfile newUserProfile,
			@RequestHeader(BuddiesHttpHeaders.USER_ID) @NotBlank String userId,
			@RequestHeader(BuddiesHttpHeaders.USERNAME) @NotBlank String username) {
		return userProfileService.update(newUserProfile, userId, username);
	}

	@DeleteMapping
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void delete(@RequestHeader(BuddiesHttpHeaders.USER_ID) @NotBlank String userId) {
		userProfileService.deleteByUserId(userId);
	}
	
	@GetMapping(produces = APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public UserProfile get(@RequestHeader(BuddiesHttpHeaders.USER_ID) @NotBlank String userId) {
		return userProfileService.getByUserId(userId);
	}
}
