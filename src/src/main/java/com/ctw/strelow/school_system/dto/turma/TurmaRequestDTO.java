package com.ctw.strelow.school_system.dto.turma;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.List;

public record TurmaRequestDTO(

        @NotBlank(message = "O nome da turma é obrigatório!")
        @Size(min = 2, max = 100, message = "O nome deve ter entre 2 e 100 caracteres.")
        String nome,

        @Positive(message = "O ID do curso deve ser positivo.")
        int curso_id,

        @Positive(message = "O ID do professor deve ser positivo.")
        int professor_id,

        @NotEmpty(message = "A turma deve ter pelo menos um aluno.")
        List<@Positive(message = "Os IDs dos alunos devem ser positivos.") Integer> alunoIds

) {}