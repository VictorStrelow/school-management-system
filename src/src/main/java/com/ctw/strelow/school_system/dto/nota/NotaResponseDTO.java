package com.ctw.strelow.school_system.dto.nota;

public record NotaResponseDTO (
        int id,
        String alunoNome,
        String aulaAssunto,
        double valor
) {}