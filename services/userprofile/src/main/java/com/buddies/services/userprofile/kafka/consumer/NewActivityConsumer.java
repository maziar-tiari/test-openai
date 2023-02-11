package com.buddies.services.userprofile.kafka.consumer;

import com.buddies.common.shared.kafka.KafkaActivityMessage;
import com.buddies.services.userprofile.service.UserProfileService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@RequiredArgsConstructor
@Component
@Slf4j
public class NewActivityConsumer {
    private final UserProfileService userProfileService;
    private @Getter CountDownLatch latch = new CountDownLatch(1);
    private @Getter KafkaActivityMessage receivedMessage;
    @KafkaListener(
            topics = "${app.kafka.topics.new-activity}",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "activityKafkaListenerContainerFactory"
    )
    public void consume(KafkaActivityMessage message) {
        log.info("New activity: {}", message);
        userProfileService.addActivity(message.userId(), message.activityId());
        latch.countDown();
        this.receivedMessage = message;
        System.out.println("Received Message: " + message);
    }

    public void resetLatch() {
        latch = new CountDownLatch(1);
    }
}
