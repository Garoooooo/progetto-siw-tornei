package org.example.progettosiwtornei.controller;

import org.example.progettosiwtornei.entity.Arbitro;
import org.example.progettosiwtornei.service.ArbitroService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AdminArbitroController {

    private final ArbitroService arbitroService;

    public AdminArbitroController(ArbitroService arbitroService) {
        this.arbitroService = arbitroService;
    }

    @GetMapping("/admin/listaArbitri/nuovo")
    public String mostraFormNuovoArbitro(Model model) {
        model.addAttribute("modelNuovoArbitro", new Arbitro());
        return "admin/nuovoArbitro";
    }

    @PostMapping("/admin/arbitro/{id}/elimina")
    public String eliminaArbitro(@PathVariable Long id) {
        this.arbitroService.eliminaArbitro(id);

        return "redirect:/listaArbitri";
    }

    @PostMapping("/admin/listaArbitri/nuovo")
    public String registraNuovoArbitro(Arbitro arbitro) {
        this.arbitroService.salvaArbitro(arbitro);
        return "redirect:/listaArbitri";
    }
}