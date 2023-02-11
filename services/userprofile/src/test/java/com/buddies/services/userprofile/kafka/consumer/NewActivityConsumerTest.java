package com.buddies.services.userprofile.kafka.consumer;

import com.buddies.common.shared.kafka.KafkaActivityMessage;
import com.buddies.services.userprofile.service.UserProfileService;
import com.buddies.services.userprofile.util.KafkaTestProducer;
import com.buddies.services.userprofile.util.KafkaTestProducerConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
@DirtiesContext
@EmbeddedKafka(
        partitions = 1,
        topics = "new-activity",
        brokerProperties = {
                "listeners=PLAINTEXT://localhost:9092",
                "port=9092"
        },
        controlledShutdown = false
)
@Import({ KafkaTestProducerConfig.class, KafkaTestProducer.class })
class NewActivityConsumerTest {

    @MockBean
    UserProfileService userProfileService;
    @Autowired private NewActivityConsumer consumer;
    @Autowired private KafkaTestProducer producer;


    @Test
    void consume() throws Exception {
        var message = new KafkaActivityMessage(1L, "activityId");
        producer.send("new-activity", message);
        var messageConsumed = consumer.getLatch().await(10000, TimeUnit.MILLISECONDS);
        assertEquals(message, consumer.getReceivedMessage());
    }
}