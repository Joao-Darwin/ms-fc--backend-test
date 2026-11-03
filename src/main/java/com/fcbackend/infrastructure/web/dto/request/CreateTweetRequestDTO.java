package com.fcbackend.infrastructure.web.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CreateTweetRequestDTO(

        @NotBlank(message = "publisher can't be blank")
        String publisher,

        @NotBlank(message = "message can't be blank")
        String message
) {
}
