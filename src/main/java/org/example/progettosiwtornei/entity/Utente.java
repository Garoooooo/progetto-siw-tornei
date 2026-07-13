package org.example.progettosiwtornei.entity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import org.example.progettosiwtornei.classiAusiliarie.RuoloUtente;

import java.util.List;
import java.util.Objects;

@Entity
public class Utente {

    @Id @GeneratedValue
    private Long id;
    private String username;
    private String password;
    @Enumerated(EnumType.STRING)
    private RuoloUtente ruoloUtente;
    @OneToMany(mappedBy ="utente")
    @JsonIgnoreProperties("utente")
    private List<Commento> commentiPubblicati;


    public Utente() {
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Utente utente = (Utente) o;
        return Objects.equals(username, utente.username);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(username);
    }

    public List<Commento> getCommentiPubblicati() {
        return commentiPubblicati;
    }

    public void setCommentiPubblicati(List<Commento> commento) {
        this.commentiPubblicati = commento;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RuoloUtente getRuoloUtente() {
        return ruoloUtente;
    }

    public void setRuoloUtente(RuoloUtente ruoloUtente) {
        this.ruoloUtente = ruoloUtente;
    }
}