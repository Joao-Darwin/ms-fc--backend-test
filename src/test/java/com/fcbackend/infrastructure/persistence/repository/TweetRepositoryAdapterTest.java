package com.fcbackend.infrastructure.persistence.repository;

import com.fcbackend.domain.enums.TweetStatus;
import com.fcbackend.domain.model.Tweet;
import com.fcbackend.infrastructure.persistence.entity.TweetEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TweetRepositoryAdapterTest {

    @Mock
    private TweetJpaRepository tweetJpaRepository;

    @InjectMocks
    private TweetRepositoryAdapter tweetRepositoryAdapter;

    @Test
    void shouldSaveTweetSuccessfully() {
        Tweet tweet = Tweet.build("Hello world!", "Joao");

        TweetEntity savedEntity = TweetEntity.builder()
                .id(UUID.randomUUID())
                .message(tweet.getMessage())
                .publisher(tweet.getPublisher())
                .status(TweetStatus.ACTIVATED)
                .build();

        when(tweetJpaRepository.save(any(TweetEntity.class)))
                .thenReturn(savedEntity);

        Tweet result = tweetRepositoryAdapter.save(tweet);

        assertNotNull(result);
        assertEquals(savedEntity.getId(), result.getId());
        assertEquals(tweet.getMessage(), result.getMessage());
        assertEquals(tweet.getPublisher(), result.getPublisher());
        assertEquals(tweet.getStatus(), result.getStatus());

        verify(tweetJpaRepository).save(any(TweetEntity.class));
    }

    @Test
    void shouldFindTweetByIdSuccessfully() {
        UUID id = UUID.randomUUID();

        TweetEntity entity = TweetEntity.builder()
                .id(id)
                .message("Hello world!")
                .publisher("Joao")
                .status(TweetStatus.ACTIVATED)
                .build();

        when(tweetJpaRepository.findById(id))
                .thenReturn(Optional.of(entity));

        Optional<Tweet> result = tweetRepositoryAdapter.findById(id);

        assertTrue(result.isPresent());
        assertEquals(id, result.get().getId());
        assertEquals(entity.getMessage(), result.get().getMessage());
        assertEquals(entity.getPublisher(), result.get().getPublisher());
        assertEquals(entity.getStatus(), result.get().getStatus());

        verify(tweetJpaRepository).findById(id);
    }

    @Test
    void shouldReturnEmptyWhenTweetDoesNotExist() {
        UUID id = UUID.randomUUID();

        when(tweetJpaRepository.findById(id))
                .thenReturn(Optional.empty());

        Optional<Tweet> result = tweetRepositoryAdapter.findById(id);

        assertTrue(result.isEmpty());

        verify(tweetJpaRepository).findById(id);
    }
}
