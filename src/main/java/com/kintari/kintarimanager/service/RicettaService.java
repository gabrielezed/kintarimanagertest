// docs/source/service/RicettaService.html

package com.kintari.kintarimanager.service;

import com.kintari.kintarimanager.dto.RicettaDTO;

import java.util.List;

public interface RicettaService {

    List<RicettaDTO> findRicetteByProdottoFinitoId(Long prodottoFinitoId);

    RicettaDTO addIngredienteToRicetta(Long prodottoFinitoId, RicettaDTO ricettaDTO);

    void removeIngredienteFromRicetta(Long ricettaId);
}