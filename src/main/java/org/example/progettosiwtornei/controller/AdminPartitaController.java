package org.example.progettosiwtornei.controller;

import org.example.progettosiwtornei.entity.Partita;
import org.example.progettosiwtornei.service.ArbitroService;
import org.example.progettosiwtornei.service.PartitaService;
import org.example.progettosiwtornei.service.SquadraService;
import org.example.progettosiwtornei.service.TorneoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminPartitaController {

    private final PartitaService partitaService;
    private final TorneoService torneoService;
    private final SquadraService squadraService;
    private final ArbitroService arbitroService;

    public AdminPartitaController(PartitaService partitaService, TorneoService torneoService, SquadraService squadraService, ArbitroService arbitroService) {
        this.partitaService = partitaService;
        this.torneoService = torneoService;
        this.squadraService = squadraService;
        this.arbitroService = arbitroService;
    }

    @GetMapping("/admin/torneo/{id}/partita/nuova")
    public String mostraFormNuovaPartitaDaTorneo(@PathVariable Long id, Model model) {
        model.addAttribute("modelNuovaPartita", new Partita());
        model.addAttribute("modelTorneo", this.torneoService.getTorneoById(id));
        model.addAttribute("modelSquadre", this.torneoService.getSquadrePartecipantiAlTorneo(id));
        model.addAttribute("modelArbitri", this.arbitroService.getAllArbitri());
        return "admin/nuovaPartita";
    }

    @PostMapping("/admin/torneo/{id}/partita/nuova")
    public String registraNuovaPartitaDaTorneo(@PathVariable Long id, Partita partita, @RequestParam Long squadraCasaId, @RequestParam Long squadraOspiteId, @RequestParam Long arbitroId) {
        partita.setId(null);
        partita.setTorneo(this.torneoService.getTorneoById(id));
        partita.setSquadraCasa(this.squadraService.getSquadraById(squadraCasaId));
        partita.setSquadraOspite(this.squadraService.getSquadraById(squadraOspiteId));
        partita.setArbitro(this.arbitroService.getArbitro(arbitroId));

        this.partitaService.salvaPartita(partita);

        return "redirect:/listaTornei/" + id;
    }

    @GetMapping("/admin/partita/{id}/modifica")
    public String mostraFormModificaPartita(@PathVariable Long id, Model model) {
        Partita partita = this.partitaService.getPartitaById(id);

        model.addAttribute("modelPartita", partita);
        model.addAttribute("modelTorneo", partita.getTorneo());
        model.addAttribute("modelSquadre", this.torneoService.getSquadrePartecipantiAlTorneo(partita.getTorneo().getId()));
        model.addAttribute("modelArbitri", this.arbitroService.getAllArbitri());

        return "admin/modificaPartita";
    }

    @PostMapping("/admin/partita/{idPartita}/modifica")
    public String modificaPartita(@PathVariable Long idPartita,  //idPartita della partita da modificare
                                  Partita partita,               //contenitore temporaneo, contiene i dati scritti nel form
                                  @RequestParam Long squadraCasaId,  //dati selezionati nel form dal menu a tendina
                                  @RequestParam Long squadraOspiteId,
                                  @RequestParam Long arbitroId) {

        Partita partitaDaModificare = this.partitaService.getPartitaById(idPartita);

        partitaDaModificare.setGiornata(partita.getGiornata());
        partitaDaModificare.setDataOraPartita(partita.getDataOraPartita());
        partitaDaModificare.setLuogo(partita.getLuogo());
        partitaDaModificare.setStatoPartita(partita.getStatoPartita());
        partitaDaModificare.setGoalsHome(partita.getGoalsHome());
        partitaDaModificare.setGoalsAway(partita.getGoalsAway());

        partitaDaModificare.setSquadraCasa(this.squadraService.getSquadraById(squadraCasaId));
        partitaDaModificare.setSquadraOspite(this.squadraService.getSquadraById(squadraOspiteId));
        partitaDaModificare.setArbitro(this.arbitroService.getArbitro(arbitroId));

        this.partitaService.salvaPartita(partitaDaModificare);

        return "redirect:/listaTornei/" + partitaDaModificare.getTorneo().getId();
    }

    @PostMapping("/admin/partita/{id}/elimina")
    public String eliminaPartita(@PathVariable Long id) {
        Partita partita = this.partitaService.getPartitaById(id);
        Long idTorneo = partita.getTorneo().getId();

        this.partitaService.eliminaPartita(id);

        return "redirect:/listaTornei/" + idTorneo;
    }
}