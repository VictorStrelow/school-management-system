package com.ctw.strelow.school_system.dto.professor;

public record ProfessorRequestDTO (
        String nome,
        String email,
        String disciplina
) {}