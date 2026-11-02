package com.fcbackend.infrastructure.persistence.repository;

import com.fcbackend.domain.model.Tweet;
import com.fcbackend.infrastructure.persistence.entity.TweetEntity;

public interface TweetMapper {

    static TweetEntity toEntity(Tweet tweet) {
        TweetEntity tweetEntity = new TweetEntity();

        tweetEntity.setId(tweet.getId());
        tweetEntity.setMessage(tweet.getMessage());
        tweetEntity.setPublisher(tweet.getPublisher());
        tweetEntity.setStatus(tweet.getStatus());
        tweetEntity.setDiscardedAt(tweet.getDiscardedAt());

        return tweetEntity;
    }

    static Tweet toDomain(TweetEntity tweetEntity) {

        return Tweet.rebuild(
                tweetEntity.getId(),
                tweetEntity.getMessage(),
                tweetEntity.getPublisher(),
                tweetEntity.getStatus(),
                tweetEntity.getCreatedAt(),
                tweetEntity.getUpdatedAt(),
                tweetEntity.getDiscardedAt()
        );
    }
}
