package org.example.progettosiwtornei.controller;

import org.example.progettosiwtornei.service.SquadraService;
import org.example.progettosiwtornei.service.TorneoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class TorneoController {

    private final TorneoService torneoService;
    private final SquadraService squadraService;

    public TorneoController(TorneoService torneoService, SquadraService squadraService){
        this.torneoService = torneoService;
        this.squadraService = squadraService;
    }

    @GetMapping("/listaTornei")
    public String mostraAllTornei(Model model){
        model.addAttribute("modelTornei", this.torneoService.getAllTornei());
        return "listaTornei";
    }

    @GetMapping("/listaTornei/{id}")
    public String mostraInfoTorneoById(@PathVariable Long id, Model model) {
        model.addAttribute("modelTorneo", this.torneoService.getTorneoById(id));
        model.addAttribute("modelSquadre", this.torneoService.getSquadrePartecipantiAlTorneo(id));
        model.addAttribute("modelPartiteTorneo", this.torneoService.getPartiteTorneo(id));
        model.addAttribute("modelClassifica", this.torneoService.getClassifica(id));
        // serve per il menu admin "aggiungi squadra al torneo"
        model.addAttribute("modelTutteSquadre", this.squadraService.getAllSquadre());

        return "torneo";
    }
}