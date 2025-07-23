// docs/source/service/impl/RicettaServiceImpl.html

package com.kintari.kintarimanager.service.impl;

import com.kintari.kintarimanager.dto.RicettaDTO;
import com.kintari.kintarimanager.model.Ingrediente;
import com.kintari.kintarimanager.model.ProdottoFinito;
import com.kintari.kintarimanager.model.Ricetta;
import com.kintari.kintarimanager.repository.IngredienteRepository;
import com.kintari.kintarimanager.repository.ProdottoFinitoRepository;
import com.kintari.kintarimanager.repository.RicettaRepository;
import com.kintari.kintarimanager.service.RicettaService;
import com.kintari.kintarimanager.service.mapper.DtoMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class RicettaServiceImpl implements RicettaService {

    private final RicettaRepository ricettaRepository;
    private final ProdottoFinitoRepository prodottoFinitoRepository;
    private final IngredienteRepository ingredienteRepository;
    private final DtoMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public List<RicettaDTO> findRicetteByProdottoFinitoId(Long prodottoFinitoId) {
        return ricettaRepository.findByProdottoFinitoId(prodottoFinitoId).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public RicettaDTO addIngredienteToRicetta(Long prodottoFinitoId, RicettaDTO ricettaDTO) {
        ProdottoFinito prodotto = prodottoFinitoRepository.findById(prodottoFinitoId)
                .orElseThrow(() -> new EntityNotFoundException("Prodotto non trovato con id: " + prodottoFinitoId));

        Ingrediente ingrediente = ingredienteRepository.findById(ricettaDTO.getIngredienteId())
                .orElseThrow(() -> new EntityNotFoundException("Ingrediente non trovato con id: " + ricettaDTO.getIngredienteId()));

        Ricetta nuovaRicetta = new Ricetta();
        nuovaRicetta.setProdottoFinito(prodotto);
        nuovaRicetta.setIngrediente(ingrediente);
        nuovaRicetta.setQuantita(ricettaDTO.getQuantita());

        Ricetta savedRicetta = ricettaRepository.save(nuovaRicetta);
        return mapper.toDto(savedRicetta);
    }

    @Override
    public void removeIngredienteFromRicetta(Long ricettaId) {
        ricettaRepository.deleteById(ricettaId);
    }
}