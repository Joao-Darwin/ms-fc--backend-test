package com.fcbackend.application.usecase.retrive.discarded;

import com.fcbackend.domain.model.Tweet;
import com.fcbackend.domain.repository.TweetRepository;
import com.fcbackend.infrastructure.web.dto.response.DiscardedTweetsResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetDiscardedTweets implements GetDiscardedTweetsUseCase {

    private final TweetRepository tweetRepository;

    public GetDiscardedTweets(TweetRepository tweetRepository) {
        this.tweetRepository = tweetRepository;
    }

    @Override
    public DiscardedTweetsResponseDTO execute() {
        List<Tweet> tweets = tweetRepository.getDiscarded();

        var tweetList = tweets.stream().map(this::mapTweet).toList();

        return new DiscardedTweetsResponseDTO(tweetList);
    }

    private DiscardedTweetsResponseDTO.Tweet mapTweet(Tweet tweet) {

        return DiscardedTweetsResponseDTO.Tweet.builder()
                .id(tweet.getId())
                .publisher(tweet.getPublisher())
                .message(tweet.getMessage())
                .discardedAt(tweet.getDiscardedAt())
                .build();
    }
}
