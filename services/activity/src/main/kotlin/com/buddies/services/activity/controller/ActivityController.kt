package com.buddies.services.activity.controller

import com.buddies.services.activity.dto.NewActivity
import com.buddies.services.activity.entities.Activity
import com.buddies.services.activity.service.ActivityService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth/user/activity")
class ActivityController(activityService: ActivityService) {
    @PostMapping
    fun createActivity(
        @RequestHeader(BuddiesHttpHeaders) userId: String,
        @Valid @RequestBody newActivity: NewActivity): Activity {
        return activityService.save(newActivity);
    }
}