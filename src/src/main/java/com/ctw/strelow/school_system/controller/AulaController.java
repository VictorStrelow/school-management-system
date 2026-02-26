package com.ctw.strelow.school_system.controller;

import com.ctw.strelow.school_system.dto.aula.AulaRequestDTO;
import com.ctw.strelow.school_system.dto.aula.AulaResponseDTO;
import com.ctw.strelow.school_system.service.AulaService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/aulas")
public class AulaController {

    private final AulaService aulaService;

    public AulaController(AulaService aulaService) {
        this.aulaService = aulaService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AulaResponseDTO criarAula(@RequestBody AulaRequestDTO aulaRequestDTO) {
        return aulaService.criarAula(aulaRequestDTO);
    }

    @GetMapping
    public List<AulaResponseDTO> listarAulas() {
        return aulaService.listarAulas();
    }

    @GetMapping("/{id}")
    public AulaResponseDTO buscarPorId(@PathVariable int id) {
        return aulaService.buscarAulaPorId(id);
    }

    @PutMapping("/{id}")
    public AulaResponseDTO atualizarAula(@PathVariable int id, @RequestBody AulaRequestDTO aulaRequestDTO) {
        return aulaService.atualizarAula(id, aulaRequestDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarAula(@PathVariable int id) {
        aulaService.deletarAula(id);
    }

}