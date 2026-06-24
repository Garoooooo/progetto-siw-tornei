package org.example.progettosiwtornei.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")    // "/" indica l' indirizzo localhost:8080 --> quando il server richiede il get del localhost:8080 esegui questo metodo
    public String index() {
        return "index";
    }

    @GetMapping("/home")
    public String mostraHome() {
        return "home";
    }

}