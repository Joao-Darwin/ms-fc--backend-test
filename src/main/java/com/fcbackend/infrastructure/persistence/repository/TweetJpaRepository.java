package com.fcbackend.infrastructure.persistence.repository;

import com.fcbackend.infrastructure.persistence.entity.TweetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TweetJpaRepository extends JpaRepository<TweetEntity, UUID> {
}
