package com.kintari.kintarimanager.controller;

import com.kintari.kintarimanager.dto.ProdottoFinitoDTO;
import com.kintari.kintarimanager.service.ProdottoFinitoService;
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
@RequestMapping("/api/prodotti-finiti")
@RequiredArgsConstructor
@Tag(name = "Prodotti Finiti", description = "Gestione dei prodotti del menu (pizze, bevande, etc.)")
@SecurityRequirement(name = "basicAuth")
public class ProdottoFinitoController {

    private final ProdottoFinitoService prodottoFinitoService;

    @GetMapping
    @Operation(
        summary = "Recupera tutti i prodotti finiti",
        description = "Restituisce la lista completa di tutti i prodotti del menu"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista prodotti recuperata con successo",
                    content = @Content(mediaType = "application/json", 
                                     schema = @Schema(implementation = ProdottoFinitoDTO.class))),
        @ApiResponse(responseCode = "401", description = "Non autorizzato",
                    content = @Content),
        @ApiResponse(responseCode = "403", description = "Accesso negato - ruolo ADMIN richiesto",
                    content = @Content)
    })
    public ResponseEntity<List<ProdottoFinitoDTO>> getAllProdottiFiniti() {
        List<ProdottoFinitoDTO> prodotti = prodottoFinitoService.findAllProdottiFiniti();
        return ResponseEntity.ok(prodotti);
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Recupera un prodotto per ID",
        description = "Restituisce i dettagli di un singolo prodotto identificato dal suo ID"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Prodotto trovato",
                    content = @Content(mediaType = "application/json", 
                                     schema = @Schema(implementation = ProdottoFinitoDTO.class))),
        @ApiResponse(responseCode = "404", description = "Prodotto non trovato",
                    content = @Content),
        @ApiResponse(responseCode = "401", description = "Non autorizzato",
                    content = @Content),
        @ApiResponse(responseCode = "403", description = "Accesso negato",
                    content = @Content)
    })
    public ResponseEntity<ProdottoFinitoDTO> getProdottoFinitoById(
            @Parameter(description = "ID univoco del prodotto", required = true)
            @PathVariable Long id) {
        return prodottoFinitoService.findProdottoFinitoById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(
        summary = "Crea un nuovo prodotto",
        description = "Aggiunge un nuovo prodotto al menu con le informazioni fornite"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Prodotto creato con successo",
                    content = @Content(mediaType = "application/json", 
                                     schema = @Schema(implementation = ProdottoFinitoDTO.class))),
        @ApiResponse(responseCode = "400", description = "Dati non validi nel corpo della richiesta",
                    content = @Content),
        @ApiResponse(responseCode = "401", description = "Non autorizzato",
                    content = @Content),
        @ApiResponse(responseCode = "403", description = "Accesso negato",
                    content = @Content)
    })
    public ResponseEntity<ProdottoFinitoDTO> createProdottoFinito(
            @Parameter(description = "Dati del nuovo prodotto", required = true)
            @RequestBody ProdottoFinitoDTO prodottoFinitoDTO) {
        ProdottoFinitoDTO savedProdotto = prodottoFinitoService.saveProdottoFinito(prodottoFinitoDTO);
        return new ResponseEntity<>(savedProdotto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Aggiorna un prodotto esistente",
        description = "Modifica i dati di un prodotto esistente identificato dal suo ID"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Prodotto aggiornato con successo",
                    content = @Content(mediaType = "application/json", 
                                     schema = @Schema(implementation = ProdottoFinitoDTO.class))),
        @ApiResponse(responseCode = "400", description = "Dati non validi",
                    content = @Content),
        @ApiResponse(responseCode = "404", description = "Prodotto non trovato",
                    content = @Content),
        @ApiResponse(responseCode = "401", description = "Non autorizzato",
                    content = @Content),
        @ApiResponse(responseCode = "403", description = "Accesso negato",
                    content = @Content)
    })
    public ResponseEntity<ProdottoFinitoDTO> updateProdottoFinito(
            @Parameter(description = "ID del prodotto da aggiornare", required = true)
            @PathVariable Long id, 
            @Parameter(description = "Nuovi dati del prodotto", required = true)
            @RequestBody ProdottoFinitoDTO prodottoFinitoDTO) {
        prodottoFinitoDTO.setId(id);
        ProdottoFinitoDTO updatedProdotto = prodottoFinitoService.saveProdottoFinito(prodottoFinitoDTO);
        return ResponseEntity.ok(updatedProdotto);
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Elimina un prodotto",
        description = "Rimuove definitivamente un prodotto dal menu"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Prodotto eliminato con successo",
                    content = @Content),
        @ApiResponse(responseCode = "404", description = "Prodotto non trovato",
                    content = @Content),
        @ApiResponse(responseCode = "401", description = "Non autorizzato",
                    content = @Content),
        @ApiResponse(responseCode = "403", description = "Accesso negato",
                    content = @Content)
    })
    public ResponseEntity<Void> deleteProdottoFinito(
            @Parameter(description = "ID del prodotto da eliminare", required = true)
            @PathVariable Long id) {
        prodottoFinitoService.deleteProdottoFinito(id);
        return ResponseEntity.noContent().build();
    }
}