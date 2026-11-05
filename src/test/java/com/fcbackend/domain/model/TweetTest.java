package com.fcbackend.domain.model;

import com.fcbackend.domain.enums.TweetStatus;
import com.fcbackend.domain.exception.BusinessException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tweet domain tests")
class TweetTest {

    @Test
    void shouldBuildTweetSuccessfully() {
        String message = "Hello world!";
        String publisher = "Joao";

        Tweet tweet = Tweet.build(message, publisher);

        assertNotNull(tweet);
        assertEquals(message, tweet.getMessage());
        assertEquals(publisher, tweet.getPublisher());
        assertEquals(TweetStatus.ACTIVATED, tweet.getStatus());

        assertNull(tweet.getId());
        assertNull(tweet.getCreatedAt());
        assertNull(tweet.getUpdatedAt());
        assertNull(tweet.getDiscardedAt());
    }

    @Test
    void shouldThrowExceptionWhenPublisherIsNullOnBuild() {
        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> Tweet.build("Hello world!", null)
        );

        assertEquals("publisher can't be null.", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenMessageIsNullOnBuild() {
        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> Tweet.build(null, "Joao")
        );

        assertEquals("message can't be null.", exception.getMessage());
    }

    @Test
    void shouldAcceptMessageWith140CharactersOnBuild() {
        String message = "a".repeat(140);

        Tweet tweet = Tweet.build(message, "Joao");

        assertEquals(message, tweet.getMessage());
    }

    @Test
    void shouldAcceptMessageWithUrlsExceeding140CharactersOnBuild() {
        String message = "a".repeat(139) + " https://example.com";

        Tweet tweet = Tweet.build(message, "Joao");

        assertEquals(message, tweet.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenMessageExceeds140CharactersOnBuild() {
        String message = "a".repeat(141);

        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> Tweet.build(message, "Joao")
        );

        assertEquals(
                "message max length is 140 characters.",
                exception.getMessage()
        );
    }

    @Test
    void shouldRebuildTweetSuccessfully() {
        UUID id = UUID.randomUUID();
        String message = "Hello world!";
        String publisher = "Joao";
        TweetStatus status = TweetStatus.ACTIVATED;

        LocalDateTime createdAt = LocalDateTime.now().minusDays(2);
        LocalDateTime updatedAt = LocalDateTime.now().minusDays(1);

        Tweet tweet = Tweet.rebuild(
                id,
                message,
                publisher,
                status,
                createdAt,
                updatedAt,
                null
        );

        assertNotNull(tweet);
        assertEquals(id, tweet.getId());
        assertEquals(message, tweet.getMessage());
        assertEquals(publisher, tweet.getPublisher());
        assertEquals(status, tweet.getStatus());
        assertEquals(createdAt, tweet.getCreatedAt());
        assertEquals(updatedAt, tweet.getUpdatedAt());
        assertNull(tweet.getDiscardedAt());
    }

    @Test
    void shouldThrowExceptionWhenPublisherIsNullOnRebuild() {
        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> Tweet.rebuild(
                        UUID.randomUUID(),
                        "Hello world!",
                        null,
                        TweetStatus.ACTIVATED,
                        LocalDateTime.now(),
                        null,
                        null
                )
        );

        assertEquals("publisher can't be null.", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenMessageIsNullOnRebuild() {
        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> Tweet.rebuild(
                        UUID.randomUUID(),
                        null,
                        "Joao",
                        TweetStatus.ACTIVATED,
                        LocalDateTime.now(),
                        null,
                        null
                )
        );

        assertEquals("message can't be null.", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenMessageExceeds140CharactersOnRebuild() {
        String message = "a".repeat(141);

        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> Tweet.rebuild(
                        UUID.randomUUID(),
                        message,
                        "Joao",
                        TweetStatus.ACTIVATED,
                        LocalDateTime.now(),
                        null,
                        null
                )
        );

        assertEquals(
                "message max length is 140 characters.",
                exception.getMessage()
        );
    }

    @Test
    void shouldDiscardTweetSuccessfully() {
        Tweet tweet = Tweet.build("Hello world!", "John Doe");

        assertEquals(TweetStatus.ACTIVATED, tweet.getStatus());
        assertNull(tweet.getDiscardedAt());

        tweet.discard();

        assertEquals(TweetStatus.DISCARDED, tweet.getStatus());
        assertNotNull(tweet.getDiscardedAt());
    }

    @Test
    void shouldNotChangeTweetWhenAlreadyDiscarded() {
        LocalDateTime discardedAt = LocalDateTime.now(ZoneId.of("America/Sao_Paulo"));

        Tweet tweet = Tweet.rebuild(
                UUID.randomUUID(),
                "Hello world!",
                "John Doe",
                TweetStatus.DISCARDED,
                LocalDateTime.now(),
                null,
                discardedAt
        );

        tweet.discard();

        assertEquals(TweetStatus.DISCARDED, tweet.getStatus());
        assertEquals(discardedAt, tweet.getDiscardedAt());
    }
}
