package com.ctw.strelow.school_system.mapper;

import com.ctw.strelow.school_system.dto.aluno.AlunoRequestDTO;
import com.ctw.strelow.school_system.dto.aluno.AlunoResponseDTO;
import com.ctw.strelow.school_system.dto.curso.CursoRequestDTO;
import com.ctw.strelow.school_system.dto.curso.CursoResponseDTO;
import com.ctw.strelow.school_system.model.Aluno;
import com.ctw.strelow.school_system.model.Curso;

import java.util.List;

public class CursoMapper {

    // Converte Requisição para Entidade
    public static Curso toEntity(CursoRequestDTO cursoRequestDTO) {
        Curso curso = new Curso();

        curso.setNome(cursoRequestDTO.nome());
        curso.setCodigo(cursoRequestDTO.codigo());

        return curso;
    }

    // Converte Entidade para Resposta
    public static CursoResponseDTO toResponseDTO(Curso curso, List<String> nomesProfessores) {
        return new CursoResponseDTO(
                curso.getId(),
                curso.getNome(),
                curso.getCodigo(),
                nomesProfessores
        );
    }

}