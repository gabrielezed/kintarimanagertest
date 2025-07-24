package com.kintari.kintarimanager.controller;

import com.kintari.kintarimanager.dto.OrdineRequestDTO;
import com.kintari.kintarimanager.service.OrdineService;
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

@RestController
@RequestMapping("/api/ordini")
@RequiredArgsConstructor
@Tag(name = "Ordini", description = "Gestione degli ordini e scarico automatico dal magazzino")
@SecurityRequirement(name = "basicAuth")
public class OrdineController {

    private final OrdineService ordineService;

    @PostMapping
    @Operation(
        summary = "Crea un nuovo ordine",
        description = "Processa un ordine complesso contenente più prodotti e scarica automaticamente gli ingredienti necessari dal magazzino in modo atomico. Se non ci sono scorte sufficienti per qualsiasi ingrediente, l'intero ordine viene rifiutato."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ordine processato con successo, ingredienti scaricati dal magazzino",
                    content = @Content),
        @ApiResponse(responseCode = "400", description = "Dati dell'ordine non validi",
                    content = @Content),
        @ApiResponse(responseCode = "404", description = "Uno o più prodotti nell'ordine non esistono",
                    content = @Content),
        @ApiResponse(responseCode = "409", description = "Scorte insufficienti per uno o più ingredienti - ordine rifiutato",
                    content = @Content),
        @ApiResponse(responseCode = "401", description = "Non autorizzato",
                    content = @Content),
        @ApiResponse(responseCode = "403", description = "Accesso negato - ruolo USER o ADMIN richiesto",
                    content = @Content)
    })
    public ResponseEntity<Void> creaOrdine(
            @Parameter(description = "Dettagli dell'ordine con lista dei prodotti e quantità", required = true,
                      content = @Content(
                          mediaType = "application/json",
                          schema = @Schema(implementation = OrdineRequestDTO.class),
                          examples = @ExampleObject(
                              name = "Esempio ordine",
                              value = "{\n" +
                                     "  \"prodotti\": [\n" +
                                     "    { \"prodottoId\": 1, \"quantita\": 2 },\n" +
                                     "    { \"prodottoId\": 3, \"quantita\": 1 }\n" +
                                     "  ]\n" +
                                     "}",
                              description = "Ordine di esempio: 2 unità del prodotto con ID 1 e 1 unità del prodotto con ID 3"
                          )
                      ))
            @RequestBody OrdineRequestDTO ordineRequest) {
        ordineService.processaOrdine(ordineRequest);
        return ResponseEntity.ok().build();
    }
}