package com.kintari.kintarimanager.controller;

import com.kintari.kintarimanager.dto.RicettaDTO;
import com.kintari.kintarimanager.service.RicettaService;
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
@RequestMapping("/api/prodotti-finiti/{prodottoId}/ricetta")
@RequiredArgsConstructor
@Tag(name = "Ricette", description = "Gestione delle ricette dei prodotti (ingredienti e quantità)")
@SecurityRequirement(name = "basicAuth")
public class RicettaController {

    private final RicettaService ricettaService;

    @GetMapping
    @Operation(
        summary = "Recupera la ricetta di un prodotto",
        description = "Restituisce la lista completa degli ingredienti e quantità per un prodotto specifico"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ricetta recuperata con successo",
                    content = @Content(mediaType = "application/json", 
                                     schema = @Schema(implementation = RicettaDTO.class))),
        @ApiResponse(responseCode = "404", description = "Prodotto non trovato",
                    content = @Content),
        @ApiResponse(responseCode = "401", description = "Non autorizzato",
                    content = @Content),
        @ApiResponse(responseCode = "403", description = "Accesso negato - ruolo ADMIN richiesto",
                    content = @Content)
    })
    public ResponseEntity<List<RicettaDTO>> getRicettaByProdottoId(
            @Parameter(description = "ID del prodotto di cui visualizzare la ricetta", required = true)
            @PathVariable Long prodottoId) {
        List<RicettaDTO> ricetta = ricettaService.findRicetteByProdottoFinitoId(prodottoId);
        return ResponseEntity.ok(ricetta);
    }

    @PostMapping
    @Operation(
        summary = "Aggiungi ingrediente alla ricetta",
        description = "Aggiunge un nuovo ingrediente con la sua quantità alla ricetta del prodotto"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Ingrediente aggiunto alla ricetta con successo",
                    content = @Content(mediaType = "application/json", 
                                     schema = @Schema(implementation = RicettaDTO.class))),
        @ApiResponse(responseCode = "400", description = "Dati non validi nel corpo della richiesta",
                    content = @Content),
        @ApiResponse(responseCode = "404", description = "Prodotto o ingrediente non trovato",
                    content = @Content),
        @ApiResponse(responseCode = "401", description = "Non autorizzato",
                    content = @Content),
        @ApiResponse(responseCode = "403", description = "Accesso negato",
                    content = @Content)
    })
    public ResponseEntity<RicettaDTO> addIngredienteToRicetta(
            @Parameter(description = "ID del prodotto a cui aggiungere l'ingrediente", required = true)
            @PathVariable Long prodottoId,
            @Parameter(description = "Dati dell'ingrediente da aggiungere (ingredienteId e quantità)", required = true)
            @RequestBody RicettaDTO ricettaDTO) {
        RicettaDTO nuovaRigaRicetta = ricettaService.addIngredienteToRicetta(prodottoId, ricettaDTO);
        return new ResponseEntity<>(nuovaRigaRicetta, HttpStatus.CREATED);
    }

    @DeleteMapping("/{ricettaId}")
    @Operation(
        summary = "Rimuovi ingrediente dalla ricetta",
        description = "Rimuove completamente un ingrediente dalla ricetta del prodotto"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Ingrediente rimosso dalla ricetta con successo",
                    content = @Content),
        @ApiResponse(responseCode = "404", description = "Riga di ricetta non trovata",
                    content = @Content),
        @ApiResponse(responseCode = "401", description = "Non autorizzato",
                    content = @Content),
        @ApiResponse(responseCode = "403", description = "Accesso negato",
                    content = @Content)
    })
    public ResponseEntity<Void> removeIngredienteFromRicetta(
            @Parameter(description = "ID del prodotto (non utilizzato ma richiesto dal path)", required = true)
            @PathVariable Long prodottoId, 
            @Parameter(description = "ID della riga di ricetta da eliminare", required = true)
            @PathVariable Long ricettaId) {
        ricettaService.removeIngredienteFromRicetta(ricettaId);
        return ResponseEntity.noContent().build();
    }
}