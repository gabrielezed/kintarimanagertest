package com.kintari.kintarimanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/")
    public String getDashboard() {
        return "dashboard";
    }

    @GetMapping("/ingredienti")
    public String getIngredienti() {
        return "ingredienti";
    }

    @GetMapping("/prodotti")
    public String getProdotti() {
        return "prodotti";
    }

    @GetMapping("/magazzino")
    public String getMagazzino() {
        return "magazzino";
    }
}