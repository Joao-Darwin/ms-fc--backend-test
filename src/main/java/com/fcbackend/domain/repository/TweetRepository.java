package com.fcbackend.domain.repository;

import com.fcbackend.domain.model.Tweet;

import java.util.Optional;
import java.util.UUID;

public interface TweetRepository {

    Tweet save(Tweet tweet);

    Optional<Tweet> findById(UUID id);
}
