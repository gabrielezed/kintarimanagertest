// docs/source/service/MagazzinoService.html

package com.kintari.kintarimanager.service;

import com.kintari.kintarimanager.dto.MagazzinoDTO;

import java.util.List;

public interface MagazzinoService {

    List<MagazzinoDTO> getStatoMagazzino();

    MagazzinoDTO aggiornaScorta(Long ingredienteId, double quantita);

    List<MagazzinoDTO> getIngredientiSottoSoglia();
}