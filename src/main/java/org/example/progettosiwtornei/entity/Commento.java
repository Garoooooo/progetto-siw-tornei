package org.example.progettosiwtornei.entity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Commento {
    @Id @GeneratedValue
    private Long id;
    private String testo;
    private LocalDateTime dataEOra;
    @ManyToOne                 //molti commenti--> una partita
    @JsonIgnoreProperties({"listaCommenti", "arbitro", "squadraCasa", "squadraOspite", "torneo"})
    private Partita partita;  //un commento va a una sola partita, una partita può avere molti commenti
    @ManyToOne                 //molti commenti --> un utente
    @JsonIgnoreProperties("commentiPubblicati")
    private Utente utente;    //un commento è scritto solo da un utente, un utente può scrivere tanti commenti

    public Commento() {
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Commento commento = (Commento) o;
        return Objects.equals(dataEOra, commento.dataEOra) && Objects.equals(partita, commento.partita) && Objects.equals(utente, commento.utente);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dataEOra, partita, utente);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTesto() {
        return testo;
    }

    public void setTesto(String testoCommento) {
        this.testo = testoCommento;
    }

    public LocalDateTime getDataEOra() {
        return dataEOra;
    }

    public void setDataEOra(LocalDateTime dataEOraPubblicazione) {
        this.dataEOra = dataEOraPubblicazione;
    }

    public Partita getPartita() {
        return partita;
    }

    public void setPartita(Partita partita) {
        this.partita = partita;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }
}