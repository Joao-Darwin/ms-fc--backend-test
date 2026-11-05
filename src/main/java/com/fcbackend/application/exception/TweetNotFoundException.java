package com.fcbackend.application.exception;

public class TweetNotFoundException extends RuntimeException {

    public TweetNotFoundException(String message) {
        super(message);
    }
}
