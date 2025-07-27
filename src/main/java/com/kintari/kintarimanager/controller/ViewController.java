package com.kintari.kintarimanager.controller;

import com.kintari.kintarimanager.dto.IngredienteDTO;
import com.kintari.kintarimanager.dto.ProdottoFinitoDTO;
import com.kintari.kintarimanager.dto.RicettaDTO;
import com.kintari.kintarimanager.service.IngredienteService;
import com.kintari.kintarimanager.service.MagazzinoService;
import com.kintari.kintarimanager.service.ProdottoFinitoService;
import com.kintari.kintarimanager.service.RicettaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class ViewController {

    private final IngredienteService ingredienteService;
    private final ProdottoFinitoService prodottoFinitoService;
    private final MagazzinoService magazzinoService;
    private final RicettaService ricettaService;

    @GetMapping("/")
    public String getDashboard() {
        return "dashboard";
    }

    // ========== INGREDIENTI ==========
    @GetMapping("/ingredienti")
    public String getIngredienti(Model model) {
        model.addAttribute("ingredienti", ingredienteService.findAllIngredienti());
        model.addAttribute("nuovoIngrediente", new IngredienteDTO());
        return "ingredienti";
    }

    @PostMapping("/ingredienti")
    public String createIngrediente(@ModelAttribute("nuovoIngrediente") IngredienteDTO ingredienteDTO) {
        ingredienteService.saveIngrediente(ingredienteDTO);
        return "redirect:/ingredienti";
    }

    @PostMapping("/ingredienti/update/{id}")
    public String updateIngrediente(@PathVariable Long id, @ModelAttribute IngredienteDTO ingredienteDTO) {
        ingredienteDTO.setId(id);
        ingredienteService.saveIngrediente(ingredienteDTO);
        return "redirect:/ingredienti";
    }

    @PostMapping("/ingredienti/delete/{id}")
    public String deleteIngrediente(@PathVariable Long id) {
        ingredienteService.deleteIngrediente(id);
        return "redirect:/ingredienti";
    }

    // ========== PRODOTTI ==========
    @GetMapping("/prodotti")
    public String getProdotti(Model model) {
        model.addAttribute("prodotti", prodottoFinitoService.findAllProdottiFiniti());
        model.addAttribute("nuovoProdotto", new ProdottoFinitoDTO());
        return "prodotti";
    }

    @PostMapping("/prodotti")
    public String createProdotto(@ModelAttribute("nuovoProdotto") ProdottoFinitoDTO prodottoDTO) {
        prodottoFinitoService.saveProdottoFinito(prodottoDTO);
        return "redirect:/prodotti";
    }

    @PostMapping("/prodotti/update/{id}")
    public String updateProdotto(@PathVariable Long id, @ModelAttribute ProdottoFinitoDTO prodottoDTO) {
        prodottoDTO.setId(id);
        prodottoFinitoService.saveProdottoFinito(prodottoDTO);
        return "redirect:/prodotti";
    }

    @PostMapping("/prodotti/delete/{id}")
    public String deleteProdotto(@PathVariable Long id) {
        prodottoFinitoService.deleteProdottoFinito(id);
        return "redirect:/prodotti";
    }

    // ========== RICETTE ==========
    @GetMapping("/prodotti/{prodottoId}/ricetta")
    public String getRicetta(@PathVariable Long prodottoId, Model model) {
        ProdottoFinitoDTO prodotto = prodottoFinitoService.findProdottoFinitoById(prodottoId)
                .orElseThrow(() -> new RuntimeException("Prodotto non trovato"));
        
        model.addAttribute("prodotto", prodotto);
        model.addAttribute("ricetta", ricettaService.findRicetteByProdottoFinitoId(prodottoId));
        model.addAttribute("ingredienti", ingredienteService.findAllIngredienti());
        model.addAttribute("nuovaRigaRicetta", new RicettaDTO());
        
        return "ricetta";
    }

    @PostMapping("/prodotti/{prodottoId}/ricetta/add")
    public String addIngredienteToRicetta(@PathVariable Long prodottoId, 
                                          @ModelAttribute("nuovaRigaRicetta") RicettaDTO ricettaDTO) {
        ricettaService.addIngredienteToRicetta(prodottoId, ricettaDTO);
        return "redirect:/prodotti/" + prodottoId + "/ricetta";
    }

    @PostMapping("/prodotti/{prodottoId}/ricetta/delete/{ricettaId}")
    public String removeIngredienteFromRicetta(@PathVariable Long prodottoId, 
                                               @PathVariable Long ricettaId) {
        ricettaService.removeIngredienteFromRicetta(ricettaId);
        return "redirect:/prodotti/" + prodottoId + "/ricetta";
    }

    // ========== MAGAZZINO ==========
    @GetMapping("/magazzino")
    public String getMagazzino(Model model) {
        model.addAttribute("statoMagazzino", magazzinoService.getStatoMagazzino());
        return "magazzino";
    }

    @PostMapping("/magazzino/update/{ingredienteId}")
    public String updateScorta(@PathVariable Long ingredienteId, 
                               @RequestParam("quantita") Double quantita) {
        magazzinoService.aggiornaScorta(ingredienteId, quantita);
        return "redirect:/magazzino";
    }
}