// docs/source/controller/ProdottoFinitoController.html

package com.kintari.kintarimanager.controller;

import com.kintari.kintarimanager.dto.ProdottoFinitoDTO;
import com.kintari.kintarimanager.service.ProdottoFinitoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prodotti-finiti")
@RequiredArgsConstructor
public class ProdottoFinitoController {

    private final ProdottoFinitoService prodottoFinitoService;

    @GetMapping
    public ResponseEntity<List<ProdottoFinitoDTO>> getAllProdottiFiniti() {
        List<ProdottoFinitoDTO> prodotti = prodottoFinitoService.findAllProdottiFiniti();
        return ResponseEntity.ok(prodotti);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdottoFinitoDTO> getProdottoFinitoById(@PathVariable Long id) {
        return prodottoFinitoService.findProdottoFinitoById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ProdottoFinitoDTO> createProdottoFinito(@RequestBody ProdottoFinitoDTO prodottoFinitoDTO) {
        ProdottoFinitoDTO savedProdotto = prodottoFinitoService.saveProdottoFinito(prodottoFinitoDTO);
        return new ResponseEntity<>(savedProdotto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdottoFinitoDTO> updateProdottoFinito(@PathVariable Long id, @RequestBody ProdottoFinitoDTO prodottoFinitoDTO) {
        prodottoFinitoDTO.setId(id);
        ProdottoFinitoDTO updatedProdotto = prodottoFinitoService.saveProdottoFinito(prodottoFinitoDTO);
        return ResponseEntity.ok(updatedProdotto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProdottoFinito(@PathVariable Long id) {
        prodottoFinitoService.deleteProdottoFinito(id);
        return ResponseEntity.noContent().build();
    }
}