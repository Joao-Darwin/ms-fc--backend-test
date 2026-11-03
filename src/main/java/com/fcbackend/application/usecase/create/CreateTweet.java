package com.fcbackend.application.usecase.create;

import com.fcbackend.domain.model.Tweet;
import com.fcbackend.domain.repository.TweetRepository;
import com.fcbackend.infrastructure.web.dto.request.CreateTweetRequestDTO;
import com.fcbackend.infrastructure.web.dto.response.CreateTweetResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CreateTweet implements CreateTweetUseCase {

    private final TweetRepository tweetRepository;

    public CreateTweet(TweetRepository tweetRepository) {
        this.tweetRepository = tweetRepository;
    }

    @Override
    public CreateTweetResponseDTO execute(CreateTweetRequestDTO input) {
        log.info("start tweet creation, publisher {}", input.publisher());

        Tweet tweet = Tweet.build(
                input.message(),
                input.publisher()
        );

        Tweet savedTweet = tweetRepository.save(tweet);

        log.info("tweet created, id: {}", savedTweet.getId());

        return new CreateTweetResponseDTO(
                savedTweet.getId(),
                savedTweet.getPublisher(),
                savedTweet.getMessage(),
                savedTweet.getStatus()
        );
    }
}
