// docs/source/dto/RicettaDTO.html

package com.kintari.kintarimanager.dto;

import lombok.Data;

@Data
public class RicettaDTO {

    private Long id;
    private Long ingredienteId;
    private String ingredienteNome;
    private double quantita;
    private String unitaDiMisura;

}