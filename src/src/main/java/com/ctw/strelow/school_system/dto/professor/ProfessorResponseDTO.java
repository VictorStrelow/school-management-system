package com.ctw.strelow.school_system.dto.professor;

public record ProfessorResponseDTO (
        int id,
        String nome,
        String email,
        String disciplina
) {}