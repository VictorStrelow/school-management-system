package com.ctw.strelow.school_system.dto.aluno;

import java.time.LocalDate;

public record AlunoRequestDTO (
        String nome,
        String email,
        String matricula,
        LocalDate data_nascimento
) {}