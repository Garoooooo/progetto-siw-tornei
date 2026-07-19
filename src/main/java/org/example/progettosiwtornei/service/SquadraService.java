package org.example.progettosiwtornei.service;

import org.example.progettosiwtornei.entity.Giocatore;
import org.example.progettosiwtornei.entity.Partita;
import org.example.progettosiwtornei.entity.Squadra;
import org.example.progettosiwtornei.repository.GiocatoreRepository;
import org.example.progettosiwtornei.repository.SquadraRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class SquadraService {
    private final SquadraRepository squadraRepository;
    private final GiocatoreRepository giocatoreRepository;

    public SquadraService(SquadraRepository squadraRepository, GiocatoreRepository giocatoreRepository) {
        this.squadraRepository = squadraRepository;
        this.giocatoreRepository = giocatoreRepository;
    }

    @Transactional(readOnly = true)
    public Squadra getSquadraById(Long id){
        return this.squadraRepository.findById(id).orElse(null);
    }

    @Transactional
    public void salvaSquadra(Squadra squadra){
        this.squadraRepository.save(squadra);
    }

    @Transactional
    public void rimuoviGiocatoreDallaSquadra(Long idSquadra, Long idGiocatore) {
        Squadra squadra = this.squadraRepository.findById(idSquadra).orElse(null);
        Giocatore giocatore = this.giocatoreRepository.findById(idGiocatore).orElse(null);

        if (squadra != null && giocatore != null) {
            List<Giocatore> giocatoriAggiornati = squadra.getGiocatori();
            giocatoriAggiornati.remove(giocatore);
            giocatore.setSquadra(null);
            squadra.setGiocatori(giocatoriAggiornati);
            this.giocatoreRepository.save(giocatore);
            this.squadraRepository.save(squadra);
        }
    }

    @Transactional
    public void eliminaSquadra(Long idSquadra) {

        Squadra squadra = this.squadraRepository.findById(idSquadra).orElse(null);

        if (squadra != null) {

            for (Giocatore g : squadra.getGiocatori()) {
                g.setSquadra(null);
                this.giocatoreRepository.save(g);
            }

            this.squadraRepository.delete(squadra);
        }
    }

    @Transactional
    public void aggiungiGiocatore(Long idSquadra, Long idGiocatore) {
        Squadra squadra = this.squadraRepository.findById(idSquadra).orElse(null);
        Giocatore giocatore = this.giocatoreRepository.findById(idGiocatore).orElse(null);

        if (squadra != null && giocatore != null) {
            giocatore.setSquadra(squadra);
            this.giocatoreRepository.save(giocatore);
        }
    }

    @Transactional(readOnly = true)
    public List<Squadra> getAllSquadre(){
        List <Squadra> allSquadre=this.squadraRepository.findAll();
        return allSquadre;
    }
}