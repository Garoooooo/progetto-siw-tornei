package org.example.progettosiwtornei.repository;

import org.example.progettosiwtornei.entity.Commento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentoRepository extends JpaRepository<Commento, Long> {
}
