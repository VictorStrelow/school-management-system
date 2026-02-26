package com.ctw.strelow.school_system.dto.curso;

import java.util.List;

public record CursoRequestDTO (
        String nome,
        String codigo,
        List<Integer> professorIds
) {}