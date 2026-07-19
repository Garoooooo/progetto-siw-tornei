package org.example.progettosiwtornei.repository;

import org.example.progettosiwtornei.entity.Commento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentoRepository extends JpaRepository<Commento, Long> {

    // LAZY
    List<Commento> findByUtenteId(Long utenteId);

    // JOIN FETCH
    @Query("SELECT c FROM Commento c " +
            "LEFT JOIN FETCH c.utente " +
            "WHERE c.utente.id = :idUtente")
    List<Commento> findByUtenteIdWithJoinFetch(@Param("idUtente") Long idUtente);

}