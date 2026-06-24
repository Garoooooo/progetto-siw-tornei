package org.example.progettosiwtornei.repository;
import org.example.progettosiwtornei.entity.Torneo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TorneoRepository extends JpaRepository<Torneo, Long> {
    Long id(Long id);
}
