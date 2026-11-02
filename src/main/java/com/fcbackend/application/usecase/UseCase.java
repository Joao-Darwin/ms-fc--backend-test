package com.fcbackend.application.usecase;

public interface UseCase<I, O> {

    O execute(I input);
}
