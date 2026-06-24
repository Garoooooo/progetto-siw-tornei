package org.example.progettosiwtornei.controller;

import org.example.progettosiwtornei.entity.Giocatore;
import org.example.progettosiwtornei.entity.Squadra;
import org.example.progettosiwtornei.service.GiocatoreService;
import org.example.progettosiwtornei.service.SquadraService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminGiocatoreController {
    private final GiocatoreService giocatoreService;
    private final SquadraService squadraService;

    public AdminGiocatoreController(GiocatoreService g, SquadraService s){
        this.giocatoreService = g;
        this.squadraService = s;
    }

    @GetMapping ("/admin/listaGiocatori/nuovo")
    public String mostraFormNuovoGiocatore(Model model){
        model.addAttribute("modelNuovoGiocatore", new Giocatore());
        model.addAttribute("modelSquadre", squadraService.getAllSquadre());
        return "/admin/nuovoGiocatore";
    }

    @PostMapping("/admin/listaGiocatori/nuovo")
    public String registraNuovoGiocatore(Giocatore giocatore, @RequestParam(required = false) Long squadraId) {

        if (squadraId != null) {
            Squadra squadra = this.squadraService.getSquadraById(squadraId);
            giocatore.setSquadra(squadra);
        }

        this.giocatoreService.salvaGiocatore(giocatore);
        return "redirect:/listaGiocatori";
    }

    @PostMapping("/admin/giocatore/{id}/elimina")
    public String eliminaGiocatore(@PathVariable Long id) {

        this.giocatoreService.eliminaGiocatore(id);

        return "redirect:/listaGiocatori";
    }

    @GetMapping("/admin/giocatore/{id}/modifica")
    public String mostraFormModificaGiocatore(@PathVariable Long id, Model model) {
        Giocatore giocatore = this.giocatoreService.getGiocatore(id);

        model.addAttribute("modelGiocatore", giocatore);
        model.addAttribute("modelSquadre", this.squadraService.getAllSquadre());
        return "admin/modificaGiocatore";
    }

    @PostMapping("/admin/giocatore/{id}/modifica")
    public String registraModificaGiocatore(@PathVariable Long id, Giocatore giocatore, @RequestParam(required = false) Long squadraId) {

        this.giocatoreService.modificaGiocatore(id, giocatore, squadraId);
        return "redirect:/giocatore/" + id;
    }

 }
