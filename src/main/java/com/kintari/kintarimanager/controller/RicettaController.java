// docs/source/controller/RicettaController.html

package com.kintari.kintarimanager.controller;

import com.kintari.kintarimanager.dto.RicettaDTO;
import com.kintari.kintarimanager.service.RicettaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prodotti-finiti/{prodottoId}/ricetta")
@RequiredArgsConstructor
public class RicettaController {

    private final RicettaService ricettaService;

    @GetMapping
    public ResponseEntity<List<RicettaDTO>> getRicettaByProdottoId(@PathVariable Long prodottoId) {
        List<RicettaDTO> ricetta = ricettaService.findRicetteByProdottoFinitoId(prodottoId);
        return ResponseEntity.ok(ricetta);
    }

    @PostMapping
    public ResponseEntity<RicettaDTO> addIngredienteToRicetta(
            @PathVariable Long prodottoId,
            @RequestBody RicettaDTO ricettaDTO) {
        RicettaDTO nuovaRigaRicetta = ricettaService.addIngredienteToRicetta(prodottoId, ricettaDTO);
        return new ResponseEntity<>(nuovaRigaRicetta, HttpStatus.CREATED);
    }

    // Nota: L'ID qui si riferisce all'ID della riga specifica della ricetta, non all'ID dell'ingrediente.
    @DeleteMapping("/{ricettaId}")
    public ResponseEntity<Void> removeIngredienteFromRicetta(@PathVariable Long prodottoId, @PathVariable Long ricettaId) {
        ricettaService.removeIngredienteFromRicetta(ricettaId);
        return ResponseEntity.noContent().build();
    }
}