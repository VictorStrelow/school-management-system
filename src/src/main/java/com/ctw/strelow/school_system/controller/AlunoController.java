package com.ctw.strelow.school_system.controller;

import com.ctw.strelow.school_system.dto.aluno.AlunoRequestDTO;
import com.ctw.strelow.school_system.dto.aluno.AlunoResponseDTO;
import com.ctw.strelow.school_system.model.Aluno;
import com.ctw.strelow.school_system.service.AlunoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alunos")
public class AlunoController {

    private final AlunoService alunoService;

    public AlunoController(AlunoService alunoService) {
        this.alunoService = alunoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AlunoResponseDTO postAluno(@RequestBody AlunoRequestDTO alunoRequestDTO) {
        return alunoService.criarAluno(alunoRequestDTO);
    }

    @GetMapping
    public List<AlunoResponseDTO> getAlunos() {
        return alunoService.listarAlunos();
    }

    @GetMapping("/{id}")
    public AlunoResponseDTO getAluno(@PathVariable int id) {
        return  alunoService.buscarAlunoPorId(id);
    }

    @PutMapping("/{id}")
    public AlunoResponseDTO putAluno(@PathVariable int id, @RequestBody AlunoRequestDTO alunoRequestDTO) {
        return alunoService.atualizarAluno(id, alunoRequestDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAluno(@PathVariable int id) {
        alunoService.deletarAluno(id);
    }

}