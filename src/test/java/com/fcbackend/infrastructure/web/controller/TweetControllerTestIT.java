package com.fcbackend.infrastructure.web.controller;

import com.fcbackend.AbstractIntegrationTest;
import com.fcbackend.domain.enums.TweetStatus;
import com.fcbackend.infrastructure.persistence.entity.TweetEntity;
import com.fcbackend.infrastructure.persistence.repository.TweetJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class TweetControllerTestIT extends AbstractIntegrationTest {

    private static final String BASE_URL = "/v1/tweets";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TweetJpaRepository repository;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
    }

    @Nested
    @DisplayName("Tweet create endpoint tests")
    class Create {

        @Test
        void shouldCreateTweetSuccessfully() throws Exception {
            String request = """
                {
                    "publisher": "Jhon Doe",
                    "message": "Foo Bar"
                }
                """;

            mockMvc.perform(post(BASE_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(request))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id").exists())
                    .andExpect(jsonPath("$.publisher").value("Jhon Doe"))
                    .andExpect(jsonPath("$.message").value("Foo Bar"))
                    .andExpect(jsonPath("$.status").value(TweetStatus.ACTIVATED.name()));
        }

        @Test
        void shouldReturnBadRequestWhenPublisherIsBlank() throws Exception {
            String request = """
                {
                    "publisher": "",
                    "message": "Foo Bar"
                }
                """;

            mockMvc.perform(post(BASE_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(request))
                    .andExpect(status().isBadRequest());
        }

        @Test
        void shouldReturnBadRequestWhenPublisherContainsOnlySpaces() throws Exception {
            String request = """
                {
                    "publisher": "   ",
                    "message": "Foo Bar"
                }
                """;

            mockMvc.perform(post(BASE_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(request))
                    .andExpect(status().isBadRequest());
        }

        @Test
        void shouldReturnBadRequestWhenPublisherIsNull() throws Exception {
            String request = """
                {
                    "publisher": null,
                    "message": "Foo Bar"
                }
                """;

            mockMvc.perform(post(BASE_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(request))
                    .andExpect(status().isBadRequest());
        }

        @Test
        void shouldReturnBadRequestWhenMessageIsBlank() throws Exception {
            String request = """
                {
                    "publisher": "Jhon Doe",
                    "message": ""
                }
                """;

            mockMvc.perform(post(BASE_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(request))
                    .andExpect(status().isBadRequest());
        }

        @Test
        void shouldReturnBadRequestWhenMessageContainsOnlySpaces() throws Exception {
            String request = """
                {
                    "publisher": "Jhon Doe",
                    "message": "   "
                }
                """;

            mockMvc.perform(post(BASE_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(request))
                    .andExpect(status().isBadRequest());
        }

        @Test
        void shouldReturnBadRequestWhenMessageIsNull() throws Exception {
            String request = """
                {
                    "publisher": "Jhon Doe",
                    "message": null
                }
                """;

            mockMvc.perform(post(BASE_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(request))
                    .andExpect(status().isBadRequest());
        }

        @Test
        void shouldReturnBadRequestWhenMessageExceeds140Characters() throws Exception {
            String message = "a".repeat(141);

            String request = """
                {
                    "publisher": "Jhon Doe",
                    "message": "%s"
                }
                """.formatted(message);

            mockMvc.perform(post(BASE_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(request))
                    .andExpect(status().isBadRequest());
        }
    }

    @Nested
    @DisplayName("Discard tweet endpoint tests")
    class Discard {

        @Test
        void shouldDiscardTweetSuccessfully() throws Exception {
            TweetEntity tweet = repository.save(TweetEntity.builder()
                    .message("Foo Bar")
                    .publisher("John Doe")
                    .status(TweetStatus.ACTIVATED)
                    .build());

            mockMvc.perform(delete(BASE_URL + "/{id}", tweet.getId()))
                    .andExpect(status().isNoContent());

            TweetEntity discardedTweet = repository.findById(tweet.getId())
                    .orElseThrow();

            assertEquals(TweetStatus.DISCARDED, discardedTweet.getStatus());
            assertNotNull(discardedTweet.getDiscardedAt());
        }

        @Test
        void shouldReturnNotFoundWhenTweetDoesNotExist() throws Exception {
            UUID id = UUID.randomUUID();

            mockMvc.perform(delete(BASE_URL + "/{id}", id))
                    .andExpect(status().isNotFound());

            assertTrue(repository.findById(id).isEmpty());
        }
    }
}
