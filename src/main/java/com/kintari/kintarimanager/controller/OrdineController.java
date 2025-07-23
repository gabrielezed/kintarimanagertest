package com.kintari.kintarimanager.controller;

import com.kintari.kintarimanager.dto.OrdineRequestDTO;
import com.kintari.kintarimanager.service.OrdineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ordini")
@RequiredArgsConstructor
public class OrdineController {

    private final OrdineService ordineService;

    @PostMapping
    public ResponseEntity<Void> creaOrdine(@RequestBody OrdineRequestDTO ordineRequest) {
        ordineService.processaOrdine(ordineRequest);
        return ResponseEntity.ok().build();
    }
}