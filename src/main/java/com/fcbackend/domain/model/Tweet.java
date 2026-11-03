package com.fcbackend.domain.model;

import com.fcbackend.domain.enums.TweetStatus;
import com.fcbackend.domain.exception.BusinessException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.regex.Pattern;

@Getter
@EqualsAndHashCode
@Builder(access = AccessLevel.PRIVATE)
public class Tweet {

    private static final int MAX_MESSAGE_LENGTH = 140;
    private static final Pattern URL_PATTERN = Pattern.compile("https?://\\S+");

    private UUID id;
    private String message;
    private String publisher;
    private TweetStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime discardedAt;

    public static Tweet build(String message, String publisher) {

        Tweet tweet = Tweet.builder()
                .message(message)
                .publisher(publisher)
                .status(TweetStatus.ACTIVATED)
                .build();

        selfValidate(tweet);

        return tweet;
    }

    public static Tweet rebuild(
            UUID id,
            String message,
            String publisher,
            TweetStatus status,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            LocalDateTime discardedAt) {

        Tweet tweet = Tweet.builder()
                .id(id)
                .message(message)
                .publisher(publisher)
                .status(status)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .discardedAt(discardedAt)
                .build();

        selfValidate(tweet);

        return tweet;
    }

    private static void selfValidate(Tweet tweet) {
        if (tweet.publisher == null || tweet.publisher.isBlank()) {
            throw new BusinessException("publisher can't be null.");
        }

        if (tweet.message == null || tweet.message.isBlank()) {
            throw new BusinessException("message can't be null.");
        }

        String cleanMessage = URL_PATTERN
                .matcher(tweet.message)
                .replaceAll("");

        if (cleanMessage.length() > MAX_MESSAGE_LENGTH) {
            throw new BusinessException("message max length is 140 characters.");
        }
    }
}
