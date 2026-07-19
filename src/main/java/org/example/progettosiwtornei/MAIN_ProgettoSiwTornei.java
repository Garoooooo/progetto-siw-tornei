package org.example.progettosiwtornei;

import org.example.progettosiwtornei.entity.Commento;
import org.example.progettosiwtornei.entity.Utente;
import org.example.progettosiwtornei.repository.CommentoRepository;
import org.example.progettosiwtornei.repository.UtenteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootApplication
public class MAIN_ProgettoSiwTornei implements CommandLineRunner {

    private final CommentoRepository commentoRepository;
    private final UtenteRepository utenteRepository;

    public MAIN_ProgettoSiwTornei(CommentoRepository commentoRepository,
                                  UtenteRepository utenteRepository) {
        this.commentoRepository = commentoRepository;
        this.utenteRepository = utenteRepository;
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
        // trova il primo utente che ha scritto almeno un commento
        List<Utente> tuttiGliUtenti = utenteRepository.findAll();
        Utente utenteConCommenti = null;
        for (Utente u : tuttiGliUtenti) {
            if (u.getCommentiPubblicati() != null && !u.getCommentiPubblicati().isEmpty()) {
                utenteConCommenti = u;
                break;
            }
        }

        if (utenteConCommenti == null) {
            System.out.println("NESSUN UTENTE CON COMMENTI! Aggiungi un commento prima.");
            return;
        }

        Long idUtente = utenteConCommenti.getId();
        System.out.println("Test con utente ID=" + idUtente + " (username=" + utenteConCommenti.getUsername() + ")");

        //test lazy
        long start1 = System.currentTimeMillis();
        List<Commento> commentiLazy = commentoRepository.findByUtenteId(idUtente);
        for (Commento c : commentiLazy) {
            c.getUtente().getUsername();  // N+1: ogni commento fa una query extra per Utente
        }
        long end1 = System.currentTimeMillis();

        //test join fetch
        long start2 = System.currentTimeMillis();
        List<Commento> commentiFetch = commentoRepository.findByUtenteIdWithJoinFetch(idUtente);
        for (Commento c : commentiFetch) {
            c.getUtente().getUsername();  // 0 query extra: Utente già caricato dal JOIN
        }
        long end2 = System.currentTimeMillis();

        System.out.println("");
        System.out.println("");
        System.out.println("RISULTATI ANALISI N+1:");
        System.out.println("LAZY :" + (end1 - start1) + " ms");
        System.out.println("JOIN FETCH :" + (end2 - start2) + " ms");
       }
}