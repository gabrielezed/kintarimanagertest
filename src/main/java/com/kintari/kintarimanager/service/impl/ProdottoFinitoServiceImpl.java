// docs/source/service/impl/ProdottoFinitoServiceImpl.html

package com.kintari.kintarimanager.service.impl;

import com.kintari.kintarimanager.dto.ProdottoFinitoDTO;
import com.kintari.kintarimanager.model.ProdottoFinito;
import com.kintari.kintarimanager.repository.ProdottoFinitoRepository;
import com.kintari.kintarimanager.service.ProdottoFinitoService;
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
public class ProdottoFinitoServiceImpl implements ProdottoFinitoService {

    private final ProdottoFinitoRepository prodottoFinitoRepository;
    private final DtoMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public List<ProdottoFinitoDTO> findAllProdottiFiniti() {
        return prodottoFinitoRepository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProdottoFinitoDTO> findProdottoFinitoById(Long id) {
        return prodottoFinitoRepository.findById(id)
                .map(mapper::toDto);
    }

    @Override
    public ProdottoFinitoDTO saveProdottoFinito(ProdottoFinitoDTO prodottoFinitoDTO) {
        ProdottoFinito prodotto = mapper.toEntity(prodottoFinitoDTO);
        ProdottoFinito savedProdotto = prodottoFinitoRepository.save(prodotto);
        return mapper.toDto(savedProdotto);
    }

    @Override
    public void deleteProdottoFinito(Long id) {
        prodottoFinitoRepository.deleteById(id);
    }
}