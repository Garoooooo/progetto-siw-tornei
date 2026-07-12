package org.example.progettosiwtornei;

import org.example.progettosiwtornei.entity.Commento;
import org.example.progettosiwtornei.entity.Partita;
import org.example.progettosiwtornei.repository.CommentoRepository;
import org.example.progettosiwtornei.repository.PartitaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootApplication
public class MAIN_ProgettoSiwTornei implements CommandLineRunner {

    private final CommentoRepository commentoRepository;
    private final PartitaRepository partitaRepository;

    public MAIN_ProgettoSiwTornei(CommentoRepository commentoRepository,
                                  PartitaRepository partitaRepository) {
        this.commentoRepository = commentoRepository;
        this.partitaRepository = partitaRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(MAIN_ProgettoSiwTornei.class, args);
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        testNpiu1();
    }

    private void testNpiu1() {
        // Trova la prima partita con commenti
        List<Partita> tutte = partitaRepository.findAll();
        Partita partitaConCommenti = null;
        for (Partita p : tutte) {
            if (p.getListaCommenti() != null && !p.getListaCommenti().isEmpty()) {
                partitaConCommenti = p;
                break;
            }
        }

        if (partitaConCommenti == null) {
            System.out.println("NESSUN COMMENTO NEL DATABASE! Aggiungi un commento prima.");
            return;
        }

        Long idPartita = partitaConCommenti.getId();
        System.out.println("Test con partita ID=" + idPartita);

        // --- TEST 1: LAZY ---
        long start1 = System.currentTimeMillis();
        List<Commento> commentiLazy = commentoRepository.findByPartitaId(idPartita);
        for (Commento c : commentiLazy) {
            c.getUtente().getUsername();
        }
        long end1 = System.currentTimeMillis();

        // --- TEST 2: JOIN FETCH ---
        long start2 = System.currentTimeMillis();
        List<Commento> commentiFetch = commentoRepository.findByPartitaIdWithJoinFetch(idPartita);
        for (Commento c : commentiFetch) {
            c.getUtente().getUsername();
        }
        long end2 = System.currentTimeMillis();

        System.out.println("========== RISULTATI N+1 ==========");
        System.out.println("LAZY (normale): " + (end1 - start1) + " ms");
        System.out.println("JOIN FETCH:     " + (end2 - start2) + " ms");
        System.out.println("====================================");
    }
}