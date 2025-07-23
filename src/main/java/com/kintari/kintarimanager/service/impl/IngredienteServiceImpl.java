// docs/source/service/impl/IngredienteServiceImpl.html

package com.kintari.kintarimanager.service.impl;

import com.kintari.kintarimanager.dto.IngredienteDTO;
import com.kintari.kintarimanager.model.Ingrediente;
import com.kintari.kintarimanager.repository.IngredienteRepository;
import com.kintari.kintarimanager.service.IngredienteService;
import com.kintari.kintarimanager.service.mapper.DtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class IngredienteServiceImpl implements IngredienteService {

    private final IngredienteRepository ingredienteRepository;
    private final DtoMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public List<IngredienteDTO> findAllIngredienti() {
        return ingredienteRepository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<IngredienteDTO> findIngredienteById(Long id) {
        return ingredienteRepository.findById(id)
                .map(mapper::toDto);
    }

    @Override
    public IngredienteDTO saveIngrediente(IngredienteDTO ingredienteDTO) {
        Ingrediente ingrediente = mapper.toEntity(ingredienteDTO);
        Ingrediente savedIngrediente = ingredienteRepository.save(ingrediente);
        return mapper.toDto(savedIngrediente);
    }

    @Override
    public void deleteIngrediente(Long id) {
        ingredienteRepository.deleteById(id);
    }
}