package com.buddies.services.activity.controller

import com.buddies.common.shared.BuddiesHttpHeaders
import com.buddies.services.activity.dto.NewActivity
import com.buddies.services.activity.entities.Activity
import com.buddies.services.activity.service.ActivityService
import jakarta.validation.Valid
import jakarta.validation.constraints.Min
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth/user/activity")
@Validated
class ActivityController(private val activityService: ActivityService) {
    @PostMapping
    fun createActivity(
            @RequestHeader(BuddiesHttpHeaders.USER_ID) @Min(1) userId: String,
            @RequestHeader(BuddiesHttpHeaders.USERNAME) @Min(1) username: String,
            @Valid @RequestBody newActivity: NewActivity): Activity {
        return activityService.save(newActivity, userId, username);
    }
}