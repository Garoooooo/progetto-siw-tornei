package org.example.progettosiwtornei.service;

import org.example.progettosiwtornei.classiAusiliarie.RuoloUtente;
import org.example.progettosiwtornei.entity.Utente;
import org.example.progettosiwtornei.repository.UtenteRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UtenteService {
    private final UtenteRepository utenteRepository;
    private final PasswordEncoder passwordEncoder;

    public UtenteService(UtenteRepository utenteRepository, PasswordEncoder passwordEncoder) {
        this.utenteRepository = utenteRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Utente findByUsername(String username){
        return this.utenteRepository.findByUsername(username);
    }

    public void saveUtente(Utente utente){
        this.utenteRepository.save(utente);
    }

public void registraNuovoUtente(Utente utente){   //quando viene chiamato questo metodo, prendo dal database i dati digitati dal nuovo utente in html, cittografo la psw e avvio
        utente.setPassword(passwordEncoder.encode(utente.getPassword()));    //la password che ha inserito l utente, nel momento del salvataggio viene cittografata
        utente.setRuoloUtente(RuoloUtente.USER);
        this.utenteRepository.save(utente);

    }
}
