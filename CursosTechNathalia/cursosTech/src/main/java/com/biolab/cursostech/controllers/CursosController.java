package com.biolab.cursostech.controllers;

import com.biolab.cursostech.DTOs.AlunosRes;
import com.biolab.cursostech.DTOs.CursosReq;
import com.biolab.cursostech.DTOs.CursosRes;
import com.biolab.cursostech.services.CursosServices;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cursos")
public class CursosController {

    private final CursosServices cursosServices;

    public CursosController(CursosServices cursosServices) {
        this.cursosServices = cursosServices;
    }

    @PostMapping
    public ResponseEntity<CursosRes> cadastrar(@Valid @RequestBody CursosReq cursosReq) {
        CursosRes cursosRes = cursosServices.cadastrarCurso(cursosReq);
        return ResponseEntity.status(HttpStatus.CREATED).body(cursosRes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CursosRes> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(cursosServices.buscarPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<CursosRes>> buscarTodosCursos() {
        return ResponseEntity.ok(cursosServices.buscarTodosCursos());
    }

    @GetMapping("/sem-alunos")
    public ResponseEntity<List<CursosRes>> buscarCursosSemAlunos() {
        return ResponseEntity.ok(cursosServices.buscarCursosSemAlunos());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CursosRes> atualizarCursoPorId(@PathVariable Long id, @Valid @RequestBody CursosReq cursosReq) {
        return ResponseEntity.ok(cursosServices.atualizarCursoPorId(id, cursosReq));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCursoPorId(@PathVariable Long id) {
        cursosServices.deletarCursoPorId(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{cursoId}/alunos")
    public ResponseEntity<List<AlunosRes>> buscarAlunosDoCurso(@PathVariable Long cursoId) {
        return ResponseEntity.ok(cursosServices.buscarAlunosDoCurso(cursoId));
    }
}
