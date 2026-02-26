package com.ctw.strelow.school_system.service;

import com.ctw.strelow.school_system.dao.AulaDAO;
import com.ctw.strelow.school_system.dao.TurmaDAO;
import com.ctw.strelow.school_system.dto.aula.AulaRequestDTO;
import com.ctw.strelow.school_system.dto.aula.AulaResponseDTO;
import com.ctw.strelow.school_system.mapper.AulaMapper;
import com.ctw.strelow.school_system.model.Aula;
import com.ctw.strelow.school_system.model.Turma;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AulaService {

    private final AulaDAO aulaDAO;
    private final TurmaDAO turmaDAO;

    public AulaService(AulaDAO aulaDAO, TurmaDAO turmaDAO) {
        this.aulaDAO = aulaDAO;
        this.turmaDAO = turmaDAO;
    }

    private AulaResponseDTO construirResponseDTO(Aula aula) {
        Turma turma = turmaDAO.findById(aula.getTurma_id());
        String nomeTurma = (turma != null) ? turma.getNome() : "Turma Desconhecida";

        return AulaMapper.toResponseDTO(aula, nomeTurma);
    }

    public AulaResponseDTO criarAula(AulaRequestDTO aulaRequestDTO) {
        if (turmaDAO.findById(aulaRequestDTO.turma_id()) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "A Turma informada não existe!");
        }

        Aula aula = AulaMapper.toEntity(aulaRequestDTO);
        Aula aulaSalva = aulaDAO.save(aula);

        return construirResponseDTO(aulaSalva);
    }

    public List<AulaResponseDTO> listarAulas() {
        return aulaDAO.findAll().stream()
                .map(this::construirResponseDTO)
                .collect(Collectors.toList());
    }

    public AulaResponseDTO buscarAulaPorId(int id) {
        Aula aula = aulaDAO.findById(id);

        if (aula == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Aula não encontrada!");
        }

        return construirResponseDTO(aula);
    }

    public AulaResponseDTO atualizarAula(int id, AulaRequestDTO aulaRequestDTO) {
        Aula aulaExistente = aulaDAO.findById(id);

        if (aulaExistente == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Aula não encontrada!");
        }

        if (turmaDAO.findById(aulaExistente.getTurma_id()) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "A Turma informada não existe!");
        }

        aulaExistente.setTurma_id(aulaRequestDTO.turma_id());
        aulaExistente.setAssunto(aulaRequestDTO.assunto());

        if (aulaRequestDTO.data_hora() != null) {
            aulaExistente.setData_hora(aulaRequestDTO.data_hora());
        }

        Aula aulaAtualizada = aulaDAO.update(aulaExistente);
        return construirResponseDTO(aulaAtualizada);
    }

    public void deletarAula(int id) {
        if (aulaDAO.findById(id) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Aula não encontrada!");
        }

        aulaDAO.delete(id);
    }

}