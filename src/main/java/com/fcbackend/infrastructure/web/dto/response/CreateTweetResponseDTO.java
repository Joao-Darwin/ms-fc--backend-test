package com.fcbackend.infrastructure.web.dto.response;

import com.fcbackend.domain.enums.TweetStatus;

import java.util.UUID;

public record CreateTweetResponseDTO(
        UUID id,
        String publisher,
        String message,
        TweetStatus status
) {
}
