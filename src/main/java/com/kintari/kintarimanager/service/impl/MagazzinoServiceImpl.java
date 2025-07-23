// docs/source/service/impl/MagazzinoServiceImpl.html

package com.kintari.kintarimanager.service.impl;

import com.kintari.kintarimanager.dto.MagazzinoDTO;
import com.kintari.kintarimanager.model.Ingrediente;
import com.kintari.kintarimanager.model.Magazzino;
import com.kintari.kintarimanager.repository.IngredienteRepository;
import com.kintari.kintarimanager.repository.MagazzinoRepository;
import com.kintari.kintarimanager.service.MagazzinoService;
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
public class MagazzinoServiceImpl implements MagazzinoService {

    private final MagazzinoRepository magazzinoRepository;
    private final IngredienteRepository ingredienteRepository;
    private final DtoMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public List<MagazzinoDTO> getStatoMagazzino() {
        return magazzinoRepository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public MagazzinoDTO aggiornaScorta(Long ingredienteId, double quantita) {
        Magazzino magazzino = magazzinoRepository.findByIngredienteId(ingredienteId)
                .orElseGet(() -> {
                    Ingrediente ingrediente = ingredienteRepository.findById(ingredienteId)
                            .orElseThrow(() -> new EntityNotFoundException("Ingrediente non trovato con id: " + ingredienteId));
                    Magazzino nuovoMagazzino = new Magazzino();
                    nuovoMagazzino.setIngrediente(ingrediente);
                    return nuovoMagazzino;
                });

        magazzino.setQuantitaDisponibile(quantita);
        Magazzino savedMagazzino = magazzinoRepository.save(magazzino);
        return mapper.toDto(savedMagazzino);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MagazzinoDTO> getIngredientiSottoSoglia() {
        return magazzinoRepository.findAll().stream()
                .filter(m -> m.getQuantitaDisponibile() < m.getSogliaMinima())
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }
}