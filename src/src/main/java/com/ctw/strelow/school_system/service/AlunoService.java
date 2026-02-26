package com.ctw.strelow.school_system.service;

import com.ctw.strelow.school_system.dao.AlunoDAO;
import com.ctw.strelow.school_system.dto.aluno.AlunoRequestDTO;
import com.ctw.strelow.school_system.dto.aluno.AlunoResponseDTO;
import com.ctw.strelow.school_system.mapper.AlunoMapper;
import com.ctw.strelow.school_system.model.Aluno;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlunoService {

    private final AlunoDAO alunoDAO;

    public AlunoService(AlunoDAO alunoDAO) {
        this.alunoDAO = alunoDAO;
    }

    public AlunoResponseDTO criarAluno(AlunoRequestDTO alunoRequestDTO) {
        Aluno aluno = AlunoMapper.toEntity(alunoRequestDTO);
        Aluno alunoSalvo = alunoDAO.save(aluno);

        return AlunoMapper.toResponseDTO(alunoSalvo);
    }

    public List<AlunoResponseDTO> listarAlunos() {
        return alunoDAO.findAll().stream()
                .map(AlunoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public AlunoResponseDTO buscarAlunoPorId(int id) {
        Aluno aluno = alunoDAO.findById(id);
        if (aluno == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno não encontrado!");
        }

        return  AlunoMapper.toResponseDTO(aluno);
    }

    public AlunoResponseDTO atualizarAluno(int id, AlunoRequestDTO alunoRequestDTO) {
        Aluno aluno = alunoDAO.findById(id);
        if (aluno == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno não encontrado!");
        }

        aluno.setNome(alunoRequestDTO.nome());
        aluno.setEmail(alunoRequestDTO.email());
        aluno.setMatricula(alunoRequestDTO.matricula());
        aluno.setData_nascimento(alunoRequestDTO.data_nascimento());

        Aluno alunoAtualizado = alunoDAO.update(aluno);
        return AlunoMapper.toResponseDTO(alunoAtualizado);
    }

    public void deletarAluno(int id) {
        Aluno aluno = alunoDAO.findById(id);
        if (aluno == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno não encontrado!");
        }

        alunoDAO.delete(id);
    }

}