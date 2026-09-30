package com.biolab.cursostech.DTOs;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CursosReq {

    @NotBlank
    private String nome;
    @NotBlank
    private String cargaHoraria;
    @NotBlank
    private String descricao;

}
