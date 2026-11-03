package com.fcbackend.infrastructure.web.controller.docs;

import com.fcbackend.infrastructure.web.dto.request.CreateTweetRequestDTO;
import com.fcbackend.infrastructure.web.dto.response.CreateTweetResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(
        name = "Tweets",
        description = "Endpoints para gerenciamento de tweets."
)
public interface ITweetController {

    @Operation(
            summary = "Criar tweet",
            description = """
                    Cria um novo tweet.

                    Regras de negócio:
                    - O publicador é obrigatório e não pode ser vazio ou conter apenas espaços em branco.
                    - A mensagem é obrigatória e não pode ser vazia.
                    - A mensagem deve possuir no máximo 140 caracteres.
                    - Links presentes na mensagem não são contabilizados no limite de 140 caracteres.
                    """
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Tweet criado com sucesso.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CreateTweetResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos ou violação de uma regra de negócio.",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno inesperado.",
                    content = @Content
            )
    })
    ResponseEntity<CreateTweetResponseDTO> create(
            @RequestBody(
                    description = "Dados necessários para a criação do tweet.",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CreateTweetRequestDTO.class)
                    )
            )
            CreateTweetRequestDTO request
    );
}