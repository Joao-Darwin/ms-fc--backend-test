package com.fcbackend.infrastructure.web.controller.docs;

import com.fcbackend.infrastructure.web.dto.request.CreateTweetRequestDTO;
import com.fcbackend.infrastructure.web.dto.response.CreateTweetResponseDTO;
import org.springframework.http.ResponseEntity;

public interface ITweetController {

    ResponseEntity<CreateTweetResponseDTO> create(CreateTweetRequestDTO request);
}
