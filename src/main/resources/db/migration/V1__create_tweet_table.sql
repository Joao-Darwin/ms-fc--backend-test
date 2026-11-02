CREATE TABLE tweet (
    id UUID PRIMARY KEY,
    message VARCHAR(255) NOT NULL,
    publisher VARCHAR(255) NOT NULL,
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    discarded_at TIMESTAMP
);

CREATE INDEX idx_tweet_status ON tweet(status);