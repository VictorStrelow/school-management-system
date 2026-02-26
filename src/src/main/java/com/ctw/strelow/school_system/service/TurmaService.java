package com.ctw.strelow.school_system.service;

import com.ctw.strelow.school_system.dao.AlunoDAO;
import com.ctw.strelow.school_system.dao.CursoDAO;
import com.ctw.strelow.school_system.dao.ProfessorDAO;
import com.ctw.strelow.school_system.dao.TurmaDAO;
import com.ctw.strelow.school_system.dto.aluno.AlunoResponseDTO;
import com.ctw.strelow.school_system.dto.turma.TurmaRequestDTO;
import com.ctw.strelow.school_system.dto.turma.TurmaResponseDTO;
import com.ctw.strelow.school_system.mapper.AlunoMapper;
import com.ctw.strelow.school_system.mapper.TurmaMapper;
import com.ctw.strelow.school_system.model.Curso;
import com.ctw.strelow.school_system.model.Professor;
import com.ctw.strelow.school_system.model.Turma;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TurmaService {

    private final TurmaDAO turmaDAO;
    private final CursoDAO cursoDAO;
    private final ProfessorDAO professorDAO;
    private final AlunoDAO alunoDAO;

    public TurmaService(TurmaDAO turmaDAO, CursoDAO cursoDAO, ProfessorDAO professorDAO, AlunoDAO alunoDAO) {
        this.turmaDAO = turmaDAO;
        this.cursoDAO = cursoDAO;
        this.professorDAO = professorDAO;
        this.alunoDAO = alunoDAO;
    }

    private TurmaResponseDTO contruirResponseDTO(Turma turma) {
        Curso curso = cursoDAO.findById(turma.getCurso_id());
        Professor professor = professorDAO.findById(turma.getProfessor_id());

        List<String> nomesAlunos = turmaDAO.findNomesAlunosByTurmaId(turma.getId());

        return TurmaMapper.toResponseDTO(turma, curso.getNome(), professor.getNome(), nomesAlunos);
    }

    public TurmaResponseDTO criarTurma(TurmaRequestDTO turmaRequestDTO) {
        if (cursoDAO.findById(turmaRequestDTO.curso_id()) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso não encontrado!");
        }

        if (professorDAO.findById(turmaRequestDTO.professor_id()) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Professor não encontrado!");
        }

        Turma turma = TurmaMapper.toEntity(turmaRequestDTO);
        Turma turmaSalva = turmaDAO.save(turma);

        if (turmaRequestDTO.alunoIds() != null && !turmaRequestDTO.alunoIds().isEmpty()) {
            turmaDAO.matricularAlunos(turmaSalva.getId(), turmaRequestDTO.alunoIds());
        }

        return contruirResponseDTO(turmaSalva);
    }

    public List<TurmaResponseDTO> listarTurmas() {
        return turmaDAO.findAll().stream()
                .map(this::contruirResponseDTO)
                .collect(Collectors.toList());
    }

    public TurmaResponseDTO buscarTurmaPorId(int id) {
        Turma turma = turmaDAO.findById(id);

        if (turma == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Turma não encontrada!");
        }

        return contruirResponseDTO(turma);
    }

    public TurmaResponseDTO atualizarTurma(int id, TurmaRequestDTO turmaRequestDTO) {
        Turma turma = turmaDAO.findById(id);
        if (turma == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Turma não encontrada!");
        }

        if (cursoDAO.findById(turmaRequestDTO.curso_id()) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso não encontrado!");
        }
        if (professorDAO.findById(turmaRequestDTO.professor_id()) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Professor não encontrado!");
        }

        turma.setNome(turmaRequestDTO.nome());
        turma.setCurso_id(turmaRequestDTO.curso_id());
        turma.setProfessor_id(turmaRequestDTO.professor_id());
        turmaDAO.update(turma);

        turmaDAO.removerTodosAlunosDaTurma(id);
        if (turmaRequestDTO.alunoIds() != null && !turmaRequestDTO.alunoIds().isEmpty()) {
            turmaDAO.matricularAlunos(id,  turmaRequestDTO.alunoIds());
        }

        return contruirResponseDTO(turma);
    }

    public void deletarTurma(int id) {
        if (turmaDAO.findById(id) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Turma não encontrada!");
        }

        turmaDAO.delete(id);
    }

    // EndPoint extra
    public List<AlunoResponseDTO> listarAlunosDaTurma(int id) {
        if (turmaDAO.findById(id) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Turma não encontrada!");
        }

        return alunoDAO.findByTurmaId(id).stream()
                .map(AlunoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

}