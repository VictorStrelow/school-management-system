package com.ctw.strelow.school_system.dto.curso;

import jakarta.validation.constraints.*;

import java.util.List;

public record CursoRequestDTO (

        @NotBlank(message = "O nome do curso é obrigatório!")
        @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres.")
        String nome,

        @NotBlank(message = "O código do curso é obrigatório!")
        @Pattern(regexp = "[A-Z]{2,5}\\d{3,5}", message = "O código deve seguir o formato: letras maiúsculas seguidas de números (ex: MAT101).")
        String codigo,

        @NotEmpty(message = "A lista de professores não pode ser vazia.")
        List<@Positive(message = "Os IDs dos professores devem ser positivos.") Integer> professorIds

) {}