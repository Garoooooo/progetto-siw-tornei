package org.example.progettosiwtornei.controller;

import org.example.progettosiwtornei.service.GiocatoreService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class GiocatoreController {
    private final GiocatoreService giocatoreService;

    public GiocatoreController(GiocatoreService g){
        this.giocatoreService=g;
    }

    @GetMapping("/giocatore/{id}")
    public String getInfoGiocatore(Model model, @PathVariable Long id){
        model.addAttribute("modelGiocatore", giocatoreService.getGiocatore(id));
        return "/giocatore";
    }

    @GetMapping("/listaGiocatori")
    public String getAllGiocatori(Model model){
        model.addAttribute("modelGiocatori", giocatoreService.getAllGiocatoriRegistratiNelSistema());
        return "/listaGiocatori";
    }

}
