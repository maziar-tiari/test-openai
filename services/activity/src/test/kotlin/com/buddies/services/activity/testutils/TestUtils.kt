package com.buddies.services.activity.testutils

import com.buddies.common.shared.BuddiesHttpHeaders
import com.buddies.services.activity.dto.NewActivity
import org.springframework.http.HttpHeaders
import java.time.LocalDate
import java.time.LocalTime

object TestUtils {
    val newActivity = NewActivity(
        title = "title",
        description = "description",
        imageUUID = "imageUUID",
        startDate = LocalDate.now(),
        endDate = LocalDate.now(),
        startTime = LocalTime.now(),
        endTime = LocalTime.now(),
        tagIds = listOf("tagId1", "tagId2")
    );

    val USER_ID = "userId";

    val USERNAME = "john.doe";

    val headers = HttpHeaders().apply {
        set(BuddiesHttpHeaders.USER_ID, USER_ID)
        set(BuddiesHttpHeaders.USERNAME, USERNAME)
    };
}