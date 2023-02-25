package com.buddies.services.userprofile.conroller;

import com.buddies.common.shared.BuddiesHttpHeaders;
import com.buddies.services.userprofile.service.UserProfileService;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth/service/user-profile/activity")
@Validated
public class UserProfileActivityController {

    private final UserProfileService userProfileService;

    @PostMapping("/{activityId}")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void addActivity(
            @PathVariable @NotBlank Long activityId,
            @RequestHeader(BuddiesHttpHeaders.USER_ID) @NotBlank String userId,
            @RequestHeader(BuddiesHttpHeaders.USERNAME) @NotBlank String username)
    {
        userProfileService.addActivity(activityId, userId, username);
    }
}
