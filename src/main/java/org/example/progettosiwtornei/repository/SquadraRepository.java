package org.example.progettosiwtornei.repository;
import org.example.progettosiwtornei.entity.Giocatore;
import org.example.progettosiwtornei.entity.Squadra;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SquadraRepository extends JpaRepository<Squadra, Long> {
}
