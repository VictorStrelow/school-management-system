package com.ctw.strelow.school_system.controller;

import com.ctw.strelow.school_system.dto.curso.CursoRequestDTO;
import com.ctw.strelow.school_system.dto.curso.CursoResponseDTO;
import com.ctw.strelow.school_system.service.CursoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cursos")
public class CursoController {

    private final CursoService cursoService;

    public CursoController(CursoService cursoService) {
        this.cursoService = cursoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CursoResponseDTO postCurso(@RequestBody CursoRequestDTO cursoRequestDTO) {
        return  cursoService.criarCurso(cursoRequestDTO);
    }

    @GetMapping
    public List<CursoResponseDTO> getCursos() {
        return cursoService.listarCursos();
    }

    @GetMapping("/{id}")
    public CursoResponseDTO getCurso(@PathVariable int id) {
        return cursoService.buscarCursoPorId(id);
    }

    @PutMapping("/{id}")
    public CursoResponseDTO putCurso(@PathVariable int id, @RequestBody CursoRequestDTO cursoRequestDTO) {
        return cursoService.atualizarCurso(id, cursoRequestDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCurso(@PathVariable int id) {
        cursoService.deletarCurso(id);
    }

}