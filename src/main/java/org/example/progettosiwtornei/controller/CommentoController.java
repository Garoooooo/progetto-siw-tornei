package org.example.progettosiwtornei.controller;

import org.example.progettosiwtornei.entity.Commento;
import org.example.progettosiwtornei.service.CommentoService;
import org.example.progettosiwtornei.service.PartitaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CommentoController {
    private final CommentoService commentoService;
    private final PartitaService partitaService;

    public CommentoController(CommentoService commentoService, PartitaService partitaService) {
        this.commentoService = commentoService;
        this.partitaService = partitaService;
    }

    @PostMapping("/partita/{id}/commento")
    public String registraNuovoCommentoPartita(@PathVariable Long id, @RequestParam String testo) {

        this.commentoService.addCommento(id, testo);

        return "redirect:/partita/" + id;
    }

    @GetMapping("/commento/{id}/modifica")
    public String mostraFormModificaCommento(@PathVariable Long id, Model model) {
        Commento commento = this.commentoService.getCommentoById(id);

        model.addAttribute("modelCommento", commento);
        return "user/modificaCommento";
    }

    @PostMapping("/commento/{id}/modifica")
    public String registraModificaCommento(@PathVariable Long id, Commento commento) {
        Commento commentoSalvato = this.commentoService.modificaCommento(id, commento);

        if (commentoSalvato == null) {
            return "redirect:/";
        }

        return "redirect:/partita/" + commentoSalvato.getPartita().getId();
    }

    @PostMapping("/commento/{id}/elimina")
    public String eliminaCommento(@PathVariable Long id,
                                  @RequestParam Long idPartita) {

        this.commentoService.eliminaCommento(id);

        return "redirect:/partita/" + idPartita;
    }
}