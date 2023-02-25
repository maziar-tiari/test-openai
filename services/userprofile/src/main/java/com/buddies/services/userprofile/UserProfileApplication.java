package com.buddies.services.userprofile;

import com.buddies.services.userprofile.dto.KafkaActivityMessage;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootApplication(scanBasePackages = {
		"com.buddies.services.userprofile",
		"com.buddies.common.shared"
})
public class UserProfileApplication {
	public static void main(String[] args) {
		SpringApplication.run(UserProfileApplication.class, args);
	}

//	@Bean
//	CommandLineRunner init(KafkaTemplate<String, KafkaActivityMessage> kafkaTemplate) {
//		return args -> {
//			kafkaTemplate.send("new-activity", new KafkaActivityMessage(null, null));
//		};
//	}
}