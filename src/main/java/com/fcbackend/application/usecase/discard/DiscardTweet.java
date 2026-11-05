package com.fcbackend.application.usecase.discard;

import com.fcbackend.application.exception.TweetNotFoundException;
import com.fcbackend.domain.model.Tweet;
import com.fcbackend.domain.repository.TweetRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class DiscardTweet implements DiscardTweetUseCase {

    private final TweetRepository tweetRepository;

    public DiscardTweet(TweetRepository tweetRepository) {
        this.tweetRepository = tweetRepository;
    }

    @Override
    public void execute(UUID id) {
        log.info("starting tweet discard with ID: {}", id);

        Tweet tweet = tweetRepository.findById(id).orElseThrow(
                () -> new TweetNotFoundException("tweet for found")
        );

        tweet.discard();

        tweetRepository.save(tweet);

        log.info("tweet {} discarded", id);
    }
}
