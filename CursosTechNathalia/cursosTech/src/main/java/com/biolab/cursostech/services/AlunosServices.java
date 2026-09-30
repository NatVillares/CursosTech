package com.biolab.cursostech.services;

import com.biolab.cursostech.DTOs.AlunosReq;
import com.biolab.cursostech.DTOs.AlunosRes;
import com.biolab.cursostech.DTOs.CursosRes;
import com.biolab.cursostech.entities.Alunos;
import com.biolab.cursostech.entities.Cursos;
import com.biolab.cursostech.repositories.AlunosRepository;
import com.biolab.cursostech.repositories.CursosRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlunosServices {

    private final AlunosRepository alunosRepository;
    private final CursosRepository cursosRepository;
    public AlunosServices(AlunosRepository alunosRepository, CursosRepository cursosRepository) {
        this.alunosRepository = alunosRepository;
        this.cursosRepository = cursosRepository;
    }


    // --- CADASTRAR ALUNO ---
    public AlunosRes cadastrar(AlunosReq alunosReq){
        Alunos alunos = new Alunos();
        alunos.setNome(alunosReq.getNome());
        alunos.setEmail(alunosReq.getEmail());
        alunos.setMatricula(alunosReq.getMatricula());

        Alunos alunosSalvo = alunosRepository.save(alunos);

        AlunosRes alunosRes = new AlunosRes();
        alunosRes.setId(alunosSalvo.getId());
        alunosRes.setNome(alunosSalvo.getNome());
        alunosRes.setEmail(alunosSalvo.getEmail());
        alunosRes.setMatricula(alunosSalvo.getMatricula());
        return alunosRes;

    }
    // --- BUSCAR POR ID ---
    public AlunosRes buscarPorId(Long id){
        Alunos alunos = alunosRepository.findById(id).orElseThrow();
        AlunosRes alunosRes = new AlunosRes();
        alunosRes.setId(alunos.getId());
        alunosRes.setNome(alunos.getNome());
        alunosRes.setEmail(alunos.getEmail());
        alunosRes.setMatricula(alunos.getMatricula());
        return alunosRes;
    }
    // --- BUSCAR TODOS ---
    public List<AlunosRes> buscarTodosAlunos(){
        List<Alunos> alunos = alunosRepository.findAll();
        return alunos.stream().map(aluno -> {
            AlunosRes alunosRes = new AlunosRes();
            alunosRes.setId(aluno.getId());
            alunosRes.setNome(aluno.getNome());
            alunosRes.setEmail(aluno.getEmail());
            alunosRes.setMatricula(aluno.getMatricula());
            return alunosRes;
        }).toList();
    }
    // --- ATUALIZAR ALUNO ---
    public AlunosRes atualizarAlunoPorId(Long id, AlunosReq alunosReq){
        Alunos alunos = alunosRepository.findById(id).orElseThrow();
        alunos.setNome(alunosReq.getNome());
        alunos.setEmail(alunosReq.getEmail());
        alunos.setMatricula(alunosReq.getMatricula());

        Alunos alunoAtualizado = alunosRepository.save(alunos);

        AlunosRes alunosRes = new AlunosRes();
        alunosRes.setId(alunos.getId());
        alunosRes.setNome(alunos.getNome());
        alunosRes.setEmail(alunos.getEmail());
        alunosRes.setMatricula(alunos.getMatricula());
        return alunosRes;
    }

    // --- DELETAR ALUNO ---
    public void deletarAlunoPorId(Long id){
        if(!alunosRepository.existsById(id)){
            throw new RuntimeException("Aluno não encontrado para deleção ");
        }
        alunosRepository.deleteById(id);
    }

    public List<AlunosRes> buscarAlunosSemCursos(){
        return alunosRepository.findAll().stream().filter(alunos -> alunos.getCursos().isEmpty()).map(alunos -> {AlunosRes alunosRes = new AlunosRes();
        alunosRes.setId(alunos.getId());
        alunosRes.setNome(alunos.getNome());
        alunosRes.setEmail(alunos.getEmail());
        alunosRes.setMatricula(alunos.getMatricula());
        return alunosRes;}).toList();
    }


    //  --- MÉTODOS DE MATRÍCULA ---


    @Transactional
    public  String matricularEmCurso(Long alunoId, Long cursoId){

        Alunos aluno = alunosRepository.findById(alunoId).orElseThrow(() -> new RuntimeException("Aluno não encontrado com o ID: " +alunoId));

        Cursos curso = cursosRepository.findById(cursoId).orElseThrow(()-> new RuntimeException("Curso não encontrado com o Id: " + cursoId));

        // Valida se o aluno já esta no curso
        if (aluno.getCursos().contains(curso)){
            throw new RuntimeException("O aluno já está matriculado neste curso!");
        }
        // Adiciona o curso na lista e salva
        aluno.getCursos().add(curso);
        alunosRepository.save(aluno);

        return "Aluno matriculado com sucesso no curso!";
    }

    @Transactional
    public String cancelarMatricula(Long alunoId, Long cursoId){

        Alunos aluno = alunosRepository.findById(alunoId).orElseThrow(()-> new RuntimeException("Aluno não encontrado com o ID: " + alunoId));

        Cursos curso = cursosRepository.findById(cursoId).orElseThrow(()->new RuntimeException("Curso não encontrado com o ID: " + cursoId));

        // Valida caso não existir uma relação entre aluno e o curso
        if(!aluno.getCursos().contains(curso)){
            throw new RuntimeException("Matrícula não encontrada: O aluno não está vinculado a este curso!");
        }

        // Remove o curso da lista e salva
        aluno.getCursos().remove(curso);
        alunosRepository.save(aluno);
        return "Matrícula cancelada com sucesso!";
    }


    @Transactional(readOnly = true)
    public List<CursosRes> buscarCursosDoALuno(Long alunoId){
        Alunos aluno = alunosRepository.findById(alunoId).orElseThrow(()-> new RuntimeException("Aluno não encontrado com o ID: " + alunoId));
        // Converte a lista de entities Cursos do aluno para o DTOS CursosRes
        return aluno.getCursos().stream().map(cursos -> {
            CursosRes cursosRes = new CursosRes();
            cursosRes.setId(cursos.getId());
            cursosRes.setNome(cursos.getNome());
            cursosRes.setCargaHoraria(cursos.getCargaHoraria());
            cursosRes.setDescricao(cursos.getDescricao());
            return cursosRes;
        }).toList();

    }




}
