package com.fcbackend.domain.model;

import com.fcbackend.domain.enums.TweetStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@EqualsAndHashCode
@Builder(access = AccessLevel.PRIVATE)
public class Tweet {

    private UUID id;
    private String message;
    private String publisher;
    private TweetStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime discardedAt;

    private Tweet() {}

    public static Tweet build(String message, String publisher) {

        return Tweet.builder()
                .message(message)
                .publisher(publisher)
                .status(TweetStatus.ACTIVATED)
                .build();
    }

    public static Tweet rebuild(
            UUID id,
            String message,
            String publisher,
            TweetStatus status,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            LocalDateTime discardedAt) {

        return Tweet.builder()
                .id(id)
                .message(message)
                .publisher(publisher)
                .status(status)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .discardedAt(discardedAt)
                .build();
    }
}
