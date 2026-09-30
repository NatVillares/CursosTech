package com.biolab.cursostech.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AlunosRes {


    private Long id;
    private String nome;
    private String email;
    private String matricula;
}
