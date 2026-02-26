package com.ctw.strelow.school_system.dto.aula;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record AulaRequestDTO (
        @JsonProperty("turmaId")
        int turma_id,

        LocalDateTime data_hora,
        String assunto
) {}