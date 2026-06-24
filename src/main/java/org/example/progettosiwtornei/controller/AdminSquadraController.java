package org.example.progettosiwtornei.controller;

import org.example.progettosiwtornei.entity.Squadra;
import org.example.progettosiwtornei.service.GiocatoreService;
import org.example.progettosiwtornei.service.SquadraService;
import org.example.progettosiwtornei.service.TorneoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminSquadraController {

    private final SquadraService squadraService;
    private final TorneoService torneoService;
    private final GiocatoreService giocatoreService;

    public AdminSquadraController(SquadraService squadraService, TorneoService torneoService, GiocatoreService giocatoreService) {
        this.squadraService = squadraService;
        this.torneoService = torneoService;
        this.giocatoreService = giocatoreService;
    }

    @GetMapping("/admin/listaSquadre/nuova")
    public String mostraFormNuovaSquadra(Model model) {
        model.addAttribute("modelNuovaSquadra", new Squadra());
        return "admin/nuovaSquadra";
    }

    @PostMapping("/admin/listaSquadre/nuova")
    public String registraNuovaSquadra(Squadra squadra) {
        this.squadraService.salvaSquadra(squadra);
        return "redirect:/listaSquadre";
    }

    @GetMapping("/admin/squadra/{id}/modifica")
    public String formModificaSquadra(@PathVariable Long id, Model model) {
        Squadra squadra = this.squadraService.getSquadraById(id);

        model.addAttribute("modelSquadra", squadra);
        model.addAttribute("modelGiocatori", squadra.getGiocatori());
        model.addAttribute("modelGiocatoriSistemaSenzaSquadra", this.giocatoreService.getGiocatoriSenzaSquadra());

        return "admin/modificaSquadra";
    }

    @PostMapping("/admin/squadra/{id}/modifica")
    public String registraModificaSquadra(@PathVariable Long id, Squadra squadra) {
        Squadra squadraRegistrataDaModificare = this.squadraService.getSquadraById(id);

        squadraRegistrataDaModificare.setNome(squadra.getNome());
        squadraRegistrataDaModificare.setCitta(squadra.getCitta());
        squadraRegistrataDaModificare.setAnnoFondazione(squadra.getAnnoFondazione());

        this.squadraService.salvaSquadra(squadraRegistrataDaModificare);

        return "redirect:/squadra/" + id;
    }

    @PostMapping("/admin/squadra/{id}/elimina")
    public String eliminaSquadra(@PathVariable Long id) {

        this.squadraService.eliminaSquadra(id);

        return "redirect:/listaSquadre";
    }

    @PostMapping("/admin/squadra/{id}/aggiungiGiocatore")
    public String registraAggiuntaGiocatoreNellaSquadra(@PathVariable Long id, @RequestParam(required = false) Long giocatoreId) {
        if (giocatoreId != null)
            this.squadraService.aggiungiGiocatore(id, giocatoreId);


        return "redirect:/admin/squadra/" + id + "/modifica";
    }

    @PostMapping("/admin/squadra/{id}/rimuoviGiocatore")
    public String rimuoviGiocatoreDallaSquadra(@PathVariable Long id, @RequestParam(required = false) Long giocatoreId) {
        if (giocatoreId != null)
            this.squadraService.rimuoviGiocatoreDallaSquadra(id, giocatoreId);


        return "redirect:/admin/squadra/" + id + "/modifica";
    }
}