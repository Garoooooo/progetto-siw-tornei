package org.example.progettosiwtornei.controller;

import org.example.progettosiwtornei.entity.Squadra;
import org.example.progettosiwtornei.entity.Torneo;
import org.example.progettosiwtornei.service.SquadraService;
import org.example.progettosiwtornei.service.TorneoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminTorneoController {

    private final TorneoService torneoService;
    private final SquadraService squadraService;

    public AdminTorneoController(TorneoService torneoService, SquadraService squadraService) {
        this.torneoService = torneoService;
        this.squadraService = squadraService;
    }

    @GetMapping("/admin/listaTornei/nuovo")
    public String mostraFormNuovoTorneo(Model model) {
        model.addAttribute("modelNuovoTorneo", new Torneo());
        model.addAttribute("modelSquadre", this.squadraService.getAllSquadre());
        return "admin/nuovoTorneo";
    }

    @PostMapping("/admin/listaTornei/nuovo")
    public String creaNuovoTorneo(Torneo torneo) {
        this.torneoService.salvaTorneo(torneo);
        return "redirect:/listaTornei";
    }

    @PostMapping("/admin/torneo/{id}/aggiungiSquadra")
    public String aggiungiSquadraAlTorneo(@PathVariable Long id, @RequestParam Long squadraId) {

        Squadra squadra = this.squadraService.getSquadraById(squadraId);
        this.torneoService.aggiungiSquadraAlTorneo(id, squadra);

        return "redirect:/listaTornei/" + id;
    }

    @GetMapping("/admin/torneo/{id}/modifica")
    public String mostraFormModificaTorneo(@PathVariable Long id, Model model) {
        Torneo torneo = this.torneoService.getTorneoById(id);

        model.addAttribute("modelTorneo", torneo);
        model.addAttribute("modelSquadre", this.torneoService.getSquadrePartecipantiAlTorneo(id));

        return "admin/modificaTorneo";
    }

    @PostMapping("/admin/torneo/{id}/modifica")
    public String registraModificaTorneo(@PathVariable Long id, Torneo torneo) {

        Torneo torneoRegistratoDaModificare = this.torneoService.getTorneoById(id);

        torneoRegistratoDaModificare.setNome(torneo.getNome());
        torneoRegistratoDaModificare.setAnno(torneo.getAnno());
        torneoRegistratoDaModificare.setDescrizione(torneo.getDescrizione());

        this.torneoService.salvaTorneo(torneoRegistratoDaModificare);

        return "redirect:/listaTornei/" + id;
    }

    @PostMapping("/admin/torneo/{id}/rimuoviSquadra")            //required false serve per dire che quel parametro non è obbligatorio
                                                                 //serve perchè se premo conferma ma non ho selezionato nessuna squadra il programma crasha
    public String rimuoviSquadraDalTorneo(@PathVariable Long id, @RequestParam(required = false) Long squadraId) {

        this.torneoService.rimuoviSquadraDalTorneo(id, squadraId);
        return "redirect:/admin/torneo/" + id + "/modifica";

    }

    @PostMapping("/admin/torneo/{id}/elimina")
    public String eliminaTorneo(@PathVariable Long id) {
        this.torneoService.eliminaTorneo(id);

        return "redirect:/listaTornei";
    }
}