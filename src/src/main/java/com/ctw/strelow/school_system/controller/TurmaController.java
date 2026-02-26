package com.ctw.strelow.school_system.controller;

import com.ctw.strelow.school_system.dto.aluno.AlunoResponseDTO;
import com.ctw.strelow.school_system.dto.turma.TurmaRequestDTO;
import com.ctw.strelow.school_system.dto.turma.TurmaResponseDTO;
import com.ctw.strelow.school_system.service.TurmaService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/turmas")
public class TurmaController {

    private final TurmaService turmaService;

    public TurmaController(final TurmaService turmaService) {
        this.turmaService = turmaService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TurmaResponseDTO postTurma(@RequestBody TurmaRequestDTO turmaRequestDTO) {
        return turmaService.criarTurma(turmaRequestDTO);
    }

    @GetMapping
    public List<TurmaResponseDTO> getTurmas() {
        return turmaService.listarTurmas();
    }

    @GetMapping("/{id}")
    public TurmaResponseDTO getTurma(@PathVariable int id) {
        return turmaService.buscarTurmaPorId(id);
    }

    @PutMapping("/{id}")
    public TurmaResponseDTO putTurma(@PathVariable int id, @RequestBody TurmaRequestDTO turmaRequestDTO) {
        return turmaService.atualizarTurma(id, turmaRequestDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTurma(@PathVariable int id) {
        turmaService.deletarTurma(id);
    }

    // EndPoint extra
    @GetMapping("/{id}/alunos")
    public List<AlunoResponseDTO> getAlunos(@PathVariable int id) {
        return turmaService.listarAlunosDaTurma(id);
    }

}