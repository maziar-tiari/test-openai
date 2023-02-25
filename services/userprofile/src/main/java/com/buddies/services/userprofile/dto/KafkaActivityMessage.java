package com.buddies.services.userprofile.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record KafkaActivityMessage(@NotNull Long activityId, @NotBlank String userId, @NotBlank String username) {
}
