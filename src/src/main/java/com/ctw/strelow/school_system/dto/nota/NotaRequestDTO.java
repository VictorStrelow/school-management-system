package com.ctw.strelow.school_system.dto.nota;

public record NotaRequestDTO (
        int aluno_id,
        int aula_id,
        double valor
) {}