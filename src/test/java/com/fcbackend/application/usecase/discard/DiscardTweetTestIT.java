package com.fcbackend.application.usecase.discard;

import com.fcbackend.AbstractIntegrationTest;
import com.fcbackend.application.exception.TweetNotFoundException;
import com.fcbackend.application.usecase.create.CreateTweetUseCase;
import com.fcbackend.domain.enums.TweetStatus;
import com.fcbackend.domain.model.Tweet;
import com.fcbackend.domain.repository.TweetRepository;
import com.fcbackend.infrastructure.web.dto.request.CreateTweetRequestDTO;
import com.fcbackend.infrastructure.web.dto.response.CreateTweetResponseDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class DiscardTweetTestIT extends AbstractIntegrationTest {

    @Autowired
    private CreateTweetUseCase createTweetUseCase;

    @Autowired
    private DiscardTweetUseCase discardTweetUseCase;

    @Autowired
    private TweetRepository tweetRepository;

    @Test
    void shouldThrowsExceptionWhenTweetNotFound() {

        assertThrowsExactly(
                TweetNotFoundException.class,
                () -> discardTweetUseCase.execute(UUID.randomUUID())
        );
    }

    @Test
    void shouldDiscardTweetSuccessfully() {
        CreateTweetRequestDTO request = new CreateTweetRequestDTO("Jhon Doe", "Foo Bar");

        CreateTweetResponseDTO response = createTweetUseCase.execute(request);

        discardTweetUseCase.execute(response.id());

        Tweet tweet = tweetRepository.findById(response.id()).orElseThrow();

        assertNotNull(tweet.getDiscardedAt());
        assertEquals(TweetStatus.DISCARDED, tweet.getStatus());
    }
}