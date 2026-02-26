package com.ctw.strelow.school_system.mapper;

import com.ctw.strelow.school_system.dto.professor.ProfessorRequestDTO;
import com.ctw.strelow.school_system.dto.professor.ProfessorResponseDTO;
import com.ctw.strelow.school_system.model.Professor;

public class ProfessorMapper {

    // Converte Requisição para Entidade
    public static Professor toEntity(ProfessorRequestDTO professorRequestDTO) {
        Professor professor = new Professor();

        professor.setNome(professorRequestDTO.nome());
        professor.setEmail(professorRequestDTO.email());
        professor.setDisciplina(professorRequestDTO.disciplina());

        return professor;
    }

    // Converte Entidade para Resposta
    public static ProfessorResponseDTO toResponseDTO(Professor professor) {
        return new ProfessorResponseDTO(
                professor.getId(),
                professor.getNome(),
                professor.getEmail(),
                professor.getDisciplina()
        );
    }

}