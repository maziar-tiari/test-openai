package com.buddies.services.userprofile.kafka.consumer;

import com.buddies.services.userprofile.dto.KafkaActivityMessage;
import com.buddies.services.userprofile.service.UserProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@RequiredArgsConstructor
@Component
@Slf4j
public class NewActivityConsumer {
    private final UserProfileService userProfileService;

    @KafkaListener(
            topics = "${app.kafka.topics.new-activity}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consume(@Payload KafkaActivityMessage message)  {
        log.info("On topic: {}, consumed message: {}", "${app.kafka.topics.new-activity}", message);
        if (message.activityId() == null || message.userId() == null) {
            return;
        }
        userProfileService.addActivity(message.activityId(), message.userId(), message.username());
    }
}
