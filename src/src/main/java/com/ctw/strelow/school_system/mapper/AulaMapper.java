package com.ctw.strelow.school_system.mapper;

import com.ctw.strelow.school_system.dto.aula.AulaRequestDTO;
import com.ctw.strelow.school_system.dto.aula.AulaResponseDTO;
import com.ctw.strelow.school_system.model.Aula;

import java.time.LocalDateTime;

public class AulaMapper {

    // Converte Requisição para Entidade
    public static Aula toEntity(AulaRequestDTO aulaRequestDTO) {
        Aula aula = new Aula();

        aula.setTurma_id(aulaRequestDTO.turma_id());
        aula.setAssunto(aulaRequestDTO.assunto());

        if (aulaRequestDTO.data_hora() != null) {
            aula.setData_hora(aulaRequestDTO.data_hora());
        }

        return aula;
    }

    // Converte Entidade para Resposta
    public static AulaResponseDTO toResponseDTO(Aula aula, String nomeTurma) {
        return new AulaResponseDTO(
                aula.getId(),
                nomeTurma,
                aula.getData_hora(),
                aula.getAssunto()
        );
    }

}