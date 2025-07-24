package com.kintari.kintarimanager.controller;

import com.kintari.kintarimanager.dto.IngredienteDTO;
import com.kintari.kintarimanager.service.IngredienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ingredienti")
@RequiredArgsConstructor
@Tag(name = "Ingredienti", description = "Gestione degli ingredienti e materie prime")
@SecurityRequirement(name = "basicAuth")
public class IngredienteController {

    private final IngredienteService ingredienteService;

    @GetMapping
    @Operation(
        summary = "Recupera tutti gli ingredienti",
        description = "Restituisce la lista completa di tutti gli ingredienti presenti nel sistema"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista ingredienti recuperata con successo",
                    content = @Content(mediaType = "application/json", 
                                     schema = @Schema(implementation = IngredienteDTO.class))),
        @ApiResponse(responseCode = "401", description = "Non autorizzato - credenziali mancanti o non valide",
                    content = @Content),
        @ApiResponse(responseCode = "403", description = "Accesso negato - ruolo ADMIN richiesto",
                    content = @Content)
    })
    public ResponseEntity<List<IngredienteDTO>> getAllIngredienti() {
        List<IngredienteDTO> ingredienti = ingredienteService.findAllIngredienti();
        return ResponseEntity.ok(ingredienti);
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Recupera un ingrediente per ID",
        description = "Restituisce i dettagli di un singolo ingrediente identificato dal suo ID univoco"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ingrediente trovato",
                    content = @Content(mediaType = "application/json", 
                                     schema = @Schema(implementation = IngredienteDTO.class))),
        @ApiResponse(responseCode = "404", description = "Ingrediente non trovato",
                    content = @Content),
        @ApiResponse(responseCode = "401", description = "Non autorizzato",
                    content = @Content),
        @ApiResponse(responseCode = "403", description = "Accesso negato",
                    content = @Content)
    })
    public ResponseEntity<IngredienteDTO> getIngredienteById(
            @Parameter(description = "ID univoco dell'ingrediente", required = true)
            @PathVariable Long id) {
        return ingredienteService.findIngredienteById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(
        summary = "Crea un nuovo ingrediente",
        description = "Aggiunge un nuovo ingrediente al sistema con le informazioni fornite"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Ingrediente creato con successo",
                    content = @Content(mediaType = "application/json", 
                                     schema = @Schema(implementation = IngredienteDTO.class))),
        @ApiResponse(responseCode = "400", description = "Dati non validi nel corpo della richiesta",
                    content = @Content),
        @ApiResponse(responseCode = "401", description = "Non autorizzato",
                    content = @Content),
        @ApiResponse(responseCode = "403", description = "Accesso negato",
                    content = @Content)
    })
    public ResponseEntity<IngredienteDTO> createIngrediente(
            @Parameter(description = "Dati del nuovo ingrediente", required = true)
            @RequestBody IngredienteDTO ingredienteDTO) {
        IngredienteDTO savedIngrediente = ingredienteService.saveIngrediente(ingredienteDTO);
        return new ResponseEntity<>(savedIngrediente, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Aggiorna un ingrediente esistente",
        description = "Modifica i dati di un ingrediente esistente identificato dal suo ID"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ingrediente aggiornato con successo",
                    content = @Content(mediaType = "application/json", 
                                     schema = @Schema(implementation = IngredienteDTO.class))),
        @ApiResponse(responseCode = "400", description = "Dati non validi",
                    content = @Content),
        @ApiResponse(responseCode = "404", description = "Ingrediente non trovato",
                    content = @Content),
        @ApiResponse(responseCode = "401", description = "Non autorizzato",
                    content = @Content),
        @ApiResponse(responseCode = "403", description = "Accesso negato",
                    content = @Content)
    })
    public ResponseEntity<IngredienteDTO> updateIngrediente(
            @Parameter(description = "ID dell'ingrediente da aggiornare", required = true)
            @PathVariable Long id, 
            @Parameter(description = "Nuovi dati dell'ingrediente", required = true)
            @RequestBody IngredienteDTO ingredienteDTO) {
        ingredienteDTO.setId(id);
        IngredienteDTO updatedIngrediente = ingredienteService.saveIngrediente(ingredienteDTO);
        return ResponseEntity.ok(updatedIngrediente);
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Elimina un ingrediente",
        description = "Rimuove definitivamente un ingrediente dal sistema"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Ingrediente eliminato con successo",
                    content = @Content),
        @ApiResponse(responseCode = "404", description = "Ingrediente non trovato",
                    content = @Content),
        @ApiResponse(responseCode = "401", description = "Non autorizzato",
                    content = @Content),
        @ApiResponse(responseCode = "403", description = "Accesso negato",
                    content = @Content)
    })
    public ResponseEntity<Void> deleteIngrediente(
            @Parameter(description = "ID dell'ingrediente da eliminare", required = true)
            @PathVariable Long id) {
        ingredienteService.deleteIngrediente(id);
        return ResponseEntity.noContent().build();
    }
}