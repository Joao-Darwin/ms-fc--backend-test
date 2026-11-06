package com.fcbackend.infrastructure.web.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record DiscardedTweetsResponseDTO(
        List<Tweet> tweets
) {

    @Data
    @Builder
    public static class Tweet {

        private UUID id;
        private String publisher;
        private String message;
        private LocalDateTime discardedAt;
    }
}
