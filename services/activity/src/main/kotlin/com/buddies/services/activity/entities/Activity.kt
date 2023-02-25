package com.buddies.services.activity.entities

import com.mongodb.lang.NonNull
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate
import java.time.LocalTime

import java.util.*
import java.util.Collections.emptyList

@Document(collection = "activities")
class Activity (
        @Id
        var id: UUID = UUID.randomUUID(),
        @set:NonNull
        @field:NonNull
        var ownerUserId: String = "",
        @set:NonNull
        @field:NonNull
        var ownerUsername: String = "",
        @set:NonNull
        @field:NonNull
        var title: String,
        var description: String?,
        var imageUUID: String?,
        var startDate: LocalDate?,
        var endDate: LocalDate?,
        var startTime: LocalTime?,
        var endTime: LocalTime?,
        var participantIds: List<String> = emptyList(),
        var userIdsWithReadAccess: List<String> = emptyList(),
        var tagIds: List<String> = emptyList(),
)