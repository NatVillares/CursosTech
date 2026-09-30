package com.biolab.cursostech.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CursosRes {

    private Long id;
    private String nome;
    private String cargaHoraria;
    private String descricao;

}
