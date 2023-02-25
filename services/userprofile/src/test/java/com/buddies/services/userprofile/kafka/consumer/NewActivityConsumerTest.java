package com.buddies.services.userprofile.kafka.consumer;

import com.buddies.services.userprofile.dto.KafkaActivityMessage;
import com.buddies.services.userprofile.service.UserProfileService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.*;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(properties = {
        "spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}"
})
@EmbeddedKafka(partitions = 1)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class NewActivityConsumerTest {
    @Autowired
    private EmbeddedKafkaBroker embeddedKafka;

    private Producer<String, String> producer;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    UserProfileService userProfileService;
    @SpyBean
    private NewActivityConsumer consumer;

    @Captor
    ArgumentCaptor<KafkaActivityMessage> messageCaptor;

    @BeforeAll
    void setUp() {
        Map<String, Object> configs = new HashMap<>(KafkaTestUtils.producerProps(embeddedKafka));
        producer = new DefaultKafkaProducerFactory<>(
                configs,
                new StringSerializer(),
                new StringSerializer()).createProducer();
    }

    @AfterAll
    void shutdown() {
        producer.close();
    }

    @Value("${app.kafka.topics.new-activity}")
    private String topic;


    @Test
    void consumeValidActivityMessage_shouldInvokeExpectedArgument() throws Exception {
        var kafkaActivityMessage = new KafkaActivityMessage(1L, "user_id_1", "john.doe");
        var message = objectMapper.writeValueAsString(kafkaActivityMessage);
        producer.send(new ProducerRecord<>(topic, message));
        producer.flush();
        verify(consumer, timeout(5000).times(1)).consume(kafkaActivityMessage);
        verify(userProfileService, times(1))
                .addActivity(
                        kafkaActivityMessage.activityId(),
                        kafkaActivityMessage.userId(),
                        kafkaActivityMessage.username());
    }

    @Test
    void sendInvalidArgumentToConsumer_shouldNotCallUserProfileService() throws Exception {
        record InvalidKafkaActivityMessage(Long activity_id, String user_ID) {}
        var kafkaActivityMessage = new InvalidKafkaActivityMessage(1L, "john.doe");
        var message = objectMapper.writeValueAsString(kafkaActivityMessage);
        producer.send(new ProducerRecord<>(topic, message));
        producer.flush();
        verify(consumer, timeout(5000).times(1))
                .consume(messageCaptor.capture());
        var receivedMessage = messageCaptor.getValue();
        assertNotNull(receivedMessage);
        assertNull(receivedMessage.userId());
        assertNull(receivedMessage.activityId());
        assertNull(receivedMessage.username());
        verify(userProfileService, times(0)).addActivity(any(), any(), any());
    }
}