package com.buddies.services.activity.dto

import com.buddies.services.activity.entities.Activity
import jakarta.validation.constraints.Size
import java.time.LocalDate
import java.time.LocalTime
import java.util.Collections.emptyList

class NewActivity (
    @field:Size(min = 8, max = 250)
    val title: String,
    val description: String?,
    val imageUUID: String?,
    val startDate: LocalDate?,
    val endDate: LocalDate?,
    val startTime: LocalTime?,
    val endTime: LocalTime?,
    val tagIds: List<String> = emptyList(),
) {
    fun toActivity(userId: String, username: String): Activity = Activity(
        ownerUsername = username,
        ownerUserId = userId,
        title = title,
        description = description,
        imageUUID = imageUUID,
        startDate = startDate,
        endDate = endDate,
        startTime = startTime,
        endTime = endTime,
        tagIds = tagIds,
    )
}