package com.ctw.strelow.school_system.dto.turma;

import java.util.List;

public record TurmaRequestDTO(
        String nome,
        int curso_id,
        int professor_id,
        List<Integer> alunoIds
) {}