package com.buddies.services.activity.service

import com.buddies.services.activity.dto.NewActivity
import com.buddies.services.activity.persistence.ActivityRepository
import org.springframework.stereotype.Service

@Service
class ActivityService(private val activityRepository: ActivityRepository) {

    fun save(newActivity: NewActivity, userId: String, username: String) {
        val a = listOf("a", "b", "c");
        val activity = newActivity.toActivity()
        activity.ownerUserUUID = userId
        activity.ownerUsername = username
        activityRepository.save(activity)
    }
}