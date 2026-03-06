package com.ctw.strelow.school_system.dto.nota;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Positive;

public record NotaRequestDTO (

        @Positive(message = "O ID do aluno deve ser positivo.")
        int aluno_id,

        @Positive(message = "O ID da aula deve ser positivo.")
        int aula_id,

        @DecimalMin(value = "0.0", message = "A nota não pode ser menor que 0.")
        @DecimalMax(value = "10.0", message = "A nota não pode ser maior que 10.")
        double valor

) {}