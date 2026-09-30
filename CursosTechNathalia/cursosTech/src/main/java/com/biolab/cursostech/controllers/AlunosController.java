package com.biolab.cursostech.controllers;

import com.biolab.cursostech.DTOs.AlunosReq;
import com.biolab.cursostech.DTOs.AlunosRes;
import com.biolab.cursostech.DTOs.CursosRes;
import com.biolab.cursostech.services.AlunosServices;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alunos")
public class AlunosController {

    private final AlunosServices alunosServices;
    public AlunosController(AlunosServices alunosServices) {
        this.alunosServices = alunosServices;
    }

    @PostMapping
    public ResponseEntity<AlunosRes> cadastrar(@Valid @RequestBody AlunosReq alunosReq){
        AlunosRes alunosRes = alunosServices.cadastrar(alunosReq);
        return ResponseEntity.status(HttpStatus.CREATED).body(alunosRes);

    }
    @GetMapping("/{id}")
    public ResponseEntity<AlunosRes> buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok(alunosServices.buscarPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<AlunosRes>> buscarTodosAlunos(){
        return ResponseEntity.ok(alunosServices.buscarTodosAlunos());
    }

    @GetMapping("/sem-cursos")
    public ResponseEntity<List<AlunosRes>> buscarAlunosSemCursos(){
        return ResponseEntity.ok(alunosServices.buscarAlunosSemCursos());
    }


    @PutMapping("/{id}")
    public ResponseEntity<AlunosRes> atualizarAlunoPorId(@PathVariable Long id, @Valid @RequestBody AlunosReq alunosReq){
        return ResponseEntity.ok(alunosServices.atualizarAlunoPorId(id, alunosReq));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAlunoPorId(@PathVariable Long id){
        alunosServices.deletarAlunoPorId(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{alunoId}/cursos/{cursosId}")
    public ResponseEntity<String> matricularEmCurso(@PathVariable Long alunoId, @PathVariable Long cursosId){
        String resposta = alunosServices.matricularEmCurso(alunoId,cursosId);
        return ResponseEntity.ok(resposta);
    }
    @DeleteMapping("/{alunoId}/cursos/{cursosId}")
    public ResponseEntity<String> cancelarMatricula(@PathVariable Long alunoId, @PathVariable Long cursosId){
        String resposta = alunosServices.cancelarMatricula(alunoId, cursosId);
        return ResponseEntity.ok(resposta);
    }

    @GetMapping("/{alunoId}/cursos")
    public ResponseEntity<List<CursosRes>> buscarCursosDosAluno(@PathVariable Long alunoId){
        return ResponseEntity.ok(alunosServices.buscarCursosDoALuno(alunoId));
    }

}
