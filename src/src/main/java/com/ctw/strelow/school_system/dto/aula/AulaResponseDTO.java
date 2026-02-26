package com.ctw.strelow.school_system.dto.aula;

import java.time.LocalDateTime;

public record AulaResponseDTO (
        int id,
        String nome_turma,
        LocalDateTime data_hora,
        String assunto
) {}