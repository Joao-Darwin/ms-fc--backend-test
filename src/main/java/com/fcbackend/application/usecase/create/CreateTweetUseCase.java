package com.fcbackend.application.usecase.create;

import com.fcbackend.application.usecase.UseCase;
import com.fcbackend.infrastructure.web.dto.request.CreateTweetRequestDTO;
import com.fcbackend.infrastructure.web.dto.response.CreateTweetResponseDTO;

public interface CreateTweetUseCase extends UseCase<CreateTweetRequestDTO, CreateTweetResponseDTO> {
}
