package com.fcbackend.application.usecase.create;

import com.fcbackend.AbstractIntegrationTest;
import com.fcbackend.domain.enums.TweetStatus;
import com.fcbackend.infrastructure.web.dto.request.CreateTweetRequestDTO;
import com.fcbackend.infrastructure.web.dto.response.CreateTweetResponseDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class CreateTweetTestIT extends AbstractIntegrationTest {

    @Autowired
    private CreateTweetUseCase createTweetUseCase;

    @Test
    void shouldCreateTweetSuccessfully() {
        CreateTweetRequestDTO request = new CreateTweetRequestDTO("Jhon Doe", "Foo Bar");

        CreateTweetResponseDTO response = createTweetUseCase.execute(request);

        assertNotNull(response.id());
        assertEquals(request.publisher(), response.publisher());
        assertEquals(request.message(), response.message());
        assertEquals(TweetStatus.ACTIVATED, response.status());
    }
}