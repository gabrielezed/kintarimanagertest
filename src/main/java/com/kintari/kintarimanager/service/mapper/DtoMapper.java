// docs/source/service/mapper/DtoMapper.html

package com.kintari.kintarimanager.service.mapper;

import com.kintari.kintarimanager.dto.IngredienteDTO;
import com.kintari.kintarimanager.dto.MagazzinoDTO;
import com.kintari.kintarimanager.dto.ProdottoFinitoDTO;
import com.kintari.kintarimanager.dto.RicettaDTO;
import com.kintari.kintarimanager.model.Ingrediente;
import com.kintari.kintarimanager.model.Magazzino;
import com.kintari.kintarimanager.model.ProdottoFinito;
import com.kintari.kintarimanager.model.Ricetta;
import org.springframework.stereotype.Component;

@Component
public class DtoMapper {

    // --- INGREDIENTE MAPPERS ---
    public IngredienteDTO toDto(Ingrediente ingrediente) {
        if (ingrediente == null) return null;
        IngredienteDTO dto = new IngredienteDTO();
        dto.setId(ingrediente.getId());
        dto.setNome(ingrediente.getNome());
        dto.setUnitaDiMisura(ingrediente.getUnitaDiMisura());
        dto.setCostoPerUnita(ingrediente.getCostoPerUnita());
        return dto;
    }

    public Ingrediente toEntity(IngredienteDTO dto) {
        if (dto == null) return null;
        Ingrediente ingrediente = new Ingrediente();
        ingrediente.setId(dto.getId());
        ingrediente.setNome(dto.getNome());
        ingrediente.setUnitaDiMisura(dto.getUnitaDiMisura());
        ingrediente.setCostoPerUnita(dto.getCostoPerUnita());
        return ingrediente;
    }
    
    // --- PRODOTTO FINITO MAPPERS ---
    public ProdottoFinitoDTO toDto(ProdottoFinito prodotto) {
        if (prodotto == null) return null;
        ProdottoFinitoDTO dto = new ProdottoFinitoDTO();
        dto.setId(prodotto.getId());
        dto.setNome(prodotto.getNome());
        dto.setCategoria(prodotto.getCategoria());
        dto.setPrezzoVendita(prodotto.getPrezzoVendita());
        return dto;
    }

    public ProdottoFinito toEntity(ProdottoFinitoDTO dto) {
        if (dto == null) return null;
        ProdottoFinito prodotto = new ProdottoFinito();
        prodotto.setId(dto.getId());
        prodotto.setNome(dto.getNome());
        prodotto.setCategoria(dto.getCategoria());
        prodotto.setPrezzoVendita(dto.getPrezzoVendita());
        return prodotto;
    }
    
    // --- RICETTA MAPPERS ---
    public RicettaDTO toDto(Ricetta ricetta) {
        if (ricetta == null) return null;
        RicettaDTO dto = new RicettaDTO();
        dto.setId(ricetta.getId());
        dto.setQuantita(ricetta.getQuantita());
        if (ricetta.getIngrediente() != null) {
            dto.setIngredienteId(ricetta.getIngrediente().getId());
            dto.setIngredienteNome(ricetta.getIngrediente().getNome());
            dto.setUnitaDiMisura(ricetta.getIngrediente().getUnitaDiMisura());
        }
        return dto;
    }

    // Nota: toEntity per Ricetta è più complesso e lo creeremo quando servirà,
    // perché richiede di recuperare le entità complete dal database.
    
    // --- MAGAZZINO MAPPERS ---
    public MagazzinoDTO toDto(Magazzino magazzino) {
        if (magazzino == null) return null;
        MagazzinoDTO dto = new MagazzinoDTO();
        dto.setId(magazzino.getId());
        dto.setQuantitaDisponibile(magazzino.getQuantitaDisponibile());
        dto.setSogliaMinima(magazzino.getSogliaMinima());
        if (magazzino.getIngrediente() != null) {
            dto.setIngrediente(toDto(magazzino.getIngrediente()));
        }
        return dto;
    }
}