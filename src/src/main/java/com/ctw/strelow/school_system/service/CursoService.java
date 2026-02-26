package com.ctw.strelow.school_system.service;

import com.ctw.strelow.school_system.dao.CursoDAO;
import com.ctw.strelow.school_system.dto.curso.CursoRequestDTO;
import com.ctw.strelow.school_system.dto.curso.CursoResponseDTO;
import com.ctw.strelow.school_system.mapper.CursoMapper;
import com.ctw.strelow.school_system.model.Curso;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CursoService {

    private final CursoDAO cursoDAO;

    public CursoService(final CursoDAO cursoDAO) {
        this.cursoDAO = cursoDAO;
    }

    public CursoResponseDTO criarCurso(CursoRequestDTO cursoRequestDTO) {
        Curso curso = CursoMapper.toEntity(cursoRequestDTO);
        Curso cursoSalvo = cursoDAO.save(curso);

        return CursoMapper.toResponseDTO(cursoSalvo, List.of());
    }

    public List<CursoResponseDTO> listarCursos() {
        return cursoDAO.findAll().stream()
                .map(curso -> {
                    List<String> professores = cursoDAO.findNomesProfessoresByCursoId(curso.getId());
                    return CursoMapper.toResponseDTO(curso, professores);
                })
                .collect(Collectors.toList());
    }

    public CursoResponseDTO buscarCursoPorId(int id) {
        Curso curso = cursoDAO.findById(id);
        if (curso == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso não encontrado!");
        }

        List<String> professores = cursoDAO.findNomesProfessoresByCursoId(curso.getId());
        return CursoMapper.toResponseDTO(curso, professores);
    }

    public CursoResponseDTO atualizarCurso(int id, CursoRequestDTO cursoRequestDTO) {
        Curso curso = cursoDAO.findById(id);
        if (curso == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso não encontrado!");
        }

        curso.setNome(cursoRequestDTO.nome());
        curso.setCodigo(cursoRequestDTO.codigo());

        Curso cursoAtualizado = cursoDAO.update(curso);
        List<String> professores = cursoDAO.findNomesProfessoresByCursoId(curso.getId());
        return CursoMapper.toResponseDTO(cursoAtualizado, professores);
    }

    public void deletarCurso(int id) {
        Curso curso = cursoDAO.findById(id);
        if (curso == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso não encontrado!");
        }

        cursoDAO.delete(id);
    }

}