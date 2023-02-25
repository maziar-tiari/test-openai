package com.buddies.services.activity.service

import com.buddies.services.activity.dto.NewActivity
import com.buddies.services.activity.entities.Activity
import com.buddies.services.activity.persistence.ActivityRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.ResponseStatus

@Service
class ActivityService(private val activityRepository: ActivityRepository) {

    @ResponseStatus(HttpStatus.OK)
    fun save(newActivity: NewActivity, userId: String, username: String): Activity {
        val activity = newActivity.toActivity(userId, username)
        return activityRepository.save(activity)
    }
}