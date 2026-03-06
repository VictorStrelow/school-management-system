package com.ctw.strelow.school_system.dto.aula;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public record AulaRequestDTO (

        @JsonProperty("turmaId")
        @Positive(message = "O ID da turma deve ser positivo.")
        int turma_id,

        @NotNull(message = "A data e hora são obrigatórias!")
        @Future(message = "A data e hora devem estar no futuro.")
        LocalDateTime data_hora,

        @NotBlank(message = "O assunto é obrigatório!")
        @Size(min = 3, max = 200, message = "O assunto deve ter entre 3 e 200 caracteres.")
        String assunto

) {}