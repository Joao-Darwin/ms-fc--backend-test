package com.fcbackend.infrastructure.persistence.repository;

import com.fcbackend.domain.model.Tweet;
import com.fcbackend.domain.repository.TweetRepository;
import com.fcbackend.infrastructure.persistence.entity.TweetEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class TweetRepositoryAdapter implements TweetRepository {

    private final TweetJpaRepository tweetJpaRepository;

    public TweetRepositoryAdapter(TweetJpaRepository tweetJpaRepository) {
        this.tweetJpaRepository = tweetJpaRepository;
    }

    @Override
    public Tweet save(Tweet tweet) {
        TweetEntity tweetEntity = TweetMapper.toEntity(tweet);

        TweetEntity savedTweet = tweetJpaRepository.save(tweetEntity);

        return TweetMapper.toDomain(savedTweet);
    }

    @Override
    public Optional<Tweet> findById(UUID id) {
        return tweetJpaRepository.findById(id).map(TweetMapper::toDomain);
    }
}
