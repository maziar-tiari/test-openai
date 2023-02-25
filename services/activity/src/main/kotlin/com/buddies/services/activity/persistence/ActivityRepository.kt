package com.buddies.services.activity.persistence

import com.buddies.services.activity.entities.Activity
import org.springframework.data.mongodb.repository.MongoRepository

interface ActivityRepository : MongoRepository<Activity, String> {
}