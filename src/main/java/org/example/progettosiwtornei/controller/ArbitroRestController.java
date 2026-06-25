package org.example.progettosiwtornei.controller;

import org.example.progettosiwtornei.entity.Arbitro;
import org.example.progettosiwtornei.service.ArbitroService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
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