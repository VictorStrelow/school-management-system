package com.ctw.strelow.school_system.dto.turma;

import java.util.List;

public record TurmaResponseDTO(
        int id,
        String nome,
        String nomeCurso,
        String nomeProfessor,
        List<String> alunos
) {}