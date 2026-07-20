package org.example.progettosiwtornei.controller;

import org.example.progettosiwtornei.entity.Arbitro;
import org.example.progettosiwtornei.service.ArbitroService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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


    @GetMapping("/api/utente")
    public Map<String, String> getUtenteCorrente(Authentication authentication) {
        String ruolo = authentication.getAuthorities().iterator().next().getAuthority();
        return Map.of("ruolo", ruolo);
    }

    @DeleteMapping("/api/arbitri/{id}")
    public void eliminaArbitro(@PathVariable Long id) {
        arbitroService.eliminaArbitro(id);
    }

}