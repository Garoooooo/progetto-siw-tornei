package org.example.progettosiwtornei.entity;
import jakarta.persistence.*;

import java.time.LocalDate;

import org.example.progettosiwtornei.classiAusiliarie.RuoloGiocatore;

import java.util.Objects;

@Entity
public class Giocatore {
    @Id
    @GeneratedValue
    private Long id;
    private String nome;
    private String cognome;
    private LocalDate dataDiNascita;
    private Float altezza;
    @Enumerated(EnumType.STRING)
    private RuoloGiocatore ruoloGiocatore;
    @ManyToOne
    private Squadra squadra;

    private Integer numeroMaglia;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Giocatore giocatore = (Giocatore) o;
        return Objects.equals(nome, giocatore.nome) && Objects.equals(cognome, giocatore.cognome) && Objects.equals(dataDiNascita, giocatore.dataDiNascita);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, cognome, dataDiNascita);
    }

    public Giocatore() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public LocalDate getDataDiNascita() {
        return dataDiNascita;
    }

    public void setDataDiNascita(LocalDate dataDiNascita) {
        this.dataDiNascita = dataDiNascita;
    }

    public Float getAltezza() {
        return altezza;
    }

    public void setAltezza(Float altezza) {
        this.altezza = altezza;
    }

    public RuoloGiocatore getRuoloGiocatore() {
        return ruoloGiocatore;
    }

    public void setRuoloGiocatore(RuoloGiocatore ruoloGiocatore) {
        this.ruoloGiocatore = ruoloGiocatore;
    }

    public Squadra getSquadra() {
        return squadra;
    }

    public void setSquadra(Squadra squadra) {
        this.squadra = squadra;
    }

    public Integer getNumeroMaglia() {
        return numeroMaglia;
    }

    public void setNumeroMaglia(Integer numeroMaglia) {
        this.numeroMaglia = numeroMaglia;
    }
}
