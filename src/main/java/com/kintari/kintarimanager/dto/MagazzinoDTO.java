// docs/source/dto/MagazzinoDTO.html

package com.kintari.kintarimanager.dto;

import lombok.Data;

@Data
public class MagazzinoDTO {

    private Long id;
    private IngredienteDTO ingrediente;
    private double quantitaDisponibile;
    private double sogliaMinima;

}