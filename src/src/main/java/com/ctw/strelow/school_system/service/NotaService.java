package com.ctw.strelow.school_system.service;

import com.ctw.strelow.school_system.dao.AlunoDAO;
import com.ctw.strelow.school_system.dao.AulaDAO;
import com.ctw.strelow.school_system.dao.NotaDAO;
import com.ctw.strelow.school_system.dto.nota.NotaRequestDTO;
import com.ctw.strelow.school_system.dto.nota.NotaResponseDTO;
import com.ctw.strelow.school_system.mapper.NotaMapper;
import com.ctw.strelow.school_system.model.Aluno;
import com.ctw.strelow.school_system.model.Aula;
import com.ctw.strelow.school_system.model.Nota;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotaService {

    private final NotaDAO notaDAO;
    private final AlunoDAO alunoDAO;
    private final AulaDAO aulaDAO;

    public NotaService(NotaDAO notaDAO, AlunoDAO alunoDAO, AulaDAO aulaDAO) {
        this.notaDAO = notaDAO;
        this.alunoDAO = alunoDAO;
        this.aulaDAO = aulaDAO;
    }

    private NotaResponseDTO construirResponseDTO(Nota nota) {
        Aluno aluno = alunoDAO.findById(nota.getAluno_id());
        Aula aula = aulaDAO.findById(nota.getAula_id());

        String nomeAluno = (aluno != null) ? aluno.getNome() : "Aluno Desconhecido";
        String assuntoAula = (aula != null) ? aula.getAssunto() : "Aula Desconhecida";

        return NotaMapper.toResponseDTO(nota, nomeAluno, assuntoAula);
    }

    public NotaResponseDTO criarNota(NotaRequestDTO requestDTO) {
        if (requestDTO.valor() < 0 || requestDTO.valor() > 10) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O valor da nota deve estar entre 0 e 10.");
        }

        if (alunoDAO.findById(requestDTO.aluno_id()) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno informado não existe!");
        }

        if (aulaDAO.findById(requestDTO.aula_id()) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Aula informada não existe!");
        }

        Nota nota = NotaMapper.toEntity(requestDTO);
        Nota notaSalva = notaDAO.save(nota);
        return construirResponseDTO(notaSalva);
    }

    public List<NotaResponseDTO> listarTodas() {
        return notaDAO.findAll().stream()
                .map(this::construirResponseDTO)
                .collect(Collectors.toList());
    }

    public NotaResponseDTO buscarPorId(int id) {
        Nota nota = notaDAO.findById(id);
        if (nota == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nota não encontrada!");
        }

        return construirResponseDTO(nota);
    }

    public NotaResponseDTO atualizarNota(int id, NotaRequestDTO requestDTO) {
        Nota notaExistente = notaDAO.findById(id);
        if (notaExistente == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nota não encontrada!");
        }

        if (requestDTO.valor() < 0 || requestDTO.valor() > 10) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O valor da nota deve estar entre 0 e 10.");
        }

        if (alunoDAO.findById(requestDTO.aluno_id()) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno informado não existe!");
        }

        if (aulaDAO.findById(requestDTO.aula_id()) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Aula informada não existe!");
        }

        notaExistente.setAluno_id(requestDTO.aluno_id());
        notaExistente.setAula_id(requestDTO.aula_id());
        notaExistente.setValor(requestDTO.valor());

        Nota notaAtualizada = notaDAO.update(notaExistente);
        return construirResponseDTO(notaAtualizada);
    }

    public void deletarNota(int id) {
        if (notaDAO.findById(id) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nota não encontrada!");
        }

        notaDAO.delete(id);
    }

    // EndPoint
    public List<NotaResponseDTO> buscarNotasPorAluno(int alunoId) {
        if (alunoDAO.findById(alunoId) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno não encontrado!");
        }

        return notaDAO.findByAlunoId(alunoId).stream()
                .map(this::construirResponseDTO)
                .collect(Collectors.toList());
    }

}