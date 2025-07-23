// docs/source/service/IngredienteService.html

package com.kintari.kintarimanager.service;

import com.kintari.kintarimanager.dto.IngredienteDTO;

import java.util.List;
import java.util.Optional;

public interface IngredienteService {

    List<IngredienteDTO> findAllIngredienti();

    Optional<IngredienteDTO> findIngredienteById(Long id);

    IngredienteDTO saveIngrediente(IngredienteDTO ingredienteDTO);

    void deleteIngrediente(Long id);
}