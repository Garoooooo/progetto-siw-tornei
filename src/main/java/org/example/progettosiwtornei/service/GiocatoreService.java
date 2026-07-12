package org.example.progettosiwtornei.service;

import org.example.progettosiwtornei.entity.Giocatore;
import org.example.progettosiwtornei.entity.Squadra;
import org.example.progettosiwtornei.repository.GiocatoreRepository;
import org.example.progettosiwtornei.repository.SquadraRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class GiocatoreService {
    private final GiocatoreRepository giocatoreRepository;
    private final SquadraRepository squadraRepository;

    public GiocatoreService(GiocatoreRepository g, SquadraRepository s) {
        this.giocatoreRepository = g;
        this.squadraRepository=s;
    }

    @Transactional(readOnly = true)
    public List<Giocatore> getGiocatoriSenzaSquadra(){
        List<Giocatore> giocatori=this.giocatoreRepository.findAll();
        List<Giocatore> giocatoriSenzaSquadra=new ArrayList<>();

        for(Giocatore giocatoreCorrente:giocatori){
            if(giocatoreCorrente.getSquadra()==null)
                giocatoriSenzaSquadra.add(giocatoreCorrente);
        }
        return giocatoriSenzaSquadra;
    }

    @Transactional
    public void modificaGiocatore(Long idGiocatore, Giocatore giocatoreModificato, Long squadraId) {
        Giocatore giocatore = this.giocatoreRepository.findById(idGiocatore).orElse(null);

        if (giocatore != null) {
            giocatore.setNome(giocatoreModificato.getNome());
            giocatore.setCognome(giocatoreModificato.getCognome());
            giocatore.setDataDiNascita(giocatoreModificato.getDataDiNascita());
            giocatore.setAltezza(giocatoreModificato.getAltezza());
            giocatore.setRuoloGiocatore(giocatoreModificato.getRuoloGiocatore());
            giocatore.setNumeroMaglia(giocatoreModificato.getNumeroMaglia());

            if (squadraId != null) {
                Squadra squadra = this.squadraRepository.findById(squadraId).orElse(null);
                giocatore.setSquadra(squadra);
            } else {
                giocatore.setSquadra(null);
            }

            this.giocatoreRepository.save(giocatore);
        }
    }

    @Transactional
    public void eliminaGiocatore(Long idGiocatore) {

        Giocatore giocatore = this.giocatoreRepository.findById(idGiocatore).orElse(null);

        if (giocatore != null) {
            this.giocatoreRepository.delete(giocatore);
        }
    }

    @Transactional(readOnly = true)
    public List<Giocatore> getAllGiocatoriSquadra(Long id){
        Squadra squadra=squadraRepository.findById(id).orElse(null);
        return squadra.getGiocatori();
    }

    @Transactional(readOnly = true)
    public Giocatore getGiocatore(Long id){
        return this.giocatoreRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<Giocatore> getAllGiocatoriRegistratiNelSistema(){
        return giocatoreRepository.findAll();
    }

    @Transactional
    public void salvaGiocatore(Giocatore giocatore){
        this.giocatoreRepository.save(giocatore);
    }
}