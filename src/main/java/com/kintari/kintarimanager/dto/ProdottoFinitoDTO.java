// docs/source/dto/ProdottoFinitoDTO.html

package com.kintari.kintarimanager.dto;

import lombok.Data;

@Data
public class ProdottoFinitoDTO {

    private Long id;
    private String nome;
    private String categoria;
    private double prezzoVendita;

}