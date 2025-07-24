package com.kintari.kintarimanager.controller;

import com.kintari.kintarimanager.dto.MagazzinoDTO;
import com.kintari.kintarimanager.service.MagazzinoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/magazzino")
@RequiredArgsConstructor
@Tag(name = "Magazzino", description = "Gestione dello stato delle scorte e inventario")
@SecurityRequirement(name = "basicAuth")
public class MagazzinoController {

    private final MagazzinoService magazzinoService;

    @GetMapping
    @Operation(
        summary = "Visualizza stato completo del magazzino",
        description = "Restituisce lo stato attuale di tutte le scorte con dettagli degli ingredienti"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Stato magazzino recuperato con successo",
                    content = @Content(mediaType = "application/json", 
                                     schema = @Schema(implementation = MagazzinoDTO.class))),
        @ApiResponse(responseCode = "401", description = "Non autorizzato",
                    content = @Content),
        @ApiResponse(responseCode = "403", description = "Accesso negato - ruolo ADMIN richiesto",
                    content = @Content)
    })
    public ResponseEntity<List<MagazzinoDTO>> getStatoMagazzino() {
        List<MagazzinoDTO> statoMagazzino = magazzinoService.getStatoMagazzino();
        return ResponseEntity.ok(statoMagazzino);
    }

    @GetMapping("/sotto-soglia")
    @Operation(
        summary = "Ingredienti sotto soglia minima",
        description = "Restituisce solo gli ingredienti la cui scorta è al di sotto della soglia di riordino"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista ingredienti sotto soglia recuperata con successo",
                    content = @Content(mediaType = "application/json", 
                                     schema = @Schema(implementation = MagazzinoDTO.class))),
        @ApiResponse(responseCode = "401", description = "Non autorizzato",
                    content = @Content),
        @ApiResponse(responseCode = "403", description = "Accesso negato - ruolo ADMIN richiesto",
                    content = @Content)
    })
    public ResponseEntity<List<MagazzinoDTO>> getIngredientiSottoSoglia() {
        List<MagazzinoDTO> ingredienti = magazzinoService.getIngredientiSottoSoglia();
        return ResponseEntity.ok(ingredienti);
    }

    @PutMapping("/ingredienti/{ingredienteId}")
    @Operation(
        summary = "Aggiorna scorta di un ingrediente",
        description = "Modifica la quantità disponibile in magazzino per un ingrediente specifico"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Scorta aggiornata con successo",
                    content = @Content(mediaType = "application/json", 
                                     schema = @Schema(implementation = MagazzinoDTO.class))),
        @ApiResponse(responseCode = "400", description = "Richiesta non valida - campo 'quantita' mancante o non valido",
                    content = @Content),
        @ApiResponse(responseCode = "404", description = "Ingrediente non trovato",
                    content = @Content),
        @ApiResponse(responseCode = "401", description = "Non autorizzato",
                    content = @Content),
        @ApiResponse(responseCode = "403", description = "Accesso negato - ruolo ADMIN richiesto",
                    content = @Content)
    })
    public ResponseEntity<MagazzinoDTO> aggiornaScorta(
            @Parameter(description = "ID dell'ingrediente di cui aggiornare la scorta", required = true)
            @PathVariable Long ingredienteId,
            @Parameter(description = "Nuova quantità disponibile", required = true,
                      content = @Content(examples = @ExampleObject(value = "{\"quantita\": 15.5}")))
            @RequestBody Map<String, Double> request) {
        
        Double quantita = request.get("quantita");
        if (quantita == null) {
            return ResponseEntity.badRequest().build();
        }
        
        MagazzinoDTO magazzinoAggiornato = magazzinoService.aggiornaScorta(ingredienteId, quantita);
        return ResponseEntity.ok(magazzinoAggiornato);
    }
}