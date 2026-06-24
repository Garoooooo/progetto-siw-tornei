package org.example.progettosiwtornei.repository;
import org.example.progettosiwtornei.entity.Arbitro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArbitroRepository extends JpaRepository<Arbitro, Long> {
}
