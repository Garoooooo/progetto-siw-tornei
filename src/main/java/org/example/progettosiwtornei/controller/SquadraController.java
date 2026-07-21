package org.example.progettosiwtornei.controller;

import org.example.progettosiwtornei.service.GiocatoreService;
import org.example.progettosiwtornei.service.SquadraService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class SquadraController {
private final SquadraService squadraService;
private final GiocatoreService giocatoreService;

public SquadraController(SquadraService s, GiocatoreService g){
    this.squadraService=s; this.giocatoreService=g;
}

@GetMapping("/squadra/{id}")
public String mostraSquadraEAllSuoiGiocatori(@PathVariable Long id, Model model){
    model.addAttribute("modelSquadra", squadraService.getSquadraById(id));
    model.addAttribute("modelPartiteSquadra",squadraService.getAllPartiteSquadra(id));
    model.addAttribute("modelGiocatoriSquadra", giocatoreService.getAllGiocatoriSquadra(id));
    return "squadra";
}

@GetMapping("/listaSquadre")
public String mostraAllSquadreRegistrate(Model model){
    model.addAttribute("modelSquadre",this.squadraService.getAllSquadre());
    return "listaSquadre";
}

}
