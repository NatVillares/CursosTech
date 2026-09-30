package com.biolab.cursostech.services;

import com.biolab.cursostech.DTOs.AlunosRes;
import com.biolab.cursostech.DTOs.CursosReq;
import com.biolab.cursostech.DTOs.CursosRes;
import com.biolab.cursostech.entities.Cursos;
import com.biolab.cursostech.repositories.CursosRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CursosServices {

    private final CursosRepository cursosRepository;
    public CursosServices(CursosRepository cursosRepository) {
        this.cursosRepository = cursosRepository;
    }

    // --- CADASTRAR CURSO ---
    public CursosRes cadastrarCurso(CursosReq cursosReq) {
        Cursos cursos = new Cursos();
        cursos.setNome(cursosReq.getNome());
        cursos.setDescricao(cursosReq.getDescricao());
        cursos.setCargaHoraria(cursosReq.getCargaHoraria());

        Cursos cursoSalvo = cursosRepository.save(cursos);
        CursosRes cursosRes = new CursosRes();
        cursosRes.setId(cursoSalvo.getId());
        cursosRes.setNome(cursoSalvo.getNome());
        cursosRes.setDescricao(cursoSalvo.getDescricao());
        cursosRes.setCargaHoraria(cursoSalvo.getCargaHoraria());
        return cursosRes;
    }

    // --- BUSCAR POR ID ---
    public CursosRes buscarPorId(Long id) {
        Cursos curso = cursosRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Curso não encontrado com o ID: " + id));

        CursosRes cursosRes = new CursosRes();
        cursosRes.setId(curso.getId());
        cursosRes.setNome(curso.getNome());
        cursosRes.setCargaHoraria(curso.getCargaHoraria());
        cursosRes.setDescricao(curso.getDescricao());
        return cursosRes;
    }

    // --- BUSCAR Tudo ---
    public List<CursosRes> buscarTodosCursos() {
        return cursosRepository.findAll().stream().map(curso -> {
            CursosRes cursosRes = new CursosRes();
            cursosRes.setId(curso.getId());
            cursosRes.setNome(curso.getNome());
            cursosRes.setCargaHoraria(curso.getCargaHoraria());
            cursosRes.setDescricao(curso.getDescricao());
            return cursosRes;
        }).toList();
    }

    // --- ATUALIZAR CURSO ---
    public CursosRes atualizarCursoPorId(Long id, CursosReq cursosReq) {
        Cursos curso = cursosRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Curso não encontrado com o ID: " + id));

        curso.setNome(cursosReq.getNome());
        curso.setCargaHoraria(cursosReq.getCargaHoraria());
        curso.setDescricao(cursosReq.getDescricao());

        Cursos cursoAtualizado = cursosRepository.save(curso);

        CursosRes cursosRes = new CursosRes();
        cursosRes.setId(cursoAtualizado.getId());
        cursosRes.setNome(cursoAtualizado.getNome());
        cursosRes.setCargaHoraria(cursoAtualizado.getCargaHoraria());
        cursosRes.setDescricao(cursoAtualizado.getDescricao());
        return cursosRes;
    }

    // --- DELETAR CURSO ---
    public void deletarCursoPorId(Long id) {
        if (!cursosRepository.existsById(id)) {
            throw new RuntimeException("Curso não encontrado para deleção.");
        }
        cursosRepository.deleteById(id);
    }

    // --- CONSULTAR ALUNOS MATRICULADOS NO CURSO ---
    @Transactional(readOnly = true)
    public List<AlunosRes> buscarAlunosDoCurso(Long cursoId) {
        Cursos curso = cursosRepository.findById(cursoId)
                .orElseThrow(() -> new RuntimeException("Curso não encontrado com o ID: " + cursoId));

        return curso.getAlunos().stream().map(aluno -> {
            AlunosRes alunosRes = new AlunosRes();
            alunosRes.setId(aluno.getId());
            alunosRes.setNome(aluno.getNome());
            alunosRes.setEmail(aluno.getEmail());
            alunosRes.setMatricula(aluno.getMatricula());
            return alunosRes;
        }).toList();
    }

    // --- DESAFIO: BUSCAR CURSOS SEM ALUNOS ---
    @Transactional(readOnly = true)
    public List<CursosRes> buscarCursosSemAlunos() {
        return cursosRepository.findAll().stream()
                .filter(curso -> curso.getAlunos().isEmpty())
                .map(curso -> {
                    CursosRes cursosRes = new CursosRes();
                    cursosRes.setId(curso.getId());
                    cursosRes.setNome(curso.getNome());
                    cursosRes.setCargaHoraria(curso.getCargaHoraria());
                    cursosRes.setDescricao(curso.getDescricao());
                    return cursosRes;
                }).toList();
    }

}
