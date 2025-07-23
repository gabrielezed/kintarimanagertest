// docs/source/controller/MagazzinoController.html

package com.kintari.kintarimanager.controller;

import com.kintari.kintarimanager.dto.MagazzinoDTO;
import com.kintari.kintarimanager.service.MagazzinoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/magazzino")
@RequiredArgsConstructor
public class MagazzinoController {

    private final MagazzinoService magazzinoService;

    @GetMapping
    public ResponseEntity<List<MagazzinoDTO>> getStatoMagazzino() {
        List<MagazzinoDTO> statoMagazzino = magazzinoService.getStatoMagazzino();
        return ResponseEntity.ok(statoMagazzino);
    }

    @GetMapping("/sotto-soglia")
    public ResponseEntity<List<MagazzinoDTO>> getIngredientiSottoSoglia() {
        List<MagazzinoDTO> ingredienti = magazzinoService.getIngredientiSottoSoglia();
        return ResponseEntity.ok(ingredienti);
    }

    @PutMapping("/ingredienti/{ingredienteId}")
    public ResponseEntity<MagazzinoDTO> aggiornaScorta(
            @PathVariable Long ingredienteId,
            @RequestBody Map<String, Double> request) {
        
        Double quantita = request.get("quantita");
        if (quantita == null) {
            return ResponseEntity.badRequest().build();
        }
        
        MagazzinoDTO magazzinoAggiornato = magazzinoService.aggiornaScorta(ingredienteId, quantita);
        return ResponseEntity.ok(magazzinoAggiornato);
    }
}