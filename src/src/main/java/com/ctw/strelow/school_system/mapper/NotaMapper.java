package com.ctw.strelow.school_system.mapper;

import com.ctw.strelow.school_system.dto.nota.NotaRequestDTO;
import com.ctw.strelow.school_system.dto.nota.NotaResponseDTO;
import com.ctw.strelow.school_system.model.Nota;

public class NotaMapper {

    // Converte Requisição para Entidade
    public static Nota toEntity(NotaRequestDTO notaRequestDTO) {
        Nota nota = new Nota();

        nota.setAluno_id(notaRequestDTO.aluno_id());
        nota.setAula_id(notaRequestDTO.aula_id());
        nota.setValor(notaRequestDTO.valor());

        return nota;
    }

    // Converte Entidade para Resposta
    public static NotaResponseDTO toResponseDTO(Nota nota, String alunoNome, String aulaAssunto) {
        return new NotaResponseDTO(
                nota.getId(),
                alunoNome,
                aulaAssunto,
                nota.getValor()
        );
    }

}