package com.ctw.strelow.school_system.service;

import com.ctw.strelow.school_system.dao.ProfessorDAO;
import com.ctw.strelow.school_system.dto.professor.ProfessorRequestDTO;
import com.ctw.strelow.school_system.dto.professor.ProfessorResponseDTO;
import com.ctw.strelow.school_system.mapper.ProfessorMapper;
import com.ctw.strelow.school_system.model.Professor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProfessorService {

    private final ProfessorDAO professorDAO;

    public ProfessorService(ProfessorDAO professorDAO) {
        this.professorDAO = professorDAO;
    }

    public ProfessorResponseDTO criarProfessor(ProfessorRequestDTO professorRequestDTO) {
        Professor professor = ProfessorMapper.toEntity(professorRequestDTO);
        Professor professorSalvo = professorDAO.save(professor);

        return ProfessorMapper.toResponseDTO(professorSalvo);
    }

    public List<ProfessorResponseDTO> listarProfessores() {
        return professorDAO.findAll().stream()
                .map(ProfessorMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public ProfessorResponseDTO buscarProfessorPorId(int id) {
        Professor professor = professorDAO.findById(id);
        if (professor == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Professor não encontrado!");
        }

        return  ProfessorMapper.toResponseDTO(professor);
    }

    public ProfessorResponseDTO atualizarProfessor(int id, ProfessorRequestDTO professorRequestDTO) {
        Professor professor = professorDAO.findById(id);
        if (professor == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Professor não encontrado!");
        }

        professor.setNome(professorRequestDTO.nome());
        professor.setEmail(professorRequestDTO.email());
        professor.setDisciplina(professorRequestDTO.disciplina());

        Professor professorAtualizado = professorDAO.update(professor);
        return ProfessorMapper.toResponseDTO(professorAtualizado);
    }

    public void deletarProfessor(int id) {
        Professor professor = professorDAO.findById(id);
        if (professor == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Professor não encontrado!");
        }

        professorDAO.delete(id);
    }

}