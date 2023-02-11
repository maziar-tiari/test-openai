package com.buddies.services.userprofile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
		"com.buddies.services.userprofile",
		"com.buddies.common.shared"
})
public class UserProfileApplication {
	public static void main(String[] args) {
		SpringApplication.run(UserProfileApplication.class, args);
	}
}