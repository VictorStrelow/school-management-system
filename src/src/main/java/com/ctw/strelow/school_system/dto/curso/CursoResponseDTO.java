package com.ctw.strelow.school_system.dto.curso;

import java.util.List;

public record CursoResponseDTO (
        int id,
        String nome,
        String codigo,
        List<String> professores
) {}