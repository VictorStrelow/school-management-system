package com.ctw.strelow.school_system.mapper;

import com.ctw.strelow.school_system.dto.aluno.AlunoRequestDTO;
import com.ctw.strelow.school_system.dto.aluno.AlunoResponseDTO;
import com.ctw.strelow.school_system.model.Aluno;

public class AlunoMapper {

    // Converte Requisição para Entidade
    public static Aluno toEntity(AlunoRequestDTO alunoRequestDTO) {
        Aluno aluno = new Aluno();

        aluno.setNome(alunoRequestDTO.nome());
        aluno.setEmail(alunoRequestDTO.email());
        aluno.setMatricula(alunoRequestDTO.matricula());
        aluno.setData_nascimento(alunoRequestDTO.data_nascimento());

        return aluno;
    }

    // Converte Entidade para Resposta
    public static AlunoResponseDTO toResponseDTO(Aluno aluno) {
        return new AlunoResponseDTO(
                aluno.getId(),
                aluno.getNome(),
                aluno.getEmail(),
                aluno.getMatricula(),
                aluno.getData_nascimento()
        );
    }

}