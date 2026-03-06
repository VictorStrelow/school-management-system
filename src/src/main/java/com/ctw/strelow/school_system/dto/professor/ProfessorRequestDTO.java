package com.ctw.strelow.school_system.dto.professor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ProfessorRequestDTO (

        @NotBlank(message = "O nome é obrigatório!")
        @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres.")
        String nome,

        @NotBlank(message = "O e-mail é obrigatório!")
        @Email(message = "E-mail inválido.")
        String email,

        @NotBlank(message = "A disciplina é obrigatória!")
        @Size(min = 3, max = 100, message = "A disciplina deve ter entre 3 e 100 caracteres.")
        String disciplina

) {}