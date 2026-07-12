package org.example.progettosiwtornei.service;

import org.example.progettosiwtornei.entity.Commento;
import org.example.progettosiwtornei.entity.Partita;
import org.example.progettosiwtornei.entity.Utente;
import org.example.progettosiwtornei.repository.CommentoRepository;
import org.example.progettosiwtornei.repository.PartitaRepository;
import org.example.progettosiwtornei.repository.UtenteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PartitaService {
    private final PartitaRepository partitaRepository;
    private final CommentoRepository commentoRepository;
    private final UtenteRepository utenteRepository;

    public PartitaService(PartitaRepository partitaRepository, CommentoRepository commentoRepository, UtenteRepository utenteRepository) {
        this.partitaRepository = partitaRepository;
        this.commentoRepository = commentoRepository;
        this.utenteRepository = utenteRepository;
    }

    @Transactional
    public void salvaPartita(Partita partita){
        this.partitaRepository.save(partita);
    }

    @Transactional(readOnly = true)
    public List<Partita> getAllPartite(){
        return this.partitaRepository.findAll();
    }

    @Transactional
    public void eliminaPartita(Long id) {
        this.partitaRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Partita getPartitaById(Long id){
        return this.partitaRepository.findById(id).orElse(null);
    }
}