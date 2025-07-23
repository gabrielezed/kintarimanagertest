// docs/source/controller/IngredienteController.html

package com.kintari.kintarimanager.controller;

import com.kintari.kintarimanager.dto.IngredienteDTO;
import com.kintari.kintarimanager.service.IngredienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ingredienti")
@RequiredArgsConstructor
public class IngredienteController {

    private final IngredienteService ingredienteService;

    @GetMapping
    public ResponseEntity<List<IngredienteDTO>> getAllIngredienti() {
        List<IngredienteDTO> ingredienti = ingredienteService.findAllIngredienti();
        return ResponseEntity.ok(ingredienti);
    }

    @GetMapping("/{id}")
    public ResponseEntity<IngredienteDTO> getIngredienteById(@PathVariable Long id) {
        return ingredienteService.findIngredienteById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<IngredienteDTO> createIngrediente(@RequestBody IngredienteDTO ingredienteDTO) {
        IngredienteDTO savedIngrediente = ingredienteService.saveIngrediente(ingredienteDTO);
        return new ResponseEntity<>(savedIngrediente, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<IngredienteDTO> updateIngrediente(@PathVariable Long id, @RequestBody IngredienteDTO ingredienteDTO) {
        // Assicura che l'ID nel DTO corrisponda a quello nel path
        ingredienteDTO.setId(id);
        IngredienteDTO updatedIngrediente = ingredienteService.saveIngrediente(ingredienteDTO);
        return ResponseEntity.ok(updatedIngrediente);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIngrediente(@PathVariable Long id) {
        ingredienteService.deleteIngrediente(id);
        return ResponseEntity.noContent().build();
    }
}