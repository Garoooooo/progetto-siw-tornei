package org.example.progettosiwtornei.controller;

import org.example.progettosiwtornei.entity.Commento;
import org.example.progettosiwtornei.service.CommentoService;
import org.example.progettosiwtornei.service.PartitaService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CommentoController {
    public CommentoService commentoService;
    public PartitaService partitaService;

    public CommentoController(CommentoService commentoService, PartitaService partitaService) {
        this.commentoService = commentoService;
        this.partitaService = partitaService;
    }

    @PostMapping("/partita/{id}/commento")
    public String registraNuovoCommentoPartita(@PathVariable Long id,
                                               @RequestParam String testo) {

        this.commentoService.addCommento(id, testo);

        return "redirect:/partita/" + id;
    }

    @PostMapping("/commento/{id}/elimina")
    public String eliminaCommento(@PathVariable Long id,
                                  @RequestParam Long idPartita) {

        this.commentoService.eliminaCommento(id);

        return "redirect:/partita/" + idPartita;
    }

}
