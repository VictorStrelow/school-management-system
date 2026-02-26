package com.ctw.strelow.school_system.mapper;

import com.ctw.strelow.school_system.dto.turma.TurmaRequestDTO;
import com.ctw.strelow.school_system.dto.turma.TurmaResponseDTO;
import com.ctw.strelow.school_system.model.Turma;

import java.util.List;

public class TurmaMapper {

    // Converte Requisição para Entidade
    public static Turma toEntity(TurmaRequestDTO turmaRequestDTO) {
        Turma turma = new Turma();

        turma.setNome(turmaRequestDTO.nome());
        turma.setCurso_id(turmaRequestDTO.curso_id());
        turma.setProfessor_id(turmaRequestDTO.professor_id());

        return turma;
    }

    // Converte Entidade para Resposta
    public static TurmaResponseDTO toResponseDTO(Turma turma, String nomeCurso, String nomeProfessor, List<String> nomesAlunos) {
        return new TurmaResponseDTO(
                turma.getId(),
                turma.getNome(),
                nomeCurso,
                nomeProfessor,
                nomesAlunos
        );
    }

}