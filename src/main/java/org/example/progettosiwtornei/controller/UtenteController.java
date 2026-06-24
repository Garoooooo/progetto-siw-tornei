package org.example.progettosiwtornei.controller;

import org.example.progettosiwtornei.entity.Utente;
import org.example.progettosiwtornei.service.UtenteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class UtenteController {
    private final UtenteService utenteService;
    
    public UtenteController(UtenteService utenteService){
        this.utenteService=utenteService;
    }

    // Mostra la pagina di registrazione quando apro /registrazione
    @GetMapping("/registrazione")
    public String mostraRegistrazione(Model model) {
        model.addAttribute("utente", new Utente()); // oggetto vuoto collegato alla form HTML
        return "registrazione"; // apre registrazione.html
    }

    // Riceve i dati della form di registrazione e salva il nuovo utente
    @PostMapping("/registrazione")
    public String registraUtente(Utente utente) {
        this.utenteService.registraNuovoUtente(utente);
        return "redirect:/login"; // dopo la registrazione manda alla pagina login. SI usa il redirect  per evitare che dopo un aggiornamento pagina il form non venga inviato di nuovo
    }

    // Mostra la pagina di login
    @GetMapping("/login")
    public String mostraLogin() {
        return "login"; // apre login.html
    }
}