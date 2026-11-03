package com.fcbackend.infrastructure.web.controller;

import com.fcbackend.AbstractIntegrationTest;
import com.fcbackend.domain.enums.TweetStatus;
import com.fcbackend.infrastructure.persistence.repository.TweetJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

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
}
