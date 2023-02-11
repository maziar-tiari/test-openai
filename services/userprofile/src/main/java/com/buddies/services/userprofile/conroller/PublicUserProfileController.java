package com.buddies.services.userprofile.conroller;

import org.springframework.http.HttpStatus;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.buddies.services.userprofile.persistence.ColumnLength;
import com.buddies.services.userprofile.persistence.UserProfile;
import com.buddies.services.userprofile.service.UserProfileService;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/user-profile")
@AllArgsConstructor
@Validated
public class PublicUserProfileController {

	private final UserProfileService userProfileService;

	@GetMapping(path = "/username/{username}", produces = APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public UserProfile getByUsername(@PathVariable("username") @NotBlank @Size(max = ColumnLength.USERNAME) String username) {
		return userProfileService.getByUsername(username);
	}

	@GetMapping(path = "/id/{id}", produces = APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public UserProfile getById(@PathVariable("id") @Min(1) Long id) {
		return userProfileService.getById(id);
	}
}
