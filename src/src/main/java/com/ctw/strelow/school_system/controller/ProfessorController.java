package com.ctw.strelow.school_system.controller;

import com.ctw.strelow.school_system.dto.professor.ProfessorRequestDTO;
import com.ctw.strelow.school_system.dto.professor.ProfessorResponseDTO;
import com.ctw.strelow.school_system.service.ProfessorService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/professores")
public class ProfessorController {

    private final ProfessorService professorService;

    public ProfessorController(ProfessorService professorService) {
        this.professorService = professorService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProfessorResponseDTO postProfessor(@RequestBody ProfessorRequestDTO professorRequestDTO) {
        return professorService.criarProfessor(professorRequestDTO);
    }

    @GetMapping
    public List<ProfessorResponseDTO> getProfessores() {
        return  professorService.listarProfessores();
    }

    @GetMapping("/{id}")
    public ProfessorResponseDTO getProfessor(@PathVariable int id) {
        return professorService.buscarProfessorPorId(id);
    }

    @PutMapping("/{id}")
    public ProfessorResponseDTO putProfessor(@PathVariable int id, @RequestBody ProfessorRequestDTO professorRequestDTO) {
        return professorService.atualizarProfessor(id, professorRequestDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProfessor(@PathVariable int id) {
        professorService.deletarProfessor(id);
    }

}