package com.biolab.cursostech.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class Cursos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String nome;
    @NotBlank
    private String cargaHoraria;
    @NotBlank @Column(length = 50)
    private String descricao;
    @ManyToMany(mappedBy = "cursos")
    private Set<Alunos> alunos = new HashSet<>();
}
