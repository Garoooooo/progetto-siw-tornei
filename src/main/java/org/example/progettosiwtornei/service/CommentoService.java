package org.example.progettosiwtornei.service;
import org.example.progettosiwtornei.entity.Commento;
import org.example.progettosiwtornei.entity.Partita;
import org.example.progettosiwtornei.entity.Utente;
import org.example.progettosiwtornei.repository.CommentoRepository;
import org.example.progettosiwtornei.repository.PartitaRepository;
import org.example.progettosiwtornei.repository.UtenteRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentoService {
    private final CommentoRepository commentoRepository;
    private final PartitaRepository partitaRepository;
    private final UtenteRepository utenteRepository;

    public CommentoService(CommentoRepository commentoRepository, PartitaRepository partitaRepository, UtenteRepository utenteRepository) {
        this.commentoRepository = commentoRepository;
        this.partitaRepository = partitaRepository;
        this.utenteRepository = utenteRepository;
    }

    @Transactional
    public void addCommento(Long idPartita, String testo) {
        Partita partita = this.partitaRepository.findById(idPartita).orElse(null);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Utente utente = this.utenteRepository.findByUsername(username);

        if (partita != null && utente != null) {
            Commento commento = new Commento();

            commento.setTesto(testo);
            commento.setPartita(partita);
            commento.setUtente(utente);
            commento.setDataEOra(LocalDateTime.now());

            this.commentoRepository.save(commento);
        }
    }

    @Transactional
    public void eliminaCommento(Long idCommento) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Utente utenteLoggato = this.utenteRepository.findByUsername(username);
        Commento commento = this.commentoRepository.findById(idCommento).orElse(null);


        if (commento != null && commento.getUtente() != null && commento.getUtente().equals(utenteLoggato)) {
            Partita partitaCorrente=commento.getPartita();
            List<Commento> listaCommentiPartita=partitaCorrente.getListaCommenti();
            listaCommentiPartita.remove(commento);
            partitaRepository.save(partitaCorrente);
            this.commentoRepository.delete(commento);
        }

    }

    @Transactional(readOnly = true)
    public List<Commento> getTuttiCommentiPerPartita(Long idPartita){
        Partita partita= this.partitaRepository.findById(idPartita).orElse(null);
        if(partita==null)
            return null;
        else
            return partita.getListaCommenti();
    }

}