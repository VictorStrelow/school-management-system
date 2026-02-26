package com.ctw.strelow.school_system.controller;

import com.ctw.strelow.school_system.dto.nota.NotaRequestDTO;
import com.ctw.strelow.school_system.dto.nota.NotaResponseDTO;
import com.ctw.strelow.school_system.service.NotaService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notas")
public class NotaController {

    private final NotaService notaService;

    public NotaController(NotaService notaService) {
        this.notaService = notaService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NotaResponseDTO criarNota(@RequestBody NotaRequestDTO request) {
        return notaService.criarNota(request);
    }

    @GetMapping
    public List<NotaResponseDTO> listarNotas() {
        return notaService.listarTodas();
    }

    @GetMapping("/{id}")
    public NotaResponseDTO buscarPorId(@PathVariable int id) {
        return notaService.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public NotaResponseDTO atualizarNota(@PathVariable int id, @RequestBody NotaRequestDTO request) {
        return notaService.atualizarNota(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarNota(@PathVariable int id) {
        notaService.deletarNota(id);
    }

}