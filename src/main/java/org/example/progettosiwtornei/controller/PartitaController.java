package org.example.progettosiwtornei.controller;

import org.example.progettosiwtornei.entity.Commento;
import org.example.progettosiwtornei.service.PartitaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PartitaController {

    private final PartitaService partitaService;

    public PartitaController(PartitaService p){
        this.partitaService = p;
    }

    @GetMapping("/listaPartite")
    public String getAllPartite(Model model) {
        model.addAttribute("modelPartite", this.partitaService.getAllPartite());
        return "listaPartite";
    }

    @GetMapping("/partita/{id}")
    public String getFormDettaglioPartita(@PathVariable Long id,Model model){
        model.addAttribute("modelPartita", this.partitaService.getPartitaById(id));
        model.addAttribute("modelCommentiPartita", this.partitaService.getPartitaById(id).getListaCommenti());
        return "/partita";
    }



}