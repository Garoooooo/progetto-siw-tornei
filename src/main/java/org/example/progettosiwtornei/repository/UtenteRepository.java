package org.example.progettosiwtornei.repository;
import org.example.progettosiwtornei.entity.Giocatore;
import org.example.progettosiwtornei.entity.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface UtenteRepository extends JpaRepository<Utente, Long> {

    Utente findByUsername(String username);

}
