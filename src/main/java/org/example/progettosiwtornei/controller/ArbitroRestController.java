package org.example.progettosiwtornei.controller;

import org.example.progettosiwtornei.entity.Arbitro;
import org.example.progettosiwtornei.service.ArbitroService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController   // @Controller restituisce pagine HTML
                  // @RestController restituisce dati che react può usare nudi
public class ArbitroRestController {

    private final ArbitroService arbitroService;

    public ArbitroRestController(ArbitroService arbitroService) {
        this.arbitroService = arbitroService;
    }

    @GetMapping("/api/arbitri")
    public List<Arbitro> getArbitri() {
        return this.arbitroService.getAllArbitri();
    }
}