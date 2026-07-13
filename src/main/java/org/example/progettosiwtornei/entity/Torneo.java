package org.example.progettosiwtornei.entity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Entity
public class Torneo {

    @Id
    @GeneratedValue
    private Long id;
    @OneToMany(mappedBy = "torneo" )
    @JsonIgnoreProperties({"torneo", "arbitro", "squadraCasa", "squadraOspite", "listaCommenti"})
    private List<Partita> listaPartite;
    private String nome;
    private Integer anno;
    private String descrizione;
    @ManyToMany
    @JsonIgnoreProperties({"torneiIscritti", "partiteInCasa", "partiteInTrasferta", "giocatori"})
    private List<Squadra> squadrePartecipanti;        //<squadra, punti fatti>

    public Torneo() {
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Torneo torneo = (Torneo) o;
        return Objects.equals(nome, torneo.nome) && Objects.equals(anno, torneo.anno);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, anno);
    }

    public List<Partita> getListaPartite() {
        return listaPartite;
    }

    public void setListaPartite(List<Partita> listaPartite) {
        this.listaPartite = listaPartite;
    }

    public List<Squadra> getSquadrePartecipanti() {
        return squadrePartecipanti;
    }

    public void setSquadrePartecipanti(List<Squadra> squadrePartecipanti) {
        this.squadrePartecipanti = squadrePartecipanti;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Integer getAnno() {
        return anno;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setId(Long id){ this.id = id; }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setAnno(Integer anno) {
        this.anno = anno;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

}