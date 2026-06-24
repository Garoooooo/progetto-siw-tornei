package org.example.progettosiwtornei.repository;
import org.example.progettosiwtornei.entity.Giocatore;
import org.example.progettosiwtornei.entity.Partita;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartitaRepository extends JpaRepository<Partita, Long> {
}
