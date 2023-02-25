package com.buddies.services.activity.service

import com.buddies.services.activity.dto.NewActivity
import com.buddies.services.activity.persistence.ActivityRepository
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.*;
import org.springframework.boot.test.mock.mockito.MockBean

import org.springframework.boot.test.mock.mockito.SpyBean
import java.time.LocalDate
import java.time.LocalTime

class ActivityServiceTest {

    @MockBean
    private lateinit var activityRepository: ActivityRepository

    @SpyBean
    private lateinit var activityService: ActivityService

    private val newActivity = NewActivity(
        title = "title",
        description = "description",
        imageUUID = "imageUUID",
        startDate = LocalDate.now(),
        endDate = LocalDate.now(),
        startTime = LocalTime.now(),
        endTime = LocalTime.now(),
        tagIds = listOf("tagId1", "tagId2")
    );

    @Test
    fun save() {
        `when`(activityRepository.save(any())).thenReturn(null).thenReturn(newActivity.toActivity())
    }
}