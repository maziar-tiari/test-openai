package com.buddies.services.activity.service

import com.buddies.services.activity.dto.NewActivity
import com.buddies.services.activity.entities.Activity
import com.buddies.services.activity.persistence.ActivityRepository
import com.buddies.services.activity.testutils.TestUtils.USERNAME
import com.buddies.services.activity.testutils.TestUtils.USER_ID
import com.buddies.services.activity.testutils.TestUtils.newActivity
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.*;
import org.springframework.boot.test.mock.mockito.MockBean

import org.springframework.boot.test.mock.mockito.SpyBean
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.time.LocalDate
import java.time.LocalTime

@ExtendWith(SpringExtension::class)
class ActivityServiceTest {

    @MockBean
    private lateinit var activityRepository: ActivityRepository

    @SpyBean
    private lateinit var activityService: ActivityService

    @Test
    fun save() {
        var expected = newActivity.toActivity(USER_ID, USERNAME);
        `when`(activityRepository.save(any(Activity::class.java))).thenReturn(expected)
        val result = activityService.save(newActivity, USER_ID, USERNAME)
        assertThat(result).isNotNull
        assertThat(result).isEqualTo(expected)
        assertThat(result.id).isNotNull
    }
}