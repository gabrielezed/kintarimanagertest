package com.kintari.kintarimanager.dto;

import lombok.Data;
import java.util.List;

@Data
public class OrdineRequestDTO {
    private List<DettaglioOrdineDTO> prodotti;
}