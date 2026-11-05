package com.fcbackend.infrastructure.web.controller;

import com.fcbackend.application.usecase.create.CreateTweetUseCase;
import com.fcbackend.application.usecase.discard.DiscardTweetUseCase;
import com.fcbackend.infrastructure.web.controller.docs.ITweetController;
import com.fcbackend.infrastructure.web.dto.request.CreateTweetRequestDTO;
import com.fcbackend.infrastructure.web.dto.response.CreateTweetResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/v1/tweets")
public class TweetController implements ITweetController {

    private final CreateTweetUseCase createTweetUseCase;
    private final DiscardTweetUseCase discardTweetUseCase;

    public TweetController(CreateTweetUseCase createTweetUseCase, DiscardTweetUseCase discardTweetUseCase) {
        this.createTweetUseCase = createTweetUseCase;
        this.discardTweetUseCase = discardTweetUseCase;
    }

    @Override
    @PostMapping
    public ResponseEntity<CreateTweetResponseDTO> create(@RequestBody @Valid CreateTweetRequestDTO request) {
        CreateTweetResponseDTO response = createTweetUseCase.execute(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> discard(@PathVariable UUID id) {
        discardTweetUseCase.execute(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
