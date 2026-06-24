package org.example.progettosiwtornei.controller;

import org.example.progettosiwtornei.service.ArbitroService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ArbitroController {
    private final ArbitroService arbitroService;

    public ArbitroController(ArbitroService arbitroService) {
        this.arbitroService = arbitroService;
    }


    @GetMapping("/arbitro/{id}")
    public  String getInfoArbitro(Model model, @PathVariable Long id){
        model.addAttribute("modelArbitro", this.arbitroService.getArbitro(id));
        model.addAttribute("modelPartiteArbitrateDallArbitro", this.arbitroService.listaPartiteArbitrateDallArbitro(id));
        return "/arbitro";
    }

    @GetMapping("/listaArbitri")
    public String getAllArbitri(Model model){
        model.addAttribute("modelArbitri", this.arbitroService.getAllArbitri());
        return "/listaArbitri";
    }

}
