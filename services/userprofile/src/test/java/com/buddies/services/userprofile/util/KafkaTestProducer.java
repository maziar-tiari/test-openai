package com.buddies.services.userprofile.util;

import com.buddies.common.shared.kafka.KafkaActivityMessage;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Profile("test")
public class KafkaTestProducer {
    @Autowired private KafkaTemplate<String, KafkaActivityMessage> kafkaTemplate;

    public void send(String topic, KafkaActivityMessage message) {
        kafkaTemplate.send(topic, message);
    }
}
