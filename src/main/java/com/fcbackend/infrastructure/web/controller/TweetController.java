package com.fcbackend.infrastructure.web.controller;

import com.fcbackend.application.usecase.create.CreateTweetUseCase;
import com.fcbackend.infrastructure.web.controller.docs.ITweetController;
import com.fcbackend.infrastructure.web.dto.request.CreateTweetRequestDTO;
import com.fcbackend.infrastructure.web.dto.response.CreateTweetResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/tweets")
public class TweetController implements ITweetController {

    private final CreateTweetUseCase createTweetUseCase;

    public TweetController(CreateTweetUseCase createTweetUseCase) {
        this.createTweetUseCase = createTweetUseCase;
    }

    @Override
    @PostMapping
    public ResponseEntity<CreateTweetResponseDTO> create(@RequestBody @Valid CreateTweetRequestDTO request) {
        CreateTweetResponseDTO response = createTweetUseCase.execute(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
