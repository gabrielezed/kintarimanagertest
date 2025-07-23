// docs/source/service/ProdottoFinitoService.html

package com.kintari.kintarimanager.service;

import com.kintari.kintarimanager.dto.ProdottoFinitoDTO;

import java.util.List;
import java.util.Optional;

public interface ProdottoFinitoService {

    List<ProdottoFinitoDTO> findAllProdottiFiniti();

    Optional<ProdottoFinitoDTO> findProdottoFinitoById(Long id);

    ProdottoFinitoDTO saveProdottoFinito(ProdottoFinitoDTO prodottoFinitoDTO);

    void deleteProdottoFinito(Long id);
}