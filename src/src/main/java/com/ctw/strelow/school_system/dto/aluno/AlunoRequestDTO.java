package com.ctw.strelow.school_system.dto.aluno;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record AlunoRequestDTO (

        @NotBlank(message = "O nome é obrigatório!")
        @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres.")
        String nome,

        @NotBlank(message = "O email é obrigatório!")
        @Email(message = "Email inválido.")
        String email,

        @NotBlank(message = "A matrícula é obrigatória!")
        @Pattern(regexp = "\\d{6,10}", message = "A matrícula deve conter entre 6 e 10 dígitos numéricos.")
        String matricula,

        @NotNull(message = "A data de nascimento é obrigatória!")
        @Past(message = "A data de nascimento deve estar no passado.")
        LocalDate data_nascimento

) {}