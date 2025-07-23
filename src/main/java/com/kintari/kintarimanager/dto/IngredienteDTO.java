// docs/source/dto/IngredienteDTO.html

package com.kintari.kintarimanager.dto;

import lombok.Data;

@Data
public class IngredienteDTO {

    private Long id;
    private String nome;
    private String unitaDiMisura;
    private double costoPerUnita;

}