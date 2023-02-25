package com.buddies.services.activity.controller

import com.buddies.services.activity.service.ActivityService
import com.buddies.services.activity.testutils.TestUtils.headers
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(ActivityController::class)
class ActivityControllerTest {

    private val PATH = "/auth/user/activity";

    @MockBean
    private lateinit var activityService: ActivityService

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `create activity without request body should return bad request`() {
        mockMvc.perform(post(PATH).headers(headers))
            .andExpect(status().isBadRequest)
    }

//    @Test
//    fun `create activity without subject headers should return bad request`() {
//        mockMvc.perform(post(PATH).content())
//            .andExpect(status().isBadRequest)
//    }
}